package com.cn.taihe.back.product.mapper;

import com.cn.taihe.back.product.entity.ProductSkuSupplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SKU与供应商关联关系表 Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface ProductSkuSupplierMapper {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSkuSupplier selectById(@Param("id") String id);

  /**
   * 根据SKU ID查找
   * @param skuId SKU ID
   * @return 实体列表
   */
  List<ProductSkuSupplier> selectBySkuId(@Param("skuId") String skuId);

  /**
   * 根据供应商ID查找
   * @param supplierId 供应商ID
   * @return 实体列表
   */
  List<ProductSkuSupplier> selectBySupplierId(@Param("supplierId") String supplierId);

  /**
   * 查询所有数据
   * @return 实体列表
   */
  List<ProductSkuSupplier> selectAll();

  /**
   * 新增数据
   * @param productSkuSupplier 实体对象
   * @return 影响行数
   */
  int insert(ProductSkuSupplier productSkuSupplier);

  /**
   * 批量新增数据
   * @param productSkuSupplierList 实体对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("list") List<ProductSkuSupplier> productSkuSupplierList);

  /**
   * 修改数据（只更新非空字段）
   * @param productSkuSupplier 实体对象
   * @return 影响行数
   */
  int updateById(ProductSkuSupplier productSkuSupplier);

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
   * 根据SKU ID批量删除
   * @param skuId SKU ID
   * @return 影响行数
   */
  int deleteBySkuId(@Param("skuId") String skuId);

  /**
   * 根据供应商ID批量删除
   * @param supplierId 供应商ID
   * @return 影响行数
   */
  int deleteBySupplierId(@Param("supplierId") String supplierId);
}
