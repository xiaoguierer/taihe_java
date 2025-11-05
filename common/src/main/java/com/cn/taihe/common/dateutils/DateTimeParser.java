package com.cn.taihe.common.dateutils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.common.dateutils
 * @Author: 大咖
 * @CreateTime: 2025-11-05  17:34
 * @Description: TODO
 * @Version: 1.0
 */
@Component
public class DateTimeParser {

  private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
    DateTimeFormatter.ISO_LOCAL_DATE_TIME,
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"),
    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
    DateTimeFormatter.ofPattern("yyyy-MM-dd")
  );

  public LocalDateTime parse(Object dateObj) {
    if (dateObj == null) return null;

    String dateStr = dateObj.toString().trim();

    // 先尝试直接解析（处理LocalDateTime对象）
    if (dateObj instanceof LocalDateTime) {
      return (LocalDateTime) dateObj;
    }

    // 尝试各种格式
    for (DateTimeFormatter formatter : FORMATTERS) {
      try {
        return LocalDateTime.parse(dateStr, formatter);
      } catch (DateTimeParseException e) {
        // 继续尝试下一个格式
      }
    }

    // 特殊处理：空格替换为T
    if (dateStr.contains(" ")) {
      try {
        String isoFormat = dateStr.replace(" ", "T");
        return LocalDateTime.parse(isoFormat);
      } catch (DateTimeParseException e) {
        // 继续处理
      }
    }

    throw new IllegalArgumentException("不支持的日期格式: " + dateStr);
  }

  // 格式化为数据库格式
  public String formatToDatabase(LocalDateTime dateTime) {
    if (dateTime == null) return null;
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  // 日期解析工具方法
  public LocalDateTime parseBirthdayTime(Object birthdayTime) {
    if (birthdayTime == null) {
      return null;
    }
    String dateStr = birthdayTime.toString().trim();
    DateTimeFormatter[] formatters = {
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),  // 数据库格式
      DateTimeFormatter.ISO_LOCAL_DATE_TIME,              // ISO格式（带T）
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"), // 带毫秒
      DateTimeFormatter.ofPattern("yyyy-MM-dd")           // 只有日期
    };
    for (DateTimeFormatter formatter : formatters) {
      try {
        return LocalDateTime.parse(dateStr, formatter);
      } catch (DateTimeParseException e) {
        // 尝试下一个格式
        continue;
      }
    }
    // 如果所有格式都失败，尝试更灵活的方式
    try {
      // 替换空格为T，转换为ISO格式
      if (dateStr.contains(" ")) {
        String isoFormat = dateStr.replace(" ", "T");
        return LocalDateTime.parse(isoFormat);
      }
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("无法解析的日期格式: " + dateStr);
    }

    throw new IllegalArgumentException("无法解析的日期格式: " + dateStr);
  }
}
