package com.cn.taihe.config;

import com.cn.taihe.imageutils.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ConfigValidator implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(ConfigValidator.class);

  @Autowired
  private UploadConfig uploadConfig;

  @Override
  public void run(String... args) throws Exception {
    logger.info("=== 开始验证上传配置 ===");

    // 验证配置注入
    if (uploadConfig == null) {
      logger.error("❌ UploadConfig 配置注入失败");
      return;
    }

    logger.info("✅ UploadConfig 配置注入成功");
    logger.info("📁 基础目录: {}", uploadConfig.getBaseDir());
    logger.info("🌐 访问URL: {}", uploadConfig.getAccessBaseUrl());
    logger.info("📏 最大文件: {}", uploadConfig.getMaxSize());
    logger.info("📄 允许类型: {}", java.util.Arrays.toString(uploadConfig.getAllowedTypes()));

    // 验证并创建目录
    validateAndCreateDirectories();

    logger.info("✅ 上传配置验证完成");
    logger.info("=== 上传配置验证结束 ===");
  }

  private void validateAndCreateDirectories() {
    try {
      String baseDir = uploadConfig.getBaseDir();

      // 验证基础目录
      Path basePath = Paths.get(baseDir);
      if (!Files.exists(basePath)) {
        Files.createDirectories(basePath);
        logger.info("📁 创建基础目录: {}", baseDir);
      }

      // 创建子目录
      String[] subDirs = {"product","productsku" ,"category", "homepage", "user", "default"};
      for (String subDir : subDirs) {
        Path subPath = Paths.get(baseDir, subDir);
        if (!Files.exists(subPath)) {
          Files.createDirectories(subPath);
          logger.info("📁 创建子目录: {}/{}", baseDir, subDir);
        }
      }

      // 验证目录权限
      File baseDirFile = new File(baseDir);
      if (!baseDirFile.canWrite()) {
        logger.error("❌ 目录无写权限: {}", baseDir);
      } else {
        logger.info("✅ 目录权限验证通过");
      }

    } catch (Exception e) {
      logger.error("❌ 目录创建失败: {}", e.getMessage());
    }
  }
}
