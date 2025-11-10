package com.cn.taihe.back.product.mapper;

import com.cn.taihe.back.product.dto.WuXingAttributeQueryDTO;
import com.cn.taihe.back.product.entity.WuXingAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 五行属性Mapper接口
 *
 * @author system
 * @since 2025-02-20
 */
@Mapper
@Repository
public interface WuXingAttributeMapper {

  /**
   * 根据主键查询五行属性
   *
   * @param id 主键ID
   * @return 五行属性实体
   */
  WuXingAttribute selectById(@Param("id") String id);

  /**
   * 新增五行属性
   *
   * @param wuXingAttribute 五行属性实体
   * @return 影响行数
   */
  int insert(WuXingAttribute wuXingAttribute);

  /**
   * 根据主键更新五行属性（只更新非空字段）
   *
   * @param wuXingAttribute 五行属性实体
   * @return 影响行数
   */
  int updateById(WuXingAttribute wuXingAttribute);

  /**
   * 根据主键删除五行属性
   *
   * @param id 主键ID
   * @return 影响行数
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询五行属性列表
   *
   * @param queryDTO 查询条件
   * @return 五行属性列表
   */
  List<WuXingAttribute> selectByCondition(WuXingAttributeQueryDTO queryDTO);

  /**
   * 查询所有五行属性
   *
   * @return 五行属性列表
   */
  List<WuXingAttribute> selectAll();

  /**
   * 查询所有启用的五行属性（关键有效信息）
   *
   * @return 启用的五行属性列表
   */
  List<WuXingAttribute> selectAllActive();

  /**
   * 根据条件查询关键有效信息列表（简化字段，用于下拉框等场景）
   *
   * @param queryDTO 查询条件
   * @return 五行属性简化列表
   */
  List<WuXingAttribute> selectKeyInfoList(WuXingAttributeQueryDTO queryDTO);

  /**
   * 根据元素分类查询启用的五行属性
   *
   * @param elementCategory 元素分类
   * @return 五行属性列表
   */
  List<WuXingAttribute> selectByCategory(@Param("elementCategory") String elementCategory);

  /**
   * 根据元素层级查询启用的五行属性
   *
   * @param elementTier 元素层级
   * @return 五行属性列表
   */
  List<WuXingAttribute> selectByTier(@Param("elementTier") Integer elementTier);

  /**
   * 根据主键集合批量删除五行属性
   *
   * @param ids 主键ID集合
   * @return 影响行数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 根据主键更新状态（冻结/启用）
   *
   * @param id 主键ID
   * @param isActive 状态值
   * @return 影响行数
   */
  int updateStatusById(@Param("id") String id, @Param("isActive") Integer isActive);

  /**
   * 根据元素键名查询五行属性
   *
   * @param elementKey 元素键名
   * @return 五行属性实体
   */
  WuXingAttribute selectByElementKey(@Param("elementKey") String elementKey);

  /**
   * 检查元素键名是否存在
   *
   * @param elementKey 元素键名
   * @param excludeId 排除的主键ID（用于更新时检查）
   * @return 存在数量
   */
  int countByElementKey(@Param("elementKey") String elementKey, @Param("excludeId") String excludeId);

  /**
   * 查询五行属性的关键字段（用于缓存或快速查询）
   *
   * @return 关键字段列表
   */
  List<WuXingAttribute> selectKeyFields();

  /**
   * 根据状态查询数量（用于统计）
   *
   * @param isActive 状态值
   * @return 数量
   */
  int countByStatus(@Param("isActive") Integer isActive);
}
