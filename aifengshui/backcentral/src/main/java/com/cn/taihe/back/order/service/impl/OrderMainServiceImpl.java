package com.cn.taihe.back.order.service.impl;

import com.cn.taihe.back.order.entity.OrderMain;
import com.cn.taihe.back.order.dto.OrderMainCreateDTO;
import com.cn.taihe.back.order.dto.OrderMainQueryDTO;
import com.cn.taihe.back.order.dto.OrderMainUpdateDTO;
import com.cn.taihe.back.order.mapper.OrderMainMapper;
import com.cn.taihe.back.order.service.OrderMainService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单主表Service实现类
 *
 * @author system
 */
@Service
public class OrderMainServiceImpl implements OrderMainService {

  private static final Logger logger = LoggerFactory.getLogger(OrderMainServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private OrderMainMapper orderMainMapper;

  /**
   * 根据主键查询订单
   */
  @Override
  public OrderMain getById(String id) {
    try {
      logger.info("查询订单详情开始 - 操作人: {}, 参数: {}", OPERATOR, id);
      OrderMain result = orderMainMapper.selectById(id);
      logger.info("查询订单详情成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("查询订单详情失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("查询订单详情失败", e);
    }
  }

  /**
   * 根据用户ID查询订单列表
   */
  @Override
  public List<OrderMain> getByUserId(String userId) {
    try {
      logger.info("根据用户ID查询订单列表开始 - 操作人: {}, 参数: {}", OPERATOR, userId);
      List<OrderMain> result = orderMainMapper.selectByUserId(userId);
      logger.info("根据用户ID查询订单列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据用户ID查询订单列表失败 - 操作人: {}, 参数: {}, 错误: {}", OPERATOR, userId, e.getMessage(), e);
      throw new RuntimeException("根据用户ID查询订单列表失败", e);
    }
  }

  /**
   * 条件分页查询订单列表
   */
  @Override
  public PageInfo<OrderMain> getByCondition(OrderMainQueryDTO queryDTO, int page, int size) {
    try {
      logger.info("条件分页查询订单列表开始 - 操作人: {}, 参数: {}, page: {}, size: {}",
        OPERATOR, queryDTO, page, size);

      PageHelper.startPage(page, size);
      List<OrderMain> list = orderMainMapper.selectByCondition(queryDTO);
      PageInfo<OrderMain> pageInfo = new PageInfo<>(list);

      logger.info("条件分页查询订单列表成功 - 操作人: {}, 总记录数: {}, 当前页: {}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getPageNum());
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询订单列表失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      throw new RuntimeException("条件分页查询订单列表失败", e);
    }
  }

  /**
   * 新增订单
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(OrderMainCreateDTO createDTO) {
    try {
      logger.info("新增订单开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);

      OrderMain orderMain = new OrderMain();
      BeanUtils.copyProperties(createDTO, orderMain);

      // 生成主键
      orderMain.setId(String.valueOf(SnowflakeIdGenerator.nextId()));

      int result = orderMainMapper.insert(orderMain);
      boolean success = result > 0;

      logger.info("新增订单{} - 操作人: {}, 订单ID: {}",
        success ? "成功" : "失败", OPERATOR, orderMain.getId());
      return success;
    } catch (Exception e) {
      logger.error("新增订单失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, createDTO, e.getMessage(), e);
      throw new RuntimeException("新增订单失败", e);
    }
  }

  /**
   * 更新订单
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(OrderMainUpdateDTO updateDTO) {
    try {
      logger.info("更新订单开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);

      OrderMain orderMain = new OrderMain();
      BeanUtils.copyProperties(updateDTO, orderMain);

      int result = orderMainMapper.updateById(orderMain);
      boolean success = result > 0;

      logger.info("更新订单{} - 操作人: {}, 订单ID: {}",
        success ? "成功" : "失败", OPERATOR, updateDTO.getId());
      return success;
    } catch (Exception e) {
      logger.error("更新订单失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, updateDTO, e.getMessage(), e);
      throw new RuntimeException("更新订单失败", e);
    }
  }

  /**
   * 根据主键删除订单
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    try {
      logger.info("删除订单开始 - 操作人: {}, 参数: {}", OPERATOR, id);

      int result = orderMainMapper.deleteById(id);
      boolean success = result > 0;

      logger.info("删除订单{} - 操作人: {}, 订单ID: {}",
        success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("删除订单失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("删除订单失败", e);
    }
  }

  /**
   * 批量删除订单
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteBatch(List<String> ids) {
    try {
      logger.info("批量删除订单开始 - 操作人: {}, 参数: {}", OPERATOR, ids);

      if (ids == null || ids.isEmpty()) {
        logger.warn("批量删除订单参数为空 - 操作人: {}", OPERATOR);
        return false;
      }

      int result = orderMainMapper.deleteBatchIds(ids);
      boolean success = result > 0;

      logger.info("批量删除订单{} - 操作人: {}, 删除数量: {}",
        success ? "成功" : "失败", OPERATOR, result);
      return success;
    } catch (Exception e) {
      logger.error("批量删除订单失败 - 操作人: {}, 参数: {}, 错误: {}",
        OPERATOR, ids, e.getMessage(), e);
      throw new RuntimeException("批量删除订单失败", e);
    }
  }

  /**
   * 更新订单状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatus(String id, Integer status) {
    try {
      logger.info("更新订单状态开始 - 操作人: {}, 参数: id={}, status={}", OPERATOR, id, status);

      int result = orderMainMapper.updateStatusById(id, status);
      boolean success = result > 0;

      logger.info("更新订单状态{} - 操作人: {}, 订单ID: {}, 新状态: {}",
        success ? "成功" : "失败", OPERATOR, id, status);
      return success;
    } catch (Exception e) {
      logger.error("更新订单状态失败 - 操作人: {}, 参数: id={}, status={}, 错误: {}",
        OPERATOR, id, status, e.getMessage(), e);
      throw new RuntimeException("更新订单状态失败", e);
    }
  }
}
