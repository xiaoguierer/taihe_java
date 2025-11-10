package com.cn.taihe.back.product.mapper;

import com.cn.taihe.back.product.entity.ProductSpuCategoryTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SPU-品类标签关系表 Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface ProductSpuCategoryTagMapper {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuCategoryTag selectById(@Param("id") String id);

  /**
   * 根据SPU ID查找（外键查找）
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuCategoryTag> selectBySpuId(@Param("spuId") String spuId);

  /**
   * 根据品类标签ID查找（外键查找）
   * @param categoryTagId 品类标签ID
   * @return 实体列表
   */
  List<ProductSpuCategoryTag> selectByCategoryTagId(@Param("categoryTagId") String categoryTagId);

  /**
   * 新增数据
   * @param productSpuCategoryTag 实体对象
   * @return 影响行数
   */
  int insert(ProductSpuCategoryTag productSpuCategoryTag);

  /**
   * 批量新增数据
   * @param productSpuCategoryTagList 实体对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("list") List<ProductSpuCategoryTag> productSpuCategoryTagList);

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
   * 根据品类标签ID批量删除
   * @param categoryTagId 品类标签ID
   * @return 影响行数
   */
  int deleteByCategoryTagId(@Param("categoryTagId") String categoryTagId);
}
