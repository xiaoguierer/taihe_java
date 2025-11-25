package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.dto.ProductSpuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSpuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSpuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSpu;
import com.cn.taihe.back.product.service.ProductSpuService;
import com.cn.taihe.back.product.vo.ProductSpuSkuDTO;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品SPU Controller
 *
 * @author system
 * @since 2025-01-01
 */
@RestController
@RequestMapping("/product-spu")
@Api(tags = "商品SPU管理接口")
public class ProductSpuController {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuController.class);

  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuService productSpuService;

  /**
   * 根据主键查找数据
   */
  @GetMapping("/getByid/{id}")
  @ApiOperation(value = "根据ID查询商品SPU", notes = "根据主键ID查询商品SPU详细信息")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true) @PathVariable String id) {
    logger.info("根据ID查询商品SPU，请求参数: id={}, operator={}", id, OPERATOR);
    try {
      if (!StringUtils.hasText(id)) {
        return ResponseEntity.badRequest().body(Result.error("参数为空"));
      }
      ProductSpu productSpu = productSpuService.getById(id);
      if (productSpu == null) {
        logger.info("查询接口未找到数据，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success("未找到对应数据"));
      }
      logger.info("查询接口调用成功，操作人：{}，结果：{}", OPERATOR, productSpu);
      return ResponseEntity.ok(Result.success(productSpu));
    } catch (Exception e) {
      logger.error("查询接口异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("查询失败"));
    }
  }

  /**
   * 新增数据
   */
  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "新增商品SPU", notes = "创建新的商品SPU")
  public ResponseEntity<Object> create(
    @ApiParam(value = "新增参数", required = true)
    @RequestPart("createDTO") @Valid ProductSpuCreateDTO createDTO,
    @RequestPart(value = "mainImagefile", required = false) MultipartFile mainImagefile,
    @RequestPart(value = "conceptImageFile", required = false) MultipartFile conceptImageFile,
    @RequestPart(value = "designImageFile", required = false) MultipartFile designImageFile,
    @RequestPart(value = "prototypeImageFile", required = false) MultipartFile prototypeImageFile,
    @RequestPart(value = "usageImageFile", required = false) MultipartFile usageImageFile,
    @RequestPart(value = "technicalImageFile", required = false) MultipartFile technicalImageFile) {
    logger.info("新增商品SPU，请求参数: {}, operator={}", createDTO, OPERATOR);
    try {
      if (createDTO == null) {
        return ResponseEntity.badRequest().body(Result.error("参数异常"));
      }
      boolean success = productSpuService.create(createDTO,mainImagefile,conceptImageFile,designImageFile,prototypeImageFile,usageImageFile,technicalImageFile);
      logger.info("新增商品SPU{}，operator: {}", success ? "成功" : "失败", OPERATOR);
      return ResponseEntity.ok(Result.success(success));
    } catch (Exception e) {
      logger.error("新增商品SPU异常，参数: {}, operator: {}", createDTO, OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error("查询失败"));
    }
  }

  /**
   * 修改数据
   */
  @PutMapping(value = "/updateByid", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "修改商品SPU", notes = "更新商品SPU信息")
  public ResponseEntity<Object> update(
    @ApiParam(value = "更新参数", required = true)
    @RequestPart ("updateDTO") @Valid  ProductSpuUpdateDTO updateDTO,
    @RequestPart(value = "mainImagefile", required = false) MultipartFile mainImagefile,
    @RequestPart(value = "conceptImageFile", required = false) MultipartFile conceptImageFile,
    @RequestPart(value = "designImageFile", required = false) MultipartFile designImageFile,
    @RequestPart(value = "prototypeImageFile", required = false) MultipartFile prototypeImageFile,
    @RequestPart(value = "usageImageFile", required = false) MultipartFile usageImageFile,
    @RequestPart(value = "technicalImageFile", required = false) MultipartFile technicalImageFile) {
    logger.info("修改商品SPU，请求参数: {}, operator={}", updateDTO, OPERATOR);
    try {
      if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
        return ResponseEntity.badRequest().body("请求参数或主键ID不能为空");
      }
      boolean success = productSpuService.update(updateDTO,mainImagefile,conceptImageFile,designImageFile,prototypeImageFile,usageImageFile,technicalImageFile);
      return ResponseEntity.ok(Result.success(success));
    } catch (Exception e) {
      logger.error("修改商品SPU异常，参数: {}, operator: {}", updateDTO, OPERATOR, e);

      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "删除商品SPU", notes = "根据主键ID删除商品SPU")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true) @PathVariable String id) {
    logger.info("删除商品SPU，请求参数: id={}, operator={}", id, OPERATOR);

    try {
      if (!StringUtils.hasText(id)) {
        return ResponseEntity.badRequest().body("主键ID不能为空");
      }
      boolean success = productSpuService.deleteById(id);
      logger.info("删除商品SPU{}，id: {}, operator: {}",
        success ? "成功" : "失败", id, OPERATOR);
      return ResponseEntity.ok(Result.success(success));
    } catch (Exception e) {
      logger.error("删除商品SPU异常，id: {}, operator: {}", id, OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }

  /**
   * 条件分页查询数据
   */
  @PostMapping("/page")
  @ApiOperation(value = "条件分页查询商品SPU", notes = "根据条件分页查询商品SPU列表")
  public ResponseEntity<Object> getByPage(
    @ApiParam(value = "查询条件")
    @RequestBody(required = false) ProductSpuQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") Integer page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") Integer size) {
    logger.info("条件分页查询商品SPU，请求参数: queryDTO={}, page={}, size={}, operator={}",
      queryDTO, page, size, OPERATOR);
    try {

      PageInfo<ProductSpu> pageInfo = productSpuService.getByCondition(queryDTO, page, size);

      logger.info("条件分页查询商品SPU成功，总记录数: {}, 当前页: {}, 页大小: {}, operator: {}",
        pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), OPERATOR);
      return ResponseEntity.ok(Result.success(pageInfo));
    } catch (Exception e) {
      logger.error("条件分页查询商品SPU异常，参数: {}, operator: {}", queryDTO, OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }

  /**
   * 查询所有数据  无任何查询参数和条件限制
   */
  @GetMapping("/list")
  @ApiOperation(value = "查询所有商品SPU", notes = "获取所有商品SPU列表（不分页）")
  public ResponseEntity<Object> getAll() {
    logger.info("查询所有商品SPU，operator={}", OPERATOR);
    try {
      List<ProductSpu> list = productSpuService.getAll();
      logger.info("查询所有商品SPU成功，记录数: {}, operator: {}", list.size(), OPERATOR);
      return ResponseEntity.ok(Result.success(list));
    } catch (Exception e) {
      logger.error("查询所有商品SPU异常，operator: {}", OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }

  /**
   * 批量删除数据
   */
  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除商品SPU", notes = "根据主键ID集合批量删除商品SPU")
  public ResponseEntity<Object> deleteBatchIds(
    @ApiParam(value = "主键ID集合", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量删除商品SPU，请求参数: ids={}, operator={}", ids, OPERATOR);

    try {
      if (ids == null || ids.isEmpty()) {
        return ResponseEntity.badRequest().body("主键ID集合不能为空");
      }
      boolean success = productSpuService.deleteBatchIds(ids);
      logger.info("批量删除商品SPU{}，删除记录数: {}, operator: {}",
        success ? "成功" : "失败", ids.size(), OPERATOR);
      return ResponseEntity.ok(success);
    } catch (Exception e) {
      logger.error("批量删除商品SPU异常，ids: {}, operator: {}", ids, OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }

  /**
   * 冻结/启用商品SPU
   */
  @PutMapping("/{id}/status")
  @ApiOperation(value = "更新商品SPU状态", notes = "冻结或启用商品SPU")
  public ResponseEntity<Object> updateStatus(
    @ApiParam(value = "主键ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "是否启用", required = true)
    @RequestParam Boolean isActive) {
    logger.info("更新商品SPU状态，请求参数: id={}, isActive={}, operator={}", id, isActive, OPERATOR);
    try {
      if (!StringUtils.hasText(id) || isActive == null) {
        return ResponseEntity.badRequest().body("主键ID和状态不能为空");
      }
      boolean success = productSpuService.updateStatusById(id, isActive);
      logger.info("更新商品SPU状态{}，id: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", id, isActive, OPERATOR);
      return ResponseEntity.ok(success);
    } catch (Exception e) {
      logger.error("更新商品SPU状态异常，id: {}, isActive: {}, operator: {}",
        id, isActive, OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }

  /**
   * 批量冻结/启用商品SPU
   */
  @PostMapping("/batch-status")
  @ApiOperation(value = "批量更新商品SPU状态", notes = "批量冻结或启用商品SPU")
  public ResponseEntity<Object> updateStatusBatch(
    @ApiParam(value = "主键ID集合", required = true)
    @RequestBody List<String> ids,
    @ApiParam(value = "是否启用", required = true)
    @RequestParam Boolean isActive) {
    logger.info("批量更新商品SPU状态，请求参数: ids={}, isActive={}, operator={}", ids, isActive, OPERATOR);
    try {
      if (ids == null || ids.isEmpty() || isActive == null) {
        return ResponseEntity.badRequest().body("主键ID集合和状态不能为空");
      }
      boolean success = productSpuService.updateStatusBatchIds(ids, isActive);
       logger.info("批量更新商品SPU状态{}，更新记录数: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", ids.size(), isActive, OPERATOR);
      return ResponseEntity.ok(Result.success(success));
    } catch (Exception e) {
      logger.error("批量更新商品SPU状态异常，ids: {}, isActive: {}, operator: {}",
        ids, isActive, OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }

  /**
   * 根据SPU编码查询数据
   */
  @GetMapping("/code/{spuCode}")
  @ApiOperation(value = "根据SPU编码查询", notes = "根据SPU编码查询商品SPU详细信息")
  public ResponseEntity<Object> getBySpuCode(
    @ApiParam(value = "SPU编码", required = true)
    @PathVariable String spuCode) {
    logger.info("根据SPU编码查询商品SPU，请求参数: spuCode={}, operator={}", spuCode, OPERATOR);
    try {
      if (!StringUtils.hasText(spuCode)) {
        return ResponseEntity.badRequest().body("SPU编码不能为空");
      }

      ProductSpu productSpu = productSpuService.getBySpuCode(spuCode);
      if (productSpu == null) {
        return ResponseEntity.ok().body("商品SPU不存在");
      }
      logger.info("根据SPU编码查询商品SPU成功，spuCode: {}, operator: {}", spuCode, OPERATOR);
      return ResponseEntity.ok(Result.success(productSpu));
    } catch (Exception e) {
      logger.error("根据SPU编码查询商品SPU异常，spuCode: {}, operator: {}", spuCode, OPERATOR, e);
       return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    }
  }


  /**
   * 根据主键查找数据
   * 根据情感意图ID查询推荐商品列表（包含主品类信息）
   * 查询记录默认为6
   */
  @GetMapping("/getRecommendProducts/{intentId}/{limit}")
  @ApiOperation(value = "根据情感意图ID查询推荐商品列表", notes = "根据情感意图ID查询推荐商品列表")
  public ResponseEntity<Object> getRecommendProducts(
    @ApiParam(value = "情感意图主键", required = true)
    @PathVariable String intentId,
    @ApiParam(value = "记录数", required = true)
    @PathVariable Integer limit) {
    logger.info("根据情感意图ID查询推荐商品列表，请求参数: id={}, operator={}", intentId, OPERATOR);
    try {
      if (!StringUtils.hasText(intentId)) {
        return ResponseEntity.badRequest().body(Result.error("参数为空"));
      }
      List<ProductSpuSkuDTO> productSpu = productSpuService.getRecommendProducts(intentId,limit);
      if (productSpu == null) {
        logger.info("根据情感意图ID查询推荐商品列表，操作人：{}，ID：{}", OPERATOR, intentId);
        return ResponseEntity.ok(Result.success("未找到对应数据"));
      }
      logger.info("根据情感意图ID查询推荐商品列表，操作人：{}，结果：{}", OPERATOR, productSpu);
      return ResponseEntity.ok(Result.success(productSpu));
    } catch (Exception e) {
      logger.error("根据情感意图ID查询推荐商品列表，操作人：{}，参数：id={}，异常信息：", OPERATOR, intentId, e);
      return ResponseEntity.badRequest().body(Result.error("查询失败"));
    }
  }
  /**
   * 根据情感意图ID查询关联的SPU列表
   *
   * @param intentId 情感意图ID
   * @return SPU列表
   */
  @GetMapping("/selectSpuByIntentId/{intentId}")
  @ApiOperation(value = "根据情感意图ID查询商品列表", notes = "根据情感意图ID查询商品列表")
  public ResponseEntity<Object> selectSpuByIntentId(
    @ApiParam(value = "情感意图主键", required = true)
    @PathVariable String intentId) {
    logger.info("根据情感意图ID查询商品列表，请求参数: id={}, operator={}", intentId, OPERATOR);
    try {
      if (!StringUtils.hasText(intentId)) {
        return ResponseEntity.badRequest().body(Result.error("参数为空"));
      }
      List<ProductSpu> productSpu = productSpuService.selectSpuByIntentId(intentId);
      if (productSpu == null) {
        logger.info("根据情感意图ID查询商品列表，操作人：{}，ID：{}", OPERATOR, intentId);
        return ResponseEntity.ok(Result.success("未找到对应数据"));
      }
      logger.info("根据情感意图ID查询商品列表，操作人：{}，结果：{}", OPERATOR, productSpu);
      return ResponseEntity.ok(Result.success(productSpu));
    } catch (Exception e) {
      logger.error("根据情感意图ID查询商品列表，操作人：{}，参数：id={}，异常信息：", OPERATOR, intentId, e);
      return ResponseEntity.badRequest().body(Result.error("查询失败"));
    }
  }

  /**
   * 根据情感意图和类型标签ID查询商品列表
   *
   * @param intentId 情感意图ID
   * @param tagId 标签ID
   * @return 商品详情列表
   */
  @GetMapping("/getProductsByIntentAndTag/{intentId}/{tagId}")
  @ApiOperation(value = "根据情感意图ID和类型标签ID查询商品列表", notes = "根据情感意图ID和类型标签ID查询商品列表")
  public ResponseEntity<Object> selectProductsByIntentAndTag(
    @ApiParam(value = "情感意图主键", required = true)
    @PathVariable String intentId,
    @ApiParam(value = "类型标签主键", required = true)
    @PathVariable String tagId) {
    logger.info("根据情感意图ID和类型标签ID查询商品列表，请求参数: id={}, operator={}", intentId, OPERATOR);
    try {
      if (!StringUtils.hasText(intentId)) {
        return ResponseEntity.badRequest().body(Result.error("参数为空"));
      }
      List<ProductSpu> productSpuList = productSpuService.getSpuByCategoryTagAndIntent(tagId,intentId);
      if (productSpuList == null) {
        logger.info("根据情感意图ID和类型标签ID查询商品列表，操作人：{}，ID：{}", OPERATOR, intentId);
        return ResponseEntity.ok(Result.success("未找到对应数据"));
      }
      logger.info("根据情感意图ID和类型标签ID查询商品列表，操作人：{}，结果：{}", OPERATOR, productSpuList);
      return ResponseEntity.ok(Result.success(productSpuList));
    } catch (Exception e) {
      logger.error("根据情感意图ID和类型标签ID查询商品列表，操作人：{}，参数：id={}，异常信息：", OPERATOR, intentId, e);
      return ResponseEntity.badRequest().body(Result.error("查询失败"));
    }
  }

  /**
   * 根据五行元素标签和意图查询商品SPU
   */
  @GetMapping("/getProductsByIntentAndElementTag/{intentId}/{elementTagId}")
  @ApiOperation(value = "根据五行元素标签和意图查询商品SPU",
    notes = "根据五行元素标签ID和意图ID查询关联的商品SPU列表")
  public ResponseEntity<Object> selectProductsByIntentAndElementTag(
    @ApiParam(value = "五行元素标签ID", required = true) @PathVariable String elementTagId,
    @ApiParam(value = "意图ID", required = true) @PathVariable String intentId) {
    logger.info("根据五行元素标签和意图查询商品SPU，请求参数: id={}, operator={}", intentId, OPERATOR);
    try {
      if (!StringUtils.hasText(intentId)) {
        return ResponseEntity.badRequest().body(Result.error("参数为空"));
      }
      List<ProductSpu> productSpuList = productSpuService.getSpuByElementTagAndIntent(elementTagId,intentId);
      if (productSpuList == null) {
        logger.info("根据五行元素标签和意图查询商品SPU，操作人：{}，ID：{}", OPERATOR, intentId);
        return ResponseEntity.ok(Result.success("未找到对应数据"));
      }
      logger.info("根据五行元素标签和意图查询商品SPU，操作人：{}，结果：{}", OPERATOR, productSpuList);
      return ResponseEntity.ok(Result.success(productSpuList));
    } catch (Exception e) {
      logger.error("根据五行元素标签和意图查询商品SPU，操作人：{}，参数：id={}，异常信息：", OPERATOR, intentId, e);
      return ResponseEntity.badRequest().body(Result.error("查询失败"));
    }
    }
}
