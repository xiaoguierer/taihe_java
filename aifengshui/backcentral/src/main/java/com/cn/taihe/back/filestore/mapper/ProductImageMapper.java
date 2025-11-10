package com.cn.taihe.back.filestore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.filestore.dto.ProductImageQueryDTO;
import com.cn.taihe.back.filestore.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品图片表 Mapper 接口
 *
 * @author system
 * @date 2025-11-07
 */
@Mapper
@Repository
public interface ProductImageMapper extends BaseMapper<ProductImage> {

  /**
   * 根据主键查询商品图片
   *
   * @param id 主键ID
   * @return 商品图片信息
   */
  ProductImage selectById(@Param("id") String id);

  /**
   * 新增商品图片
   *
   * @param productImage 商品图片信息
   * @return 影响行数
   */
  int insertProductImage(@Param("entity") ProductImage productImage);

  /**
   * 根据主键更新商品图片（非空字段更新）
   *
   * @param productImage 商品图片信息
   * @return 影响行数
   */
  int updateProductImage(@Param("entity") ProductImage productImage);

  /**
   * 根据主键删除商品图片
   *
   * @param id 主键ID
   * @return 影响行数
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询商品图片列表
   *
   * @param queryDTO 查询条件
   * @return 商品图片列表
   */
  List<ProductImage> selectProductImageList(@Param("query") ProductImageQueryDTO queryDTO);

  /**
   * 查询所有商品图片
   *
   * @return 商品图片列表
   */
  List<ProductImage> selectAllProductImage();

  /**
   * 根据主键集合批量删除商品图片
   *
   * @param ids 主键ID集合
   * @return 影响行数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 更新商品图片状态（冻结/启用）
   *
   * @param id 主键ID
   * @param isActive 是否启用
   * @param updatedBy 更新人ID
   * @return 影响行数
   */
  int updateStatus(@Param("id") String id,
                   @Param("isActive") Boolean isActive,
                   @Param("updatedBy") Long updatedBy);

  /**
   * 批量更新商品图片状态
   *
   * @param ids 主键ID集合
   * @param isActive 是否启用
   * @param updatedBy 更新人ID
   * @return 影响行数
   */
  int updateBatchStatus(@Param("ids") List<String> ids,
                        @Param("isActive") Boolean isActive,
                        @Param("updatedBy") Long updatedBy);

}
