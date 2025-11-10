package com.cn.taihe.common.objectutils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class PageInfoJsonConverter {

  private final ObjectMapper objectMapper;

  public PageInfoJsonConverter() {
    this.objectMapper = new ObjectMapper();
    // 配置ObjectMapper
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }

  /**
   * 将PageInfo转换为JSON字符串
   */
  public String convertToJson(PageInfo<?> pageInfo) {
    try {
      return objectMapper.writeValueAsString(pageInfo);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("PageInfo转JSON失败", e);
    }
  }

  /**
   * 将PageInfo转换为格式化的JSON字符串
   */
  public String convertToPrettyJson(PageInfo<?> pageInfo) {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pageInfo);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("PageInfo转JSON失败", e);
    }
  }
}
