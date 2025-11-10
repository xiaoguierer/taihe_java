package com.cn.taihe.back.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品SPU查询条件DTO
 *
 * @author system
 * @since 2025-01-01
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductSpuQueryDTO", description = "商品SPU查询条件DTO")
public class ProductSpuQueryDTO {

  @ApiModelProperty(value = "SPU编码（模糊查询）")
  private String spuCode;

  @ApiModelProperty(value = "英文产品名称（模糊查询）")
  private String productNameEn;

  @ApiModelProperty(value = "中文产品名称（模糊查询）")
  private String productNameZh;

  @ApiModelProperty(value = "是否推荐")
  private Boolean isFeatured;

  @ApiModelProperty(value = "是否新品")
  private Boolean isNewArrival;

  @ApiModelProperty(value = "是否AI设计")
  private Boolean isAiDesigned;

  @ApiModelProperty(value = "是否启用")
  private Boolean isActive;

  @ApiModelProperty(value = "主要五行元素")
  private String primaryElement;
}
