package com.cn.taihe.back.order.mapper;

import com.cn.taihe.back.order.entity.OrderItem;
import com.cn.taihe.back.order.dto.OrderItemQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品表Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface OrderItemMapper {

  /**
   * 根据主键查询订单商品
   */
  OrderItem selectById(@Param("id") String id);

  /**
   * 根据订单ID查询订单商品列表
   */
  List<OrderItem> selectByOrderId(@Param("orderId") String orderId);

  /**
   * 根据SKU ID查询订单商品列表
   */
  List<OrderItem> selectBySkuId(@Param("skuId") String skuId);

  /**
   * 新增订单商品
   */
  int insert(OrderItem orderItem);

  /**
   * 根据主键更新订单商品（只更新非空字段）
   */
  int updateById(OrderItem orderItem);

  /**
   * 根据主键删除订单商品
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询订单商品列表（支持分页）
   */
  List<OrderItem> selectByCondition(OrderItemQueryDTO queryDTO);

  /**
   * 根据主键集合批量删除订单商品
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 根据订单ID批量删除订单商品
   */
  int deleteByOrderId(@Param("orderId") String orderId);

  /**
   * 根据主键更新退款状态
   */
  int updateRefundStatusById(@Param("id") String id, @Param("refundStatus") Integer refundStatus);

  /**
   * 更新退款信息
   */
  int updateRefundInfoById(@Param("id") String id, @Param("refundQuantity") Integer refundQuantity,
                           @Param("refundAmount") BigDecimal refundAmount, @Param("refundStatus") Integer refundStatus);
}
