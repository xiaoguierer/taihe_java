package com.cn.taihe.common.fileutils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件上传响应结果
 */
@Data
public class FileUploadResult {

  /**
   * 文件相对路径（存储在数据库中的值）
   */
  private String relativePath;

  /**
   * 原始文件名
   */
  private String originalFilename;

  /**
   * 文件大小（字节）
   */
  private Long fileSize;

  /**
   * 文件类型（MIME类型）
   */
  private String contentType;

  /**
   * 业务类型
   */
  private String businessType;

  /**
   * 文件访问URL
   */
  private String accessUrl;

  /**
   * 文件分类（image/document/video/audio等）
   */
  private String fileCategory;

  /**
   * 上传时间
   */
  private LocalDateTime uploadTime;

  /**
   * 文件扩展名
   */
  private String fileExtension;

  /**
   * 文件MD5哈希值（可选，用于文件去重）
   */
  private String fileHash;

  /**
   * 文件存储路径（绝对路径，仅用于内部处理）
   */
  private String storagePath;

  public FileUploadResult() {
    this.uploadTime = LocalDateTime.now();
  }

  public FileUploadResult(String relativePath, String originalFilename, Long fileSize,
                          String contentType, String businessType, String accessUrl, String fileCategory) {
    this();
    this.relativePath = relativePath;
    this.originalFilename = originalFilename;
    this.fileSize = fileSize;
    this.contentType = contentType;
    this.businessType = businessType;
    this.accessUrl = accessUrl;
    this.fileCategory = fileCategory;

    // 自动提取文件扩展名
    if (originalFilename != null && originalFilename.contains(".")) {
      this.fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
    }
  }

  /**
   * 构建器模式，便于链式调用
   */
  public static FileUploadResultBuilder builder() {
    return new FileUploadResultBuilder();
  }

  /**
   * 构建器类
   */
  public static class FileUploadResultBuilder {
    private String relativePath;
    private String originalFilename;
    private Long fileSize;
    private String contentType;
    private String businessType;
    private String accessUrl;
    private String fileCategory;
    private String fileHash;

    public FileUploadResultBuilder relativePath(String relativePath) {
      this.relativePath = relativePath;
      return this;
    }

    public FileUploadResultBuilder originalFilename(String originalFilename) {
      this.originalFilename = originalFilename;
      return this;
    }

    public FileUploadResultBuilder fileSize(Long fileSize) {
      this.fileSize = fileSize;
      return this;
    }

    public FileUploadResultBuilder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    public FileUploadResultBuilder businessType(String businessType) {
      this.businessType = businessType;
      return this;
    }

    public FileUploadResultBuilder accessUrl(String accessUrl) {
      this.accessUrl = accessUrl;
      return this;
    }

    public FileUploadResultBuilder fileCategory(String fileCategory) {
      this.fileCategory = fileCategory;
      return this;
    }

    public FileUploadResultBuilder fileHash(String fileHash) {
      this.fileHash = fileHash;
      return this;
    }

    public FileUploadResult build() {
      FileUploadResult result = new FileUploadResult(relativePath, originalFilename, fileSize,
        contentType, businessType, accessUrl, fileCategory);
      result.setFileHash(fileHash);
      return result;
    }
  }

  /**
   * 获取文件大小的人类可读格式
   */
  public String getFileSizeHumanReadable() {
    if (fileSize == null) return "0 B";

    if (fileSize < 1024) {
      return fileSize + " B";
    } else if (fileSize < 1024 * 1024) {
      return String.format("%.1f KB", fileSize / 1024.0);
    } else if (fileSize < 1024 * 1024 * 1024) {
      return String.format("%.1f MB", fileSize / (1024.0 * 1024.0));
    } else {
      return String.format("%.1f GB", fileSize / (1024.0 * 1024.0 * 1024.0));
    }
  }

  /**
   * 判断是否为图片文件
   */
  public boolean isImage() {
    return fileCategory != null && "image".equals(fileCategory);
  }

  /**
   * 判断是否为视频文件
   */
  public boolean isVideo() {
    return fileCategory != null && "video".equals(fileCategory);
  }

  /**
   * 判断是否为文档文件
   */
  public boolean isDocument() {
    return fileCategory != null && "document".equals(fileCategory);
  }

  /**
   * 判断是否为音频文件
   */
  public boolean isAudio() {
    return fileCategory != null && "audio".equals(fileCategory);
  }

  /**
   * 获取文件图标类型（用于前端显示）
   */
  public String getFileIcon() {
    if (isImage()) return "image";
    if (isVideo()) return "video";
    if (isAudio()) return "audio";
    if (isDocument()) {
      if (fileExtension != null) {
        switch (fileExtension.toLowerCase()) {
          case "pdf": return "pdf";
          case "doc": case "docx": return "word";
          case "xls": case "xlsx": return "excel";
          case "ppt": case "pptx": return "powerpoint";
          case "txt": return "text";
          case "zip": case "rar": case "7z": return "archive";
          default: return "document";
        }
      }
      return "document";
    }
    return "file";
  }
}
