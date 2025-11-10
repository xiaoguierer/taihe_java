package com.cn.taihe.back.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 情感意图新增数据传输对象
 *
 * @author system
 * @date 2025-11-06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "EmotionalIntentCreateDTO", description = "情感意图新增数据传输对象")
public class EmotionalIntentCreateDTO {

  // ==================== 基础标识字段 ====================
  @ApiModelProperty(value = "意图键名", example = "love_and_care", required = true)
  private String intentKey;

  @ApiModelProperty(value = "意图代码", example = "LOVE001", required = true)
  private String intentCode;

  @ApiModelProperty(value = "意图分类:primary主要,extended扩展,combined组合", example = "primary", required = true)
  private String intentCategory;

  // ==================== 多语言名称 ====================
  @ApiModelProperty(value = "英文名称", example = "Love and Care", required = true)
  private String intentNameEn;

  @ApiModelProperty(value = "中文名称", example = "爱与关怀", required = true)
  private String intentNameZh;

  @ApiModelProperty(value = "阿拉伯语名称", example = "الحب والرعاية")
  private String intentNameAr;

  // ==================== 核心象征属性 ====================
  @ApiModelProperty(value = "象征字符", example = "❤️", required = true)
  private String symbolCharacter;

  @ApiModelProperty(value = "象征颜色代码", example = "#FF6B6B", required = true)
  private String symbolColor;

  @ApiModelProperty(value = "渐变色系", example = "linear-gradient(45deg, #FF6B6B, #4ECDC4)")
  private String symbolColorGradient;

  // ==================== 情感属性 ====================
  @ApiModelProperty(value = "主要情感(中文)", example = "爱")
  private String primaryEmotionZh;

  @ApiModelProperty(value = "主要情感(英文)", example = "Love")
  private String primaryEmotionEn;

  @ApiModelProperty(value = "主要情感(阿拉伯语)", example = "حب")
  private String primaryEmotionAr;

  @ApiModelProperty(value = "次要情感(中文)", example = "关怀,温暖,包容")
  private String secondaryEmotionsZh;

  @ApiModelProperty(value = "次要情感(英文)", example = "Care,Warmth,Acceptance")
  private String secondaryEmotionsEn;

  @ApiModelProperty(value = "次要情感(阿拉伯语)", example = "رعاية,دفء,قبول")
  private String secondaryEmotionsAr;

  @ApiModelProperty(value = "情感强度(1-100)", example = "75")
  private Integer emotionalIntensity;

  @ApiModelProperty(value = "情感方向:inward内向,outward外向,balanced平衡", example = "outward")
  private String emotionalDirection;

  @ApiModelProperty(value = "情感频率:low低频,medium中频,high高频", example = "medium")
  private String emotionalFrequency;

  // ==================== 哲学文化含义 ====================
  @ApiModelProperty(value = "中文哲学含义")
  private String philosophyMeaningZh;

  @ApiModelProperty(value = "英文哲学含义")
  private String philosophyMeaningEn;

  @ApiModelProperty(value = "阿拉伯语哲学含义")
  private String philosophyMeaningAr;

  @ApiModelProperty(value = "中文文化意义")
  private String culturalSignificanceZh;

  @ApiModelProperty(value = "英文文化意义")
  private String culturalSignificanceEn;

  @ApiModelProperty(value = "阿拉伯语文化意义")
  private String culturalSignificanceAr;

  @ApiModelProperty(value = "中文灵性意义")
  private String spiritualMeaningZh;

  @ApiModelProperty(value = "英文灵性意义")
  private String spiritualMeaningEn;

  @ApiModelProperty(value = "阿拉伯语灵性意义")
  private String spiritualMeaningAr;

  @ApiModelProperty(value = "中文现代诠释")
  private String modernInterpretationZh;

  @ApiModelProperty(value = "英文现代诠释")
  private String modernInterpretationEn;

  @ApiModelProperty(value = "阿拉伯语现代诠释")
  private String modernInterpretationAr;

  // ==================== 实用属性描述 ====================
  @ApiModelProperty(value = "中文人格原型")
  private String personalityArchetypeZh;

  @ApiModelProperty(value = "英文人格原型")
  private String personalityArchetypeEn;

  @ApiModelProperty(value = "阿拉伯语人格原型")
  private String personalityArchetypeAr;

  @ApiModelProperty(value = "中文人生指引")
  private String lifeGuidanceZh;

  @ApiModelProperty(value = "英文人生指引")
  private String lifeGuidanceEn;

  @ApiModelProperty(value = "阿拉伯语人生指引")
  private String lifeGuidanceAr;

  @ApiModelProperty(value = "中文疗愈属性")
  private String healingPropertyZh;

  @ApiModelProperty(value = "英文疗愈属性")
  private String healingPropertyEn;

  @ApiModelProperty(value = "阿拉伯语疗愈属性")
  private String healingPropertyAr;

  @ApiModelProperty(value = "中文关系影响")
  private String relationshipImpactZh;

  @ApiModelProperty(value = "英文关系影响")
  private String relationshipImpactEn;

  @ApiModelProperty(value = "阿拉伯语关系影响")
  private String relationshipImpactAr;

  @ApiModelProperty(value = "中文职业契合")
  private String careerAlignmentZh;

  @ApiModelProperty(value = "英文职业契合")
  private String careerAlignmentEn;

  @ApiModelProperty(value = "阿拉伯语职业契合")
  private String careerAlignmentAr;

  // ==================== 图片资源属性 ====================
  @ApiModelProperty(value = "图标图片Id")
  private String iconId;

  @ApiModelProperty(value = "象征图id")
  private String symbolImageId;

  @ApiModelProperty(value = "能量可视化图id")
  private String energyImageId;

  @ApiModelProperty(value = "应用场景图id")
  private String applicationImageId;

  @ApiModelProperty(value = "冥想引导图URL")
  private String meditationImageId;
  //
  @ApiModelProperty(value = "图标图片地址")
  private String iconUrl;

  @ApiModelProperty(value = "象征图地址")
  private String symbolImageUrl;

  @ApiModelProperty(value = "能量可视化图地址")
  private String energyImageUrl;

  @ApiModelProperty(value = "应用场景图地址")
  private String applicationImageUrl;

  @ApiModelProperty(value = "冥想引导图地址")
  private String meditationImageUrl;

  // ==================== SEO优化字段 ====================
  @ApiModelProperty(value = "中文SEO标题")
  private String metaTitleZh;

  @ApiModelProperty(value = "英文SEO标题")
  private String metaTitleEn;

  @ApiModelProperty(value = "阿拉伯语SEO标题")
  private String metaTitleAr;

  @ApiModelProperty(value = "中文SEO描述")
  private String metaDescriptionZh;

  @ApiModelProperty(value = "英文SEO描述")
  private String metaDescriptionEn;

  @ApiModelProperty(value = "阿拉伯语SEO描述")
  private String metaDescriptionAr;

  @ApiModelProperty(value = "中文SEO关键词")
  private String metaKeywordsZh;

  @ApiModelProperty(value = "英文SEO关键词")
  private String metaKeywordsEn;

  @ApiModelProperty(value = "阿拉伯语SEO关键词")
  private String metaKeywordsAr;

  // ==================== 控制属性 ====================
  @ApiModelProperty(value = "排序值", example = "0", required = true)
  private Integer sortOrder;

  @ApiModelProperty(value = "强度等级(1-100)", example = "50")
  private Integer intensityLevel;

  @ApiModelProperty(value = "受欢迎度评分", example = "0")
  private Integer popularityScore;

  @ApiModelProperty(value = "是否启用", example = "1", required = true)
  private Integer isActive;

  @ApiModelProperty(value = "是否在导航显示", example = "1", required = true)
  private Integer showInNavigation;

  @ApiModelProperty(value = "是否在筛选器显示", example = "1", required = true)
  private Integer showInFilter;

  @ApiModelProperty(value = "是否推荐", example = "0", required = true)
  private Integer isFeatured;
}
