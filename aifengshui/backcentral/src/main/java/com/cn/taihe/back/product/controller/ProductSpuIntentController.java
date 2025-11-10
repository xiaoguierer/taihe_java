package com.cn.taihe.back.product.controller;

import com.cn.taihe.back.product.entity.ProductSpuIntent;
import com.cn.taihe.back.product.service.ProductSpuIntentService;
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
 * SPU-情感意图关系表 Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/product-spu-intent")
@Api(tags = "SPU-情感意图关系管理")
public class ProductSpuIntentController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuIntentController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuIntentService productSpuIntentService;

  /**
   * 根据主键查找
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据主键查找SPU-情感意图关系")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键查找请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuIntent result = productSpuIntentService.findById(id);

    logger.info("根据主键查找请求处理完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");

    return ResponseEntity.ok(result);
  }

  /**
   * 根据SPU ID查找
   */
  @GetMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID查找SPU-情感意图关系")
  public ResponseEntity<Object> getBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID查找请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuIntent> result = productSpuIntentService.findBySpuId(spuId);

    logger.info("根据SPU ID查找请求处理完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据情感意图ID查找
   */
  @GetMapping("/intent/{intentId}")
  @ApiOperation(value = "根据情感意图ID查找SPU-情感意图关系")
  public ResponseEntity<Object> getByIntentId(
    @ApiParam(value = "情感意图ID", required = true)
    @PathVariable String intentId) {

    logger.info("开始处理根据情感意图ID查找请求, 操作人: {}, 参数: intentId={}", OPERATOR, intentId);

    List<ProductSpuIntent> result = productSpuIntentService.findByIntentId(intentId);

    logger.info("根据情感意图ID查找请求处理完成, 操作人: {}, 参数: intentId={}, 结果数量: {}",
      OPERATOR, intentId, result != null ? result.size() : 0);

    return ResponseEntity.ok(result);
  }

  /**
   * 新增数据
   */
  @PostMapping
  @ApiOperation(value = "新增SPU-情感意图关系")
  public ResponseEntity<Object> create(
    @ApiParam(value = "SPU-情感意图关系信息", required = true)
    @RequestBody ProductSpuIntent productSpuIntent) {

    logger.info("开始处理新增SPU-情感意图关系请求, 操作人: {}, 参数: {}", OPERATOR, productSpuIntent);

    int result = productSpuIntentService.save(productSpuIntent);

    logger.info("新增SPU-情感意图关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuIntent, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 批量新增数据
   */
  @PostMapping("/batch")
  @ApiOperation(value = "批量新增SPU-情感意图关系")
  public ResponseEntity<Object> createBatch(
    @ApiParam(value = "SPU-情感意图关系信息列表", required = true)
    @RequestBody List<ProductSpuIntent> productSpuIntentList) {

    logger.info("开始处理批量新增SPU-情感意图关系请求, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuIntentList != null ? productSpuIntentList.size() : 0);

    int result = productSpuIntentService.saveBatch(productSpuIntentList);

    logger.info("批量新增SPU-情感意图关系请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuIntentList != null ? productSpuIntentList.size() : 0, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据主键删除SPU-情感意图关系")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键删除请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuIntentService.deleteById(id);

    logger.info("根据主键删除请求处理完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据主键集合批量删除
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据主键集合批量删除SPU-情感意图关系")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("开始处理批量删除请求, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuIntentService.deleteBatchIds(ids);

    logger.info("批量删除请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据SPU ID批量删除
   */
  @DeleteMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID批量删除SPU-情感意图关系")
  public ResponseEntity<Object> deleteBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID批量删除请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuIntentService.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除请求处理完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据情感意图ID批量删除
   */
  @DeleteMapping("/intent/{intentId}")
  @ApiOperation(value = "根据情感意图ID批量删除SPU-情感意图关系")
  public ResponseEntity<Object> deleteByIntentId(
    @ApiParam(value = "情感意图ID", required = true)
    @PathVariable String intentId) {

    logger.info("开始处理根据情感意图ID批量删除请求, 操作人: {}, 参数: intentId={}", OPERATOR, intentId);

    int result = productSpuIntentService.deleteByIntentId(intentId);

    logger.info("根据情感意图ID批量删除请求处理完成, 操作人: {}, 参数: intentId={}, 影响行数: {}",
      OPERATOR, intentId, result);

    return ResponseEntity.ok(result);
  }
}
