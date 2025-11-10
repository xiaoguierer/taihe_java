package com.cn.taihe.back.product.service;


import com.cn.taihe.back.product.entity.ProductSpuWuxing;

import java.util.List;

/**
 * SPU-五行关系表 Service接口
 *
 * @author system
 */
public interface ProductSpuWuxingService {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuWuxing findById(String id);

  /**
   * 根据SPU ID查找
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuWuxing> findBySpuId(String spuId);

  /**
   * 根据五行属性ID查找
   * @param wuXingId 五行属性ID
   * @return 实体列表
   */
  List<ProductSpuWuxing> findByWuXingId(String wuXingId);

  /**
   * 新增数据
   * @param productSpuWuxing 实体对象
   * @return 影响行数
   */
  int save(ProductSpuWuxing productSpuWuxing);

  /**
   * 批量新增数据
   * @param productSpuWuxingList 实体对象列表
   * @return 影响行数
   */
  int saveBatch(List<ProductSpuWuxing> productSpuWuxingList);

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
   * 根据五行属性ID批量删除
   * @param wuXingId 五行属性ID
   * @return 影响行数
   */
  int deleteByWuXingId(String wuXingId);
}
