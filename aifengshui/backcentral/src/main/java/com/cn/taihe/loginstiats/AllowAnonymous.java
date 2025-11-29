package com.cn.taihe.loginstiats;

import java.lang.annotation.*;

/**
 * 允许匿名访问，即不需要登录
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllowAnonymous {
}
