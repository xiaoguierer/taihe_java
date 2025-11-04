package com.cn.taihe.back.imagefile.service.impl;
import com.cn.taihe.common.mainexception.FileNotFoundException;
import com.cn.taihe.common.mainexception.FileStorageException;
import com.cn.taihe.common.mainexception.FileValidationException;
import com.cn.taihe.config.FileStorageProperties;
import com.cn.taihe.config.FileTypeValidator;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 本地文件存储服务实现（修复版）
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {
  @Autowired
  private FileStorageProperties fileStorageProperties;

  @Autowired
  private FileTypeValidator fileTypeValidator;

  private Path basePath;

  /**
   * 初始化基础路径（安全版本）
   */
  @PostConstruct
  public void init() {
    try {
      // 获取配置的基础目录
      String baseDir = fileStorageProperties.getBaseDir();
      log.info("配置的存储目录: {}", baseDir);

      // 解析系统属性
      baseDir = resolveSystemProperties(baseDir);

      this.basePath = Paths.get(baseDir).toAbsolutePath().normalize();
      log.info("解析后的存储路径: {}", basePath);

      // 尝试创建存储目录
      if (!tryCreateStorageDirectory()) {
        // 如果失败，使用备用方案
        useFallbackDirectory();
      }

      log.info("✅ 文件存储服务初始化完成，基础路径: {}", basePath);

    } catch (Exception e) {
      log.error("❌ 文件存储服务初始化失败", e);
      // 使用临时目录作为最后手段
      useTemporaryDirectoryAsLastResort();
    }
  }

  /**
   * 解析系统属性（实际实现）
   */
  private String resolveSystemProperties(String path) {
    if (path.contains("${user.home}")) {
      path = path.replace("${user.home}", System.getProperty("user.home"));
    }
    if (path.contains("${java.io.tmpdir}")) {
      path = path.replace("${java.io.tmpdir}", System.getProperty("java.io.tmpdir"));
    }
    return path;
  }

  /**
   * 尝试创建存储目录（实际实现）
   */
  private boolean tryCreateStorageDirectory() {
    String[] directoryOptions = {
      fileStorageProperties.getBaseDir(),  // 首选配置目录
      "./data/uploads",  // 项目相对路径
      System.getProperty("user.home") + "/backcentral/uploads",  // 用户主目录
      System.getProperty("java.io.tmpdir") + "/backcentral/uploads"  // 临时目录
    };

    for (String dirPath : directoryOptions) {
      dirPath = resolveSystemProperties(dirPath);
      Path path = Paths.get(dirPath).toAbsolutePath().normalize();

      log.info("尝试使用目录: {}", path);

      try {
        if (Files.exists(path)) {
          if (Files.isDirectory(path) && Files.isWritable(path)) {
            this.basePath = path;
            log.info("✅ 使用现有可写目录: {}", path);
            return true;
          } else {
            log.warn("目录存在但不可写: {}", path);
          }
        } else {
          // 尝试创建目录
          Files.createDirectories(path);
          if (Files.isWritable(path)) {
            this.basePath = path;
            log.info("✅ 创建并使用目录: {}", path);
            return true;
          } else {
            log.warn("目录创建成功但不可写: {}", path);
            // 尝试删除创建的目录
            try {
              Files.deleteIfExists(path);
            } catch (Exception e) {
              // 忽略删除错误
            }
          }
        }
      } catch (Exception e) {
        log.warn("目录处理失败 {}: {}", path, e.getMessage());
      }
    }

    log.error("❌ 所有目录方案均失败");
    return false;
  }

  /**
   * 使用备用目录
   */
  private void useFallbackDirectory() {
    // 使用项目相对路径作为备用
    Path fallbackPath = Paths.get("data/uploads").toAbsolutePath().normalize();

    try {
      Files.createDirectories(fallbackPath);
      if (Files.isWritable(fallbackPath)) {
        this.basePath = fallbackPath;
        log.warn("⚠️ 使用备用目录: {}", fallbackPath);
        return;
      }
    } catch (Exception e) {
      log.warn("备用目录创建失败: {}", fallbackPath);
    }

    // 备用方案也失败，使用临时目录
    useTemporaryDirectoryAsLastResort();
  }

  /**
   * 使用临时目录作为最后手段
   */
  private void useTemporaryDirectoryAsLastResort() {
    try {
      Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), "backcentral_uploads")
        .toAbsolutePath().normalize();

      Files.createDirectories(tempPath);
      this.basePath = tempPath;
      log.warn("⚠️ 使用临时目录作为最后手段: {}", tempPath);

    } catch (Exception e) {
      log.error("❌ 所有存储目录方案均失败，文件上传功能将不可用");
      // 设置一个默认路径，但服务将不可用
      this.basePath = null;
    }
  }

  @Override
  public String upload(MultipartFile file, String businessType) throws FileStorageException {
    return upload(file, businessType, null);
  }

  @Override
  public String upload(MultipartFile file, String businessType, String customFileName) throws FileStorageException {
    // 检查服务是否可用
    if (basePath == null) {
      throw new FileStorageException("文件存储服务不可用，请检查存储目录配置");
    }

    log.info("开始上传文件: 业务类型={}, 原始文件名={}", businessType, file.getOriginalFilename());

    try {
      // 1. 文件验证
      validateFile(file);

      // 2. 生成存储路径（相对路径）
      String relativePath = generateStoragePath(file, businessType, customFileName);

      // 3. 构建绝对路径（基于配置的基础目录）
      Path targetLocation = basePath.resolve(relativePath.substring(1)); // 移除开头的斜杠

      // 4. 确保目录存在
      ensureDirectoryExists(targetLocation.getParent());

      // 5. 保存文件
      saveFileToDisk(file, targetLocation);

      log.info("✅ 文件上传成功: 相对路径={}, 文件大小={} bytes", relativePath, file.getSize());
      return relativePath;

    } catch (FileValidationException e) {
      log.warn("❌ 文件验证失败: {}", e.getMessage());
      throw e;
    } catch (Exception e) {
      log.error("❌ 文件上传失败: {}", e.getMessage(), e);
      throw new FileStorageException("文件上传失败: " + e.getMessage(), e);
    }
  }

  @Override
  public Resource downloadAsResource(String relativePath) throws FileStorageException {
    log.debug("尝试加载文件资源: {}", relativePath);

    try {
      // 安全检查：防止路径遍历攻击
      validateRelativePath(relativePath);

      // 构建绝对路径（基于基础目录 + 相对路径）
      Path filePath = getFileAbsolutePath(relativePath);

      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists() && resource.isReadable()) {
        log.debug("✅ 文件资源加载成功: {}", relativePath);
        return resource;
      } else {
        log.warn("❌ 文件不存在或不可读: {}", relativePath);
        throw new FileNotFoundException("文件不存在或不可读: " + relativePath);
      }

    } catch (MalformedURLException e) {
      log.error("❌ 文件路径格式错误: {}", relativePath, e);
      throw new FileStorageException("文件路径格式错误: " + relativePath, e);
    } catch (FileNotFoundException e) {
      throw e;
    } catch (Exception e) {
      log.error("❌ 加载文件资源失败: {}", relativePath, e);
      throw new FileStorageException("加载文件资源失败: " + relativePath, e);
    }
  }

  @Override
  public InputStream getFileInputStream(String relativePath) throws FileStorageException {
    try {
      Path filePath = getFileAbsolutePath(relativePath);
      return Files.newInputStream(filePath);
    } catch (IOException e) {
      log.error("❌ 获取文件输入流失败: {}", relativePath, e);
      throw new FileStorageException("获取文件输入流失败: " + relativePath, e);
    }
  }

  @Override
  public boolean delete(String relativePath) throws FileStorageException {
    log.info("尝试删除文件: {}", relativePath);

    try {
      Path filePath = getFileAbsolutePath(relativePath);

      if (Files.exists(filePath)) {
        Files.delete(filePath);
        log.info("✅ 文件删除成功: {}", relativePath);

        // 尝试删除空目录
        deleteEmptyParentDirectories(filePath.getParent());
        return true;
      } else {
        log.warn("⚠️ 要删除的文件不存在: {}", relativePath);
        return false;
      }

    } catch (IOException e) {
      log.error("❌ 文件删除失败: {}", relativePath, e);
      throw new FileStorageException("文件删除失败: " + relativePath, e);
    }
  }

  @Override
  public boolean exists(String relativePath) {
    try {
      Path filePath = getFileAbsolutePath(relativePath);
      return Files.exists(filePath) && Files.isRegularFile(filePath);
    } catch (Exception e) {
      log.warn("⚠️ 检查文件存在性时发生错误: {}", relativePath, e);
      return false;
    }
  }

  @Override
  public long getFileSize(String relativePath) throws FileStorageException {
    try {
      Path filePath = getFileAbsolutePath(relativePath);
      return Files.size(filePath);
    } catch (IOException e) {
      log.error("❌ 获取文件大小时发生错误: {}", relativePath, e);
      throw new FileStorageException("获取文件大小失败: " + relativePath, e);
    }
  }

  @Override
  public String getFileAccessUrl(String relativePath) {
    // 这里返回相对路径，由Controller处理URL映射
    // 实际URL格式为: /api/files/{relativePath}
    return "/api/files" + relativePath;
  }

  @Override
  public Path getFileAbsolutePath(String relativePath) {
    validateRelativePath(relativePath);

    // 移除相对路径开头的斜杠，然后基于基础目录解析
    String pathWithoutLeadingSlash = relativePath.startsWith("/") ?
      relativePath.substring(1) : relativePath;

    Path filePath = basePath.resolve(pathWithoutLeadingSlash).normalize();

    // 安全检查：确保路径在基础目录内
    if (!filePath.startsWith(basePath)) {
      throw new FileValidationException("非法文件路径: " + relativePath);
    }

    return filePath;
  }

  /**
   * 文件验证
   */
  private void validateFile(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new FileValidationException("文件为空");
    }

    if (!fileTypeValidator.isValidFileType(file)) {
      throw new FileValidationException("不支持的文件类型: " + file.getContentType());
    }

    if (!fileTypeValidator.isValidFileSize(file)) {
      throw new FileValidationException("文件大小超过限制: " + file.getSize() + " bytes");
    }

    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null || originalFilename.contains("..")) {
      throw new FileValidationException("文件名包含非法字符");
    }
  }

  /**
   * 生成存储路径
   * 格式: /{businessType}/{yyyy}/{MM}/{dd}/{uuid}.{ext}
   */
  private String generateStoragePath(MultipartFile file, String businessType, String customFileName) {
    // 1. 业务类型目录 - 安全处理
    String bizDir = sanitizeDirectoryName(businessType);
    if (bizDir.isEmpty()) {
      bizDir = "default";
    }

    // 2. 日期目录
    String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

    // 3. 文件名处理
    String fileName;
    if (customFileName != null && !customFileName.trim().isEmpty()) {
      fileName = sanitizeFileName(customFileName.trim());
    } else {
      // 使用UUID作为文件名
      fileName = UUID.randomUUID().toString().replace("-", "");
    }

    // 4. 文件扩展名
    String originalFilename = file.getOriginalFilename();
    String extension = getFileExtension(originalFilename);

    // 5. 构建相对路径（以斜杠开头）
    return "/" + bizDir + "/" + dateDir + "/" + fileName + extension;
  }

  /**
   * 安全处理目录名称
   */
  private String sanitizeDirectoryName(String dirName) {
    if (dirName == null || dirName.trim().isEmpty()) {
      return "default";
    }

    // 移除危险字符，只保留字母、数字、下划线、短横线
    return dirName.replaceAll("[^a-zA-Z0-9_-]", "").trim();
  }

  /**
   * 安全处理文件名
   */
  private String sanitizeFileName(String fileName) {
    if (fileName == null || fileName.trim().isEmpty()) {
      return UUID.randomUUID().toString().replace("-", "");
    }

    // 移除路径分隔符和危险字符
    return fileName.replaceAll("[\\\\/:*?\"<>|]", "").trim();
  }

  /**
   * 获取文件扩展名
   */
  private String getFileExtension(String filename) {
    if (filename == null || filename.isEmpty()) {
      return ".dat";
    }

    int lastDotIndex = filename.lastIndexOf('.');
    if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
      String ext = filename.substring(lastDotIndex + 1).toLowerCase();
      // 只允许特定的扩展名
      if (fileStorageProperties.getAllowedExtensions().contains(ext)) {
        return "." + ext;
      }
    }

    // 如果无法从文件名获取有效的扩展名，使用默认扩展名
    return ".dat";
  }

  /**
   * 确保目录存在
   */
  private void ensureDirectoryExists(Path directory) throws IOException {
    if (!Files.exists(directory)) {
      try {
        Files.createDirectories(directory);
        log.debug("✅ 创建目录: {}", directory);
      } catch (IOException e) {
        log.error("❌ 创建目录失败: {}", directory, e);
        throw new IOException("无法创建目录: " + directory, e);
      }
    }

    // 验证目录可写
    if (!Files.isWritable(directory)) {
      throw new IOException("目录不可写: " + directory);
    }
  }

  /**
   * 保存文件到磁盘
   */
  private void saveFileToDisk(MultipartFile file, Path targetLocation) throws IOException {
    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }
  }

  /**
   * 验证相对路径安全性
   */
  private void validateRelativePath(String relativePath) {
    if (relativePath == null || relativePath.trim().isEmpty()) {
      throw new FileValidationException("文件路径为空");
    }

    if (relativePath.contains("..") || relativePath.contains("//") || relativePath.contains("\\\\")) {
      throw new FileValidationException("文件路径包含非法字符: " + relativePath);
    }

    if (!relativePath.startsWith("/")) {
      throw new FileValidationException("文件路径必须以/开头: " + relativePath);
    }
  }

  /**
   * 删除空的父目录
   */
  private void deleteEmptyParentDirectories(Path directory) {
    try {
      // 只删除到基础路径为止
      while (directory != null &&
        Files.exists(directory) &&
        Files.isDirectory(directory) &&
        !directory.equals(basePath)) {

        if (Files.list(directory).findAny().isPresent()) {
          // 目录不为空，停止删除
          break;
        }

        Files.delete(directory);
        log.debug("删除空目录: {}", directory);

        directory = directory.getParent();
      }
    } catch (IOException e) {
      log.warn("⚠️ 删除空目录时发生错误: {}", e.getMessage());
      // 忽略错误，这不是关键操作
    }
  }
}
