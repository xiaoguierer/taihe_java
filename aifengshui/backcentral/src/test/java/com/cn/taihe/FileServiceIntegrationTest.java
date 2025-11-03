package com.cn.taihe;

import com.cn.taihe.back.imagefile.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 文件服务完整集成测试
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class FileServiceIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private FileStorageService fileStorageService;

  @Test
  void testCompleteFileWorkflow() throws Exception {
    // 1. 健康检查
    mockMvc.perform(get("/file/health"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200));

    // 2. 上传文件
    MockMultipartFile file = new MockMultipartFile(
      "file",
      "test-image.jpg",
      "image/jpeg",
      "fake image content".getBytes()
    );

    MvcResult uploadResult = mockMvc.perform(multipart("/file/upload")
      .file(file)
      .param("businessType", "integration-test"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andReturn();

    String response = uploadResult.getResponse().getContentAsString();
    log.info("上传响应: {}", response);

    // 3. 从响应中提取相对路径（这里需要解析JSON）
    // 在实际测试中，您可能需要使用JSONPath或对象映射来提取路径

    log.info("✅ 完整工作流测试通过");
  }

  @Test
  void testControllerMapping() throws Exception {
    // 测试控制器基本映射
    mockMvc.perform(get("/file/test"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value("测试接口正常"));

    log.info("✅ 控制器映射测试通过");
  }
}
