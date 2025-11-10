package com.cn.taihe.product;
import com.cn.taihe.back.filestore.controller.ProductImageController;
import com.cn.taihe.back.filestore.dto.ProductImageCreateDTO;
import com.cn.taihe.back.filestore.dto.ProductImageQueryDTO;
import com.cn.taihe.back.filestore.dto.ProductImageUpdateDTO;
import com.cn.taihe.back.filestore.entity.ProductImage;
import com.cn.taihe.back.filestore.service.ProductImageService;
import com.cn.taihe.common.Result;
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
 * ProductImageController 单元测试
 */
@ExtendWith(MockitoExtension.class)
public class ProductImageControllerTest {

  @Mock
  private ProductImageService productImageService;

  @InjectMocks
  private ProductImageController productImageController;

  private ProductImage mockProductImage;
  private ProductImageCreateDTO mockCreateDTO;
  private ProductImageUpdateDTO mockUpdateDTO;
  private ProductImageQueryDTO mockQueryDTO;

  @BeforeEach
  void setUp() {
    // 初始化模拟数据
    mockProductImage = new ProductImage();
    mockProductImage.setId("test-id-123");
    mockProductImage.setFileName("test-image.jpg");
    mockProductImage.setFileSize(1024);
    mockProductImage.setMimeType("image/jpeg");
    mockProductImage.setIsActive(true);
    mockProductImage.setIsPrimary(false);
    mockProductImage.setCreatedTime(LocalDateTime.now());
    mockProductImage.setUpdatedTime(LocalDateTime.now());

    mockCreateDTO = new ProductImageCreateDTO();
    mockCreateDTO.setFileName("new-image.jpg");
    mockCreateDTO.setFileSize(2048);
    mockCreateDTO.setMimeType("image/png");
    mockCreateDTO.setIsActive(true);
    mockCreateDTO.setIsPrimary(true);

    mockUpdateDTO = new ProductImageUpdateDTO();
    mockUpdateDTO.setId("test-id-123");
    mockUpdateDTO.setFileName("updated-image.jpg");
    mockUpdateDTO.setIsActive(false);

    mockQueryDTO = new ProductImageQueryDTO();
    mockQueryDTO.setFileName("test");
    mockQueryDTO.setIsActive(true);
  }

  @Test
  void getProductImageById_Success() {
    // 准备
    String id = "test-id-123";
    when(productImageService.getProductImageById(id)).thenReturn(mockProductImage);

    // 执行
    ResponseEntity<Object> response = productImageController.getProductImageById(id);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof ProductImage);

    ProductImage result = (ProductImage) response.getBody();
    assertEquals(id, result.getId());
    assertEquals("test-image.jpg", result.getFileName());
  }

  @Test
  void getProductImageById_NotFound() {
    // 准备
    String id = "non-existent-id";
    when(productImageService.getProductImageById(id)).thenReturn(null);

    // 执行
    ResponseEntity<Object> response = productImageController.getProductImageById(id);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  void getProductImageById_Exception() {
    // 准备
    String id = "test-id-123";
    when(productImageService.getProductImageById(id)).thenThrow(new RuntimeException("Database error"));

    // 执行
    ResponseEntity<Object> response = productImageController.getProductImageById(id);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof Result);
  }

  @Test
  void createProductImage_Success() {
    // 准备
    when(productImageService.createProductImage(any(ProductImageCreateDTO.class))).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productImageController.createProductImage(mockCreateDTO);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody());
  }

  @Test
  void createProductImage_Failure() {
    // 准备
    when(productImageService.createProductImage(any(ProductImageCreateDTO.class))).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productImageController.createProductImage(mockCreateDTO);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertFalse((Boolean) response.getBody());
  }

  @Test
  void createProductImage_Exception() {
    // 准备
    when(productImageService.createProductImage(any(ProductImageCreateDTO.class)))
      .thenThrow(new RuntimeException("Create failed"));

    // 执行
    ResponseEntity<Object> response = productImageController.createProductImage(mockCreateDTO);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof Result);
  }

  @Test
  void updateProductImage_Success() {
    // 准备
    when(productImageService.updateProductImage(any(ProductImageUpdateDTO.class))).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productImageController.updateProductImage(mockUpdateDTO);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody());
  }

  @Test
  void updateProductImage_Failure() {
    // 准备
    when(productImageService.updateProductImage(any(ProductImageUpdateDTO.class))).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productImageController.updateProductImage(mockUpdateDTO);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertFalse((Boolean) response.getBody());
  }

  @Test
  void deleteProductImage_Success() {
    // 准备
    String id = "test-id-123";
    when(productImageService.deleteProductImageById(id)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productImageController.deleteProductImage(id);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody());
  }

  @Test
  void getProductImagePage_Success() {
    // 准备
    PageInfo<ProductImage> mockPageInfo = new PageInfo<>();
    mockPageInfo.setList(Arrays.asList(mockProductImage));
    mockPageInfo.setTotal(1L);

    when(productImageService.getProductImagePage(any(ProductImageQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(mockPageInfo);

    // 执行
    ResponseEntity<Object> response = productImageController.getProductImagePage(mockQueryDTO, 1, 10);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody() instanceof PageInfo);

    PageInfo<?> result = (PageInfo<?>) response.getBody();
    assertEquals(1L, result.getTotal());
    assertEquals(1, result.getList().size());
  }

  @Test
  void getProductImagePage_WithNullQueryDTO() {
    // 准备
    PageInfo<ProductImage> mockPageInfo = new PageInfo<>();
    mockPageInfo.setList(Collections.emptyList());
    mockPageInfo.setTotal(0L);

    when(productImageService.getProductImagePage(any(ProductImageQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(mockPageInfo);

    // 执行
    ResponseEntity<Object> response = productImageController.getProductImagePage(null, 1, 10);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody() instanceof PageInfo);
  }

  @Test
  void getProductImageList_Success() {
    // 准备
    List<ProductImage> mockList = Arrays.asList(mockProductImage);
    when(productImageService.getProductImageList(any(ProductImageQueryDTO.class))).thenReturn(mockList);

    // 执行
    ResponseEntity<Object> response = productImageController.getProductImageList(mockQueryDTO);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody() instanceof List);

    List<?> result = (List<?>) response.getBody();
    assertEquals(1, result.size());
    assertTrue(result.get(0) instanceof ProductImage);
  }

  @Test
  void getAllProductImage_Success() {
    // 准备
    List<ProductImage> mockList = Arrays.asList(mockProductImage, mockProductImage);
    when(productImageService.getAllProductImage()).thenReturn(mockList);

    // 执行
    ResponseEntity<Object> response = productImageController.getAllProductImage();

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody() instanceof List);

    List<?> result = (List<?>) response.getBody();
    assertEquals(2, result.size());
  }

  @Test
  void deleteProductImageBatch_Success() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    when(productImageService.deleteProductImageBatch(ids)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productImageController.deleteProductImageBatch(ids);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody());
  }

  @Test
  void deleteProductImageBatch_EmptyList() {
    // 准备
    List<String> ids = Collections.emptyList();
    when(productImageService.deleteProductImageBatch(ids)).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productImageController.deleteProductImageBatch(ids);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertFalse((Boolean) response.getBody());
  }

  @Test
  void updateProductImageStatus_Success() {
    // 准备
    String id = "test-id-123";
    Boolean isActive = false;
    when(productImageService.updateProductImageStatus(id, isActive)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productImageController.updateProductImageStatus(id, isActive);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody());
  }

  @Test
  void updateProductImageStatusBatch_Success() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    Boolean isActive = true;
    when(productImageService.updateProductImageStatusBatch(ids, isActive)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productImageController.updateProductImageStatusBatch(ids, isActive);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody());
  }

  @Test
  void updateProductImageStatusBatch_Failure() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    Boolean isActive = true;
    when(productImageService.updateProductImageStatusBatch(ids, isActive)).thenReturn(false);

    // 执行
    ResponseEntity<Object> response = productImageController.updateProductImageStatusBatch(ids, isActive);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertFalse((Boolean) response.getBody());
  }

  @Test
  void testDefaultPageParameters() {
    // 准备
    PageInfo<ProductImage> mockPageInfo = new PageInfo<>();
    mockPageInfo.setList(Arrays.asList(mockProductImage));
    mockPageInfo.setTotal(1L);

    // 使用正确的参数匹配器组合
    when(productImageService.getProductImagePage(
      any(ProductImageQueryDTO.class),
      isNull(),
      isNull()
    )).thenReturn(mockPageInfo);

    // 执行
    ResponseEntity<Object> response = productImageController.getProductImagePage(mockQueryDTO, null, null);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof PageInfo);

    PageInfo<?> result = (PageInfo<?>) response.getBody();
    assertEquals(1L, result.getTotal());
  }

  @Test
  void testInvalidPageParameters() {
    // 准备
    PageInfo<ProductImage> mockPageInfo = new PageInfo<>();
    mockPageInfo.setList(Collections.emptyList());
    mockPageInfo.setTotal(0L);

    when(productImageService.getProductImagePage(any(ProductImageQueryDTO.class), anyInt(), anyInt()))
      .thenReturn(mockPageInfo);

    // 执行 - 传入无效的分页参数
    ResponseEntity<Object> response = productImageController.getProductImagePage(mockQueryDTO, -1, -10);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody() instanceof PageInfo);
  }
}
