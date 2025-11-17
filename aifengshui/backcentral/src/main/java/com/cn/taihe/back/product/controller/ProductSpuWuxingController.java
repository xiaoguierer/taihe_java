package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.entity.ProductSpuWuxing;
import com.cn.taihe.back.product.service.ProductSpuWuxingService;
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
 * SPU-五行关系表 Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/product-spu-wuxing")
@Api(tags = "SPU-五行关系管理")
public class ProductSpuWuxingController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuWuxingController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuWuxingService productSpuWuxingService;

  /**
   * 根据主键查找
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据主键查找SPU-五行关系")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键查找请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuWuxing result = productSpuWuxingService.findById(id);

    logger.info("根据主键查找请求处理完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");
    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SPU ID查找
   */
  @GetMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID查找SPU-五行关系")
  public ResponseEntity<Object> getBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {
    logger.info("开始处理根据SPU ID查找请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);
    List<ProductSpuWuxing> result = productSpuWuxingService.findBySpuId(spuId);
    logger.info("根据SPU ID查找请求处理完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);
    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据五行属性ID查找
   */
  @GetMapping("/wuxing/{wuXingId}")
  @ApiOperation(value = "根据五行属性ID查找SPU-五行关系")
  public ResponseEntity<Object> getByWuXingId(
    @ApiParam(value = "五行属性ID", required = true)
    @PathVariable String wuXingId) {

    logger.info("开始处理根据五行属性ID查找请求, 操作人: {}, 参数: wuXingId={}", OPERATOR, wuXingId);

    List<ProductSpuWuxing> result = productSpuWuxingService.findByWuXingId(wuXingId);

    logger.info("根据五行属性ID查找请求处理完成, 操作人: {}, 参数: wuXingId={}, 结果数量: {}",
      OPERATOR, wuXingId, result != null ? result.size() : 0);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 新增数据
   */
  @PostMapping
  @ApiOperation(value = "新增SPU-五行关系")
  public ResponseEntity<Object> create(
    @ApiParam(value = "SPU-五行关系信息", required = true)
    @RequestBody ProductSpuWuxing productSpuWuxing) {
    logger.info("开始处理新增SPU-五行关系请求, 操作人: {}, 参数: {}", OPERATOR, productSpuWuxing);
    int result = productSpuWuxingService.save(productSpuWuxing);
    logger.info("新增SPU-五行关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuWuxing, result);
    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 批量新增数据
   */
  @PostMapping("/batch")
  @ApiOperation(value = "批量新增SPU-五行关系")
  public ResponseEntity<Object> createBatch(
    @ApiParam(value = "SPU-五行关系信息列表", required = true)
    @RequestBody List<ProductSpuWuxing> productSpuWuxingList) {

    logger.info("开始处理批量新增SPU-五行关系请求, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuWuxingList != null ? productSpuWuxingList.size() : 0);

    int result = productSpuWuxingService.saveBatch(productSpuWuxingList);

    logger.info("批量新增SPU-五行关系请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuWuxingList != null ? productSpuWuxingList.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "根据主键删除SPU-五行关系")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id) {

    logger.info("开始处理根据主键删除请求, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuWuxingService.deleteById(id);

    logger.info("根据主键删除请求处理完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据主键集合批量删除
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "根据主键集合批量删除SPU-五行关系")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("开始处理批量删除请求, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuWuxingService.deleteBatchIds(ids);

    logger.info("批量删除请求处理完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);

    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据SPU ID批量删除
   */
  @DeleteMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID批量删除SPU-五行关系")
  public ResponseEntity<Object> deleteBySpuId(
    @ApiParam(value = "SPU ID", required = true)
    @PathVariable String spuId) {

    logger.info("开始处理根据SPU ID批量删除请求, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuWuxingService.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除请求处理完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);
    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据五行属性ID批量删除
   */
  @DeleteMapping("/wuxing/{wuXingId}")
  @ApiOperation(value = "根据五行属性ID批量删除SPU-五行关系")
  public ResponseEntity<Object> deleteByWuXingId(
    @ApiParam(value = "五行属性ID", required = true)
    @PathVariable String wuXingId) {

    logger.info("开始处理根据五行属性ID批量删除请求, 操作人: {}, 参数: wuXingId={}", OPERATOR, wuXingId);

    int result = productSpuWuxingService.deleteByWuXingId(wuXingId);

    logger.info("根据五行属性ID批量删除请求处理完成, 操作人: {}, 参数: wuXingId={}, 影响行数: {}",
      OPERATOR, wuXingId, result);

    return ResponseEntity.ok(Result.success(result));
  }
}
