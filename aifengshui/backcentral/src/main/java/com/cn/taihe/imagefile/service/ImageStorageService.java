package com.cn.taihe.imagefile.service;

import com.cn.taihe.imageutils.FileUtil;
import com.cn.taihe.imageutils.ImageUtil;
import com.cn.taihe.imageutils.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageStorageService {

  private static final Logger logger = LoggerFactory.getLogger(ImageStorageService.class);

  @Autowired
  private UploadConfig uploadConfig;

  /**
   * 上传图片
   */
  public Map<String, Object> uploadImage(MultipartFile file, String category) {
    Map<String, Object> result = new HashMap<>();

    try {
      // 1. 验证文件
      if (file == null || file.isEmpty()) {
        result.put("success", false);
        result.put("message", "文件为空");
        return result;
      }

      if (!FileUtil.isValidImageType(file)) {
        result.put("success", false);
        result.put("message", "不支持的文件类型");
        return result;
      }

      // 2. 验证文件大小
      double fileSizeMB = FileUtil.getFileSizeMB(file);
      if (fileSizeMB > 10) { // 10MB限制
        result.put("success", false);
        result.put("message", String.format("文件大小%.2fMB超过限制", fileSizeMB));
        return result;
      }

      // 3. 创建目录
      String categoryDir = uploadConfig.getBaseDir() + File.separator + category;
      FileUtil.createDirIfNotExists(categoryDir);

      // 4. 生成文件名并保存
      String fileName = FileUtil.generateFileName(file.getOriginalFilename());
      String filePath = categoryDir + File.separator + fileName;
      File dest = new File(filePath);
      file.transferTo(dest);

      logger.info("图片上传成功: {} -> {}", file.getOriginalFilename(), filePath);

      // 5. 获取图片信息（静默处理错误）
      ImageUtil.ImageInfo imageInfo = ImageUtil.getImageInfo(dest);

      // 6. 返回结果
      String accessUrl = uploadConfig.getAccessBaseUrl() + "/" + category + "/" + fileName;

      result.put("success", true);
      result.put("fileName", fileName);
      result.put("filePath", filePath);
      result.put("accessUrl", accessUrl);
      result.put("fileSize", fileSizeMB);
      result.put("originalFileName", file.getOriginalFilename());
      result.put("contentType", file.getContentType());
      result.put("imageInfo", imageInfo);
      result.put("category", category);

    } catch (Exception e) {
      logger.error("图片上传失败: {}", e.getMessage());
      result.put("success", false);
      result.put("message", "上传失败: " + e.getMessage());
    }

    return result;
  }

  /**
   * 删除图片
   */
  public boolean deleteImage(String filePath) {
    try {
      File file = new File(filePath);
      if (file.exists()) {
        boolean deleted = file.delete();
        if (deleted) {
          logger.info("图片删除成功: {}", filePath);
        } else {
          logger.warn("图片删除失败: {}", filePath);
        }
        return deleted;
      } else {
        logger.warn("图片文件不存在: {}", filePath);
        return false;
      }
    } catch (Exception e) {
      logger.error("删除图片异常: {}", e.getMessage());
      return false;
    }
  }

  /**
   * 获取图片访问URL
   */
  public String getImageUrl(String category, String fileName) {
    if (category == null || category.trim().isEmpty()) {
      category = "default";
    }
    return uploadConfig.getAccessBaseUrl() + "/" + category + "/" + fileName;
  }

  /**
   * 检查文件是否存在
   */
  public boolean imageExists(String filePath) {
    if (filePath == null || filePath.trim().isEmpty()) {
      return false;
    }
    File file = new File(filePath);
    return file.exists() && file.isFile();
  }
}
