package com.cn.taihe.loginstiats;

import com.cn.taihe.loginstiats.AllowAnonymous;
import com.cn.taihe.loginstiats.RequireLogin;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截器
 * 拦截逻辑：
 * 1. 优先检查@AllowAnonymous注解，存在则直接放行
 * 2. 其次检查@RequireLogin注解，存在则验证登录
 * 3. 默认情况下直接放行（不强制要求登录）
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

  private static final String BEARER_PREFIX = "Bearer ";
  private static final int UNAUTHORIZED_CODE = 401;

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler) throws Exception {

    // 如果不是HandlerMethod类型（如资源处理器），直接放行
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;

    // 1. 检查是否有@AllowAnonymous注解（最高优先级）
    if (hasAllowAnonymousAnnotation(handlerMethod)) {
      return true;
    }

    // 2. 检查是否有@RequireLogin注解
    if (hasRequireLoginAnnotation(handlerMethod)) {
      return validateTokenAndLogin(request, response);
    }

    // 3. 默认行为：直接放行
    return true;
  }

  /**
   * 检查是否包含AllowAnonymous注解
   * 优先级：方法注解 > 类注解
   */
  private boolean hasAllowAnonymousAnnotation(HandlerMethod handlerMethod) {
    // 检查方法上的注解
    if (handlerMethod.hasMethodAnnotation(AllowAnonymous.class)) {
      return true;
    }

    // 检查类上的注解
    return handlerMethod.getBeanType().isAnnotationPresent(AllowAnonymous.class);
  }

  /**
   * 检查是否包含RequireLogin注解
   * 优先级：方法注解 > 类注解
   */
  private boolean hasRequireLoginAnnotation(HandlerMethod handlerMethod) {
    // 检查方法上的注解
    if (handlerMethod.hasMethodAnnotation(RequireLogin.class)) {
      return true;
    }

    // 检查类上的注解
    return handlerMethod.getBeanType().isAnnotationPresent(RequireLogin.class);
  }

  /**
   * 验证Token和登录状态
   */
  private boolean validateTokenAndLogin(HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
    String token = request.getHeader("Authorization");

    // 检查Token是否存在且格式正确
    if (token == null || token.trim().isEmpty()) {
      sendUnauthorizedResponse(response, "缺少Authorization头信息");
      return false;
    }

    if (!token.startsWith(BEARER_PREFIX)) {
      sendUnauthorizedResponse(response, "Token格式错误，必须以'Bearer '开头");
      return false;
    }

    // 提取真实Token（去掉Bearer前缀）
    String realToken = token.substring(BEARER_PREFIX.length()).trim();
    if (realToken.isEmpty()) {
      sendUnauthorizedResponse(response, "Token不能为空");
      return false;
    }

    // 验证Token有效性（这里可以扩展具体的验证逻辑）
    if (!isValidToken(realToken)) {
      sendUnauthorizedResponse(response, "Token无效或已过期");
      return false;
    }

    // 可以将用户信息存入请求上下文，供后续使用
    setUserInfoToRequest(request, realToken);

    return true;
  }

  /**
   * 验证Token有效性（需要根据实际业务实现）
   */
  private boolean isValidToken(String token) {
    // TODO: 实现具体的Token验证逻辑
    // 例如：调用认证服务验证Token、检查Redis中的Token状态等
    // 这里先返回true作为示例，实际使用时需要实现具体逻辑

    // 示例：简单的Token长度检查（实际应该更复杂的验证）
    return token.length() >= 10;

    // 实际实现示例：
    // try {
    //     return tokenService.validateToken(token);
    // } catch (Exception e) {
    //     log.error("Token验证异常", e);
    //     return false;
    // }
  }

  /**
   * 将用户信息设置到请求中，供后续使用
   */
  private void setUserInfoToRequest(HttpServletRequest request, String token) {
    // TODO: 根据Token解析用户信息并存入请求属性中
    // 例如：从Token中解析用户ID、用户名等信息

    // 示例：解析Token获取用户信息
    // UserInfo userInfo = tokenService.parseToken(token);
    // request.setAttribute("currentUser", userInfo);
    // request.setAttribute("userId", userInfo.getUserId());

    // 临时示例：
    request.setAttribute("token", token);
  }

  /**
   * 发送未授权响应
   */
  private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
    response.setStatus(UNAUTHORIZED_CODE);
    response.setContentType("application/json;charset=UTF-8");

    String jsonResponse = String.format(
      "{\"code\":%d,\"message\":\"%s\",\"data\":null}",
      UNAUTHORIZED_CODE,
      message
    );

    response.getWriter().write(jsonResponse);
    response.getWriter().flush();
  }
}
