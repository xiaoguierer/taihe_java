package com.cn.taihe.back.order.controller;

import com.cn.taihe.back.order.entity.OrderRefund;
import com.cn.taihe.back.order.dto.OrderRefundCreateDTO;
import com.cn.taihe.back.order.dto.OrderRefundQueryDTO;
import com.cn.taihe.back.order.dto.OrderRefundUpdateDTO;
import com.cn.taihe.back.order.service.OrderRefundService;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单退单表Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/order-refund")
@Api(tags = "订单退单管理接口")
@RequireLogin
public class OrderRefundController {

  private static final Logger logger = LoggerFactory.getLogger(OrderRefundController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderRefundService orderRefundService;

  /**
   * 根据主键查询订单退单
   */
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "根据ID查询订单退单详情")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "退单ID", required = true)
    @PathVariable String id) {
    try {
      logger.info("查询订单退单详情接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderRefund result = orderRefundService.getById(id);
      logger.info("查询订单退单详情接口调用成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询订单退单详情接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("查询订单退单详情失败"));
    }
  }

  /**
   * 根据退单号查询订单退单
   */
  @GetMapping("/sn/{refundSn}")
  @ApiOperation(value = "根据退单号查询订单退单")
  public ResponseEntity<Object> getByRefundSn(
    @ApiParam(value = "退单号", required = true)
    @PathVariable String refundSn) {
    try {
      logger.info("根据退单号查询订单退单接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, refundSn);
      OrderRefund result = orderRefundService.getByRefundSn(refundSn);
      logger.info("根据退单号查询订单退单接口调用成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据退单号查询订单退单接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, refundSn, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据退单号查询订单退单失败"));
    }
  }

  /**
   * 根据订单ID查询订单退单列表
   */
  @GetMapping("/order/{orderId}")
  @ApiOperation(value = "根据订单ID查询订单退单列表")
  public ResponseEntity<Object> getByOrderId(
    @ApiParam(value = "订单ID", required = true)
    @PathVariable String orderId) {
    try {
      logger.info("根据订单ID查询订单退单列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);
      List<OrderRefund> result = orderRefundService.getByOrderId(orderId);
      logger.info("根据订单ID查询订单退单列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据订单ID查询订单退单列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, orderId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据订单ID查询订单退单列表失败"));
    }
  }

  /**
   * 根据用户ID查询订单退单列表
   */
  @GetMapping("/user/{userId}")
  @ApiOperation(value = "根据用户ID查询订单退单列表")
  public ResponseEntity<Object> getByUserId(
    @ApiParam(value = "用户ID", required = true)
    @PathVariable String userId) {
    try {
      logger.info("根据用户ID查询订单退单列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, userId);
      List<OrderRefund> result = orderRefundService.getByUserId(userId);
      logger.info("根据用户ID查询订单退单列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据用户ID查询订单退单列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, userId, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据用户ID查询订单退单列表失败"));
    }
  }

  /**
   * 根据退单状态查询订单退单列表
   */
  @GetMapping("/status/{refundStatus}")
  @ApiOperation(value = "根据退单状态查询订单退单列表")
  public ResponseEntity<Object> getByRefundStatus(
    @ApiParam(value = "退单状态:1申请中 2审核通过 3审核拒绝 4退款中 5退款成功 6退款失败", required = true)
    @PathVariable Integer refundStatus) {
    try {
      logger.info("根据退单状态查询订单退单列表接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, refundStatus);
      List<OrderRefund> result = orderRefundService.getByRefundStatus(refundStatus);
      logger.info("根据退单状态查询订单退单列表接口调用成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("根据退单状态查询订单退单列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, refundStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("根据退单状态查询订单退单列表失败"));
    }
  }

  /**
   * 条件分页查询订单退单列表
   */
  @PostMapping("/list")
  @ApiOperation(value = "条件分页查询订单退单列表")
  public ResponseEntity<Object> getByCondition(
    @ApiParam(value = "查询条件")
    @Valid @RequestBody OrderRefundQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") int page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") int size) {
    try {
      logger.info("条件分页查询订单退单列表接口调用开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);
      PageInfo<OrderRefund> result = orderRefundService.getByCondition(queryDTO, page, size);
      logger.info("条件分页查询订单退单列表接口调用成功 - 操作人: {}, 总记录数: {}", OPERATOR, result.getTotal());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("条件分页查询订单退单列表接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("条件分页查询订单退单列表失败"));
    }
  }

  /**
   * 新增订单退单
   */
  @PostMapping(value = "/add")
  @ApiOperation(value = "新增订单退单")
  public ResponseEntity<Object> create(
    @ApiParam(value = "订单退单信息", required = true)
    @Valid @RequestBody OrderRefundCreateDTO createDTO) {
    try {
      logger.info("新增订单退单接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
      boolean result = orderRefundService.create(createDTO);
      logger.info("新增订单退单接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("新增订单退单成功"))
        : ResponseEntity.badRequest().body(Result.error("新增订单退单失败"));
    } catch (Exception e) {
      logger.error("新增订单退单接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("新增订单退单失败"));
    }
  }

  /**
   * 更新订单退单
   */
  @PutMapping(value = "update")
  @ApiOperation(value = "更新订单退单")
  public ResponseEntity<Object> update(
    @ApiParam(value = "订单退单信息", required = true)
    @Valid @RequestBody OrderRefundUpdateDTO updateDTO) {
    try {
      logger.info("更新订单退单接口调用开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
      boolean result = orderRefundService.update(updateDTO);
      logger.info("更新订单退单接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新订单退单成功"))
        : ResponseEntity.badRequest().body(Result.error("更新订单退单失败"));
    } catch (Exception e) {
      logger.error("更新订单退单接口调用失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, updateDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新订单退单失败"));
    }
  }

  /**
   * 更新退单状态
   */
  @PutMapping("/{id}/status/{refundStatus}")
  @ApiOperation(value = "更新退单状态")
  public ResponseEntity<Object> updateRefundStatus(
    @ApiParam(value = "退单ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "退单状态:1申请中 2审核通过 3审核拒绝 4退款中 5退款成功 6退款失败", required = true)
    @PathVariable Integer refundStatus) {
    try {
      logger.info("更新退单状态接口调用开始 - 操作人: {}, 参数: id={}, refundStatus={}", OPERATOR, id, refundStatus);
      boolean result = orderRefundService.updateRefundStatus(id, refundStatus);
      logger.info("更新退单状态接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新退单状态成功"))
        : ResponseEntity.badRequest().body(Result.error("更新退单状态失败"));
    } catch (Exception e) {
      logger.error("更新退单状态接口调用失败 - 操作人: {}, 参数: id={}, refundStatus={}, 错误: {}",
        OPERATOR, id, refundStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新退单状态失败"));
    }
  }

  /**
   * 审核退单申请
   */
  @PutMapping("/{id}/review")
  @ApiOperation(value = "审核退单申请")
  public ResponseEntity<Object> updateRefundReview(
    @ApiParam(value = "退单ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "审核人ID", required = true)
    @RequestParam String approverId,
    @ApiParam(value = "审核意见", required = true)
    @RequestParam String approverNotes,
    @ApiParam(value = "退单状态:2审核通过 3审核拒绝", required = true)
    @RequestParam Integer refundStatus,
    @ApiParam(value = "审核时间", required = true)
    @RequestParam LocalDateTime reviewTime) {
    try {
      logger.info("审核退单申请接口调用开始 - 操作人: {}, 参数: id={}, approverId={}, refundStatus={}",
        OPERATOR, id, approverId, refundStatus);
      boolean result = orderRefundService.updateRefundReview(id, approverId, approverNotes, refundStatus, reviewTime);
      logger.info("审核退单申请接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("审核退单申请成功"))
        : ResponseEntity.badRequest().body(Result.error("审核退单申请失败"));
    } catch (Exception e) {
      logger.error("审核退单申请接口调用失败 - 操作人: {}, 参数: id={}, approverId={}, refundStatus={}, 错误: {}",
        OPERATOR, id, approverId, refundStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("审核退单申请失败"));
    }
  }

  /**
   * 更新退款信息
   */
  @PutMapping("/{id}/refund-info")
  @ApiOperation(value = "更新退款信息")
  public ResponseEntity<Object> updateRefundInfo(
    @ApiParam(value = "退单ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "实际退款金额", required = true)
    @RequestParam BigDecimal actualAmount,
    @ApiParam(value = "退单状态:4退款中 5退款成功 6退款失败", required = true)
    @RequestParam Integer refundStatus,
    @ApiParam(value = "退款时间", required = true)
    @RequestParam LocalDateTime refundTime) {
    try {
      logger.info("更新退款信息接口调用开始 - 操作人: {}, 参数: id={}, actualAmount={}, refundStatus={}",
        OPERATOR, id, actualAmount, refundStatus);
      boolean result = orderRefundService.updateRefundInfo(id, actualAmount, refundStatus, refundTime);
      logger.info("更新退款信息接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新退款信息成功"))
        : ResponseEntity.badRequest().body(Result.error("更新退款信息失败"));
    } catch (Exception e) {
      logger.error("更新退款信息接口调用失败 - 操作人: {}, 参数: id={}, actualAmount={}, refundStatus={}, 错误: {}",
        OPERATOR, id, actualAmount, refundStatus, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新退款信息失败"));
    }
  }

  /**
   * 更新退货物流信息
   */
  @PutMapping("/{id}/return-logistics")
  @ApiOperation(value = "更新退货物流信息")
  public ResponseEntity<Object> updateReturnLogistics(
    @ApiParam(value = "退单ID", required = true)
    @PathVariable String id,
    @ApiParam(value = "退货物流公司", required = true)
    @RequestParam String returnLogistics,
    @ApiParam(value = "退货物流单号", required = true)
    @RequestParam String returnTrackingNo) {
    try {
      logger.info("更新退货物流信息接口调用开始 - 操作人: {}, 参数: id={}, returnLogistics={}, returnTrackingNo={}",
        OPERATOR, id, returnLogistics, returnTrackingNo);
      boolean result = orderRefundService.updateReturnLogistics(id, returnLogistics, returnTrackingNo);
      logger.info("更新退货物流信息接口调用{} - 操作人: {}", result ? "成功" : "失败", OPERATOR);
      return result ? ResponseEntity.ok(Result.success("更新退货物流信息成功"))
        : ResponseEntity.badRequest().body(Result.error("更新退货物流信息失败"));
    } catch (Exception e) {
      logger.error("更新退货物流信息接口调用失败 - 操作人: {}, 参数: id={}, returnLogistics={}, returnTrackingNo={}, 错误: {}",
        OPERATOR, id, returnLogistics, returnTrackingNo, e.getMessage(), e);
      return ResponseEntity.badRequest().body(Result.error("更新退货物流信息失败"));
    }
  }
}
