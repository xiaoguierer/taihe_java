package com.cn.taihe.back.product.mapper;

import com.cn.taihe.back.product.entity.ProductSpuSkuRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SPU-SKU关联关系表 Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface ProductSpuSkuRelMapper {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuSkuRel selectById(@Param("id") String id);

  /**
   * 根据SPU ID查找
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuSkuRel> selectBySpuId(@Param("spuId") String spuId);

  /**
   * 根据SKU ID查找
   * @param skuId SKU ID
   * @return 实体列表
   */
  List<ProductSpuSkuRel> selectBySkuId(@Param("skuId") String skuId);

  /**
   * 条件查询数据
   * @param productSpuSkuRel 查询条件
   * @return 实体列表
   */
  List<ProductSpuSkuRel> selectByCondition(ProductSpuSkuRel productSpuSkuRel);

  /**
   * 新增数据
   * @param productSpuSkuRel 实体对象
   * @return 影响行数
   */
  int insert(ProductSpuSkuRel productSpuSkuRel);

  /**
   * 批量新增数据
   * @param productSpuSkuRelList 实体对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("list") List<ProductSpuSkuRel> productSpuSkuRelList);

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
   * 根据SKU ID批量删除
   * @param skuId SKU ID
   * @return 影响行数
   */
  int deleteBySkuId(@Param("skuId") String skuId);
}
