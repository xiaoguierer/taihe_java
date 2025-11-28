package com.cn.taihe.back.shoppingcart.service.impl;

import com.cn.taihe.back.shoppingcart.entity.Shopingcart;
import com.cn.taihe.back.shoppingcart.mapper.ShopingcartMapper;
import com.cn.taihe.back.shoppingcart.service.ShopingcartService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 购物车商品表 Service实现类
 *
 * @author system
 */
@Service
public class ShopingcartServiceImpl implements ShopingcartService {

  private static final Logger logger = LoggerFactory.getLogger(ShopingcartServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ShopingcartMapper shopingcartMapper;

  /**
   * 根据主键查找数据
   */
  @Override
  public Shopingcart getById(String id) {
    logger.info("查询购物车项开始 - 操作人: {}, 参数: id={}", OPERATOR, id);
    try {
      Shopingcart result = shopingcartMapper.selectById(id);
      logger.info("查询购物车项成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("查询购物车项异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean save(Shopingcart shopingcart) {
    logger.info("新增购物车项开始 - 操作人: {}, 参数: {}", OPERATOR, shopingcart);
    try {
      // 生成主键
      shopingcart.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      shopingcart.setAddedTime(LocalDateTime.now());
      int affectRows = shopingcartMapper.insert(shopingcart);
      boolean result = affectRows > 0;
      logger.info("新增购物车项完成 - 操作人: {}, 影响行数: {}, 结果: {}", OPERATOR, affectRows, result);
      return result;
    } catch (Exception e) {
      logger.error("新增购物车项异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, shopingcart, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 修改数据（只更新非空字段）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateById(Shopingcart shopingcart) {
    logger.info("修改购物车项开始 - 操作人: {}, 参数: {}", OPERATOR, shopingcart);
    try {
      int affectRows = shopingcartMapper.updateByIdSelective(shopingcart);
      shopingcart.setUpdatedTime(LocalDateTime.now());
      boolean result = affectRows > 0;
      logger.info("修改购物车项完成 - 操作人: {}, 影响行数: {}, 结果: {}", OPERATOR, affectRows, result);
      return result;
    } catch (Exception e) {
      logger.error("修改购物车项异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, shopingcart, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean removeById(String id) {
    logger.info("删除购物车项开始 - 操作人: {}, 参数: id={}", OPERATOR, id);
    try {
      int affectRows = shopingcartMapper.deleteById(id);
      boolean result = affectRows > 0;
      logger.info("删除购物车项完成 - 操作人: {}, 影响行数: {}, 结果: {}", OPERATOR, affectRows, result);
      return result;
    } catch (Exception e) {
      logger.error("删除购物车项异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 条件分页查询数据
   */
  @Override
  public PageInfo<Shopingcart> getByPage(Shopingcart shopingcart, Integer page, Integer size) {
    logger.info("分页查询购物车项开始 - 操作人: {}, 参数: shopingcart={}, page={}, size={}", OPERATOR, shopingcart, page, size);
    try {
      // 设置分页参数
      if (page == null || page < 1) {
        page = 1;
      }
      if (size == null || size < 1) {
        size = 10;
      }
      PageHelper.startPage(page, size);

      List<Shopingcart> list = shopingcartMapper.selectByPage(shopingcart);
      PageInfo<Shopingcart> pageInfo = new PageInfo<>(list);

      logger.info("分页查询购物车项成功 - 操作人: {}, 总记录数: {}, 当前页记录数: {}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getSize());
      return pageInfo;
    } catch (Exception e) {
      logger.error("分页查询购物车项异常 - 操作人: {}, 参数: shopingcart={}, page={}, size={}, 异常信息: {}",
        OPERATOR, shopingcart, page, size, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据主键集合批量删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean removeBatchByIds(List<String> ids) {
    logger.info("批量删除购物车项开始 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量删除购物车项参数为空 - 操作人: {}", OPERATOR);
        return false;
      }

      int affectRows = shopingcartMapper.deleteBatchIds(ids);
      boolean result = affectRows > 0;
      logger.info("批量删除购物车项完成 - 操作人: {}, 影响行数: {}, 结果: {}", OPERATOR, affectRows, result);
      return result;
    } catch (Exception e) {
      logger.error("批量删除购物车项异常 - 操作人: {}, 参数: ids={}, 异常信息: {}", OPERATOR, ids, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 更新选中状态（冻结功能）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateSelectedStatus(String id, Integer selected) {
    logger.info("更新购物车项选中状态开始 - 操作人: {}, 参数: id={}, selected={}", OPERATOR, id, selected);
    try {
      int affectRows = shopingcartMapper.updateSelectedStatus(id, selected);
      boolean result = affectRows > 0;
      logger.info("更新购物车项选中状态完成 - 操作人: {}, 影响行数: {}, 结果: {}", OPERATOR, affectRows, result);
      return result;
    } catch (Exception e) {
      logger.error("更新购物车项选中状态异常 - 操作人: {}, 参数: id={}, selected={}, 异常信息: {}",
        OPERATOR, id, selected, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据用户ID查询购物车列表
   */
  @Override
  public List<Shopingcart> getByUserId(String userId) {
    logger.info("根据用户ID查询购物车列表开始 - 操作人: {}, 参数: userId={}", OPERATOR, userId);
    try {
      List<Shopingcart> result = shopingcartMapper.selectByUserId(userId);
      logger.info("根据用户ID查询购物车列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据用户ID查询购物车列表异常 - 操作人: {}, 参数: userId={}, 异常信息: {}",
        OPERATOR, userId, e.getMessage(), e);
      throw e;
    }
  }
}
