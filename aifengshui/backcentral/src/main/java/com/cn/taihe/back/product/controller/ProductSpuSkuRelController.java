package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.entity.ProductSpuSkuRel;
import com.cn.taihe.back.product.service.ProductSpuSkuRelService;
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
import java.util.List;

/**
 * SPU-SKU关联关系表 Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/product-spu-sku-rel")
@Api(tags = "SPU-SKU关联关系管理")
public class ProductSpuSkuRelController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuSkuRelController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuSkuRelService productSpuSkuRelService;

  /**
   * 根据主键查找
   */
  @GetMapping("/getByid/{id}")
  @ApiOperation(value = "根据主键查找SPU-SKU关系")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键查找请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuSkuRel result = productSpuSkuRelService.findById(id);

    logger.info("根据主键查找请求处理完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SPU ID查找
   */
  @GetMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID查找SPU-SKU关系")
  public ResponseEntity<Object> getBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID查找请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuSkuRel> result = productSpuSkuRelService.findBySpuId(spuId);

    logger.info("根据SPU ID查找请求处理完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SKU ID查找
   */
  @GetMapping("/sku/{skuId}")
  @ApiOperation(value = "根据SKU ID查找SPU-SKU关系")
  public ResponseEntity<Object> getBySkuId(
    @ApiParam(value = "SKU ID", required = true)
    @PathVariable String skuId) {

    logger.info("开始处理根据SKU ID查找请求, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    List<ProductSpuSkuRel> result = productSpuSkuRelService.findBySkuId(skuId);

    logger.info("根据SKU ID查找请求处理完成, 操作人: {}, 参数: skuId={}, 结果数量: {}",
      OPERATOR, skuId, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 条件查询数据
   */
  @PostMapping("/condition")
  @ApiOperation(value = "条件查询SPU-SKU关系")
  public ResponseEntity<Object> getByCondition(
    @ApiParam(value = "查询条件", required = true)
    @RequestBody ProductSpuSkuRel productSpuSkuRel) {

    logger.info("开始处理条件查询请求, 操作人: {}, 参数: {}", OPERATOR, productSpuSkuRel);

    List<ProductSpuSkuRel> result = productSpuSkuRelService.findByCondition(productSpuSkuRel);

    logger.info("条件查询请求处理完成, 操作人: {}, 参数: {}, 结果数量: {}",
      OPERATOR, productSpuSkuRel, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 条件分页查询数据
   */
  @PostMapping("/condition/page")
  @ApiOperation(value = "条件分页查询SPU-SKU关系")
  public ResponseEntity<Object> getByConditionWithPage(
    @ApiParam(value = "查询条件", required = true)
    @RequestBody ProductSpuSkuRel productSpuSkuRel,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {

    logger.info("开始处理条件分页查询请求, 操作人: {}, 参数: {}, page={}, size={}",
      OPERATOR, productSpuSkuRel, page, size);

    PageInfo<ProductSpuSkuRel> result = productSpuSkuRelService.findByConditionWithPage(productSpuSkuRel, page, size);

    logger.info("条件分页查询请求处理完成, 操作人: {}, 参数: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, productSpuSkuRel, page, size, result.getTotal());

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 查询所有数据
   */
  @GetMapping("/all")
  @ApiOperation(value = "查询所有SPU-SKU关系")
  public ResponseEntity<Object> getAll() {

    logger.info("开始处理查询所有请求, 操作人: {}", OPERATOR);

    List<ProductSpuSkuRel> result = productSpuSkuRelService.findAll();

    logger.info("查询所有请求处理完成, 操作人: {}, 结果数量: {}",
      OPERATOR, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 分页查询所有数据
   */
  @GetMapping("/all/page")
  @ApiOperation(value = "分页查询所有SPU-SKU关系")
  public ResponseEntity<Object> getAllWithPage(
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {

    logger.info("开始处理分页查询所有请求, 操作人: {}, page={}, size={}", OPERATOR, page, size);

    PageInfo<ProductSpuSkuRel> result = productSpuSkuRelService.findAllWithPage(page, size);

    logger.info("分页查询所有请求处理完成, 操作人: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, page, size, result.getTotal());

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 新增数据
   */
  @PostMapping
  @ApiOperation(value = "新增SPU-SKU关系")
  public ResponseEntity<Object> create(
    @ApiParam(value = "SPU-SKU关系信息", required = true)
    @RequestBody ProductSpuSkuRel productSpuSkuRel) {

    logger.info("开始处理新增SPU-SKU关系请求, 操作人: {}, 参数: {}", OPERATOR, productSpuSkuRel);

    int result = productSpuSkuRelService.save(productSpuSkuRel);
    logger.info("新增SPU-SKU关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuSkuRel, result);
    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 批量新增数据
   */
  @PostMapping("/batch")
  @ApiOperation(value = "批量新增SPU-SKU关系")
  public ResponseEntity<Object> createBatch(
    @ApiParam(value = "SPU-SKU关系信息列表", required = true)
    @RequestBody List<ProductSpuSkuRel> productSpuSkuRelList) {

    logger.info("开始处理批量新增SPU-SKU关系请求, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuSkuRelList != null ? productSpuSkuRelList.size() : 0);

    int result = productSpuSkuRelService.saveBatch(productSpuSkuRelList);

    logger.info("批量新增SPU-SKU关系请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuSkuRelList != null ? productSpuSkuRelList.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 创建SPU与SKU关联关系（先删除后新增）
   */
  @PostMapping("/relations/{spuId}")
  @ApiOperation(value = "创建SPU与SKU关联关系")
  public ResponseEntity<Object> createRelations(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId,
    @ApiParam(value = "SKU ID列表", required = true)
    @RequestBody List<String> skuIds) {

    logger.info("开始处理创建SPU-SKU关联关系请求, 操作人: {}, spuId: {}, skuIds数量: {}",
      OPERATOR, spuId, skuIds != null ? skuIds.size() : 0);

    int result = productSpuSkuRelService.createRealiations(spuId, skuIds);

    logger.info("创建SPU-SKU关联关系请求处理完成, 操作人: {}, spuId: {}, skuIds数量: {}, 影响行数: {}",
      OPERATOR, spuId, skuIds != null ? skuIds.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据主键删除SPU-SKU关系")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键删除请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuSkuRelService.deleteById(id);

    logger.info("根据主键删除请求处理完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键集合批量删除
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据主键集合批量删除SPU-SKU关系")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("开始处理批量删除请求, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuSkuRelService.deleteBatchIds(ids);

    logger.info("批量删除请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SPU ID批量删除
   */
  @DeleteMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID批量删除SPU-SKU关系")
  public ResponseEntity<Object> deleteBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID批量删除请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuSkuRelService.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除请求处理完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SKU ID批量删除
   */
  @DeleteMapping("/sku/{skuId}")
  @ApiOperation(value = "根据SKU ID批量删除SPU-SKU关系")
  public ResponseEntity<Object> deleteBySkuId(
    @ApiParam(value = "SKU ID", required = true)
    @PathVariable String skuId) {

    logger.info("开始处理根据SKU ID批量删除请求, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    int result = productSpuSkuRelService.deleteBySkuId(skuId);

    logger.info("根据SKU ID批量删除请求处理完成, 操作人: {}, 参数: skuId={}, 影响行数: {}",
      OPERATOR, skuId, result);

    return ResponseEntity.ok(Result.success(result));
  }
}
