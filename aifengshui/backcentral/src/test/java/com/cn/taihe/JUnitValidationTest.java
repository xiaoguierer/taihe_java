package com.cn.taihe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("JUnit和依赖导入验证测试")
class JUnitValidationTest {

  @Autowired(required = false)
  private ApplicationContext applicationContext;

  @Test
  @DisplayName("验证JUnit 5注解导入")
  void testJUnit5Annotations() {
    // 测试JUnit 5核心注解
    assertDoesNotThrow(() -> {
      Class<?> testAnnotation = Class.forName("org.junit.jupiter.api.Test");
      Class<?> displayNameAnnotation = Class.forName("org.junit.jupiter.api.DisplayName");
      Class<?> beforeEachAnnotation = Class.forName("org.junit.jupiter.api.BeforeEach");

      assertNotNull(testAnnotation);
      assertNotNull(displayNameAnnotation);
      assertNotNull(beforeEachAnnotation);
    }, "JUnit 5注解应该可以正常导入");

    System.out.println("✅ JUnit 5注解导入验证通过");
  }

  @Test
  @DisplayName("验证断言库导入")
  void testAssertionImports() {
    // 测试JUnit 5断言
    assertTrue(true, "assertTrue应该工作正常");
    assertFalse(false, "assertFalse应该工作正常");
    assertNotNull("not null", "assertNotNull应该工作正常");
    assertNull(null, "assertNull应该工作正常");
    assertEquals(1, 1, "assertEquals应该工作正常");

    // 测试异常断言
    assertThrows(IllegalArgumentException.class, () -> {
      throw new IllegalArgumentException("测试异常");
    }, "assertThrows应该工作正常");

    System.out.println("✅ 断言库导入验证通过");
  }

  @Test
  @DisplayName("验证Spring测试框架导入")
  void testSpringTestImports() {
    assertDoesNotThrow(() -> {
      Class<?> springBootTest = Class.forName("org.springframework.boot.test.context.SpringBootTest");
      Class<?> autowired = Class.forName("org.springframework.beans.factory.annotation.Autowired");
      Class<?> mockMultipartFile = Class.forName("org.springframework.mock.web.MockMultipartFile");

      assertNotNull(springBootTest);
      assertNotNull(autowired);
      assertNotNull(mockMultipartFile);
    }, "Spring测试框架类应该可以正常导入");

    System.out.println("✅ Spring测试框架导入验证通过");
  }

  @Test
  @DisplayName("验证Web相关导入")
  void testWebImports() {
    assertDoesNotThrow(() -> {
      Class<?> multipartFile = Class.forName("org.springframework.web.multipart.MultipartFile");
      Class<?> mapClass = Class.forName("java.util.Map");

      assertNotNull(multipartFile);
      assertNotNull(mapClass);
    }, "Web相关类应该可以正常导入");

    System.out.println("✅ Web相关导入验证通过");
  }

  @Test
  @DisplayName("验证Spring上下文注入")
  void testSpringContextInjection() {
    assertNotNull(applicationContext, "ApplicationContext应该被成功注入");

    // 验证Bean是否存在
    assertTrue(applicationContext.containsBean("backCentralApplication"),
      "应该包含主应用Bean");

    System.out.println("✅ Spring上下文注入验证通过");
    System.out.println("   Bean数量: " + applicationContext.getBeanDefinitionCount());
  }

  @Test
  @DisplayName("验证MockMultipartFile功能")
  void testMockMultipartFileFunctionality() {
    // 创建测试文件
    MultipartFile file = new MockMultipartFile(
      "test.jpg",
      "test.jpg",
      "image/jpeg",
      "test image content".getBytes()
    );

    assertNotNull(file, "MockMultipartFile应该能正常创建");
    assertEquals("test.jpg", file.getOriginalFilename(), "文件名应该匹配");
    assertEquals("image/jpeg", file.getContentType(), "内容类型应该匹配");
    assertFalse(file.isEmpty(), "文件不应该为空");
    assertTrue(file.getSize() > 0, "文件大小应该大于0");

    System.out.println("✅ MockMultipartFile功能验证通过");
    System.out.println("   文件名: " + file.getOriginalFilename());
    System.out.println("   文件大小: " + file.getSize() + " bytes");
  }

  @Test
  @DisplayName("验证Map和集合操作")
  void testMapAndCollectionOperations() {
    // 测试Java集合框
    Map<String, Object> testMap = new HashMap<>();
    testMap.put("key1","value1");
    testMap.put("key2",123);
    testMap.put("key3",true);

    assertNotNull(testMap, "Map应该能正常创建");
    assertEquals(3, testMap.size(), "Map大小应该正确");
    assertTrue(testMap.containsKey("key1"), "应该包含key1");
    assertEquals("value1", testMap.get("key1"), "key1的值应该正确");

    System.out.println("✅ Map和集合操作验证通过");
  }

  @Test
  @DisplayName("综合验证所有必需导入")
  void testAllRequiredImports() {
    String[] requiredClasses = {
      "org.junit.jupiter.api.Test",
      "org.junit.jupiter.api.DisplayName",
      "org.junit.jupiter.api.BeforeEach",
      "org.springframework.boot.test.context.SpringBootTest",
      "org.springframework.beans.factory.annotation.Autowired",
      "org.springframework.mock.web.MockMultipartFile",
      "org.springframework.web.multipart.MultipartFile",
      "java.util.Map"
    };

    for (String className : requiredClasses) {
      assertDoesNotThrow(() -> {
        Class.forName(className);
      }, "类 " + className + " 应该可以正常导入");
    }

    System.out.println("✅ 所有必需导入验证通过");
    System.out.println("   验证了 " + requiredClasses.length + " 个核心类");
  }

  @Test
  @DisplayName("性能测试：快速断言")
  void testPerformance() {
    long startTime = System.currentTimeMillis();

    // 执行1000次快速断言
    for (int i = 0; i < 1000; i++) {
      assertTrue(i >= 0, "快速断言测试");
    }

    long duration = System.currentTimeMillis() - startTime;
    assertTrue(duration < 1000, "1000次断言应该在1秒内完成，实际耗时: " + duration + "ms");

    System.out.println("✅ 性能测试通过，1000次断言耗时: " + duration + "ms");
  }
}
