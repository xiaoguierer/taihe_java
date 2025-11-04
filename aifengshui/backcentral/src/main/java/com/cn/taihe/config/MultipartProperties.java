package com.cn.taihe.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.config
 * @Author: 大咖
 * @CreateTime: 2025-11-03  12:28
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.servlet.multipart")
public class MultipartProperties {
  /**
   * 启用文件上传支持
   */
  private boolean enabled = true;

  /**
   * 单个文件最大大小
   */
  private String maxFileSize = "10MB";

  /**
   * 整个请求最大大小（可包含多个文件）
   */
  private String maxRequestSize = "50MB";

  /**
   * 文件大小阈值，超过此值将写入磁盘
   */
  private String fileSizeThreshold = "0";

  /**
   * 上传文件临时存储位置
   */
  private String location;
}
