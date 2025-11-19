package com.cn.taihe.back.order.mapper;

import com.cn.taihe.back.order.entity.OrderRefund;
import com.cn.taihe.back.order.dto.OrderRefundQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单退单表Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface OrderRefundMapper {

  /**
   * 根据主键查询订单退单
   */
  OrderRefund selectById(@Param("id") String id);

  /**
   * 根据退单号查询订单退单
   */
  OrderRefund selectByRefundSn(@Param("refundSn") String refundSn);

  /**
   * 根据订单ID查询订单退单列表
   */
  List<OrderRefund> selectByOrderId(@Param("orderId") String orderId);

  /**
   * 根据用户ID查询订单退单列表
   */
  List<OrderRefund> selectByUserId(@Param("userId") String userId);

  /**
   * 根据退单状态查询订单退单列表
   */
  List<OrderRefund> selectByRefundStatus(@Param("refundStatus") Integer refundStatus);

  /**
   * 新增订单退单
   */
  int insert(OrderRefund orderRefund);

  /**
   * 根据主键更新订单退单（只更新非空字段）
   */
  int updateById(OrderRefund orderRefund);

  /**
   * 条件查询订单退单列表（支持分页）
   */
  List<OrderRefund> selectByCondition(OrderRefundQueryDTO queryDTO);

  /**
   * 根据主键更新退单状态
   */
  int updateRefundStatusById(@Param("id") String id, @Param("refundStatus") Integer refundStatus);

  /**
   * 审核退单申请
   */
  int updateRefundReview(@Param("id") String id, @Param("approverId") String approverId,
                         @Param("approverNotes") String approverNotes, @Param("refundStatus") Integer refundStatus,
                         @Param("reviewTime") java.time.LocalDateTime reviewTime);

  /**
   * 更新退款信息
   */
  int updateRefundInfo(@Param("id") String id, @Param("actualAmount") java.math.BigDecimal actualAmount,
                       @Param("refundStatus") Integer refundStatus, @Param("refundTime") java.time.LocalDateTime refundTime);

  /**
   * 更新退货物流信息
   */
  int updateReturnLogistics(@Param("id") String id, @Param("returnLogistics") String returnLogistics,
                            @Param("returnTrackingNo") String returnTrackingNo);
}
