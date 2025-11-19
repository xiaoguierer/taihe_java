package com.cn.taihe.back.order.service;

import com.cn.taihe.back.order.entity.OrderItem;
import com.cn.taihe.back.order.dto.OrderItemCreateDTO;
import com.cn.taihe.back.order.dto.OrderItemQueryDTO;
import com.cn.taihe.back.order.dto.OrderItemUpdateDTO;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品表Service接口
 *
 * @author system
 */
public interface OrderItemService {

  /**
   * 根据主键查询订单商品
   */
  OrderItem getById(String id);

  /**
   * 根据订单ID查询订单商品列表
   */
  List<OrderItem> getByOrderId(String orderId);

  /**
   * 根据SKU ID查询订单商品列表
   */
  List<OrderItem> getBySkuId(String skuId);

  /**
   * 条件分页查询订单商品列表
   */
  PageInfo<OrderItem> getByCondition(OrderItemQueryDTO queryDTO, int page, int size);

  /**
   * 新增订单商品
   */
  boolean create(OrderItemCreateDTO createDTO);

  /**
   * 更新订单商品
   */
  boolean update(OrderItemUpdateDTO updateDTO);

  /**
   * 根据主键删除订单商品
   */
  boolean deleteById(String id);

  /**
   * 批量删除订单商品
   */
  boolean deleteBatch(List<String> ids);

  /**
   * 根据订单ID批量删除订单商品
   */
  boolean deleteByOrderId(String orderId);

  /**
   * 更新退款状态
   */
  boolean updateRefundStatus(String id, Integer refundStatus);

  /**
   * 更新退款信息
   */
  boolean updateRefundInfo(String id, Integer refundQuantity, BigDecimal refundAmount, Integer refundStatus);
}
