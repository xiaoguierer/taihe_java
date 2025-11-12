package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.dto.ProductSkuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSkuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSkuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSku;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品SKU Service接口
 *
 * @author ADMIN
 */
public interface ProductSkuService {

  /**
   * 根据主键查找
   */
  ProductSku findById(String id);

  /**
   * 新增数据
   */
  boolean create(ProductSkuCreateDTO createDTO, MultipartFile mainImagefile,MultipartFile image1file,MultipartFile image2file,
                 MultipartFile image3file,MultipartFile image4file,MultipartFile image5file);

  /**
   * 修改数据
   */
  boolean update(ProductSkuUpdateDTO updateDTO,MultipartFile mainImagefile,MultipartFile image1file,MultipartFile image2file,
                 MultipartFile image3file,MultipartFile image4file,MultipartFile image5file);

  /**
   * 根据主键删除数据
   */
  boolean deleteById(String id);

  /**
   * 条件分页查询数据
   */
  PageInfo<ProductSku> findByCondition(ProductSkuQueryDTO queryDTO, int page, int size);

  /**
   * 查询所有数据
   */
  List<ProductSku> findAll();

  /**
   * 根据主键集合批量删除
   */
  boolean deleteByIds(List<String> ids);

  /**
   * 更新状态
   */
  boolean updateStatus(String id, Integer status, Long updatedBy);

  /**
   * 更新是否可售状态（冻结/解冻）
   */
  boolean updateIsAvailable(String id, Boolean isAvailable, Long updatedBy);

  /**
   * 批量更新状态
   */
  boolean batchUpdateStatus(List<String> ids, Integer status, Long updatedBy);

  /**
   * 批量更新是否可售状态
   */
  boolean batchUpdateIsAvailable(List<String> ids, Boolean isAvailable, Long updatedBy);

  /**
   * 根据SPU ID查询
   */
  List<ProductSku> findBySpuId(String spuId);

  /**
   * 根据SKU编码查询
   */
  ProductSku findBySkuCode(String skuCode);

  /**
   * 检查SKU编码是否存在
   */
  boolean isSkuCodeExists(String skuCode, String excludeId);
}
