package com.cn.taihe.product;
import com.cn.taihe.back.product.controller.ProductCategoryTagController;
import com.cn.taihe.back.product.dto.ProductCategoryTagCreateDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagQueryDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagUpdateDTO;
import com.cn.taihe.back.product.entity.ProductCategoryTag;
import com.cn.taihe.back.product.service.ProductCategoryTagService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * 商品品类标签表 Controller单元测试
 */
@ExtendWith(MockitoExtension.class)
class ProductCategoryTagControllerTest {

  @Mock
  private ProductCategoryTagService productCategoryTagService;

  @InjectMocks
  private ProductCategoryTagController productCategoryTagController;

  private ProductCategoryTag mockProductCategoryTag;
  private ProductCategoryTagCreateDTO mockCreateDTO;
  private ProductCategoryTagUpdateDTO mockUpdateDTO;
  private ProductCategoryTagQueryDTO mockQueryDTO;

  @BeforeEach
  void setUp() {
    // 初始化测试数据
    mockProductCategoryTag = new ProductCategoryTag();
    mockProductCategoryTag.setId("test-id-123");
    mockProductCategoryTag.setTagKey("necklace");
    mockProductCategoryTag.setTagNameEn("Necklace");
    mockProductCategoryTag.setTagNameZh("项链");
    mockProductCategoryTag.setTagType(1);
    mockProductCategoryTag.setIsActive(true);
    mockProductCategoryTag.setCreatedTime(LocalDateTime.now());
    mockProductCategoryTag.setUpdatedTime(LocalDateTime.now());

    mockCreateDTO = new ProductCategoryTagCreateDTO();
    mockCreateDTO.setTagKey("necklace");
    mockCreateDTO.setTagNameEn("Necklace");
    mockCreateDTO.setTagNameZh("项链");
    mockCreateDTO.setTagType(1);

    mockUpdateDTO = new ProductCategoryTagUpdateDTO();
    mockUpdateDTO.setId("test-id-123");
    mockUpdateDTO.setTagKey("necklace-updated");
    mockUpdateDTO.setTagNameEn("Necklace Updated");
    mockUpdateDTO.setTagNameZh("项链更新");
    mockUpdateDTO.setTagType(1);

    mockQueryDTO = new ProductCategoryTagQueryDTO();
    mockQueryDTO.setTagName("项链");
    mockQueryDTO.setIsActive(true);
  }

  @Test
  void getById_Success() {
    // 准备
    when(productCategoryTagService.getById("test-id-123")).thenReturn(mockProductCategoryTag);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getById("test-id-123");

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void getById_NotFound() {
    // 准备
    when(productCategoryTagService.getById("non-existent-id")).thenReturn(null);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getById("non-existent-id");

    // 验证
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getById_Exception() {
    // 准备
    when(productCategoryTagService.getById("error-id"))
      .thenThrow(new RuntimeException("Database error"));

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getById("error-id");

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
//
//  @Test
//  void create_Success() {
//    // 准备
//    when(productCategoryTagService.create(any(ProductCategoryTagCreateDTO.class))).thenReturn(true);
//
//    // 执行
//    ResponseEntity<Object> response = productCategoryTagController.create(mockCreateDTO);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//  }
//
//  @Test
//  void create_Failure() {
//    // 准备
//    when(productCategoryTagService.create(any(ProductCategoryTagCreateDTO.class))).thenReturn(false);
//
//    // 执行
//    ResponseEntity<Object> response = productCategoryTagController.create(mockCreateDTO);
//
//    // 验证
//    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//  }
//
//  @Test
//  void create_ValidationFailure() {
//    // 准备 - 创建无效的DTO
//    ProductCategoryTagCreateDTO invalidDTO = new ProductCategoryTagCreateDTO();
//    // 缺少必填字段
//
//    // 执行
//    ResponseEntity<Object> response = productCategoryTagController.create(invalidDTO);
//
//    // 验证 - 由于@Valid注解，会抛出MethodArgumentNotValidException，但这里简化测试
//    // 实际项目中需要配置Validation测试
//    assertNotNull(response);
//  }
//
//  @Test
//  void update_Success() {
//    // 准备
//    when(productCategoryTagService.update(any(ProductCategoryTagUpdateDTO.class))).thenReturn(true);
//
//    // 执行
//    ResponseEntity<Object> response = productCategoryTagController.update(mockUpdateDTO);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//  }
//
//  @Test
//  void update_Failure() {
//    // 准备
//    when(productCategoryTagService.update(any(ProductCategoryTagUpdateDTO.class))).thenReturn(false);
//
//    // 执行
//    ResponseEntity<Object> response = productCategoryTagController.update(mockUpdateDTO);
//
//    // 验证
//    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//  }

  @Test
  void deleteById_Success() {
    // 准备
    when(productCategoryTagService.deleteById("test-id-123")).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.deleteById("test-id-123");

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void deleteById_Failure() {
    // 准备
    when(productCategoryTagService.deleteById("non-existent-id")).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.deleteById("non-existent-id");

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void getPageList_Success() {
    // 准备
    PageInfo<ProductCategoryTag> pageInfo = new PageInfo<>();
    pageInfo.setList(Arrays.asList(mockProductCategoryTag));
    pageInfo.setTotal(1L);

    when(productCategoryTagService.getPageList(any(ProductCategoryTagQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(pageInfo);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getPageList(mockQueryDTO, 1, 10);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getPageList_EmptyResult() {
    // 准备
    PageInfo<ProductCategoryTag> pageInfo = new PageInfo<>();
    pageInfo.setList(Collections.emptyList());
    pageInfo.setTotal(0L);

    when(productCategoryTagService.getPageList(any(ProductCategoryTagQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(pageInfo);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getPageList(mockQueryDTO, 1, 10);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getList_Success() {
    // 准备
    List<ProductCategoryTag> tagList = Arrays.asList(mockProductCategoryTag);
    when(productCategoryTagService.getList(any(ProductCategoryTagQueryDTO.class))).thenReturn(tagList);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getList(mockQueryDTO);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getAll_Success() {
    // 准备
    List<ProductCategoryTag> tagList = Arrays.asList(mockProductCategoryTag);
    when(productCategoryTagService.getAll()).thenReturn(tagList);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getAll();

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void deleteBatch_Success() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    when(productCategoryTagService.deleteBatch(anyList())).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.deleteBatch(ids);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void deleteBatch_EmptyList() {
    // 准备
    List<String> ids = Collections.emptyList();
    when(productCategoryTagService.deleteBatch(anyList())).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.deleteBatch(ids);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void freeze_Success() {
    // 准备
    when(productCategoryTagService.freeze("test-id-123")).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.freeze("test-id-123");

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void freeze_Failure() {
    // 准备
    when(productCategoryTagService.freeze("non-existent-id")).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.freeze("non-existent-id");

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void unfreeze_Success() {
    // 准备
    when(productCategoryTagService.unfreeze("test-id-123")).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.unfreeze("test-id-123");

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void unfreeze_Failure() {
    // 准备
    when(productCategoryTagService.unfreeze("non-existent-id")).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.unfreeze("non-existent-id");

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void freezeBatch_Success() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    when(productCategoryTagService.freezeBatch(anyList())).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.freezeBatch(ids);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void unfreezeBatch_Success() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    when(productCategoryTagService.unfreezeBatch(anyList())).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.unfreezeBatch(ids);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getByParentTagId_Success() {
    // 准备
    List<ProductCategoryTag> tagList = Arrays.asList(mockProductCategoryTag);
    when(productCategoryTagService.getByParentTagId("parent-id")).thenReturn(tagList);

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getByParentTagId("parent-id");

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getByParentTagId_EmptyResult() {
    // 准备
    when(productCategoryTagService.getByParentTagId("non-existent-parent")).thenReturn(Collections.emptyList());

    // 执行
    ResponseEntity<Object> response = productCategoryTagController.getByParentTagId("non-existent-parent");

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
//
//  @Test
//  void createSuccessResponse_ValidData() {
//    // 执行
//    Object response = productCategoryTagController.createSuccessResponse("test data");
//
//    // 验证
//    assertNotNull(response);
//    // 这里可以进一步验证ResponseResult的结构
//  }
//
//  @Test
//  void createErrorResponse_ValidMessage() {
//    // 执行
//    Object response = productCategoryTagController.createErrorResponse("Error message");
//
//    // 验证
//    assertNotNull(response);
//    // 这里可以进一步验证ResponseResult的结构
//  }

  @Test
  void getPageList_InvalidPagination() {
    // 准备
    PageInfo<ProductCategoryTag> pageInfo = new PageInfo<>();
    pageInfo.setList(Collections.emptyList());
    pageInfo.setTotal(0L);

    when(productCategoryTagService.getPageList(any(ProductCategoryTagQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(pageInfo);

    // 执行 - 测试无效的分页参数
    ResponseEntity<Object> response = productCategoryTagController.getPageList(mockQueryDTO, -1, -10);

    // 验证 - Service层应该处理无效参数
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
//
//  @Test
//  void update_NullId() {
//    // 准备
//    ProductCategoryTagUpdateDTO invalidDTO = new ProductCategoryTagUpdateDTO();
//    // 不设置ID
//
//    // 执行
//    ResponseEntity<Object> response = productCategoryTagController.update(invalidDTO);
//
//    // 验证 - 由于@Valid注解，会进行参数验证
//    assertNotNull(response);
//  }
}
