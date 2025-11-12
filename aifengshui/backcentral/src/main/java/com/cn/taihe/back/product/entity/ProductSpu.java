package com.cn.taihe.back.product.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品SPU表(关联图片ID) 实体类
 *
 * @author system
 * @since 2025-01-01
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "product_spu")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@TableName("product_spu")
@ApiModel(value = "ProductSpu", description = "商品SPU表(关联图片ID)")
public class ProductSpu implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键ID", example = "1234567890abcdef")
  private String id;

  @TableField("spu_code")
  @ApiModelProperty(value = "SPU编码", required = true)
  private String spuCode;

  @TableField("product_name_en")
  @ApiModelProperty(value = "英文产品名称", required = true)
  private String productNameEn;

  @TableField("product_name_zh")
  @ApiModelProperty(value = "中文产品名称", required = true)
  private String productNameZh;

  @TableField("product_name_ar")
  @ApiModelProperty(value = "阿拉伯语产品名称")
  private String productNameAr;

  @TableField("short_description_en")
  @ApiModelProperty(value = "英文简短描述")
  private String shortDescriptionEn;

  @TableField("short_description_zh")
  @ApiModelProperty(value = "中文简短描述")
  private String shortDescriptionZh;

  @TableField("short_description_ar")
  @ApiModelProperty(value = "阿拉伯语简短描述")
  private String shortDescriptionAr;

  @TableField("full_description_en")
  @ApiModelProperty(value = "英文详细描述")
  private String fullDescriptionEn;

  @TableField("full_description_zh")
  @ApiModelProperty(value = "中文详细描述")
  private String fullDescriptionZh;

  @TableField("full_description_ar")
  @ApiModelProperty(value = "阿拉伯语详细描述")
  private String fullDescriptionAr;

  @TableField("design_concept_en")
  @ApiModelProperty(value = "英文设计理念")
  private String designConceptEn;

  @TableField("design_concept_zh")
  @ApiModelProperty(value = "中文设计理念")
  private String designConceptZh;

  @TableField("design_concept_ar")
  @ApiModelProperty(value = "阿拉伯语设计理念")
  private String designConceptAr;

  @TableField("intended_usage_en")
  @ApiModelProperty(value = "英文用途说明")
  private String intendedUsageEn;

  @TableField("intended_usage_zh")
  @ApiModelProperty(value = "中文用途说明")
  private String intendedUsageZh;

  @TableField("intended_usage_ar")
  @ApiModelProperty(value = "阿拉伯语用途说明")
  private String intendedUsageAr;

  @TableField("emotional_purpose_en")
  @ApiModelProperty(value = "英文情感目的")
  private String emotionalPurposeEn;

  @TableField("emotional_purpose_zh")
  @ApiModelProperty(value = "中文情感目的")
  private String emotionalPurposeZh;

  @TableField("emotional_purpose_ar")
  @ApiModelProperty(value = "阿拉伯语情感目的")
  private String emotionalPurposeAr;

  @TableField("spiritual_significance_en")
  @ApiModelProperty(value = "英文灵性意义")
  private String spiritualSignificanceEn;

  @TableField("spiritual_significance_zh")
  @ApiModelProperty(value = "中文灵性意义")
  private String spiritualSignificanceZh;

  @TableField("spiritual_significance_ar")
  @ApiModelProperty(value = "阿拉伯语灵性意义")
  private String spiritualSignificanceAr;

  @TableField("energy_properties_en")
  @ApiModelProperty(value = "英文能量属性")
  private String energyPropertiesEn;

  @TableField("energy_properties_zh")
  @ApiModelProperty(value = "中文能量属性")
  private String energyPropertiesZh;

  @TableField("energy_properties_ar")
  @ApiModelProperty(value = "阿拉伯语能量属性")
  private String energyPropertiesAr;

  @TableField("primary_element")
  @ApiModelProperty(value = "主要五行元素")
  private String primaryElement;

  @TableField("element_combination")
  @ApiModelProperty(value = "五行组合配置")
  private String elementCombination;

  @TableField("energy_intensity_default")
  @ApiModelProperty(value = "默认能量强度(1-100)")
  private Integer energyIntensityDefault;

  @TableField("material_standards")
  @ApiModelProperty(value = "材质标准JSON")
  private String materialStandards;

  @TableField("craftsmanship_standards")
  @ApiModelProperty(value = "工艺标准JSON")
  private String craftsmanshipStandards;

  @TableField("quality_standards")
  @ApiModelProperty(value = "质量标准JSON")
  private String qualityStandards;

  @TableField("production_guidelines_en")
  @ApiModelProperty(value = "英文生产指南")
  private String productionGuidelinesEn;

  @TableField("production_guidelines_zh")
  @ApiModelProperty(value = "中文生产指南")
  private String productionGuidelinesZh;

  @TableField("production_guidelines_ar")
  @ApiModelProperty(value = "阿拉伯语生产指南")
  private String productionGuidelinesAr;

  @TableField("variant_definition")
  @ApiModelProperty(value = "变体定义JSON")
  private String variantDefinition;

  @TableField("customization_options")
  @ApiModelProperty(value = "定制选项JSON")
  private String customizationOptions;

  @TableField("production_lead_time")
  @ApiModelProperty(value = "生产周期(天)")
  private Integer productionLeadTime;

  @TableField("main_image_id")
  @ApiModelProperty(value = "主图ID")
  private String mainImageId;

  @TableField("concept_image_id")
  @ApiModelProperty(value = "概念图ID")
  private String conceptImageId;

  @TableField("design_image_id")
  @ApiModelProperty(value = "设计图ID")
  private String designImageId;

  @TableField("prototype_image_id")
  @ApiModelProperty(value = "原型图ID")
  private String prototypeImageId;

  @TableField("usage_image_id")
  @ApiModelProperty(value = "使用场景图ID")
  private String usageImageId;

  @TableField("technical_image_id")
  @ApiModelProperty(value = "技术图纸ID")
  private String technicalImageId;

  @TableField("main_image_url")
  @ApiModelProperty(value = "主图url")
  private String mainImageUrl;

  @TableField("concept_image_url")
  @ApiModelProperty(value = "概念图url")
  private String conceptImageUrl;

  @TableField("design_image_url")
  @ApiModelProperty(value = "设计图url")
  private String designImageUrl;

  @TableField("prototype_image_url")
  @ApiModelProperty(value = "原型图url")
  private String prototypeImageUrl;

  @TableField("usage_image_url")
  @ApiModelProperty(value = "使用场景图url")
  private String usageImageUrl;

  @TableField("technical_image_url")
  @ApiModelProperty(value = "技术图纸url")
  private String technicalImageUrl;

  @TableField("sort_order")
  @ApiModelProperty(value = "排序值")
  private Integer sortOrder;

  @TableField("is_featured")
  @ApiModelProperty(value = "是否推荐")
  private Boolean isFeatured;

  @TableField("is_new_arrival")
  @ApiModelProperty(value = "是否新品")
  private Boolean isNewArrival;

  @TableField("is_ai_designed")
  @ApiModelProperty(value = "是否AI设计")
  private Boolean isAiDesigned;

  @TableField("ai_design_score")
  @ApiModelProperty(value = "AI设计评分(1-100)")
  private Integer aiDesignScore;

  @TableField("is_active")
  @ApiModelProperty(value = "是否启用")
  private Boolean isActive;

  @TableField("meta_title_en")
  @ApiModelProperty(value = "英文SEO标题")
  private String metaTitleEn;

  @TableField("meta_title_zh")
  @ApiModelProperty(value = "中文SEO标题")
  private String metaTitleZh;

  @TableField("meta_title_ar")
  @ApiModelProperty(value = "阿拉伯语SEO标题")
  private String metaTitleAr;

  @TableField("meta_description_en")
  @ApiModelProperty(value = "英文SEO描述")
  private String metaDescriptionEn;

  @TableField("meta_description_zh")
  @ApiModelProperty(value = "中文SEO描述")
  private String metaDescriptionZh;

  @TableField("meta_description_ar")
  @ApiModelProperty(value = "阿拉伯语SEO描述")
  private String metaDescriptionAr;

  @TableField("meta_keywords_en")
  @ApiModelProperty(value = "英文SEO关键词")
  private String metaKeywordsEn;

  @TableField("meta_keywords_zh")
  @ApiModelProperty(value = "中文SEO关键词")
  private String metaKeywordsZh;

  @TableField("meta_keywords_ar")
  @ApiModelProperty(value = "阿拉伯语SEO关键词")
  private String metaKeywordsAr;

  @CreatedBy
  @TableField("created_by")
  @ApiModelProperty(value = "创建人ID")
  private Long createdBy;

  @LastModifiedBy
  @TableField("updated_by")
  @ApiModelProperty(value = "更新人ID")
  private Long updatedBy;

  @CreatedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField("created_time")
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createdTime;

  @LastModifiedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField("updated_time")
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updatedTime;
}
