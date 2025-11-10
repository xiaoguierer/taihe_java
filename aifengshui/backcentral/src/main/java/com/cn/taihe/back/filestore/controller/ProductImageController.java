package com.cn.taihe.back.filestore.controller;

import com.cn.taihe.back.filestore.dto.ProductImageCreateDTO;
import com.cn.taihe.back.filestore.dto.ProductImageQueryDTO;
import com.cn.taihe.back.filestore.dto.ProductImageUpdateDTO;
import com.cn.taihe.back.filestore.entity.ProductImage;
import com.cn.taihe.back.filestore.service.ProductImageService;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 商品图片表 Controller
 *
 * @author system
 * @date 2025-11-07
 */
@RestController
@RequestMapping("/product-image")
@Api(value = "ProductImageController", tags = "商品图片管理接口")
@Validated
public class ProductImageController {

  private static final Logger logger = LoggerFactory.getLogger(ProductImageController.class);
  private static final String OPERATOR = "ADMIN";
  @Autowired
  private ProductImageService productImageService;

  /**
   * 根据主键查询商品图片
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "根据主键查询商品图片", notes = "根据主键ID查询商品图片详细信息")
  public ResponseEntity<Object> getProductImageById(
    @ApiParam(value = "主键ID", required = true)
    @NotBlank(message = "主键ID不能为空")
    @PathVariable String id) {
    logger.info("查询商品图片详情开始 - 操作人: {}, 参数: id={}", OPERATOR, id);

    try {
      ProductImage result = productImageService.getProductImageById(id);
      logger.info("查询商品图片详情成功 - 操作人: {}, 结果: {}", OPERATOR, result != null ? "存在" : "不存在");
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("查询商品图片详情异常 - 操作人: {}, 参数: id={}, 异常信息: ", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 新增商品图片
   */
  @PostMapping(value = "/add")
  @ApiOperation(value = "新增商品图片", notes = "创建新的商品图片记录")
  public ResponseEntity<Object> createProductImage(
    @ApiParam(value = "商品图片信息", required = true)
    @Valid @RequestBody ProductImageCreateDTO createDTO){
    logger.info("新增商品图片开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
    try {
      Boolean result = productImageService.createProductImage(createDTO);
      logger.info("新增商品图片完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(false);
    } catch (Exception e) {
      logger.error("新增商品图片异常 - 操作人: {}, 参数: {}, 异常信息: ", OPERATOR, createDTO, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 更新商品图片
   */
  @PutMapping
  @ApiOperation(value = "更新商品图片", notes = "更新商品图片信息")
  public ResponseEntity<Object> updateProductImage(
    @ApiParam(value = "商品图片信息", required = true)
    @Valid @RequestBody ProductImageUpdateDTO updateDTO) {
    logger.info("更新商品图片开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);

    try {
      Boolean result = productImageService.updateProductImage(updateDTO);
      logger.info("更新商品图片完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(false);
    } catch (Exception e) {
      logger.error("更新商品图片异常 - 操作人: {}, 参数: {}, 异常信息: ", OPERATOR, updateDTO, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 删除商品图片
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "删除商品图片", notes = "根据主键ID删除商品图片")
  public ResponseEntity<Object> deleteProductImage(
    @ApiParam(value = "主键ID", required = true)
    @NotBlank(message = "主键ID不能为空")
    @PathVariable String id) {
    logger.info("删除商品图片开始 - 操作人: {}, 参数: id={}", OPERATOR, id);

    try {
      Boolean result = productImageService.deleteProductImageById(id);
      logger.info("删除商品图片完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(false);
    } catch (Exception e) {
      logger.error("删除商品图片异常 - 操作人: {}, 参数: id={}, 异常信息: ", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 条件分页查询商品图片列表
   */
  @PostMapping("/page")
  @ApiOperation(value = "条件分页查询商品图片列表", notes = "根据条件分页查询商品图片列表")
  public ResponseEntity<Object> getProductImagePage(
    @ApiParam(value = "查询条件")
    @RequestBody(required = false) ProductImageQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") Integer page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") Integer size) {
    logger.info("条件分页查询商品图片列表开始 - 操作人: {}, 参数: queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);

    try {
      if (queryDTO == null) {
        queryDTO = new ProductImageQueryDTO();
      }
      PageInfo<ProductImage> result = productImageService.getProductImagePage(queryDTO, page, size);
      logger.info("条件分页查询商品图片列表成功 - 操作人: {}, 总记录数: {}", OPERATOR, result.getTotal());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("条件分页查询商品图片列表异常 - 操作人: {}, 参数: queryDTO={}, page={}, size={}, 异常信息: ",
        OPERATOR, queryDTO, page, size, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 条件查询商品图片列表
   */
  @PostMapping("/list")
  @ApiOperation(value = "条件查询商品图片列表", notes = "根据条件查询商品图片列表（不分页）")
  public ResponseEntity<Object> getProductImageList(
    @ApiParam(value = "查询条件")
    @RequestBody(required = false) ProductImageQueryDTO queryDTO) {
    logger.info("条件查询商品图片列表开始 - 操作人: {}, 参数: {}", OPERATOR, queryDTO);

    try {
      if (queryDTO == null) {
        queryDTO = new ProductImageQueryDTO();
      }
      List<ProductImage> result = productImageService.getProductImageList(queryDTO);
      logger.info("条件查询商品图片列表成功 - 操作人: {}, 记录数: {}", OPERATOR, result.size());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("条件查询商品图片列表异常 - 操作人: {}, 参数: {}, 异常信息: ", OPERATOR, queryDTO, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 查询所有商品图片
   */
  @GetMapping("/all")
  @ApiOperation(value = "查询所有商品图片", notes = "查询所有商品图片列表")
  public ResponseEntity<Object> getAllProductImage() {
    logger.info("查询所有商品图片开始 - 操作人: {}", OPERATOR);

    try {
      List<ProductImage> result = productImageService.getAllProductImage();
      logger.info("查询所有商品图片成功 - 操作人: {}, 记录数: {}", OPERATOR, result.size());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("查询所有商品图片异常 - 操作人: {}, 异常信息: ", OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 批量删除商品图片
   */
  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除商品图片", notes = "根据主键ID集合批量删除商品图片")
  public ResponseEntity<Object> deleteProductImageBatch(
    @ApiParam(value = "主键ID集合", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量删除商品图片开始 - 操作人: {}, 参数: ids={}", OPERATOR, ids);

    try {
      Boolean result = productImageService.deleteProductImageBatch(ids);
      logger.info("批量删除商品图片完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(false);
    } catch (Exception e) {
      logger.error("批量删除商品图片异常 - 操作人: {}, 参数: ids={}, 异常信息: ", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 更新商品图片状态（冻结/启用）
   */
  @PutMapping("/status/{id}")
  @ApiOperation(value = "更新商品图片状态", notes = "冻结或启用商品图片")
  public ResponseEntity<Object> updateProductImageStatus(
    @ApiParam(value = "主键ID", required = true)
    @NotBlank(message = "主键ID不能为空")
    @PathVariable String id,
    @ApiParam(value = "是否启用", required = true)
    @RequestParam Boolean isActive) {
    logger.info("更新商品图片状态开始 - 操作人: {}, 参数: id={}, isActive={}", OPERATOR, id, isActive);

    try {
      Boolean result = productImageService.updateProductImageStatus(id, isActive);
      logger.info("更新商品图片状态完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(false);
    } catch (Exception e) {
      logger.error("更新商品图片状态异常 - 操作人: {}, 参数: id={}, isActive={}, 异常信息: ", OPERATOR, id, isActive, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 批量更新商品图片状态
   */
  @PostMapping("/batch-status")
  @ApiOperation(value = "批量更新商品图片状态", notes = "批量冻结或启用商品图片")
  public ResponseEntity<Object> updateProductImageStatusBatch(
    @ApiParam(value = "主键ID集合", required = true)
    @RequestBody List<String> ids,
    @ApiParam(value = "是否启用", required = true)
    @RequestParam Boolean isActive) {
    logger.info("批量更新商品图片状态开始 - 操作人: {}, 参数: ids={}, isActive={}", OPERATOR, ids, isActive);

    try {
      Boolean result = productImageService.updateProductImageStatusBatch(ids, isActive);
      logger.info("批量更新商品图片状态完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    } catch (Exception e) {
      logger.error("批量更新商品图片状态异常 - 操作人: {}, 参数: ids={}, isActive={}, 异常信息: ", OPERATOR, ids, isActive, e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }
}
