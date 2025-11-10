package com.cn.taihe.back.product.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

/**
 * 商品品类标签查询参数DTO
 *
 * @author auto generate
 * @since 2025-11-07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductCategoryTagQueryDTO", description = "商品品类标签查询参数")
public class ProductCategoryTagQueryDTO {

  @ApiModelProperty(value = "标签名称(模糊查询，支持中英文标签名)", example = "项链")
  private String tagName;

  @ApiModelProperty(value = "是否启用", example = "true")
  private Boolean isActive;

  @ApiModelProperty(value = "是否在筛选器显示", example = "true")
  private Boolean showInFilter;

  @ApiModelProperty(value = "是否在导航显示", example = "false")
  private Boolean showInNavigation;

  @ApiModelProperty(value = "父标签ID")
  private String parentTagId;
}
