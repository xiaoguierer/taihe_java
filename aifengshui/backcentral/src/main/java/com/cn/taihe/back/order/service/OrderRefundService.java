package com.cn.taihe.back.order.service;

import com.cn.taihe.back.order.entity.OrderRefund;
import com.cn.taihe.back.order.dto.OrderRefundCreateDTO;
import com.cn.taihe.back.order.dto.OrderRefundQueryDTO;
import com.cn.taihe.back.order.dto.OrderRefundUpdateDTO;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单退单表Service接口
 *
 * @author system
 */
public interface OrderRefundService {

  /**
   * 根据主键查询订单退单
   */
  OrderRefund getById(String id);

  /**
   * 根据退单号查询订单退单
   */
  OrderRefund getByRefundSn(String refundSn);

  /**
   * 根据订单ID查询订单退单列表
   */
  List<OrderRefund> getByOrderId(String orderId);

  /**
   * 根据用户ID查询订单退单列表
   */
  List<OrderRefund> getByUserId(String userId);

  /**
   * 根据退单状态查询订单退单列表
   */
  List<OrderRefund> getByRefundStatus(Integer refundStatus);

  /**
   * 条件分页查询订单退单列表
   */
  PageInfo<OrderRefund> getByCondition(OrderRefundQueryDTO queryDTO, int page, int size);

  /**
   * 新增订单退单
   */
  boolean create(OrderRefundCreateDTO createDTO);

  /**
   * 更新订单退单
   */
  boolean update(OrderRefundUpdateDTO updateDTO);

  /**
   * 更新退单状态
   */
  boolean updateRefundStatus(String id, Integer refundStatus);

  /**
   * 审核退单申请
   */
  boolean updateRefundReview(String id, String approverId, String approverNotes, Integer refundStatus, LocalDateTime reviewTime);

  /**
   * 更新退款信息
   */
  boolean updateRefundInfo(String id, BigDecimal actualAmount, Integer refundStatus, LocalDateTime refundTime);

  /**
   * 更新退货物流信息
   */
  boolean updateReturnLogistics(String id, String returnLogistics, String returnTrackingNo);
}
