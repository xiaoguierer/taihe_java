package com.cn.taihe.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件类型验证工具类（增强版）
 */
@Slf4j
@Component
public class FileTypeValidator {

  @Autowired
  private FileStorageProperties fileStorageProperties;

  // 文件分类映射
  private static final Set<String> IMAGE_TYPES = new HashSet<>(Arrays.asList(
    "image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp", "image/svg+xml"
  ));

  private static final Set<String> VIDEO_TYPES = new HashSet<>(Arrays.asList(
    "video/mp4", "video/mpeg", "video/quicktime", "video/x-msvideo", "video/x-ms-wmv",
    "video/x-flv", "video/webm", "video/3gpp", "video/3gpp2"
  ));

  private static final Set<String> AUDIO_TYPES = new HashSet<>(Arrays.asList(
    "audio/mpeg", "audio/wav", "audio/x-wav", "audio/aac", "audio/ogg", "audio/webm"
  ));

  private static final Set<String> DOCUMENT_TYPES = new HashSet<>(Arrays.asList(
    "application/pdf", "application/msword",
    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
    "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation",
    "text/plain", "text/csv", "application/rtf"
  ));

  private static final Set<String> ARCHIVE_TYPES = new HashSet<>(Arrays.asList(
    "application/zip", "application/x-rar-compressed", "application/x-tar",
    "application/x-7z-compressed", "application/gzip"
  ));

  /**
   * 验证文件类型是否允许
   */
  public boolean isValidFileType(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return false;
    }

    String contentType = file.getContentType();
    String originalFilename = file.getOriginalFilename();

    log.debug("验证文件类型: contentType={}, filename={}", contentType, originalFilename);

    // 检查 MIME 类型
    if (contentType != null && fileStorageProperties.getAllowedTypes().contains(contentType)) {
      return true;
    }

    // 如果 MIME 类型检查失败，尝试通过文件扩展名检查
    if (originalFilename != null) {
      String extension = getFileExtension(originalFilename).toLowerCase();
      boolean allowed = fileStorageProperties.getAllowedExtensions().contains(extension);
      if (allowed) {
        log.warn("文件通过扩展名验证，但MIME类型不匹配: {} -> {}", contentType, extension);
      }
      return allowed;
    }

    log.warn("文件类型验证失败: contentType={}, filename={}", contentType, originalFilename);
    return false;
  }

  /**
   * 验证文件大小是否允许
   */
  public boolean isValidFileSize(MultipartFile file) {
    if (file == null) {
      return false;
    }

    long maxSizeBytes = parseSizeToBytes(fileStorageProperties.getMaxSize());
    long fileSize = file.getSize();
    boolean valid = fileSize <= maxSizeBytes;

    if (!valid) {
      log.warn("文件大小超过限制: {} > {}", fileSize, maxSizeBytes);
    }

    return valid;
  }

  /**
   * 获取文件分类
   */
  public String getFileCategory(String contentType) {
    if (contentType == null) {
      return "other";
    }

    if (IMAGE_TYPES.contains(contentType)) {
      return "image";
    } else if (VIDEO_TYPES.contains(contentType)) {
      return "video";
    } else if (AUDIO_TYPES.contains(contentType)) {
      return "audio";
    } else if (DOCUMENT_TYPES.contains(contentType)) {
      return "document";
    } else if (ARCHIVE_TYPES.contains(contentType)) {
      return "archive";
    } else if (contentType.startsWith("text/")) {
      return "text";
    } else {
      return "other";
    }
  }

  /**
   * 获取文件扩展名
   */
  private String getFileExtension(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "";
    }

    int lastDotIndex = filename.lastIndexOf('.');
    if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
      return filename.substring(lastDotIndex + 1);
    }
    return "";
  }

  /**
   * 将大小字符串转换为字节数
   */
  private long parseSizeToBytes(String size) {
    if (size == null || size.isEmpty()) {
      return 50 * 1024 * 1024L; // 默认 50MB
    }

    size = size.toUpperCase().trim();
    try {
      if (size.endsWith("GB")) {
        return Long.parseLong(size.replace("GB", "").trim()) * 1024 * 1024 * 1024;
      } else if (size.endsWith("MB")) {
        return Long.parseLong(size.replace("MB", "").trim()) * 1024 * 1024;
      } else if (size.endsWith("KB")) {
        return Long.parseLong(size.replace("KB", "").trim()) * 1024;
      } else if (size.endsWith("B")) {
        return Long.parseLong(size.replace("B", "").trim());
      } else {
        return Long.parseLong(size.trim());
      }
    } catch (NumberFormatException e) {
      log.warn("无法解析文件大小配置: {}, 使用默认值 50MB", size);
      return 50 * 1024 * 1024L;
    }
  }

  /**
   * 获取所有允许的文件扩展名（用于前端显示）
   */
  public String getAllowedExtensionsForDisplay() {
    return String.join(", ", fileStorageProperties.getAllowedExtensions());
  }

  /**
   * 获取最大文件大小（用于前端显示）
   */
  public String getMaxSizeForDisplay() {
    return fileStorageProperties.getMaxSize();
  }
}
