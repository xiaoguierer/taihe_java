package com.cn.taihe.loginstiats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 新增内存黑名单管理类
 **/
@Component
public class MemoryTokenBlacklist {

  private static final Logger logger = LoggerFactory.getLogger(MemoryTokenBlacklist.class);

  // 存储黑名单Token和过期时间
  private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

  // 用于定期清理的队列（避免遍历整个map）
  private final ConcurrentLinkedQueue<TokenExpiry> expiryQueue = new ConcurrentLinkedQueue<>();

  // 统计信息
  private final AtomicLong totalBlacklisted = new AtomicLong(0);
  private final AtomicLong totalCleaned = new AtomicLong(0);

  private static class TokenExpiry {
    final String token;
    final long expiryTime;

    TokenExpiry(String token, long expiryTime) {
      this.token = token;
      this.expiryTime = expiryTime;
    }
  }

  /**
   * 将Token加入黑名单
   */
  public void addToBlacklist(String token, long expiresInMillis) {
    if (token == null || token.trim().isEmpty()) {
      return;
    }

    long expiryTime = System.currentTimeMillis() + expiresInMillis;
    blacklist.put(token, expiryTime);
    expiryQueue.offer(new TokenExpiry(token, expiryTime));
    totalBlacklisted.incrementAndGet();

    logger.debug("Token加入内存黑名单，剩余有效期: {}秒", expiresInMillis / 1000);
  }

  /**
   * 检查Token是否在黑名单中
   */
  public boolean isBlacklisted(String token) {
    if (token == null || token.trim().isEmpty()) {
      return false;
    }

    Long expiryTime = blacklist.get(token);
    if (expiryTime == null) {
      return false;
    }

    // 检查是否已过期
    if (System.currentTimeMillis() > expiryTime) {
      // 异步清理过期Token
      blacklist.remove(token);
      return false;
    }

    return true;
  }

  /**
   * 从黑名单中移除Token（用于手动清理）
   */
  public void removeFromBlacklist(String token) {
    if (token != null) {
      blacklist.remove(token);
      logger.debug("Token从黑名单中移除: {}", maskToken(token));
    }
  }

  /**
   * 获取黑名单大小
   */
  public int getBlacklistSize() {
    return blacklist.size();
  }

  /**
   * 定期清理过期Token（每5分钟执行一次）
   */
  @Scheduled(fixedRate = 300000) // 5分钟
  public void cleanupExpiredTokens() {
    long currentTime = System.currentTimeMillis();
    int cleanedCount = 0;

    // 清理队列中的过期Token
    TokenExpiry tokenExpiry;
    while ((tokenExpiry = expiryQueue.peek()) != null) {
      if (tokenExpiry.expiryTime <= currentTime) {
        // 从队列中移除
        expiryQueue.poll();
        // 从map中移除（如果还存在的话）
        Long storedExpiry = blacklist.get(tokenExpiry.token);
        if (storedExpiry != null && storedExpiry <= currentTime) {
          blacklist.remove(tokenExpiry.token);
          cleanedCount++;
        }
      } else {
        // 队列头部未过期，停止清理
        break;
      }
    }

    if (cleanedCount > 0) {
      totalCleaned.addAndGet(cleanedCount);
      logger.info("内存黑名单清理完成，移除 {} 个过期Token", cleanedCount);
    }
  }

  /**
   * 强制清理所有过期Token（手动调用）
   */
  public void forceCleanup() {
    long currentTime = System.currentTimeMillis();
    int initialSize = blacklist.size();

    blacklist.entrySet().removeIf(entry -> entry.getValue() <= currentTime);

    int cleanedCount = initialSize - blacklist.size();
    if (cleanedCount > 0) {
      totalCleaned.addAndGet(cleanedCount);
      logger.info("强制清理完成，移除 {} 个过期Token", cleanedCount);
    }
  }

  /**
   * 获取统计信息
   */
  public Map<String, Object> getStatistics() {
    Map<String, Object> stats = new java.util.HashMap<>();
    stats.put("currentBlacklistSize", blacklist.size());
    stats.put("totalBlacklisted", totalBlacklisted.get());
    stats.put("totalCleaned", totalCleaned.get());
    stats.put("expiryQueueSize", expiryQueue.size());
    return stats;
  }

  private String maskToken(String token) {
    if (token == null || token.length() <= 10) {
      return "***";
    }
    return token.substring(0, 10) + "***";
  }
}
