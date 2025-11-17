package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.entity.ProductSpuSupplier;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * SPU与供应商关联关系表 Service接口
 *
 * @author system
 */
public interface ProductSpuSupplierService {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuSupplier findById(String id);

  /**
   * 根据SPU ID查找
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuSupplier> findBySpuId(String spuId);

  /**
   * 根据供应商ID查找
   * @param supplierId 供应商ID
   * @return 实体列表
   */
  List<ProductSpuSupplier> findBySupplierId(String supplierId);

  /**
   * 条件查询数据
   * @param productSpuSupplier 查询条件
   * @return 实体列表
   */
  List<ProductSpuSupplier> findByCondition(ProductSpuSupplier productSpuSupplier);

  /**
   * 条件分页查询数据
   * @param productSpuSupplier 查询条件
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<ProductSpuSupplier> findByConditionWithPage(ProductSpuSupplier productSpuSupplier, int page, int size);

  /**
   * 查询所有数据
   * @return 实体列表
   */
  List<ProductSpuSupplier> findAll();

  /**
   * 分页查询所有数据
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<ProductSpuSupplier> findAllWithPage(int page, int size);

  /**
   * 新增数据
   * @param productSpuSupplier 实体对象
   * @return 影响行数
   */
  int save(ProductSpuSupplier productSpuSupplier);

  /**
   * 创建SPU与供应商关联关系（先删除后新增）
   * @param spuId SPU主键ID
   * @param supplierIds 供应商主键集合
   * @return 影响行数
   */
  int createRealiations(String spuId, List<String> supplierIds);

  /**
   * 修改数据
   * @param productSpuSupplier 实体对象
   * @return 影响行数
   */
  int update(ProductSpuSupplier productSpuSupplier);

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
   * 根据SPU ID批量删除
   * @param spuId SPU ID
   * @return 影响行数
   */
  int deleteBySpuId(String spuId);

  /**
   * 根据供应商ID批量删除
   * @param supplierId 供应商ID
   * @return 影响行数
   */
  int deleteBySupplierId(String supplierId);

  /**
   * 检查SPU和供应商关联是否存在
   * @param spuId SPU ID
   * @param supplierId 供应商ID
   * @return 是否存在
   */
  boolean checkExists(String spuId, String supplierId);
}
