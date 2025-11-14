package com.cn.taihe.back.product.service.impl;
import com.cn.taihe.back.product.entity.ProductSpuIntent;
import com.cn.taihe.back.product.mapper.ProductSpuIntentMapper;
import com.cn.taihe.back.product.service.ProductSpuIntentService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * SPU-情感意图关系表 Service实现类
 *
 * @author system
 */
@Service
public class ProductSpuIntentServiceImpl implements ProductSpuIntentService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuIntentServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuIntentMapper productSpuIntentMapper;

  /**
   * 根据主键查找
   */
  @Override
  public ProductSpuIntent findById(String id) {
    logger.info("开始根据主键查找SPU-情感意图关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuIntent result = productSpuIntentMapper.selectById(id);

    logger.info("根据主键查找SPU-情感意图关系完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result);
    return result;
  }

  /**
   * 根据SPU ID查找
   */
  @Override
  public List<ProductSpuIntent> findBySpuId(String spuId) {
    logger.info("开始根据SPU ID查找SPU-情感意图关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuIntent> result = productSpuIntentMapper.selectBySpuId(spuId);

    logger.info("根据SPU ID查找SPU-情感意图关系完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 根据情感意图ID查找
   */
  @Override
  public List<ProductSpuIntent> findByIntentId(String intentId) {
    logger.info("开始根据情感意图ID查找SPU-情感意图关系, 操作人: {}, 参数: intentId={}", OPERATOR, intentId);

    List<ProductSpuIntent> result = productSpuIntentMapper.selectByIntentId(intentId);

    logger.info("根据情感意图ID查找SPU-情感意图关系完成, 操作人: {}, 参数: intentId={}, 结果数量: {}",
      OPERATOR, intentId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int save(ProductSpuIntent productSpuIntent) {
    logger.info("开始新增SPU-情感意图关系, 操作人: {}, 参数: {}", OPERATOR, productSpuIntent);

    int result = productSpuIntentMapper.insert(productSpuIntent);

    logger.info("新增SPU-情感意图关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuIntent, result);
    return result;
  }

  /**
   * @description:
   * @author: 大咖
   * @date: 新建关系，逻辑 先删除之前的数据再重新新建
   * @param: [spuId, arrays]
   * @return: [java.lang.String, java.util.Arrays]
   **/
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int createRealiations(String spuId, List list){
    logger.info("开始根据SPU ID查找SPU-情感意图关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);
    int del_result  = productSpuIntentMapper.deleteBySpuId(spuId);
    Date date = new Date();
    if(list!=null && list.size()>0){
      for (Object item : list) {
        ProductSpuIntent productSpuIntent = new ProductSpuIntent();
        productSpuIntent.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
        productSpuIntent.setSpuId(spuId);
        productSpuIntent.setIntentId(String.valueOf(item));
        productSpuIntent.setCreatedTime(date);
        productSpuIntent.setSortOrder(1);
        productSpuIntent.setIsActive(1);
        productSpuIntent.setAssociationLevel("1");
        productSpuIntent.setCustomMessageZh("default");
        productSpuIntentMapper.insert(productSpuIntent);
      }
    }
    return del_result;
  }
  /**
   * 批量新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int saveBatch(List<ProductSpuIntent> productSpuIntentList) {
    logger.info("开始批量新增SPU-情感意图关系, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuIntentList != null ? productSpuIntentList.size() : 0);

    int result = productSpuIntentMapper.insertBatch(productSpuIntentList);

    logger.info("批量新增SPU-情感意图关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuIntentList != null ? productSpuIntentList.size() : 0, result);
    return result;
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(String id) {
    logger.info("开始根据主键删除SPU-情感意图关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuIntentMapper.deleteById(id);

    logger.info("根据主键删除SPU-情感意图关系完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);
    return result;
  }

  /**
   * 根据主键集合批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<String> ids) {
    logger.info("开始根据主键集合批量删除SPU-情感意图关系, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuIntentMapper.deleteBatchIds(ids);

    logger.info("根据主键集合批量删除SPU-情感意图关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);
    return result;
  }

  /**
   * 根据SPU ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySpuId(String spuId) {
    logger.info("开始根据SPU ID批量删除SPU-情感意图关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuIntentMapper.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除SPU-情感意图关系完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);
    return result;
  }

  /**
   * 根据情感意图ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteByIntentId(String intentId) {
    logger.info("开始根据情感意图ID批量删除SPU-情感意图关系, 操作人: {}, 参数: intentId={}", OPERATOR, intentId);

    int result = productSpuIntentMapper.deleteByIntentId(intentId);

    logger.info("根据情感意图ID批量删除SPU-情感意图关系完成, 操作人: {}, 参数: intentId={}, 影响行数: {}",
      OPERATOR, intentId, result);
    return result;
  }
}
