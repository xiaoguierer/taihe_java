package com.cn.taihe.back.order.controller;

import com.cn.taihe.back.order.entity.OrderItem;
import com.cn.taihe.back.order.dto.OrderItemCreateDTO;
import com.cn.taihe.back.order.dto.OrderItemQueryDTO;
import com.cn.taihe.back.order.dto.OrderItemUpdateDTO;
import com.cn.taihe.back.order.service.OrderItemService;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品表Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/order-item")
@Api(tags = "订单商品管理接口")
@RequireLogin
public class OrderItemController {

  private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderItemService orderItemService;

  /**
   * 根据主键查询订单商品
   */
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "根据ID查询订单商品详情")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "订单商品ID", required = true)
    @PathVariable String id) {
    try {
      logger.info("查询订单商品详情接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderItem result = orderItemService.getById(id);
      logger.info("查询订单商品详情接口调用成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询订单商品详情接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询订单商品详情失败"));
    }
  }

  /**
   * 根据订单ID查询订单商品列表
   */
  @GetMapping("/order/{orderId}")
  @ApiOperation(value = "根据订单ID查询订单商品列表")
  public ResponseEntity<Object> getByOrderId(
    @ApiParam(value = "订单ID", required = true)
    @PathVariable String orderId) {
    try {
      logger.info("根据订单ID查询订单商品列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);
      List<OrderItem> result = orderItemService.getByOrderId(orderId);
      logger.info("根据订单ID查询订单商品列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据订单ID查询订单商品列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, orderId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据订单ID查询订单商品列表失败"));
    }
  }

  /**
   * 根据SKU ID查询订单商品列表
   */
  @GetMapping("/sku/{skuId}")
  @ApiOperation(value = "根据SKU ID查询订单商品列表")
  public ResponseEntity<Object> getBySkuId(
    @ApiParam(value = "SKU ID", required = true)
    @PathVariable String skuId) {
    try {
      logger.info("根据SKU ID查询订单商品列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, skuId);
      List<OrderItem> result = orderItemService.getBySkuId(skuId);
      logger.info("根据SKU ID查询订单商品列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据SKU ID查询订单商品列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, skuId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据SKU ID查询订单商品列表失败"));
    }
  }

  /**
   * 条件分页查询订单商品列表
   */
  @PostMapping("/list")
  @ApiOperation(value = "条件分页查询订单商品列表")
  public ResponseEntity<Object> getByCondition(
    @ApiParam(value = "查询条件")
    @Valid @RequestBody OrderItemQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {
    try {
      logger.info("条件分页查询订单商品列表接口调用开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);
      PageInfo<OrderItem> result = orderItemService.getByCondition(queryDTO, page, size);
      logger.info("条件分页查询订单商品列表接口调用成功 - 操作人: {}, 总记录数: {}", OPERATOR, result.getTotal());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("条件分页查询订单商品列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("条件分页查询订单商品列表失败"));
    }
  }

  /**
   * 新增订单商品
   */
  @PostMapping(value = "/add")
  @ApiOperation(value = "新增订单商品")
  public ResponseEntity<Object> create(
    @ApiParam(value = "订单商品信息", required = true)
    @Valid @RequestBody OrderItemCreateDTO createDTO) {
    try {
      logger.info("新增订单商品接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
      boolean result = orderItemService.create(createDTO);
      logger.info("新增订单商品接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("新增订单商品成功"))
        : ResponseEntity.badRequest().body(Result.error("新增订单商品失败"));
    } catch (Exception e) {
      logger.error("新增订单商品接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("新增订单商品失败"));
    }
  }

  /**
   * 更新订单商品
   */
  @PutMapping(value = "/update")
  @ApiOperation(value = "更新订单商品")
  public ResponseEntity<Object> update(
    @ApiParam(value = "订单商品信息", required = true)
    @Valid @RequestBody OrderItemUpdateDTO updateDTO) {
    try {
      logger.info("更新订单商品接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
      boolean result = orderItemService.update(updateDTO);
      logger.info("更新订单商品接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新订单商品成功"))
        : ResponseEntity.badRequest().body(Result.error("更新订单商品失败"));
    } catch (Exception e) {
      logger.error("更新订单商品接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, updateDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新订单商品失败"));
    }
  }

  /**
   * 根据主键删除订单商品
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "根据ID删除订单商品")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "订单商品ID", required = true)
    @PathVariable String id) {
    try {
      logger.info("删除订单商品接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      boolean result = orderItemService.deleteById(id);
      logger.info("删除订单商品接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("删除订单商品成功"))
        : ResponseEntity.badRequest().body(Result.error("删除订单商品失败"));
    } catch (Exception e) {
      logger.error("删除订单商品接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("删除订单商品失败"));
    }
  }

  /**
   * 批量删除订单商品
   */
  @DeleteMapping("/batch")
  @ApiOperation(value = "批量删除订单商品")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "订单商品ID列表", required = true)
    @RequestBody List<String> ids) {
    try {
      logger.info("批量删除订单商品接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, ids);
      boolean result = orderItemService.deleteBatch(ids);
      logger.info("批量删除订单商品接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("批量删除订单商品成功"))
        : ResponseEntity.badRequest().body(Result.error("批量删除订单商品失败"));
    } catch (Exception e) {
      logger.error("批量删除订单商品接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, ids, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("批量删除订单商品失败"));
    }
  }

  /**
   * 根据订单ID批量删除订单商品
   */
  @DeleteMapping("/order/{orderId}")
  @ApiOperation(value = "根据订单ID批量删除订单商品")
  public ResponseEntity<Object> deleteByOrderId(
    @ApiParam(value = "订单ID", required = true)
    @PathVariable String orderId) {
    try {
      logger.info("根据订单ID批量删除订单商品接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);
      boolean result = orderItemService.deleteByOrderId(orderId);
      logger.info("根据订单ID批量删除订单商品接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("根据订单ID批量删除订单商品成功"))
        : ResponseEntity.badRequest().body(Result.error("根据订单ID批量删除订单商品失败"));
    } catch (Exception e) {
      logger.error("根据订单ID批量删除订单商品接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, orderId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据订单ID批量删除订单商品失败"));
    }
  }

  /**
   * 更新退款状态
   */
  @PutMapping("/{id}/refund-status/{refundStatus}")
  @ApiOperation(value = "更新退款状态")
  public ResponseEntity<Object> updateRefundStatus(
    @ApiParam(value = "订单商品ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "退款状态:0无退款 1部分退款 2全部退款", required = true)
    @PathVariable Integer refundStatus) {
    try {
      logger.info("更新退款状态接口调用开始 - 操作人: {}, 参数: id={}, refundStatus={}", OPERATOR, id, refundStatus);
      boolean result = orderItemService.updateRefundStatus(id, refundStatus);
      logger.info("更新退款状态接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新退款状态成功"))
        : ResponseEntity.badRequest().body(Result.error("更新退款状态失败"));
    } catch (Exception e) {
      logger.error("更新退款状态接口调用失败 - 操作人: {}, 参数: id={}, refundStatus={}, 错误: {}",
        OPERATOR, id, refundStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新退款状态失败"));
    }
  }

  /**
   * 更新退款信息
   */
  @PutMapping("/{id}/refund-info")
  @ApiOperation(value = "更新退款信息")
  public ResponseEntity<Object> updateRefundInfo(
    @ApiParam(value = "订单商品ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "已退款数量", required = true)
    @RequestParam Integer refundQuantity,
    @ApiParam(value = "已退款金额", required = true)
    @RequestParam BigDecimal refundAmount,
    @ApiParam(value = "退款状态:0无退款 1部分退款 2全部退款", required = true)
    @RequestParam Integer refundStatus) {
    try {
      logger.info("更新退款信息接口调用开始 - 操作人: {}, 参数: id={}, refundQuantity={}, refundAmount={}, refundStatus={}",
        OPERATOR, id, refundQuantity, refundAmount, refundStatus);
      boolean result = orderItemService.updateRefundInfo(id, refundQuantity, refundAmount, refundStatus);
      logger.info("更新退款信息接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新退款信息成功"))
        : ResponseEntity.badRequest().body(Result.error("更新退款信息失败"));
    } catch (Exception e) {
      logger.error("更新退款信息接口调用失败 - 操作人: {}, 参数: id={}, refundQuantity={}, refundAmount={}, refundStatus={}, 错误: {}",
        OPERATOR, id, refundQuantity, refundAmount, refundStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新退款信息失败"));
    }
  }
}
