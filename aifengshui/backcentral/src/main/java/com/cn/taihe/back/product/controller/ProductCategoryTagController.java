package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.dto.CreateRelationsRequest;
import com.cn.taihe.back.product.dto.ProductCategoryTagCreateDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagQueryDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagUpdateDTO;
import com.cn.taihe.back.product.entity.ProductCategoryTag;
import com.cn.taihe.back.product.service.ProductCategoryTagService;
import com.cn.taihe.back.product.vo.ProductCategoryTagCountDTO;
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
 * 商品品类标签表 Controller
 *
 * @author auto generate
 * @since 2025-11-07
 */
@RestController
@RequestMapping("/product-category-tags")
@Api(tags = "商品品类标签管理")
public class ProductCategoryTagController {

  private static final Logger logger = LoggerFactory.getLogger(ProductCategoryTagController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductCategoryTagService productCategoryTagService;

  /**
   * 根据主键查询商品品类标签
   */
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "根据ID查询商品品类标签", notes = "根据主键ID查询商品品类标签详情")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "主键ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("查询商品品类标签详情开始，操作人：{}，参数：id={}", OPERATOR, id);
    try {
      ProductCategoryTag result = productCategoryTagService.getById(id);
      if (result == null) {
        logger.warn("查询商品品类标签详情失败，记录不存在，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.notFound().build();
      }
      logger.info("查询商品品类标签详情成功，操作人：{}，ID：{}", OPERATOR, id);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询商品品类标签详情异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 新增商品品类标签
   */
  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "新增商品品类标签", notes = "创建新的商品品类标签")
  public ResponseEntity<Object> create(
    @ApiParam(value = "新增参数", required = true)
    @Valid @RequestPart("createDTO") ProductCategoryTagCreateDTO createDTO,
    @RequestPart(value = "iconfile", required = false) MultipartFile iconfile,
    @RequestPart(value = "coverimagefile", required = false) MultipartFile coverimagefile,
    @RequestPart(value = "hoverimagefile", required = false) MultipartFile hoverimagefile) {
    logger.info("新增商品品类标签开始，操作人：{}，参数：{}", OPERATOR, createDTO);
    try {
      boolean success = productCategoryTagService.create(createDTO,iconfile,coverimagefile,hoverimagefile);
      if (success) {
        logger.info("新增商品品类标签成功，操作人：{}", OPERATOR);
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("新增商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("新增商品品类标签异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 更新商品品类标签
   */
  @PutMapping(value = "/updateByid", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "更新商品品类标签", notes = "根据ID更新商品品类标签信息")
  public ResponseEntity<Object> update(
    @ApiParam(value = "更新参数", required = true)
    @Valid @RequestPart("updateDTO") ProductCategoryTagUpdateDTO updateDTO,
    @RequestPart(value = "iconfile", required = false) MultipartFile iconfile,
    @RequestPart(value = "coverimagefile", required = false) MultipartFile coverimagefile,
    @RequestPart(value = "hoverimagefile", required = false) MultipartFile hoverimagefile) {
    logger.info("更新商品品类标签开始，操作人：{}，参数：{}", OPERATOR, updateDTO);
    try {
      boolean success = productCategoryTagService.update(updateDTO,iconfile,coverimagefile,hoverimagefile);
      if (success) {
        logger.info("更新商品品类标签成功，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("更新商品品类标签失败，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("更新商品品类标签异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }
  /**
   * 新增数据
   */
  @PostMapping(value = "/createRealiations")
  @ApiOperation(value = "新增SPU-商品标签")
  public ResponseEntity<Object> createRealiations(
    @RequestBody @Valid CreateRelationsRequest request) {
    logger.info("开始处理新增SPU-商品标签关系请求, 操作人: {}, 参数: {}", OPERATOR, request.getSpuId());
    int result = productCategoryTagService.createRealiations(request.getSpuId(),request.getIntentIds());
    logger.info("新增SPU-商品标签关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, result);
    return ResponseEntity.ok(Result.success(result));
  }
  /**
   * 根据主键删除商品品类标签
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "删除商品品类标签", notes = "根据主键ID删除商品品类标签")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("删除商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);
    try {
      boolean success = productCategoryTagService.deleteById(id);
      if (success) {
        logger.info("删除商品品类标签成功，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("删除商品品类标签失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("删除商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 条件分页查询商品品类标签列表
   */
  @PostMapping("/page")
  @ApiOperation(value = "分页查询商品品类标签列表", notes = "根据条件分页查询商品品类标签列表")
  public ResponseEntity<Object> getPageList(
    @ApiParam(value = "查询条件") ProductCategoryTagQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页数量", defaultValue = "10") @RequestParam(defaultValue = "10") int size) {
    logger.info("分页查询商品品类标签列表开始，操作人：{}，参数：queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);
    try {
      PageInfo<ProductCategoryTag> pageInfo = productCategoryTagService.getPageList(queryDTO, page, size);
      logger.info("分页查询商品品类标签列表成功，操作人：{}，总记录数：{}", OPERATOR, pageInfo.getTotal());
      return ResponseEntity.ok(Result.success(pageInfo));
    } catch (Exception e) {
      logger.error("分页查询商品品类标签列表异常，操作人：{}，参数：queryDTO={}, page={}, size={}，异常信息：", OPERATOR, queryDTO, page, size, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 条件查询商品品类标签列表（不分页）
   */
  @PostMapping("/list")
  @ApiOperation(value = "查询商品品类标签列表", notes = "根据条件查询商品品类标签列表（不分页）")
  public ResponseEntity<Object> getList(
    @ApiParam(value = "查询条件") ProductCategoryTagQueryDTO queryDTO) {
    logger.info("查询商品品类标签列表开始，操作人：{}，参数：{}", OPERATOR, queryDTO);

    try {
      List<ProductCategoryTag> list = productCategoryTagService.getList(queryDTO);
      logger.info("查询商品品类标签列表成功，操作人：{}，记录数：{}", OPERATOR, list.size());
      return ResponseEntity.ok(Result.success(list));
    } catch (Exception e) {
      logger.error("查询商品品类标签列表异常，操作人：{}，参数：{}，异常信息：", OPERATOR, queryDTO, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 查询所有商品品类标签
   */
  @PostMapping("/all")
  @ApiOperation(value = "查询所有商品品类标签", notes = "查询所有商品品类标签列表")
  public ResponseEntity<Object> getAll() {
    logger.info("查询所有商品品类标签开始，操作人：{}", OPERATOR);
    try {
      List<ProductCategoryTag> list = productCategoryTagService.getAll();
      logger.info("查询所有商品品类标签成功，操作人：{}，记录数：{}", OPERATOR, list.size());
      return ResponseEntity.ok(Result.success(list));
    } catch (Exception e) {
      logger.error("查询所有商品品类标签异常，操作人：{}，异常信息：", OPERATOR, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 批量删除商品品类标签
   */
  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除商品品类标签", notes = "根据ID列表批量删除商品品类标签")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量删除商品品类标签开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    try {
      boolean success = productCategoryTagService.deleteBatch(ids);
      if (success) {
        logger.info("批量删除商品品类标签成功，操作人：{}，删除记录数：{}", OPERATOR, ids.size());
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("批量删除商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("批量删除商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 冻结商品品类标签（禁用）
   */
  @PutMapping("/{id}/freeze")
  @ApiOperation(value = "冻结商品品类标签", notes = "根据ID冻结商品品类标签（禁用）")
  public ResponseEntity<Object> freeze(
    @ApiParam(value = "主键ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("冻结商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);
    try {
      boolean success = productCategoryTagService.freeze(id);
      if (success) {
        logger.info("冻结商品品类标签成功，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("冻结商品品类标签失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("冻结商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 解冻商品品类标签（启用）
   */
  @PutMapping("/{id}/unfreeze")
  @ApiOperation(value = "解冻商品品类标签", notes = "根据ID解冻商品品类标签（启用）")
  public ResponseEntity<Object> unfreeze(
    @ApiParam(value = "主键ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("解冻商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);

    try {
      boolean success = productCategoryTagService.unfreeze(id);
      if (success) {
        logger.info("解冻商品品类标签成功，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("解冻商品品类标签失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("解冻商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 批量冻结商品品类标签
   */
  @PostMapping("/batch-freeze")
  @ApiOperation(value = "批量冻结商品品类标签", notes = "根据ID列表批量冻结商品品类标签")
  public ResponseEntity<Object> freezeBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量冻结商品品类标签开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    try {
      boolean success = productCategoryTagService.freezeBatch(ids);
      if (success) {
        logger.info("批量冻结商品品类标签成功，操作人：{}，冻结记录数：{}", OPERATOR, ids.size());
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("批量冻结商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("批量冻结商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 批量解冻商品品类标签
   */
  @PostMapping("/batch-unfreeze")
  @ApiOperation(value = "批量解冻商品品类标签", notes = "根据ID列表批量解冻商品品类标签")
  public ResponseEntity<Object> unfreezeBatch(
    @ApiParam(value = "主键ID列表", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量解冻商品品类标签开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    try {
      boolean success = productCategoryTagService.unfreezeBatch(ids);
      if (success) {
        logger.info("批量解冻商品品类标签成功，操作人：{}，解冻记录数：{}", OPERATOR, ids.size());
        return ResponseEntity.ok(Result.success(success));
      } else {
        logger.warn("批量解冻商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.ok(Result.error("操作失败"));
      }
    } catch (Exception e) {
      logger.error("批量解冻商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 根据父标签ID查询子标签列表
   */
  @GetMapping("/parent/{parentTagId}")
  @ApiOperation(value = "根据父标签查询子标签", notes = "根据父标签ID查询子标签列表")
  public ResponseEntity<Object> getByParentTagId(
    @ApiParam(value = "父标签ID", required = true, example = "1234567890abcdef")
    @PathVariable String parentTagId) {
    logger.info("根据父标签查询子标签开始，操作人：{}，参数：parentTagId={}", OPERATOR, parentTagId);

    try {
      List<ProductCategoryTag> list = productCategoryTagService.getByParentTagId(parentTagId);
      logger.info("根据父标签查询子标签成功，操作人：{}，记录数：{}", OPERATOR, list.size());
      return ResponseEntity.ok(Result.success(list));
    } catch (Exception e) {
      logger.error("根据父标签查询子标签异常，操作人：{}，参数：parentTagId={}，异常信息：", OPERATOR, parentTagId, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }
  /**
   * @description:
   * 获取品类标签（首饰类型）
   * 根据情感意图id查询商品spu所属标签及数量
   **/
  @GetMapping("/JewelryTagByIntentId/{intentId}")
  @ApiOperation(value = "根据情感意图intentId查找所属商品的标签以及数量", notes = "根据情感意图查询商品品类标签详情")
  public ResponseEntity<Object> JewelryTagByIntentId(
    @ApiParam(value = "情感意图主键ID", required = true, example = "1234567890abcdef")
    @PathVariable String intentId) {
    logger.info("根据情感意图intentId查找所属商品的标签开始，操作人：{}，参数：id={}", OPERATOR, intentId);
    try {
      List<ProductCategoryTagCountDTO> result = productCategoryTagService.selectJewelryTagByIntentId(intentId);
      if (result == null) {
        logger.warn("根据情感意图intentId查找所属商品的标签失败，记录不存在，操作人：{}，ID：{}", OPERATOR, intentId);
        return ResponseEntity.notFound().build();
      }
      logger.info("根据情感意图intentId查找所属商品的标签成功，操作人：{}，ID：{}", OPERATOR, intentId);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据情感意图intentId查找所属商品的标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, intentId, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }
  /**
   * @description:
   * 根据情感意图id查询能量标签及数量
   * -- 获取能量属性标签（五行属性） ----------------------------------------
   * 	-- 逻辑关系：
   * -- 通过 product_spu_intent和 product_spu_categorytag的联合查询
   * -- 找到同时具有特定情感意图和特定品类/能量标签的SPU
   * -- 再通过 product_spu_sku_rel关联到具体的SKU
   * -- 对有效的SKU进行计数，得到每个标签的商品数量
   **/
  @GetMapping("/JEnergyInfoByIntentId/{intentId}")
  @ApiOperation(value = "根据情感意图intentId查找所属商品的标签以及数量", notes = "根据情感意图intentId查找所属商品的标签以及数量")
  public ResponseEntity<Object> JEnergyInfoByIntentId(
    @ApiParam(value = "情感意图主键ID", required = true, example = "1234567890abcdef")
    @PathVariable String intentId) {
    logger.info("根据情感意图intentId查找所属商品的标签以及数量开始，操作人：{}，参数：id={}", OPERATOR, intentId);
    try {
      List<ProductCategoryTagCountDTO> result = productCategoryTagService.selectEnergyInfoByIntentId(intentId);
      if (result == null) {
        logger.warn("根据情感意图intentId查找所属商品的标签以及数量失败，记录不存在，操作人：{}，ID：{}", OPERATOR, intentId);
        return ResponseEntity.notFound().build();
      }
      logger.info("根据情感意图intentId查找所属商品的标签以及数量成功，操作人：{}，ID：{}", OPERATOR, intentId);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据情感意图intentId查找所属商品的标签以及数量标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, intentId, e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }
}
