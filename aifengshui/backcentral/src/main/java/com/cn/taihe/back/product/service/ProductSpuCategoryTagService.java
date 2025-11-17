package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.entity.ProductSpuCategoryTag;

import java.util.List;

/**
 * SPU-品类标签关系表 Service接口
 *
 * @author system
 */
public interface ProductSpuCategoryTagService {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuCategoryTag findById(String id);

  /**
   * 根据SPU ID查找
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuCategoryTag> findBySpuId(String spuId);

  /**
   * 根据品类标签ID查找
   * @param categoryTagId 品类标签ID
   * @return 实体列表
   */
  List<ProductSpuCategoryTag> findByCategoryTagId(String categoryTagId);

  /**
   * 新增数据
   * @param productSpuCategoryTag 实体对象
   * @return 影响行数
   */
  int save(ProductSpuCategoryTag productSpuCategoryTag);

  /**
   * 批量新增数据
   * @param productSpuCategoryTagList 实体对象列表
   * @return 影响行数
   */
  int saveBatch(List<ProductSpuCategoryTag> productSpuCategoryTagList);

  /**
   * @description:
   * @author: 创建产品呢spu和分类标签之间的关系
   * @date: 2025/11/15 15:19
   * @param: [spuId, list]
   * @return: [java.lang.String, java.util.List]
   **/
  int createRealiations(String spuId, List list);

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
   * 根据品类标签ID批量删除
   * @param categoryTagId 品类标签ID
   * @return 影响行数
   */
  int deleteByCategoryTagId(String categoryTagId);
}
