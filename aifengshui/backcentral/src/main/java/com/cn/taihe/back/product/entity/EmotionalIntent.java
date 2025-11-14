package com.cn.taihe.back.product.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 情感意图表(完整多语言支持) 基础DTO
 *
 * @author system
 * @date 2025-11-06
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "emotional_intent")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@TableName("emotional_intent")
@ApiModel(value = "EmotionalIntent", description = "情感意图基础数据传输对象")
public class EmotionalIntent implements Serializable {
  private static final long serialVersionUID = 1L;

  // ==================== 主键字段 ====================
  @Id
  @TableId
  @ApiModelProperty(value = "主键ID", example = "1234567890", required = true)
  @NotBlank(message = "主键ID不能为空")
  @Size(max = 50, message = "主键ID长度不能超过32个字符")
  private String id;

  // ==================== 基础标识字段 ====================
  @TableField("intent_key")
  @ApiModelProperty(value = "意图键名", example = "love_and_care", required = true)
  @NotBlank(message = "意图键名不能为空")
  @Size(max = 50, message = "意图键名长度不能超过50个字符")
  private String intentKey;

  @TableField("intent_code")
  @ApiModelProperty(value = "意图代码", example = "LOVE001", required = true)
  @NotBlank(message = "意图代码不能为空")
  @Size(max = 20, message = "意图代码长度不能超过20个字符")
  private String intentCode;

  @TableField("intent_category")
  @ApiModelProperty(value = "意图分类:primary主要,extended扩展,combined组合", example = "primary", required = true)
  @NotBlank(message = "意图分类不能为空")
  @Size(max = 20, message = "意图分类长度不能超过20个字符")
  private String intentCategory;

  // ==================== 多语言名称 ====================
  @TableField("intent_name_en")
  @ApiModelProperty(value = "英文名称", example = "Love and Care", required = true)
  @NotBlank(message = "英文名称不能为空")
  @Size(max = 100, message = "英文名称长度不能超过100个字符")
  private String intentNameEn;

  @TableField("intent_name_zh")
  @ApiModelProperty(value = "中文名称", example = "爱与关怀", required = true)
  @NotBlank(message = "中文名称不能为空")
  @Size(max = 100, message = "中文名称长度不能超过100个字符")
  private String intentNameZh;

  @TableField("intent_name_ar")
  @ApiModelProperty(value = "阿拉伯语名称", example = "الحب والرعاية")
  @Size(max = 100, message = "阿拉伯语名称长度不能超过100个字符")
  private String intentNameAr;

  // ==================== 核心象征属性 ====================
  @TableField("symbol_character")
  @ApiModelProperty(value = "象征字符", example = "❤️", required = true)
  @NotBlank(message = "象征字符不能为空")
  @Size(max = 10, message = "象征字符长度不能超过10个字符")
  private String symbolCharacter;

  @TableField("symbol_color")
  @ApiModelProperty(value = "象征颜色代码", example = "#FF6B6B", required = true)
  @NotBlank(message = "象征颜色代码不能为空")
  @Size(max = 20, message = "象征颜色代码长度不能超过20个字符")
  private String symbolColor;

  @TableField("symbol_color_gradient")
  @ApiModelProperty(value = "渐变色系", example = "linear-gradient(45deg, #FF6B6B, #4ECDC4)")
  @Size(max = 100, message = "渐变色系长度不能超过100个字符")
  private String symbolColorGradient;

  // ==================== 情感属性 ====================
  @TableField("primary_emotion_zh")
  @ApiModelProperty(value = "主要情感(中文)", example = "爱")
  @Size(max = 50, message = "主要情感(中文)长度不能超过50个字符")
  private String primaryEmotionZh;

  @TableField("primary_emotion_en")
  @ApiModelProperty(value = "主要情感(英文)", example = "Love")
  @Size(max = 50, message = "主要情感(英文)长度不能超过50个字符")
  private String primaryEmotionEn;

  @TableField("primary_emotion_ar")
  @ApiModelProperty(value = "主要情感(阿拉伯语)", example = "حب")
  @Size(max = 50, message = "主要情感(阿拉伯语)长度不能超过50个字符")
  private String primaryEmotionAr;

  @TableField("secondary_emotions_zh")
  @ApiModelProperty(value = "次要情感(中文)", example = "关怀,温暖,包容")
  @Size(max = 500, message = "次要情感(中文)长度不能超过500个字符")
  private String secondaryEmotionsZh;

  @TableField("secondary_emotions_en")
  @ApiModelProperty(value = "次要情感(英文)", example = "Care,Warmth,Acceptance")
  @Size(max = 500, message = "次要情感(英文)长度不能超过500个字符")
  private String secondaryEmotionsEn;

  @TableField("secondary_emotions_ar")
  @ApiModelProperty(value = "次要情感(阿拉伯语)", example = "رعاية,دفء,قبول")
  @Size(max = 500, message = "次要情感(阿拉伯语)长度不能超过500个字符")
  private String secondaryEmotionsAr;

  @TableField("emotional_intensity")
  @ApiModelProperty(value = "情感强度(1-100)", example = "75")
  private Integer emotionalIntensity;

  @TableField("emotional_direction")
  @ApiModelProperty(value = "情感方向:inward内向,outward外向,balanced平衡", example = "outward")
  @Size(max = 20, message = "情感方向长度不能超过20个字符")
  private String emotionalDirection;

  @TableField("emotional_frequency")
  @ApiModelProperty(value = "情感频率:low低频,medium中频,high高频", example = "medium")
  @Size(max = 20, message = "情感频率长度不能超过20个字符")
  private String emotionalFrequency;

  // ==================== 哲学文化含义 ====================
  @TableField("philosophy_meaning_zh")
  @ApiModelProperty(value = "中文哲学含义")
  private String philosophyMeaningZh;

  @TableField("philosophy_meaning_en")
  @ApiModelProperty(value = "英文哲学含义")
  private String philosophyMeaningEn;

  @TableField("philosophy_meaning_ar")
  @ApiModelProperty(value = "阿拉伯语哲学含义")
  private String philosophyMeaningAr;

  @TableField("cultural_significance_zh")
  @ApiModelProperty(value = "中文文化意义")
  private String culturalSignificanceZh;

  @TableField("cultural_significance_en")
  @ApiModelProperty(value = "英文文化意义")
  private String culturalSignificanceEn;

  @TableField("cultural_significance_ar")
  @ApiModelProperty(value = "阿拉伯语文化意义")
  private String culturalSignificanceAr;

  @TableField("spiritual_meaning_zh")
  @ApiModelProperty(value = "中文灵性意义")
  private String spiritualMeaningZh;

  @TableField("spiritual_meaning_en")
  @ApiModelProperty(value = "英文灵性意义")
  private String spiritualMeaningEn;

  @TableField("spiritual_meaning_ar")
  @ApiModelProperty(value = "阿拉伯语灵性意义")
  private String spiritualMeaningAr;

  @TableField("modern_interpretation_zh")
  @ApiModelProperty(value = "中文现代诠释")
  private String modernInterpretationZh;

  @TableField("modern_interpretation_en")
  @ApiModelProperty(value = "英文现代诠释")
  private String modernInterpretationEn;

  @TableField("modern_interpretation_ar")
  @ApiModelProperty(value = "阿拉伯语现代诠释")
  private String modernInterpretationAr;

  // ==================== 实用属性描述 ====================
  @TableField("personality_archetype_zh")
  @ApiModelProperty(value = "中文人格原型")
  private String personalityArchetypeZh;

  @TableField("personality_archetype_en")
  @ApiModelProperty(value = "英文人格原型")
  private String personalityArchetypeEn;

  @TableField("personality_archetype_ar")
  @ApiModelProperty(value = "阿拉伯语人格原型")
  private String personalityArchetypeAr;

  @TableField("life_guidance_zh")
  @ApiModelProperty(value = "中文人生指引")
  private String lifeGuidanceZh;

  @TableField("life_guidance_en")
  @ApiModelProperty(value = "英文人生指引")
  private String lifeGuidanceEn;

  @TableField("life_guidance_ar")
  @ApiModelProperty(value = "阿拉伯语人生指引")
  private String lifeGuidanceAr;

  @TableField("healing_property_zh")
  @ApiModelProperty(value = "中文疗愈属性")
  private String healingPropertyZh;

  @TableField("healing_property_en")
  @ApiModelProperty(value = "英文疗愈属性")
  private String healingPropertyEn;

  @TableField("healing_property_ar")
  @ApiModelProperty(value = "阿拉伯语疗愈属性")
  private String healingPropertyAr;

  @TableField("relationship_impact_zh")
  @ApiModelProperty(value = "中文关系影响")
  private String relationshipImpactZh;

  @TableField("relationship_impact_en")
  @ApiModelProperty(value = "英文关系影响")
  private String relationshipImpactEn;

  @TableField("relationship_impact_ar")
  @ApiModelProperty(value = "阿拉伯语关系影响")
  private String relationshipImpactAr;

  @TableField("career_alignment_zh")
  @ApiModelProperty(value = "中文职业契合")
  private String careerAlignmentZh;

  @TableField("career_alignment_en")
  @ApiModelProperty(value = "英文职业契合")
  private String careerAlignmentEn;

  @TableField("career_alignment_ar")
  @ApiModelProperty(value = "阿拉伯语职业契合")
  private String careerAlignmentAr;

  // ==================== 图片资源属性 ====================
  //图像id
  @TableField("icon_id")
  @ApiModelProperty(value = "图标图片Id")
  @Size(max = 500, message = "图标图片Id长度不能超过500个字符")
  private String iconId;

  @TableField("symbol_image_id")
  @ApiModelProperty(value = "象征图id")
  @Size(max = 500, message = "象征图id长度不能超过500个字符")
  private String symbolImageId;

  @TableField("energy_image_id")
  @ApiModelProperty(value = "能量可视化图id")
  @Size(max = 500, message = "能量可视化图id长度不能超过500个字符")
  private String energyImageId;

  @TableField("application_image_id")
  @ApiModelProperty(value = "应用场景图id")
  @Size(max = 500, message = "应用场景图id长度不能超过500个字符")
  private String applicationImageId;

  @TableField("meditation_image_id")
  @ApiModelProperty(value = "冥想引导图id")
  @Size(max = 500, message = "冥想引导图id长度不能超过500个字符")
  private String meditationImageId;

  //图像地址
  @TableField("icon_url")
  @ApiModelProperty(value = "图标图片地址")
  @Size(max = 500, message = "图标图片地址长度不能超过500个字符")
  private String iconUrl;

  @TableField("symbol_image_url")
  @ApiModelProperty(value = "象征图地址")
  @Size(max = 500, message = "象征图地址长度不能超过500个字符")
  private String symbolImageUrl;

  @TableField("energy_image_url")
  @ApiModelProperty(value = "能量可视化图地址")
  @Size(max = 500, message = "能量可视化图地址长度不能超过500个字符")
  private String energyImageUrl;

  @TableField("application_image_url")
  @ApiModelProperty(value = "应用场景图地址")
  @Size(max = 500, message = "应用场景图地址长度不能超过500个字符")
  private String applicationImageUrl;

  @TableField("meditation_image_url")
  @ApiModelProperty(value = "冥想引导图URL")
  @Size(max = 500, message = "冥想引导图URL长度不能超过500个字符")
  private String meditationImageUrl;

  // ==================== SEO优化字段 ====================
  @TableField("meta_title_zh")
  @ApiModelProperty(value = "中文SEO标题")
  @Size(max = 200, message = "中文SEO标题长度不能超过200个字符")
  private String metaTitleZh;

  @TableField("meta_title_en")
  @ApiModelProperty(value = "英文SEO标题")
  @Size(max = 200, message = "英文SEO标题长度不能超过200个字符")
  private String metaTitleEn;

  @TableField("meta_title_ar")
  @ApiModelProperty(value = "阿拉伯语SEO标题")
  @Size(max = 200, message = "阿拉伯语SEO标题长度不能超过200个字符")
  private String metaTitleAr;

  @TableField("meta_description_zh")
  @ApiModelProperty(value = "中文SEO描述")
  private String metaDescriptionZh;

  @TableField("meta_description_en")
  @ApiModelProperty(value = "英文SEO描述")
  private String metaDescriptionEn;

  @TableField("meta_description_ar")
  @ApiModelProperty(value = "阿拉伯语SEO描述")
  private String metaDescriptionAr;

  @TableField("meta_keywords_zh")
  @ApiModelProperty(value = "中文SEO关键词")
  @Size(max = 500, message = "中文SEO关键词长度不能超过500个字符")
  private String metaKeywordsZh;

  @TableField("meta_keywords_en")
  @ApiModelProperty(value = "英文SEO关键词")
  @Size(max = 500, message = "英文SEO关键词长度不能超过500个字符")
  private String metaKeywordsEn;

  @TableField("meta_keywords_ar")
  @ApiModelProperty(value = "阿拉伯语SEO关键词")
  @Size(max = 500, message = "阿拉伯语SEO关键词长度不能超过500个字符")
  private String metaKeywordsAr;

  // ==================== 控制属性 ====================
  @TableField("sort_order")
  @ApiModelProperty(value = "排序值", example = "0", required = true)
  @NotNull(message = "排序值不能为空")
  private Integer sortOrder;

  @TableField("intensity_level")
  @ApiModelProperty(value = "强度等级(1-100)", example = "50")
  private Integer intensityLevel;

  @TableField("popularity_score")
  @ApiModelProperty(value = "受欢迎度评分", example = "0")
  private Integer popularityScore;

  @TableField("is_active")
  @ApiModelProperty(value = "状态:0-冻结,1-启用,2-删除", example = "1", required = true)
  @NotNull(message = "状态不能为空")
  private Integer isActive;

  @TableField("show_in_navigation")
  @ApiModelProperty(value = "是否在导航显示", example = "1", required = true)
  @NotNull(message = "是否在导航显示不能为空")
  private Integer showInNavigation;

  @TableField("show_in_filter")
  @ApiModelProperty(value = "是否在筛选器显示", example = "1", required = true)
  @NotNull(message = "是否在筛选器显示不能为空")
  private Integer showInFilter;

  @TableField("is_featured")
  @ApiModelProperty(value = "是否推荐", example = "0", required = true)
  @NotNull(message = "是否推荐不能为空")
  private Integer isFeatured;

  // ==================== 审计字段 ====================
  @TableField("created_time")
  @CreatedDate
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createdTime;

  @TableField("updated_time")
  @LastModifiedDate
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updatedTime;
}
