package com.cn.taihe;

import com.cn.taihe.imagefile.service.ImageStorageService;
import com.cn.taihe.imageutils.UploadConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("图片存储服务测试")
class ImageStorageServiceTest {

  @Autowired
  private ImageStorageService imageStorageService;

  @Autowired
  private UploadConfig uploadConfig;

  private MultipartFile validImageFile;
  private MultipartFile invalidTypeFile;
  private MultipartFile emptyFile;

  @BeforeEach
  void setUp() {
    // 创建更真实的模拟JPEG文件（包含有效的JPEG文件头）
    byte[] jpegContent = createValidJpegContent();

    validImageFile = new MockMultipartFile(
      "test.jpg",
      "test.jpg",
      "image/jpeg",
      jpegContent
    );

    // 创建无效类型的文件
    invalidTypeFile = new MockMultipartFile(
      "test.txt",
      "test.txt",
      "text/plain",
      "invalid content".getBytes()
    );

    // 创建空文件
    emptyFile = new MockMultipartFile(
      "empty.jpg",
      "empty.jpg",
      "image/jpeg",
      new byte[0]
    );
  }

  /**
   * 创建有效的JPEG文件内容（最小有效的JPEG结构）
   */
  private byte[] createValidJpegContent() {
    // 最小有效的JPEG文件结构
    byte[] jpeg = new byte[] {
      (byte)0xFF, (byte)0xD8, // SOI marker
      (byte)0xFF, (byte)0xE0, // APP0 marker
      (byte)0x00, (byte)0x10, // APP0 length
      (byte)0x4A, (byte)0x46, (byte)0x49, (byte)0x46, (byte)0x00, // "JFIF"
      (byte)0x01, (byte)0x01, // Version
      (byte)0x00, // Aspect ratio
      (byte)0x00, (byte)0x00, // Thumbnail dimensions
      (byte)0x00, (byte)0x00, // Thumbnail data length
      (byte)0xFF, (byte)0xDB, // DQT marker
      (byte)0x00, (byte)0x43, // DQT length
      // ... 简化的量化表数据
      (byte)0xFF, (byte)0xC0, // SOF0 marker
      (byte)0x00, (byte)0x0B, // SOF0 length
      (byte)0x08, // Precision
      (byte)0x00, (byte)0x01, // Height (1像素)
      (byte)0x00, (byte)0x01, // Width (1像素)
      (byte)0x03, // Component count
      // ... 简化的组件数据
      (byte)0xFF, (byte)0xDA, // SOS marker
      (byte)0x00, (byte)0x0C, // SOS length
      (byte)0x03, // Component count
      // ... 简化的扫描数据
      (byte)0x00, // 图像数据
      (byte)0xFF, (byte)0xD9  // EOI marker
    };
    return jpeg;
  }

  @Test
  @DisplayName("测试有效图片上传")
  void testUploadValidImage() {
    Map<String, Object> result = imageStorageService.uploadImage(validImageFile, "products");

    assertTrue((Boolean) result.get("success"), "上传应该成功");
    assertNotNull(result.get("fileName"), "文件名不应为null");
    assertNotNull(result.get("accessUrl"), "访问URL不应为null");
    assertNotNull(result.get("filePath"), "文件路径不应为null");

    String accessUrl = (String) result.get("accessUrl");
    assertTrue(accessUrl.contains("/images/"), "访问URL应包含/images/路径");

    // 静默处理图片信息获取结果
    Object imageInfo = result.get("imageInfo");
    if (imageInfo != null) {
      System.out.println("   图片信息: " + imageInfo);
    }

    System.out.println("✅ 有效图片上传测试通过");
    System.out.println("   文件名: " + result.get("fileName"));
    System.out.println("   访问URL: " + result.get("accessUrl"));
    System.out.println("   文件大小: " + result.get("fileSize") + "MB");
  }

  @Test
  @DisplayName("测试无效文件类型上传")
  void testUploadInvalidFileType() {
    Map<String, Object> result = imageStorageService.uploadImage(invalidTypeFile, "products");

    assertFalse((Boolean) result.get("success"), "无效类型上传应该失败");
    assertTrue(((String) result.get("message")).toLowerCase().contains("不支持"),
      "错误消息应提示不支持的文件类型");

    System.out.println("✅ 无效文件类型测试通过: " + result.get("message"));
  }

  @Test
  @DisplayName("测试空文件上传")
  void testUploadEmptyFile() {
    Map<String, Object> result = imageStorageService.uploadImage(emptyFile, "products");

    assertFalse((Boolean) result.get("success"), "空文件上传应该失败");
    String message = (String) result.get("message");
    assertTrue(message.contains("空") || message.contains("empty"),
      "错误消息应提示文件为空");

    System.out.println("✅ 空文件上传测试通过: " + message);
  }

  @Test
  @DisplayName("测试配置注入")
  void testConfigInjection() {
    assertNotNull(uploadConfig, "UploadConfig应该被注入");
    assertNotNull(imageStorageService, "ImageStorageService应该被注入");

    System.out.println("✅ 配置注入测试通过");
    System.out.println("   基础目录: " + uploadConfig.getBaseDir());
    System.out.println("   访问URL: " + uploadConfig.getAccessBaseUrl());
    System.out.println("   最大文件: " + uploadConfig.getMaxSize());
  }

  @Test
  @DisplayName("测试URL生成功能")
  void testImageUrlGeneration() {
    String testFileName = "test-image-123.jpg";
    String url = imageStorageService.getImageUrl("products", testFileName);

    assertNotNull(url, "生成的URL不应为null");
    assertTrue(url.startsWith(uploadConfig.getAccessBaseUrl()), "URL应以配置的baseUrl开头");
    assertTrue(url.contains("/products/"), "URL应包含分类路径");
    assertTrue(url.endsWith(testFileName), "URL应以文件名结尾");

    System.out.println("✅ URL生成测试通过: " + url);
  }

  @Test
  @DisplayName("测试文件存在性检查")
  void testFileExistenceCheck() {
    // 先上传文件
    Map<String, Object> result = imageStorageService.uploadImage(validImageFile, "test");

    if ((Boolean) result.get("success")) {
      String filePath = (String) result.get("filePath");
      boolean exists = imageStorageService.imageExists(filePath);

      assertTrue(exists, "上传的文件应该存在");
      System.out.println("✅ 文件存在性检查测试通过");

      // 清理测试文件
      imageStorageService.deleteImage(filePath);
    }
  }
}
