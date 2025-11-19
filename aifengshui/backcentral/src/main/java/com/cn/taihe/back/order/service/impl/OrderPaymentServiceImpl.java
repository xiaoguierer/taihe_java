package com.cn.taihe.back.order.service.impl;

import com.cn.taihe.back.order.entity.OrderPayment;
import com.cn.taihe.back.order.dto.OrderPaymentCreateDTO;
import com.cn.taihe.back.order.dto.OrderPaymentQueryDTO;
import com.cn.taihe.back.order.mapper.OrderPaymentMapper;
import com.cn.taihe.back.order.service.OrderPaymentService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单支付表Service实现类
 *
 * @author system
 */
@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {

  private static final Logger logger = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderPaymentMapper orderPaymentMapper;

  /**
   * 根据主键查询订单支付
   */
  @Override
  public OrderPayment getById(String id) {
    try {
      logger.info("查询订单支付详情开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderPayment result = orderPaymentMapper.selectById(id);
      logger.info("查询订单支付详情成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("查询订单支付详情失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("查询订单支付详情失败", e);
    }
  }

  /**
   * 根据订单ID查询订单支付列表
   */
  @Override
  public List<OrderPayment> getByOrderId(String orderId) {
    try {
      logger.info("根据订单ID查询订单支付列表开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);
      List<OrderPayment> result = orderPaymentMapper.selectByOrderId(orderId);
      logger.info("根据订单ID查询订单支付列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据订单ID查询订单支付列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, orderId, e.getMessage(), e);
      throw new RuntimeException("根据订单ID查询订单支付列表失败", e);
    }
  }

  /**
   * 根据支付状态查询订单支付列表
   */
  @Override
  public List<OrderPayment> getByPaymentStatus(String paymentStatus) {
    try {
      logger.info("根据支付状态查询订单支付列表开始 - 操作人: {}, 参数: {}", OPERATOR, paymentStatus);
      List<OrderPayment> result = orderPaymentMapper.selectByPaymentStatus(paymentStatus);
      logger.info("根据支付状态查询订单支付列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据支付状态查询订单支付列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, paymentStatus, e.getMessage(), e);
      throw new RuntimeException("根据支付状态查询订单支付列表失败", e);
    }
  }

  /**
   * 根据支付平台交易号查询订单支付
   */
  @Override
  public OrderPayment getByGatewayTradeNo(String gatewayTradeNo) {
    try {
      logger.info("根据支付平台交易号查询订单支付开始 - 操作人: {}, 参数: {}", OPERATOR, gatewayTradeNo);
      OrderPayment result = orderPaymentMapper.selectByGatewayTradeNo(gatewayTradeNo);
      logger.info("根据支付平台交易号查询订单支付成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("根据支付平台交易号查询订单支付失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, gatewayTradeNo, e.getMessage(), e);
      throw new RuntimeException("根据支付平台交易号查询订单支付失败", e);
    }
  }

  /**
   * 条件分页查询订单支付列表
   */
  @Override
  public PageInfo<OrderPayment> getByCondition(OrderPaymentQueryDTO queryDTO, int page, int size) {
    try {
      logger.info("条件分页查询订单支付列表开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);

      PageHelper.startPage(page, size);
      List<OrderPayment> list = orderPaymentMapper.selectByCondition(queryDTO);
      PageInfo<OrderPayment> pageInfo = new PageInfo<>(list);

      logger.info("条件分页查询订单支付列表成功 - 操作人: {}, 总记录数: {}, 当前页: {}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getPageNum());
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询订单支付列表失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      throw new RuntimeException("条件分页查询订单支付列表失败", e);
    }
  }

  /**
   * 新增订单支付
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(OrderPaymentCreateDTO createDTO) {
    try {
      logger.info("新增订单支付开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);

      OrderPayment orderPayment = new OrderPayment();
      BeanUtils.copyProperties(createDTO, orderPayment);

      // 生成主键
      orderPayment.setId(String.valueOf(SnowflakeIdGenerator.nextId()));

      int result = orderPaymentMapper.insert(orderPayment);
      boolean success = result > 0;

      logger.info("新增订单支付{} - 操作人: {}, 支付ID: {}",
        success ? "成功" : "失败", OPERATOR, orderPayment.getId());
      return success;
    } catch (Exception e) {
      logger.error("新增订单支付失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      throw new RuntimeException("新增订单支付失败", e);
    }
  }

  /**
   * 更新支付状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updatePaymentStatus(String id, String paymentStatus) {
    try {
      logger.info("更新支付状态开始 - 操作人: {}, 参数: id={}, paymentStatus={}", OPERATOR, id, paymentStatus);

      int result = orderPaymentMapper.updatePaymentStatusById(id, paymentStatus);
      boolean success = result > 0;

      logger.info("更新支付状态{} - 操作人: {}, 支付ID: {}, 新状态: {}",
        success ? "成功" : "失败", OPERATOR, id, paymentStatus);
      return success;
    } catch (Exception e) {
      logger.error("更新支付状态失败 - 操作人: {}, 参数: id={}, paymentStatus={}, 错误: {}",
        OPERATOR, id, paymentStatus, e.getMessage(), e);
      throw new RuntimeException("更新支付状态失败", e);
    }
  }

  /**
   * 更新支付成功信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updatePaymentSuccess(String id, String gatewayTradeNo, LocalDateTime paidTime, String paymentStatus) {
    try {
      logger.info("更新支付成功信息开始 - 操作人: {}, 参数: id={}, gatewayTradeNo={}, paidTime={}, paymentStatus={}",
        OPERATOR, id, gatewayTradeNo, paidTime, paymentStatus);

      int result = orderPaymentMapper.updatePaymentSuccess(id, gatewayTradeNo, paidTime, paymentStatus);
      boolean success = result > 0;

      logger.info("更新支付成功信息{} - 操作人: {}, 支付ID: {}",
        success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("更新支付成功信息失败 - 操作人: {}, 参数: id={}, gatewayTradeNo={}, paidTime={}, paymentStatus={}, 错误: {}",
        OPERATOR, id, gatewayTradeNo, paidTime, paymentStatus, e.getMessage(), e);
      throw new RuntimeException("更新支付成功信息失败", e);
    }
  }
}
