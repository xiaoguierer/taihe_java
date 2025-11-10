package com.cn.taihe.back.filestore.service;

import com.cn.taihe.back.filestore.entity.ProductImage;
import com.cn.taihe.back.filestore.dto.ProductImageCreateDTO;
import com.cn.taihe.back.filestore.dto.ProductImageQueryDTO;
import com.cn.taihe.back.filestore.dto.ProductImageUpdateDTO;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 商品图片表 Service接口
 *
 * @author system
 * @date 2025-11-07
 */
public interface ProductImageService {

  /**
   * 根据主键查询商品图片
   *
   * @param id 主键ID
   * @return 商品图片信息
   */
  ProductImage getProductImageById(String id);

  /**
   * 新增商品图片
   *
   * @param createDTO 商品图片信息
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean createProductImage(ProductImageCreateDTO createDTO);

  /**
   * @description:
   * @author: 大咖
   * @date: 2025/11/9 16:47
   * @param: [model, key, multipartFile]
   * @return: [java.lang.String, java.lang.String, org.springframework.web.multipart.MultipartFile]
   * model 要写入的路径；key业务外键 作为附件表的主键， multipartFile 文件  ；
   **/

  Map<String, String>  createProductImageWhithOpen(String  model, MultipartFile multipartFile);

  /**
   * 更新商品图片
   *
   * @param updateDTO 商品图片信息
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateProductImage(ProductImageUpdateDTO updateDTO);

  /**
   * 根据主键删除商品图片
   *
   * @param id 主键ID
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean deleteProductImageById(String id);

  /**
   * 条件分页查询商品图片列表
   *
   * @param queryDTO 查询条件
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<ProductImage> getProductImagePage(ProductImageQueryDTO queryDTO, Integer page, Integer size);

  /**
   * 条件查询商品图片列表
   *
   * @param queryDTO 查询条件
   * @return 商品图片列表
   */
  List<ProductImage> getProductImageList(ProductImageQueryDTO queryDTO);

  /**
   * 查询所有商品图片
   *
   * @return 商品图片列表
   */
  List<ProductImage> getAllProductImage();

  /**
   * 根据主键集合批量删除商品图片
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean deleteProductImageBatch(List<String> ids);

  /**
   * 更新商品图片状态（冻结/启用）
   *
   * @param id 主键ID
   * @param isActive 是否启用
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateProductImageStatus(String id, Boolean isActive);

  /**
   * 批量更新商品图片状态
   *
   * @param ids 主键ID集合
   * @param isActive 是否启用
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateProductImageStatusBatch(List<String> ids, Boolean isActive);

}
