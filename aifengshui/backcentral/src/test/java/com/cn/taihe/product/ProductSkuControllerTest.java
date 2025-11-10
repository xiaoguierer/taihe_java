package com.cn.taihe.product;

import com.cn.taihe.back.product.controller.ProductSkuController;
import com.cn.taihe.back.product.dto.ProductSkuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSkuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSkuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSku;
import com.cn.taihe.back.product.service.ProductSkuService;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 商品SKU Controller单元测试
 */
@ExtendWith(MockitoExtension.class)
class ProductSkuControllerTest {

  @Mock
  private ProductSkuService productSkuService;

  @InjectMocks
  private ProductSkuController productSkuController;

  private ProductSku testProductSku;
  private ProductSkuCreateDTO testCreateDTO;
  private ProductSkuUpdateDTO testUpdateDTO;
  private ProductSkuQueryDTO testQueryDTO;

  @BeforeEach
  void setUp() {
    // 初始化测试数据
    testProductSku = new ProductSku();
    testProductSku.setId("test-id-123");
    testProductSku.setSkuCode("TEST-SKU-001");
    testProductSku.setSkuNameEn("Test Product");
    testProductSku.setSkuNameZh("测试商品");
    testProductSku.setSpuId("test-spu-001");
    testProductSku.setPrimaryMaterial("Gold");
    testProductSku.setTotalWeightG(new BigDecimal("10.50"));
    testProductSku.setCostPrice(new BigDecimal("100.00"));
    testProductSku.setRetailPrice(new BigDecimal("200.00"));
    testProductSku.setStatus(1);
    testProductSku.setIsAvailable(true);
    testProductSku.setCreatedBy(1L);
    testProductSku.setUpdatedBy(1L);
    testProductSku.setCreatedTime(LocalDateTime.now());
    testProductSku.setUpdatedTime(LocalDateTime.now());

    testCreateDTO = new ProductSkuCreateDTO();
    testCreateDTO.setSkuCode("TEST-SKU-001");
    testCreateDTO.setSkuNameEn("Test Product");
    testCreateDTO.setSkuNameZh("测试商品");
    testCreateDTO.setSpuId("test-spu-001");
    testCreateDTO.setPrimaryMaterial("Gold");
    testCreateDTO.setTotalWeightG(new BigDecimal("10.50"));
    testCreateDTO.setCostPrice(new BigDecimal("100.00"));
    testCreateDTO.setRetailPrice(new BigDecimal("200.00"));
    testCreateDTO.setCreatedBy(1L);
    testCreateDTO.setUpdatedBy(1L);

    testUpdateDTO = new ProductSkuUpdateDTO();
    testUpdateDTO.setId("test-id-123");
    testUpdateDTO.setSkuCode("TEST-SKU-001");
    testUpdateDTO.setSkuNameEn("Updated Product");
    testUpdateDTO.setSkuNameZh("更新后的商品");
    testUpdateDTO.setRetailPrice(new BigDecimal("250.00"));
    testUpdateDTO.setUpdatedBy(2L);

    testQueryDTO = new ProductSkuQueryDTO();
    testQueryDTO.setSkuCode("TEST");
    testQueryDTO.setStatus(1);
  }

  @Test
  void getById_ShouldReturnProductSku_WhenExists() {
    // Arrange
    when(productSkuService.findById("test-id-123")).thenReturn(testProductSku);

    // Act
    ResponseEntity<Object> response = productSkuController.getById("test-id-123");

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof ProductSku);

    ProductSku result = (ProductSku) response.getBody();
    assertEquals("test-id-123", result.getId());
    assertEquals("TEST-SKU-001", result.getSkuCode());

    verify(productSkuService, times(1)).findById("test-id-123");
  }

  @Test
  void getById_ShouldReturnNotFound_WhenNotExists() {
    // Arrange
    when(productSkuService.findById("non-existent-id")).thenReturn(null);

    // Act
    ResponseEntity<Object> response = productSkuController.getById("non-existent-id");

    // Assert
    assertEquals(404, response.getStatusCodeValue());
    verify(productSkuService, times(1)).findById("non-existent-id");
  }

  @Test
  void getById_ShouldReturnError_WhenExceptionOccurs() {
    // Arrange
    when(productSkuService.findById("test-id-123")).thenThrow(new RuntimeException("Database error"));

    // Act
    ResponseEntity<Object> response = productSkuController.getById("test-id-123");

    // Assert
    assertEquals(400, response.getStatusCodeValue());
    assertTrue(response.getBody() instanceof Result);
    verify(productSkuService, times(1)).findById("test-id-123");
  }

  @Test
  void create_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    when(productSkuService.create(any(ProductSkuCreateDTO.class))).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.create(testCreateDTO);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).create(testCreateDTO);
  }

  @Test
  void create_ShouldReturnFalse_WhenSkuCodeExists() {
    // Arrange
    when(productSkuService.create(any(ProductSkuCreateDTO.class))).thenReturn(false);

    // Act
    ResponseEntity<Object> response = productSkuController.create(testCreateDTO);

    // Assert
    assertEquals(400, response.getStatusCodeValue());
    assertEquals(false, response.getBody());
    verify(productSkuService, times(1)).create(testCreateDTO);
  }

  @Test
  void create_ShouldReturnError_WhenExceptionOccurs() {
    // Arrange
    when(productSkuService.create(any(ProductSkuCreateDTO.class))).thenThrow(new RuntimeException("Database error"));

    // Act
    ResponseEntity<Object> response = productSkuController.create(testCreateDTO);

    // Assert
    assertEquals(400, response.getStatusCodeValue());
    assertTrue(response.getBody() instanceof Result);
    verify(productSkuService, times(1)).create(testCreateDTO);
  }

  @Test
  void update_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    when(productSkuService.update(any(ProductSkuUpdateDTO.class))).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.update(testUpdateDTO);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).update(testUpdateDTO);
  }

  @Test
  void update_ShouldReturnFalse_WhenNotExists() {
    // Arrange
    when(productSkuService.update(any(ProductSkuUpdateDTO.class))).thenReturn(false);

    // Act
    ResponseEntity<Object> response = productSkuController.update(testUpdateDTO);

    // Assert
    assertEquals(400, response.getStatusCodeValue());
    assertEquals(false, response.getBody());
    verify(productSkuService, times(1)).update(testUpdateDTO);
  }

  @Test
  void deleteById_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    String testId = "test-id-123";
    when(productSkuService.deleteById(testId)).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.deleteById(testId);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).deleteById(testId);
  }

  @Test
  void queryByCondition_ShouldReturnPageInfo_WhenSuccess() {
    // Arrange
    PageInfo<ProductSku> pageInfo = new PageInfo<>(Collections.singletonList(testProductSku));
    when(productSkuService.findByCondition(any(ProductSkuQueryDTO.class), eq(1), eq(10)))
      .thenReturn(pageInfo);

    // Act
    ResponseEntity<Object> response = productSkuController.queryByCondition(testQueryDTO, 1, 10);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody() instanceof PageInfo);
    verify(productSkuService, times(1)).findByCondition(testQueryDTO, 1, 10);
  }

  @Test
  void getAll_ShouldReturnList_WhenSuccess() {
    // Arrange
    List<ProductSku> productList = Collections.singletonList(testProductSku);
    when(productSkuService.findAll()).thenReturn(productList);

    // Act
    ResponseEntity<Object> response = productSkuController.getAll();

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody() instanceof List);
    verify(productSkuService, times(1)).findAll();
  }

  @Test
  void batchDelete_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    when(productSkuService.deleteByIds(ids)).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.batchDelete(ids);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).deleteByIds(ids);
  }

  @Test
  void updateStatus_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    when(productSkuService.updateStatus("test-id-123", 1, 2L)).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.updateStatus("test-id-123", 1, 2L);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).updateStatus("test-id-123", 1, 2L);
  }

  @Test
  void updateIsAvailable_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    when(productSkuService.updateIsAvailable("test-id-123", false, 2L)).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.updateIsAvailable("test-id-123", false, 2L);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).updateIsAvailable("test-id-123", false, 2L);
  }

  @Test
  void batchUpdateStatus_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    List<String> ids = Arrays.asList("id1", "id2");
    when(productSkuService.batchUpdateStatus(ids, 2, 2L)).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.batchUpdateStatus(ids, 2, 2L);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).batchUpdateStatus(ids, 2, 2L);
  }

  @Test
  void batchUpdateIsAvailable_ShouldReturnTrue_WhenSuccess() {
    // Arrange
    List<String> ids = Arrays.asList("id1", "id2");
    when(productSkuService.batchUpdateIsAvailable(ids, true, 2L)).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.batchUpdateIsAvailable(ids, true, 2L);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).batchUpdateIsAvailable(ids, true, 2L);
  }

  @Test
  void getBySpuId_ShouldReturnList_WhenSuccess() {
    // Arrange
    List<ProductSku> productList = Collections.singletonList(testProductSku);
    when(productSkuService.findBySpuId("test-spu-001")).thenReturn(productList);

    // Act
    ResponseEntity<Object> response = productSkuController.getBySpuId("test-spu-001");

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody() instanceof List);
    verify(productSkuService, times(1)).findBySpuId("test-spu-001");
  }

  @Test
  void getBySkuCode_ShouldReturnProductSku_WhenExists() {
    // Arrange
    when(productSkuService.findBySkuCode("TEST-SKU-001")).thenReturn(testProductSku);

    // Act
    ResponseEntity<Object> response = productSkuController.getBySkuCode("TEST-SKU-001");

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody() instanceof ProductSku);
    verify(productSkuService, times(1)).findBySkuCode("TEST-SKU-001");
  }

  @Test
  void getBySkuCode_ShouldReturnNotFound_WhenNotExists() {
    // Arrange
    when(productSkuService.findBySkuCode("NON-EXISTENT")).thenReturn(null);

    // Act
    ResponseEntity<Object> response = productSkuController.getBySkuCode("NON-EXISTENT");

    // Assert
    assertEquals(404, response.getStatusCodeValue());
    verify(productSkuService, times(1)).findBySkuCode("NON-EXISTENT");
  }

  @Test
  void checkSkuCodeExists_ShouldReturnBoolean_WhenSuccess() {
    // Arrange
    when(productSkuService.isSkuCodeExists("TEST-SKU-001", null)).thenReturn(true);

    // Act
    ResponseEntity<Object> response = productSkuController.checkSkuCodeExists("TEST-SKU-001", null);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(true, response.getBody());
    verify(productSkuService, times(1)).isSkuCodeExists("TEST-SKU-001", null);
  }

  @Test
  void checkSkuCodeExists_ShouldReturnError_WhenExceptionOccurs() {
    // Arrange
    when(productSkuService.isSkuCodeExists("TEST-SKU-001", null))
      .thenThrow(new RuntimeException("Database error"));

    // Act
    ResponseEntity<Object> response = productSkuController.checkSkuCodeExists("TEST-SKU-001", null);

    // Assert
    assertEquals(400, response.getStatusCodeValue());
    assertTrue(response.getBody() instanceof Result);
    verify(productSkuService, times(1)).isSkuCodeExists("TEST-SKU-001", null);
  }
}
