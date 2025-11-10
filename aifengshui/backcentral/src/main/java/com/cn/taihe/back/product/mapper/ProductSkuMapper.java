package com.cn.taihe.back.product.mapper;

import com.cn.taihe.back.product.dto.ProductSkuQueryDTO;
import com.cn.taihe.back.product.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品SKU Mapper接口
 *
 * @author ADMIN
 */
@Mapper
@Repository
public interface ProductSkuMapper {

  /**
   * 根据主键查找
   */
  ProductSku selectById(@Param("id") String id);

  /**
   * 新增数据
   */
  int insert(ProductSku productSku);

  /**
   * 修改数据（更新非空字段）
   */
  int updateById(ProductSku productSku);

  /**
   * 根据主键删除数据
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询数据
   */
  List<ProductSku> selectByCondition(ProductSkuQueryDTO queryDTO);

  /**
   * 查询所有数据
   */
  List<ProductSku> selectAll();

  /**
   * 根据主键集合批量删除
   */
  int deleteByIds(@Param("ids") List<String> ids);

  /**
   * 更新状态
   */
  int updateStatus(@Param("id") String id, @Param("status") Integer status, @Param("updatedBy") Long updatedBy);

  /**
   * 更新是否可售状态（冻结/解冻）
   */
  int updateIsAvailable(@Param("id") String id, @Param("isAvailable") Boolean isAvailable, @Param("updatedBy") Long updatedBy);

  /**
   * 批量更新状态
   */
  int batchUpdateStatus(@Param("ids") List<String> ids, @Param("status") Integer status, @Param("updatedBy") Long updatedBy);

  /**
   * 批量更新是否可售状态
   */
  int batchUpdateIsAvailable(@Param("ids") List<String> ids, @Param("isAvailable") Boolean isAvailable, @Param("updatedBy") Long updatedBy);

  /**
   * 根据SPU ID查询
   */
  List<ProductSku> selectBySpuId(@Param("spuId") String spuId);

  /**
   * 根据SKU编码查询
   */
  ProductSku selectBySkuCode(@Param("skuCode") String skuCode);

  /**
   * 检查SKU编码是否存在
   */
  int countBySkuCode(@Param("skuCode") String skuCode, @Param("excludeId") String excludeId);
}
