package com.cn.taihe.back.product.controller;

import com.cn.taihe.back.product.entity.ProductSkuSupplier;
import com.cn.taihe.back.product.service.ProductSkuSupplierService;
import com.cn.taihe.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SKU与供应商关联关系表 Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/product-sku-supplier")
@Api(tags = "SKU-供应商关联关系管理")
public class ProductSkuSupplierController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSkuSupplierController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSkuSupplierService productSkuSupplierService;

  /**
   * 根据主键查找
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据主键查找SKU-供应商关系")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键查找请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSkuSupplier result = productSkuSupplierService.findById(id);

    logger.info("根据主键查找请求处理完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SKU ID查找
   */
  @GetMapping("/sku/{skuId}")
  @ApiOperation(value = "根据SKU ID查找SKU-供应商关系")
  public ResponseEntity<Object> getBySkuId(
    @ApiParam(value = "SKU ID", required = true)
    @PathVariable String skuId) {

    logger.info("开始处理根据SKU ID查找请求, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    List<ProductSkuSupplier> result = productSkuSupplierService.findBySkuId(skuId);

    logger.info("根据SKU ID查找请求处理完成, 操作人: {}, 参数: skuId={}, 结果数量: {}",
      OPERATOR, skuId, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据供应商ID查找
   */
  @GetMapping("/supplier/{supplierId}")
  @ApiOperation(value = "根据供应商ID查找SKU-供应商关系")
  public ResponseEntity<Object> getBySupplierId(
    @ApiParam(value = "供应商ID", required = true)
    @PathVariable String supplierId) {

    logger.info("开始处理根据供应商ID查找请求, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    List<ProductSkuSupplier> result = productSkuSupplierService.findBySupplierId(supplierId);

    logger.info("根据供应商ID查找请求处理完成, 操作人: {}, 参数: supplierId={}, 结果数量: {}",
      OPERATOR, supplierId, result != null ? result.size() : 0);
    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 查询所有数据
   */
  @GetMapping("/all")
  @ApiOperation(value = "查询所有SKU-供应商关系")
  public ResponseEntity<Object> getAll() {

    logger.info("开始处理查询所有请求, 操作人: {}", OPERATOR);

    List<ProductSkuSupplier> result = productSkuSupplierService.findAll();

    logger.info("查询所有请求处理完成, 操作人: {}, 结果数量: {}",
      OPERATOR, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 新增数据
   */
  @PostMapping
  @ApiOperation(value = "新增SKU-供应商关系")
  public ResponseEntity<Object> create(
    @ApiParam(value = "SKU-供应商关系信息", required = true)
    @RequestBody ProductSkuSupplier productSkuSupplier) {

    logger.info("开始处理新增SKU-供应商关系请求, 操作人: {}, 参数: {}", OPERATOR, productSkuSupplier);

    int result = productSkuSupplierService.save(productSkuSupplier);

    logger.info("新增SKU-供应商关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSkuSupplier, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 批量新增数据
   */
  @PostMapping("/batch")
  @ApiOperation(value = "批量新增SKU-供应商关系")
  public ResponseEntity<Object> createBatch(
    @ApiParam(value = "SKU-供应商关系信息列表", required = true)
    @RequestBody List<ProductSkuSupplier> productSkuSupplierList) {

    logger.info("开始处理批量新增SKU-供应商关系请求, 操作人: {}, 参数数量: {}", OPERATOR,
      productSkuSupplierList != null ? productSkuSupplierList.size() : 0);

    int result = productSkuSupplierService.saveBatch(productSkuSupplierList);

    logger.info("批量新增SKU-供应商关系请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSkuSupplierList != null ? productSkuSupplierList.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 创建SKU与供应商关联关系（先删除后新增）
   */
  @PostMapping("/relations/{skuId}")
  @ApiOperation(value = "创建SKU与供应商关联关系")
  public ResponseEntity<Object> createRelations(
    @ApiParam(value = "SKU ID", required = true)
    @PathVariable String skuId,
    @ApiParam(value = "供应商ID列表", required = true)
    @RequestBody List<String> supplierIds) {

    logger.info("开始处理创建SKU-供应商关联关系请求, 操作人: {}, skuId: {}, supplierIds数量: {}",
      OPERATOR, skuId, supplierIds != null ? supplierIds.size() : 0);

    int result = productSkuSupplierService.createRealiations(skuId, supplierIds);

    logger.info("创建SKU-供应商关联关系请求处理完成, 操作人: {}, skuId: {}, supplierIds数量: {}, 影响行数: {}",
      OPERATOR, skuId, supplierIds != null ? supplierIds.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 修改数据
   */
  @PutMapping
  @ApiOperation(value = "修改SKU-供应商关系")
  public ResponseEntity<Object> update(
    @ApiParam(value = "SKU-供应商关系信息", required = true)
    @RequestBody ProductSkuSupplier productSkuSupplier) {

    logger.info("开始处理修改SKU-供应商关系请求, 操作人: {}, 参数: {}", OPERATOR, productSkuSupplier);

    int result = productSkuSupplierService.update(productSkuSupplier);

    logger.info("修改SKU-供应商关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSkuSupplier, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据主键删除SKU-供应商关系")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键删除请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSkuSupplierService.deleteById(id);

    logger.info("根据主键删除请求处理完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键集合批量删除
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据主键集合批量删除SKU-供应商关系")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("开始处理批量删除请求, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSkuSupplierService.deleteBatchIds(ids);

    logger.info("批量删除请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }
  /**
   * 根据SKU ID批量删除
   */
  @DeleteMapping("/sku/{skuId}")
  @ApiOperation(value = "根据SKU ID批量删除SKU-供应商关系")
  public ResponseEntity<Object> deleteBySkuId(
    @ApiParam(value = "SKU ID", required = true)
    @PathVariable String skuId) {

    logger.info("开始处理根据SKU ID批量删除请求, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    int result = productSkuSupplierService.deleteBySkuId(skuId);

    logger.info("根据SKU ID批量删除请求处理完成, 操作人: {}, 参数: skuId={}, 影响行数: {}",
      OPERATOR, skuId, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据供应商ID批量删除
   */
  @DeleteMapping("/supplier/{supplierId}")
  @ApiOperation(value = "根据供应商ID批量删除SKU-供应商关系")
  public ResponseEntity<Object> deleteBySupplierId(
    @ApiParam(value = "供应商ID", required = true)
    @PathVariable String supplierId) {

    logger.info("开始处理根据供应商ID批量删除请求, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    int result = productSkuSupplierService.deleteBySupplierId(supplierId);

    logger.info("根据供应商ID批量删除请求处理完成, 操作人: {}, 参数: supplierId={}, 影响行数: {}",
      OPERATOR, supplierId, result);

    return ResponseEntity.ok(Result.success(result));
  }
}
