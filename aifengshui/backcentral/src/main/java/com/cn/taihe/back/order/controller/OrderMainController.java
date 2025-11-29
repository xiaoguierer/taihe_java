package com.cn.taihe.back.order.controller;

import com.cn.taihe.back.order.entity.OrderMain;
import com.cn.taihe.back.order.dto.OrderMainCreateDTO;
import com.cn.taihe.back.order.dto.OrderMainQueryDTO;
import com.cn.taihe.back.order.dto.OrderMainUpdateDTO;
import com.cn.taihe.back.order.service.OrderMainService;
import com.cn.taihe.common.Result;
import com.cn.taihe.loginstiats.RequireLogin;
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
 * 订单主表Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单管理接口")
@RequireLogin  // 可以在某个具体的接口上  若某个接口不需要登录状态  在某个接口上添加@AllowAnonymous  // ← 单独放开这个接口
public class OrderMainController {

  private static final Logger logger = LoggerFactory.getLogger(OrderMainController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderMainService orderMainService;

  /**
   * 根据主键查询订单
   */
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "根据ID查询订单详情")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "订单ID", required = true)
    @PathVariable String id) {
    try {
      logger.info("查询订单详情接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderMain result = orderMainService.getById(id);
      logger.info("查询订单详情接口调用成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询订单详情接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询订单详情失败"));
    }
  }

  /**
   * 根据用户ID查询订单列表
   */
  @GetMapping("/user/{userId}")
  @ApiOperation(value = "根据用户ID查询订单列表")
  public ResponseEntity<Object> getByUserId(
    @ApiParam(value = "用户ID", required = true)
    @PathVariable String userId) {
    try {
      logger.info("根据用户ID查询订单列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, userId);
      List<OrderMain> result = orderMainService.getByUserId(userId);
      logger.info("根据用户ID查询订单列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据用户ID查询订单列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, userId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据用户ID查询订单列表失败"));
    }
  }

  /**
   * 条件分页查询订单列表
   */
  @PostMapping("/page")
  @ApiOperation(value = "条件分页查询订单列表")
  public ResponseEntity<Object> getByCondition(
    @ApiParam(value = "查询条件")
    @Valid @RequestBody OrderMainQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {
    try {
      logger.info("条件分页查询订单列表接口调用开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);
      PageInfo<OrderMain> result = orderMainService.getByCondition(queryDTO, page, size);
      logger.info("条件分页查询订单列表接口调用成功 - 操作人: {}, 总记录数: {}", OPERATOR, result.getTotal());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("条件分页查询订单列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("条件分页查询订单列表失败"));
    }
  }

  /**
   * 新增订单
   */
  @PostMapping(value = "/add")
  @ApiOperation(value = "新增订单")
  public ResponseEntity<Object> create(
    @ApiParam(value = "订单信息", required = true)
    @Valid @RequestBody OrderMainCreateDTO createDTO) {
    try {
      logger.info("新增订单接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
      boolean result = orderMainService.create(createDTO);
      logger.info("新增订单接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("新增订单接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("新增订单失败"));
    }
  }

  /**
   * 更新订单
   */
  @PutMapping(value = "/update")
  @ApiOperation(value = "更新订单")
  public ResponseEntity<Object> update(
    @ApiParam(value = "订单信息", required = true)
    @Valid @RequestBody OrderMainUpdateDTO updateDTO) {
    try {
      logger.info("更新订单接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
      boolean result = orderMainService.update(updateDTO);
      logger.info("更新订单接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新订单成功"))
        : ResponseEntity.badRequest().body(Result.error("更新订单失败"));
    } catch (Exception e) {
      logger.error("更新订单接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, updateDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新订单失败"));
    }
  }

  /**
   * 根据主键删除订单
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "根据ID删除订单")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "订单ID", required = true)
    @PathVariable String id) {
    try {
      logger.info("删除订单接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      boolean result = orderMainService.deleteById(id);
      logger.info("删除订单接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("删除订单成功"))
        : ResponseEntity.badRequest().body(Result.error("删除订单失败"));
    } catch (Exception e) {
      logger.error("删除订单接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("删除订单失败"));
    }
  }

  /**
   * 批量删除订单
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "批量删除订单")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "订单ID列表", required = true)
    @RequestBody List<String> ids) {
    try {
      logger.info("批量删除订单接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, ids);
      boolean result = orderMainService.deleteBatch(ids);
      logger.info("批量删除订单接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("批量删除订单成功"))
        : ResponseEntity.badRequest().body(Result.error("批量删除订单失败"));
    } catch (Exception e) {
      logger.error("批量删除订单接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, ids, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("批量删除订单失败"));
    }
  }

  /**
   * 更新订单状态
   */
  @PutMapping("/{id}/status/{status}")
  @ApiOperation(value = "更新订单状态")
  public ResponseEntity<Object> updateStatus(
    @ApiParam(value = "订单ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "状态:1待付款 2待发货 3待收货 4已完成 5已关闭", required = true)
    @PathVariable Integer status) {
    try {
      logger.info("更新订单状态接口调用开始 - 操作人: {}, 参数: id={}, status={}", OPERATOR, id, status);
      boolean result = orderMainService.updateStatus(id, status);
      logger.info("更新订单状态接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新订单状态成功"))
        : ResponseEntity.badRequest().body(Result.error("更新订单状态失败"));
    } catch (Exception e) {
      logger.error("更新订单状态接口调用失败 - 操作人: {}, 参数: id={}, status={}, 错误: {}",
        OPERATOR, id, status, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新订单状态失败"));
    }
  }
}

