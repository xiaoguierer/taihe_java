package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.product.entity.ProductSkuSupplier;
import com.cn.taihe.back.product.mapper.ProductSkuSupplierMapper;
import com.cn.taihe.back.product.service.ProductSkuSupplierService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SKU与供应商关联关系表 Service实现类
 *
 * @author system
 */
@Service
public class ProductSkuSupplierServiceImpl implements ProductSkuSupplierService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSkuSupplierServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSkuSupplierMapper productSkuSupplierMapper;

  /**
   * 根据主键查找
   */
  @Override
  public ProductSkuSupplier findById(String id) {
    logger.info("开始根据主键查找SKU-供应商关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    ProductSkuSupplier result = productSkuSupplierMapper.selectById(id);

    logger.info("根据主键查找SKU-供应商关系完成, 操作人: {}, 参数: id={}, 结果: {}",
      OPERATOR, id, result != null ? "找到记录" : "未找到记录");
    return result;
  }

  /**
   * 根据SKU ID查找
   */
  @Override
  public List<ProductSkuSupplier> findBySkuId(String skuId) {
    logger.info("开始根据SKU ID查找SKU-供应商关系, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    List<ProductSkuSupplier> result = productSkuSupplierMapper.selectBySkuId(skuId);

    logger.info("根据SKU ID查找SKU-供应商关系完成, 操作人: {}, 参数: skuId={}, 结果数量: {}",
      OPERATOR, skuId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 根据供应商ID查找
   */
  @Override
  public List<ProductSkuSupplier> findBySupplierId(String supplierId) {
    logger.info("开始根据供应商ID查找SKU-供应商关系, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    List<ProductSkuSupplier> result = productSkuSupplierMapper.selectBySupplierId(supplierId);

    logger.info("根据供应商ID查找SKU-供应商关系完成, 操作人: {}, 参数: supplierId={}, 结果数量: {}",
      OPERATOR, supplierId, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 查询所有数据
   */
  @Override
  public List<ProductSkuSupplier> findAll() {
    logger.info("开始查询所有SKU-供应商关系, 操作人: {}", OPERATOR);

    List<ProductSkuSupplier> result = productSkuSupplierMapper.selectAll();

    logger.info("查询所有SKU-供应商关系完成, 操作人: {}, 结果数量: {}",
      OPERATOR, result != null ? result.size() : 0);
    return result;
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int save(ProductSkuSupplier productSkuSupplier) {
    logger.info("开始新增SKU-供应商关系, 操作人: {}, 参数: {}", OPERATOR, productSkuSupplier);
    productSkuSupplier.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
    int result = productSkuSupplierMapper.insert(productSkuSupplier);

    logger.info("新增SKU-供应商关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSkuSupplier, result);
    return result;
  }

  /**
   * 批量新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int saveBatch(List<ProductSkuSupplier> productSkuSupplierList) {
    logger.info("开始批量新增SKU-供应商关系, 操作人: {}, 参数数量: {}", OPERATOR,
      productSkuSupplierList != null ? productSkuSupplierList.size() : 0);

    int result = productSkuSupplierMapper.insertBatch(productSkuSupplierList);

    logger.info("批量新增SKU-供应商关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, productSkuSupplierList != null ? productSkuSupplierList.size() : 0, result);
    return result;
  }

  /**
   * 创建SKU与供应商关联关系（先删除后新增）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int createRealiations(String skuId, List<String> supplierIds) {
    logger.info("开始创建SKU-供应商关联关系, 操作人: {}, skuId: {}, supplierIds数量: {}",
      OPERATOR, skuId, supplierIds != null ? supplierIds.size() : 0);

    // 1. 先删除该SKU的所有关联关系
    int deleteCount = productSkuSupplierMapper.deleteBySkuId(skuId);
    logger.info("删除SKU原有供应商关联完成, 操作人: {}, skuId: {}, 删除数量: {}", OPERATOR, skuId, deleteCount);

    // 2. 如果供应商列表为空，直接返回
    if (supplierIds == null || supplierIds.isEmpty()) {
      logger.info("供应商列表为空，无需新增, 操作人: {}, skuId: {}", OPERATOR, skuId);
      return deleteCount;
    }

    // 3. 批量新增新的关联关系
    List<ProductSkuSupplier> relationList = new ArrayList<>();
    for (int i = 0; i < supplierIds.size(); i++) {
      String supplierId = supplierIds.get(i);
      ProductSkuSupplier relation = new ProductSkuSupplier();
      relation.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      relation.setSkuId(skuId);
      relation.setSupplierId(supplierId);
      relation.setSortOrder(i); // 按顺序设置排序值
      relation.setCreatedTime(LocalDateTime.now());
      relationList.add(relation);
    }

    int insertCount = productSkuSupplierMapper.insertBatch(relationList);
    logger.info("创建SKU-供应商关联关系完成, 操作人: {}, skuId: {}, 新增数量: {}, 总影响行数: {}",
      OPERATOR, skuId, insertCount, deleteCount + insertCount);

    return deleteCount + insertCount;
  }

  /**
   * 修改数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int update(ProductSkuSupplier productSkuSupplier) {
    logger.info("开始修改SKU-供应商关系, 操作人: {}, 参数: {}", OPERATOR, productSkuSupplier);

    int result = productSkuSupplierMapper.updateById(productSkuSupplier);

    logger.info("修改SKU-供应商关系完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, productSkuSupplier, result);
    return result;
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(String id) {
    logger.info("开始根据主键删除SKU-供应商关系, 操作人: {}, 参数: id={}", OPERATOR, id);

    int result = productSkuSupplierMapper.deleteById(id);

    logger.info("根据主键删除SKU-供应商关系完成, 操作人: {}, 参数: id={}, 影响行数: {}",
      OPERATOR, id, result);
    return result;
  }

  /**
   * 根据主键集合批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<String> ids) {
    logger.info("开始根据主键集合批量删除SKU-供应商关系, 操作人: {}, 参数数量: {}", OPERATOR,
      ids != null ? ids.size() : 0);

    int result = productSkuSupplierMapper.deleteBatchIds(ids);

    logger.info("根据主键集合批量删除SKU-供应商关系完成, 操作人: {}, 参数数量: {}, 影响行数: {}",
      OPERATOR, ids != null ? ids.size() : 0, result);
    return result;
  }

  /**
   * 根据SKU ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySkuId(String skuId) {
    logger.info("开始根据SKU ID批量删除SKU-供应商关系, 操作人: {}, 参数: skuId={}", OPERATOR, skuId);

    int result = productSkuSupplierMapper.deleteBySkuId(skuId);

    logger.info("根据SKU ID批量删除SKU-供应商关系完成, 操作人: {}, 参数: skuId={}, 影响行数: {}",
      OPERATOR, skuId, result);
    return result;
  }

  /**
   * 根据供应商ID批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBySupplierId(String supplierId) {
    logger.info("开始根据供应商ID批量删除SKU-供应商关系, 操作人: {}, 参数: supplierId={}", OPERATOR, supplierId);

    int result = productSkuSupplierMapper.deleteBySupplierId(supplierId);

    logger.info("根据供应商ID批量删除SKU-供应商关系完成, 操作人: {}, 参数: supplierId={}, 影响行数: {}",
      OPERATOR, supplierId, result);
    return result;
  }
}
