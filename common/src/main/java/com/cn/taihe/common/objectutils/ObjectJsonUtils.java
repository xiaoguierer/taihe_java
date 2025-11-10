package com.cn.taihe.common.objectutils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectJsonUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 对象转JSON字符串
   */
  public static String toJsonString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException("对象转JSON失败", e);
    }
  }

  /**
   * 对象转格式化的JSON字符串（带缩进）
   */
  public static String toPrettyJsonString(Object obj) {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException("对象转JSON失败", e);
    }
  }
}
