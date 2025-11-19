package com.cn.taihe.back.user.service;

import com.cn.taihe.back.user.dto.UserAddressCreateDTO;
import com.cn.taihe.back.user.dto.UserAddressUpdateDTO;
import com.cn.taihe.back.user.entity.UserAddress;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 用户收货地址表Service接口
 *
 * @author system
 */
@ApiModel(description = "用户收货地址服务接口")
public interface UserAddressService {

  /**
   * 根据主键查找数据
   *
   * @param id 地址ID
   * @return 用户地址信息
   */
  UserAddress getById(String id);

  /**
   * 新增用户地址
   *
   * @param createDTO 地址创建信息
   * @return 创建成功的地址信息
   */
  UserAddress create(UserAddressCreateDTO createDTO);

  /**
   * 修改用户地址
   *
   * @param updateDTO 地址更新信息
   * @return 更新后的地址信息
   */
  UserAddress update(UserAddressUpdateDTO updateDTO);

  /**
   * 根据主键逻辑删除地址
   *
   * @param id 地址ID
   * @return 是否删除成功
   */
  boolean deleteById(String id);

  /**
   * 根据用户ID查询地址列表
   *
   * @param userId 用户ID
   * @return 地址列表
   */
  List<UserAddress> getByUserId(String userId);

  /**
   * 查询所有地址列表（分页）
   *
   * @param page 页码
   * @param size 每页大小
   * @return 分页地址列表
   */
  PageInfo<UserAddress> getAllWithPage(Integer page, Integer size);

  /**
   * 冻结地址（设置为无效）
   *
   * @param id 地址ID
   * @return 是否操作成功
   */
  boolean freeze(String id);

  /**
   * 解冻地址（设置为有效）
   *
   * @param id 地址ID
   * @return 是否操作成功
   */
  boolean unfreeze(String id);

  /**
   * 设置默认地址
   *
   * @param id 地址ID
   * @param userId 用户ID
   * @return 是否设置成功
   */
  boolean setDefault(String id, String userId);

  /**
   * 获取用户的默认地址
   *
   * @param userId 用户ID
   * @return 默认地址信息
   */
  UserAddress getDefaultAddress(String userId);
}
