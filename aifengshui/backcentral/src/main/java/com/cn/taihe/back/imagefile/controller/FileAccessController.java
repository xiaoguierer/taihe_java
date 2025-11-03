package com.cn.taihe.back.imagefile.controller;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件访问控制器（用于文件下载和预览）
 */
@Slf4j
@RestController
@RequestMapping("/files")
public class FileAccessController {

  @Autowired
  private FileStorageService fileStorageService;

  /**
   * 通用文件访问接口
   * 支持路径通配符：/api/files/{businessType}/**
   *
   * @param businessType 业务类型（路径第一部分）
   * @param request HTTP请求
   * @return 文件资源
   */
  @GetMapping("/{businessType}/**")
  public ResponseEntity<Resource> accessFile(
    @PathVariable String businessType,
    HttpServletRequest request) throws UnsupportedEncodingException {
    try {
      // 从请求URL中提取完整的相对路径
      String relativePath = extractRelativePath(request, businessType);
      log.debug("文件访问请求: {}", relativePath);

      // 加载文件资源
      Resource resource = fileStorageService.downloadAsResource(relativePath);

      // 确定Content-Type
      String contentType = determineContentType(relativePath);

      // 设置响应头
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType(contentType));

      // 对于非图片文件，建议下载而不是预览
      if (!contentType.startsWith("image/")) {
        String filename = extractFilename(relativePath);
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        headers.setContentDispositionFormData("attachment", encodedFilename);
      } else {
        // 图片文件允许预览
        headers.setContentDispositionFormData("inline", extractFilename(relativePath));
      }

      // 缓存控制（可选）
      headers.setCacheControl("max-age=3600"); // 缓存1小时

      log.info("文件访问成功: {}", relativePath);
      return ResponseEntity.ok()
        .headers(headers)
        .body(resource);

    } catch (Exception e) {
      log.error("文件访问失败: {}", e.getMessage());
      throw e;
    }
  }

  /**
   * 图片预览专用接口（强制内联显示）
   */
  @GetMapping("/preview/{businessType}/**")
  public ResponseEntity<Resource> previewImage(
    @PathVariable String businessType,
    HttpServletRequest request) {

    try {
      String relativePath = extractRelativePath(request, businessType);
      log.debug("图片预览请求: {}", relativePath);

      Resource resource = fileStorageService.downloadAsResource(relativePath);

      // 强制设置为内联显示
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG); // 默认类型，实际会根据文件类型设置
      headers.setContentDispositionFormData("inline", extractFilename(relativePath));

      log.info("图片预览成功: {}", relativePath);
      return ResponseEntity.ok()
        .headers(headers)
        .body(resource);

    } catch (Exception e) {
      log.error("图片预览失败: {}", e.getMessage());
      throw e;
    }
  }

  /**
   * 文件下载专用接口（强制下载）
   */
  @GetMapping("/download/{businessType}/**")
  public ResponseEntity<Resource> downloadFile(
    @PathVariable String businessType,
    HttpServletRequest request) throws UnsupportedEncodingException {

    try {
      String relativePath = extractRelativePath(request, businessType);
      log.debug("文件下载请求: {}", relativePath);

      Resource resource = fileStorageService.downloadAsResource(relativePath);

      String filename = extractFilename(relativePath);
      String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.setContentDispositionFormData("attachment", encodedFilename);

      log.info("文件下载成功: {}", relativePath);
      return ResponseEntity.ok()
        .headers(headers)
        .body(resource);

    } catch (Exception e) {
      log.error("文件下载失败: {}", e.getMessage());
      throw e;
    }
  }

  /**
   * 从请求URL中提取相对路径
   */
  private String extractRelativePath(HttpServletRequest request, String businessType) {
    String requestURI = request.getRequestURI();
    String contextPath = request.getContextPath();

    log.debug("提取路径 - 原始URI: {}, 上下文路径: {}", requestURI, contextPath);

    // 移除上下文路径
    String path = requestURI.substring(contextPath.length());
    log.debug("提取路径 - 处理后路径: {}", path);

    // 支持多种URL模式
    String[] possiblePrefixes = {
      "/files/" + businessType + "/",
      "/files/preview/" + businessType + "/",
      "/files/download/" + businessType + "/"
    };

    for (String prefix : possiblePrefixes) {
      int prefixIndex = path.indexOf(prefix);
      if (prefixIndex >= 0) {
        String filePath = path.substring(prefixIndex + prefix.length());
        String relativePath = "/" + businessType + "/" + filePath;
        log.debug("提取路径 - 匹配前缀: {}, 相对路径: {}", prefix, relativePath);
        return relativePath;
      }
    }

    // 如果以上都不匹配，尝试直接提取
    if (path.startsWith("/files/")) {
      String filePath = path.substring("/files/".length());
      return "/" + filePath;
    }

    throw new IllegalArgumentException("无法从URL中提取文件路径: " + requestURI);
  }

  /**
   * 根据文件扩展名确定Content-Type
   */
  private String determineContentType(String filePath) {
    if (filePath == null) {
      return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    String lowerCasePath = filePath.toLowerCase();

    if (lowerCasePath.endsWith(".jpg") || lowerCasePath.endsWith(".jpeg")) {
      return MediaType.IMAGE_JPEG_VALUE;
    } else if (lowerCasePath.endsWith(".png")) {
      return MediaType.IMAGE_PNG_VALUE;
    } else if (lowerCasePath.endsWith(".gif")) {
      return MediaType.IMAGE_GIF_VALUE;
    } else if (lowerCasePath.endsWith(".pdf")) {
      return MediaType.APPLICATION_PDF_VALUE;
    } else if (lowerCasePath.endsWith(".txt")) {
      return MediaType.TEXT_PLAIN_VALUE;
    } else if (lowerCasePath.endsWith(".html") || lowerCasePath.endsWith(".htm")) {
      return MediaType.TEXT_HTML_VALUE;
    } else if (lowerCasePath.endsWith(".mp4")) {
      return "video/mp4";
    } else if (lowerCasePath.endsWith(".mp3")) {
      return "audio/mpeg";
    } else {
      return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
  }

  /**
   * 从路径中提取文件名
   */
  private String extractFilename(String relativePath) {
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
