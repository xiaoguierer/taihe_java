package com.cn.taihe.back.filestore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品图片查询参数DTO
 *
 * @author system
 * @date 2025-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ProductImageQueryDTO对象", description = "商品图片查询参数")
public class ProductImageQueryDTO {

  @ApiModelProperty(value = "文件名(模糊查询)")
  private String fileName;

  @ApiModelProperty(value = "文件类型")
  private String mimeType;

  @ApiModelProperty(value = "是否主图")
  private Boolean isPrimary;

  @ApiModelProperty(value = "是否启用")
  private Boolean isActive;

}
