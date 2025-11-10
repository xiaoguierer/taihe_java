package com.cn.taihe.back.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品SPU新增DTO
 *
 * @author system
 * @since 2025-01-01
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductSpuCreateDTO", description = "商品SPU新增DTO")
public class ProductSpuCreateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "SPU编码不能为空")
  @Length(max = 50, message = "SPU编码长度不能超过50个字符")
  @ApiModelProperty(value = "SPU编码", required = true)
  private String spuCode;

  @NotBlank(message = "英文产品名称不能为空")
  @Length(max = 200, message = "英文产品名称长度不能超过200个字符")
  @ApiModelProperty(value = "英文产品名称", required = true)
  private String productNameEn;

  @NotBlank(message = "中文产品名称不能为空")
  @Length(max = 200, message = "中文产品名称长度不能超过200个字符")
  @ApiModelProperty(value = "中文产品名称", required = true)
  private String productNameZh;

  @Length(max = 200, message = "阿拉伯语产品名称长度不能超过200个字符")
  @ApiModelProperty(value = "阿拉伯语产品名称")
  private String productNameAr;

  @Length(max = 500, message = "英文简短描述长度不能超过500个字符")
  @ApiModelProperty(value = "英文简短描述")
  private String shortDescriptionEn;

  @Length(max = 500, message = "中文简短描述长度不能超过500个字符")
  @ApiModelProperty(value = "中文简短描述")
  private String shortDescriptionZh;

  @Length(max = 500, message = "阿拉伯语简短描述长度不能超过500个字符")
  @ApiModelProperty(value = "阿拉伯语简短描述")
  private String shortDescriptionAr;

  @ApiModelProperty(value = "英文详细描述")
  private String fullDescriptionEn;

  @ApiModelProperty(value = "中文详细描述")
  private String fullDescriptionZh;

  @ApiModelProperty(value = "阿拉伯语详细描述")
  private String fullDescriptionAr;

  @ApiModelProperty(value = "英文设计理念")
  private String designConceptEn;

  @ApiModelProperty(value = "中文设计理念")
  private String designConceptZh;

  @ApiModelProperty(value = "阿拉伯语设计理念")
  private String designConceptAr;

  @ApiModelProperty(value = "英文用途说明")
  private String intendedUsageEn;

  @ApiModelProperty(value = "中文用途说明")
  private String intendedUsageZh;

  @ApiModelProperty(value = "阿拉伯语用途说明")
  private String intendedUsageAr;

  @ApiModelProperty(value = "英文情感目的")
  private String emotionalPurposeEn;

  @ApiModelProperty(value = "中文情感目的")
  private String emotionalPurposeZh;

  @ApiModelProperty(value = "阿拉伯语情感目的")
  private String emotionalPurposeAr;

  @ApiModelProperty(value = "英文灵性意义")
  private String spiritualSignificanceEn;

  @ApiModelProperty(value = "中文灵性意义")
  private String spiritualSignificanceZh;

  @ApiModelProperty(value = "阿拉伯语灵性意义")
  private String spiritualSignificanceAr;

  @ApiModelProperty(value = "英文能量属性")
  private String energyPropertiesEn;

  @ApiModelProperty(value = "中文能量属性")
  private String energyPropertiesZh;

  @ApiModelProperty(value = "阿拉伯语能量属性")
  private String energyPropertiesAr;

  @Length(max = 20, message = "主要五行元素长度不能超过20个字符")
  @ApiModelProperty(value = "主要五行元素")
  private String primaryElement;

  @ApiModelProperty(value = "五行组合配置JSON")
  private String elementCombination;

  @Range(min = 1, max = 100, message = "默认能量强度必须在1-100之间")
  @ApiModelProperty(value = "默认能量强度(1-100)")
  private Integer energyIntensityDefault;

  @ApiModelProperty(value = "材质标准JSON")
  private String materialStandards;

  @ApiModelProperty(value = "工艺标准JSON")
  private String craftsmanshipStandards;

  @ApiModelProperty(value = "质量标准JSON")
  private String qualityStandards;

  @ApiModelProperty(value = "英文生产指南")
  private String productionGuidelinesEn;

  @ApiModelProperty(value = "中文生产指南")
  private String productionGuidelinesZh;

  @ApiModelProperty(value = "阿拉伯语生产指南")
  private String productionGuidelinesAr;

  @ApiModelProperty(value = "变体定义JSON")
  private String variantDefinition;

  @ApiModelProperty(value = "定制选项JSON")
  private String customizationOptions;

  @ApiModelProperty(value = "生产周期(天)")
  private Integer productionLeadTime;

  @ApiModelProperty(value = "主图ID")
  private Long mainImageId;

  @ApiModelProperty(value = "概念图ID")
  private Long conceptImageId;

  @ApiModelProperty(value = "设计图ID")
  private Long designImageId;

  @ApiModelProperty(value = "原型图ID")
  private Long prototypeImageId;

  @ApiModelProperty(value = "使用场景图ID")
  private Long usageImageId;

  @ApiModelProperty(value = "技术图纸ID")
  private Long technicalImageId;

  @ApiModelProperty(value = "主图url")
  private Long mainImageUrl;

  @ApiModelProperty(value = "概念图url")
  private Long conceptImageUrl;

  @ApiModelProperty(value = "设计图url")
  private Long designImageUrl;

  @ApiModelProperty(value = "原型图url")
  private Long prototypeImageUrl;

  @ApiModelProperty(value = "使用场景图url")
  private Long usageImageUrl;

  @ApiModelProperty(value = "技术图纸url")
  private Long technicalImageUrl;

  @ApiModelProperty(value = "排序值")
  private Integer sortOrder;

  @ApiModelProperty(value = "是否推荐")
  private Boolean isFeatured;

  @ApiModelProperty(value = "是否新品")
  private Boolean isNewArrival;

  @ApiModelProperty(value = "是否AI设计")
  private Boolean isAiDesigned;

  @Range(min = 1, max = 100, message = "AI设计评分必须在1-100之间")
  @ApiModelProperty(value = "AI设计评分(1-100)")
  private Integer aiDesignScore;

  @ApiModelProperty(value = "是否启用")
  private Boolean isActive;

  @Length(max = 200, message = "英文SEO标题长度不能超过200个字符")
  @ApiModelProperty(value = "英文SEO标题")
  private String metaTitleEn;

  @Length(max = 200, message = "中文SEO标题长度不能超过200个字符")
  @ApiModelProperty(value = "中文SEO标题")
  private String metaTitleZh;

  @Length(max = 200, message = "阿拉伯语SEO标题长度不能超过200个字符")
  @ApiModelProperty(value = "阿拉伯语SEO标题")
  private String metaTitleAr;

  @ApiModelProperty(value = "英文SEO描述")
  private String metaDescriptionEn;

  @ApiModelProperty(value = "中文SEO描述")
  private String metaDescriptionZh;

  @ApiModelProperty(value = "阿拉伯语SEO描述")
  private String metaDescriptionAr;

  @Length(max = 500, message = "英文SEO关键词长度不能超过500个字符")
  @ApiModelProperty(value = "英文SEO关键词")
  private String metaKeywordsEn;

  @Length(max = 500, message = "中文SEO关键词长度不能超过500个字符")
  @ApiModelProperty(value = "中文SEO关键词")
  private String metaKeywordsZh;

  @Length(max = 500, message = "阿拉伯语SEO关键词长度不能超过500个字符")
  @ApiModelProperty(value = "阿拉伯语SEO关键词")
  private String metaKeywordsAr;

  @NotNull(message = "创建人ID不能为空")
  @ApiModelProperty(value = "创建人ID", required = true)
  private Long createdBy;

  @NotNull(message = "更新人ID不能为空")
  @ApiModelProperty(value = "更新人ID", required = true)
  private Long updatedBy;
}
