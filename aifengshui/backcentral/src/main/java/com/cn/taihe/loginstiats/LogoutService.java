package com.cn.taihe.loginstiats;

import com.cn.taihe.config.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

  private static final Logger logger = LoggerFactory.getLogger(LogoutService.class);

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private MemoryTokenBlacklist memoryTokenBlacklist;

  /**
   * 退出登录 - 将Token加入黑名单
   */
  public void logout(String token) {
    try {
      if (token == null || token.trim().isEmpty()) {
        logger.warn("尝试将空Token加入黑名单");
        return;
      }

      // 验证token基本格式
      if (!isValidTokenFormat(token)) {
        logger.warn("Token格式无效: {}", maskToken(token));
        return;
      }

      // 检查token是否还有效（但不过期检查，因为过期token也需要加入黑名单）
      boolean isValid = false;
      try {
        jwtUtil.validateToken(token);
        isValid = true;
      } catch (Exception e) {
        // 即使token过期，只要格式正确就加入黑名单
        logger.debug("Token验证失败，但仍可加入黑名单: {}", e.getMessage());
      }

      // 计算剩余有效期（如果是有效token）
      long remainingTime = 0;
      if (isValid) {
        remainingTime = jwtUtil.getTokenRemainingTime(token);
        if (remainingTime <= 0) {
          logger.debug("Token已过期，无需加入黑名单");
          return;
        }
      } else {
        // 对于无效token，设置一个较短的过期时间（1小时）
        remainingTime = 60 * 60 * 1000; // 1小时
      }

      // 加入内存黑名单
      memoryTokenBlacklist.addToBlacklist(token, remainingTime);
      logger.info("用户退出登录成功，Token加入黑名单，剩余有效期: {}秒", remainingTime / 1000);

    } catch (Exception e) {
      logger.error("退出登录失败", e);
      throw new RuntimeException("退出登录失败", e);
    }
  }

  /**
   * 强制退出所有用户（管理员功能）
   */
  public void forceLogoutAll() {
    memoryTokenBlacklist.forceCleanup();
    logger.info("强制清理所有黑名单Token");
  }

  /**
   * 获取退出统计信息
   */
  public Object getLogoutStatistics() {
    return memoryTokenBlacklist.getStatistics();
  }

  private boolean isValidTokenFormat(String token) {
    return token != null && token.split("\\.").length == 3;
  }

  private String maskToken(String token) {
    if (token == null || token.length() <= 10) {
      return "***";
    }
    return token.substring(0, 10) + "***";
  }
}
