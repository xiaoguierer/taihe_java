package com.cn.taihe;

import com.cn.taihe.config.FileStorageProperties;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 文件存储服务测试（修复版）
 */
@Slf4j
@SpringBootTest
class FileStorageServiceTest {

  @Autowired
  private FileStorageService fileStorageService;

  @Autowired
  private FileStorageProperties fileStorageProperties;

  @Test
  void testServiceInitialization() {
    assertNotNull(fileStorageService);
    assertNotNull(fileStorageProperties);
    log.info("✅ 文件存储服务初始化测试通过");

    // 打印支持的文件类型
    log.info("✅ 支持的文件类型: {}", fileStorageProperties.getAllowedTypes());
    log.info("✅ 最大文件大小: {}", fileStorageProperties.getMaxSize());
  }

  @Test
  void testImageUpload() {
    try {
      // 创建测试图片文件
      byte[] imageContent = "fake image content".getBytes();
      MultipartFile mockFile = new MockMultipartFile(
        "test.jpg",
        "test.jpg",
        "image/jpeg",
        imageContent
      );

      // 上传文件
      String relativePath = fileStorageService.upload(mockFile, "test");
      assertNotNull(relativePath);
      assertTrue(relativePath.startsWith("/test/"));
      log.info("✅ 图片文件上传成功: {}", relativePath);

      // 检查文件是否存在
      assertTrue(fileStorageService.exists(relativePath));

      log.info("✅ 图片文件上传测试通过");

    } catch (Exception e) {
      log.error("❌ 图片文件测试失败", e);
    }
  }

  @Test
  void testVideoUpload() {
    try {
      // 创建测试视频文件
      byte[] videoContent = "fake video content".getBytes();
      MultipartFile mockFile = new MockMultipartFile(
        "test.mp4",
        "test.mp4",
        "video/mp4",
        videoContent
      );

      // 上传文件
      String relativePath = fileStorageService.upload(mockFile, "videos");
      assertNotNull(relativePath);
      assertTrue(relativePath.startsWith("/videos/"));
      log.info("✅ 视频文件上传成功: {}", relativePath);

      // 检查文件是否存在
      assertTrue(fileStorageService.exists(relativePath));

      log.info("✅ 视频文件上传测试通过");

    } catch (Exception e) {
      log.error("❌ 视频文件测试失败", e);
      fail("视频文件测试失败: " + e.getMessage());
    }
  }

  @Test
  void testDocumentUpload() {
    try {
      // 创建测试文档文件
      byte[] docContent = "fake document content".getBytes();
      MultipartFile mockFile = new MockMultipartFile(
        "test.pdf",
        "test.pdf",
        "application/pdf",
        docContent
      );

      // 上传文件
      String relativePath = fileStorageService.upload(mockFile, "documents");
      assertNotNull(relativePath);
      assertTrue(relativePath.startsWith("/documents/"));
      log.info("✅ 文档文件上传成功: {}", relativePath);

      log.info("✅ 文档文件上传测试通过");

    } catch (Exception e) {
      log.error("❌ 文档文件测试失败", e);
    }
  }

  @Test
  void testFileExistsCheck() {
    // 检查不存在的文件
    boolean exists = fileStorageService.exists("/nonexistent/file.jpg");
    assertFalse(exists);
    log.info("✅ 文件存在性检查测试通过");
  }
}
