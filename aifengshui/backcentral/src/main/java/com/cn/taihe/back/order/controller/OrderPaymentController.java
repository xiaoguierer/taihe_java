package com.cn.taihe.back.order.controller;

import com.cn.taihe.back.order.entity.OrderPayment;
import com.cn.taihe.back.order.dto.OrderPaymentCreateDTO;
import com.cn.taihe.back.order.dto.OrderPaymentQueryDTO;
import com.cn.taihe.back.order.service.OrderPaymentService;
import com.cn.taihe.common.Result;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单支付表Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/order-payment")
@Api(tags = "订单支付管理接口")
public class OrderPaymentController {

  private static final Logger logger = LoggerFactory.getLogger(OrderPaymentController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderPaymentService orderPaymentService;

  /**
   * 根据主键查询订单支付
   */
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "根据ID查询订单支付详情")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "支付ID", required = true)
    @PathVariable String id) {
    try {
      logger.info("查询订单支付详情接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderPayment result = orderPaymentService.getById(id);
      logger.info("查询订单支付详情接口调用成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询订单支付详情接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询订单支付详情失败"));
    }
  }

  /**
   * 根据订单ID查询订单支付列表
   */
  @GetMapping("/order/{orderId}")
  @ApiOperation(value = "根据订单ID查询订单支付列表")
  public ResponseEntity<Object> getByOrderId(
    @ApiParam(value = "订单ID", required = true)
    @PathVariable String orderId) {
    try {
      logger.info("根据订单ID查询订单支付列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);
      List<OrderPayment> result = orderPaymentService.getByOrderId(orderId);
      logger.info("根据订单ID查询订单支付列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据订单ID查询订单支付列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, orderId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据订单ID查询订单支付列表失败"));
    }
  }

  /**
   * 根据支付状态查询订单支付列表
   */
  @GetMapping("/status/{paymentStatus}")
  @ApiOperation(value = "根据支付状态查询订单支付列表")
  public ResponseEntity<Object> getByPaymentStatus(
    @ApiParam(value = "支付状态:pending,paid,failed,refunded", required = true)
    @PathVariable String paymentStatus) {
    try {
      logger.info("根据支付状态查询订单支付列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, paymentStatus);
      List<OrderPayment> result = orderPaymentService.getByPaymentStatus(paymentStatus);
      logger.info("根据支付状态查询订单支付列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据支付状态查询订单支付列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, paymentStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据支付状态查询订单支付列表失败"));
    }
  }

  /**
   * 根据支付平台交易号查询订单支付
   */
  @GetMapping("/gateway/{gatewayTradeNo}")
  @ApiOperation(value = "根据支付平台交易号查询订单支付")
  public ResponseEntity<Object> getByGatewayTradeNo(
    @ApiParam(value = "支付平台交易号", required = true)
    @PathVariable String gatewayTradeNo) {
    try {
      logger.info("根据支付平台交易号查询订单支付接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, gatewayTradeNo);
      OrderPayment result = orderPaymentService.getByGatewayTradeNo(gatewayTradeNo);
      logger.info("根据支付平台交易号查询订单支付接口调用成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据支付平台交易号查询订单支付接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, gatewayTradeNo, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据支付平台交易号查询订单支付失败"));
    }
  }

  /**
   * 条件分页查询订单支付列表
   */
  @PostMapping("/list")
  @ApiOperation(value = "条件分页查询订单支付列表")
  public ResponseEntity<Object> getByCondition(
    @ApiParam(value = "查询条件")
    @Valid @RequestBody OrderPaymentQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {
    try {
      logger.info("条件分页查询订单支付列表接口调用开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);
      PageInfo<OrderPayment> result = orderPaymentService.getByCondition(queryDTO, page, size);
      logger.info("条件分页查询订单支付列表接口调用成功 - 操作人: {}, 总记录数: {}", OPERATOR, result.getTotal());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("条件分页查询订单支付列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("条件分页查询订单支付列表失败"));
    }
  }

  /**
   * 新增订单支付
   */
  @PostMapping(value = "/add")
  @ApiOperation(value = "新增订单支付")
  public ResponseEntity<Object> create(
    @ApiParam(value = "订单支付信息", required = true)
    @Valid @RequestBody OrderPaymentCreateDTO createDTO) {
    try {
      logger.info("新增订单支付接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
      boolean result = orderPaymentService.create(createDTO);
      logger.info("新增订单支付接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("新增订单支付成功"))
        : ResponseEntity.badRequest().body(Result.error("新增订单支付失败"));
    } catch (Exception e) {
      logger.error("新增订单支付接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("新增订单支付失败"));
    }
  }

  /**
   * 更新支付状态
   */
  @PutMapping("/{id}/status/{paymentStatus}")
  @ApiOperation(value = "更新支付状态")
  public ResponseEntity<Object> updatePaymentStatus(
    @ApiParam(value = "支付ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "支付状态:pending,paid,failed,refunded", required = true)
    @PathVariable String paymentStatus) {
    try {
      logger.info("更新支付状态接口调用开始 - 操作人: {}, 参数: id={}, paymentStatus={}", OPERATOR, id, paymentStatus);
      boolean result = orderPaymentService.updatePaymentStatus(id, paymentStatus);
      logger.info("更新支付状态接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新支付状态成功"))
        : ResponseEntity.badRequest().body(Result.error("更新支付状态失败"));
    } catch (Exception e) {
      logger.error("更新支付状态接口调用失败 - 操作人: {}, 参数: id={}, paymentStatus={}, 错误: {}",
        OPERATOR, id, paymentStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新支付状态失败"));
    }
  }

  /**
   * 更新支付成功信息
   */
  @PutMapping("/{id}/payment-success")
  @ApiOperation(value = "更新支付成功信息")
  public ResponseEntity<Object> updatePaymentSuccess(
    @ApiParam(value = "支付ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "支付平台交易号", required = true)
    @RequestParam String gatewayTradeNo,
    @ApiParam(value = "支付成功时间", required = true)
    @RequestParam LocalDateTime paidTime,
    @ApiParam(value = "支付状态", required = true)
    @RequestParam String paymentStatus) {
    try {
      logger.info("更新支付成功信息接口调用开始 - 操作人: {}, 参数: id={}, gatewayTradeNo={}, paidTime={}, paymentStatus={}",
        OPERATOR, id, gatewayTradeNo, paidTime, paymentStatus);
      boolean result = orderPaymentService.updatePaymentSuccess(id, gatewayTradeNo, paidTime, paymentStatus);
      logger.info("更新支付成功信息接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新支付成功信息成功"))
        : ResponseEntity.badRequest().body(Result.error("更新支付成功信息失败"));
    } catch (Exception e) {
      logger.error("更新支付成功信息接口调用失败 - 操作人: {}, 参数: id={}, gatewayTradeNo={}, paidTime={}, paymentStatus={}, 错误: {}",
        OPERATOR, id, gatewayTradeNo, paidTime, paymentStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新支付成功信息失败"));
    }
  }
}
