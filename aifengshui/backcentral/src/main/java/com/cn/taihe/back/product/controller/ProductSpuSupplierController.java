package com.cn.taihe.back.product.controller;

import com.cn.taihe.back.product.dto.CreateRelationsRequest;
import com.cn.taihe.back.product.entity.ProductSpuSupplier;
import com.cn.taihe.back.product.service.ProductSpuSupplierService;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * SPU与供应商关联关系表 Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/product-spu-supplier")
@Api(tags = "SPU-供应商关联关系管理")
public class ProductSpuSupplierController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuSupplierController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuSupplierService productSpuSupplierService;

  /**
   * 根据主键查找
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据主键查找SPU-供应商关系")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键查找请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuSupplier result = productSpuSupplierService.findById(id);

    logger.info("根据主键查找请求处理完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SPU ID查找
   */
  @GetMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID查找SPU-供应商关系")
  public ResponseEntity<Object> getBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID查找请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuSupplier> result = productSpuSupplierService.findBySpuId(spuId);

    logger.info("根据SPU ID查找请求处理完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据供应商ID查找
   */
  @GetMapping("/supplier/{supplierId}")
  @ApiOperation(value = "根据供应商ID查找SPU-供应商关系")
  public ResponseEntity<Object> getBySupplierId(
    @ApiParam(value = "供应商ID", required = true)
    @PathVariable String supplierId) {

    logger.info("开始处理根据供应商ID查找请求, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    List<ProductSpuSupplier> result = productSpuSupplierService.findBySupplierId(supplierId);

    logger.info("根据供应商ID查找请求处理完成, 操作人: {}, 参数: supplierId={}, 结果数量: {}",
      OPERATOR, supplierId, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 条件查询数据
   */
  @PostMapping("/condition")
  @ApiOperation(value = "条件查询SPU-供应商关系")
  public ResponseEntity<Object> getByCondition(
    @ApiParam(value = "查询条件", required = true)
    @RequestBody ProductSpuSupplier productSpuSupplier) {

    logger.info("开始处理条件查询请求, 操作人: {}, 参数: {}", OPERATOR, productSpuSupplier);

    List<ProductSpuSupplier> result = productSpuSupplierService.findByCondition(productSpuSupplier);

    logger.info("条件查询请求处理完成, 操作人: {}, 参数: {}, 结果数量: {}",
      OPERATOR, productSpuSupplier, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 条件分页查询数据
   */
  @PostMapping("/condition/page")
  @ApiOperation(value = "条件分页查询SPU-供应商关系")
  public ResponseEntity<Object> getByConditionWithPage(
    @ApiParam(value = "查询条件", required = true)
    @RequestBody ProductSpuSupplier productSpuSupplier,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {

    logger.info("开始处理条件分页查询请求, 操作人: {}, 参数: {}, page={}, size={}",
      OPERATOR, productSpuSupplier, page, size);

    PageInfo<ProductSpuSupplier> result = productSpuSupplierService.findByConditionWithPage(productSpuSupplier, page, size);

    logger.info("条件分页查询请求处理完成, 操作人: {}, 参数: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, productSpuSupplier, page, size, result.getTotal());

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 查询所有数据
   */
  @GetMapping("/all")
  @ApiOperation(value = "查询所有SPU-供应商关系")
  public ResponseEntity<Object> getAll() {

    logger.info("开始处理查询所有请求, 操作人: {}", OPERATOR);

    List<ProductSpuSupplier> result = productSpuSupplierService.findAll();

    logger.info("查询所有请求处理完成, 操作人: {}, 结果数量: {}",
      OPERATOR, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 分页查询所有数据
   */
  @GetMapping("/all/page")
  @ApiOperation(value = "分页查询所有SPU-供应商关系")
  public ResponseEntity<Object> getAllWithPage(
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {

    logger.info("开始处理分页查询所有请求, 操作人: {}, page={}, size={}", OPERATOR, page, size);

    PageInfo<ProductSpuSupplier> result = productSpuSupplierService.findAllWithPage(page, size);

    logger.info("分页查询所有请求处理完成, 操作人: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, page, size, result.getTotal());

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 新增数据
   */
  @PostMapping
  @ApiOperation(value = "新增SPU-供应商关系")
  public ResponseEntity<Object> create(
    @ApiParam(value = "SPU-供应商关系信息", required = true)
    @RequestBody ProductSpuSupplier productSpuSupplier) {

    logger.info("开始处理新增SPU-供应商关系请求, 操作人: {}, 参数: {}", OPERATOR, productSpuSupplier);

    int result = productSpuSupplierService.save(productSpuSupplier);

    logger.info("新增SPU-供应商关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuSupplier, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 创建SPU与供应商关联关系（先删除后新增）
   */
  @PostMapping("/relations/{spuId}")
  @ApiOperation(value = "创建SPU与供应商关联关系")
  public ResponseEntity<Object> createRelations(
    @RequestBody @Valid CreateRelationsRequest request) {

    logger.info("开始处理创建SPU-供应商关联关系请求, 操作人: {}, spuId: {}, supplierIds数量: {}",
      OPERATOR, request.getSpuId(), request.getIntentIds() != null ? request.getIntentIds().size() : 0);

    int result = productSpuSupplierService.createRealiations(request.getSpuId(), request.getIntentIds());

    logger.info("创建SPU-供应商关联关系请求处理完成, 操作人: {}, spuId: {}, supplierIds数量: {}, 影响行数: {}",
      OPERATOR, request.getSpuId(), request.getIntentIds() != null ? request.getIntentIds().size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 修改数据
   */
  @PutMapping
  @ApiOperation(value = "修改SPU-供应商关系")
  public ResponseEntity<Object> update(
    @ApiParam(value = "SPU-供应商关系信息", required = true)
    @RequestBody ProductSpuSupplier productSpuSupplier) {

    logger.info("开始处理修改SPU-供应商关系请求, 操作人: {}, 参数: {}", OPERATOR, productSpuSupplier);

    int result = productSpuSupplierService.update(productSpuSupplier);

    logger.info("修改SPU-供应商关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuSupplier, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据主键删除SPU-供应商关系")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键删除请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuSupplierService.deleteById(id);

    logger.info("根据主键删除请求处理完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键集合批量删除
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据主键集合批量删除SPU-供应商关系")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("开始处理批量删除请求, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuSupplierService.deleteBatchIds(ids);

    logger.info("批量删除请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SPU ID批量删除
   */
  @DeleteMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID批量删除SPU-供应商关系")
  public ResponseEntity<Object> deleteBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID批量删除请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuSupplierService.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除请求处理完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);

    return ResponseEntity.ok(Result.success(result));
  }
  /**
   * 根据供应商ID批量删除
   */
  @DeleteMapping("/supplier/{supplierId}")
  @ApiOperation(value = "根据供应商ID批量删除SPU-供应商关系")
  public ResponseEntity<Object> deleteBySupplierId(
    @ApiParam(value = "供应商ID", required = true)
    @PathVariable String supplierId) {

    logger.info("开始处理根据供应商ID批量删除请求, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    int result = productSpuSupplierService.deleteBySupplierId(supplierId);

    logger.info("根据供应商ID批量删除请求处理完成, 操作人: {}, 参数: supplierId={}, 影响行数: {}",
      OPERATOR, supplierId, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 检查SPU和供应商关联是否存在
   */
  @GetMapping("/check-exists")
  @ApiOperation(value = "检查SPU和供应商关联是否存在")
  public ResponseEntity<Object> checkExists(
    @ApiParam(value = "SPU ID", required = true)
    @RequestParam String spuId,
    @ApiParam(value = "供应商ID", required = true)
    @RequestParam String supplierId) {

    logger.info("开始处理检查关联是否存在请求, 操作人: {}, spuId: {}, supplierId: {}",
      OPERATOR, spuId, supplierId);

    boolean result = productSpuSupplierService.checkExists(spuId, supplierId);

    logger.info("检查关联是否存在请求处理完成, 操作人: {}, spuId: {}, supplierId: {}, 结果: {}",
      OPERATOR, spuId, supplierId, result);

    return ResponseEntity.ok(Result.success(result));
  }
}
