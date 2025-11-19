package com.cn.taihe.back.order.mapper;

import com.cn.taihe.back.order.entity.OrderPayment;
import com.cn.taihe.back.order.dto.OrderPaymentQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单支付表Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface OrderPaymentMapper {

  /**
   * 根据主键查询订单支付
   */
  OrderPayment selectById(@Param("id") String id);

  /**
   * 根据订单ID查询订单支付列表
   */
  List<OrderPayment> selectByOrderId(@Param("orderId") String orderId);

  /**
   * 根据支付状态查询订单支付列表
   */
  List<OrderPayment> selectByPaymentStatus(@Param("paymentStatus") String paymentStatus);

  /**
   * 根据支付平台交易号查询订单支付
   */
  OrderPayment selectByGatewayTradeNo(@Param("gatewayTradeNo") String gatewayTradeNo);

  /**
   * 新增订单支付
   */
  int insert(OrderPayment orderPayment);

  /**
   * 条件查询订单支付列表（支持分页）
   */
  List<OrderPayment> selectByCondition(OrderPaymentQueryDTO queryDTO);

  /**
   * 根据主键更新支付状态
   */
  int updatePaymentStatusById(@Param("id") String id, @Param("paymentStatus") String paymentStatus);

  /**
   * 更新支付成功信息
   */
  int updatePaymentSuccess(@Param("id") String id, @Param("gatewayTradeNo") String gatewayTradeNo,
                           @Param("paidTime") java.time.LocalDateTime paidTime, @Param("paymentStatus") String paymentStatus);
}
