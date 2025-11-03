package com.cn.taihe.back.imagefile.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件信息封装类
 */
@Data
public class FileInfo {

  /**
   * 文件相对路径
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
   * 文件类型
   */
  private String contentType;

  /**
   * 业务类型
   */
  private String businessType;

  /**
   * 文件分类（image/document等）
   */
  private String fileCategory;

  /**
   * 上传时间
   */
  private LocalDateTime uploadTime;

  /**
   * 文件访问URL
   */
  private String accessUrl;

  public FileInfo(String relativePath, String originalFilename, Long fileSize,
                  String contentType, String businessType) {
    this.relativePath = relativePath;
    this.originalFilename = originalFilename;
    this.fileSize = fileSize;
    this.contentType = contentType;
    this.businessType = businessType;
    this.uploadTime = LocalDateTime.now();
  }
}
