package com.cn.taihe.back.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品品类标签新增参数DTO
 *
 * @author auto generate
 * @since 2025-11-07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductCategoryTagCreateDTO", description = "商品品类标签新增参数")
public class ProductCategoryTagCreateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "标签键名不能为空")
  @Length(max = 50, message = "标签键名长度不能超过50个字符")
  @ApiModelProperty(value = "标签键名", required = true, example = "necklace")
  private String tagKey;

  @NotBlank(message = "英文标签名不能为空")
  @Length(max = 100, message = "英文标签名长度不能超过100个字符")
  @ApiModelProperty(value = "英文标签名", required = true, example = "Necklace")
  private String tagNameEn;

  @NotBlank(message = "中文标签名不能为空")
  @Length(max = 100, message = "中文标签名长度不能超过100个字符")
  @ApiModelProperty(value = "中文标签名", required = true, example = "项链")
  private String tagNameZh;

  @Length(max = 100, message = "阿拉伯语标签名长度不能超过100个字符")
  @ApiModelProperty(value = "阿拉伯语标签名", example = "قلادة")
  private String tagNameAr;

  @ApiModelProperty(value = "英文详细描述")
  private String descriptionEn;

  @ApiModelProperty(value = "中文详细描述")
  private String descriptionZh;

  @ApiModelProperty(value = "阿拉伯语详细描述")
  private String descriptionAr;

  @Length(max = 200, message = "英文简短描述长度不能超过200个字符")
  @ApiModelProperty(value = "英文简短描述")
  private String shortDescEn;

  @Length(max = 200, message = "中文简短描述长度不能超过200个字符")
  @ApiModelProperty(value = "中文简短描述")
  private String shortDescZh;

  @Length(max = 200, message = "阿拉伯语简短描述长度不能超过200个字符")
  @ApiModelProperty(value = "阿拉伯语简短描述")
  private String shortDescAr;

  @Length(max = 50, message = "图标访问id长度不能超过50个字符")
  @ApiModelProperty(value = "图标访问id")
  private String iconId;

  @Length(max = 255, message = "图标url长度不能超过255个字符")
  @ApiModelProperty(value = "图标url")
  private String iconPath;

  @Length(max = 500, message = "封面图访问URL长度不能超过500个字符")
  @ApiModelProperty(value = "封面图访问URL")
  private String coverImageUrl;

  @Length(max = 50, message = "封面图ID长度不能超过50个字符")
  @ApiModelProperty(value = "封面图ID")
  private String coverId;

  @Length(max = 500, message = "悬停图访问URL长度不能超过500个字符")
  @ApiModelProperty(value = "悬停图访问URL")
  private String hoverImageUrl;

  @Length(max = 50, message = "悬停图id长度不能超过50个字符")
  @ApiModelProperty(value = "悬停图id")
  private String hoverId;

  @NotNull(message = "标签类型不能为空")
  @ApiModelProperty(value = "标签类型:1首饰类型,2材质,3宝石类型,4风格,5场合,6工艺,7人群,8五行属性", required = true, example = "1")
  private Integer tagType;

  @Length(max = 50, message = "父标签ID长度不能超过50个字符")
  @ApiModelProperty(value = "父标签ID")
  private String parentTagId;

  @Length(max = 20, message = "标签代表色长度不能超过20个字符")
  @ApiModelProperty(value = "标签代表色(用于UI)", example = "#FF5733")
  private String colorCode;

  @ApiModelProperty(value = "排序值", example = "0")
  private Integer sortOrder = 0;

  @ApiModelProperty(value = "是否启用", example = "true")
  private Boolean isActive = true;

  @ApiModelProperty(value = "是否在筛选器显示", example = "true")
  private Boolean showInFilter = true;

  @ApiModelProperty(value = "是否在导航显示", example = "false")
  private Boolean showInNavigation = false;

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
}
