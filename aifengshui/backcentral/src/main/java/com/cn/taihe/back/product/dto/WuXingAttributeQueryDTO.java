package com.cn.taihe.back.product.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 五行属性查询条件DTO
 *
 * @author system
 * @since 2025-02-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "WuXingAttributeQueryDTO", description = "五行属性查询条件")
public class WuXingAttributeQueryDTO {

  @ApiModelProperty(value = "五行元素键名（模糊查询）", example = "metal")
  private String elementKey;

  @ApiModelProperty(value = "元素分类:basic五行,extended扩展,combined组合", example = "basic")
  private String elementCategory;

  @ApiModelProperty(value = "中文名称（模糊查询）", example = "金")
  private String elementNameZh;

  @ApiModelProperty(value = "元素层级", example = "1")
  private Integer elementTier;

  @ApiModelProperty(value = "是否启用(0-冻结 1-启用)", example = "1")
  private Integer isActive;
}
