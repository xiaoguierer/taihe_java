package com.cn.taihe.back.shoppingcart.controller;

import com.cn.taihe.back.shoppingcart.dto.request.ShopingcartCreateDTO;
import com.cn.taihe.back.shoppingcart.dto.request.ShopingcartUpdateDTO;
import com.cn.taihe.back.shoppingcart.entity.Shopingcart;
import com.cn.taihe.back.shoppingcart.service.ShopingcartService;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 购物车商品表 Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/shopingcart")
@Api(tags = "购物车管理接口")
public class ShopingcartController {

  private static final Logger logger = LoggerFactory.getLogger(ShopingcartController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ShopingcartService shopingcartService;

  /**
   * 根据主键查找数据
   */
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "根据ID查询购物车项", notes = "根据主键ID查询购物车项详细信息")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "购物车项ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id) {
    logger.info("查询购物车项接口开始 - 操作人: {}, 参数: id={}", OPERATOR, id);
    try {
      Shopingcart result = shopingcartService.getById(id);
      logger.info("查询购物车项接口成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询购物车项接口异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询购物车项失败"));
    }
  }

  /**
   * 新增数据
   */
  @PostMapping(value = "/add")
  @ApiOperation(value = "新增购物车项", notes = "添加新的购物车项")
  public ResponseEntity<Object> save(
    @Valid @RequestBody ShopingcartCreateDTO createDTO) {
    logger.info("新增购物车项接口开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
    try {
      Shopingcart shopingcart = new Shopingcart();
      BeanUtils.copyProperties(createDTO, shopingcart);

      Boolean result = shopingcartService.save(shopingcart);
      logger.info("新增购物车项接口完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("新增购物车项接口异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, createDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("新增购物车项失败"));
    }
  }

  /**
   * 修改数据
   */
  @PutMapping(value = "/update")
  @ApiOperation(value = "修改购物车项", notes = "根据ID修改购物车项信息")
  public ResponseEntity<Object> update(
    @Valid @RequestBody ShopingcartUpdateDTO updateDTO) {
    logger.info("修改购物车项接口开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
    try {
      Shopingcart shopingcart = new Shopingcart();
      BeanUtils.copyProperties(updateDTO, shopingcart);

      Boolean result = shopingcartService.updateById(shopingcart);
      logger.info("修改购物车项接口完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("修改购物车项接口异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, updateDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("修改购物车项失败"));
    }
  }

  /**
   * 根据主键删除数据
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "删除购物车项", notes = "根据ID删除购物车项")
  public ResponseEntity<Object> removeById(
    @ApiParam(value = "购物车项ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id) {
    logger.info("删除购物车项接口开始 - 操作人: {}, 参数: id={}", OPERATOR, id);
    try {
      Boolean result = shopingcartService.removeById(id);
      logger.info("删除购物车项接口完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("删除购物车项接口异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("删除购物车项失败"));
    }
  }

  /**
   * 条件分页查询数据
   */
  @PostMapping("/page")
  @ApiOperation(value = "分页查询购物车项", notes = "根据条件分页查询购物车项列表")
  public ResponseEntity<Object> getByPage(
    @ApiParam(value = "查询条件") @RequestBody(required = false) Shopingcart queryDTO,
    @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer page,
    @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer size) {
    logger.info("分页查询购物车项接口开始 - 操作人: {}, 参数: queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);
    try {
      PageInfo<Shopingcart> result = shopingcartService.getByPage(queryDTO, page, size);
      logger.info("分页查询购物车项接口成功 - 操作人: {}, 总记录数: {}", OPERATOR, result.getTotal());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("分页查询购物车项接口异常 - 操作人: {}, 参数: queryDTO={}, page={}, size={}, 异常信息: {}",
        OPERATOR, queryDTO, page, size, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("分页查询购物车项失败"));
    }
  }

  /**
   * 批量删除数据
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "批量删除购物车项", notes = "根据ID集合批量删除购物车项")
  public ResponseEntity<Object> removeBatch(
    @ApiParam(value = "购物车项ID集合", required = true) @RequestBody List<String> ids) {
    logger.info("批量删除购物车项接口开始 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
    try {
      Boolean result = shopingcartService.removeBatchByIds(ids);
      logger.info("批量删除购物车项接口完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("批量删除购物车项接口异常 - 操作人: {}, 参数: ids={}, 异常信息: {}", OPERATOR, ids, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("批量删除购物车项失败"));
    }
  }

  /**
   * 更新选中状态（冻结功能）
   */
  @PutMapping("/{id}/selected/{selected}")
  @ApiOperation(value = "更新选中状态", notes = "更新购物车项的选中状态（冻结功能）")
  public ResponseEntity<Object> updateSelectedStatus(
    @ApiParam(value = "购物车项ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id,
    @ApiParam(value = "选中状态: 0-否, 1-是", required = true, allowableValues = "0,1")
    @PathVariable Integer selected) {
    logger.info("更新选中状态接口开始 - 操作人: {}, 参数: id={}, selected={}", OPERATOR, id, selected);
    try {
      Boolean result = shopingcartService.updateSelectedStatus(id, selected);
      logger.info("更新选中状态接口完成 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("更新选中状态接口异常 - 操作人: {}, 参数: id={}, selected={}, 异常信息: {}",
        OPERATOR, id, selected, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新选中状态失败"));
    }
  }

  /**
   * 根据用户ID查询购物车列表
   */
  @GetMapping("/user/{userId}")
  @ApiOperation(value = "根据用户ID查询购物车", notes = "根据用户ID查询该用户的购物车列表")
  public ResponseEntity<Object> getByUserId(
    @ApiParam(value = "用户ID", required = true, example = "user123")
    @PathVariable String userId) {
    logger.info("根据用户ID查询购物车接口开始 - 操作人: {}, 参数: userId={}", OPERATOR, userId);
    try {
      List<Shopingcart> result = shopingcartService.getByUserId(userId);
      logger.info("根据用户ID查询购物车接口成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据用户ID查询购物车接口异常 - 操作人: {}, 参数: userId={}, 异常信息: {}",
        OPERATOR, userId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据用户ID查询购物车失败"));
    }
  }
}
