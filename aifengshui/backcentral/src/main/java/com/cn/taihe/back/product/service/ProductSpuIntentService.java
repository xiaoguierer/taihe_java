package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.entity.ProductSpuIntent;

import java.util.List;

/**
 * SPU-情感意图关系表 Service接口
 *
 * @author system
 */
public interface ProductSpuIntentService {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuIntent findById(String id);

  /**
   * 根据SPU ID查找
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuIntent> findBySpuId(String spuId);

  /**
   * 根据情感意图ID查找
   * @param intentId 情感意图ID
   * @return 实体列表
   */
  List<ProductSpuIntent> findByIntentId(String intentId);

  /**
   * 新增数据
   * @param productSpuIntent 实体对象
   * @return 影响行数
   */
  int save(ProductSpuIntent productSpuIntent);

  /**
   * 批量新增数据
   * @param productSpuIntentList 实体对象列表
   * @return 影响行数
   */
  int saveBatch(List<ProductSpuIntent> productSpuIntentList);

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
   * 根据情感意图ID批量删除
   * @param intentId 情感意图ID
   * @return 影响行数
   */
  int deleteByIntentId(String intentId);
}
