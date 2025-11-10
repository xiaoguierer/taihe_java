package com.cn.taihe.back.filestore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * 商品图片更新参数DTO
 *
 * @author system
 * @date 2025-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ProductImageUpdateDTO对象", description = "商品图片更新参数")
public class ProductImageUpdateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID", required = true)
  @NotBlank(message = "主键ID不能为空")
  @Length(max = 50, message = "主键ID长度不能超过50个字符")
  private String id;

  @ApiModelProperty(value = "绝对URL(CDN地址)")
  @Length(max = 500, message = "绝对URL长度不能超过500个字符")
  private String absoluteUrl;

  @ApiModelProperty(value = "相对路径")
  @Length(max = 500, message = "相对路径长度不能超过500个字符")
  private String relativePath;

  @ApiModelProperty(value = "文件名")
  @Length(max = 255, message = "文件名长度不能超过255个字符")
  private String fileName;

  @ApiModelProperty(value = "文件大小(字节)")
  @PositiveOrZero(message = "文件大小必须大于等于0")
  private Integer fileSize;

  @ApiModelProperty(value = "文件类型")
  @Length(max = 100, message = "文件类型长度不能超过100个字符")
  private String mimeType;

  @ApiModelProperty(value = "英文替代文本")
  @Length(max = 200, message = "英文替代文本长度不能超过200个字符")
  private String imageAltEn;

  @ApiModelProperty(value = "中文替代文本")
  @Length(max = 200, message = "中文替代文本长度不能超过200个字符")
  private String imageAltZh;

  @ApiModelProperty(value = "阿拉伯语替代文本")
  @Length(max = 200, message = "阿拉伯语替代文本长度不能超过200个字符")
  private String imageAltAr;

  @ApiModelProperty(value = "英文标题")
  @Length(max = 200, message = "英文标题长度不能超过200个字符")
  private String imageTitleEn;

  @ApiModelProperty(value = "中文标题")
  @Length(max = 200, message = "中文标题长度不能超过200个字符")
  private String imageTitleZh;

  @ApiModelProperty(value = "阿拉伯语标题")
  @Length(max = 200, message = "阿拉伯语标题长度不能超过200个字符")
  private String imageTitleAr;

  @ApiModelProperty(value = "排序值")
  @PositiveOrZero(message = "排序值必须大于等于0")
  private Integer sortOrder;

  @ApiModelProperty(value = "是否主图")
  private Boolean isPrimary;

  @ApiModelProperty(value = "是否启用")
  private Boolean isActive;

  @ApiModelProperty(value = "更新人ID", required = true, hidden = true)
  private Long updatedBy;

}
