package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.filestore.service.ProductImageService;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import com.cn.taihe.back.product.dto.EmotionalIntentCreateDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentQueryDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentUpdateDTO;
import com.cn.taihe.back.product.entity.EmotionalIntent;
import com.cn.taihe.back.product.mapper.EmotionalIntentMapper;
import com.cn.taihe.back.product.service.EmotionalIntentService;
import com.cn.taihe.back.product.service.ProductSpuIntentService;
import com.cn.taihe.common.AppCommonConstants;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
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
 * 情感意图服务实现类
 *
 * @author system
 * @date 2025-11-06
 */
@Service
@Api(tags = "情感意图管理服务实现类")
public class EmotionalIntentServiceImpl implements EmotionalIntentService {

  private static final Logger logger = LoggerFactory.getLogger(EmotionalIntentServiceImpl.class);

  private static final String OPERATOR = "ADMIN"; // 默认操作人

  @Autowired
  private EmotionalIntentMapper emotionalIntentMapper;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private FileStorageService fileStorageService;
  @Autowired
  private ProductSpuIntentService productSpuIntentService;

  /**
   * 根据ID查询情感意图详情
   */
  @Override
  public EmotionalIntent getById(String id) {
    logger.info("查询情感意图详情开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("查询情感意图详情失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }

    try {
      EmotionalIntent result = emotionalIntentMapper.selectById(id);
      logger.info("查询情感意图详情成功，操作人：{}，结果：{}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("查询情感意图详情异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("查询情感意图详情失败", e);
    }
  }

  /**
   * 分页查询情感意图列表
   */
  @Override
  public PageInfo<EmotionalIntent> getByPage(EmotionalIntentQueryDTO queryDTO, Integer page, Integer size) {
    // 设置默认分页参数
    int pageNum = (page == null || page < 1) ? 1 : page;
    int pageSize = (size == null || size < 1) ? 10 : size;

    logger.info("分页查询情感意图列表开始，操作人：{}，参数：page={}, size={}, queryDTO={}",
      OPERATOR, pageNum, pageSize, queryDTO);
    try {
      // 使用PageHelper进行分页
      PageHelper.startPage(pageNum, pageSize);
      List<EmotionalIntent> list = emotionalIntentMapper.selectByCondition(queryDTO);
      PageInfo<EmotionalIntent> pageInfo = new PageInfo<>(list);

      logger.info("分页查询情感意图列表成功，操作人：{}，总记录数：{}，当前页记录数：{}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getSize());
      return pageInfo;
    } catch (Exception e) {
      logger.error("分页查询情感意图列表异常，操作人：{}，参数：page={}, size={}, queryDTO={}，异常信息：",
        OPERATOR, pageNum, pageSize, queryDTO, e);
      throw new RuntimeException("分页查询情感意图列表失败", e);
    }
  }

  /**
   * 查询所有情感意图列表（不包含已删除的）
   */
  @Override
  public List<EmotionalIntent> getAll() {
    logger.info("查询所有情感意图列表开始，操作人：{}", OPERATOR);

    try {
      List<EmotionalIntent> result = emotionalIntentMapper.selectAll();
      logger.info("查询所有情感意图列表成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询所有情感意图列表异常，操作人：{}，异常信息：", OPERATOR, e);
      throw new RuntimeException("查询所有情感意图列表失败", e);
    }
  }

  /**
   * 查询所有情感意图关键信息（简化字段）
   */
  @Override
  public List<EmotionalIntent> getAllKeyInfo() {
    logger.info("查询所有情感意图关键信息开始，操作人：{}", OPERATOR);

    try {
      List<EmotionalIntent> result = emotionalIntentMapper.selectAllKeyInfo();
      logger.info("查询所有情感意图关键信息成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("查询所有情感意图关键信息异常，操作人：{}，异常信息：", OPERATOR, e);
      throw new RuntimeException("查询所有情感意图关键信息失败", e);
    }
  }

  /**
   * 新增情感意图
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String create(EmotionalIntentCreateDTO createDTO, MultipartFile iconfile, MultipartFile symbolImageFile,
                       MultipartFile energyImageFile, MultipartFile applicationImageFile, MultipartFile meditationImageFile) {
    logger.info("新增情感意图开始，操作人：{}，参数：{}", OPERATOR, createDTO);
    if (createDTO == null) {
      logger.warn("新增情感意图失败，参数不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("参数不能为空");
    }
    try {
      // 生成ID
      String id = String.valueOf(SnowflakeIdGenerator.nextId());
      // 转换DTO
      EmotionalIntent EmotionalIntent = new EmotionalIntent();
      BeanUtils.copyProperties(createDTO, EmotionalIntent);
      EmotionalIntent.setId(id);

      if(iconfile != null && !iconfile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,iconfile);
        EmotionalIntent.setIconId((String) map.get("key"));
        EmotionalIntent.setIconUrl((String) map.get("jdpath"));
      }
      if(symbolImageFile != null && !symbolImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,symbolImageFile);
        EmotionalIntent.setSymbolImageId((String) map.get("key"));
        EmotionalIntent.setSymbolImageUrl((String) map.get("jdpath"));
      }
      if(energyImageFile != null && !energyImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,energyImageFile);
        EmotionalIntent.setEnergyImageId((String) map.get("key"));
        EmotionalIntent.setEnergyImageUrl((String) map.get("jdpath"));
      }
      if(applicationImageFile != null && !applicationImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,applicationImageFile);
        EmotionalIntent.setApplicationImageId((String) map.get("key"));
        EmotionalIntent.setApplicationImageUrl((String) map.get("jdpath"));
      }
      if(meditationImageFile != null && !meditationImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,meditationImageFile);
        EmotionalIntent.setMeditationImageId((String) map.get("key"));
        EmotionalIntent.setMeditationImageUrl((String) map.get("jdpath"));
      }
      // 设置默认值
      if (EmotionalIntent.getIsActive() == null) {
        EmotionalIntent.setIsActive(1); // 默认启用
      }
      if (EmotionalIntent.getShowInNavigation() == null) {
        EmotionalIntent.setShowInNavigation(1); // 默认在导航显示
      }
      if (EmotionalIntent.getShowInFilter() == null) {
        EmotionalIntent.setShowInFilter(1); // 默认在筛选器显示
      }
      if (EmotionalIntent.getIsFeatured() == null) {
        EmotionalIntent.setIsFeatured(0); // 默认不推荐
      }
      if (EmotionalIntent.getSortOrder() == null) {
        EmotionalIntent.setSortOrder(0); // 默认排序值
      }

      int result = emotionalIntentMapper.insert(EmotionalIntent);
      if (result > 0) {
        logger.info("新增情感意图成功，操作人：{}，生成的ID：{}", OPERATOR, id);
        return id;
      } else {
        logger.error("新增情感意图失败，操作人：{}，参数：{}", OPERATOR, createDTO);
        throw new RuntimeException("新增情感意图失败");
      }
    } catch (RuntimeException e) {
      logger.error("新增情感意图业务异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      throw e;
    } catch (Exception e) {
      logger.error("新增情感意图异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      throw new RuntimeException("新增情感意图失败", e);
    }
  }

  /**
   * 更新情感意图信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean update(EmotionalIntentUpdateDTO updateDTO, MultipartFile iconfile,MultipartFile symbolImageFile,MultipartFile energyImageFile,
                        MultipartFile applicationImageFile,MultipartFile meditationImageFile) {
    logger.info("更新情感意图信息开始，操作人：{}，参数：{}", OPERATOR, updateDTO);

    if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
      logger.warn("更新情感意图信息失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }

    try {
      // 检查数据是否存在
      EmotionalIntent existing = emotionalIntentMapper.selectById(updateDTO.getId());
      if (existing == null) {
        logger.warn("更新情感意图信息失败，数据不存在，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
        throw new RuntimeException("情感意图不存在");
      }

      // 检查意图代码是否已存在（排除当前记录）
      if (StringUtils.hasText(updateDTO.getIntentCode()) &&
        checkIntentCodeExists(updateDTO.getIntentCode(), updateDTO.getId())) {
        logger.warn("更新情感意图信息失败，意图代码已存在，操作人：{}，意图代码：{}", OPERATOR, updateDTO.getIntentCode());
        throw new RuntimeException("意图代码已存在");
      }
      // 转换DTO
      EmotionalIntent emotionalIntent = new EmotionalIntent();
      BeanUtils.copyProperties(updateDTO, emotionalIntent);
      //若图片有数据  则 1先删除原图片数据  2再删除图片标数据，3然后新增图片数据和图片表数据，4最后更新业务表数据
      //1 删除图片数据
      if(emotionalIntent.getIconUrl()!= null && !emotionalIntent.getIconUrl().isEmpty()){
        fileStorageService.delete(emotionalIntent.getIconUrl().replace("/api/files", ""));
      }
      if(emotionalIntent.getSymbolImageUrl()!= null && !emotionalIntent.getSymbolImageUrl().isEmpty()){
        fileStorageService.delete(emotionalIntent.getSymbolImageUrl().replace("/api/files", ""));
      }
      if(emotionalIntent.getEnergyImageUrl()!= null && !emotionalIntent.getEnergyImageUrl().isEmpty()){
        fileStorageService.delete(emotionalIntent.getEnergyImageUrl().replace("/api/files", ""));
      }
      if(emotionalIntent.getApplicationImageUrl()!= null && !emotionalIntent.getApplicationImageUrl().isEmpty()){
        fileStorageService.delete(emotionalIntent.getApplicationImageUrl().replace("/api/files", ""));
      }
      if(emotionalIntent.getMeditationImageUrl()!= null && !emotionalIntent.getMeditationImageUrl().isEmpty()){
        fileStorageService.delete(emotionalIntent.getMeditationImageUrl().replace("/api/files", ""));
      }
      //2 删除图片表数据，根据主键批量删除
      if(emotionalIntent.getIconId()!= null && !emotionalIntent.getIconId().isEmpty()){
        productImageService.deleteProductImageById(emotionalIntent.getIconId());
      }
      if(emotionalIntent.getSymbolImageId()!= null && !emotionalIntent.getSymbolImageId().isEmpty()){
        productImageService.deleteProductImageById(emotionalIntent.getSymbolImageId());
      }
      if(emotionalIntent.getEnergyImageId()!= null && !emotionalIntent.getEnergyImageId().isEmpty()){
        productImageService.deleteProductImageById(emotionalIntent.getEnergyImageId());
      }
      if(emotionalIntent.getApplicationImageId()!= null && !emotionalIntent.getApplicationImageId().isEmpty()){
        productImageService.deleteProductImageById(emotionalIntent.getApplicationImageId());
      }
      if(emotionalIntent.getMeditationImageId()!= null && !emotionalIntent.getMeditationImageId().isEmpty()){
        productImageService.deleteProductImageById(emotionalIntent.getMeditationImageId());
      }
      //新增图片和图片表数据
      if(iconfile != null && !iconfile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,iconfile);
        emotionalIntent.setIconId((String) map.get("key"));
        emotionalIntent.setIconUrl((String) map.get("jdpath"));
      }
      if(symbolImageFile != null && !symbolImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,symbolImageFile);
        emotionalIntent.setSymbolImageId((String) map.get("key"));
        emotionalIntent.setSymbolImageUrl((String) map.get("jdpath"));
      }
      if(energyImageFile != null && !energyImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,energyImageFile);
        emotionalIntent.setEnergyImageId((String) map.get("key"));
        emotionalIntent.setEnergyImageUrl((String) map.get("jdpath"));
      }
      if(applicationImageFile != null && !applicationImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,applicationImageFile);
        emotionalIntent.setApplicationImageId((String) map.get("key"));
        emotionalIntent.setApplicationImageUrl((String) map.get("jdpath"));
      }
      if(meditationImageFile != null && !meditationImageFile.isEmpty()){
        Map map =productImageService.createProductImageWhithOpen(AppCommonConstants.IMAGE_PRODUCT_EMO_File_PATH,meditationImageFile);
        emotionalIntent.setMeditationImageId((String) map.get("key"));
        emotionalIntent.setMeditationImageUrl((String) map.get("jdpath"));
      }
      int result = emotionalIntentMapper.updateById(emotionalIntent);
      boolean success = result > 0;

      if (success) {
        logger.info("更新情感意图信息成功，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
      } else {
        logger.warn("更新情感意图信息失败，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
      }

      return success;
    } catch (RuntimeException e) {
      logger.error("更新情感意图信息业务异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      throw e;
    } catch (Exception e) {
      logger.error("更新情感意图信息异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      throw new RuntimeException("更新情感意图信息失败", e);
    }
  }

  /**
   * @description:
   * @author: 新建关联关系
   * @date: 2025/11/14 13:33
   * @param: [spuId, arrays]
   * @return: [java.lang.String, java.util.Arrays]
   **/
  public int createRealiations(String spuId, List list){
    return productSpuIntentService.createRealiations(spuId,list);
  }

  /**
   * 根据ID删除情感意图（逻辑删除）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean deleteById(String id) {
    logger.info("删除情感意图开始，操作人：{}，参数：id={}", OPERATOR, id);
    if (!StringUtils.hasText(id)) {
      logger.warn("删除情感意图失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }
    try {
      //删除业务表数据
      int result = emotionalIntentMapper.deleteById(id);
      boolean success = result > 0;
      if (success) {
        logger.info("删除情感意图成功，操作人：{}，ID：{}", OPERATOR, id);
      } else {
        logger.warn("删除情感意图失败，操作人：{}，ID：{}", OPERATOR, id);
      }

      return success;
    } catch (Exception e) {
      logger.error("删除情感意图异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("删除情感意图失败", e);
    }
  }

  /**
   * 批量删除情感意图（逻辑删除）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer deleteBatch(List<String> ids) {
    logger.info("批量删除情感意图开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量删除情感意图失败，ID列表不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID列表不能为空");
    }

    try {
      int result = emotionalIntentMapper.deleteBatchByIds(ids);
      logger.info("批量删除情感意图成功，操作人：{}，删除数量：{}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("批量删除情感意图异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      throw new RuntimeException("批量删除情感意图失败", e);
    }
  }

  /**
   * 冻结情感意图（更新状态为0-冻结）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean freeze(String id) {
    logger.info("冻结情感意图开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("冻结情感意图失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }

    try {
      int result = emotionalIntentMapper.updateStatusById(id, 0);
      boolean success = result > 0;

      if (success) {
        logger.info("冻结情感意图成功，操作人：{}，ID：{}", OPERATOR, id);
      } else {
        logger.warn("冻结情感意图失败，操作人：{}，ID：{}", OPERATOR, id);
      }

      return success;
    } catch (Exception e) {
      logger.error("冻结情感意图异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("冻结情感意图失败", e);
    }
  }

  /**
   * 启用情感意图（更新状态为1-启用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean activate(String id) {
    logger.info("启用情感意图开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("启用情感意图失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }

    try {
      int result = emotionalIntentMapper.updateStatusById(id, 1);
      boolean success = result > 0;

      if (success) {
        logger.info("启用情感意图成功，操作人：{}，ID：{}", OPERATOR, id);
      } else {
        logger.warn("启用情感意图失败，操作人：{}，ID：{}", OPERATOR, id);
      }

      return success;
    } catch (Exception e) {
      logger.error("启用情感意图异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("启用情感意图失败", e);
    }
  }

  /**
   * 批量冻结情感意图
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer freezeBatch(List<String> ids) {
    logger.info("批量冻结情感意图开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量冻结情感意图失败，ID列表不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID列表不能为空");
    }

    try {
      int result = emotionalIntentMapper.updateStatusBatchByIds(ids, 0);
      logger.info("批量冻结情感意图成功，操作人：{}，冻结数量：{}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("批量冻结情感意图异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      throw new RuntimeException("批量冻结情感意图失败", e);
    }
  }

  /**
   * 批量启用情感意图
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer activateBatch(List<String> ids) {
    logger.info("批量启用情感意图开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量启用情感意图失败，ID列表不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID列表不能为空");
    }

    try {
      int result = emotionalIntentMapper.updateStatusBatchByIds(ids, 1);
      logger.info("批量启用情感意图成功，操作人：{}，启用数量：{}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("批量启用情感意图异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      throw new RuntimeException("批量启用情感意图失败", e);
    }
  }

  /**
   * 更新排序值
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateSortOrder(String id, Integer sortOrder) {
    logger.info("更新排序值开始，操作人：{}，参数：id={}, sortOrder={}", OPERATOR, id, sortOrder);

    if (!StringUtils.hasText(id)) {
      logger.warn("更新排序值失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }

    if (sortOrder == null || sortOrder < 0) {
      logger.warn("更新排序值失败，排序值不能为空且必须大于等于0，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("排序值不能为空且必须大于等于0");
    }

    try {
      int result = emotionalIntentMapper.updateSortOrder(id, sortOrder);
      boolean success = result > 0;

      if (success) {
        logger.info("更新排序值成功，操作人：{}，ID：{}，排序值：{}", OPERATOR, id, sortOrder);
      } else {
        logger.warn("更新排序值失败，操作人：{}，ID：{}，排序值：{}", OPERATOR, id, sortOrder);
      }

      return success;
    } catch (Exception e) {
      logger.error("更新排序值异常，操作人：{}，参数：id={}, sortOrder={}，异常信息：", OPERATOR, id, sortOrder, e);
      throw new RuntimeException("更新排序值失败", e);
    }
  }

  /**
   * 更新推荐状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateFeaturedStatus(String id, Integer isFeatured) {
    logger.info("更新推荐状态开始，操作人：{}，参数：id={}, isFeatured={}", OPERATOR, id, isFeatured);

    if (!StringUtils.hasText(id)) {
      logger.warn("更新推荐状态失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }

    if (isFeatured == null || (isFeatured != 0 && isFeatured != 1)) {
      logger.warn("更新推荐状态失败，推荐状态值必须为0或1，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("推荐状态值必须为0或1");
    }

    try {
      int result = emotionalIntentMapper.updateFeaturedStatus(id, isFeatured);
      boolean success = result > 0;

      if (success) {
        logger.info("更新推荐状态成功，操作人：{}，ID：{}，推荐状态：{}", OPERATOR, id, isFeatured);
      } else {
        logger.warn("更新推荐状态失败，操作人：{}，ID：{}，推荐状态：{}", OPERATOR, id, isFeatured);
      }

      return success;
    } catch (Exception e) {
      logger.error("更新推荐状态异常，操作人：{}，参数：id={}, isFeatured={}，异常信息：", OPERATOR, id, isFeatured, e);
      throw new RuntimeException("更新推荐状态失败", e);
    }
  }

  /**
   * 更新显示状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateShowStatus(String id, Integer showInNavigation) {
    logger.info("更新显示状态开始，操作人：{}，参数：id={}, showInNavigation={}", OPERATOR, id, showInNavigation);

    if (!StringUtils.hasText(id)) {
      logger.warn("更新显示状态失败，ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("ID不能为空");
    }

    if (showInNavigation == null || (showInNavigation != 0 && showInNavigation != 1)) {
      logger.warn("更新显示状态失败，显示状态值必须为0或1，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("显示状态值必须为0或1");
    }

    try {
      int result = emotionalIntentMapper.updateShowStatus(id, showInNavigation);
      boolean success = result > 0;

      if (success) {
        logger.info("更新显示状态成功，操作人：{}，ID：{}，显示状态：{}", OPERATOR, id, showInNavigation);
      } else {
        logger.warn("更新显示状态失败，操作人：{}，ID：{}，显示状态：{}", OPERATOR, id, showInNavigation);
      }

      return success;
    } catch (Exception e) {
      logger.error("更新显示状态异常，操作人：{}，参数：id={}, showInNavigation={}，异常信息：", OPERATOR, id, showInNavigation, e);
      throw new RuntimeException("更新显示状态失败", e);
    }
  }

  /**
   * 检查意图代码是否已存在
   */
  @Override
  public Boolean checkIntentCodeExists(String intentCode, String excludeId) {
    logger.info("检查意图代码是否存在开始，操作人：{}，参数：intentCode={}, excludeId={}", OPERATOR, intentCode, excludeId);

    if (!StringUtils.hasText(intentCode)) {
      return false;
    }

    try {
      EmotionalIntentQueryDTO queryDTO = new EmotionalIntentQueryDTO();
      queryDTO.setIntentCode(intentCode);
      List<EmotionalIntent> list = emotionalIntentMapper.selectByCondition(queryDTO);

      if (list.isEmpty()) {
        logger.info("意图代码不存在，操作人：{}，意图代码：{}", OPERATOR, intentCode);
        return false;
      }

      // 如果提供了排除ID，检查是否只有当前记录使用该代码
      if (StringUtils.hasText(excludeId)) {
        boolean exists = list.stream().anyMatch(item -> !item.getId().equals(excludeId));
        logger.info("检查意图代码是否存在完成，操作人：{}，意图代码：{}，排除ID：{}，结果：{}",
          OPERATOR, intentCode, excludeId, exists);
        return exists;
      }

      logger.info("意图代码已存在，操作人：{}，意图代码：{}", OPERATOR, intentCode);
      return true;
    } catch (Exception e) {
      logger.error("检查意图代码是否存在异常，操作人：{}，参数：intentCode={}, excludeId={}，异常信息：",
        OPERATOR, intentCode, excludeId, e);
      throw new RuntimeException("检查意图代码是否存在失败", e);
    }
  }

  /**
   * 根据意图代码查询情感意图
   */
  @Override
  public EmotionalIntent getByIntentCode(String intentCode) {
    logger.info("根据意图代码查询情感意图开始，操作人：{}，参数：intentCode={}", OPERATOR, intentCode);

    if (!StringUtils.hasText(intentCode)) {
      logger.warn("根据意图代码查询情感意图失败，意图代码不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("意图代码不能为空");
    }

    try {
      EmotionalIntentQueryDTO queryDTO = new EmotionalIntentQueryDTO();
      queryDTO.setIntentCode(intentCode);
      List<EmotionalIntent> list = emotionalIntentMapper.selectByCondition(queryDTO);

      if (list.isEmpty()) {
        logger.info("根据意图代码查询情感意图未找到数据，操作人：{}，意图代码：{}", OPERATOR, intentCode);
        return null;
      }

      EmotionalIntent result = list.get(0);
      logger.info("根据意图代码查询情感意图成功，操作人：{}，意图代码：{}，结果：{}", OPERATOR, intentCode, result);
      return result;
    } catch (Exception e) {
      logger.error("根据意图代码查询情感意图异常，操作人：{}，参数：intentCode={}，异常信息：", OPERATOR, intentCode, e);
      throw new RuntimeException("根据意图代码查询情感意图失败", e);
    }
  }

  /**
   * 获取启用状态的情感意图数量
   */
  @Override
  public Long getActiveCount() {
    logger.info("获取启用状态的情感意图数量开始，操作人：{}", OPERATOR);

    try {
      EmotionalIntentQueryDTO queryDTO = new EmotionalIntentQueryDTO();
      queryDTO.setStatus(1); // 启用状态
      long count = emotionalIntentMapper.countByCondition(queryDTO);

      logger.info("获取启用状态的情感意图数量成功，操作人：{}，数量：{}", OPERATOR, count);
      return count;
    } catch (Exception e) {
      logger.error("获取启用状态的情感意图数量异常，操作人：{}，异常信息：", OPERATOR, e);
      throw new RuntimeException("获取启用状态的情感意图数量失败", e);
    }
  }

  /**
   * 获取推荐的情感意图列表
   */
  @Override
  public List<EmotionalIntent> getFeaturedList() {
    logger.info("获取推荐的情感意图列表开始，操作人：{}", OPERATOR);

    try {
      EmotionalIntentQueryDTO queryDTO = new EmotionalIntentQueryDTO();
      queryDTO.setIsFeatured(1); // 推荐状态
      queryDTO.setStatus(1); // 启用状态

      List<EmotionalIntent> result = emotionalIntentMapper.selectByCondition(queryDTO);
      logger.info("获取推荐的情感意图列表成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("获取推荐的情感意图列表异常，操作人：{}，异常信息：", OPERATOR, e);
      throw new RuntimeException("获取推荐的情感意图列表失败", e);
    }
  }

  /**
   * 获取在导航显示的情感意图列表
   */
  @Override
  public List<EmotionalIntent> getNavigationList() {
    logger.info("获取在导航显示的情感意图列表开始，操作人：{}", OPERATOR);

    try {
      EmotionalIntentQueryDTO queryDTO = new EmotionalIntentQueryDTO();
      queryDTO.setShowInNavigation(1); // 在导航显示
      queryDTO.setStatus(1); // 启用状态

      List<EmotionalIntent> result = emotionalIntentMapper.selectByCondition(queryDTO);
      logger.info("获取在导航显示的情感意图列表成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("获取在导航显示的情感意图列表异常，操作人：{}，异常信息：", OPERATOR, e);
      throw new RuntimeException("获取在导航显示的情感意图列表失败", e);
    }
  }
}
