package com.cn.taihe.back.product.service;
import com.cn.taihe.back.product.entity.ProductSpuSkuRel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * SPU-SKU关联关系表 Service接口
 *
 * @author system
 */
public interface ProductSpuSkuRelService {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuSkuRel findById(String id);

  /**
   * 根据SPU ID查找
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuSkuRel> findBySpuId(String spuId);

  /**
   * 根据SKU ID查找
   * @param skuId SKU ID
   * @return 实体列表
   */
  List<ProductSpuSkuRel> findBySkuId(String skuId);

  /**
   * 条件查询数据
   * @param productSpuSkuRel 查询条件
   * @return 实体列表
   */
  List<ProductSpuSkuRel> findByCondition(ProductSpuSkuRel productSpuSkuRel);

  /**
   * 条件分页查询数据
   * @param productSpuSkuRel 查询条件
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<ProductSpuSkuRel> findByConditionWithPage(ProductSpuSkuRel productSpuSkuRel, int page, int size);

  /**
   * 查询所有数据
   * @return 实体列表
   */
  List<ProductSpuSkuRel> findAll();

  /**
   * 分页查询所有数据
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<ProductSpuSkuRel> findAllWithPage(int page, int size);

  /**
   * 新增数据
   * @param productSpuSkuRel 实体对象
   * @return 影响行数
   */
  int save(ProductSpuSkuRel productSpuSkuRel);

  /**
   * 批量新增数据
   * @param productSpuSkuRelList 实体对象列表
   * @return 影响行数
   */
  int saveBatch(List<ProductSpuSkuRel> productSpuSkuRelList);

  /**
   * 创建SPU与SKU关联关系（先删除后新增）
   * @param spuId SPU主键ID
   * @param skuIds SKU主键集合
   * @return 影响行数
   */
  int createRealiations(String spuId, List<String> skuIds);

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
   * 根据SKU ID批量删除
   * @param skuId SKU ID
   * @return 影响行数
   */
  int deleteBySkuId(String skuId);
}
