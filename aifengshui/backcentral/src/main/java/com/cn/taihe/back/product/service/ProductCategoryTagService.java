package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.dto.ProductCategoryTagCreateDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagQueryDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagUpdateDTO;
import com.cn.taihe.back.product.entity.ProductCategoryTag;
import com.cn.taihe.back.product.vo.ProductCategoryTagCountDTO;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品品类标签表 Service接口
 *
 * @author auto generate
 * @since 2025-11-07
 */
public interface ProductCategoryTagService {

  /**
   * 根据主键查询商品品类标签
   *
   * @param id 主键ID
   * @return 商品品类标签信息
   */
  ProductCategoryTag getById(String id);

  /**
   * 新增商品品类标签
   *
   * @param createDTO 新增参数
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean create(ProductCategoryTagCreateDTO createDTO, MultipartFile iconfile,MultipartFile
    coverimagefile,MultipartFile hoverimagefile);

  /**
   * 更新商品品类标签
   *
   * @param updateDTO 更新参数
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean update(ProductCategoryTagUpdateDTO updateDTO, MultipartFile iconfile,MultipartFile
    coverimagefile,MultipartFile hoverimagefile);

  /**
   * @description:
   * @author: 创建产品呢spu和分类标签之间的关系
   * @date: 2025/11/15 15:19
   * @param: [spuId, list]
   * @return: [java.lang.String, java.util.List]
   **/
  int createRealiations(String spuId, List list);
  /**
   * 根据主键删除商品品类标签
   *
   * @param id 主键ID
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean deleteById(String id);

  /**
   * 条件分页查询商品品类标签列表
   *
   * @param queryDTO 查询条件
   * @param page 页码，默认1
   * @param size 每页数量，默认10
   * @return 分页结果
   */
  PageInfo<ProductCategoryTag> getPageList(ProductCategoryTagQueryDTO queryDTO, int page, int size);

  /**
   * 条件查询商品品类标签列表（不分页）
   *
   * @param queryDTO 查询条件
   * @return 商品品类标签列表
   */
  List<ProductCategoryTag> getList(ProductCategoryTagQueryDTO queryDTO);

  /**
   * 查询所有商品品类标签
   *
   * @return 所有商品品类标签列表
   */
  List<ProductCategoryTag> getAll();

  /**
   * 批量删除商品品类标签
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean deleteBatch(List<String> ids);

  /**
   * 冻结商品品类标签（禁用）
   *
   * @param id 主键ID
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean freeze(String id);

  /**
   * 解冻商品品类标签（启用）
   *
   * @param id 主键ID
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean unfreeze(String id);

  /**
   * 批量冻结商品品类标签（禁用）
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean freezeBatch(List<String> ids);

  /**
   * 批量解冻商品品类标签（启用）
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  boolean unfreezeBatch(List<String> ids);

  /**
   * 根据标签类型查询商品品类标签列表
   *
   * @param tagType 标签类型
   * @return 商品品类标签列表
   */
  List<ProductCategoryTag> getByTagType(Integer tagType);

  /**
   * 根据父标签ID查询子标签列表
   *
   * @param parentTagId 父标签ID
   * @return 子标签列表
   */
  List<ProductCategoryTag> getByParentTagId(String parentTagId);

  //  夸表综合查询
  /**
   * 根据情感意图查找所属商品的标签以及数量
   *
   * @param intentId 意图ID（前端传输）
   * @return 标签统计列表，包含标签信息和对应的商品数量
   */
  List<ProductCategoryTagCountDTO> selectJewelryTagByIntentId(String intentId);

  /**
   * 根据情感意愿ID查询能量信息（tag_type = 8）
   *
   * @param intentId 情感意愿ID
   * @return 能量信息列表
   */
  List<ProductCategoryTagCountDTO> selectEnergyInfoByIntentId(String intentId);
}
