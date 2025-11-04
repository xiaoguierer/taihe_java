package com.cn.taihe.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件存储自动配置类
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({FileStorageProperties.class, MultipartProperties.class})
public class FileStorageAutoConfiguration {
  @Autowired
  private FileStorageProperties fileStorageProperties;

  private String actualStoragePath;

  /**
   * 智能初始化文件存储目录
   */
  @PostConstruct
  public void initStorageDirectory() {
    log.info("=== 开始初始化文件存储目录 ===");

    // 尝试多个目录方案
    String[] directoryOptions = {
      fileStorageProperties.getBaseDir(),        // 首选目录
      fileStorageProperties.getFallbackDir(),    // 备用目录
      System.getProperty("java.io.tmpdir") + File.separator + "uploads" // 临时目录
    };

    for (int i = 0; i < directoryOptions.length; i++) {
      String dirPath = directoryOptions[i];
      if (tryCreateDirectory(dirPath, i)) {
        actualStoragePath = dirPath;
        fileStorageProperties.setBaseDir(dirPath);
        createSubDirectories(Paths.get(dirPath));
        log.info("✅ 文件存储目录初始化成功: {}", dirPath);
        return;
      }
    }

    // 所有方案都失败，使用当前工作目录作为最后手段
    String currentDir = System.getProperty("user.dir") + File.separator + "uploads";
    if (tryCreateDirectory(currentDir, 3)) {
      actualStoragePath = currentDir;
      fileStorageProperties.setBaseDir(currentDir);
      log.warn("⚠️ 使用当前工作目录作为存储目录: {}", currentDir);
    } else {
      log.error("❌ 所有存储目录方案均失败，文件上传功能将不可用");
      throw new RuntimeException("无法创建任何可用的文件存储目录");
    }
  }

  /**
   * 尝试创建目录
   */
  private boolean tryCreateDirectory(String dirPath, int attempt) {
    try {
      Path path = Paths.get(dirPath);
      File dir = path.toFile();

      String attemptName = getAttemptName(attempt);
      log.info("尝试 {}: 创建目录 {}", attemptName, dirPath);

      if (dir.exists()) {
        if (dir.isDirectory() && dir.canWrite()) {
          log.info("✅ {}: 目录已存在且可写", attemptName);
          return true;
        } else {
          log.warn("⚠️ {}: 路径存在但不是目录或不可写", attemptName);
          return false;
        }
      }

      // 尝试创建目录
      if (dir.mkdirs()) {
        log.info("✅ {}: 目录创建成功", attemptName);

        // 验证目录权限
        if (dir.canWrite()) {
          log.info("✅ {}: 目录权限验证通过", attemptName);
          return true;
        } else {
          log.warn("⚠️ {}: 目录创建成功但无写权限", attemptName);
          // 尝试修复权限
          return attemptFixPermissions(dir, attemptName);
        }
      } else {
        log.warn("⚠️ {}: 目录创建失败", attemptName);
        return false;
      }

    } catch (SecurityException e) {
      log.warn("⚠️ {}: 权限不足，无法创建目录: {}", e.getMessage());
      return false;
    } catch (Exception e) {
      log.warn("⚠️ {}: 创建目录时发生异常: {}", e.getMessage());
      return false;
    }
  }

  /**
   * 尝试修复目录权限
   */
  private boolean attemptFixPermissions(File dir, String attemptName) {
    try {
      // 尝试设置可写权限（仅限 Unix 系统）
      if (System.getProperty("os.name").toLowerCase().contains("linux") ||
        System.getProperty("os.name").toLowerCase().contains("mac")) {

        Process process = Runtime.getRuntime().exec(new String[]{
          "chmod", "755", dir.getAbsolutePath()
        });
        int result = process.waitFor();
        if (result == 0 && dir.canWrite()) {
          log.info("✅ {}: 目录权限修复成功", attemptName);
          return true;
        }
      }
    } catch (Exception e) {
      log.warn("⚠️ {}: 权限修复失败: {}", attemptName, e.getMessage());
    }
    return false;
  }

  /**
   * 获取尝试名称
   */
  private String getAttemptName(int attempt) {
    switch (attempt) {
      case 0:
        return "首选方案";
      case 1:
        return "备用方案";
      case 2:
        return "临时目录方案";
      case 3:
        return "当前目录方案";
      default:
        return "方案" + attempt;
    }
  }

  /**
   * 创建子目录结构
   */
  private void createSubDirectories(Path basePath) {
    String[] subDirs = {"images", "documents", "temp", "avatars"};

    for (String subDir : subDirs) {
      File dir = basePath.resolve(subDir).toFile();
      if (!dir.exists()) {
        if (dir.mkdir()) {
          log.info("✅ 创建子目录: {}", dir.getAbsolutePath());
        } else {
          log.warn("⚠️ 子目录创建失败: {}", dir.getAbsolutePath());
        }
      }
    }
  }

  /**
   * 文件存储路径 Bean
   */
  @Bean
  public Path fileStoragePath() {
    return Paths.get(actualStoragePath != null ? actualStoragePath : fileStorageProperties.getBaseDir());
  }

  /**
   * 获取实际存储路径
   */
  public String getActualStoragePath() {
    return actualStoragePath;
  }

  /**
   * 验证配置并打印详细信息
   */
  @PostConstruct
  public void validateAndLogConfig() {
    log.info("=== 文件存储配置详情 ===");
    log.info("配置的存储目录: {}", fileStorageProperties.getBaseDir());
    log.info("实际使用的目录: {}", actualStoragePath);
    log.info("单文件最大大小: {}", fileStorageProperties.getMaxSize());
    log.info("允许的文件类型: {}", fileStorageProperties.getAllowedTypes());

    // 检查磁盘空间
    checkDiskSpace();
    log.info("=== 配置验证完成 ===");
  }

  /**
   * 检查磁盘空间
   */
  private void checkDiskSpace() {
    try {
      File storageDir = new File(actualStoragePath);
      long freeSpace = storageDir.getFreeSpace();
      long totalSpace = storageDir.getTotalSpace();

      if (freeSpace > 0) {
        double freeSpaceGB = freeSpace / (1024.0 * 1024.0 * 1024.0);
        double totalSpaceGB = totalSpace / (1024.0 * 1024.0 * 1024.0);
        log.info("磁盘空间: {:.2f}GB 可用 / {:.2f}GB 总量", freeSpaceGB, totalSpaceGB);

        if (freeSpace < 100 * 1024 * 1024) { // 小于 100MB
          log.warn("⚠️ 磁盘空间不足，剩余空间小于 100MB");
        }
      } else {
        log.info("磁盘空间: 无法获取磁盘空间信息");
      }
    } catch (Exception e) {
      log.warn("无法检查磁盘空间: {}", e.getMessage());
    }
  }
}
