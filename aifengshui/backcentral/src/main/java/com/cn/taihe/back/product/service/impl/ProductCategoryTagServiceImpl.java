package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.filestore.service.ProductImageService;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import com.cn.taihe.back.product.dto.ProductCategoryTagCreateDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagQueryDTO;
import com.cn.taihe.back.product.dto.ProductCategoryTagUpdateDTO;
import com.cn.taihe.back.product.entity.ProductCategoryTag;
import com.cn.taihe.back.product.mapper.ProductCategoryTagMapper;
import com.cn.taihe.back.product.service.ProductCategoryTagService;
import com.cn.taihe.back.product.service.ProductSpuCategoryTagService;
import com.cn.taihe.back.product.vo.ProductCategoryTagCountDTO;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 商品品类标签表 Service实现类
 *
 * @author auto generate
 * @since 2025-11-07
 */
@Service
public class ProductCategoryTagServiceImpl implements ProductCategoryTagService {

  private static final Logger logger = LoggerFactory.getLogger(ProductCategoryTagServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductCategoryTagMapper productCategoryTagMapper;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private FileStorageService fileStorageService;
  @Autowired
  private ProductSpuCategoryTagService productSpuCategoryTagService;

  /**
   * 根据主键查询商品品类标签
   */
  @Override
  public ProductCategoryTag getById(String id) {
    logger.info("根据主键查询商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (id == null || id.trim().isEmpty()) {
      logger.warn("根据主键查询商品品类标签失败，ID不能为空");
      return null;
    }

    try {
      ProductCategoryTag result = productCategoryTagMapper.selectById(id);
      logger.info("根据主键查询商品品类标签成功，操作人：{}，结果：{}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("根据主键查询商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("查询商品品类标签失败", e);
    }
  }

  /**
   * 新增商品品类标签
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(ProductCategoryTagCreateDTO createDTO, MultipartFile iconfile, MultipartFile
    coverimagefile, MultipartFile hoverimagefile) {
    logger.info("新增商品品类标签开始，操作人：{}，参数：{}", OPERATOR, createDTO);

    if (createDTO == null) {
      logger.warn("新增商品品类标签失败，参数不能为空");
      return false;
    }
    try {
      // 转换DTO
      ProductCategoryTag productCategoryTag = new ProductCategoryTag();
      BeanUtils.copyProperties(createDTO, productCategoryTag);
      // 设置系统字段
      productCategoryTag.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      productCategoryTag.setCreatedBy(1L); // 实际应从上下文中获取
      productCategoryTag.setUpdatedBy(1L); // 实际应从上下文中获取
      productCategoryTag.setCreatedTime(LocalDateTime.now());
      productCategoryTag.setUpdatedTime(LocalDateTime.now());

      //文件图像处理
      if (iconfile != null && !iconfile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, iconfile);
        productCategoryTag.setIconId((String) map.get("key"));
        productCategoryTag.setIconPath((String) map.get("jdpath"));
      }
      if (coverimagefile != null && !coverimagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, coverimagefile);
        productCategoryTag.setCoverId((String) map.get("key"));
        productCategoryTag.setCoverImageUrl((String) map.get("jdpath"));
      }
      if (hoverimagefile != null && !hoverimagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, hoverimagefile);
        productCategoryTag.setHoverId((String) map.get("key"));
        productCategoryTag.setHoverImageUrl((String) map.get("jdpath"));
      }

      int result = productCategoryTagMapper.insert(productCategoryTag);
      boolean success = result > 0;

      logger.info("新增商品品类标签{}，操作人：{}，ID：{}", success ? "成功" : "失败", OPERATOR, productCategoryTag.getId());
      return success;
    } catch (Exception e) {
      logger.error("新增商品品类标签异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      throw new RuntimeException("新增商品品类标签失败", e);
    }
  }

  /**
   * 更新商品品类标签
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(ProductCategoryTagUpdateDTO updateDTO, MultipartFile iconfile,MultipartFile
    coverimagefile,MultipartFile hoverimagefile) {
    logger.info("更新商品品类标签开始，操作人：{}，参数：{}", OPERATOR, updateDTO);
    if (updateDTO == null || updateDTO.getId() == null) {
      logger.warn("更新商品品类标签失败，参数或ID不能为空");
      return false;
    }
    try {
      // 先查询是否存在
      ProductCategoryTag existing = productCategoryTagMapper.selectById(updateDTO.getId());
      if (existing == null) {
        logger.warn("更新商品品类标签失败，记录不存在，ID：{}", updateDTO.getId());
        return false;
      }
      // 转换DTO
      ProductCategoryTag productCategoryTag = new ProductCategoryTag();
      BeanUtils.copyProperties(updateDTO, productCategoryTag);
      // 设置系统字段
      productCategoryTag.setUpdatedBy(1L); // 实际应从上下文中获取
      productCategoryTag.setUpdatedTime(LocalDateTime.now());
      //若图片有数据  则 1先删除原图片数据  2再删除图片标数据，3然后新增图片数据和图片表数据，4最后更新业务表数据
      //1 删除图片数据
      if (productCategoryTag.getIconPath() != null && !productCategoryTag.getIconPath().isEmpty()) {
        fileStorageService.delete(productCategoryTag.getIconPath().replace("/api/files", ""));
      }
      if (productCategoryTag.getCoverImageUrl() != null && !productCategoryTag.getCoverImageUrl().isEmpty() ) {
        fileStorageService.delete(productCategoryTag.getCoverImageUrl().replace("/api/files", ""));
      }
      if (productCategoryTag.getHoverImageUrl() != null && !productCategoryTag.getHoverImageUrl().isEmpty()) {
        fileStorageService.delete(productCategoryTag.getHoverImageUrl().replace("/api/files", ""));
      }
      //2 删除图片表数据，根据主键批量删除
      if (productCategoryTag.getIconId() != null && !productCategoryTag.getIconId().isEmpty()) {
        productImageService.deleteProductImageById(productCategoryTag.getIconId());
      }
      if (productCategoryTag.getCoverId() != null && !productCategoryTag.getCoverId().isEmpty()) {
        productImageService.deleteProductImageById(productCategoryTag.getCoverId());
      }
      if (productCategoryTag.getHoverImageUrl() != null && !productCategoryTag.getHoverImageUrl().isEmpty()) {
        productImageService.deleteProductImageById(productCategoryTag.getHoverImageUrl());
      }
      //新增图片和图片表数据
      if (iconfile != null && !iconfile.isEmpty()){
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, iconfile);
        productCategoryTag.setIconId((String) map.get("key"));
        productCategoryTag.setIconPath((String) map.get("jdpath"));
      }
      if (coverimagefile != null && !coverimagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, coverimagefile);
        productCategoryTag.setCoverId((String) map.get("key"));
        productCategoryTag.setCoverImageUrl((String) map.get("jdpath"));
      }
      if (hoverimagefile != null && !hoverimagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, hoverimagefile);
        productCategoryTag.setHoverId((String) map.get("key"));
        productCategoryTag.setHoverImageUrl((String) map.get("jdpath"));
      }

      int result = productCategoryTagMapper.updateById(productCategoryTag);
      boolean success = result > 0;

      logger.info("更新商品品类标签{}，操作人：{}，ID：{}", success ? "成功" : "失败", OPERATOR, updateDTO.getId());
      return success;
    } catch (Exception e) {
      logger.error("更新商品品类标签异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      throw new RuntimeException("更新商品品类标签失败", e);
    }
  }
  /**
   * @description:
   * @author: 创建产品呢spu和分类标签之间的关系
   * @date: 2025/11/15 15:19
   * @param: [spuId, list]
   * @return: [java.lang.String, java.util.List]
   **/
  public int createRealiations(String spuId, List list){
    return productSpuCategoryTagService.createRealiations(spuId,list);
  }
  /**
   * 根据主键删除商品品类标签
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    logger.info("删除商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);
    if (id == null || id.trim().isEmpty()) {
      logger.warn("删除商品品类标签失败，ID不能为空");
      return false;
    }
    try {
      ProductCategoryTag productCategoryTag = productCategoryTagMapper.selectById(id);
      //1 删除图片数据
      if (productCategoryTag.getIconPath() != null && !productCategoryTag.getIconPath().isEmpty()) {
        fileStorageService.delete(productCategoryTag.getIconPath().replace("/api/files", ""));
      }
      if (productCategoryTag.getCoverImageUrl() != null) {
        fileStorageService.delete(productCategoryTag.getCoverImageUrl().replace("/api/files", ""));
      }
      if (productCategoryTag.getHoverImageUrl() != null) {
        fileStorageService.delete(productCategoryTag.getHoverImageUrl().replace("/api/files", ""));
      }
      //2 删除图片表数据，根据主键批量删除
      if (productCategoryTag.getIconId() != null) {
        productImageService.deleteProductImageById(productCategoryTag.getIconId());
      }
      if (productCategoryTag.getCoverId() != null) {
        productImageService.deleteProductImageById(productCategoryTag.getCoverId());
      }
      if (productCategoryTag.getHoverImageUrl() != null) {
        productImageService.deleteProductImageById(productCategoryTag.getHoverImageUrl());
      }
      int result = productCategoryTagMapper.deleteById(id);
      boolean success = result > 0;
      logger.info("删除商品品类标签{}，操作人：{}，ID：{}", success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("删除商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("删除商品品类标签失败", e);
    }
  }

  /**
   * 条件分页查询商品品类标签列表
   */
  @Override
  public PageInfo<ProductCategoryTag> getPageList(ProductCategoryTagQueryDTO queryDTO, int page, int size) {
    logger.info("分页查询商品品类标签开始，操作人：{}，参数：queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);

    // 设置分页参数
    if (page < 1) page = 1;
    if (size < 1) size = 10;

    try {
      PageHelper.startPage(page, size);
      List<ProductCategoryTag> list = productCategoryTagMapper.selectList(queryDTO);
      PageInfo<ProductCategoryTag> pageInfo = new PageInfo<>(list);

      logger.info("分页查询商品品类标签成功，操作人：{}，总记录数：{}，当前页：{}", OPERATOR, pageInfo.getTotal(), pageInfo.getPageNum());
      return pageInfo;
    } catch (Exception e) {
      logger.error("分页查询商品品类标签异常，操作人：{}，参数：queryDTO={}, page={}, size={}，异常信息：", OPERATOR, queryDTO, page, size, e);
      throw new RuntimeException("分页查询商品品类标签失败", e);
    }
  }

  /**
   * 条件查询商品品类标签列表（不分页）
   */
  @Override
  public List<ProductCategoryTag> getList(ProductCategoryTagQueryDTO queryDTO) {
    logger.info("查询商品品类标签列表开始，操作人：{}，参数：{}", OPERATOR, queryDTO);

    try {
      List<ProductCategoryTag> result = productCategoryTagMapper.selectList(queryDTO);
      logger.info("查询商品品类标签列表成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询商品品类标签列表异常，操作人：{}，参数：{}，异常信息：", OPERATOR, queryDTO, e);
      throw new RuntimeException("查询商品品类标签列表失败", e);
    }
  }

  /**
   * 查询所有商品品类标签
   */
  @Override
  public List<ProductCategoryTag> getAll() {
    logger.info("查询所有商品品类标签开始，操作人：{}", OPERATOR);

    try {
      List<ProductCategoryTag> result = productCategoryTagMapper.selectAll();
      logger.info("查询所有商品品类标签成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询所有商品品类标签异常，操作人：{}，异常信息：", OPERATOR, e);
      throw new RuntimeException("查询所有商品品类标签失败", e);
    }
  }

  /**
   * 批量删除商品品类标签
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteBatch(List<String> ids) {
    logger.info("批量删除商品品类标签开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量删除商品品类标签失败，ID列表不能为空");
      return false;
    }

    try {
      int result = productCategoryTagMapper.deleteBatchIds(ids);
      boolean success = result > 0;

      logger.info("批量删除商品品类标签{}，操作人：{}，删除记录数：{}，总记录数：{}",
        success ? "成功" : "失败", OPERATOR, result, ids.size());
      return success;
    } catch (Exception e) {
      logger.error("批量删除商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      throw new RuntimeException("批量删除商品品类标签失败", e);
    }
  }

  /**
   * 冻结商品品类标签（禁用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean freeze(String id) {
    logger.info("冻结商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (id == null || id.trim().isEmpty()) {
      logger.warn("冻结商品品类标签失败，ID不能为空");
      return false;
    }

    try {
      int result = productCategoryTagMapper.updateStatusById(id, false);
      boolean success = result > 0;

      logger.info("冻结商品品类标签{}，操作人：{}，ID：{}", success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("冻结商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("冻结商品品类标签失败", e);
    }
  }

  /**
   * 解冻商品品类标签（启用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean unfreeze(String id) {
    logger.info("解冻商品品类标签开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (id == null || id.trim().isEmpty()) {
      logger.warn("解冻商品品类标签失败，ID不能为空");
      return false;
    }

    try {
      int result = productCategoryTagMapper.updateStatusById(id, true);
      boolean success = result > 0;

      logger.info("解冻商品品类标签{}，操作人：{}，ID：{}", success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("解冻商品品类标签异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("解冻商品品类标签失败", e);
    }
  }

  /**
   * 批量冻结商品品类标签（禁用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean freezeBatch(List<String> ids) {
    logger.info("批量冻结商品品类标签开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量冻结商品品类标签失败，ID列表不能为空");
      return false;
    }

    try {
      int result = productCategoryTagMapper.updateBatchStatus(ids, false);
      boolean success = result > 0;

      logger.info("批量冻结商品品类标签{}，操作人：{}，冻结记录数：{}，总记录数：{}",
        success ? "成功" : "失败", OPERATOR, result, ids.size());
      return success;
    } catch (Exception e) {
      logger.error("批量冻结商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      throw new RuntimeException("批量冻结商品品类标签失败", e);
    }
  }

  /**
   * 批量解冻商品品类标签（启用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean unfreezeBatch(List<String> ids) {
    logger.info("批量解冻商品品类标签开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量解冻商品品类标签失败，ID列表不能为空");
      return false;
    }

    try {
      int result = productCategoryTagMapper.updateBatchStatus(ids, true);
      boolean success = result > 0;

      logger.info("批量解冻商品品类标签{}，操作人：{}，解冻记录数：{}，总记录数：{}",
        success ? "成功" : "失败", OPERATOR, result, ids.size());
      return success;
    } catch (Exception e) {
      logger.error("批量解冻商品品类标签异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      throw new RuntimeException("批量解冻商品品类标签失败", e);
    }
  }

  /**
   * 根据标签类型查询商品品类标签列表
   */
  @Override
  public List<ProductCategoryTag> getByTagType(Integer tagType) {
    logger.info("根据标签类型查询商品品类标签开始，操作人：{}，参数：tagType={}", OPERATOR, tagType);

    if (tagType == null) {
      logger.warn("根据标签类型查询商品品类标签失败，标签类型不能为空");
      return null;
    }

    try {
      ProductCategoryTagQueryDTO queryDTO = new ProductCategoryTagQueryDTO();
      // 这里需要扩展QueryDTO或使用自定义查询，暂时返回空列表
      logger.info("根据标签类型查询商品品类标签成功，操作人：{}，标签类型：{}", OPERATOR, tagType);
      return java.util.Collections.emptyList();
    } catch (Exception e) {
      logger.error("根据标签类型查询商品品类标签异常，操作人：{}，参数：tagType={}，异常信息：", OPERATOR, tagType, e);
      throw new RuntimeException("根据标签类型查询商品品类标签失败", e);
    }
  }

  /**
   * 根据父标签ID查询子标签列表
   */
  @Override
  public List<ProductCategoryTag> getByParentTagId(String parentTagId) {
    logger.info("根据父标签ID查询子标签列表开始，操作人：{}，参数：parentTagId={}", OPERATOR, parentTagId);

    try {
      ProductCategoryTagQueryDTO queryDTO = new ProductCategoryTagQueryDTO();
      queryDTO.setParentTagId(parentTagId);
      List<ProductCategoryTag> result = productCategoryTagMapper.selectList(queryDTO);

      logger.info("根据父标签ID查询子标签列表成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据父标签ID查询子标签列表异常，操作人：{}，参数：parentTagId={}，异常信息：", OPERATOR, parentTagId, e);
      throw new RuntimeException("根据父标签ID查询子标签列表失败", e);
    }
  }
  //  夸表综合查询
  /**
   * 根据情感意图查找所属商品的标签以及数量
   *
   * @param intentId 意图ID（前端传输）
   * @return 标签统计列表，包含标签信息和对应的商品数量
   */
  public List<ProductCategoryTagCountDTO> selectJewelryTagByIntentId(String intentId){
    logger.info("根据情感意图查找所属商品的标签以及数量开始，操作人：{}，参数：parentTagId={}", OPERATOR, intentId);
    try {
      List<ProductCategoryTagCountDTO> result = productCategoryTagMapper.selectJewelryTagByIntentId(intentId);

      logger.info("根据父标签ID查询子标签列表成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据父标签ID查询子标签列表异常，操作人：{}，参数：parentTagId={}，异常信息：", OPERATOR, intentId, e);
      throw new RuntimeException("根据父标签ID查询子标签列表失败", e);
    }
  }

  /**
   * 根据情感意愿ID查询能量信息（tag_type = 8）
   *
   * @param intentId 情感意愿ID
   * @return 能量信息列表
   */
  public List<ProductCategoryTagCountDTO> selectEnergyInfoByIntentId(String intentId){
    logger.info("根据情感意图查找所属商品的标签以及数量开始，操作人：{}，参数：parentTagId={}", OPERATOR, intentId);
    try {
      List<ProductCategoryTagCountDTO> result = productCategoryTagMapper.selectEnergyInfoByIntentId(intentId);

      logger.info("根据父标签ID查询子标签列表成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据父标签ID查询子标签列表异常，操作人：{}，参数：parentTagId={}，异常信息：", OPERATOR, intentId, e);
      throw new RuntimeException("根据父标签ID查询子标签列表失败", e);
    }
  }
}
