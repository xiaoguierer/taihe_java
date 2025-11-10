package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.dto.WuXingAttributeCreateDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeQueryDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeUpdateDTO;
import com.cn.taihe.back.product.entity.WuXingAttribute;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 五行属性服务接口
 *
 * @author system
 * @since 2025-02-20
 */
public interface WuXingAttributeService {

  /**
   * 根据主键查询五行属性
   *
   * @param id 主键ID
   * @return 五行属性实体
   */
  WuXingAttribute getById(String id);

  /**
   * 新增五行属性
   *
   * @param createDTO 新增参数
   * @return 是否成功
   */
  boolean create(WuXingAttributeCreateDTO createDTO);

  /**
   * 更新五行属性
   *
   * @param updateDTO 更新参数
   * @return 是否成功
   */
  boolean update(WuXingAttributeUpdateDTO updateDTO);

  /**
   * 根据主键删除五行属性
   *
   * @param id 主键ID
   * @return 是否成功
   */
  boolean deleteById(String id);

  /**
   * 条件分页查询五行属性列表
   *
   * @param queryDTO 查询条件
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<WuXingAttribute> getPageList(WuXingAttributeQueryDTO queryDTO, Integer page, Integer size);

  /**
   * 条件查询五行属性列表（不分页）
   *
   * @param queryDTO 查询条件
   * @return 五行属性列表
   */
  List<WuXingAttribute> getList(WuXingAttributeQueryDTO queryDTO);

  /**
   * 查询所有五行属性
   *
   * @return 五行属性列表
   */
  List<WuXingAttribute> getAll();

  /**
   * 查询所有启用的五行属性（关键有效信息）
   *
   * @return 启用的五行属性列表
   */
  List<WuXingAttribute> getAllActive();

  /**
   * 根据条件查询关键有效信息列表（简化字段）
   *
   * @param queryDTO 查询条件
   * @return 五行属性简化列表
   */
  List<WuXingAttribute> getKeyInfoList(WuXingAttributeQueryDTO queryDTO);

  /**
   * 根据元素分类查询启用的五行属性
   *
   * @param elementCategory 元素分类
   * @return 五行属性列表
   */
  List<WuXingAttribute> getByCategory(String elementCategory);

  /**
   * 根据元素层级查询启用的五行属性
   *
   * @param elementTier 元素层级
   * @return 五行属性列表
   */
  List<WuXingAttribute> getByTier(Integer elementTier);

  /**
   * 根据主键集合批量删除五行属性
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  boolean deleteBatch(List<String> ids);

  /**
   * 冻结五行属性
   *
   * @param id 主键ID
   * @return 是否成功
   */
  boolean freeze(String id);

  /**
   * 启用五行属性
   *
   * @param id 主键ID
   * @return 是否成功
   */
  boolean enable(String id);

  /**
   * 批量冻结五行属性
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  boolean freezeBatch(List<String> ids);

  /**
   * 批量启用五行属性
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  boolean enableBatch(List<String> ids);

  /**
   * 根据元素键名查询五行属性
   *
   * @param elementKey 元素键名
   * @return 五行属性实体
   */
  WuXingAttribute getByElementKey(String elementKey);

  /**
   * 检查元素键名是否存在
   *
   * @param elementKey 元素键名
   * @param excludeId 排除的主键ID
   * @return 是否存在
   */
  boolean isElementKeyExists(String elementKey, String excludeId);

  /**
   * 查询五行属性的关键字段（用于缓存或快速查询）
   *
   * @return 关键字段列表
   */
  List<WuXingAttribute> getKeyFields();
}
