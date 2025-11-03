package com.cn.taihe.common.fileutils;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.common.fileutils
 * @Author: 大咖
 * @CreateTime: 2025-11-03  13:44
 * @Description: TODO
 * @Version: 1.0
 */

import lombok.Data;

/**
 * 统一API响应结果
 */
@Data
public class ApiResult<T> {

  /**
   * 状态码：200成功，其他失败
   */
  private int code;

  /**
   * 响应消息
   */
  private String message;

  /**
   * 响应数据
   */
  private T data;

  /**
   * 时间戳
   */
  private long timestamp;

  public ApiResult() {
    this.timestamp = System.currentTimeMillis();
  }

  public static <T> ApiResult<T> success(T data) {
    ApiResult<T> result = new ApiResult<>();
    result.code = 200;
    result.message = "success";
    result.data = data;
    return result;
  }

  public static <T> ApiResult<T> success(String message, T data) {
    ApiResult<T> result = new ApiResult<>();
    result.code = 200;
    result.message = message;
    result.data = data;
    return result;
  }

  public static <T> ApiResult<T> error(int code, String message) {
    ApiResult<T> result = new ApiResult<>();
    result.code = code;
    result.message = message;
    return result;
  }

  public static <T> ApiResult<T> error(String message) {
    return error(500, message);
  }
}
