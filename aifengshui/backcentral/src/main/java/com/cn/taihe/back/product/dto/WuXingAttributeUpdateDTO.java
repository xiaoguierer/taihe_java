package com.cn.taihe.back.product.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 五行属性更新DTO
 *
 * @author system
 * @since 2025-02-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "WuXingAttributeUpdateDTO", description = "五行属性更新参数")
public class WuXingAttributeUpdateDTO {

  @NotBlank(message = "主键ID不能为空")
  @ApiModelProperty(value = "主键ID", required = true, example = "1234567890abcdef")
  private String id;

  @NotBlank(message = "五行元素键名不能为空")
  @ApiModelProperty(value = "五行元素键名", required = true, example = "metal")
  private String elementKey;

  @ApiModelProperty(value = "五行代码", example = "JIN")
  private String elementCode;

  @ApiModelProperty(value = "元素分类:basic五行,extended扩展,combined组合", example = "basic")
  private String elementCategory;

  @ApiModelProperty(value = "英文名称", example = "Metal")
  private String elementNameEn;

  @ApiModelProperty(value = "中文名称", example = "金")
  private String elementNameZh;

  @ApiModelProperty(value = "阿拉伯语名称", example = "معدن")
  private String elementNameAr;

  @ApiModelProperty(value = "象征字符", example = "金")
  private String symbolCharacter;

  @ApiModelProperty(value = "象征颜色代码", example = "#FFD700")
  private String symbolColor;

  @ApiModelProperty(value = "渐变色系", example = "linear-gradient(90deg, #FFD700, #FFA500)")
  private String symbolColorGradient;

  @ApiModelProperty(value = "象征方位", example = "西")
  private String symbolDirection;

  @ApiModelProperty(value = "象征季节", example = "秋")
  private String symbolSeason;

  @ApiModelProperty(value = "象征时辰", example = "酉时")
  private String symbolTime;

  @ApiModelProperty(value = "象征行星", example = "金星")
  private String symbolPlanet;

  @ApiModelProperty(value = "象征气候", example = "干燥")
  private String symbolWeather;

  @ApiModelProperty(value = "象征地貌", example = "山脉")
  private String symbolLandform;

  @ApiModelProperty(value = "象征动物", example = "虎")
  private String symbolAnimal;

  @ApiModelProperty(value = "象征植物", example = "菊花")
  private String symbolPlant;

  @ApiModelProperty(value = "象征脏腑", example = "肺")
  private String symbolOrgan;

  @ApiModelProperty(value = "象征感官", example = "鼻")
  private String symbolSense;

  @ApiModelProperty(value = "象征组织", example = "皮毛")
  private String symbolTissue;

  @ApiModelProperty(value = "象征情绪", example = "悲")
  private String symbolEmotion;

  @ApiModelProperty(value = "象征声音", example = "商")
  private String symbolSound;

  @ApiModelProperty(value = "象征味道", example = "辛")
  private String symbolTaste;

  @ApiModelProperty(value = "象征品德", example = "义")
  private String symbolVirtue;

  @ApiModelProperty(value = "象征职业", example = "金属加工")
  private String symbolCareer;

  @ApiModelProperty(value = "象征数字", example = "9")
  private Integer symbolNumber;

  @ApiModelProperty(value = "象征音律", example = "商音")
  private String symbolMusic;

  @ApiModelProperty(value = "中文哲学本义")
  private String philosophyMeaningZh;

  @ApiModelProperty(value = "英文哲学本义")
  private String philosophyMeaningEn;

  @ApiModelProperty(value = "中文宇宙观含义")
  private String cosmologyMeaningZh;

  @ApiModelProperty(value = "英文宇宙观含义")
  private String cosmologyMeaningEn;

  @ApiModelProperty(value = "中文生命哲学")
  private String lifePhilosophyZh;

  @ApiModelProperty(value = "英文生命哲学")
  private String lifePhilosophyEn;

  @ApiModelProperty(value = "中文变化法则")
  private String changePrincipleZh;

  @ApiModelProperty(value = "英文变化法则")
  private String changePrincipleEn;

  @ApiModelProperty(value = "中文文化象征")
  private String culturalSymbolismZh;

  @ApiModelProperty(value = "英文文化象征")
  private String culturalSymbolismEn;

  @ApiModelProperty(value = "中文神话关联")
  private String mythologicalAssociationZh;

  @ApiModelProperty(value = "英文神话关联")
  private String mythologicalAssociationEn;

  @ApiModelProperty(value = "中文历史典故")
  private String historicalReferenceZh;

  @ApiModelProperty(value = "英文历史典故")
  private String historicalReferenceEn;

  @ApiModelProperty(value = "中文艺术表现")
  private String artisticExpressionZh;

  @ApiModelProperty(value = "英文艺术表现")
  private String artisticExpressionEn;

  @ApiModelProperty(value = "中文能量本质")
  private String energyEssenceZh;

  @ApiModelProperty(value = "英文能量本质")
  private String energyEssenceEn;

  @ApiModelProperty(value = "中文能量显现")
  private String energyManifestationZh;

  @ApiModelProperty(value = "英文能量显现")
  private String energyManifestationEn;

  @ApiModelProperty(value = "中文灵性意义")
  private String spiritualMeaningZh;

  @ApiModelProperty(value = "英文灵性意义")
  private String spiritualMeaningEn;

  @ApiModelProperty(value = "中文人格原型")
  private String personalityArchetypeZh;

  @ApiModelProperty(value = "英文人格原型")
  private String personalityArchetypeEn;

  @ApiModelProperty(value = "中文人生指引")
  private String lifeGuidanceZh;

  @ApiModelProperty(value = "英文人生指引")
  private String lifeGuidanceEn;

  @ApiModelProperty(value = "中文疗愈属性")
  private String healingPropertyZh;

  @ApiModelProperty(value = "英文疗愈属性")
  private String healingPropertyEn;

  @ApiModelProperty(value = "相生元素", example = "water")
  private String generatesElement;

  @ApiModelProperty(value = "被生元素", example = "earth")
  private String generatedByElement;

  @ApiModelProperty(value = "相克元素", example = "wood")
  private String restrainsElement;

  @ApiModelProperty(value = "被克元素", example = "fire")
  private String restrainedByElement;

  @ApiModelProperty(value = "中文关系意义")
  private String relationshipMeaningZh;

  @ApiModelProperty(value = "英文关系意义")
  private String relationshipMeaningEn;

  @ApiModelProperty(value = "象征图标URL", example = "https://example.com/metal.png")
  private String symbolIconUrl;

  @ApiModelProperty(value = "哲学图解URL", example = "https://example.com/metal_philosophy.png")
  private String philosophyImageUrl;

  @ApiModelProperty(value = "能量流动图URL", example = "https://example.com/metal_energy.png")
  private String energyFlowImageUrl;

  @ApiModelProperty(value = "象征图标ID")
  private String symbolIconId;

  @ApiModelProperty(value = "哲学原理图ID")
  private String philosophyImageId;

  @ApiModelProperty(value = "能量流动图ID")
  private String energyFlowImageId;

  @ApiModelProperty(value = "排序值", example = "1")
  private Integer sortOrder;

  @ApiModelProperty(value = "元素层级", example = "1")
  private Integer elementTier;

  @ApiModelProperty(value = "是否启用(0-冻结 1-启用)", example = "1")
  private Integer isActive;
}
