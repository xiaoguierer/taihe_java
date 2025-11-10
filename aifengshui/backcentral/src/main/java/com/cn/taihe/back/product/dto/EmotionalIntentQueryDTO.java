package com.cn.taihe.back.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * 情感意图查询条件DTO
 *
 * @author system
 * @date 2025-11-06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "EmotionalIntentQueryDTO", description = "情感意图查询条件传输对象")
public class EmotionalIntentQueryDTO {

  // ==================== 核心查询条件 ====================
  @ApiModelProperty(value = "意图名称(中文名称模糊查询)", example = "爱与关怀")
  @Size(max = 100, message = "意图名称长度不能超过100个字符")
  private String intentName;

  @ApiModelProperty(value = "意图代码(精确查询)", example = "LOVE001")
  @Size(max = 20, message = "意图代码长度不能超过20个字符")
  private String intentCode;

  @ApiModelProperty(value = "意图分类: primary-主要, extended-扩展, combined-组合", example = "primary")
  private String intentCategory;

  @ApiModelProperty(value = "状态: 1-启用, 0-冻结", example = "1")
  private Integer status;

  @ApiModelProperty(value = "是否在导航显示: 1-是, 0-否", example = "1")
  private Integer showInNavigation;

  @ApiModelProperty(value = "是否推荐: 1-是, 0-否", example = "0")
  private Integer isFeatured;

  // ==================== 分页排序参数 ====================
  @ApiModelProperty(value = "排序字段", example = "sort_order")
  private String sortBy;

  @ApiModelProperty(value = "排序字段", example = "created_time")
  private String sortField;

  @ApiModelProperty(value = "排序方向: ASC-升序, DESC-降序", example = "DESC")
  private String sortOrder;

  // ==================== 时间范围查询 ====================
  @ApiModelProperty(value = "创建时间开始(yyyy-MM-dd HH:mm:ss)", example = "2024-01-01 00:00:00")
  private String createTimeStart;

  @ApiModelProperty(value = "创建时间结束(yyyy-MM-dd HH:mm:ss)", example = "2024-12-31 23:59:59")
  private String createTimeEnd;

  @ApiModelProperty(value = "更新时间开始(yyyy-MM-dd HH:mm:ss)", example = "2024-01-01 00:00:00")
  private String updateTimeStart;

  @ApiModelProperty(value = "更新时间结束(yyyy-MM-dd HH:mm:ss)", example = "2024-12-31 23:59:59")
  private String updateTimeEnd;

  // ==================== 情感属性查询 ====================
  @ApiModelProperty(value = "主要情感", example = "爱")
  @Size(max = 50, message = "主要情感长度不能超过50个字符")
  private String primaryEmotion;

  @ApiModelProperty(value = "情感强度范围: 低(1-33), 中(34-66), 高(67-100)", example = "高")
  private String intensityLevel;

  @ApiModelProperty(value = "情感方向: inward-内向, outward-外向, balanced-平衡", example = "outward")
  private String emotionalDirection;

  // ==================== 控制参数 ====================
  @ApiModelProperty(value = "是否包含已删除数据", example = "false")
  private Boolean includeDeleted;

  @ApiModelProperty(value = "是否只查询启用状态", example = "true")
  private Boolean onlyActive ;

  // ==================== 构造方法 ====================
  public EmotionalIntentQueryDTO() {
  }

  public EmotionalIntentQueryDTO(String intentName, String intentCode, String intentCategory) {
    this.intentName = intentName;
    this.intentCode = intentCode;
    this.intentCategory = intentCategory;
  }

  // ==================== 业务方法 ====================
  /**
   * 是否有查询条件
   */
  public boolean hasQueryConditions() {
    return intentName != null || intentCode != null || intentCategory != null ||
      status != null || showInNavigation != null || isFeatured != null ||
      primaryEmotion != null || intensityLevel != null || emotionalDirection != null;
  }

  /**
   * 是否有时间范围查询
   */
  public boolean hasTimeRange() {
    return (createTimeStart != null && createTimeEnd != null) ||
      (updateTimeStart != null && updateTimeEnd != null);
  }

  /**
   * 获取排序字段
   */
  public String getSortField() {
    if (sortField == null || sortField.trim().isEmpty()) {
      return "sort_order";
    }
    return sortField;
  }

  /**
   * 获取排序方向
   */
  public String getSortOrder() {
    if (sortOrder == null || sortOrder.trim().isEmpty()) {
      return "ASC";
    }
    return sortOrder.toUpperCase();
  }

  /**
   * 构建模糊查询条件
   */
  public String getIntentNameLike() {
    if (intentName == null || intentName.trim().isEmpty()) {
      return null;
    }
    return "%" + intentName.trim() + "%";
  }

  /**
   * 构建主要情感模糊查询条件
   */
  public String getPrimaryEmotionLike() {
    if (primaryEmotion == null || primaryEmotion.trim().isEmpty()) {
      return null;
    }
    return "%" + primaryEmotion.trim() + "%";
  }

  /**
   * 获取情感强度范围
   */
  public Integer[] getIntensityRange() {
    if ("低".equals(intensityLevel) || "low".equalsIgnoreCase(intensityLevel)) {
      return new Integer[]{1, 33};
    } else if ("中".equals(intensityLevel) || "medium".equalsIgnoreCase(intensityLevel)) {
      return new Integer[]{34, 66};
    } else if ("高".equals(intensityLevel) || "high".equalsIgnoreCase(intensityLevel)) {
      return new Integer[]{67, 100};
    }
    return null;
  }
}
