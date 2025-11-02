package com.cn.taihe.imageutils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {

  /**
   * 生成唯一文件名
   */
  public static String generateFileName(String originalFileName) {
    String extension = getFileExtension(originalFileName);
    return UUID.randomUUID().toString() + extension;
  }

  /**
   * 获取文件扩展名
   */
  public static String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      return ".jpg"; // 默认扩展名
    }
    return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
  }

  /**
   * 创建目录（如果不存在）
   */
  public static void createDirIfNotExists(String dirPath) throws IOException {
    Path path = Paths.get(dirPath);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
  }

  /**
   * 验证文件类型
   */
  public static boolean isValidImageType(MultipartFile file) {
    String contentType = file.getContentType();
    if (contentType == null) {
      return false;
    }

    String[] allowedTypes = {"image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp"};
    for (String type : allowedTypes) {
      if (type.equalsIgnoreCase(contentType)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取文件大小（MB）
   */
  public static double getFileSizeMB(MultipartFile file) {
    return file.getSize() / (1024.0 * 1024.0);
  }

  /**
   * 删除文件
   */
  public static boolean deleteFile(String filePath) {
    File file = new File(filePath);
    return file.exists() && file.delete();
  }
}
