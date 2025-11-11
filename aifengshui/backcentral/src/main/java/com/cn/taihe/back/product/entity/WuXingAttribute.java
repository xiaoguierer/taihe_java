package com.cn.taihe.back.product.entity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 五行哲学文化属性实体类
 *
 * @author system
 * @since 2025-02-20
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "wu_xing_attribute")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "WuXingAttribute", description = "五行哲学文化属性实体")
public class WuXingAttribute implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @TableId(value = "id")
  @ApiModelProperty(value = "主键ID", example = "1234567890abcdef")
  private String id;

  @Column(name = "element_key", nullable = false, length = 20)
  @TableField("element_key")
  @ApiModelProperty(value = "五行元素键名", required = true, example = "metal")
  private String elementKey;

  @Column(name = "element_code", nullable = false, length = 10)
  @TableField("element_code")
  @ApiModelProperty(value = "五行代码", required = true, example = "JIN")
  private String elementCode;

  @Column(name = "element_category", nullable = false, length = 20)
  @TableField("element_category")
  @ApiModelProperty(value = "元素分类:basic五行,extended扩展,combined组合", required = true, example = "basic")
  private String elementCategory;

  @Column(name = "element_name_en", nullable = false, length = 50)
  @TableField("element_name_en")
  @ApiModelProperty(value = "英文名称", required = true, example = "Metal")
  private String elementNameEn;

  @Column(name = "element_name_zh", nullable = false, length = 50)
  @TableField("element_name_zh")
  @ApiModelProperty(value = "中文名称", required = true, example = "金")
  private String elementNameZh;

  @Column(name = "element_name_ar", length = 50)
  @TableField("element_name_ar")
  @ApiModelProperty(value = "阿拉伯语名称", example = "معدن")
  private String elementNameAr;

  @Column(name = "symbol_character", nullable = false, length = 10)
  @TableField("symbol_character")
  @ApiModelProperty(value = "象征字符", required = true, example = "金")
  private String symbolCharacter;

  @Column(name = "symbol_color", nullable = false, length = 20)
  @TableField("symbol_color")
  @ApiModelProperty(value = "象征颜色代码", required = true, example = "#FFD700")
  private String symbolColor;

  @Column(name = "symbol_color_gradient", length = 100)
  @TableField("symbol_color_gradient")
  @ApiModelProperty(value = "渐变色系", example = "linear-gradient(90deg, #FFD700, #FFA500)")
  private String symbolColorGradient;

  @Column(name = "symbol_direction", length = 20)
  @TableField("symbol_direction")
  @ApiModelProperty(value = "象征方位", example = "西")
  private String symbolDirection;

  @Column(name = "symbol_season", length = 20)
  @TableField("symbol_season")
  @ApiModelProperty(value = "象征季节", example = "秋")
  private String symbolSeason;

  @Column(name = "symbol_time", length = 20)
  @TableField("symbol_time")
  @ApiModelProperty(value = "象征时辰", example = "酉时")
  private String symbolTime;

  @Column(name = "symbol_planet", length = 20)
  @TableField("symbol_planet")
  @ApiModelProperty(value = "象征行星", example = "金星")
  private String symbolPlanet;

  @Column(name = "symbol_weather", length = 20)
  @TableField("symbol_weather")
  @ApiModelProperty(value = "象征气候", example = "干燥")
  private String symbolWeather;

  @Column(name = "symbol_landform", length = 20)
  @TableField("symbol_landform")
  @ApiModelProperty(value = "象征地貌", example = "山脉")
  private String symbolLandform;

  @Column(name = "symbol_animal", length = 20)
  @TableField("symbol_animal")
  @ApiModelProperty(value = "象征动物", example = "虎")
  private String symbolAnimal;

  @Column(name = "symbol_plant", length = 20)
  @TableField("symbol_plant")
  @ApiModelProperty(value = "象征植物", example = "菊花")
  private String symbolPlant;

  @Column(name = "symbol_organ", length = 20)
  @TableField("symbol_organ")
  @ApiModelProperty(value = "象征脏腑", example = "肺")
  private String symbolOrgan;

  @Column(name = "symbol_sense", length = 20)
  @TableField("symbol_sense")
  @ApiModelProperty(value = "象征感官", example = "鼻")
  private String symbolSense;

  @Column(name = "symbol_tissue", length = 20)
  @TableField("symbol_tissue")
  @ApiModelProperty(value = "象征组织", example = "皮毛")
  private String symbolTissue;

  @Column(name = "symbol_emotion", length = 20)
  @TableField("symbol_emotion")
  @ApiModelProperty(value = "象征情绪", example = "悲")
  private String symbolEmotion;

  @Column(name = "symbol_sound", length = 20)
  @TableField("symbol_sound")
  @ApiModelProperty(value = "象征声音", example = "商")
  private String symbolSound;

  @Column(name = "symbol_taste", length = 20)
  @TableField("symbol_taste")
  @ApiModelProperty(value = "象征味道", example = "辛")
  private String symbolTaste;

  @Column(name = "symbol_virtue", length = 20)
  @TableField("symbol_virtue")
  @ApiModelProperty(value = "象征品德", example = "义")
  private String symbolVirtue;

  @Column(name = "symbol_career", length = 20)
  @TableField("symbol_career")
  @ApiModelProperty(value = "象征职业", example = "金属加工")
  private String symbolCareer;

  @Column(name = "symbol_number")
  @TableField("symbol_number")
  @ApiModelProperty(value = "象征数字", example = "9")
  private Integer symbolNumber;

  @Column(name = "symbol_music", length = 20)
  @TableField("symbol_music")
  @ApiModelProperty(value = "象征音律", example = "商音")
  private String symbolMusic;

  @Column(name = "philosophy_meaning_zh")
  @TableField("philosophy_meaning_zh")
  @ApiModelProperty(value = "中文哲学本义")
  private String philosophyMeaningZh;

  @Column(name = "philosophy_meaning_en")
  @TableField("philosophy_meaning_en")
  @ApiModelProperty(value = "英文哲学本义")
  private String philosophyMeaningEn;

  @Column(name = "cosmology_meaning_zh")
  @TableField("cosmology_meaning_zh")
  @ApiModelProperty(value = "中文宇宙观含义")
  private String cosmologyMeaningZh;

  @Column(name = "cosmology_meaning_en")
  @TableField("cosmology_meaning_en")
  @ApiModelProperty(value = "英文宇宙观含义")
  private String cosmologyMeaningEn;

  @Column(name = "life_philosophy_zh")
  @TableField("life_philosophy_zh")
  @ApiModelProperty(value = "中文生命哲学")
  private String lifePhilosophyZh;

  @Column(name = "life_philosophy_en")
  @TableField("life_philosophy_en")
  @ApiModelProperty(value = "英文生命哲学")
  private String lifePhilosophyEn;

  @Column(name = "change_principle_zh")
  @TableField("change_principle_zh")
  @ApiModelProperty(value = "中文变化法则")
  private String changePrincipleZh;

  @Column(name = "change_principle_en")
  @TableField("change_principle_en")
  @ApiModelProperty(value = "英文变化法则")
  private String changePrincipleEn;

  @Column(name = "cultural_symbolism_zh")
  @TableField("cultural_symbolism_zh")
  @ApiModelProperty(value = "中文文化象征")
  private String culturalSymbolismZh;

  @Column(name = "cultural_symbolism_en")
  @TableField("cultural_symbolism_en")
  @ApiModelProperty(value = "英文文化象征")
  private String culturalSymbolismEn;

  @Column(name = "mythological_association_zh")
  @TableField("mythological_association_zh")
  @ApiModelProperty(value = "中文神话关联")
  private String mythologicalAssociationZh;

  @Column(name = "mythological_association_en")
  @TableField("mythological_association_en")
  @ApiModelProperty(value = "英文神话关联")
  private String mythologicalAssociationEn;

  @Column(name = "historical_reference_zh")
  @TableField("historical_reference_zh")
  @ApiModelProperty(value = "中文历史典故")
  private String historicalReferenceZh;

  @Column(name = "historical_reference_en")
  @TableField("historical_reference_en")
  @ApiModelProperty(value = "英文历史典故")
  private String historicalReferenceEn;

  @Column(name = "artistic_expression_zh")
  @TableField("artistic_expression_zh")
  @ApiModelProperty(value = "中文艺术表现")
  private String artisticExpressionZh;

  @Column(name = "artistic_expression_en")
  @TableField("artistic_expression_en")
  @ApiModelProperty(value = "英文艺术表现")
  private String artisticExpressionEn;

  @Column(name = "energy_essence_zh")
  @TableField("energy_essence_zh")
  @ApiModelProperty(value = "中文能量本质")
  private String energyEssenceZh;

  @Column(name = "energy_essence_en")
  @TableField("energy_essence_en")
  @ApiModelProperty(value = "英文能量本质")
  private String energyEssenceEn;

  @Column(name = "energy_manifestation_zh")
  @TableField("energy_manifestation_zh")
  @ApiModelProperty(value = "中文能量显现")
  private String energyManifestationZh;

  @Column(name = "energy_manifestation_en")
  @TableField("energy_manifestation_en")
  @ApiModelProperty(value = "英文能量显现")
  private String energyManifestationEn;

  @Column(name = "spiritual_meaning_zh")
  @TableField("spiritual_meaning_zh")
  @ApiModelProperty(value = "中文灵性意义")
  private String spiritualMeaningZh;

  @Column(name = "spiritual_meaning_en")
  @TableField("spiritual_meaning_en")
  @ApiModelProperty(value = "英文灵性意义")
  private String spiritualMeaningEn;

  @Column(name = "personality_archetype_zh")
  @TableField("personality_archetype_zh")
  @ApiModelProperty(value = "中文人格原型")
  private String personalityArchetypeZh;

  @Column(name = "personality_archetype_en")
  @TableField("personality_archetype_en")
  @ApiModelProperty(value = "英文人格原型")
  private String personalityArchetypeEn;

  @Column(name = "life_guidance_zh")
  @TableField("life_guidance_zh")
  @ApiModelProperty(value = "中文人生指引")
  private String lifeGuidanceZh;

  @Column(name = "life_guidance_en")
  @TableField("life_guidance_en")
  @ApiModelProperty(value = "英文人生指引")
  private String lifeGuidanceEn;

  @Column(name = "healing_property_zh")
  @TableField("healing_property_zh")
  @ApiModelProperty(value = "中文疗愈属性")
  private String healingPropertyZh;

  @Column(name = "healing_property_en")
  @TableField("healing_property_en")
  @ApiModelProperty(value = "英文疗愈属性")
  private String healingPropertyEn;

  @Column(name = "generates_element", length = 20)
  @TableField("generates_element")
  @ApiModelProperty(value = "相生元素", example = "water")
  private String generatesElement;

  @Column(name = "generated_by_element", length = 20)
  @TableField("generated_by_element")
  @ApiModelProperty(value = "被生元素", example = "earth")
  private String generatedByElement;

  @Column(name = "restrains_element", length = 20)
  @TableField("restrains_element")
  @ApiModelProperty(value = "相克元素", example = "wood")
  private String restrainsElement;

  @Column(name = "restrained_by_element", length = 20)
  @TableField("restrained_by_element")
  @ApiModelProperty(value = "被克元素", example = "fire")
  private String restrainedByElement;

  @Column(name = "relationship_meaning_zh")
  @TableField("relationship_meaning_zh")
  @ApiModelProperty(value = "中文关系意义")
  private String relationshipMeaningZh;

  @Column(name = "relationship_meaning_en")
  @TableField("relationship_meaning_en")
  @ApiModelProperty(value = "英文关系意义")
  private String relationshipMeaningEn;
  //图像
  @Column(name = "symbol_icon_url", length = 500)
  @TableField("symbol_icon_url")
  @ApiModelProperty(value = "象征图标URL", example = "https://example.com/metal.png")
  private String symbolIconUrl;

  @Column(name = "philosophy_image_url", length = 500)
  @TableField("philosophy_image_url")
  @ApiModelProperty(value = "哲学图解URL", example = "https://example.com/metal_philosophy.png")
  private String philosophyImageUrl;

  @Column(name = "energy_flow_image_url", length = 500)
  @TableField("energy_flow_image_url")
  @ApiModelProperty(value = "能量流动图URL", example = "https://example.com/metal_energy.png")
  private String energyFlowImageUrl;
  // ==================== 图片资源属性 ====================
  @Column(name = "symbol_icon_id")
  @TableField("symbol_icon_id")
  @ApiModelProperty(value = "象征图标ID")
  @Size(max = 50, message = "象征图标ID长度不能超过50个字符")
  private String symbolIconId;

  @Column(name = "philosophy_image_id")
  @TableField("philosophy_image_id")
  @ApiModelProperty(value = "哲学原理图ID")
  @Size(max = 50, message = "哲学原理图ID长度不能超过50个字符")
  private String philosophyImageId;

  @Column(name = "energy_flow_image_id")
  @TableField("energy_flow_image_id")
  @ApiModelProperty(value = "能量流动图ID")
  @Size(max = 50, message = "能量流动图ID长度不能超过50个字符")
  private String energyFlowImageId;


  @Column(name = "sort_order", nullable = false)
  @TableField("sort_order")
  @ApiModelProperty(value = "排序值", required = true, example = "1")
  private Integer sortOrder;

  @Column(name = "element_tier", nullable = false)
  @TableField("element_tier")
  @ApiModelProperty(value = "元素层级", required = true, example = "1")
  private Integer elementTier;

  @Column(name = "is_active", nullable = false)
  @TableField("is_active")
  @ApiModelProperty(value = "是否启用(0-冻结 1-启用)", required = true, example = "1")
  private Integer isActive;

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @TableField(value = "created_time", fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createdTime;

  @LastModifiedDate
  @Column(name = "updated_time", nullable = false)
  @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updatedTime;
}
