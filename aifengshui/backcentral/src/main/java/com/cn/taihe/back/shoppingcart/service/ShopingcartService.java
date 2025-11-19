package com.cn.taihe.back.shoppingcart.service;

import com.cn.taihe.back.shoppingcart.entity.Shopingcart;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车商品表 Service接口
 *
 * @author system
 */
@Service
public interface ShopingcartService {

  /**
   * 根据主键查找数据
   *
   * @param id 购物车项ID
   * @return 购物车实体
   */
  Shopingcart getById(String id);

  /**
   * 新增数据
   *
   * @param shopingcart 购物车实体
   * @return 是否成功
   */
  Boolean save(Shopingcart shopingcart);

  /**
   * 修改数据（只更新非空字段）
   *
   * @param shopingcart 购物车实体
   * @return 是否成功
   */
  Boolean updateById(Shopingcart shopingcart);

  /**
   * 根据主键删除数据
   *
   * @param id 购物车项ID
   * @return 是否成功
   */
  Boolean removeById(String id);

  /**
   * 条件分页查询数据
   *
   * @param shopingcart 查询条件
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<Shopingcart> getByPage(Shopingcart shopingcart, Integer page, Integer size);

  /**
   * 根据主键集合批量删除数据
   *
   * @param ids 主键集合
   * @return 是否成功
   */
  Boolean removeBatchByIds(List<String> ids);

  /**
   * 更新选中状态（冻结功能）
   *
   * @param id 购物车项ID
   * @param selected 选中状态: 0-否, 1-是
   * @return 是否成功
   */
  Boolean updateSelectedStatus(String id, Integer selected);

  /**
   * 根据用户ID查询购物车列表
   *
   * @param userId 用户ID
   * @return 购物车列表
   */
  List<Shopingcart> getByUserId(String userId);

}
