package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.dto.ProductSpuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSpuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSpuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSpu;
import com.cn.taihe.back.product.service.ProductSpuService;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品SPU Controller
 *
 * @author system
 * @since 2025-01-01
 */
@RestController
@RequestMapping("/product/spu")
@Api(tags = "商品SPU管理接口")
public class ProductSpuController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuController.class);

  private static final String DEFAULT_OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuService productSpuService;

  /**
   * 根据主键查找数据
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据ID查询商品SPU", notes = "根据主键ID查询商品SPU详细信息")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true) @PathVariable String id) {
    logger.info("根据ID查询商品SPU，请求参数: id={}, operator={}", id, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (!StringUtils.hasText(id)) {
        result.put("success", false);
        result.put("message", "主键ID不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      ProductSpu productSpu = productSpuService.getById(id);
      if (productSpu == null) {
        result.put("success", false);
        result.put("message", "商品SPU不存在");
        return ResponseEntity.ok().body(result);
      }

      result.put("success", true);
      result.put("data", productSpu);
      result.put("message", "查询成功");
      logger.info("根据ID查询商品SPU成功，id: {}, operator: {}", id, DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("根据ID查询商品SPU异常，id: {}, operator: {}", id, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "查询失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 新增数据
   */
  @PostMapping
  @ApiOperation(value = "新增商品SPU", notes = "创建新的商品SPU")
  public ResponseEntity<Object> create(
    @ApiParam(value = "新增参数", required = true) @RequestBody ProductSpuCreateDTO createDTO) {
    logger.info("新增商品SPU，请求参数: {}, operator={}", createDTO, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (createDTO == null) {
        result.put("success", false);
        result.put("message", "请求参数不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      boolean success = productSpuService.create(createDTO);
      result.put("success", success);
      result.put("message", success ? "新增成功" : "新增失败");
      logger.info("新增商品SPU{}，operator: {}", success ? "成功" : "失败", DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("新增商品SPU异常，参数: {}, operator: {}", createDTO, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "新增失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 修改数据
   */
  @PutMapping
  @ApiOperation(value = "修改商品SPU", notes = "更新商品SPU信息")
  public ResponseEntity<Object> update(
    @ApiParam(value = "更新参数", required = true) @RequestBody ProductSpuUpdateDTO updateDTO) {
    logger.info("修改商品SPU，请求参数: {}, operator={}", updateDTO, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
        result.put("success", false);
        result.put("message", "请求参数或主键ID不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      boolean success = productSpuService.update(updateDTO);
      result.put("success", success);
      result.put("message", success ? "修改成功" : "修改失败");
      logger.info("修改商品SPU{}，id: {}, operator: {}",
        success ? "成功" : "失败", updateDTO.getId(), DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("修改商品SPU异常，参数: {}, operator: {}", updateDTO, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "修改失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "删除商品SPU", notes = "根据主键ID删除商品SPU")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true) @PathVariable String id) {
    logger.info("删除商品SPU，请求参数: id={}, operator={}", id, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (!StringUtils.hasText(id)) {
        result.put("success", false);
        result.put("message", "主键ID不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      boolean success = productSpuService.deleteById(id);
      result.put("success", success);
      result.put("message", success ? "删除成功" : "删除失败");
      logger.info("删除商品SPU{}，id: {}, operator: {}",
        success ? "成功" : "失败", id, DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("删除商品SPU异常，id: {}, operator: {}", id, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "删除失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 条件分页查询数据
   */
  @PostMapping("/page")
  @ApiOperation(value = "条件分页查询商品SPU", notes = "根据条件分页查询商品SPU列表")
  public ResponseEntity<Object> getByCondition(
    @ApiParam(value = "查询条件") @RequestBody(required = false) ProductSpuQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer page,
    @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer size) {
    logger.info("条件分页查询商品SPU，请求参数: queryDTO={}, page={}, size={}, operator={}",
      queryDTO, page, size, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      // 设置默认查询条件
      if (queryDTO == null) {
        queryDTO = new ProductSpuQueryDTO();
      }

      PageInfo<ProductSpu> pageInfo = productSpuService.getByCondition(queryDTO, page, size);
      result.put("success", true);
      result.put("data", pageInfo.getList());
      result.put("total", pageInfo.getTotal());
      result.put("page", pageInfo.getPageNum());
      result.put("size", pageInfo.getPageSize());
      result.put("pages", pageInfo.getPages());
      result.put("message", "查询成功");
      logger.info("条件分页查询商品SPU成功，总记录数: {}, 当前页: {}, 页大小: {}, operator: {}",
        pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("条件分页查询商品SPU异常，参数: {}, operator: {}", queryDTO, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "查询失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 查询所有数据
   */
  @GetMapping("/all")
  @ApiOperation(value = "查询所有商品SPU", notes = "获取所有商品SPU列表（不分页）")
  public ResponseEntity<Object> getAll() {
    logger.info("查询所有商品SPU，operator={}", DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      List<ProductSpu> list = productSpuService.getAll();
      result.put("success", true);
      result.put("data", list);
      result.put("total", list.size());
      result.put("message", "查询成功");
      logger.info("查询所有商品SPU成功，记录数: {}, operator: {}", list.size(), DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("查询所有商品SPU异常，operator: {}", DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "查询失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 批量删除数据
   */
  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除商品SPU", notes = "根据主键ID集合批量删除商品SPU")
  public ResponseEntity<Object> deleteBatchIds(
    @ApiParam(value = "主键ID集合", required = true) @RequestBody List<String> ids) {
    logger.info("批量删除商品SPU，请求参数: ids={}, operator={}", ids, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (ids == null || ids.isEmpty()) {
        result.put("success", false);
        result.put("message", "主键ID集合不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      boolean success = productSpuService.deleteBatchIds(ids);
      result.put("success", success);
      result.put("message", success ? "批量删除成功" : "批量删除失败");
      logger.info("批量删除商品SPU{}，删除记录数: {}, operator: {}",
        success ? "成功" : "失败", ids.size(), DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("批量删除商品SPU异常，ids: {}, operator: {}", ids, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "批量删除失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 冻结/启用商品SPU
   */
  @PutMapping("/{id}/status")
  @ApiOperation(value = "更新商品SPU状态", notes = "冻结或启用商品SPU")
  public ResponseEntity<Object> updateStatus(
    @ApiParam(value = "主键ID", required = true) @PathVariable String id,
    @ApiParam(value = "是否启用", required = true) @RequestParam Boolean isActive) {
    logger.info("更新商品SPU状态，请求参数: id={}, isActive={}, operator={}", id, isActive, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (!StringUtils.hasText(id) || isActive == null) {
        result.put("success", false);
        result.put("message", "主键ID和状态不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      boolean success = productSpuService.updateStatusById(id, isActive);
      result.put("success", success);
      result.put("message", success ? (isActive ? "启用成功" : "冻结成功") : "状态更新失败");
      logger.info("更新商品SPU状态{}，id: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", id, isActive, DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("更新商品SPU状态异常，id: {}, isActive: {}, operator: {}",
        id, isActive, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "状态更新失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 批量冻结/启用商品SPU
   */
  @PostMapping("/batch-status")
  @ApiOperation(value = "批量更新商品SPU状态", notes = "批量冻结或启用商品SPU")
  public ResponseEntity<Object> updateStatusBatch(
    @ApiParam(value = "主键ID集合", required = true) @RequestBody List<String> ids,
    @ApiParam(value = "是否启用", required = true) @RequestParam Boolean isActive) {
    logger.info("批量更新商品SPU状态，请求参数: ids={}, isActive={}, operator={}", ids, isActive, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (ids == null || ids.isEmpty() || isActive == null) {
        result.put("success", false);
        result.put("message", "主键ID集合和状态不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      boolean success = productSpuService.updateStatusBatchIds(ids, isActive);
      result.put("success", success);
      result.put("message", success ? (isActive ? "批量启用成功" : "批量冻结成功") : "批量状态更新失败");
      logger.info("批量更新商品SPU状态{}，更新记录数: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", ids.size(), isActive, DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("批量更新商品SPU状态异常，ids: {}, isActive: {}, operator: {}",
        ids, isActive, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "批量状态更新失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 根据SPU编码查询数据
   */
  @GetMapping("/code/{spuCode}")
  @ApiOperation(value = "根据SPU编码查询", notes = "根据SPU编码查询商品SPU详细信息")
  public ResponseEntity<Object> getBySpuCode(
    @ApiParam(value = "SPU编码", required = true) @PathVariable String spuCode) {
    logger.info("根据SPU编码查询商品SPU，请求参数: spuCode={}, operator={}", spuCode, DEFAULT_OPERATOR);

    Map<String, Object> result = new HashMap<>();
    try {
      if (!StringUtils.hasText(spuCode)) {
        result.put("success", false);
        result.put("message", "SPU编码不能为空");
        return ResponseEntity.badRequest().body(result);
      }

      ProductSpu productSpu = productSpuService.getBySpuCode(spuCode);
      if (productSpu == null) {
        result.put("success", false);
        result.put("message", "商品SPU不存在");
        return ResponseEntity.ok().body(result);
      }

      result.put("success", true);
      result.put("data", productSpu);
      result.put("message", "查询成功");
      logger.info("根据SPU编码查询商品SPU成功，spuCode: {}, operator: {}", spuCode, DEFAULT_OPERATOR);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      logger.error("根据SPU编码查询商品SPU异常，spuCode: {}, operator: {}", spuCode, DEFAULT_OPERATOR, e);
      result.put("success", false);
      result.put("message", "查询失败: " + e.getMessage());
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }
}
