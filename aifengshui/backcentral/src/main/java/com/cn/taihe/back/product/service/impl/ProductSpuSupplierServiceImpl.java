package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.product.entity.ProductSpuSupplier;
import com.cn.taihe.back.product.mapper.ProductSpuSupplierMapper;
import com.cn.taihe.back.product.service.ProductSpuSupplierService;
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
 * SPU与供应商关联关系表 Service实现类
 *
 * @author system
 */
@Service
public class ProductSpuSupplierServiceImpl implements ProductSpuSupplierService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuSupplierServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuSupplierMapper productSpuSupplierMapper;

  /**
   * 根据主键查找
   */
  @Override
  public ProductSpuSupplier findById(String id) {
    logger.info("开始根据主键查找SPU-供应商关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSpuSupplier result = productSpuSupplierMapper.selectById(id);

    logger.info("根据主键查找SPU-供应商关系完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");
    return result;
  }

  /**
   * 根据SPU ID查找
   */
  @Override
  public List<ProductSpuSupplier> findBySpuId(String spuId) {
    logger.info("开始根据SPU ID查找SPU-供应商关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    List<ProductSpuSupplier> result = productSpuSupplierMapper.selectBySpuId(spuId);

    logger.info("根据SPU ID查找SPU-供应商关系完成, 操作人: {}, 参数: spuId={}, 结果数量: {}",
      OPERATOR, spuId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 根据供应商ID查找
   */
  @Override
  public List<ProductSpuSupplier> findBySupplierId(String supplierId) {
    logger.info("开始根据供应商ID查找SPU-供应商关系, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    List<ProductSpuSupplier> result = productSpuSupplierMapper.selectBySupplierId(supplierId);

    logger.info("根据供应商ID查找SPU-供应商关系完成, 操作人: {}, 参数: supplierId={}, 结果数量: {}",
      OPERATOR, supplierId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 条件查询数据
   */
  @Override
  public List<ProductSpuSupplier> findByCondition(ProductSpuSupplier productSpuSupplier) {
    logger.info("开始条件查询SPU-供应商关系, 操作人: {}, 参数: {}", OPERATOR, productSpuSupplier);

    List<ProductSpuSupplier> result = productSpuSupplierMapper.selectByCondition(productSpuSupplier);

    logger.info("条件查询SPU-供应商关系完成, 操作人: {}, 参数: {}, 结果数量: {}",
      OPERATOR, productSpuSupplier, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 条件分页查询数据
   */
  @Override
  public PageInfo<ProductSpuSupplier> findByConditionWithPage(ProductSpuSupplier productSpuSupplier, int page, int size) {
    logger.info("开始条件分页查询SPU-供应商关系, 操作人: {}, 参数: {}, page={}, size={}",
      OPERATOR, productSpuSupplier, page, size);

    PageHelper.startPage(page, size);
    List<ProductSpuSupplier> resultList = productSpuSupplierMapper.selectByCondition(productSpuSupplier);
    PageInfo<ProductSpuSupplier> result = new PageInfo<>(resultList);

    logger.info("条件分页查询SPU-供应商关系完成, 操作人: {}, 参数: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, productSpuSupplier, page, size, result.getTotal());
    return result;
  }

  /**
   * 查询所有数据
   */
  @Override
  public List<ProductSpuSupplier> findAll() {
    logger.info("开始查询所有SPU-供应商关系, 操作人: {}", OPERATOR);

    List<ProductSpuSupplier> result = productSpuSupplierMapper.selectAll();

    logger.info("查询所有SPU-供应商关系完成, 操作人: {}, 结果数量: {}",
      OPERATOR, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 分页查询所有数据
   */
  @Override
  public PageInfo<ProductSpuSupplier> findAllWithPage(int page, int size) {
    logger.info("开始分页查询所有SPU-供应商关系, 操作人: {}, page={}, size={}", OPERATOR, page, size);

    PageHelper.startPage(page, size);
    List<ProductSpuSupplier> resultList = productSpuSupplierMapper.selectAll();
    PageInfo<ProductSpuSupplier> result = new PageInfo<>(resultList);

    logger.info("分页查询所有SPU-供应商关系完成, 操作人: {}, page={}, size={}, 结果数量: {}",
      OPERATOR, page, size, result.getTotal());
    return result;
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int save(ProductSpuSupplier productSpuSupplier) {
    logger.info("开始新增SPU-供应商关系, 操作人: {}, 参数: {}", OPERATOR, productSpuSupplier);

    // 检查唯一性约束
    boolean exists = productSpuSupplierMapper.checkExists(productSpuSupplier.getSpuId(), productSpuSupplier.getSupplierId()) > 0;
    if (exists) {
      logger.warn("SPU-供应商关系已存在, 操作人: {}, 参数: {}", OPERATOR, productSpuSupplier);
      return 0;
    }
    productSpuSupplier.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
    int result = productSpuSupplierMapper.insert(productSpuSupplier);

    logger.info("新增SPU-供应商关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuSupplier, result);
    return result;
  }


  /**
   * 创建SPU与供应商关联关系（先删除后新增）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int createRealiations(String spuId, List<String> supplierIds) {
    logger.info("开始创建SPU-供应商关联关系, 操作人: {}, spuId: {}, supplierIds数量: {}",
      OPERATOR, spuId, supplierIds != null ? supplierIds.size() : 0);

    // 1. 先删除该SPU的所有关联关系
    int deleteCount = productSpuSupplierMapper.deleteBySpuId(spuId);
    logger.info("删除SPU原有供应商关联完成, 操作人: {}, spuId: {}, 删除数量: {}", OPERATOR, spuId, deleteCount);

    // 2. 如果供应商列表为空，直接返回
    if (supplierIds == null || supplierIds.isEmpty()) {
      logger.info("供应商列表为空，无需新增, 操作人: {}, spuId: {}", OPERATOR, spuId);
      return deleteCount;
    }

    // 3. 批量新增新的关联关系
    List<ProductSpuSupplier> relationList = new ArrayList<>();
    for (String supplierId : supplierIds) {
      ProductSpuSupplier relation = new ProductSpuSupplier();
      relation.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      relation.setSpuId(spuId);
      relation.setSupplierId(supplierId);
      relation.setCreatedTime(LocalDateTime.now());
      relationList.add(relation);
      productSpuSupplierMapper.insert(relation);
    }
    logger.info("创建SPU-供应商关联关系完成, 操作人: {}, spuId: {}, 新增数量: {}, 总影响行数: {}",
      OPERATOR, spuId, deleteCount);

    return deleteCount ;
  }

  /**
   * 修改数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int update(ProductSpuSupplier productSpuSupplier) {
    logger.info("开始修改SPU-供应商关系, 操作人: {}, 参数: {}", OPERATOR, productSpuSupplier);

    int result = productSpuSupplierMapper.updateById(productSpuSupplier);

    logger.info("修改SPU-供应商关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSpuSupplier, result);
    return result;
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(String id) {
    logger.info("开始根据主键删除SPU-供应商关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSpuSupplierMapper.deleteById(id);

    logger.info("根据主键删除SPU-供应商关系完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);
    return result;
  }

  /**
   * 根据主键集合批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<String> ids) {
    logger.info("开始根据主键集合批量删除SPU-供应商关系, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSpuSupplierMapper.deleteBatchIds(ids);

    logger.info("根据主键集合批量删除SPU-供应商关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);
    return result;
  }

  /**
   * 根据SPU ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySpuId(String spuId) {
    logger.info("开始根据SPU ID批量删除SPU-供应商关系, 操作人: {}, 参数: spuId={}", OPERATOR, spuId);

    int result = productSpuSupplierMapper.deleteBySpuId(spuId);

    logger.info("根据SPU ID批量删除SPU-供应商关系完成, 操作人: {}, 参数: spuId={}, 影响行数: {}",
      OPERATOR, spuId, result);
    return result;
  }

  /**
   * 根据供应商ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySupplierId(String supplierId) {
    logger.info("开始根据供应商ID批量删除SPU-供应商关系, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    int result = productSpuSupplierMapper.deleteBySupplierId(supplierId);

    logger.info("根据供应商ID批量删除SPU-供应商关系完成, 操作人: {}, 参数: supplierId={}, 影响行数: {}",
      OPERATOR, supplierId, result);
    return result;
  }

  /**
   * 检查SPU和供应商关联是否存在
   */
  @Override
  public boolean checkExists(String spuId, String supplierId) {
    logger.info("开始检查SPU-供应商关联是否存在, 操作人: {}, spuId: {}, supplierId: {}",
      OPERATOR, spuId, supplierId);

    int count = productSpuSupplierMapper.checkExists(spuId, supplierId);
    boolean exists = count > 0;

    logger.info("检查SPU-供应商关联是否存在完成, 操作人: {}, spuId: {}, supplierId: {}, 是否存在: {}",
      OPERATOR, spuId, supplierId, exists);
    return exists;
  }
}
