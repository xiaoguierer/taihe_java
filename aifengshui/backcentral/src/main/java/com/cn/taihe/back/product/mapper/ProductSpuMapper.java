package com.cn.taihe.back.product.mapper;
import com.cn.taihe.back.product.dto.ProductSpuQueryDTO;
import com.cn.taihe.back.product.entity.ProductSpu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品SPU Mapper接口
 *
 * @author system
 * @since 2025-01-01
 */
@Mapper
@Repository
public interface ProductSpuMapper {

  /**
   * 根据主键查找数据
   *
   * @param id 主键ID
   * @return 商品SPU信息
   */
  ProductSpu selectById(@Param("id") String id);

  /**
   * 新增数据
   *
   * @param productSpu 商品SPU信息
   * @return 影响行数
   */
  int insert(ProductSpu productSpu);

  /**
   * 修改数据（只更新非空字段）
   *
   * @param productSpu 商品SPU信息
   * @return 影响行数
   */
  int updateByIdSelective(ProductSpu productSpu);

  /**
   * 根据主键删除数据
   *
   * @param id 主键ID
   * @return 影响行数
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询数据（支持分页）
   *
   * @param queryDTO 查询条件
   * @return 商品SPU列表
   */
  List<ProductSpu> selectByCondition(ProductSpuQueryDTO queryDTO);

  /**
   * 查询所有数据
   *
   * @return 所有商品SPU列表
   */
  List<ProductSpu> selectAll();

  /**
   * 根据主键集合批量删除数据
   *
   * @param ids 主键ID集合
   * @return 影响行数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 根据主键更新状态（冻结/启用）
   *
   * @param id 主键ID
   * @param isActive 是否启用
   * @param updatedBy 更新人ID
   * @return 影响行数
   */
  int updateStatusById(@Param("id") String id,
                       @Param("isActive") Boolean isActive,
                       @Param("updatedBy") Long updatedBy);

  /**
   * 根据主键批量更新状态
   *
   * @param ids 主键ID集合
   * @param isActive 是否启用
   * @param updatedBy 更新人ID
   * @return 影响行数
   */
  int updateStatusBatchIds(@Param("ids") List<String> ids,
                           @Param("isActive") Boolean isActive,
                           @Param("updatedBy") Long updatedBy);

  /**
   * 根据SPU编码查询数据
   *
   * @param spuCode SPU编码
   * @return 商品SPU信息
   */
  ProductSpu selectBySpuCode(@Param("spuCode") String spuCode);

  /**
   * 检查SPU编码是否存在
   *
   * @param spuCode SPU编码
   * @param excludeId 排除的主键ID（用于更新时检查）
   * @return 存在数量
   */
  int countBySpuCode(@Param("spuCode") String spuCode,
                     @Param("excludeId") String excludeId);
}
