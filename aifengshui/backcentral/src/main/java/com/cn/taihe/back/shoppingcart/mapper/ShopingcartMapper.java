package com.cn.taihe.back.shoppingcart.mapper;

import com.cn.taihe.back.shoppingcart.entity.Shopingcart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车商品表 Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface ShopingcartMapper {

  /**
   * 根据主键查找数据
   *
   * @param id 购物车项ID
   * @return 购物车实体
   */
  Shopingcart selectById(@Param("id") String id);

  /**
   * 新增数据
   *
   * @param shopingcart 购物车实体
   * @return 影响行数
   */
  int insert(Shopingcart shopingcart);

  /**
   * 修改数据（只更新非空字段）
   *
   * @param shopingcart 购物车实体
   * @return 影响行数
   */
  int updateByIdSelective(Shopingcart shopingcart);

  /**
   * 根据主键删除数据
   *
   * @param id 购物车项ID
   * @return 影响行数
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件分页查询数据
   *
   * @param shopingcart 查询条件
   * @return 购物车列表
   */
  List<Shopingcart> selectByPage(Shopingcart shopingcart);

  /**
   * 根据主键集合批量删除数据
   *
   * @param ids 主键集合
   * @return 影响行数
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 更新选中状态（冻结功能）
   *
   * @param id 购物车项ID
   * @param selected 选中状态: 0-否, 1-是
   * @return 影响行数
   */
  int updateSelectedStatus(@Param("id") String id, @Param("selected") Integer selected);

  /**
   * 根据用户ID查询购物车列表
   *
   * @param userId 用户ID
   * @return 购物车列表
   */
  List<Shopingcart> selectByUserId(@Param("userId") String userId);

}
