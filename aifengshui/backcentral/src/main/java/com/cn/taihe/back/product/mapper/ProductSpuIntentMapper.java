package com.cn.taihe.back.product.mapper;
import com.cn.taihe.back.product.entity.ProductSpuIntent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SPU-情感意图关系表 Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface ProductSpuIntentMapper {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuIntent selectById(@Param("id") String id);

  /**
   * 根据SPU ID查找（外键查找）
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuIntent> selectBySpuId(@Param("spuId") String spuId);

  /**
   * 根据情感意图ID查找（外键查找）
   * @param intentId 情感意图ID
   * @return 实体列表
   */
  List<ProductSpuIntent> selectByIntentId(@Param("intentId") String intentId);

  /**
   * 新增数据
   * @param productSpuIntent 实体对象
   * @return 影响行数
   */
  int insert(ProductSpuIntent productSpuIntent);

  /**
   * 批量新增数据
   * @param productSpuIntentList 实体对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("list") List<ProductSpuIntent> productSpuIntentList);

  /**
   * 根据主键删除数据
   * @param id 主键ID
   * @return 影响行数
   */
  int deleteById(@Param("id") String id);

  /**
   * 根据主键集合批量删除
   * @param ids 主键集合
   * @return 影响行数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 根据SPU ID批量删除
   * @param spuId SPU ID
   * @return 影响行数
   */
  int deleteBySpuId(@Param("spuId") String spuId);

  /**
   * 根据情感意图ID批量删除
   * @param intentId 情感意图ID
   * @return 影响行数
   */
  int deleteByIntentId(@Param("intentId") String intentId);
}
