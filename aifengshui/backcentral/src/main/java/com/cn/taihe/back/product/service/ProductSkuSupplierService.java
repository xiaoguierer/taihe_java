package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.entity.ProductSkuSupplier;

import java.util.List;

/**
 * SKU与供应商关联关系表 Service接口
 *
 * @author system
 */
public interface ProductSkuSupplierService {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSkuSupplier findById(String id);

  /**
   * 根据SKU ID查找
   * @param skuId SKU ID
   * @return 实体列表
   */
  List<ProductSkuSupplier> findBySkuId(String skuId);

  /**
   * 根据供应商ID查找
   * @param supplierId 供应商ID
   * @return 实体列表
   */
  List<ProductSkuSupplier> findBySupplierId(String supplierId);

  /**
   * 查询所有数据
   * @return 实体列表
   */
  List<ProductSkuSupplier> findAll();

  /**
   * 新增数据
   * @param productSkuSupplier 实体对象
   * @return 影响行数
   */
  int save(ProductSkuSupplier productSkuSupplier);

  /**
   * 批量新增数据
   * @param productSkuSupplierList 实体对象列表
   * @return 影响行数
   */
  int saveBatch(List<ProductSkuSupplier> productSkuSupplierList);

  /**
   * 创建SKU与供应商关联关系（先删除后新增）
   * @param skuId SKU ID
   * @param supplierIds 供应商ID集合
   * @return 影响行数
   */
  int createRealiations(String skuId, List<String> supplierIds);

  /**
   * 修改数据
   * @param productSkuSupplier 实体对象
   * @return 影响行数
   */
  int update(ProductSkuSupplier productSkuSupplier);

  /**
   * 根据主键删除数据
   * @param id 主键ID
   * @return 影响行数
   */
  int deleteById(String id);

  /**
   * 根据主键集合批量删除
   * @param ids 主键集合
   * @return 影响行数
   */
  int deleteBatchIds(List<String> ids);

  /**
   * 根据SKU ID批量删除
   * @param skuId SKU ID
   * @return 影响行数
   */
  int deleteBySkuId(String skuId);

  /**
   * 根据供应商ID批量删除
   * @param supplierId 供应商ID
   * @return 影响行数
   */
  int deleteBySupplierId(String supplierId);
}
