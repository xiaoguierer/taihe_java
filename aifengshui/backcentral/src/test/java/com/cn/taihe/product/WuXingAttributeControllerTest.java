package com.cn.taihe.product;

import com.cn.taihe.back.product.controller.WuXingAttributeController;
import com.cn.taihe.back.product.dto.WuXingAttributeCreateDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeQueryDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeUpdateDTO;
import com.cn.taihe.back.product.entity.WuXingAttribute;
import com.cn.taihe.back.product.service.WuXingAttributeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 五行属性控制器单元测试
 */
@ExtendWith(MockitoExtension.class)
class WuXingAttributeControllerTest {

  private MockMvc mockMvc;

  @Mock
  private WuXingAttributeService wuXingAttributeService;

  @InjectMocks
  private WuXingAttributeController wuXingAttributeController;

  private ObjectMapper objectMapper = new ObjectMapper();

  private WuXingAttribute testEntity;
  private WuXingAttributeCreateDTO testCreateDTO;
  private WuXingAttributeUpdateDTO testUpdateDTO;
  private WuXingAttributeQueryDTO testQueryDTO;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(wuXingAttributeController).build();

    // 初始化测试数据
    String testId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

    testEntity = new WuXingAttribute()
      .setId(testId)
      .setElementKey("test_metal")
      .setElementCode("TEST_JIN")
      .setElementCategory("basic")
      .setElementNameEn("Test Metal")
      .setElementNameZh("测试金")
      .setSymbolCharacter("金")
      .setSymbolColor("#FFD700")
      .setSortOrder(1)
      .setElementTier(1)
      .setIsActive(1)
      .setCreatedTime(LocalDateTime.now())
      .setUpdatedTime(LocalDateTime.now());

    testCreateDTO = new WuXingAttributeCreateDTO()
      .setId(testId)
      .setElementKey("test_metal")
      .setElementCode("TEST_JIN")
      .setElementCategory("basic")
      .setElementNameEn("Test Metal")
      .setElementNameZh("测试金")
      .setSymbolCharacter("金")
      .setSymbolColor("#FFD700")
      .setSortOrder(1)
      .setElementTier(1)
      .setIsActive(1);

    testUpdateDTO = new WuXingAttributeUpdateDTO()
      .setId(testId)
      .setElementNameZh("更新后的中文名")
      .setElementNameEn("Updated English Name")
      .setSortOrder(2);

    testQueryDTO = new WuXingAttributeQueryDTO()
      .setElementCategory("basic")
      .setIsActive(1);
  }

  @Test
  void testGetById_Success() throws Exception {
    // 准备
    when(wuXingAttributeService.getById(anyString())).thenReturn(testEntity);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/{id}", testEntity.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data.id").value(testEntity.getId()))
      .andExpect(jsonPath("$.data.elementKey").value(testEntity.getElementKey()));
  }

  @Test
  void testGetById_NotFound() throws Exception {
    // 准备
    when(wuXingAttributeService.getById(anyString())).thenReturn(null);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/{id}", "non_exist_id"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("五行属性不存在"));
  }

  @Test
  void testGetById_EmptyId() throws Exception {
    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/{id}", ""))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("ID不能为空"));
  }

  @Test
  void testCreate_Success() throws Exception {
    // 准备
    when(wuXingAttributeService.create(any(WuXingAttributeCreateDTO.class))).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/{id}")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testCreateDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testCreate_Failure() throws Exception {
    // 准备
    when(wuXingAttributeService.create(any(WuXingAttributeCreateDTO.class))).thenReturn(false);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testCreateDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("新增五行属性失败"));
  }

  @Test
  void testCreate_Exception() throws Exception {
    // 准备
    when(wuXingAttributeService.create(any(WuXingAttributeCreateDTO.class)))
      .thenThrow(new RuntimeException("业务异常"));

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testCreateDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("业务异常"));
  }

  @Test
  void testCreate_NullDTO() throws Exception {
    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{}"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("400"));
  }

  @Test
  void testUpdate_Success() throws Exception {
    // 准备
    when(wuXingAttributeService.update(any(WuXingAttributeUpdateDTO.class))).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/api/wu-xing-attributes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testUpdateDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testUpdate_Failure() throws Exception {
    // 准备
    when(wuXingAttributeService.update(any(WuXingAttributeUpdateDTO.class))).thenReturn(false);

    // 执行 & 验证
    mockMvc.perform(put("/api/wu-xing-attributes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testUpdateDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("更新五行属性失败，记录可能不存在"));
  }

  @Test
  void testUpdate_EmptyId() throws Exception {
    // 准备
    WuXingAttributeUpdateDTO invalidDTO = new WuXingAttributeUpdateDTO();

    // 执行 & 验证
    mockMvc.perform(put("/api/wu-xing-attributes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(invalidDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("400"));
  }

  @Test
  void testDeleteById_Success() throws Exception {
    // 准备
    when(wuXingAttributeService.deleteById(anyString())).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(delete("/api/wu-xing-attributes/{id}", testEntity.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testDeleteById_Failure() throws Exception {
    // 准备
    when(wuXingAttributeService.deleteById(anyString())).thenReturn(false);

    // 执行 & 验证
    mockMvc.perform(delete("/api/wu-xing-attributes/{id}", testEntity.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("删除五行属性失败，记录可能不存在"));
  }

  @Test
  void testGetPageList_Success() throws Exception {
    // 准备
    PageInfo<WuXingAttribute> pageInfo = new PageInfo<>();
    pageInfo.setList(Arrays.asList(testEntity));
    pageInfo.setTotal(1L);

    when(wuXingAttributeService.getPageList(any(WuXingAttributeQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(pageInfo);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/page")
      .param("page", "1")
      .param("size", "10")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testQueryDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data.list").isArray())
      .andExpect(jsonPath("$.data.list.length()").value(1));
  }

  @Test
  void testGetList_Success() throws Exception {
    // 准备
    List<WuXingAttribute> list = Arrays.asList(testEntity);
    when(wuXingAttributeService.getList(any(WuXingAttributeQueryDTO.class))).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/list")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testQueryDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").isArray())
      .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void testGetAll_Success() throws Exception {
    // 准备
    List<WuXingAttribute> list = Arrays.asList(testEntity);
    when(wuXingAttributeService.getAll()).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/all"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").isArray())
      .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void testGetAllActive_Success() throws Exception {
    // 准备
    List<WuXingAttribute> list = Arrays.asList(testEntity);
    when(wuXingAttributeService.getAllActive()).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/active"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").isArray())
      .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void testGetKeyInfoList_Success() throws Exception {
    // 准备
    List<WuXingAttribute> list = Arrays.asList(testEntity);
    when(wuXingAttributeService.getKeyInfoList(any(WuXingAttributeQueryDTO.class))).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/key-info")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(testQueryDTO)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").isArray())
      .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void testGetByCategory_Success() throws Exception {
    // 准备
    List<WuXingAttribute> list = Arrays.asList(testEntity);
    when(wuXingAttributeService.getByCategory(anyString())).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/category/{category}", "basic"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").isArray())
      .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void testGetByCategory_EmptyCategory() throws Exception {
    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/category/{category}", ""))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("五行属性不存在"));
  }

  @Test
  void testGetByTier_Success() throws Exception {
    // 准备
    List<WuXingAttribute> list = Arrays.asList(testEntity);
    when(wuXingAttributeService.getByTier(anyInt())).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/tier/{tier}", 1))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").isArray())
      .andExpect(jsonPath("$.data.length()").value(1));
  }

  @Test
  void testGetByTier_NullTier() throws Exception {
    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/tier/{tier}", "null"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("层级不能为空"));
  }

  @Test
  void testDeleteBatch_Success() throws Exception {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    when(wuXingAttributeService.deleteBatch(anyList())).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/batch-delete")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(ids)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testDeleteBatch_EmptyIds() throws Exception {
    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/batch-delete")
      .contentType(MediaType.APPLICATION_JSON)
      .content("[]"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("ID集合不能为空"));
  }

  @Test
  void testFreeze_Success() throws Exception {
    // 准备
    when(wuXingAttributeService.freeze(anyString())).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/api/wu-xing-attributes/{id}/freeze", testEntity.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testEnable_Success() throws Exception {
    // 准备
    when(wuXingAttributeService.enable(anyString())).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/api/wu-xing-attributes/{id}/enable", testEntity.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testFreezeBatch_Success() throws Exception {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    when(wuXingAttributeService.freezeBatch(anyList())).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/batch-freeze")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(ids)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testEnableBatch_Success() throws Exception {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    when(wuXingAttributeService.enableBatch(anyList())).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(post("/api/wu-xing-attributes/batch-enable")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(ids)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data").value(true));
  }

  @Test
  void testGetByElementKey_Success() throws Exception {
    // 准备
    when(wuXingAttributeService.getByElementKey(anyString())).thenReturn(testEntity);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/element-key/{elementKey}", "test_metal"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.data.elementKey").value("test_metal"));
  }

  @Test
  void testGetByElementKey_NotFound() throws Exception {
    // 准备
    when(wuXingAttributeService.getByElementKey(anyString())).thenReturn(null);

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/element-key/{elementKey}", "non_exist"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("五行属性不存在"));
  }

  @Test
  void testGetByElementKey_EmptyKey() throws Exception {
    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/element-key/{elementKey}", ""))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("五行属性不存在"));
  }

  @Test
  void testServiceExceptionHandling() throws Exception {
    // 准备 - 模拟Service层抛出异常
    when(wuXingAttributeService.getById(anyString()))
      .thenThrow(new RuntimeException("数据库连接失败"));

    // 执行 & 验证
    mockMvc.perform(get("/api/wu-xing-attributes/{id}", testEntity.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("查询五行属性详情失败"));
  }
}
