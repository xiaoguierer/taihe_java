package com.cn.taihe.back.product.service;

import com.cn.taihe.back.product.dto.EmotionalIntentCreateDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentQueryDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentUpdateDTO;
import com.cn.taihe.back.product.entity.EmotionalIntent;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 情感意图服务接口
 *
 * @author system
 * @date 2025-11-06
 */
@Api(tags = "情感意图管理服务接口")
public interface EmotionalIntentService {

  /**
   * 根据ID查询情感意图详情
   *
   * @param id 主键ID
   * @return 情感意图详情
   */
  @ApiOperation(value = "根据ID查询情感意图详情")
  EmotionalIntent getById(String id);

  /**
   * 分页查询情感意图列表
   *
   * @param queryDTO 查询条件
   * @param page 页码，默认1
   * @param size 每页大小，默认10
   * @return 分页结果
   */
  @ApiOperation(value = "分页查询情感意图列表")
  PageInfo<EmotionalIntent> getByPage(@Valid EmotionalIntentQueryDTO queryDTO, Integer page, Integer size);

  /**
   * 查询所有情感意图列表（不包含已删除的）
   *
   * @return 情感意图列表
   */
  @ApiOperation(value = "查询所有情感意图列表")
  List<EmotionalIntent> getAll();

  /**
   * 查询所有情感意图关键信息（简化字段，用于下拉选择等场景）
   *
   * @return 情感意图关键信息列表
   */
  @ApiOperation(value = "查询所有情感意图关键信息")
  List<EmotionalIntent> getAllKeyInfo();

  /**
   * 新增情感意图
   *
   * @param createDTO 新增参数
   * @return 新增的情感意图ID
   */
  @ApiOperation(value = "新增情感意图")
  String create(@Valid EmotionalIntentCreateDTO createDTO, MultipartFile iconfile,MultipartFile symbolImageFile,MultipartFile energyImageFile,
                MultipartFile applicationImageFile,MultipartFile meditationImageFile);

  /**
   * 更新情感意图信息
   *
   * @param updateDTO 更新参数
   * @return 是否更新成功
   */
  @ApiOperation(value = "更新情感意图信息")
  Boolean update(@Valid EmotionalIntentUpdateDTO updateDTO, MultipartFile iconfile,MultipartFile symbolImageFile,MultipartFile energyImageFile,
                 MultipartFile applicationImageFile,MultipartFile meditationImageFile);

  /**
   * 根据ID删除情感意图（逻辑删除）
   *
   * @param id 主键ID
   * @return 是否删除成功
   */
  @ApiOperation(value = "根据ID删除情感意图")
  Boolean deleteById(String id);

  /**
   * 批量删除情感意图（逻辑删除）
   *
   * @param ids 主键ID集合
   * @return 删除成功的数量
   */
  @ApiOperation(value = "批量删除情感意图")
  Integer deleteBatch(List<String> ids);

  /**
   * 冻结情感意图（更新状态为0-冻结）
   *
   * @param id 主键ID
   * @return 是否冻结成功
   */
  @ApiOperation(value = "冻结情感意图")
  Boolean freeze(String id);

  /**
   * 启用情感意图（更新状态为1-启用）
   *
   * @param id 主键ID
   * @return 是否启用成功
   */
  @ApiOperation(value = "启用情感意图")
  Boolean activate(String id);

  /**
   * 批量冻结情感意图
   *
   * @param ids 主键ID集合
   * @return 冻结成功的数量
   */
  @ApiOperation(value = "批量冻结情感意图")
  Integer freezeBatch(List<String> ids);

  /**
   * 批量启用情感意图
   *
   * @param ids 主键ID集合
   * @return 启用成功的数量
   */
  @ApiOperation(value = "批量启用情感意图")
  Integer activateBatch(List<String> ids);

  /**
   * 更新排序值
   *
   * @param id 主键ID
   * @param sortOrder 排序值
   * @return 是否更新成功
   */
  @ApiOperation(value = "更新排序值")
  Boolean updateSortOrder(String id, Integer sortOrder);

  /**
   * 更新推荐状态
   *
   * @param id 主键ID
   * @param isFeatured 是否推荐：1-是，0-否
   * @return 是否更新成功
   */
  @ApiOperation(value = "更新推荐状态")
  Boolean updateFeaturedStatus(String id, Integer isFeatured);

  /**
   * 更新显示状态
   *
   * @param id 主键ID
   * @param showInNavigation 是否在导航显示：1-是，0-否
   * @return 是否更新成功
   */
  @ApiOperation(value = "更新显示状态")
  Boolean updateShowStatus(String id, Integer showInNavigation);

  /**
   * 检查意图代码是否已存在
   *
   * @param intentCode 意图代码
   * @param excludeId 排除的ID（用于更新时检查）
   * @return 是否存在
   */
  @ApiOperation(value = "检查意图代码是否已存在")
  Boolean checkIntentCodeExists(String intentCode, String excludeId);


  /**
   * 根据意图代码查询情感意图
   *
   * @param intentCode 意图代码
   * @return 情感意图信息
   */
  @ApiOperation(value = "根据意图代码查询情感意图")
  EmotionalIntent getByIntentCode(String intentCode);

  /**
   * 获取启用状态的情感意图数量
   *
   * @return 启用状态的数量
   */
  @ApiOperation(value = "获取启用状态的情感意图数量")
  Long getActiveCount();

  /**
   * 获取推荐的情感意图列表
   *
   * @return 推荐的情感意图列表
   */
  @ApiOperation(value = "获取推荐的情感意图列表")
  List<EmotionalIntent> getFeaturedList();

  /**
   * 获取在导航显示的情感意图列表
   *
   * @return 在导航显示的情感意图列表
   */
  @ApiOperation(value = "获取在导航显示的情感意图列表")
  List<EmotionalIntent> getNavigationList();
}
