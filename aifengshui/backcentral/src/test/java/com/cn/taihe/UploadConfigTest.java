package com.cn.taihe;

import com.cn.taihe.imageutils.UploadConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UploadConfigTest {

  @Autowired
  private UploadConfig uploadConfig;  // 现在应该能正常注入了

  @Test
  void testConfigLoads() {
    System.out.println("=== 配置测试开始 ===");

    assertNotNull(uploadConfig, "UploadConfig应该被成功注入");
    assertNotNull(uploadConfig.getBaseDir(), "baseDir不应该为null");
    assertNotNull(uploadConfig.getAccessBaseUrl(), "accessBaseUrl不应该为null");

    System.out.println("配置注入成功！");
    System.out.println("BaseDir: " + uploadConfig.getBaseDir());
    System.out.println("AccessBaseUrl: " + uploadConfig.getAccessBaseUrl());
    System.out.println("MaxSize: " + uploadConfig.getMaxSize());
    System.out.println("AllowedTypes: " + java.util.Arrays.toString(uploadConfig.getAllowedTypes()));

    // 验证配置值是否正确绑定
    assertEquals("./src/main/resources/static/upload", uploadConfig.getBaseDir());
    assertEquals("10MB", uploadConfig.getMaxSize());

    System.out.println("=== 配置测试完成 ===");
  }
}
