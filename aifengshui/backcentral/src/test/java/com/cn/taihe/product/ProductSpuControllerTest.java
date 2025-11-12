package com.cn.taihe.product;

import com.cn.taihe.back.product.controller.ProductSpuController;
import com.cn.taihe.back.product.dto.ProductSpuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSpuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSpuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSpu;
import com.cn.taihe.back.product.service.ProductSpuService;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * ProductSpuController单元测试
 */
@ExtendWith(MockitoExtension.class)
class ProductSpuControllerTest {

  @Mock
  private ProductSpuService productSpuService;

  @InjectMocks
  private ProductSpuController productSpuController;

  private ProductSpu mockProductSpu;
  private ProductSpuCreateDTO mockCreateDTO;
  private ProductSpuUpdateDTO mockUpdateDTO;
  private ProductSpuQueryDTO mockQueryDTO;

  @BeforeEach
  void setUp() {
    // 初始化测试数据
    mockProductSpu = new ProductSpu();
    mockProductSpu.setId("test-id-123");
    mockProductSpu.setSpuCode("SPU001");
    mockProductSpu.setProductNameEn("Test Product EN");
    mockProductSpu.setProductNameZh("测试产品中文");
    mockProductSpu.setIsActive(true);
    mockProductSpu.setCreatedTime(LocalDateTime.now());
    mockProductSpu.setUpdatedTime(LocalDateTime.now());

    mockCreateDTO = new ProductSpuCreateDTO();
    mockCreateDTO.setSpuCode("SPU001");
    mockCreateDTO.setProductNameEn("Test Product EN");
    mockCreateDTO.setProductNameZh("测试产品中文");
    mockCreateDTO.setCreatedBy(1L);
    mockCreateDTO.setUpdatedBy(1L);

    mockUpdateDTO = new ProductSpuUpdateDTO();
    mockUpdateDTO.setId("test-id-123");
    mockUpdateDTO.setSpuCode("SPU001-UPDATED");
    mockUpdateDTO.setProductNameEn("Updated Product EN");
    mockUpdateDTO.setUpdatedBy(1L);

    mockQueryDTO = new ProductSpuQueryDTO();
    mockQueryDTO.setSpuCode("SPU001");
    mockQueryDTO.setIsActive(true);
  }

  @Test
  void getById_Success() {
    // 准备
    String id = "test-id-123";
    when(productSpuService.getById(id)).thenReturn(mockProductSpu);

    // 执行
    ResponseEntity<Object> response = productSpuController.getById(id);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("查询成功", result.get("message"));
    assertNotNull(result.get("data"));

    verify(productSpuService, times(1)).getById(id);
  }

  @Test
  void getById_EmptyId() {
    // 准备
    String id = "";

    // 执行
    ResponseEntity<Object> response = productSpuController.getById(id);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("主键ID不能为空", result.get("message"));

    verify(productSpuService, never()).getById(anyString());
  }

  @Test
  void getById_NotFound() {
    // 准备
    String id = "non-existent-id";
    when(productSpuService.getById(id)).thenReturn(null);

    // 执行
    ResponseEntity<Object> response = productSpuController.getById(id);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("商品SPU不存在", result.get("message"));

    verify(productSpuService, times(1)).getById(id);
  }
//
//  @Test
//  void create_Success() {
//    // 准备
//    when(productSpuService.create(mockCreateDTO)).thenReturn(true);
//
//    // 执行
//    ResponseEntity<Object> response = productSpuController.create(mockCreateDTO);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertTrue((Boolean) result.get("success"));
//    assertEquals("新增成功", result.get("message"));
//
//    verify(productSpuService, times(1)).create(mockCreateDTO);
//  }
//
//  @Test
//  void create_NullDTO() {
//    // 执行
//    ResponseEntity<Object> response = productSpuController.create(null);
//
//    // 验证
//    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertFalse((Boolean) result.get("success"));
//    assertEquals("请求参数不能为空", result.get("message"));
//
//    verify(productSpuService, never()).create(any());
//  }
//
//  @Test
//  void create_Failure() {
//    // 准备
//    when(productSpuService.create(mockCreateDTO)).thenReturn(false);
//
//    // 执行
//    ResponseEntity<Object> response = productSpuController.create(mockCreateDTO);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertFalse((Boolean) result.get("success"));
//    assertEquals("新增失败", result.get("message"));
//
//    verify(productSpuService, times(1)).create(mockCreateDTO);
//  }
//
//  @Test
//  void update_Success() {
//    // 准备
//    when(productSpuService.update(mockUpdateDTO)).thenReturn(true);
//
//    // 执行
//    ResponseEntity<Object> response = productSpuController.update(mockUpdateDTO);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertTrue((Boolean) result.get("success"));
//    assertEquals("修改成功", result.get("message"));
//
//    verify(productSpuService, times(1)).update(mockUpdateDTO);
//  }
//
//  @Test
//  void update_NullDTO() {
//    // 执行
//    ResponseEntity<Object> response = productSpuController.update(null);
//
//    // 验证
//    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertFalse((Boolean) result.get("success"));
//    assertEquals("请求参数或主键ID不能为空", result.get("message"));
//
//    verify(productSpuService, never()).update(any());
//  }
//
//  @Test
//  void update_EmptyId() {
//    // 准备
//    ProductSpuUpdateDTO dto = new ProductSpuUpdateDTO();
//    dto.setId("");
//
//    // 执行
//    ResponseEntity<Object> response = productSpuController.update(dto);
//
//    // 验证
//    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertFalse((Boolean) result.get("success"));
//    assertEquals("请求参数或主键ID不能为空", result.get("message"));
//
//    verify(productSpuService, never()).update(any());
//  }

  @Test
  void deleteById_Success() {
    // 准备
    String id = "test-id-123";
    when(productSpuService.deleteById(id)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productSpuController.deleteById(id);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("删除成功", result.get("message"));

    verify(productSpuService, times(1)).deleteById(id);
  }

  @Test
  void deleteById_EmptyId() {
    // 执行
    ResponseEntity<Object> response = productSpuController.deleteById("");

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("主键ID不能为空", result.get("message"));

    verify(productSpuService, never()).deleteById(anyString());
  }

//  @Test
//  void getByCondition_Success() {
//    // 准备
//    PageInfo<ProductSpu> pageInfo = new PageInfo<>();
//    pageInfo.setList(Arrays.asList(mockProductSpu));
//    pageInfo.setTotal(1L);
//    pageInfo.setPageNum(1);
//    pageInfo.setPageSize(10);
//    pageInfo.setPages(1);
//
//    when(productSpuService.getByCondition(any(ProductSpuQueryDTO.class), eq(1), eq(10)))
//      .thenReturn(pageInfo);
//
//    // 执行
//    ResponseEntity<Object> response = productSpuController.getByCondition(mockQueryDTO, 1, 10);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertTrue((Boolean) result.get("success"));
//    assertEquals("查询成功", result.get("message"));
//    assertNotNull(result.get("data"));
//    assertEquals(1L, result.get("total"));
//
//    verify(productSpuService, times(1)).getByCondition(any(ProductSpuQueryDTO.class), eq(1), eq(10));
//  }

//  @Test
//  void getByCondition_NullQueryDTO() {
//    // 准备
//    PageInfo<ProductSpu> pageInfo = new PageInfo<>();
//    pageInfo.setList(Arrays.asList(mockProductSpu));
//    pageInfo.setTotal(1L);
//
//    when(productSpuService.getByCondition(any(ProductSpuQueryDTO.class), eq(1), eq(10)))
//      .thenReturn(pageInfo);
//
//    // 执行
//    ResponseEntity<Object> response = productSpuController.getByCondition(null, 1, 10);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    Map<String, Object> result = (Map<String, Object>) response.getBody();
//    assertTrue((Boolean) result.get("success"));
//
//    verify(productSpuService, times(1)).getByCondition(any(ProductSpuQueryDTO.class), eq(1), eq(10));
//  }
//
//  @Test
//  void getByCondition_DefaultPagination() {
//    // 准备
//    PageInfo<ProductSpu> pageInfo = new PageInfo<>();
//    pageInfo.setList(Arrays.asList(mockProductSpu));
//    pageInfo.setTotal(1L);
//    pageInfo.setPageNum(1);
//    pageInfo.setPageSize(10);
//    pageInfo.setPages(1);
//
//    // 修正：匹配实际的null参数调用
//    when(productSpuService.getByCondition(
//      any(ProductSpuQueryDTO.class),  // 匹配任何QueryDTO对象
//      isNull(),                        // 匹配null的page参数
//      isNull()                         // 匹配null的size参数
//    )).thenReturn(pageInfo);
//
//    // 执行 - 使用null值测试默认分页
//    ResponseEntity<Object> response = productSpuController.getByCondition(mockQueryDTO, null, null);
//
//    // 验证
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    // 验证服务层被正确调用（匹配实际的null参数）
//    verify(productSpuService, times(1)).getByCondition(
//      any(ProductSpuQueryDTO.class),
//      isNull(),
//      isNull()
//    );
//  }

  @Test
  void getAll_Success() {
    // 准备
    List<ProductSpu> productList = Arrays.asList(mockProductSpu);
    when(productSpuService.getAll()).thenReturn(productList);

    // 执行
    ResponseEntity<Object> response = productSpuController.getAll();

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("查询成功", result.get("message"));
    assertNotNull(result.get("data"));
    assertEquals(1, result.get("total"));

    verify(productSpuService, times(1)).getAll();
  }

  @Test
  void deleteBatchIds_Success() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    when(productSpuService.deleteBatchIds(ids)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productSpuController.deleteBatchIds(ids);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("批量删除成功", result.get("message"));

    verify(productSpuService, times(1)).deleteBatchIds(ids);
  }

  @Test
  void deleteBatchIds_EmptyList() {
    // 准备
    List<String> ids = Arrays.asList();

    // 执行
    ResponseEntity<Object> response = productSpuController.deleteBatchIds(ids);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("主键ID集合不能为空", result.get("message"));

    verify(productSpuService, never()).deleteBatchIds(anyList());
  }

  @Test
  void updateStatus_Success() {
    // 准备
    String id = "test-id-123";
    Boolean isActive = false;
    when(productSpuService.updateStatusById(id, isActive)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productSpuController.updateStatus(id, isActive);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("冻结成功", result.get("message"));

    verify(productSpuService, times(1)).updateStatusById(id, isActive);
  }

  @Test
  void updateStatus_EnableSuccess() {
    // 准备
    String id = "test-id-123";
    Boolean isActive = true;
    when(productSpuService.updateStatusById(id, isActive)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productSpuController.updateStatus(id, isActive);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("启用成功", result.get("message"));

    verify(productSpuService, times(1)).updateStatusById(id, isActive);
  }

  @Test
  void updateStatus_EmptyId() {
    // 执行
    ResponseEntity<Object> response = productSpuController.updateStatus("", true);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("主键ID和状态不能为空", result.get("message"));

    verify(productSpuService, never()).updateStatusById(anyString(), anyBoolean());
  }

  @Test
  void updateStatus_NullStatus() {
    // 执行
    ResponseEntity<Object> response = productSpuController.updateStatus("test-id", null);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("主键ID和状态不能为空", result.get("message"));

    verify(productSpuService, never()).updateStatusById(anyString(), anyBoolean());
  }

  @Test
  void updateStatusBatch_Success() {
    // 准备
    List<String> ids = Arrays.asList("id1", "id2");
    Boolean isActive = false;
    when(productSpuService.updateStatusBatchIds(ids, isActive)).thenReturn(true);

    // 执行
    ResponseEntity<Object> response = productSpuController.updateStatusBatch(ids, isActive);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("批量冻结成功", result.get("message"));

    verify(productSpuService, times(1)).updateStatusBatchIds(ids, isActive);
  }

  @Test
  void updateStatusBatch_EmptyList() {
    // 准备
    List<String> ids = Arrays.asList();

    // 执行
    ResponseEntity<Object> response = productSpuController.updateStatusBatch(ids, true);

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("主键ID集合和状态不能为空", result.get("message"));

    verify(productSpuService, never()).updateStatusBatchIds(anyList(), anyBoolean());
  }

  @Test
  void getBySpuCode_Success() {
    // 准备
    String spuCode = "SPU001";
    when(productSpuService.getBySpuCode(spuCode)).thenReturn(mockProductSpu);

    // 执行
    ResponseEntity<Object> response = productSpuController.getBySpuCode(spuCode);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertTrue((Boolean) result.get("success"));
    assertEquals("查询成功", result.get("message"));
    assertNotNull(result.get("data"));

    verify(productSpuService, times(1)).getBySpuCode(spuCode);
  }

  @Test
  void getBySpuCode_EmptyCode() {
    // 执行
    ResponseEntity<Object> response = productSpuController.getBySpuCode("");

    // 验证
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("SPU编码不能为空", result.get("message"));

    verify(productSpuService, never()).getBySpuCode(anyString());
  }

  @Test
  void getBySpuCode_NotFound() {
    // 准备
    String spuCode = "NON-EXISTENT";
    when(productSpuService.getBySpuCode(spuCode)).thenReturn(null);

    // 执行
    ResponseEntity<Object> response = productSpuController.getBySpuCode(spuCode);

    // 验证
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Map<String, Object> result = (Map<String, Object>) response.getBody();
    assertFalse((Boolean) result.get("success"));
    assertEquals("商品SPU不存在", result.get("message"));

    verify(productSpuService, times(1)).getBySpuCode(spuCode);
  }

  @Test
  void testExceptionHandling() {
    // 准备 - 模拟Service层抛出异常
    String id = "test-id";
    when(productSpuService.getById(id)).thenThrow(new RuntimeException("Database connection failed"));

    // 执行
    ResponseEntity<Object> response = productSpuController.getById(id);

    // 验证 - 检查异常处理逻辑
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof Result);
  }
}
