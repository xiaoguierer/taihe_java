package com.cn.taihe.back.user.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {
  @Id  // 必须添加这个注解
  @Column(nullable = false, unique = true, length = 255)
  private String id;

  @Column(nullable = false, unique = true, length = 128)
  private String email;

  @Column(name = "password_hash", nullable = false, length = 255)
  private String passwordHash;

  @Column(name = "salt", nullable = false, length = 64)
  private String salt;

  @Column(length = 64)
  private String nickname = "新用户";

  @Column(length = 255)
  private String avatar;

  @Column(nullable = false)
  private Integer status = 1;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  //new add image infor
  // === 新增字段（用于文件存储增强）===

  /**
   * 头像相对路径（新增，存储在文件系统中的路径）
   * 例如：/avatar/2024/01/15/abc123.jpg
   */
  @Column(name = "avatar_path", length = 500)
  private String avatarPath;

  /**
   * 原始头像文件名（新增）
   */
  @Column(name = "avatar_original_name", length = 255)
  private String avatarOriginalName;

  /**
   * 头像文件大小（新增，字节）
   */
  @Column(name = "avatar_size")
  private Long avatarSize;

  /**
   * 头像文件类型（新增，MIME类型）
   */
  @Column(name = "avatar_content_type", length = 100)
  private String avatarContentType;

  /**
   * 获取头像访问URL（计算属性，不存储）
   * 优先使用avatarPath生成URL，如果不存在则使用avatar字段
   */
  @Transient
  public String getAvatarUrl() {
    if (avatarPath != null && !avatarPath.trim().isEmpty()) {
      // 使用文件存储服务生成的URL
      return "/api/files" + avatarPath;
    } else if (avatar != null && !avatar.trim().isEmpty()) {
      // 使用现有的avatar字段（可能是完整URL）
      return avatar;
    }
    return null;
  }

  /**
   * 判断是否有头像
   */
  @Transient
  public boolean hasAvatar() {
    return (avatarPath != null && !avatarPath.trim().isEmpty()) ||
      (avatar != null && !avatar.trim().isEmpty());
  }

  /**
   * 获取文件大小的人类可读格式
   */
  @Transient
  public String getAvatarSizeHumanReadable() {
    if (avatarSize == null) return "0 B";

    if (avatarSize < 1024) {
      return avatarSize + " B";
    } else if (avatarSize < 1024 * 1024) {
      return String.format("%.1f KB", avatarSize / 1024.0);
    } else if (avatarSize < 1024 * 1024 * 1024) {
      return String.format("%.1f MB", avatarSize / (1024.0 * 1024.0));
    } else {
      return String.format("%.1f GB", avatarSize / (1024.0 * 1024.0 * 1024.0));
    }
  }

  /**
   * 判断是否为图片文件
   */
  @Transient
  public boolean isAvatarImage() {
    return avatarContentType != null && avatarContentType.startsWith("image/");
  }

  /**
   * 获取文件图标类型（用于前端显示）
   */
  @Transient
  public String getAvatarIcon() {
    if (avatarContentType == null) return "file";

    if (avatarContentType.startsWith("image/")) return "image";
    if (avatarContentType.startsWith("video/")) return "video";
    if (avatarContentType.startsWith("audio/")) return "audio";

    if (avatarOriginalName != null) {
      String filename = avatarOriginalName.toLowerCase();
      if (filename.endsWith(".pdf")) return "pdf";
      if (filename.endsWith(".doc") || filename.endsWith(".docx")) return "word";
      if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) return "excel";
      if (filename.endsWith(".ppt") || filename.endsWith(".pptx")) return "powerpoint";
      if (filename.endsWith(".txt")) return "text";
      if (filename.endsWith(".zip") || filename.endsWith(".rar")) return "archive";
    }

    return "file";
  }

  /**
   * 兼容性方法：设置头像时同时更新新旧字段
   */
  public void setAvatarUrl(String avatarUrl) {
    this.avatar = avatarUrl;
    // 如果是相对路径，可以同时设置avatarPath
    if (avatarUrl != null && avatarUrl.startsWith("/api/files/")) {
      this.avatarPath = avatarUrl.substring("/api/files".length());
    }
  }

  // 新增结束

  // 构造方法
  public User() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getAvatarPath() {
    return avatarPath;
  }

  public void setAvatarPath(String avatarPath) {
    this.avatarPath = avatarPath;
  }

  public String getAvatarOriginalName() {
    return avatarOriginalName;
  }

  public void setAvatarOriginalName(String avatarOriginalName) {
    this.avatarOriginalName = avatarOriginalName;
  }

  public Long getAvatarSize() {
    return avatarSize;
  }

  public void setAvatarSize(Long avatarSize) {
    this.avatarSize = avatarSize;
  }

  public String getAvatarContentType() {
    return avatarContentType;
  }

  public void setAvatarContentType(String avatarContentType) {
    this.avatarContentType = avatarContentType;
  }
}
