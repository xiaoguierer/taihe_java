package com.cn.taihe.back.product.mapper;
import com.cn.taihe.back.product.dto.EmotionalIntentQueryDTO;
import com.cn.taihe.back.product.entity.EmotionalIntent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 情感意图表Mapper接口
 *
 * @author system
 * @date 2025-11-06
 */
@Mapper
@Repository
public interface EmotionalIntentMapper {

  /**
   * 根据主键查询情感意图
   *
   * @param id 主键ID
   * @return 情感意图信息
   */
  EmotionalIntent selectById(@Param("id") String id);

  /**
   * 新增情感意图
   *
   * @param
   * @return 影响行数
   */
  int insert(EmotionalIntent emotionalIntentDTO);

  /**
   * 根据主键更新情感意图（只更新非空字段）
   *
   * @param emotionalIntentDTO 情感意图信息
   * @return 影响行数
   */
  int updateById(EmotionalIntent emotionalIntentDTO);

  /**
   * 根据主键逻辑删除情感意图（更新is_active=2）
   *
   * @param id 主键ID
   * @return 影响行数
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询情感意图列表（支持分页）
   *
   * @param queryDTO 查询条件
   * @return 情感意图列表
   */
  List<EmotionalIntent> selectByCondition(EmotionalIntentQueryDTO queryDTO);

  /**
   * 条件查询情感意图数量（用于分页）
   *
   * @param queryDTO 查询条件
   * @return 数量
   */
  long countByCondition(EmotionalIntentQueryDTO queryDTO);

  /**
   * 查询所有情感意图（不包含已删除的）
   *
   * @return 情感意图列表
   */
  List<EmotionalIntent> selectAll();

  /**
   * 查询所有情感意图的关键信息（简化字段，用于下拉选择等场景）
   *
   * @return 情感意图关键信息列表
   */
  List<EmotionalIntent> selectAllKeyInfo();

  /**
   * 根据主键集合批量逻辑删除情感意图（更新is_active=2）
   *
   * @param ids 主键ID集合
   * @return 影响行数
   */
  int deleteBatchByIds(@Param("ids") List<String> ids);

  /**
   * 根据主键更新状态
   *
   * @param id 主键ID
   * @param isActive 状态:0-冻结,1-启用,2-删除
   * @return 影响行数
   */
  int updateStatusById(@Param("id") String id, @Param("isActive") Integer isActive);

  /**
   * 根据主键集合批量更新状态
   *
   * @param ids 主键ID集合
   * @param isActive 状态:0-冻结,1-启用,2-删除
   * @return 影响行数
   */
  int updateStatusBatchByIds(@Param("ids") List<String> ids, @Param("isActive") Integer isActive);

  /**
   * 更新排序值
   *
   * @param id 主键ID
   * @param sortOrder 排序值
   * @return 影响行数
   */
  int updateSortOrder(@Param("id") String id, @Param("sortOrder") Integer sortOrder);

  /**
   * 更新推荐状态
   *
   * @param id 主键ID
   * @param isFeatured 是否推荐:1-是,0-否
   * @return 影响行数
   */
  int updateFeaturedStatus(@Param("id") String id, @Param("isFeatured") Integer isFeatured);

  /**
   * 更新显示状态
   *
   * @param id 主键ID
   * @param showInNavigation 是否在导航显示:1-是,0-否
   * @return 影响行数
   */
  int updateShowStatus(@Param("id") String id, @Param("showInNavigation") Integer showInNavigation);
}
