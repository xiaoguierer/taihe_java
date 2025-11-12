package com.cn.taihe.back.product.service;
import com.cn.taihe.back.product.dto.ProductSpuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSpuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSpuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSpu;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品SPU Service接口
 *
 * @author system
 * @since 2025-01-01
 */
public interface ProductSpuService {

  /**
   * 根据主键查找数据
   *
   * @param id 主键ID
   * @return 商品SPU信息
   */
  ProductSpu getById(String id);

  /**
   * 新增数据
   *
   * @param createDTO 新增参数
   * @return 是否成功
   */
  boolean create(ProductSpuCreateDTO createDTO, MultipartFile mainImagefile, MultipartFile conceptImageFile, MultipartFile designImageFile,
                 MultipartFile prototypeImageFile, MultipartFile usageImageFile, MultipartFile technicalImageFile);

  /**
   * 修改数据
   *
   * @param updateDTO 更新参数
   * @return 是否成功
   */
  boolean update(ProductSpuUpdateDTO updateDTO, MultipartFile mainImagefile,MultipartFile conceptImageFile,MultipartFile designImageFile,
                 MultipartFile prototypeImageFile,MultipartFile usageImageFile,MultipartFile technicalImageFile);

  /**
   * 根据主键删除数据
   *
   * @param id 主键ID
   * @return 是否成功
   */
  boolean deleteById(String id);

  /**
   * 条件分页查询数据
   *
   * @param queryDTO 查询条件
   * @param page 页码
   * @param size 每页大小
   * @return 分页结果
   */
  PageInfo<ProductSpu> getByCondition(ProductSpuQueryDTO queryDTO, Integer page, Integer size);

  /**
   * 查询所有数据
   *
   * @return 所有商品SPU列表
   */
  List<ProductSpu> getAll();

  /**
   * 根据主键集合批量删除数据
   *
   * @param ids 主键ID集合
   * @return 是否成功
   */
  boolean deleteBatchIds(List<String> ids);

  /**
   * 根据主键更新状态（冻结/启用）
   *
   * @param id 主键ID
   * @param isActive 是否启用
   * @return 是否成功
   */
  boolean updateStatusById(String id, Boolean isActive);

  /**
   * 根据主键批量更新状态
   *
   * @param ids 主键ID集合
   * @param isActive 是否启用
   * @return 是否成功
   */
  boolean updateStatusBatchIds(List<String> ids, Boolean isActive);

  /**
   * 根据SPU编码查询数据
   *
   * @param spuCode SPU编码
   * @return 商品SPU信息
   */
  ProductSpu getBySpuCode(String spuCode);

  /**
   * 检查SPU编码是否存在
   *
   * @param spuCode SPU编码
   * @param excludeId 排除的主键ID（用于更新时检查）
   * @return 是否存在
   */
  boolean isSpuCodeExists(String spuCode, String excludeId);
}
