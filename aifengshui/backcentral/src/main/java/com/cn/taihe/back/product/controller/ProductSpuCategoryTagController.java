package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.entity.ProductSpuCategoryTag;
import com.cn.taihe.back.product.service.ProductSpuCategoryTagService;
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
 * SPU-品类标签关系表 Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/product-spu-category-tag")
@Api(tags = "SPU-品类标签关系管理")
public class ProductSpuCategoryTagController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuCategoryTagController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuCategoryTagService productSpuCategoryTagService;

  /**
   * 根据主键查找
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据主键查找SPU-品类标签关系")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键查找请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuCategoryTag result = productSpuCategoryTagService.findById(id);

    logger.info("根据主键查找请求处理完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");

    return ResponseEntity.ok(result);
  }

  /**
   * 根据SPU ID查找
   */
  @GetMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID查找SPU-品类标签关系")
  public ResponseEntity<Object> getBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID查找请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuCategoryTag> result = productSpuCategoryTagService.findBySpuId(spuId);

    logger.info("根据SPU ID查找请求处理完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据品类标签ID查找
   */
  @GetMapping("/category-tag/{categoryTagId}")
  @ApiOperation(value = "根据品类标签ID查找SPU-品类标签关系")
  public ResponseEntity<Object> getByCategoryTagId(
    @ApiParam(value = "品类标签ID", required = true)
    @PathVariable String categoryTagId) {

    logger.info("开始处理根据品类标签ID查找请求, 操作人: {}, 参数: categoryTagId={}", OPERATOR, categoryTagId);

    List<ProductSpuCategoryTag> result = productSpuCategoryTagService.findByCategoryTagId(categoryTagId);

    logger.info("根据品类标签ID查找请求处理完成, 操作人: {}, 参数: categoryTagId={}, 结果数量: {}",
      OPERATOR, categoryTagId, result != null ? result.size() : 0);

    return ResponseEntity.ok(result);
  }

  /**
   * 新增数据
   */
  @PostMapping
  @ApiOperation(value = "新增SPU-品类标签关系")
  public ResponseEntity<Object> create(
    @ApiParam(value = "SPU-品类标签关系信息", required = true)
    @RequestBody ProductSpuCategoryTag productSpuCategoryTag) {

    logger.info("开始处理新增SPU-品类标签关系请求, 操作人: {}, 参数: {}", OPERATOR, productSpuCategoryTag);

    int result = productSpuCategoryTagService.save(productSpuCategoryTag);

    logger.info("新增SPU-品类标签关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuCategoryTag, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 批量新增数据
   */
  @PostMapping("/batch")
  @ApiOperation(value = "批量新增SPU-品类标签关系")
  public ResponseEntity<Object> createBatch(
    @ApiParam(value = "SPU-品类标签关系信息列表", required = true)
    @RequestBody List<ProductSpuCategoryTag> productSpuCategoryTagList) {

    logger.info("开始处理批量新增SPU-品类标签关系请求, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuCategoryTagList != null ? productSpuCategoryTagList.size() : 0);

    int result = productSpuCategoryTagService.saveBatch(productSpuCategoryTagList);

    logger.info("批量新增SPU-品类标签关系请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuCategoryTagList != null ? productSpuCategoryTagList.size() : 0, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据主键删除SPU-品类标签关系")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键删除请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuCategoryTagService.deleteById(id);

    logger.info("根据主键删除请求处理完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据主键集合批量删除
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据主键集合批量删除SPU-品类标签关系")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("开始处理批量删除请求, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuCategoryTagService.deleteBatchIds(ids);

    logger.info("批量删除请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据SPU ID批量删除
   */
  @DeleteMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID批量删除SPU-品类标签关系")
  public ResponseEntity<Object> deleteBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID批量删除请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuCategoryTagService.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除请求处理完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);

    return ResponseEntity.ok(result);
  }

  /**
   * 根据品类标签ID批量删除
   */
  @DeleteMapping("/category-tag/{categoryTagId}")
  @ApiOperation(value = "根据品类标签ID批量删除SPU-品类标签关系")
  public ResponseEntity<Object> deleteByCategoryTagId(
    @ApiParam(value = "品类标签ID", required = true)
    @PathVariable String categoryTagId) {

    logger.info("开始处理根据品类标签ID批量删除请求, 操作人: {}, 参数: categoryTagId={}", OPERATOR, categoryTagId);

    int result = productSpuCategoryTagService.deleteByCategoryTagId(categoryTagId);

    logger.info("根据品类标签ID批量删除请求处理完成, 操作人: {}, 参数: categoryTagId={}, 影响行数: {}",
      OPERATOR, categoryTagId, result);

    return ResponseEntity.ok(result);
  }
}
