package com.cn.taihe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootTest
class DiagnosticTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  void testBeanScanning() {
    System.out.println("=== Bean扫描诊断 ===");

    // 1. 检查所有bean
    String[] allBeans = applicationContext.getBeanDefinitionNames();
    System.out.println("总Bean数量: " + allBeans.length);

    // 2. 查找包含upload的bean
    Arrays.stream(allBeans)
      .filter(name -> name.toLowerCase().contains("upload"))
      .forEach(name -> System.out.println("找到upload相关bean: " + name));

    // 3. 按类型查找UploadConfig
    try {
      String[] uploadConfigBeans = applicationContext.getBeanNamesForType(
        Class.forName("com.cn.taihe.imageutils.UploadConfig"));
      System.out.println("UploadConfig类型bean: " + Arrays.toString(uploadConfigBeans));
    } catch (ClassNotFoundException e) {
      System.out.println("UploadConfig类未找到: " + e.getMessage());
    }

    // 4. 检查配置属性bean
    String[] configPropsBeans = applicationContext.getBeanNamesForType(
      org.springframework.boot.context.properties.ConfigurationPropertiesBean.class);
    System.out.println("配置属性bean数量: " + configPropsBeans.length);
  }
}
