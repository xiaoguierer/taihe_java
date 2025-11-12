package com.cn.taihe.back.product.controller;

import com.cn.taihe.back.product.dto.ProductSkuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSkuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSkuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSku;
import com.cn.taihe.back.product.service.ProductSkuService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品SKU Controller
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/product-sku")
@Api(tags = "商品SKU管理接口")
public class ProductSkuController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSkuController.class);

  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSkuService productSkuService;

  /**
   * 根据主键查找
   */
  @GetMapping("/getByid/{id}")
  @ApiOperation(value = "根据主键查找商品SKU", notes = "根据主键ID查找商品SKU详细信息")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "商品SKU主键ID", required = true) @PathVariable String id) {
    logger.info("根据主键查找商品SKU，请求参数：id={}, operator={}", id, OPERATOR);
    try {
      ProductSku result = productSkuService.findById(id);
      if (result == null) {
        logger.warn("商品SKU不存在，id={}, operator={}", id, OPERATOR);
        return ResponseEntity.notFound().build();
      }
      logger.info("根据主键查找商品SKU成功，返回结果：{}, operator={}", result, OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("根据主键查找商品SKU异常，id={}, operator={}, error={}", id, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("失败"));
    }
  }

  /**
   * 新增数据
   */
  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "新增商品SKU", notes = "创建新的商品SKU记录")
  public ResponseEntity<Object> create(
    @ApiParam(value = "商品SKU新增参数", required = true)
    @RequestPart("createDTO") @Valid  ProductSkuCreateDTO createDTO,
    @RequestPart(value = "mainImagefile", required = false) MultipartFile mainImagefile,
    @RequestPart(value = "image1file", required = false) MultipartFile image1file,
    @RequestPart(value = "image2File", required = false) MultipartFile image2File,
    @RequestPart(value = "image3File", required = false) MultipartFile image3File,
    @RequestPart(value = "image4File", required = false) MultipartFile image4File,
    @RequestPart(value = "image5File", required = false) MultipartFile image5File) {
    logger.info("新增商品SKU，请求参数：{}, operator={}", createDTO, OPERATOR);
    try {
      boolean result = productSkuService.create(createDTO,mainImagefile,image1file,image2File,image3File,image4File,image5File);
      if (!result) {
        logger.warn("新增商品SKU失败，可能SKU编码已存在，createDTO={}, operator={}", createDTO, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("新增商品SKU成功，operator={}", OPERATOR);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("新增商品SKU异常，createDTO={}, operator={}, error={}", createDTO, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 修改数据
   */
  @PutMapping(value = "/updateByid", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "修改商品SKU", notes = "更新商品SKU信息")
  public ResponseEntity<Object> update(
    @ApiParam(value = "商品SKU更新参数", required = true)
    @RequestPart("updateDTO") @Valid ProductSkuUpdateDTO updateDTO,
    @RequestPart(value = "mainImagefile", required = false) MultipartFile mainImagefile,
    @RequestPart(value = "image1file", required = false) MultipartFile image1file,
    @RequestPart(value = "image2File", required = false) MultipartFile image2File,
    @RequestPart(value = "image3File", required = false) MultipartFile image3File,
    @RequestPart(value = "image4File", required = false) MultipartFile image4File,
    @RequestPart(value = "image5File", required = false) MultipartFile image5File) {
    logger.info("修改商品SKU，请求参数：{}, operator={}", updateDTO, OPERATOR);
    try {
      boolean result = productSkuService.update(updateDTO,mainImagefile,image1file,image2File,image3File,image4File,image5File);
      if (!result) {
        logger.warn("修改商品SKU失败，可能记录不存在或SKU编码冲突，updateDTO={}, operator={}", updateDTO, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("修改商品SKU成功，operator={}", OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("修改商品SKU异常，updateDTO={}, operator={}, error={}", updateDTO, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "删除商品SKU", notes = "根据主键删除商品SKU记录")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "商品SKU主键ID", required = true) @PathVariable String id) {
    logger.info("删除商品SKU，请求参数：id={}, operator={}", id, OPERATOR);
    try {
      boolean result = productSkuService.deleteById(id);
      if (!result) {
        logger.warn("删除商品SKU失败，可能记录不存在，id={}, operator={}", id, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("删除商品SKU成功，id={}, operator={}", id, OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("删除商品SKU异常，id={}, operator={}, error={}", id, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 条件分页查询数据
   */
  @PostMapping("/page")
  @ApiOperation(value = "条件分页查询商品SKU", notes = "根据查询条件分页查询商品SKU列表")
  public ResponseEntity<Object> getByPage(
    @ApiParam(value = "商品SKU查询条件")
    @RequestBody ProductSkuQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {
    logger.info("条件分页查询商品SKU，请求参数：queryDTO={}, page={}, size={}, operator={}", queryDTO, page, size, OPERATOR);
    try {
      PageInfo<ProductSku> result = productSkuService.findByCondition(queryDTO, page, size);
      logger.info("条件分页查询商品SKU成功，总记录数：{}, operator={}", result.getTotal(), OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("条件分页查询商品SKU异常，queryDTO={}, page={}, size={}, operator={}, error={}",
        queryDTO, page, size, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 查询所有数据
   */
  @GetMapping("/list")
  @ApiOperation(value = "查询所有商品SKU", notes = "获取所有商品SKU列表")
  public ResponseEntity<Object> getAll() {
    logger.info("查询所有商品SKU，operator={}", OPERATOR);
    try {
      List<ProductSku> result = productSkuService.findAll();
      logger.info("查询所有商品SKU成功，记录数：{}, operator={}", result.size(), OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("查询所有商品SKU异常，operator={}, error={}", OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 根据主键集合批量删除
   */
  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除商品SKU", notes = "根据主键ID集合批量删除商品SKU")
  public ResponseEntity<Object> batchDelete(
    @ApiParam(value = "商品SKU主键ID集合", required = true) @RequestBody List<String> ids) {
    logger.info("批量删除商品SKU，请求参数：ids={}, operator={}", ids, OPERATOR);
    try {
      boolean result = productSkuService.deleteByIds(ids);
      if (!result) {
        logger.warn("批量删除商品SKU失败，可能参数为空，ids={}, operator={}", ids, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("批量删除商品SKU成功，删除记录数：{}, operator={}", ids.size(), OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("批量删除商品SKU异常，ids={}, operator={}, error={}", ids, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 更新状态
   */
  @PutMapping("/{id}/status")
  @ApiOperation(value = "更新商品SKU状态", notes = "更新商品SKU的状态（0草稿/1上架/2下架）")
  public ResponseEntity<Object> updateStatus(
    @ApiParam(value = "商品SKU主键ID", required = true) @PathVariable String id,
    @ApiParam(value = "状态：0草稿/1上架/2下架", required = true) @RequestParam Integer status,
    @ApiParam(value = "更新人ID", required = true) @RequestParam Long updatedBy) {
    logger.info("更新商品SKU状态，请求参数：id={}, status={}, updatedBy={}, operator={}", id, status, updatedBy, OPERATOR);
    try {
      boolean result = productSkuService.updateStatus(id, status, updatedBy);
      if (!result) {
        logger.warn("更新商品SKU状态失败，可能记录不存在，id={}, status={}, operator={}", id, status, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("更新商品SKU状态成功，id={}, status={}, operator={}", id, status, OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("更新商品SKU状态异常，id={}, status={}, updatedBy={}, operator={}, error={}",
        id, status, updatedBy, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 更新是否可售状态（冻结/解冻）
   */
  @PutMapping("/{id}/available")
  @ApiOperation(value = "更新商品SKU可售状态", notes = "冻结或解冻商品SKU的可售状态")
  public ResponseEntity<Object> updateIsAvailable(
    @ApiParam(value = "商品SKU主键ID", required = true) @PathVariable String id,
    @ApiParam(value = "是否可售", required = true) @RequestParam Boolean isAvailable,
    @ApiParam(value = "更新人ID", required = true) @RequestParam Long updatedBy) {
    logger.info("更新商品SKU可售状态，请求参数：id={}, isAvailable={}, updatedBy={}, operator={}",
      id, isAvailable, updatedBy, OPERATOR);
    try {
      boolean result = productSkuService.updateIsAvailable(id, isAvailable, updatedBy);
      if (!result) {
        logger.warn("更新商品SKU可售状态失败，可能记录不存在，id={}, isAvailable={}, operator={}", id, isAvailable, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("更新商品SKU可售状态成功，id={}, isAvailable={}, operator={}", id, isAvailable, OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("更新商品SKU可售状态异常，id={}, isAvailable={}, updatedBy={}, operator={}, error={}",
        id, isAvailable, updatedBy, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 批量更新状态
   */
  @PutMapping("/batch-status")
  @ApiOperation(value = "批量更新商品SKU状态", notes = "批量更新商品SKU的状态")
  public ResponseEntity<Object> batchUpdateStatus(
    @ApiParam(value = "商品SKU主键ID集合", required = true) @RequestBody List<String> ids,
    @ApiParam(value = "状态：0草稿/1上架/2下架", required = true) @RequestParam Integer status,
    @ApiParam(value = "更新人ID", required = true) @RequestParam Long updatedBy) {
    logger.info("批量更新商品SKU状态，请求参数：ids={}, status={}, updatedBy={}, operator={}", ids, status, updatedBy, OPERATOR);
    try {
      boolean result = productSkuService.batchUpdateStatus(ids, status, updatedBy);
      if (!result) {
        logger.warn("批量更新商品SKU状态失败，可能参数为空，ids={}, status={}, operator={}", ids, status, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("批量更新商品SKU状态成功，更新记录数：{}, status={}, operator={}", ids.size(), status, OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("批量更新商品SKU状态异常，ids={}, status={}, updatedBy={}, operator={}, error={}",
        ids, status, updatedBy, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 批量更新是否可售状态
   */
  @PutMapping("/batch-available")
  @ApiOperation(value = "批量更新商品SKU可售状态", notes = "批量冻结或解冻商品SKU的可售状态")
  public ResponseEntity<Object> batchUpdateIsAvailable(
    @ApiParam(value = "商品SKU主键ID集合", required = true) @RequestBody List<String> ids,
    @ApiParam(value = "是否可售", required = true) @RequestParam Boolean isAvailable,
    @ApiParam(value = "更新人ID", required = true) @RequestParam Long updatedBy) {
    logger.info("批量更新商品SKU可售状态，请求参数：ids={}, isAvailable={}, updatedBy={}, operator={}",
      ids, isAvailable, updatedBy, OPERATOR);
    try {
      boolean result = productSkuService.batchUpdateIsAvailable(ids, isAvailable, updatedBy);
      if (!result) {
        logger.warn("批量更新商品SKU可售状态失败，可能参数为空，ids={}, isAvailable={}, operator={}", ids, isAvailable, OPERATOR);
        return ResponseEntity.badRequest().body(false);
      }
      logger.info("批量更新商品SKU可售状态成功，更新记录数：{}, isAvailable={}, operator={}", ids.size(), isAvailable, OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("批量更新商品SKU可售状态异常，ids={}, isAvailable={}, updatedBy={}, operator={}, error={}",
        ids, isAvailable, updatedBy, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 根据SPU ID查询
   */
  @GetMapping("/spu/{spuId}")
  @ApiOperation(value = "根据SPU ID查询商品SKU", notes = "根据SPU ID查询对应的商品SKU列表")
  public ResponseEntity<Object> getBySpuId(
    @ApiParam(value = "SPU ID", required = true) @PathVariable String spuId) {
    logger.info("根据SPU ID查询商品SKU，请求参数：spuId={}, operator={}", spuId, OPERATOR);
    try {
      List<ProductSku> result = productSkuService.findBySpuId(spuId);
      logger.info("根据SPU ID查询商品SKU成功，记录数：{}, operator={}", result.size(), OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("根据SPU ID查询商品SKU异常，spuId={}, operator={}, error={}", spuId, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 根据SKU编码查询
   */
  @GetMapping("/code/{skuCode}")
  @ApiOperation(value = "根据SKU编码查询商品SKU", notes = "根据SKU编码查询商品SKU详细信息")
  public ResponseEntity<Object> getBySkuCode(
    @ApiParam(value = "SKU编码", required = true) @PathVariable String skuCode) {
    logger.info("根据SKU编码查询商品SKU，请求参数：skuCode={}, operator={}", skuCode, OPERATOR);
    try {
      ProductSku result = productSkuService.findBySkuCode(skuCode);
      if (result == null) {
        logger.warn("商品SKU不存在，skuCode={}, operator={}", skuCode, OPERATOR);
        return ResponseEntity.notFound().build();
      }
      logger.info("根据SKU编码查询商品SKU成功，返回结果：{}, operator={}", result, OPERATOR);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("根据SKU编码查询商品SKU异常，skuCode={}, operator={}, error={}", skuCode, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 检查SKU编码是否存在
   */
  @GetMapping("/check-sku-code")
  @ApiOperation(value = "检查SKU编码是否存在", notes = "检查SKU编码是否已存在")
  public ResponseEntity<Object> checkSkuCodeExists(
    @ApiParam(value = "SKU编码", required = true) @RequestParam String skuCode,
    @ApiParam(value = "排除的主键ID") @RequestParam(required = false) String excludeId) {
    logger.info("检查SKU编码是否存在，请求参数：skuCode={}, excludeId={}, operator={}", skuCode, excludeId, OPERATOR);
    try {
      boolean exists = productSkuService.isSkuCodeExists(skuCode, excludeId);
      logger.info("检查SKU编码是否存在，结果：{}, operator={}", exists, OPERATOR);
      return ResponseEntity.ok(exists);
    } catch (Exception e) {
      logger.error("检查SKU编码是否存在异常，skuCode={}, excludeId={}, operator={}, error={}",
        skuCode, excludeId, OPERATOR, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }
}
