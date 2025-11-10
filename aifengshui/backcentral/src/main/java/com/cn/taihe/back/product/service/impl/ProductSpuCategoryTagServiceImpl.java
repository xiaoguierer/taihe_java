package com.cn.taihe.back.product.service.impl;
import com.cn.taihe.back.product.entity.ProductSpuCategoryTag;
import com.cn.taihe.back.product.mapper.ProductSpuCategoryTagMapper;
import com.cn.taihe.back.product.service.ProductSpuCategoryTagService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SPU-品类标签关系表 Service实现类
 *
 * @author system
 */
@Service
public class ProductSpuCategoryTagServiceImpl implements ProductSpuCategoryTagService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuCategoryTagServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuCategoryTagMapper productSpuCategoryTagMapper;

  /**
   * 根据主键查找
   */
  @Override
  public ProductSpuCategoryTag findById(String id) {
    logger.info("开始根据主键查找SPU-品类标签关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuCategoryTag result = productSpuCategoryTagMapper.selectById(id);

    logger.info("根据主键查找SPU-品类标签关系完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");
    return result;
  }

  /**
   * 根据SPU ID查找
   */
  @Override
  public List<ProductSpuCategoryTag> findBySpuId(String spuId) {
    logger.info("开始根据SPU ID查找SPU-品类标签关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuCategoryTag> result = productSpuCategoryTagMapper.selectBySpuId(spuId);

    logger.info("根据SPU ID查找SPU-品类标签关系完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 根据品类标签ID查找
   */
  @Override
  public List<ProductSpuCategoryTag> findByCategoryTagId(String categoryTagId) {
    logger.info("开始根据品类标签ID查找SPU-品类标签关系, 操作人: {}, 参数: categoryTagId={}", OPERATOR, categoryTagId);

    List<ProductSpuCategoryTag> result = productSpuCategoryTagMapper.selectByCategoryTagId(categoryTagId);

    logger.info("根据品类标签ID查找SPU-品类标签关系完成, 操作人: {}, 参数: categoryTagId={}, 结果数量: {}",
      OPERATOR, categoryTagId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int save(ProductSpuCategoryTag productSpuCategoryTag) {
    logger.info("开始新增SPU-品类标签关系, 操作人: {}, 参数: {}", OPERATOR, productSpuCategoryTag);
    productSpuCategoryTag.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
    int result = productSpuCategoryTagMapper.insert(productSpuCategoryTag);

    logger.info("新增SPU-品类标签关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuCategoryTag, result);
    return result;
  }

  /**
   * 批量新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int saveBatch(List<ProductSpuCategoryTag> productSpuCategoryTagList) {
    logger.info("开始批量新增SPU-品类标签关系, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuCategoryTagList != null ? productSpuCategoryTagList.size() : 0);

    int result = productSpuCategoryTagMapper.insertBatch(productSpuCategoryTagList);

    logger.info("批量新增SPU-品类标签关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuCategoryTagList != null ? productSpuCategoryTagList.size() : 0, result);
    return result;
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(String id) {
    logger.info("开始根据主键删除SPU-品类标签关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuCategoryTagMapper.deleteById(id);

    logger.info("根据主键删除SPU-品类标签关系完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);
    return result;
  }

  /**
   * 根据主键集合批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<String> ids) {
    logger.info("开始根据主键集合批量删除SPU-品类标签关系, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuCategoryTagMapper.deleteBatchIds(ids);

    logger.info("根据主键集合批量删除SPU-品类标签关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);
    return result;
  }

  /**
   * 根据SPU ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySpuId(String spuId) {
    logger.info("开始根据SPU ID批量删除SPU-品类标签关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuCategoryTagMapper.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除SPU-品类标签关系完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);
    return result;
  }

  /**
   * 根据品类标签ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteByCategoryTagId(String categoryTagId) {
    logger.info("开始根据品类标签ID批量删除SPU-品类标签关系, 操作人: {}, 参数: categoryTagId={}", OPERATOR, categoryTagId);

    int result = productSpuCategoryTagMapper.deleteByCategoryTagId(categoryTagId);

    logger.info("根据品类标签ID批量删除SPU-品类标签关系完成, 操作人: {}, 参数: categoryTagId={}, 影响行数: {}",
      OPERATOR, categoryTagId, result);
    return result;
  }
}
