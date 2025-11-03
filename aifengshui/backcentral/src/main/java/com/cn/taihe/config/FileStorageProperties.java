package com.cn.taihe.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 文件存储配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.file-storage")
public class FileStorageProperties {

  /**
   * 文件存储基础目录
   */
  private String baseDir = "uploads";

  /**
   * 备用目录（当主目录创建失败时使用）
   */
  private String fallbackDir = "data/uploads";

  /**
   * 允许的文件类型 MIME 类型列表（支持图片、文档、视频、音频等）
   */
  private List<String> allowedTypes = Arrays.asList(
    // 图片类型
    "image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp", "image/svg+xml",

    // 文档类型
    "application/pdf",
    "application/msword",
    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
    "application/vnd.ms-excel",
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    "application/vnd.ms-powerpoint",
    "application/vnd.openxmlformats-officedocument.presentationml.presentation",
    "text/plain", "text/csv", "application/rtf",

    // 视频类型
    "video/mp4", "video/mpeg", "video/quicktime", "video/x-msvideo", "video/x-ms-wmv",
    "video/x-flv", "video/webm", "video/3gpp", "video/3gpp2",

    // 音频类型
    "audio/mpeg", "audio/wav", "audio/x-wav", "audio/aac", "audio/ogg", "audio/webm",

    // 压缩文件
    "application/zip", "application/x-rar-compressed", "application/x-tar",
    "application/x-7z-compressed", "application/gzip",

    // 其他常见类型
    "application/json", "application/xml", "text/html", "text/css", "application/javascript"
  );

  /**
   * 单个文件最大大小
   */
  private String maxSize = "50MB"; // 增大到50MB以支持视频文件

  /**
   * 是否启用文件存储
   */
  private boolean enabled = true;

  /**
   * 允许的文件扩展名列表（根据 MIME 类型自动推导）
   */
  public List<String> getAllowedExtensions() {
    return Arrays.asList(
      "jpg", "jpeg", "png", "gif", "webp", "bmp", "svg",
      "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "csv", "rtf",
      "mp4", "mpeg", "mov", "avi", "wmv", "flv", "webm", "3gp", "3g2",
      "mp3", "wav", "aac", "ogg", "weba",
      "zip", "rar", "tar", "7z", "gz",
      "json", "xml", "html", "css", "js"
    );
  }
}
