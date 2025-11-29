package com.cn.taihe.loginstiats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // 可以用在方法和类上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface RequireLogin {
  // 什么都不用写，就是个标记
}
