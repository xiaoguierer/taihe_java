package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.product.entity.ProductSpuIntent;
import com.cn.taihe.back.product.entity.ProductSpuWuxing;
import com.cn.taihe.back.product.mapper.ProductSpuWuxingMapper;
import com.cn.taihe.back.product.service.ProductSpuWuxingService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * SPU-五行关系表 Service实现类
 *
 * @author system
 */
@Service
public class ProductSpuWuxingServiceImpl implements ProductSpuWuxingService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuWuxingServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuWuxingMapper productSpuWuxingMapper;

  /**
   * 根据主键查找
   */
  @Override
  public ProductSpuWuxing findById(String id) {
    logger.info("开始根据主键查找SPU-五行关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuWuxing result = productSpuWuxingMapper.selectById(id);

    logger.info("根据主键查找SPU-五行关系完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");
    return result;
  }

  /**
   * 根据SPU ID查找
   */
  @Override
  public List<ProductSpuWuxing> findBySpuId(String spuId) {
    logger.info("开始根据SPU ID查找SPU-五行关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuWuxing> result = productSpuWuxingMapper.selectBySpuId(spuId);

    logger.info("根据SPU ID查找SPU-五行关系完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 根据五行属性ID查找
   */
  @Override
  public List<ProductSpuWuxing> findByWuXingId(String wuXingId) {
    logger.info("开始根据五行属性ID查找SPU-五行关系, 操作人: {}, 参数: wuXingId={}", OPERATOR, wuXingId);

    List<ProductSpuWuxing> result = productSpuWuxingMapper.selectByWuXingId(wuXingId);

    logger.info("根据五行属性ID查找SPU-五行关系完成, 操作人: {}, 参数: wuXingId={}, 结果数量: {}",
      OPERATOR, wuXingId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int save(ProductSpuWuxing productSpuWuxing) {
    logger.info("开始新增SPU-五行关系, 操作人: {}, 参数: {}", OPERATOR, productSpuWuxing);
    productSpuWuxing.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
    int result = productSpuWuxingMapper.insert(productSpuWuxing);

    logger.info("新增SPU-五行关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuWuxing, result);
    return result;
  }

  /**
   * 批量新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int saveBatch(List<ProductSpuWuxing> productSpuWuxingList) {
    logger.info("开始批量新增SPU-五行关系, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuWuxingList != null ? productSpuWuxingList.size() : 0);

    int result = productSpuWuxingMapper.insertBatch(productSpuWuxingList);

    logger.info("批量新增SPU-五行关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuWuxingList != null ? productSpuWuxingList.size() : 0, result);
    return result;
  }
  /**
   * @description:
   * @author: 商品spu与五行元素建立关系
   * @date: 2025/11/15 01:09
   * @param: [spuId, list]
   * @return: [java.lang.String, java.util.List]
   **/
  @Override
  @Transactional(rollbackFor = Exception.class)
  public  int createRealiations(String spuId, List list){
    logger.info("开始根据SPU ID查找SPU-情感意图关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);
    int del_result  = productSpuWuxingMapper.deleteBySpuId(spuId);
    Date date = new Date();
    if(list!=null && list.size()>0){
      for (Object item : list) {
        ProductSpuWuxing productSpuWuxing = new ProductSpuWuxing();
        productSpuWuxing.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
        productSpuWuxing.setSpuId(spuId);
        productSpuWuxing.setWuXingId(String.valueOf(item));
        productSpuWuxing.setCreatedTime(date);
        productSpuWuxing.setSortOrder(1);
        productSpuWuxing.setElementStrength("100");
        productSpuWuxing.setIsPrimary(1);
        productSpuWuxingMapper.insert(productSpuWuxing);
      }
    }
    return del_result;
  }
  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(String id) {
    logger.info("开始根据主键删除SPU-五行关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuWuxingMapper.deleteById(id);

    logger.info("根据主键删除SPU-五行关系完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);
    return result;
  }

  /**
   * 根据主键集合批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<String> ids) {
    logger.info("开始根据主键集合批量删除SPU-五行关系, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuWuxingMapper.deleteBatchIds(ids);

    logger.info("根据主键集合批量删除SPU-五行关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);
    return result;
  }

  /**
   * 根据SPU ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySpuId(String spuId) {
    logger.info("开始根据SPU ID批量删除SPU-五行关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuWuxingMapper.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除SPU-五行关系完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);
    return result;
  }

  /**
   * 根据五行属性ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteByWuXingId(String wuXingId) {
    logger.info("开始根据五行属性ID批量删除SPU-五行关系, 操作人: {}, 参数: wuXingId={}", OPERATOR, wuXingId);

    int result = productSpuWuxingMapper.deleteByWuXingId(wuXingId);

    logger.info("根据五行属性ID批量删除SPU-五行关系完成, 操作人: {}, 参数: wuXingId={}, 影响行数: {}",
      OPERATOR, wuXingId, result);
    return result;
  }
}
