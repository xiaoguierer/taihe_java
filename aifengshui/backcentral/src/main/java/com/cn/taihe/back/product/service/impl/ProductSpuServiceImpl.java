package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.filestore.service.ProductImageService;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import com.cn.taihe.back.product.dto.ProductSpuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSpuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSpuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSpu;
import com.cn.taihe.back.product.mapper.ProductSpuMapper;
import com.cn.taihe.back.product.service.ProductSpuService;
import com.cn.taihe.back.product.vo.ProductSpuSkuDTO;
import com.cn.taihe.back.product.vo.ProductspuByEmotionAndTagId;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 商品SPU Service实现类
 *
 * @author system
 * @since 2025-01-01
 */
@Service
public class ProductSpuServiceImpl implements ProductSpuService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuServiceImpl.class);

  private static final String DEFAULT_OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuMapper productSpuMapper;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private FileStorageService fileStorageService;

  /**
   * 根据主键查找数据
   */
  @Override
  public ProductSpu getById(String id) {
    logger.info("根据主键查找商品SPU数据，请求参数: id={}, operator={}", id, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(id)) {
      logger.warn("主键ID不能为空");
      return null;
    }

    try {
      ProductSpu result = productSpuMapper.selectById(id);
      logger.info("根据主键查找商品SPU数据成功，返回结果: {}", result);
      return result;
    } catch (Exception e) {
      logger.error("根据主键查找商品SPU数据异常，id: {}, operator: {}", id, DEFAULT_OPERATOR, e);
      throw new RuntimeException("查询商品SPU数据失败", e);
    }
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(ProductSpuCreateDTO createDTO, MultipartFile mainImagefile, MultipartFile conceptImageFile, MultipartFile designImageFile,
                        MultipartFile prototypeImageFile, MultipartFile usageImageFile, MultipartFile technicalImageFile) {
    logger.info("新增商品SPU数据，请求参数: {}, operator={}", createDTO, DEFAULT_OPERATOR);
    if (createDTO == null) {
      logger.warn("新增参数不能为空");
      return false;
    }
    // 检查SPU编码是否已存在
    if (productSpuMapper.countBySpuCode(createDTO.getSpuCode(), null) > 0) {
      logger.warn("SPU编码已存在: {}", createDTO.getSpuCode());
      throw new RuntimeException("SPU编码已存在");
    }
    try {
      ProductSpu productSpu = new ProductSpu();
      BeanUtils.copyProperties(createDTO, productSpu);
      // 设置系统生成字段
      productSpu.setId(String.valueOf(SnowflakeIdGenerator.nextId()));

      if(mainImagefile != null && !mainImagefile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,mainImagefile);
        productSpu.setMainImageId((String) map.get("key"));
        productSpu.setMainImageUrl((String) map.get("jdpath"));
      }
      if(conceptImageFile != null && !conceptImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,conceptImageFile);
        productSpu.setConceptImageId((String) map.get("key"));
        productSpu.setConceptImageUrl((String) map.get("jdpath"));
      }
      if(designImageFile != null && !designImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,designImageFile);
        productSpu.setDesignImageId((String) map.get("key"));
        productSpu.setDesignImageUrl((String) map.get("jdpath"));
      }
      if(prototypeImageFile != null && !prototypeImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,prototypeImageFile);
        productSpu.setPrototypeImageId((String) map.get("key"));
        productSpu.setPrototypeImageUrl((String) map.get("jdpath"));
      }
      if(usageImageFile != null && !usageImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,usageImageFile);
        productSpu.setUsageImageId((String) map.get("key"));
        productSpu.setUsageImageUrl((String) map.get("jdpath"));
      }
      if(technicalImageFile != null && !technicalImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,technicalImageFile);
        productSpu.setTechnicalImageId((String) map.get("key"));
        productSpu.setTechnicalImageUrl((String) map.get("jdpath"));
      }

      int result = productSpuMapper.insert(productSpu);
      boolean success = result > 0;
      logger.info("新增商品SPU数据{}，id: {}, operator: {}",
        success ? "成功" : "失败", productSpu.getId(), DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("新增商品SPU数据异常，参数: {}, operator: {}", createDTO, DEFAULT_OPERATOR, e);
      throw new RuntimeException("新增商品SPU数据失败", e);
    }
  }

  /**
   * 修改数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(ProductSpuUpdateDTO updateDTO, MultipartFile mainImagefile,MultipartFile conceptImageFile,MultipartFile designImageFile,
                        MultipartFile prototypeImageFile,MultipartFile usageImageFile,MultipartFile technicalImageFile) {
    logger.info("修改商品SPU数据，请求参数: {}, operator={}", updateDTO, DEFAULT_OPERATOR);
    if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
      logger.warn("更新参数或主键ID不能为空");
      return false;
    }
    // 检查数据是否存在
    ProductSpu existing = productSpuMapper.selectById(updateDTO.getId());
    if (existing == null) {
      logger.warn("商品SPU数据不存在，id: {}", updateDTO.getId());
      throw new RuntimeException("商品SPU数据不存在");
    }
    // 检查SPU编码是否已存在（排除当前记录）
    if (StringUtils.hasText(updateDTO.getSpuCode()) &&
      !updateDTO.getSpuCode().equals(existing.getSpuCode())) {
      if (productSpuMapper.countBySpuCode(updateDTO.getSpuCode(), updateDTO.getId()) > 0) {
        logger.warn("SPU编码已存在: {}", updateDTO.getSpuCode());
        throw new RuntimeException("SPU编码已存在");
      }
    }
    try {
      ProductSpu productSpu = new ProductSpu();
      BeanUtils.copyProperties(updateDTO, productSpu);

      //若图片有数据  则 1先删除原图片数据  2再删除图片标数据，3然后新增图片数据和图片表数据，4最后更新业务表数据
      //1 删除图片数据
      if(productSpu.getMainImageUrl()!= null && !productSpu.getMainImageUrl().isEmpty()){
        fileStorageService.delete(productSpu.getMainImageUrl().replace("/api/files", ""));
      }
      if(productSpu.getConceptImageUrl()!= null && !productSpu.getConceptImageUrl().isEmpty()){
        fileStorageService.delete(productSpu.getConceptImageUrl().replace("/api/files", ""));
      }
      if(productSpu.getDesignImageUrl()!= null && !productSpu.getDesignImageUrl().isEmpty()){
        fileStorageService.delete(productSpu.getDesignImageUrl().replace("/api/files", ""));
      }
      if(productSpu.getPrototypeImageUrl()!= null && !productSpu.getPrototypeImageUrl().isEmpty()){
        fileStorageService.delete(productSpu.getPrototypeImageUrl().replace("/api/files", ""));
      }
      if(productSpu.getUsageImageUrl()!= null && !productSpu.getUsageImageUrl().isEmpty()){
        fileStorageService.delete(productSpu.getUsageImageUrl().replace("/api/files", ""));
      }
      if(productSpu.getTechnicalImageUrl()!= null && !productSpu.getTechnicalImageUrl().isEmpty()){
        fileStorageService.delete(productSpu.getTechnicalImageUrl().replace("/api/files", ""));
      }
      //2 删除图片表数据，根据主键批量删除
      if(productSpu.getMainImageId()!= null && !productSpu.getMainImageId().isEmpty()){
        productImageService.deleteProductImageById(productSpu.getMainImageId());
      }
      if(productSpu.getConceptImageId()!= null && !productSpu.getConceptImageId().isEmpty()){
        productImageService.deleteProductImageById(productSpu.getConceptImageId());
      }
      if(productSpu.getDesignImageId()!= null && !productSpu.getDesignImageId().isEmpty()){
        productImageService.deleteProductImageById(productSpu.getDesignImageId());
      }
      if(productSpu.getPrototypeImageId()!= null && !productSpu.getPrototypeImageId().isEmpty()){
        productImageService.deleteProductImageById(productSpu.getPrototypeImageId());
      }
      if(productSpu.getUsageImageId()!= null && !productSpu.getUsageImageId().isEmpty()){
        productImageService.deleteProductImageById(productSpu.getUsageImageId());
      }
      if(productSpu.getTechnicalImageId()!= null && !productSpu.getTechnicalImageId().isEmpty()){
        productImageService.deleteProductImageById(productSpu.getTechnicalImageId());
      }
      //新增图片和图片表数据
      if(mainImagefile != null && !mainImagefile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,mainImagefile);
        productSpu.setMainImageId((String) map.get("key"));
        productSpu.setMainImageUrl((String) map.get("jdpath"));
      }
      if(conceptImageFile != null && !conceptImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,conceptImageFile);
        productSpu.setConceptImageId((String) map.get("key"));
        productSpu.setConceptImageUrl((String) map.get("jdpath"));
      }
      if(designImageFile != null && !designImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,designImageFile);
        productSpu.setDesignImageId((String) map.get("key"));
        productSpu.setDesignImageUrl((String) map.get("jdpath"));
      }
      if(prototypeImageFile != null && !prototypeImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,prototypeImageFile);
        productSpu.setPrototypeImageId((String) map.get("key"));
        productSpu.setPrototypeImageUrl((String) map.get("jdpath"));
      }
      if(usageImageFile != null && !usageImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,usageImageFile);
        productSpu.setUsageImageId((String) map.get("key"));
        productSpu.setUsageImageUrl((String) map.get("jdpath"));
      }
      if(technicalImageFile != null && !technicalImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_DETAIL_File_PATH,technicalImageFile);
        productSpu.setTechnicalImageId((String) map.get("key"));
        productSpu.setTechnicalImageUrl((String) map.get("jdpath"));
      }

      int result = productSpuMapper.updateByIdSelective(productSpu);
      boolean success = result > 0;
      logger.info("修改商品SPU数据{}，id: {}, operator: {}",
        success ? "成功" : "失败", updateDTO.getId(), DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("修改商品SPU数据异常，参数: {}, operator: {}", updateDTO, DEFAULT_OPERATOR, e);
      throw new RuntimeException("修改商品SPU数据失败", e);
    }
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    logger.info("删除商品SPU数据，请求参数: id={}, operator={}", id, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(id)) {
      logger.warn("主键ID不能为空");
      return false;
    }

    // 检查数据是否存在
    ProductSpu existing = productSpuMapper.selectById(id);
    if (existing == null) {
      logger.warn("商品SPU数据不存在，id: {}", id);
      throw new RuntimeException("商品SPU数据不存在");
    }

    try {
      int result = productSpuMapper.deleteById(id);
      boolean success = result > 0;
      logger.info("删除商品SPU数据{}，id: {}, operator: {}",
        success ? "成功" : "失败", id, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("删除商品SPU数据异常，id: {}, operator: {}", id, DEFAULT_OPERATOR, e);
      throw new RuntimeException("删除商品SPU数据失败", e);
    }
  }

  /**
   * 条件分页查询数据
   */
  @Override
  public PageInfo<ProductSpu> getByCondition(ProductSpuQueryDTO queryDTO, Integer page, Integer size) {
    logger.info("条件分页查询商品SPU数据，请求参数: queryDTO={}, page={}, size={}, operator={}",
      queryDTO, page, size, DEFAULT_OPERATOR);

    // 设置分页参数默认值
    int pageNum = (page == null || page < 1) ? 1 : page;
    int pageSize = (size == null || size < 1) ? 10 : size;

    try {
      // 开启分页
      PageHelper.startPage(pageNum, pageSize);
      List<ProductSpu> list = productSpuMapper.selectByCondition(queryDTO);
      PageInfo<ProductSpu> pageInfo = new PageInfo<>(list);

      logger.info("条件分页查询商品SPU数据成功，总记录数: {}, 当前页: {}, 页大小: {}, operator: {}",
        pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), DEFAULT_OPERATOR);
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询商品SPU数据异常，参数: {}, operator: {}", queryDTO, DEFAULT_OPERATOR, e);
      throw new RuntimeException("查询商品SPU数据失败", e);
    }
  }

  /**
   * 查询所有数据
   */
  @Override
  public List<ProductSpu> getAll() {
    logger.info("查询所有商品SPU数据，operator={}", DEFAULT_OPERATOR);

    try {
      List<ProductSpu> result = productSpuMapper.selectAll();
      logger.info("查询所有商品SPU数据成功，记录数: {}, operator: {}", result.size(), DEFAULT_OPERATOR);
      return result;
    } catch (Exception e) {
      logger.error("查询所有商品SPU数据异常，operator: {}", DEFAULT_OPERATOR, e);
      throw new RuntimeException("查询所有商品SPU数据失败", e);
    }
  }

  /**
   * 根据主键集合批量删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteBatchIds(List<String> ids) {
    logger.info("批量删除商品SPU数据，请求参数: ids={}, operator={}", ids, DEFAULT_OPERATOR);

    if (ids == null || ids.isEmpty()) {
      logger.warn("主键ID集合不能为空");
      return false;
    }

    try {
      int result = productSpuMapper.deleteBatchIds(ids);
      boolean success = result > 0;
      logger.info("批量删除商品SPU数据{}，删除记录数: {}, operator: {}",
        success ? "成功" : "失败", result, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("批量删除商品SPU数据异常，ids: {}, operator: {}", ids, DEFAULT_OPERATOR, e);
      throw new RuntimeException("批量删除商品SPU数据失败", e);
    }
  }

  /**
   * 根据主键更新状态（冻结/启用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatusById(String id, Boolean isActive) {
    logger.info("更新商品SPU状态，请求参数: id={}, isActive={}, operator={}", id, isActive, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(id) || isActive == null) {
      logger.warn("主键ID和状态不能为空");
      return false;
    }

    // 检查数据是否存在
    ProductSpu existing = productSpuMapper.selectById(id);
    if (existing == null) {
      logger.warn("商品SPU数据不存在，id: {}", id);
      throw new RuntimeException("商品SPU数据不存在");
    }

    try {
      int result = productSpuMapper.updateStatusById(id, isActive, Long.parseLong(id));
      boolean success = result > 0;
      logger.info("更新商品SPU状态{}，id: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", id, isActive, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("更新商品SPU状态异常，id: {}, isActive: {}, operator: {}",
        id, isActive, DEFAULT_OPERATOR, e);
      throw new RuntimeException("更新商品SPU状态失败", e);
    }
  }

  /**
   * 根据主键批量更新状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatusBatchIds(List<String> ids, Boolean isActive) {
    logger.info("批量更新商品SPU状态，请求参数: ids={}, isActive={}, operator={}", ids, isActive, DEFAULT_OPERATOR);

    if (ids == null || ids.isEmpty() || isActive == null) {
      logger.warn("主键ID集合和状态不能为空");
      return false;
    }

    try {
      int result = productSpuMapper.updateStatusBatchIds(ids, isActive, Long.parseLong(ids.get(0)));
      boolean success = result > 0;
      logger.info("批量更新商品SPU状态{}，更新记录数: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", result, isActive, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("批量更新商品SPU状态异常，ids: {}, isActive: {}, operator: {}",
        ids, isActive, DEFAULT_OPERATOR, e);
      throw new RuntimeException("批量更新商品SPU状态失败", e);
    }
  }

  /**
   * 根据SPU编码查询数据
   */
  @Override
  public ProductSpu getBySpuCode(String spuCode) {
    logger.info("根据SPU编码查询商品SPU数据，请求参数: spuCode={}, operator={}", spuCode, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(spuCode)) {
      logger.warn("SPU编码不能为空");
      return null;
    }

    try {
      ProductSpu result = productSpuMapper.selectBySpuCode(spuCode);
      logger.info("根据SPU编码查询商品SPU数据成功，返回结果: {}", result);
      return result;
    } catch (Exception e) {
      logger.error("根据SPU编码查询商品SPU数据异常，spuCode: {}, operator: {}", spuCode, DEFAULT_OPERATOR, e);
      throw new RuntimeException("根据SPU编码查询商品SPU数据失败", e);
    }
  }

  /**
   * 检查SPU编码是否存在
   */
  @Override
  public boolean isSpuCodeExists(String spuCode, String excludeId) {
    logger.info("检查SPU编码是否存在，请求参数: spuCode={}, excludeId={}, operator={}",
      spuCode, excludeId, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(spuCode)) {
      return false;
    }

    try {
      int count = productSpuMapper.countBySpuCode(spuCode, excludeId);
      boolean exists = count > 0;
      logger.info("检查SPU编码是否存在结果: {}, spuCode: {}, operator: {}", exists, spuCode, DEFAULT_OPERATOR);
      return exists;
    } catch (Exception e) {
      logger.error("检查SPU编码是否存在异常，spuCode: {}, operator: {}", spuCode, DEFAULT_OPERATOR, e);
      throw new RuntimeException("检查SPU编码是否存在失败", e);
    }
  }

  /**
   * 夸表综合查询
   * 根据情感意图ID查询推荐商品列表（包含主品类信息）
   *
   * @param intentId 情感意图ID
   * @param limit 查询条数
   * @return 推荐商品列表
   */
  public List<ProductSpuSkuDTO> getRecommendProducts(String intentId, Integer limit) {
    return productSpuMapper.selectRecommendProductsByIntentId(intentId, limit);
  }

  /**
   * 根据情感意图ID查询关联的SPU列表
   *
   * @param intentId 情感意图ID
   * @return SPU列表
   */
  public List<ProductSpu> selectSpuByIntentId(String intentId){
    return productSpuMapper.selectSpuByIntentId(intentId);
  }

  /**
   * 根据分类标签和意图查询商品SPU
   */
  @Override
  public List<ProductSpu> getSpuByCategoryTagAndIntent(String categoryTagId, String intentId) {
    logger.info("根据分类标签和意图查询商品SPU，categoryTagId: {}, intentId: {}",
      categoryTagId, intentId);
    try {
      List<ProductSpu> result = productSpuMapper.selectByCategoryTagAndIntent(categoryTagId, intentId);
      logger.info("根据分类标签和意图查询商品SPU成功，记录数: {}", result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据分类标签和意图查询商品SPU异常，categoryTagId: {}, intentId: {}",
        categoryTagId, intentId, e);
      throw new RuntimeException("查询商品SPU失败", e);
    }
  }
  /**
   * 根据五行元素标签和意图查询商品SPU
   */
  @Override
  public List<ProductSpu> getSpuByElementTagAndIntent(String elementTagId, String intentId) {
    logger.info("根据五行元素标签和意图查询商品SPU，elementTagId: {}, intentId: {}",
      elementTagId, intentId);

    try {
      List<ProductSpu> result = productSpuMapper.selectByElementTagAndIntent(elementTagId, intentId);
      logger.info("根据五行元素标签和意图查询商品SPU成功，记录数: {}", result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据五行元素标签和意图查询商品SPU异常，elementTagId: {}, intentId: {}",
        elementTagId, intentId, e);
      throw new RuntimeException("查询商品SPU失败", e);
    }
  }
}
