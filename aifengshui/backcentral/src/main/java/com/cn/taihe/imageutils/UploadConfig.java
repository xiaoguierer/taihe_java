package com.cn.taihe.imageutils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
@ConfigurationProperties(prefix = "app.upload")  // ✅ 修正前缀
public class UploadConfig {
  private String baseDir = "./src/main/resources/static/upload";
  private String accessBaseUrl = "http://localhost:8080/images";
  private String maxSize = "10MB";
  private String[] allowedTypes = {"jpg", "jpeg", "png", "gif", "webp"};

  // ✅ 必须添加getter和setter方法
  public String getBaseDir() {
    return baseDir;
  }

  public void setBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }

  public String getAccessBaseUrl() {
    return accessBaseUrl;
  }

  public void setAccessBaseUrl(String accessBaseUrl) {
    this.accessBaseUrl = accessBaseUrl;
  }

  public String getMaxSize() {
    return maxSize;
  }

  public void setMaxSize(String maxSize) {
    this.maxSize = maxSize;
  }

  public String[] getAllowedTypes() {
    return allowedTypes;
  }

  public void setAllowedTypes(String[] allowedTypes) {
    this.allowedTypes = allowedTypes;
  }

  @Override
  public String toString() {
    return "UploadConfig{" +
      "baseDir='" + baseDir + '\'' +
      ", accessBaseUrl='" + accessBaseUrl + '\'' +
      ", maxSize='" + maxSize + '\'' +
      ", allowedTypes=" + Arrays.toString(allowedTypes) +
      '}';
  }
}
