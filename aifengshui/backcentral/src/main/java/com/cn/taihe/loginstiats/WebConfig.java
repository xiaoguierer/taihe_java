package com.cn.taihe.loginstiats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 *拦截所有路径只是让每个请求都经过拦截器检查，实际是否需要登录完全由注解控制：
 * 有 @AllowAnonymous→ 直接放行（不需要登录）
 * 有 @RequireLogin→ 需要登录验证
 * 没有注解 → 直接放行（不需要登录）
 * 这样设计既保证了安全性，又提供了最大的灵活性！
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Autowired
  private LoginInterceptor loginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
      .addPathPatterns("/**")  // 拦截所有路径
      .excludePathPatterns(    // 排除静态资源
        "/error",
        "/favicon.ico",
        "/static/**",
        "/resources/**",
        "/public/**"
      )
      .order(1);  // 设置拦截器顺序
  }
}
