package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.filestore.service.ProductImageService;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import com.cn.taihe.back.product.dto.WuXingAttributeCreateDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeQueryDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeUpdateDTO;
import com.cn.taihe.back.product.entity.WuXingAttribute;
import com.cn.taihe.back.product.mapper.WuXingAttributeMapper;
import com.cn.taihe.back.product.service.ProductSpuIntentService;
import com.cn.taihe.back.product.service.ProductSpuWuxingService;
import com.cn.taihe.back.product.service.WuXingAttributeService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 五行属性服务实现类
 *
 * @author system
 * @since 2025-02-20
 */
@Service
public class WuXingAttributeServiceImpl implements WuXingAttributeService {

  private static final Logger logger = LoggerFactory.getLogger(WuXingAttributeServiceImpl.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private WuXingAttributeMapper wuXingAttributeMapper;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private FileStorageService fileStorageService;
  @Autowired
  private ProductSpuWuxingService productSpuWuxingService;

  @Override
  public WuXingAttribute getById(String id) {
    logger.info("查询五行属性详情 - 操作人: {}, 参数: id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("查询五行属性详情 - 参数错误: id为空");
      return null;
    }

    try {
      WuXingAttribute result = wuXingAttributeMapper.selectById(id);
      logger.info("查询五行属性详情成功 - 操作人: {}, 结果: {}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("查询五行属性详情异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("查询五行属性详情失败", e);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(WuXingAttributeCreateDTO createDTO, MultipartFile symbolIconfile, MultipartFile philosophyImagefile, MultipartFile energyFlowImagefile) {
    logger.info("新增五行属性 - 操作人: {}, 参数: {}", OPERATOR, createDTO);

    if (createDTO == null) {
      logger.warn("新增五行属性 - 参数错误: createDTO为空");
      return false;
    }
    try {
      WuXingAttribute entity = new WuXingAttribute();
      BeanUtils.copyProperties(createDTO, entity);
      entity.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      // 设置创建时间和更新时间
      entity.setCreatedTime(LocalDateTime.now());
      entity.setUpdatedTime(LocalDateTime.now());
      //文件图像处理
      if (symbolIconfile != null && !symbolIconfile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, symbolIconfile);
        entity.setSymbolIconId((String) map.get("key"));
        entity.setSymbolIconUrl((String) map.get("jdpath"));
      }
      if (philosophyImagefile != null && !philosophyImagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, philosophyImagefile);
        entity.setPhilosophyImageId((String) map.get("key"));
        entity.setPhilosophyImageUrl((String) map.get("jdpath"));
      }
      if (energyFlowImagefile != null && !energyFlowImagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, energyFlowImagefile);
        entity.setEnergyFlowImageId((String) map.get("key"));
        entity.setEnergyFlowImageUrl((String) map.get("jdpath"));
      }

      int result = wuXingAttributeMapper.insert(entity);
      boolean success = result > 0;
      logger.info("新增五行属性{} - 操作人: {}, 参数: {}", success ? "成功" : "失败", OPERATOR, createDTO);
      return success;
    } catch (Exception e) {
      logger.error("新增五行属性异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, createDTO, e.getMessage(), e);
      throw new RuntimeException("新增五行属性失败", e);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(WuXingAttributeUpdateDTO updateDTO, MultipartFile symbolIconfile, MultipartFile philosophyImagefile, MultipartFile energyFlowImagefile) {
    logger.info("更新五行属性 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);

    if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
      logger.warn("更新五行属性 - 参数错误: updateDTO或id为空");
      return false;
    }
    try {
      // 先查询现有记录
      WuXingAttribute existing = wuXingAttributeMapper.selectById(updateDTO.getId());
      if (existing == null) {
        logger.warn("更新五行属性 - 记录不存在: id={}", updateDTO.getId());
        return false;
      }
      WuXingAttribute entity = new WuXingAttribute();
      BeanUtils.copyProperties(updateDTO, entity);
      // 保留创建时间，更新修改时间
      entity.setCreatedTime(existing.getCreatedTime());
      entity.setUpdatedTime(LocalDateTime.now());
      //若图片有数据  则 1先删除原图片数据  2再删除图片标数据，3然后新增图片数据和图片表数据，4最后更新业务表数据
      //1 删除图片数据
      logger.info("entity.getSymbolIconUrl()   :  " + entity.getSymbolIconUrl());
      if (entity.getSymbolIconUrl() != null && !entity.getSymbolIconUrl().isEmpty()) {
        fileStorageService.delete(entity.getSymbolIconUrl().replace("/api/files", ""));
      }
      if (entity.getPhilosophyImageUrl() != null) {
        fileStorageService.delete(entity.getPhilosophyImageUrl().replace("/api/files", ""));
      }
      if (entity.getEnergyFlowImageUrl() != null) {
        fileStorageService.delete(entity.getEnergyFlowImageUrl().replace("/api/files", ""));
      }
      //2 删除图片表数据，根据主键批量删除
      if (entity.getSymbolIconId() != null) {
        productImageService.deleteProductImageById(entity.getSymbolIconId());
      }
      if (entity.getPhilosophyImageId() != null) {
        productImageService.deleteProductImageById(entity.getPhilosophyImageId());
      }
      if (entity.getEnergyFlowImageId() != null) {
        productImageService.deleteProductImageById(entity.getEnergyFlowImageId());
      }
      //新增图片和图片表数据
      if (symbolIconfile != null && !symbolIconfile.isEmpty()){
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, symbolIconfile);
        entity.setSymbolIconId((String) map.get("key"));
        entity.setSymbolIconUrl((String) map.get("jdpath"));
      }
      if (philosophyImagefile != null && !philosophyImagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, philosophyImagefile);
        entity.setPhilosophyImageId((String) map.get("key"));
        entity.setPhilosophyImageUrl((String) map.get("jdpath"));
      }
      if (energyFlowImagefile != null && !energyFlowImagefile.isEmpty()) {
        Map map = productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH, energyFlowImagefile);
        entity.setEnergyFlowImageId((String) map.get("key"));
        entity.setEnergyFlowImageUrl((String) map.get("jdpath"));
      }
      int result = wuXingAttributeMapper.updateById(entity);
      boolean success = result > 0;
      logger.info("更新五行属性{} - 操作人: {}, 参数: {}", success ? "成功" : "失败", OPERATOR, updateDTO);
      return success;
    } catch (Exception e) {
      logger.error("更新五行属性异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, updateDTO, e.getMessage(), e);
      throw new RuntimeException("更新五行属性失败", e);
    }
  }


  /**
   * @description:
   * @author: 新建和商品SPU的关联关系
   * @date: 2025/11/14 13:33
   * @param: [spuId, arrays]
   * @return: [java.lang.String, java.util.Arrays]
   **/
  public int createRealiations(String spuId, List list){
    return productSpuWuxingService.createRealiations(spuId,list);
  }


  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    logger.info("删除五行属性 - 操作人: {}, 参数: id={}", OPERATOR, id);
    if (!StringUtils.hasText(id)) {
      logger.warn("删除五行属性 - 参数错误: id为空");
      return false;
    }
    try {
      WuXingAttribute entity = wuXingAttributeMapper.selectById(id);
      //1 删除图片数据
      if (entity.getSymbolIconUrl() != null && !entity.getSymbolIconUrl().isEmpty()) {
        fileStorageService.delete(entity.getSymbolIconUrl().replace("/api/files", ""));
      }
      if (entity.getPhilosophyImageUrl() != null && !entity.getPhilosophyImageUrl().isEmpty()) {
        fileStorageService.delete(entity.getPhilosophyImageUrl().replace("/api/files", ""));
      }
      if (entity.getEnergyFlowImageUrl() != null && !entity.getPhilosophyImageUrl().isEmpty()) {
        fileStorageService.delete(entity.getEnergyFlowImageUrl().replace("/api/files", ""));
      }
      //2 删除图片表数据，根据主键批量删除
      if (entity.getSymbolIconId() != null && !entity.getSymbolIconId().isEmpty()) {
        productImageService.deleteProductImageById(entity.getSymbolIconId());
      }
      if (entity.getPhilosophyImageId() != null && !entity.getPhilosophyImageId().isEmpty()) {
        productImageService.deleteProductImageById(entity.getPhilosophyImageId());
      }
      if (entity.getEnergyFlowImageId() != null && !entity.getEnergyFlowImageId().isEmpty()) {
        productImageService.deleteProductImageById(entity.getEnergyFlowImageId());
      }
      int result = wuXingAttributeMapper.deleteById(id);
      boolean success = result > 0;
      logger.info("删除五行属性{} - 操作人: {}, 参数: id={}", success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("删除五行属性异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      throw new RuntimeException("删除五行属性失败", e);
    }
  }

  @Override
  public PageInfo<WuXingAttribute> getPageList(WuXingAttributeQueryDTO queryDTO, Integer page, Integer size) {
    logger.info("分页查询五行属性列表 - 操作人: {}, 参数: queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);

    // 设置默认分页参数
    int pageNum = (page == null || page < 1) ? 1 : page;
    int pageSize = (size == null || size < 1) ? 10 : size;

    try {
      PageHelper.startPage(pageNum, pageSize);
      List<WuXingAttribute> list = wuXingAttributeMapper.selectByCondition(queryDTO);
      PageInfo<WuXingAttribute> pageInfo = new PageInfo<>(list);
      logger.info("分页查询五行属性列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, pageInfo.getList().size());
      return pageInfo;
    } catch (Exception e) {
      logger.error("分页查询五行属性列表异常 - 操作人: {}, 参数: queryDTO={}, page={}, size={}, 异常信息: {}",
        OPERATOR, queryDTO, page, size, e.getMessage(), e);
      throw new RuntimeException("分页查询五行属性列表失败", e);
    }
  }

  @Override
  public List<WuXingAttribute> getList(WuXingAttributeQueryDTO queryDTO) {
    logger.info("查询五行属性列表 - 操作人: {}, 参数: {}", OPERATOR, queryDTO);

    try {
      List<WuXingAttribute> result = wuXingAttributeMapper.selectByCondition(queryDTO);
      logger.info("查询五行属性列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询五行属性列表异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, queryDTO, e.getMessage(), e);
      throw new RuntimeException("查询五行属性列表失败", e);
    }
  }

  @Override
  public List<WuXingAttribute> getAll() {
    logger.info("查询所有五行属性 - 操作人: {}", OPERATOR);

    try {
      List<WuXingAttribute> result = wuXingAttributeMapper.selectAll();
      logger.info("查询所有五行属性成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询所有五行属性异常 - 操作人: {}, 异常信息: {}", OPERATOR, e.getMessage(), e);
      throw new RuntimeException("查询所有五行属性失败", e);
    }
  }

  @Override
  public List<WuXingAttribute> getAllActive() {
    logger.info("查询所有启用的五行属性 - 操作人: {}", OPERATOR);

    try {
      List<WuXingAttribute> result = wuXingAttributeMapper.selectAllActive();
      logger.info("查询所有启用的五行属性成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询所有启用的五行属性异常 - 操作人: {}, 异常信息: {}", OPERATOR, e.getMessage(), e);
      throw new RuntimeException("查询所有启用的五行属性失败", e);
    }
  }

  @Override
  public List<WuXingAttribute> getKeyInfoList(WuXingAttributeQueryDTO queryDTO) {
    logger.info("查询关键信息列表 - 操作人: {}, 参数: {}", OPERATOR, queryDTO);

    try {
      List<WuXingAttribute> result = wuXingAttributeMapper.selectKeyInfoList(queryDTO);
      logger.info("查询关键信息列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询关键信息列表异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, queryDTO, e.getMessage(), e);
      throw new RuntimeException("查询关键信息列表失败", e);
    }
  }

  @Override
  public List<WuXingAttribute> getByCategory(String elementCategory) {
    logger.info("按分类查询五行属性 - 操作人: {}, 参数: elementCategory={}", OPERATOR, elementCategory);

    if (!StringUtils.hasText(elementCategory)) {
      logger.warn("按分类查询五行属性 - 参数错误: elementCategory为空");
      return null;
    }

    try {
      List<WuXingAttribute> result = wuXingAttributeMapper.selectByCategory(elementCategory);
      logger.info("按分类查询五行属性成功 - 操作人: {}, 参数: elementCategory={}, 结果数量: {}",
        OPERATOR, elementCategory, result.size());
      return result;
    } catch (Exception e) {
      logger.error("按分类查询五行属性异常 - 操作人: {}, 参数: elementCategory={}, 异常信息: {}",
        OPERATOR, elementCategory, e.getMessage(), e);
      throw new RuntimeException("按分类查询五行属性失败", e);
    }
  }

  @Override
  public List<WuXingAttribute> getByTier(Integer elementTier) {
    logger.info("按层级查询五行属性 - 操作人: {}, 参数: elementTier={}", OPERATOR, elementTier);

    if (elementTier == null) {
      logger.warn("按层级查询五行属性 - 参数错误: elementTier为空");
      return null;
    }

    try {
      List<WuXingAttribute> result = wuXingAttributeMapper.selectByTier(elementTier);
      logger.info("按层级查询五行属性成功 - 操作人: {}, 参数: elementTier={}, 结果数量: {}",
        OPERATOR, elementTier, result.size());
      return result;
    } catch (Exception e) {
      logger.error("按层级查询五行属性异常 - 操作人: {}, 参数: elementTier={}, 异常信息: {}",
        OPERATOR, elementTier, e.getMessage(), e);
      throw new RuntimeException("按层级查询五行属性失败", e);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteBatch(List<String> ids) {
    logger.info("批量删除五行属性 - 操作人: {}, 参数: ids={}", OPERATOR, ids);

    if (CollectionUtils.isEmpty(ids)) {
      logger.warn("批量删除五行属性 - 参数错误: ids为空");
      return false;
    }

    try {
      int result = wuXingAttributeMapper.deleteBatchIds(ids);
      boolean success = result > 0;
      logger.info("批量删除五行属性{} - 操作人: {}, 参数: ids={}, 删除数量: {}",
        success ? "成功" : "失败", OPERATOR, ids, result);
      return success;
    } catch (Exception e) {
      logger.error("批量删除五行属性异常 - 操作人: {}, 参数: ids={}, 异常信息: {}", OPERATOR, ids, e.getMessage(), e);
      throw new RuntimeException("批量删除五行属性失败", e);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean freeze(String id) {
    logger.info("冻结五行属性 - 操作人: {}, 参数: id={}", OPERATOR, id);
    return updateStatus(id, 0);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean enable(String id) {
    logger.info("启用五行属性 - 操作人: {}, 参数: id={}", OPERATOR, id);
    return updateStatus(id, 1);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean freezeBatch(List<String> ids) {
    logger.info("批量冻结五行属性 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
    return updateStatusBatch(ids, 0);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean enableBatch(List<String> ids) {
    logger.info("批量启用五行属性 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
    return updateStatusBatch(ids, 1);
  }

  @Override
  public WuXingAttribute getByElementKey(String elementKey) {
    logger.info("按元素键名查询五行属性 - 操作人: {}, 参数: elementKey={}", OPERATOR, elementKey);

    if (!StringUtils.hasText(elementKey)) {
      logger.warn("按元素键名查询五行属性 - 参数错误: elementKey为空");
      return null;
    }

    try {
      WuXingAttribute result = wuXingAttributeMapper.selectByElementKey(elementKey);
      logger.info("按元素键名查询五行属性成功 - 操作人: {}, 参数: elementKey={}, 结果: {}",
        OPERATOR, elementKey, result);
      return result;
    } catch (Exception e) {
      logger.error("按元素键名查询五行属性异常 - 操作人: {}, 参数: elementKey={}, 异常信息: {}",
        OPERATOR, elementKey, e.getMessage(), e);
      throw new RuntimeException("按元素键名查询五行属性失败", e);
    }
  }

  @Override
  public boolean isElementKeyExists(String elementKey, String excludeId) {
    logger.info("检查元素键名是否存在 - 操作人: {}, 参数: elementKey={}, excludeId={}", OPERATOR, elementKey, excludeId);

    if (!StringUtils.hasText(elementKey)) {
      return false;
    }

    try {
      int count = wuXingAttributeMapper.countByElementKey(elementKey, excludeId);
      boolean exists = count > 0;
      logger.info("检查元素键名是否存在 - 操作人: {}, 参数: elementKey={}, excludeId={}, 结果: {}",
        OPERATOR, elementKey, excludeId, exists);
      return exists;
    } catch (Exception e) {
      logger.error("检查元素键名是否存在异常 - 操作人: {}, 参数: elementKey={}, excludeId={}, 异常信息: {}",
        OPERATOR, elementKey, excludeId, e.getMessage(), e);
      throw new RuntimeException("检查元素键名是否存在失败", e);
    }
  }

  @Override
  public List<WuXingAttribute> getKeyFields() {
    logger.info("查询关键字段列表 - 操作人: {}", OPERATOR);

    try {
      List<WuXingAttribute> result = wuXingAttributeMapper.selectKeyFields();
      logger.info("查询关键字段列表成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询关键字段列表异常 - 操作人: {}, 异常信息: {}", OPERATOR, e.getMessage(), e);
      throw new RuntimeException("查询关键字段列表失败", e);
    }
  }

  /**
   * 更新状态私有方法
   */
  private boolean updateStatus(String id, Integer status) {
    if (!StringUtils.hasText(id)) {
      logger.warn("更新状态 - 参数错误: id为空");
      return false;
    }

    try {
      int result = wuXingAttributeMapper.updateStatusById(id, status);
      boolean success = result > 0;
      logger.info("更新状态{} - 操作人: {}, 参数: id={}, status={}",
        success ? "成功" : "失败", OPERATOR, id, status);
      return success;
    } catch (Exception e) {
      logger.error("更新状态异常 - 操作人: {}, 参数: id={}, status={}, 异常信息: {}",
        OPERATOR, id, status, e.getMessage(), e);
      throw new RuntimeException("更新状态失败", e);
    }
  }

  /**
   * 批量更新状态私有方法
   */
  private boolean updateStatusBatch(List<String> ids, Integer status) {
    if (CollectionUtils.isEmpty(ids)) {
      logger.warn("批量更新状态 - 参数错误: ids为空");
      return false;
    }

    try {
      int totalCount = 0;
      for (String id : ids) {
        int result = wuXingAttributeMapper.updateStatusById(id, status);
        totalCount += result;
      }
      boolean success = totalCount > 0;
      logger.info("批量更新状态{} - 操作人: {}, 参数: ids={}, status={}, 更新数量: {}",
        success ? "成功" : "失败", OPERATOR, ids, status, totalCount);
      return success;
    } catch (Exception e) {
      logger.error("批量更新状态异常 - 操作人: {}, 参数: ids={}, status={}, 异常信息: {}",
        OPERATOR, ids, status, e.getMessage(), e);
      throw new RuntimeException("批量更新状态失败", e);
    }
  }
}
