package com.cn.taihe.back.order.service.impl;

import com.cn.taihe.back.order.entity.OrderItem;
import com.cn.taihe.back.order.dto.OrderItemCreateDTO;
import com.cn.taihe.back.order.dto.OrderItemQueryDTO;
import com.cn.taihe.back.order.dto.OrderItemUpdateDTO;
import com.cn.taihe.back.order.mapper.OrderItemMapper;
import com.cn.taihe.back.order.service.OrderItemService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商品表Service实现类
 *
 * @author system
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

  private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderItemMapper orderItemMapper;

  /**
   * 根据主键查询订单商品
   */
  @Override
  public OrderItem getById(String id) {
    try {
      logger.info("查询订单商品详情开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderItem result = orderItemMapper.selectById(id);
      logger.info("查询订单商品详情成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("查询订单商品详情失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("查询订单商品详情失败", e);
    }
  }

  /**
   * 根据订单ID查询订单商品列表
   */
  @Override
  public List<OrderItem> getByOrderId(String orderId) {
    try {
      logger.info("根据订单ID查询订单商品列表开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);
      List<OrderItem> result = orderItemMapper.selectByOrderId(orderId);
      logger.info("根据订单ID查询订单商品列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据订单ID查询订单商品列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, orderId, e.getMessage(), e);
      throw new RuntimeException("根据订单ID查询订单商品列表失败", e);
    }
  }

  /**
   * 根据SKU ID查询订单商品列表
   */
  @Override
  public List<OrderItem> getBySkuId(String skuId) {
    try {
      logger.info("根据SKU ID查询订单商品列表开始 - 操作人: {}, 参数: {}", OPERATOR, skuId);
      List<OrderItem> result = orderItemMapper.selectBySkuId(skuId);
      logger.info("根据SKU ID查询订单商品列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据SKU ID查询订单商品列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, skuId, e.getMessage(), e);
      throw new RuntimeException("根据SKU ID查询订单商品列表失败", e);
    }
  }

  /**
   * 条件分页查询订单商品列表
   */
  @Override
  public PageInfo<OrderItem> getByCondition(OrderItemQueryDTO queryDTO, int page, int size) {
    try {
      logger.info("条件分页查询订单商品列表开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);

      PageHelper.startPage(page, size);
      List<OrderItem> list = orderItemMapper.selectByCondition(queryDTO);
      PageInfo<OrderItem> pageInfo = new PageInfo<>(list);

      logger.info("条件分页查询订单商品列表成功 - 操作人: {}, 总记录数: {}, 当前页: {}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getPageNum());
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询订单商品列表失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      throw new RuntimeException("条件分页查询订单商品列表失败", e);
    }
  }

  /**
   * 新增订单商品
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(OrderItemCreateDTO createDTO) {
    try {
      logger.info("新增订单商品开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);

      OrderItem orderItem = new OrderItem();
      BeanUtils.copyProperties(createDTO, orderItem);

      // 生成主键
      orderItem.setId(String.valueOf(SnowflakeIdGenerator.nextId()));

      int result = orderItemMapper.insert(orderItem);
      boolean success = result > 0;

      logger.info("新增订单商品{} - 操作人: {}, 订单商品ID: {}",
        success ? "成功" : "失败", OPERATOR, orderItem.getId());
      return success;
    } catch (Exception e) {
      logger.error("新增订单商品失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      throw new RuntimeException("新增订单商品失败", e);
    }
  }

  /**
   * 更新订单商品
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(OrderItemUpdateDTO updateDTO) {
    try {
      logger.info("更新订单商品开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);

      OrderItem orderItem = new OrderItem();
      BeanUtils.copyProperties(updateDTO, orderItem);

      int result = orderItemMapper.updateById(orderItem);
      boolean success = result > 0;

      logger.info("更新订单商品{} - 操作人: {}, 订单商品ID: {}",
        success ? "成功" : "失败", OPERATOR, updateDTO.getId());
      return success;
    } catch (Exception e) {
      logger.error("更新订单商品失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, updateDTO, e.getMessage(), e);
      throw new RuntimeException("更新订单商品失败", e);
    }
  }

  /**
   * 根据主键删除订单商品
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    try {
      logger.info("删除订单商品开始 - 操作人: {}, 参数: {}", OPERATOR, id);

      int result = orderItemMapper.deleteById(id);
      boolean success = result > 0;

      logger.info("删除订单商品{} - 操作人: {}, 订单商品ID: {}",
        success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("删除订单商品失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("删除订单商品失败", e);
    }
  }

  /**
   * 批量删除订单商品
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteBatch(List<String> ids) {
    try {
      logger.info("批量删除订单商品开始 - 操作人: {}, 参数: {}", OPERATOR, ids);

      if (ids == null || ids.isEmpty()) {
        logger.warn("批量删除订单商品参数为空 - 操作人: {}", OPERATOR);
        return false;
      }

      int result = orderItemMapper.deleteBatchIds(ids);
      boolean success = result > 0;

      logger.info("批量删除订单商品{} - 操作人: {}, 删除数量: {}",
        success ? "成功" : "失败", OPERATOR, result);
      return success;
    } catch (Exception e) {
      logger.error("批量删除订单商品失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, ids, e.getMessage(), e);
      throw new RuntimeException("批量删除订单商品失败", e);
    }
  }

  /**
   * 根据订单ID批量删除订单商品
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteByOrderId(String orderId) {
    try {
      logger.info("根据订单ID批量删除订单商品开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);

      int result = orderItemMapper.deleteByOrderId(orderId);
      boolean success = result > 0;

      logger.info("根据订单ID批量删除订单商品{} - 操作人: {}, 删除数量: {}",
        success ? "成功" : "失败", OPERATOR, result);
      return success;
    } catch (Exception e) {
      logger.error("根据订单ID批量删除订单商品失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, orderId, e.getMessage(), e);
      throw new RuntimeException("根据订单ID批量删除订单商品失败", e);
    }
  }

  /**
   * 更新退款状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateRefundStatus(String id, Integer refundStatus) {
    try {
      logger.info("更新退款状态开始 - 操作人: {}, 参数: id={}, refundStatus={}", OPERATOR, id, refundStatus);

      int result = orderItemMapper.updateRefundStatusById(id, refundStatus);
      boolean success = result > 0;

      logger.info("更新退款状态{} - 操作人: {}, 订单商品ID: {}, 新状态: {}",
        success ? "成功" : "失败", OPERATOR, id, refundStatus);
      return success;
    } catch (Exception e) {
      logger.error("更新退款状态失败 - 操作人: {}, 参数: id={}, refundStatus={}, 错误: {}",
        OPERATOR, id, refundStatus, e.getMessage(), e);
      throw new RuntimeException("更新退款状态失败", e);
    }
  }

  /**
   * 更新退款信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateRefundInfo(String id, Integer refundQuantity, BigDecimal refundAmount, Integer refundStatus) {
    try {
      logger.info("更新退款信息开始 - 操作人: {}, 参数: id={}, refundQuantity={}, refundAmount={}, refundStatus={}",
        OPERATOR, id, refundQuantity, refundAmount, refundStatus);

      int result = orderItemMapper.updateRefundInfoById(id, refundQuantity, refundAmount, refundStatus);
      boolean success = result > 0;

      logger.info("更新退款信息{} - 操作人: {}, 订单商品ID: {}",
        success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("更新退款信息失败 - 操作人: {}, 参数: id={}, refundQuantity={}, refundAmount={}, refundStatus={}, 错误: {}",
        OPERATOR, id, refundQuantity, refundAmount, refundStatus, e.getMessage(), e);
      throw new RuntimeException("更新退款信息失败", e);
    }
  }
}
