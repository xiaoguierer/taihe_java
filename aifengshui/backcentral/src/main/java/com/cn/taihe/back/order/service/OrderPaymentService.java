package com.cn.taihe.back.order.service;

import com.cn.taihe.back.order.entity.OrderPayment;
import com.cn.taihe.back.order.dto.OrderPaymentCreateDTO;
import com.cn.taihe.back.order.dto.OrderPaymentQueryDTO;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单支付表Service接口
 *
 * @author system
 */
public interface OrderPaymentService {

  /**
   * 根据主键查询订单支付
   */
  OrderPayment getById(String id);

  /**
   * 根据订单ID查询订单支付列表
   */
  List<OrderPayment> getByOrderId(String orderId);

  /**
   * 根据支付状态查询订单支付列表
   */
  List<OrderPayment> getByPaymentStatus(String paymentStatus);

  /**
   * 根据支付平台交易号查询订单支付
   */
  OrderPayment getByGatewayTradeNo(String gatewayTradeNo);

  /**
   * 条件分页查询订单支付列表
   */
  PageInfo<OrderPayment> getByCondition(OrderPaymentQueryDTO queryDTO, int page, int size);

  /**
   * 新增订单支付
   */
  boolean create(OrderPaymentCreateDTO createDTO);

  /**
   * 更新支付状态
   */
  boolean updatePaymentStatus(String id, String paymentStatus);

  /**
   * 更新支付成功信息
   */
  boolean updatePaymentSuccess(String id, String gatewayTradeNo, LocalDateTime paidTime, String paymentStatus);
}
