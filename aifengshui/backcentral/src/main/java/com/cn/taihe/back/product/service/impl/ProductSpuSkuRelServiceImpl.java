package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.product.entity.ProductSpuSkuRel;
import com.cn.taihe.back.product.mapper.ProductSpuSkuRelMapper;
import com.cn.taihe.back.product.service.ProductSpuSkuRelService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SPU-SKU关联关系表 Service实现类
 *
 * @author system
 */
@Service
public class ProductSpuSkuRelServiceImpl implements ProductSpuSkuRelService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuSkuRelServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuSkuRelMapper productSpuSkuRelMapper;

  /**
   * 根据主键查找
   */
  @Override
  public ProductSpuSkuRel findById(String id) {
    logger.info("开始根据主键查找SPU-SKU关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuSkuRel result = productSpuSkuRelMapper.selectById(id);

    logger.info("根据主键查找SPU-SKU关系完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");
    return result;
  }

  /**
   * 根据SPU ID查找
   */
  @Override
  public List<ProductSpuSkuRel> findBySpuId(String spuId) {
    logger.info("开始根据SPU ID查找SPU-SKU关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuSkuRel> result = productSpuSkuRelMapper.selectBySpuId(spuId);

    logger.info("根据SPU ID查找SPU-SKU关系完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 根据SKU ID查找
   */
  @Override
  public List<ProductSpuSkuRel> findBySkuId(String skuId) {
    logger.info("开始根据SKU ID查找SPU-SKU关系, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    List<ProductSpuSkuRel> result = productSpuSkuRelMapper.selectBySkuId(skuId);

    logger.info("根据SKU ID查找SPU-SKU关系完成, 操作人: {}, 参数: skuId={}, 结果数量: {}",
      OPERATOR, skuId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 条件查询数据
   */
  @Override
  public List<ProductSpuSkuRel> findByCondition(ProductSpuSkuRel productSpuSkuRel) {
    logger.info("开始条件查询SPU-SKU关系, 操作人: {}, 参数: {}", OPERATOR, productSpuSkuRel);

    List<ProductSpuSkuRel> result = productSpuSkuRelMapper.selectByCondition(productSpuSkuRel);

    logger.info("条件查询SPU-SKU关系完成, 操作人: {}, 参数: {}, 结果数量: {}",
      OPERATOR, productSpuSkuRel, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 条件分页查询数据
   */
  @Override
  public PageInfo<ProductSpuSkuRel> findByConditionWithPage(ProductSpuSkuRel productSpuSkuRel, int page, int size) {
    logger.info("开始条件分页查询SPU-SKU关系, 操作人: {}, 参数: {}, page={}, size={}",
      OPERATOR, productSpuSkuRel, page, size);

    PageHelper.startPage(page, size);
    List<ProductSpuSkuRel> resultList = productSpuSkuRelMapper.selectByCondition(productSpuSkuRel);
    PageInfo<ProductSpuSkuRel> result = new PageInfo<>(resultList);

    logger.info("条件分页查询SPU-SKU关系完成, 操作人: {}, 参数: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, productSpuSkuRel, page, size, result.getTotal());
    return result;
  }

  /**
   * 查询所有数据
   */
  @Override
  public List<ProductSpuSkuRel> findAll() {
    logger.info("开始查询所有SPU-SKU关系, 操作人: {}", OPERATOR);

    // 通过条件查询实现查询所有数据
    List<ProductSpuSkuRel> result = productSpuSkuRelMapper.selectByCondition(new ProductSpuSkuRel());

    logger.info("查询所有SPU-SKU关系完成, 操作人: {}, 结果数量: {}",
      OPERATOR, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 分页查询所有数据
   */
  @Override
  public PageInfo<ProductSpuSkuRel> findAllWithPage(int page, int size) {
    logger.info("开始分页查询所有SPU-SKU关系, 操作人: {}, page={}, size={}", OPERATOR, page, size);

    PageHelper.startPage(page, size);
    List<ProductSpuSkuRel> resultList = productSpuSkuRelMapper.selectByCondition(new ProductSpuSkuRel());
    PageInfo<ProductSpuSkuRel> result = new PageInfo<>(resultList);

    logger.info("分页查询所有SPU-SKU关系完成, 操作人: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, page, size, result.getTotal());
    return result;
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int save(ProductSpuSkuRel productSpuSkuRel) {
    logger.info("开始新增SPU-SKU关系, 操作人: {}, 参数: {}", OPERATOR, productSpuSkuRel);
    productSpuSkuRel.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
    int result = productSpuSkuRelMapper.insert(productSpuSkuRel);

    logger.info("新增SPU-SKU关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuSkuRel, result);
    return result;
  }

  /**
   * 批量新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int saveBatch(List<ProductSpuSkuRel> productSpuSkuRelList) {
    logger.info("开始批量新增SPU-SKU关系, 操作人: {}, 参数数量: {}", OPERATOR,
      productSpuSkuRelList != null ? productSpuSkuRelList.size() : 0);

    int result = productSpuSkuRelMapper.insertBatch(productSpuSkuRelList);

    logger.info("批量新增SPU-SKU关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSpuSkuRelList != null ? productSpuSkuRelList.size() : 0, result);
    return result;
  }

  /**
   * 创建SPU与SKU关联关系（先删除后新增）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int createRealiations(String spuId, List<String> skuIds) {
    logger.info("开始创建SPU-SKU关联关系, 操作人: {}, spuId: {}, skuIds数量: {}",
      OPERATOR, spuId, skuIds != null ? skuIds.size() : 0);

    // 1. 先删除该SPU的所有关联关系
    int deleteCount = productSpuSkuRelMapper.deleteBySpuId(spuId);
    logger.info("删除SPU原有SKU关联完成, 操作人: {}, spuId: {}, 删除数量: {}", OPERATOR, spuId, deleteCount);

    // 2. 如果SKU列表为空，直接返回
    if (skuIds == null || skuIds.isEmpty()) {
      logger.info("SKU列表为空，无需新增, 操作人: {}, spuId: {}", OPERATOR, spuId);
      return deleteCount;
    }

    // 3. 批量新增新的关联关系
    List<ProductSpuSkuRel> relationList = new ArrayList<>();
    for (int i = 0; i < skuIds.size(); i++) {
      String skuId = skuIds.get(i);
      ProductSpuSkuRel relation = new ProductSpuSkuRel();
      relation.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      relation.setSpuId(spuId);
      relation.setSkuId(skuId);
      relation.setIsDefault(i == 0); // 第一个SKU设为默认
      relation.setSortOrder(i); // 按顺序设置排序值
      relation.setCreatedTime(LocalDateTime.now());
      relation.setUpdatedTime(LocalDateTime.now());
      relationList.add(relation);
    }

    int insertCount = productSpuSkuRelMapper.insertBatch(relationList);
    logger.info("创建SPU-SKU关联关系完成, 操作人: {}, spuId: {}, 新增数量: {}, 总影响行数: {}",
      OPERATOR, spuId, insertCount, deleteCount + insertCount);

    return deleteCount + insertCount;
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(String id) {
    logger.info("开始根据主键删除SPU-SKU关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuSkuRelMapper.deleteById(id);

    logger.info("根据主键删除SPU-SKU关系完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);
    return result;
  }

  /**
   * 根据主键集合批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<String> ids) {
    logger.info("开始根据主键集合批量删除SPU-SKU关系, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuSkuRelMapper.deleteBatchIds(ids);

    logger.info("根据主键集合批量删除SPU-SKU关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);
    return result;
  }

  /**
   * 根据SPU ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySpuId(String spuId) {
    logger.info("开始根据SPU ID批量删除SPU-SKU关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuSkuRelMapper.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除SPU-SKU关系完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);
    return result;
  }

  /**
   * 根据SKU ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySkuId(String skuId) {
    logger.info("开始根据SKU ID批量删除SPU-SKU关系, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    int result = productSpuSkuRelMapper.deleteBySkuId(skuId);

    logger.info("根据SKU ID批量删除SPU-SKU关系完成, 操作人: {}, 参数: skuId={}, 影响行数: {}",
      OPERATOR, skuId, result);
    return result;
  }
}
