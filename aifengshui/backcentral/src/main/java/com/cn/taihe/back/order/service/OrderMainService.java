package com.cn.taihe.back.order.service;

import com.cn.taihe.back.order.entity.OrderMain;
import com.cn.taihe.back.order.dto.OrderMainCreateDTO;
import com.cn.taihe.back.order.dto.OrderMainQueryDTO;
import com.cn.taihe.back.order.dto.OrderMainUpdateDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 订单主表Service接口
 *
 * @author system
 */
public interface OrderMainService {

  /**
   * 根据主键查询订单
   */
  OrderMain getById(String id);

  /**
   * 根据用户ID查询订单列表
   */
  List<OrderMain> getByUserId(String userId);

  /**
   * 条件分页查询订单列表
   */
  PageInfo<OrderMain> getByCondition(OrderMainQueryDTO queryDTO, int page, int size);

  /**
   * 新增订单
   */
  boolean create(OrderMainCreateDTO createDTO);

  /**
   * 更新订单
   */
  boolean update(OrderMainUpdateDTO updateDTO);

  /**
   * 根据主键删除订单
   */
  boolean deleteById(String id);

  /**
   * 批量删除订单
   */
  boolean deleteBatch(List<String> ids);

  /**
   * 更新订单状态
   */
  boolean updateStatus(String id, Integer status);
}
