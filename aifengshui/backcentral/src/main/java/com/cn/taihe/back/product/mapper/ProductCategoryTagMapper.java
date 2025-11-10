package com.cn.taihe.back.product.mapper;

import com.cn.taihe.back.product.entity.ProductCategoryTag;
import com.cn.taihe.back.product.dto.ProductCategoryTagQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品品类标签表 Mapper接口
 *
 * @author auto generate
 * @since 2025-11-07
 */
@Mapper
@Repository
public interface ProductCategoryTagMapper {

  /**
   * 根据主键查询
   *
   * @param id 主键ID
   * @return 商品品类标签信息
   */
  ProductCategoryTag selectById(@Param("id") String id);

  /**
   * 新增数据
   *
   * @param productCategoryTag 商品品类标签信息
   * @return 影响行数
   */
  int insert(ProductCategoryTag productCategoryTag);

  /**
   * 根据主键更新数据（只更新非空字段）
   *
   * @param productCategoryTag 商品品类标签信息
   * @return 影响行数
   */
  int updateById(ProductCategoryTag productCategoryTag);

  /**
   * 根据主键删除数据
   *
   * @param id 主键ID
   * @return 影响行数
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询列表（不分页）
   *
   * @param queryDTO 查询条件
   * @return 商品品类标签列表
   */
  List<ProductCategoryTag> selectList(ProductCategoryTagQueryDTO queryDTO);

  /**
   * 条件查询分页列表
   *
   * @param queryDTO 查询条件
   * @param offset 偏移量
   * @param limit 每页数量
   * @return 商品品类标签列表
   */
  List<ProductCategoryTag> selectPageList(@Param("queryDTO") ProductCategoryTagQueryDTO queryDTO,
                                          @Param("offset") int offset,
                                          @Param("limit") int limit);

  /**
   * 条件查询总数
   *
   * @param queryDTO 查询条件
   * @return 总记录数
   */
  long selectCount(ProductCategoryTagQueryDTO queryDTO);

  /**
   * 查询所有数据
   *
   * @return 所有商品品类标签列表
   */
  List<ProductCategoryTag> selectAll();

  /**
   * 根据主键集合批量删除
   *
   * @param ids 主键ID集合
   * @return 影响行数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 根据主键更新状态（冻结/解冻）
   *
   * @param id 主键ID
   * @param isActive 是否启用
   * @return 影响行数
   */
  int updateStatusById(@Param("id") String id, @Param("isActive") Boolean isActive);

  /**
   * 批量更新状态（冻结/解冻）
   *
   * @param ids 主键ID集合
   * @param isActive 是否启用
   * @return 影响行数
   */
  int updateBatchStatus(@Param("ids") List<String> ids, @Param("isActive") Boolean isActive);
}
