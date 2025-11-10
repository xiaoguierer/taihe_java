package com.cn.taihe.product;

import com.cn.taihe.back.product.controller.EmotionalIntentController;
import com.cn.taihe.back.product.dto.EmotionalIntentCreateDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentQueryDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentUpdateDTO;
import com.cn.taihe.back.product.entity.EmotionalIntent;
import com.cn.taihe.back.product.service.EmotionalIntentService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 情感意图控制器单元测试类
 */
@ExtendWith(MockitoExtension.class)
class EmotionalIntentControllerTest {

  private MockMvc mockMvc;

  @Mock
  private EmotionalIntentService emotionalIntentService;

  @InjectMocks
  private EmotionalIntentController emotionalIntentController;

  private ObjectMapper objectMapper = new ObjectMapper();

  private EmotionalIntent mockEmotionalIntent;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(emotionalIntentController).build();

    // 初始化测试数据
    mockEmotionalIntent = new EmotionalIntent();
    mockEmotionalIntent.setId("test123");
    mockEmotionalIntent.setIntentKey("love_and_care");
    mockEmotionalIntent.setIntentCode("LOVE001");
    mockEmotionalIntent.setIntentCategory("primary");
    mockEmotionalIntent.setIntentNameZh("爱与关怀");
    mockEmotionalIntent.setIntentNameEn("Love and Care");
    mockEmotionalIntent.setSymbolCharacter("❤️");
    mockEmotionalIntent.setSymbolColor("#FF6B6B");
    mockEmotionalIntent.setIsActive(1);
    mockEmotionalIntent.setShowInNavigation(1);
    mockEmotionalIntent.setIsFeatured(0);
    mockEmotionalIntent.setSortOrder(1);
  }

  /**
   * 测试根据ID查询情感意图详情 - 成功场景
   */
  @Test
  void getById_Success() throws Exception {
    // 准备
    String id = "test123";
    when(emotionalIntentService.getById(id)).thenReturn(mockEmotionalIntent);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试根据ID查询情感意图详情 - 数据不存在场景
   */
  @Test
  void getById_NotFound() throws Exception {
    // 准备
    String id = "not-exist";
    when(emotionalIntentService.getById(id)).thenReturn(null);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试分页查询情感意图列表 - 成功场景
   */
  @Test
  void getByPage_Success() throws Exception {
    // 准备
    EmotionalIntentQueryDTO queryDTO = new EmotionalIntentQueryDTO();
    queryDTO.setIntentName("爱");

    PageInfo<EmotionalIntent> pageInfo = new PageInfo<>();
    pageInfo.setTotal(1L);
    pageInfo.setList(Arrays.asList(mockEmotionalIntent));

    when(emotionalIntentService.getByPage(any(EmotionalIntentQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(pageInfo);

    // 执行 & 验证
    mockMvc.perform(post("/emotional-intent/page")
      .param("page", "1")
      .param("size", "10")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(queryDTO)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试查询所有情感意图列表 - 成功场景
   */
  @Test
  void getAll_Success() throws Exception {
    // 准备
    List<EmotionalIntent> list = Arrays.asList(mockEmotionalIntent);
    when(emotionalIntentService.getAll()).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/list")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试查询所有情感意图关键信息 - 成功场景
   */
  @Test
  void getAllKeyInfo_Success() throws Exception {
    // 准备
    List<EmotionalIntent> list = Arrays.asList(mockEmotionalIntent);
    when(emotionalIntentService.getAllKeyInfo()).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/key-info")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试新增情感意图 - 成功场景
   */
 // @Test
//  void create_Success() throws Exception {
//    // 准备
//    EmotionalIntentCreateDTO createDTO = new EmotionalIntentCreateDTO();
//    createDTO.setIntentKey("love_and_care");
//    createDTO.setIntentCode("LOVE001");
//    createDTO.setIntentNameZh("爱与关怀");
//
//    String generatedId = "generated123";
//    when(emotionalIntentService.create(any(EmotionalIntentCreateDTO.class))).thenReturn(generatedId);
//
//    // 执行 & 验证
//    mockMvc.perform(post("/emotional-intent")
//      .contentType(MediaType.APPLICATION_JSON)
//      .content(objectMapper.writeValueAsString(createDTO)))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$.code").value(200))
//      .andExpect(jsonPath("$.message").value("成功"));
//  }

  /**
   * 测试更新情感意图信息 - 成功场景
   */
//  @Test
//  void update_Success() throws Exception {
//    // 准备
//    EmotionalIntentUpdateDTO updateDTO = new EmotionalIntentUpdateDTO();
//    updateDTO.setId("test123");
//    updateDTO.setIntentNameZh("更新后的爱与关怀");
//
//    when(emotionalIntentService.update(any(EmotionalIntentUpdateDTO.class))).thenReturn(true);
//
//    // 执行 & 验证
//    mockMvc.perform(put("/emotional-intent")
//      .contentType(MediaType.APPLICATION_JSON)
//      .content(objectMapper.writeValueAsString(updateDTO)))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$.code").value(200))
//      .andExpect(jsonPath("$.message").value("成功"));
//  }

  /**
   * 测试更新情感意图信息 - 更新失败场景
   */
//  @Test
//  void update_Failed() throws Exception {
//    // 准备
//    EmotionalIntentUpdateDTO updateDTO = new EmotionalIntentUpdateDTO();
//    updateDTO.setId("not-exist");
//
//    when(emotionalIntentService.update(any(EmotionalIntentUpdateDTO.class))).thenReturn(false);
//
//    // 执行 & 验证
//    mockMvc.perform(put("/emotional-intent")
//      .contentType(MediaType.APPLICATION_JSON)
//      .content(objectMapper.writeValueAsString(updateDTO)))
//      .andExpect(status().isOk())
//      .andExpect(jsonPath("$.code").value(500))
//      .andExpect(jsonPath("$.message").value("更新失败，数据可能不存在"));
//  }

  /**
   * 测试根据ID删除情感意图 - 成功场景
   */
  @Test
  void deleteById_Success() throws Exception {
    // 准备
    String id = "test123";
    when(emotionalIntentService.deleteById(id)).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(delete("/emotional-intent/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试根据ID删除情感意图 - 删除失败场景
   */
  @Test
  void deleteById_Failed() throws Exception {
    // 准备
    String id = "not-exist";
    when(emotionalIntentService.deleteById(id)).thenReturn(false);

    // 执行 & 验证
    mockMvc.perform(delete("/emotional-intent/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("删除失败，数据可能不存在"));
  }

  /**
   * 测试批量删除情感意图 - 成功场景
   */
  @Test
  void deleteBatch_Success() throws Exception {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    when(emotionalIntentService.deleteBatch(ids)).thenReturn(3);

    // 执行 & 验证
    mockMvc.perform(post("/emotional-intent/batch-delete")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(ids)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试冻结情感意图 - 成功场景
   */
  @Test
  void freeze_Success() throws Exception {
    // 准备
    String id = "test123";
    when(emotionalIntentService.freeze(id)).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/emotional-intent/freeze/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试启用情感意图 - 成功场景
   */
  @Test
  void activate_Success() throws Exception {
    // 准备
    String id = "test123";
    when(emotionalIntentService.activate(id)).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/emotional-intent/activate/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试批量冻结情感意图 - 成功场景
   */
  @Test
  void freezeBatch_Success() throws Exception {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    when(emotionalIntentService.freezeBatch(ids)).thenReturn(2);

    // 执行 & 验证
    mockMvc.perform(post("/emotional-intent/batch-freeze")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(ids)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试批量启用情感意图 - 成功场景
   */
  @Test
  void activateBatch_Success() throws Exception {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    when(emotionalIntentService.activateBatch(ids)).thenReturn(3);

    // 执行 & 验证
    mockMvc.perform(post("/emotional-intent/batch-activate")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(ids)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试更新排序值 - 成功场景
   */
  @Test
  void updateSortOrder_Success() throws Exception {
    // 准备
    String id = "test123";
    Integer sortOrder = 10;
    when(emotionalIntentService.updateSortOrder(id, sortOrder)).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/emotional-intent/sort-order/{id}", id)
      .param("sortOrder", sortOrder.toString())
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试更新推荐状态 - 成功场景
   */
  @Test
  void updateFeaturedStatus_Success() throws Exception {
    // 准备
    String id = "test123";
    Integer isFeatured = 1;
    when(emotionalIntentService.updateFeaturedStatus(id, isFeatured)).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/emotional-intent/featured/{id}", id)
      .param("isFeatured", isFeatured.toString())
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试更新显示状态 - 成功场景
   */
  @Test
  void updateShowStatus_Success() throws Exception {
    // 准备
    String id = "test123";
    Integer showInNavigation = 1;
    when(emotionalIntentService.updateShowStatus(id, showInNavigation)).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(put("/emotional-intent/show-status/{id}", id)
      .param("showInNavigation", showInNavigation.toString())
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试检查意图代码是否已存在 - 存在场景
   */
  @Test
  void checkIntentCodeExists_Exists() throws Exception {
    // 准备
    String intentCode = "LOVE001";
    String excludeId = "test123";
    when(emotionalIntentService.checkIntentCodeExists(intentCode, excludeId)).thenReturn(true);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/check-intent-code")
      .param("intentCode", intentCode)
      .param("excludeId", excludeId)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试根据意图代码查询情感意图 - 成功场景
   */
  @Test
  void getByIntentCode_Success() throws Exception {
    // 准备
    String intentCode = "LOVE001";
    when(emotionalIntentService.getByIntentCode(intentCode)).thenReturn(mockEmotionalIntent);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/by-code/{intentCode}", intentCode)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试获取启用状态的情感意图数量 - 成功场景
   */
  @Test
  void getActiveCount_Success() throws Exception {
    // 准备
    when(emotionalIntentService.getActiveCount()).thenReturn(5L);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/active-count")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试获取推荐的情感意图列表 - 成功场景
   */
  @Test
  void getFeaturedList_Success() throws Exception {
    // 准备
    List<EmotionalIntent> list = Arrays.asList(mockEmotionalIntent);
    when(emotionalIntentService.getFeaturedList()).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/featured")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试获取在导航显示的情感意图列表 - 成功场景
   */
  @Test
  void getNavigationList_Success() throws Exception {
    // 准备
    List<EmotionalIntent> list = Arrays.asList(mockEmotionalIntent);
    when(emotionalIntentService.getNavigationList()).thenReturn(list);

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/navigation")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.code").value(200))
      .andExpect(jsonPath("$.message").value("成功"));
  }

  /**
   * 测试异常处理 - 系统异常场景
   */
  @Test
  void getById_SystemException() throws Exception {
    // 准备
    String id = "test123";
    when(emotionalIntentService.getById(id)).thenThrow(new RuntimeException("数据库连接失败"));

    // 执行 & 验证
    mockMvc.perform(get("/emotional-intent/{id}", id)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.code").value(500))
      .andExpect(jsonPath("$.message").value("查询情感意图详情失败"));
  }
}
