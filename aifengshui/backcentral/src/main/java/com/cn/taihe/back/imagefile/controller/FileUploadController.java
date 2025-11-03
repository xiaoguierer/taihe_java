package com.cn.taihe.back.imagefile.controller;
import com.cn.taihe.common.fileutils.ApiResult;
import com.cn.taihe.common.fileutils.FileUploadResult;
import com.cn.taihe.config.FileTypeValidator;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileUploadController {

  @Autowired
  private FileStorageService fileStorageService;

  @Autowired
  private FileTypeValidator fileTypeValidator;

  /**
   * 单文件上传
   *
   * @param file 上传的文件
   * @param businessType 业务类型
   * @return 上传结果
   */
  @PostMapping("/upload")
  public ApiResult<FileUploadResult> uploadFile(
    @RequestParam("file") MultipartFile file,
    @RequestParam("businessType") String businessType) {
    log.info("收到文件上传请求: businessType={}, filename={}, size={} bytes",
      businessType, file.getOriginalFilename(), file.getSize());
    try {
      // 上传文件
      String relativePath = fileStorageService.upload(file, businessType);
      // 使用构建器模式创建响应结果
      FileUploadResult result = FileUploadResult.builder()
        .relativePath(relativePath)
        .originalFilename(file.getOriginalFilename())
        .fileSize(file.getSize())
        .contentType(file.getContentType())
        .businessType(businessType)
        .accessUrl(fileStorageService.getFileAccessUrl(relativePath))
        .fileCategory(fileTypeValidator.getFileCategory(file.getContentType()))
        .build();

      log.info("文件上传成功: {}", relativePath);
      return ApiResult.success("文件上传成功", result);

    } catch (Exception e) {
      log.error("文件上传失败: {}", e.getMessage());
      throw e;
    }
  }

  /**
   * 多文件上传
   *
   * @param files 文件数组
   * @param businessType 业务类型
   * @return 上传结果
   */
  @PostMapping("/upload/batch")
  public ApiResult<java.util.List<FileUploadResult>> uploadFiles(
    @RequestParam("files") MultipartFile[] files,
    @RequestParam("businessType") String businessType) {

    log.info("收到批量文件上传请求: businessType={}, fileCount={}", businessType, files.length);

    java.util.List<FileUploadResult> results = new java.util.ArrayList<>();

    for (MultipartFile file : files) {
      try {
        String relativePath = fileStorageService.upload(file, businessType);

        FileUploadResult result = new FileUploadResult(
          relativePath,
          file.getOriginalFilename(),
          file.getSize(),
          file.getContentType(),
          businessType,
          fileStorageService.getFileAccessUrl(relativePath),
          fileTypeValidator.getFileCategory(file.getContentType())
        );

        results.add(result);
        log.info("文件上传成功: {}", relativePath);

      } catch (Exception e) {
        log.error("文件上传失败: {} - {}", file.getOriginalFilename(), e.getMessage());
        // 单个文件失败不影响其他文件
      }
    }

    return ApiResult.success("批量文件上传完成", results);
  }

  /**
   * 删除文件
   *
   * @param relativePath 文件相对路径
   * @return 删除结果
   */
  @DeleteMapping("/delete")
  public ApiResult<Void> deleteFile(@RequestParam("relativePath") String relativePath) {

    log.info("收到文件删除请求: {}", relativePath);

    try {
      boolean deleted = fileStorageService.delete(relativePath);
      if (deleted) {
        log.info("文件删除成功: {}", relativePath);
        return ApiResult.success("文件删除成功", null);
      } else {
        log.warn("文件删除失败或文件不存在: {}", relativePath);
        return ApiResult.error(404, "文件不存在或删除失败");
      }

    } catch (Exception e) {
      log.error("文件删除失败: {}", e.getMessage());
      throw e;
    }
  }

  /**
   * 检查文件是否存在
   *
   * @param relativePath 文件相对路径
   * @return 检查结果
   */
  @GetMapping("/exists")
  public ApiResult<Boolean> checkFileExists(@RequestParam("relativePath") String relativePath) {

    boolean exists = fileStorageService.exists(relativePath);
    log.debug("检查文件存在性: {} -> {}", relativePath, exists);

    return ApiResult.success(exists);
  }

  /**
   * 获取文件信息
   *
   * @param relativePath 文件相对路径
   * @return 文件信息
   */
  @GetMapping("/info")
  public ApiResult<FileUploadResult> getFileInfo(@RequestParam("relativePath") String relativePath) {

    log.debug("获取文件信息: {}", relativePath);

    try {
      if (!fileStorageService.exists(relativePath)) {
        return ApiResult.error(404, "文件不存在");
      }

      long fileSize = fileStorageService.getFileSize(relativePath);

      // 从路径中解析业务类型和文件名
      String businessType = extractBusinessTypeFromPath(relativePath);
      String originalFilename = extractFilenameFromPath(relativePath);

      FileUploadResult result = new FileUploadResult(
        relativePath,
        originalFilename,
        fileSize,
        null, // 需要额外存储才能获取contentType
        businessType,
        fileStorageService.getFileAccessUrl(relativePath),
        null  // 需要额外存储才能获取fileCategory
      );

      return ApiResult.success(result);

    } catch (Exception e) {
      log.error("获取文件信息失败: {}", e.getMessage());
      throw e;
    }
  }

  /**
   * 从路径中提取业务类型
   */
  private String extractBusinessTypeFromPath(String relativePath) {
    if (relativePath == null || relativePath.length() < 2) {
      return "unknown";
    }

    // 路径格式: /businessType/yyyy/MM/dd/filename.ext
    int secondSlash = relativePath.indexOf('/', 1);
    if (secondSlash > 0) {
      return relativePath.substring(1, secondSlash);
    }

    return "unknown";
  }

  /**
   * 从路径中提取文件名
   */
  private String extractFilenameFromPath(String relativePath) {
    if (relativePath == null) {
      return "unknown";
    }

    int lastSlash = relativePath.lastIndexOf('/');
    if (lastSlash >= 0 && lastSlash < relativePath.length() - 1) {
      return relativePath.substring(lastSlash + 1);
    }

    return "unknown";
  }
}
