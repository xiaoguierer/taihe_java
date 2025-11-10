package com.cn.taihe.back.filestore.service.impl;

import com.cn.taihe.back.filestore.dto.ProductImageCreateDTO;
import com.cn.taihe.back.filestore.dto.ProductImageQueryDTO;
import com.cn.taihe.back.filestore.dto.ProductImageUpdateDTO;
import com.cn.taihe.back.filestore.entity.ProductImage;
import com.cn.taihe.back.filestore.mapper.ProductImageMapper;
import com.cn.taihe.back.filestore.service.ProductImageService;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import com.cn.taihe.common.AppCommonConstants;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 商品图片表 Service实现类
 *
 * @author system
 * @date 2025-11-07
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

  private static final Logger logger = LoggerFactory.getLogger(ProductImageServiceImpl.class);

  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductImageMapper productImageMapper;
  @Autowired
  private FileStorageService fileStorageService;

  /**
   * 根据主键查询商品图片
   */
  @Override
  public ProductImage getProductImageById(String id) {
    logger.info("根据主键查询商品图片开始 - 操作人: {}, 参数: id={}", OPERATOR, id);

    try {
      ProductImage result = productImageMapper.selectById(id);
      logger.info("根据主键查询商品图片成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("根据主键查询商品图片异常 - 操作人: {}, 参数: id={}, 异常信息: ", OPERATOR, id, e);
      throw e;
    }
  }

  /**
   * 新增商品图片
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean createProductImage(ProductImageCreateDTO createDTO) {
    logger.info("新增商品图片开始 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
    try {
      ProductImage productImage = new ProductImage();
      BeanUtils.copyProperties(createDTO, productImage);


      // 生成主键ID（如果前端未传入）
      if (createDTO.getId() == null || createDTO.getId().trim().isEmpty()) {
        productImage.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      } else {
        productImage.setId(createDTO.getId());
      }

      // 设置审计信息（实际项目中应从上下文中获取）
      if (createDTO.getCreatedBy() == null) {
        productImage.setCreatedBy(1L); // 默认操作人ID
      }
      if (createDTO.getUpdatedBy() == null) {
        productImage.setUpdatedBy(1L); // 默认操作人ID
      }
      productImage.setCreatedTime(LocalDateTime.now());
      productImage.setUpdatedTime(LocalDateTime.now());

      int result = productImageMapper.insertProductImage(productImage);
      boolean success = result > 0;
      logger.info("新增商品图片完成 - 操作人: {}, 结果: {}", OPERATOR, success);
      return success;
    } catch (Exception e) {
      logger.error("新增商品图片异常 - 操作人: {}, 参数: {}, 异常信息: ", OPERATOR, createDTO, e);
      throw e;
    }
  }

  /**
   * @description:
   * @author: 大咖
   * @date: 2025/11/9 17:26
   * @param: [model, multipartFile]
   * @return: [java.lang.String, org.springframework.web.multipart.MultipartFile]
   * 对外服务  传入路径  文件   返回文件存储id 和绝对路径
   **/

  public Map<String, String>  createProductImageWhithOpen(String  model,MultipartFile multipartFile){
    logger.info("新增商品图片开始 - 操作人: {}", OPERATOR);
    Map<String, String> map = new HashMap<>();
    try {
      if(multipartFile != null && !multipartFile.isEmpty()){
        ProductImage productImage = new ProductImage();
        //  file 文件; avatarPath  文件路径
        String realPath = fileStorageService.upload(multipartFile, model);//相对路径  写入服务器
        productImage.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
        productImage.setAbsoluteUrl("/api/files" + realPath);
        productImage.setRelativePath(realPath);
        productImage.setFileName(multipartFile.getOriginalFilename());
        productImage.setFileSize((int) multipartFile.getSize());
        productImage.setMimeType(multipartFile.getContentType());
        productImage.setIsActive(Boolean.TRUE);
        productImage.setIsPrimary(Boolean.TRUE);
        productImage.setCreatedBy(1L); // 默认操作人ID
        productImage.setUpdatedBy(1L); // 默认操作人ID
        productImage.setCreatedTime(LocalDateTime.now());
        productImage.setUpdatedTime(LocalDateTime.now());
        int result = productImageMapper.insertProductImage(productImage);//写入数据库
        if(result > 0){
          map.put("key",productImage.getId());
          map.put("jdpath",productImage.getAbsoluteUrl());
          logger.info("新增文件成功");
          return map;
        }
      }else {
        logger.info("参数为空,新增文件失败");
        return  map;
      }
    }catch (Exception e){
      throw e;
    }
    return map;
  }

  /**
   * 更新商品图片
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateProductImage(ProductImageUpdateDTO updateDTO) {
    logger.info("更新商品图片开始 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);

    try {
      // 先查询现有数据
      ProductImage existing = productImageMapper.selectById(updateDTO.getId());
      if (existing == null) {
        logger.warn("更新商品图片失败 - 记录不存在 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
        return false;
      }

      ProductImage productImage = new ProductImage();
      BeanUtils.copyProperties(updateDTO, productImage);

      // 设置审计信息（实际项目中应从上下文中获取）
      if (updateDTO.getUpdatedBy() == null) {
        productImage.setUpdatedBy(1L); // 默认操作人ID
      }
      productImage.setUpdatedTime(LocalDateTime.now());

      int result = productImageMapper.updateProductImage(productImage);
      boolean success = result > 0;
      logger.info("更新商品图片完成 - 操作人: {}, 结果: {}", OPERATOR, success);
      return success;
    } catch (Exception e) {
      logger.error("更新商品图片异常 - 操作人: {}, 参数: {}, 异常信息: ", OPERATOR, updateDTO, e);
      throw e;
    }
  }

  /**
   * 根据主键删除商品图片
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean deleteProductImageById(String id) {
    logger.info("删除商品图片开始 - 操作人: {}, 参数: id={}", OPERATOR, id);

    try {
      int result = productImageMapper.deleteById(id);
      boolean success = result > 0;
      logger.info("删除商品图片完成 - 操作人: {}, 结果: {}", OPERATOR, success);
      return success;
    } catch (Exception e) {
      logger.error("删除商品图片异常 - 操作人: {}, 参数: id={}, 异常信息: ", OPERATOR, id, e);
      throw e;
    }
  }

  /**
   * 条件分页查询商品图片列表
   */
  @Override
  public PageInfo<ProductImage> getProductImagePage(ProductImageQueryDTO queryDTO, Integer page, Integer size) {
    logger.info("条件分页查询商品图片列表开始 - 操作人: {}, 参数: queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);

    try {
      // 设置分页参数
      if (page == null || page < 1) {
        page = 1;
      }
      if (size == null || size < 1) {
        size = 10;
      }
      PageHelper.startPage(page, size);

      List<ProductImage> list = productImageMapper.selectProductImageList(queryDTO);
      PageInfo<ProductImage> pageInfo = new PageInfo<>(list);

      logger.info("条件分页查询商品图片列表成功 - 操作人: {}, 总记录数: {}", OPERATOR, pageInfo.getTotal());
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询商品图片列表异常 - 操作人: {}, 参数: queryDTO={}, page={}, size={}, 异常信息: ",
        OPERATOR, queryDTO, page, size, e);
      throw e;
    }
  }

  /**
   * 条件查询商品图片列表
   */
  @Override
  public List<ProductImage> getProductImageList(ProductImageQueryDTO queryDTO) {
    logger.info("条件查询商品图片列表开始 - 操作人: {}, 参数: {}", OPERATOR, queryDTO);

    try {
      List<ProductImage> result = productImageMapper.selectProductImageList(queryDTO);
      logger.info("条件查询商品图片列表成功 - 操作人: {}, 记录数: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("条件查询商品图片列表异常 - 操作人: {}, 参数: {}, 异常信息: ", OPERATOR, queryDTO, e);
      throw e;
    }
  }

  /**
   * 查询所有商品图片
   */
  @Override
  public List<ProductImage> getAllProductImage() {
    logger.info("查询所有商品图片开始 - 操作人: {}", OPERATOR);

    try {
      List<ProductImage> result = productImageMapper.selectAllProductImage();
      logger.info("查询所有商品图片成功 - 操作人: {}, 记录数: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询所有商品图片异常 - 操作人: {}, 异常信息: ", OPERATOR, e);
      throw e;
    }
  }

  /**
   * 根据主键集合批量删除商品图片
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean deleteProductImageBatch(List<String> ids) {
    logger.info("批量删除商品图片开始 - 操作人: {}, 参数: ids={}", OPERATOR, ids);

    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量删除商品图片失败 - 参数为空 - 操作人: {}", OPERATOR);
        return false;
      }

      int result = productImageMapper.deleteBatchIds(ids);
      boolean success = result > 0;
      logger.info("批量删除商品图片完成 - 操作人: {}, 删除记录数: {}, 结果: {}", OPERATOR, result, success);
      return success;
    } catch (Exception e) {
      logger.error("批量删除商品图片异常 - 操作人: {}, 参数: ids={}, 异常信息: ", OPERATOR, ids, e);
      throw e;
    }
  }

  /**
   * 更新商品图片状态（冻结/启用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateProductImageStatus(String id, Boolean isActive) {
    logger.info("更新商品图片状态开始 - 操作人: {}, 参数: id={}, isActive={}", OPERATOR, id, isActive);

    try {
      Long updatedBy = 1L; // 默认操作人ID（实际项目中应从上下文中获取）
      int result = productImageMapper.updateStatus(id, isActive, updatedBy);
      boolean success = result > 0;
      logger.info("更新商品图片状态完成 - 操作人: {}, 结果: {}", OPERATOR, success);
      return success;
    } catch (Exception e) {
      logger.error("更新商品图片状态异常 - 操作人: {}, 参数: id={}, isActive={}, 异常信息: ", OPERATOR, id, isActive, e);
      throw e;
    }
  }

  /**
   * 批量更新商品图片状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateProductImageStatusBatch(List<String> ids, Boolean isActive) {
    logger.info("批量更新商品图片状态开始 - 操作人: {}, 参数: ids={}, isActive={}", OPERATOR, ids, isActive);

    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量更新商品图片状态失败 - 参数为空 - 操作人: {}", OPERATOR);
        return false;
      }

      Long updatedBy = 1L; // 默认操作人ID（实际项目中应从上下文中获取）
      int result = productImageMapper.updateBatchStatus(ids, isActive, updatedBy);
      boolean success = result > 0;
      logger.info("批量更新商品图片状态完成 - 操作人: {}, 更新记录数: {}, 结果: {}", OPERATOR, result, success);
      return success;
    } catch (Exception e) {
      logger.error("批量更新商品图片状态异常 - 操作人: {}, 参数: ids={}, isActive={}, 异常信息: ", OPERATOR, ids, isActive, e);
      throw e;
    }
  }
}
