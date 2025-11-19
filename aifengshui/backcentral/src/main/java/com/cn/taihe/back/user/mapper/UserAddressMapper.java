package com.cn.taihe.back.user.mapper;

import com.cn.taihe.back.user.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户收货地址表Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface UserAddressMapper {

  /**
   * 根据主键查找数据
   *
   * @param id 地址ID
   * @return 用户地址信息
   */
  UserAddress selectById(@Param("id") String id);

  /**
   * 新增数据
   *
   * @param userAddress 用户地址信息
   * @return 影响行数
   */
  int insert(UserAddress userAddress);

  /**
   * 修改数据（更新非空字段）
   *
   * @param userAddress 用户地址信息
   * @return 影响行数
   */
  int updateByIdSelective(UserAddress userAddress);

  /**
   * 根据主键逻辑删除数据
   *
   * @param id 地址ID
   * @return 影响行数
   */
  int logicDeleteById(@Param("id") String id);

  /**
   * 根据用户ID查询地址列表
   *
   * @param userId 用户ID
   * @return 地址列表
   */
  List<UserAddress> selectByUserId(@Param("userId") String userId);

  /**
   * 根据用户ID查询有效地址列表
   *
   * @param userId 用户ID
   * @return 地址列表
   */
  List<UserAddress> selectActiveByUserId(@Param("userId") String userId);

  /**
   * 查询所有数据
   *
   * @return 所有地址列表
   */
  List<UserAddress> selectAll();

  /**
   * 更新地址状态（冻结/解冻）
   *
   * @param id 地址ID
   * @param isActive 是否有效
   * @return 影响行数
   */
  int updateStatus(@Param("id") String id, @Param("isActive") Integer isActive);

  /**
   * 设置用户的所有地址为非默认
   *
   * @param userId 用户ID
   * @return 影响行数
   */
  int updateAllNotDefault(@Param("userId") String userId);
}
