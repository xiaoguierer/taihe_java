package com.cn.taihe.back.product.mapper;

import com.cn.taihe.back.product.entity.ProductSpuSupplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SPU与供应商关联关系表 Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface ProductSpuSupplierMapper {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuSupplier selectById(@Param("id") String id);

  /**
   * 根据SPU ID查找
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuSupplier> selectBySpuId(@Param("spuId") String spuId);

  /**
   * 根据供应商ID查找
   * @param supplierId 供应商ID
   * @return 实体列表
   */
  List<ProductSpuSupplier> selectBySupplierId(@Param("supplierId") String supplierId);

  /**
   * 条件查询数据
   * @param productSpuSupplier 查询条件
   * @return 实体列表
   */
  List<ProductSpuSupplier> selectByCondition(ProductSpuSupplier productSpuSupplier);

  /**
   * 查询所有数据
   * @return 实体列表
   */
  List<ProductSpuSupplier> selectAll();

  /**
   * 新增数据
   * @param productSpuSupplier 实体对象
   * @return 影响行数
   */
  int insert(ProductSpuSupplier productSpuSupplier);

  /**
   * 修改数据（只更新非空字段）
   * @param productSpuSupplier 实体对象
   * @return 影响行数
   */
  int updateById(ProductSpuSupplier productSpuSupplier);

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
   * 根据供应商ID批量删除
   * @param supplierId 供应商ID
   * @return 影响行数
   */
  int deleteBySupplierId(@Param("supplierId") String supplierId);

  /**
   * 检查SPU和供应商关联是否存在
   * @param spuId SPU ID
   * @param supplierId 供应商ID
   * @return 存在数量
   */
  int checkExists(@Param("spuId") String spuId, @Param("supplierId") String supplierId);
}
