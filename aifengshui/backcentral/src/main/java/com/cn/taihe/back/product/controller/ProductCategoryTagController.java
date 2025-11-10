package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.dto.ProductCategoryTagCreateDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagQueryDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagUpdateDTO;
import com.cn.taihe.back.product.entity.ProductCategoryTag;
import com.cn.taihe.back.product.service.ProductCategoryTagService;
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
  @GetMapping("/{id}")
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
      return ResponseEntity.ok().body(createSuccessResponse(result));
    } catch (Exception e) {
      logger.error("查询商品品类标签详情异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(createErrorResponse("查询失败：" + e.getMessage()));
    }
  }

  /**
   * 新增商品品类标签
   */
  @PostMapping
  @ApiOperation(value = "新增商品品类标签", notes = "创建新的商品品类标签")
  public ResponseEntity<Object> create(
    @ApiParam(value = "新增参数", required = true)
    @Valid @RequestBody ProductCategoryTagCreateDTO createDTO) {
    logger.info("新增商品品类标签开始，操作人：{}，参数：{}", OPERATOR, createDTO);

    try {
      boolean success = productCategoryTagService.create(createDTO);
      if (success) {
        logger.info("新增商品品类标签成功，操作人：{}", OPERATOR);
        return ResponseEntity.ok().body(createSuccessResponse("新增成功"));
      } else {
        logger.warn("新增商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(createErrorResponse("新增失败"));
      }
    } catch (Exception e) {
      logger.error("新增商品品类标签异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      return ResponseEntity.badRequest().body(createErrorResponse("新增失败：" + e.getMessage()));
    }
  }

  /**
   * 更新商品品类标签
   */
  @PutMapping
  @ApiOperation(value = "更新商品品类标签", notes = "根据ID更新商品品类标签信息")
  public ResponseEntity<Object> update(
    @ApiParam(value = "更新参数", required = true)
    @Valid @RequestBody ProductCategoryTagUpdateDTO updateDTO) {
    logger.info("更新商品品类标签开始，操作人：{}，参数：{}", OPERATOR, updateDTO);

    try {
      boolean success = productCategoryTagService.update(updateDTO);
      if (success) {
        logger.info("更新商品品类标签成功，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
        return ResponseEntity.ok().body(createSuccessResponse("更新成功"));
      } else {
        logger.warn("更新商品品类标签失败，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
        return ResponseEntity.badRequest().body(createErrorResponse("更新失败，记录不存在或更新异常"));
      }
    } catch (Exception e) {
      logger.error("更新商品品类标签异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      return ResponseEntity.badRequest().body(createErrorResponse("更新失败：" + e.getMessage()));
    }
  }

  /**
   * 根据主键删除商品品类标签
   */
  @DeleteMapping("/{id}")
  @ApiOperation(value = "删除商品品类标签", notes = "根据主键ID删除商品品类标签")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "主键ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("删除商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);

    try {
      boolean success = productCategoryTagService.deleteById(id);
      if (success) {
        logger.info("删除商品品类标签成功，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok().body(createSuccessResponse("删除成功"));
      } else {
        logger.warn("删除商品品类标签失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.badRequest().body(createErrorResponse("删除失败，记录不存在或删除异常"));
      }
    } catch (Exception e) {
      logger.error("删除商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(createErrorResponse("删除失败：" + e.getMessage()));
    }
  }

  /**
   * 条件分页查询商品品类标签列表
   */
  @GetMapping("/page")
  @ApiOperation(value = "分页查询商品品类标签列表", notes = "根据条件分页查询商品品类标签列表")
  public ResponseEntity<Object> getPageList(
    @ApiParam(value = "查询条件") ProductCategoryTagQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页数量", defaultValue = "10") @RequestParam(defaultValue = "10") int size) {
    logger.info("分页查询商品品类标签列表开始，操作人：{}，参数：queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);

    try {
      PageInfo<ProductCategoryTag> pageInfo = productCategoryTagService.getPageList(queryDTO, page, size);
      logger.info("分页查询商品品类标签列表成功，操作人：{}，总记录数：{}", OPERATOR, pageInfo.getTotal());
      return ResponseEntity.ok().body(createSuccessResponse(pageInfo));
    } catch (Exception e) {
      logger.error("分页查询商品品类标签列表异常，操作人：{}，参数：queryDTO={}, page={}, size={}，异常信息：", OPERATOR, queryDTO, page, size, e);
      return ResponseEntity.badRequest().body(createErrorResponse("查询失败：" + e.getMessage()));
    }
  }

  /**
   * 条件查询商品品类标签列表（不分页）
   */
  @GetMapping("/list")
  @ApiOperation(value = "查询商品品类标签列表", notes = "根据条件查询商品品类标签列表（不分页）")
  public ResponseEntity<Object> getList(
    @ApiParam(value = "查询条件") ProductCategoryTagQueryDTO queryDTO) {
    logger.info("查询商品品类标签列表开始，操作人：{}，参数：{}", OPERATOR, queryDTO);

    try {
      List<ProductCategoryTag> list = productCategoryTagService.getList(queryDTO);
      logger.info("查询商品品类标签列表成功，操作人：{}，记录数：{}", OPERATOR, list.size());
      return ResponseEntity.ok().body(createSuccessResponse(list));
    } catch (Exception e) {
      logger.error("查询商品品类标签列表异常，操作人：{}，参数：{}，异常信息：", OPERATOR, queryDTO, e);
      return ResponseEntity.badRequest().body(createErrorResponse("查询失败：" + e.getMessage()));
    }
  }

  /**
   * 查询所有商品品类标签
   */
  @GetMapping("/all")
  @ApiOperation(value = "查询所有商品品类标签", notes = "查询所有商品品类标签列表")
  public ResponseEntity<Object> getAll() {
    logger.info("查询所有商品品类标签开始，操作人：{}", OPERATOR);

    try {
      List<ProductCategoryTag> list = productCategoryTagService.getAll();
      logger.info("查询所有商品品类标签成功，操作人：{}，记录数：{}", OPERATOR, list.size());
      return ResponseEntity.ok().body(createSuccessResponse(list));
    } catch (Exception e) {
      logger.error("查询所有商品品类标签异常，操作人：{}，异常信息：", OPERATOR, e);
      return ResponseEntity.badRequest().body(createErrorResponse("查询失败：" + e.getMessage()));
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
        return ResponseEntity.ok().body(createSuccessResponse("批量删除成功"));
      } else {
        logger.warn("批量删除商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(createErrorResponse("批量删除失败"));
      }
    } catch (Exception e) {
      logger.error("批量删除商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(createErrorResponse("批量删除失败：" + e.getMessage()));
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
        return ResponseEntity.ok().body(createSuccessResponse("冻结成功"));
      } else {
        logger.warn("冻结商品品类标签失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.badRequest().body(createErrorResponse("冻结失败，记录不存在或操作异常"));
      }
    } catch (Exception e) {
      logger.error("冻结商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(createErrorResponse("冻结失败：" + e.getMessage()));
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
        return ResponseEntity.ok().body(createSuccessResponse("解冻成功"));
      } else {
        logger.warn("解冻商品品类标签失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.badRequest().body(createErrorResponse("解冻失败，记录不存在或操作异常"));
      }
    } catch (Exception e) {
      logger.error("解冻商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(createErrorResponse("解冻失败：" + e.getMessage()));
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
        return ResponseEntity.ok().body(createSuccessResponse("批量冻结成功"));
      } else {
        logger.warn("批量冻结商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(createErrorResponse("批量冻结失败"));
      }
    } catch (Exception e) {
      logger.error("批量冻结商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(createErrorResponse("批量冻结失败：" + e.getMessage()));
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
        return ResponseEntity.ok().body(createSuccessResponse("批量解冻成功"));
      } else {
        logger.warn("批量解冻商品品类标签失败，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(createErrorResponse("批量解冻失败"));
      }
    } catch (Exception e) {
      logger.error("批量解冻商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(createErrorResponse("批量解冻失败：" + e.getMessage()));
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
      return ResponseEntity.ok().body(createSuccessResponse(list));
    } catch (Exception e) {
      logger.error("根据父标签查询子标签异常，操作人：{}，参数：parentTagId={}，异常信息：", OPERATOR, parentTagId, e);
      return ResponseEntity.badRequest().body(createErrorResponse("查询失败：" + e.getMessage()));
    }
  }

  /**
   * 创建成功响应
   */
  public Object createSuccessResponse(Object data) {
    // 这里使用统一的响应格式，您可以根据项目实际响应格式调整
    return new ResponseResult<>(200, "成功", data);
  }

  /**
   * 创建错误响应
   */
  public Object createErrorResponse(String message) {
    // 这里使用统一的响应格式，您可以根据项目实际响应格式调整
    return new ResponseResult<>(400, message, null);
  }

  /**
   * 统一响应结果类（示例，请根据项目实际情况调整）
   */
  private static class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    public ResponseResult(int code, String message, T data) {
      this.code = code;
      this.message = message;
      this.data = data;
    }

    // getter and setter 方法
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
  }
}
