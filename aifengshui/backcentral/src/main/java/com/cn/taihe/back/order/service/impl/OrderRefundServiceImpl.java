package com.cn.taihe.back.order.service.impl;

import com.cn.taihe.back.order.entity.OrderRefund;
import com.cn.taihe.back.order.dto.OrderRefundCreateDTO;
import com.cn.taihe.back.order.dto.OrderRefundQueryDTO;
import com.cn.taihe.back.order.dto.OrderRefundUpdateDTO;
import com.cn.taihe.back.order.mapper.OrderRefundMapper;
import com.cn.taihe.back.order.service.OrderRefundService;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单退单表Service实现类
 *
 * @author system
 */
@Service
public class OrderRefundServiceImpl implements OrderRefundService {

  private static final Logger logger = LoggerFactory.getLogger(OrderRefundServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderRefundMapper orderRefundMapper;

  /**
   * 根据主键查询订单退单
   */
  @Override
  public OrderRefund getById(String id) {
    try {
      logger.info("查询订单退单详情开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderRefund result = orderRefundMapper.selectById(id);
      logger.info("查询订单退单详情成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("查询订单退单详情失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("查询订单退单详情失败", e);
    }
  }

  /**
   * 根据退单号查询订单退单
   */
  @Override
  public OrderRefund getByRefundSn(String refundSn) {
    try {
      logger.info("根据退单号查询订单退单开始 - 操作人: {}, 参数: {}", OPERATOR, refundSn);
      OrderRefund result = orderRefundMapper.selectByRefundSn(refundSn);
      logger.info("根据退单号查询订单退单成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("根据退单号查询订单退单失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, refundSn, e.getMessage(), e);
      throw new RuntimeException("根据退单号查询订单退单失败", e);
    }
  }

  /**
   * 根据订单ID查询订单退单列表
   */
  @Override
  public List<OrderRefund> getByOrderId(String orderId) {
    try {
      logger.info("根据订单ID查询订单退单列表开始 - 操作人: {}, 参数: {}", OPERATOR, orderId);
      List<OrderRefund> result = orderRefundMapper.selectByOrderId(orderId);
      logger.info("根据订单ID查询订单退单列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据订单ID查询订单退单列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, orderId, e.getMessage(), e);
      throw new RuntimeException("根据订单ID查询订单退单列表失败", e);
    }
  }

  /**
   * 根据用户ID查询订单退单列表
   */
  @Override
  public List<OrderRefund> getByUserId(String userId) {
    try {
      logger.info("根据用户ID查询订单退单列表开始 - 操作人: {}, 参数: {}", OPERATOR, userId);
      List<OrderRefund> result = orderRefundMapper.selectByUserId(userId);
      logger.info("根据用户ID查询订单退单列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据用户ID查询订单退单列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, userId, e.getMessage(), e);
      throw new RuntimeException("根据用户ID查询订单退单列表失败", e);
    }
  }

  /**
   * 根据退单状态查询订单退单列表
   */
  @Override
  public List<OrderRefund> getByRefundStatus(Integer refundStatus) {
    try {
      logger.info("根据退单状态查询订单退单列表开始 - 操作人: {}, 参数: {}", OPERATOR, refundStatus);
      List<OrderRefund> result = orderRefundMapper.selectByRefundStatus(refundStatus);
      logger.info("根据退单状态查询订单退单列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据退单状态查询订单退单列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, refundStatus, e.getMessage(), e);
      throw new RuntimeException("根据退单状态查询订单退单列表失败", e);
    }
  }

  /**
   * 条件分页查询订单退单列表
   */
  @Override
  public PageInfo<OrderRefund> getByCondition(OrderRefundQueryDTO queryDTO, int page, int size) {
    try {
      logger.info("条件分页查询订单退单列表开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);

      PageHelper.startPage(page, size);
      List<OrderRefund> list = orderRefundMapper.selectByCondition(queryDTO);
      PageInfo<OrderRefund> pageInfo = new PageInfo<>(list);

      logger.info("条件分页查询订单退单列表成功 - 操作人: {}, 总记录数: {}, 当前页: {}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getPageNum());
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询订单退单列表失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      throw new RuntimeException("条件分页查询订单退单列表失败", e);
    }
  }

  /**
   * 新增订单退单
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(OrderRefundCreateDTO createDTO) {
    try {
      logger.info("新增订单退单开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);

      OrderRefund orderRefund = new OrderRefund();
      BeanUtils.copyProperties(createDTO, orderRefund);

      // 生成主键
      orderRefund.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      // 设置初始状态为申请中
      orderRefund.setRefundStatus(1);
      // 设置申请时间为当前时间
      orderRefund.setApplyTime(LocalDateTime.now());

      int result = orderRefundMapper.insert(orderRefund);
      boolean success = result > 0;

      logger.info("新增订单退单{} - 操作人: {}, 退单ID: {}",
        success ? "成功" : "失败", OPERATOR, orderRefund.getId());
      return success;
    } catch (Exception e) {
      logger.error("新增订单退单失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      throw new RuntimeException("新增订单退单失败", e);
    }
  }

  /**
   * 更新订单退单
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(OrderRefundUpdateDTO updateDTO) {
    try {
      logger.info("更新订单退单开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);

      OrderRefund orderRefund = new OrderRefund();
      BeanUtils.copyProperties(updateDTO, orderRefund);

      int result = orderRefundMapper.updateById(orderRefund);
      boolean success = result > 0;

      logger.info("更新订单退单{} - 操作人: {}, 退单ID: {}",
        success ? "成功" : "失败", OPERATOR, updateDTO.getId());
      return success;
    } catch (Exception e) {
      logger.error("更新订单退单失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, updateDTO, e.getMessage(), e);
      throw new RuntimeException("更新订单退单失败", e);
    }
  }

  /**
   * 更新退单状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateRefundStatus(String id, Integer refundStatus) {
    try {
      logger.info("更新退单状态开始 - 操作人: {}, 参数: id={}, refundStatus={}", OPERATOR, id, refundStatus);

      int result = orderRefundMapper.updateRefundStatusById(id, refundStatus);
      boolean success = result > 0;

      logger.info("更新退单状态{} - 操作人: {}, 退单ID: {}, 新状态: {}",
        success ? "成功" : "失败", OPERATOR, id, refundStatus);
      return success;
    } catch (Exception e) {
      logger.error("更新退单状态失败 - 操作人: {}, 参数: id={}, refundStatus={}, 错误: {}",
        OPERATOR, id, refundStatus, e.getMessage(), e);
      throw new RuntimeException("更新退单状态失败", e);
    }
  }

  /**
   * 审核退单申请
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateRefundReview(String id, String approverId, String approverNotes, Integer refundStatus, LocalDateTime reviewTime) {
    try {
      logger.info("审核退单申请开始 - 操作人: {}, 参数: id={}, approverId={}, refundStatus={}",
        OPERATOR, id, approverId, refundStatus);

      int result = orderRefundMapper.updateRefundReview(id, approverId, approverNotes, refundStatus, reviewTime);
      boolean success = result > 0;

      logger.info("审核退单申请{} - 操作人: {}, 退单ID: {}",
        success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("审核退单申请失败 - 操作人: {}, 参数: id={}, approverId={}, refundStatus={}, 错误: {}",
        OPERATOR, id, approverId, refundStatus, e.getMessage(), e);
      throw new RuntimeException("审核退单申请失败", e);
    }
  }
  /**
   * 更新退款信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateRefundInfo(String id, BigDecimal actualAmount, Integer refundStatus, LocalDateTime refundTime) {
    try {
      logger.info("更新退款信息开始 - 操作人: {}, 参数: id={}, actualAmount={}, refundStatus={}",
        OPERATOR, id, actualAmount, refundStatus);

      int result = orderRefundMapper.updateRefundInfo(id, actualAmount, refundStatus, refundTime);
      boolean success = result > 0;

      logger.info("更新退款信息{} - 操作人: {}, 退单ID: {}",
        success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("更新退款信息失败 - 操作人: {}, 参数: id={}, actualAmount={}, refundStatus={}, 错误: {}",
        OPERATOR, id, actualAmount, refundStatus, e.getMessage(), e);
      throw new RuntimeException("更新退款信息失败", e);
    }
  }

  /**
   * 更新退货物流信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateReturnLogistics(String id, String returnLogistics, String returnTrackingNo) {
    try {
      logger.info("更新退货物流信息开始 - 操作人: {}, 参数: id={}, returnLogistics={}, returnTrackingNo={}",
        OPERATOR, id, returnLogistics, returnTrackingNo);

      int result = orderRefundMapper.updateReturnLogistics(id, returnLogistics, returnTrackingNo);
      boolean success = result > 0;

      logger.info("更新退货物流信息{} - 操作人: {}, 退单ID: {}",
        success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("更新退货物流信息失败 - 操作人: {}, 参数: id={}, returnLogistics={}, returnTrackingNo={}, 错误: {}",
        OPERATOR, id, returnLogistics, returnTrackingNo, e.getMessage(), e);
      throw new RuntimeException("更新退货物流信息失败", e);
    }
  }
}
