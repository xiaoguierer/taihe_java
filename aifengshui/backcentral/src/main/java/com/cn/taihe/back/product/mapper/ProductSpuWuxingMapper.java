package com.cn.taihe.back.product.mapper;
import com.cn.taihe.back.product.entity.ProductSpuWuxing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SPU-五行关系表 Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface ProductSpuWuxingMapper {

  /**
   * 根据主键查找
   * @param id 主键ID
   * @return 实体对象
   */
  ProductSpuWuxing selectById(@Param("id") String id);

  /**
   * 根据SPU ID查找（外键查找）
   * @param spuId SPU ID
   * @return 实体列表
   */
  List<ProductSpuWuxing> selectBySpuId(@Param("spuId") String spuId);

  /**
   * 根据五行属性ID查找（外键查找）
   * @param wuXingId 五行属性ID
   * @return 实体列表
   */
  List<ProductSpuWuxing> selectByWuXingId(@Param("wuXingId") String wuXingId);

  /**
   * 新增数据
   * @param productSpuWuxing 实体对象
   * @return 影响行数
   */
  int insert(ProductSpuWuxing productSpuWuxing);

  /**
   * 批量新增数据
   * @param productSpuWuxingList 实体对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("list") List<ProductSpuWuxing> productSpuWuxingList);

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
   * 根据五行属性ID批量删除
   * @param wuXingId 五行属性ID
   * @return 影响行数
   */
  int deleteByWuXingId(@Param("wuXingId") String wuXingId);
}
