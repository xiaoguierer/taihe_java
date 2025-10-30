package com.cn.taihe.back.category.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 分类更新DTO
 */
@Data
@ApiModel(description = "分类更新请求参数")
public class CategoryUpdateDTO {

    @NotNull(message = "分类ID不能为空")
    @ApiModelProperty(value = "分类ID", required = true)
    private String id;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 64, message = "分类名称长度不能超过64个字符")
    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "图标URL")
    private String icon;

    @ApiModelProperty(value = "分类图片URL")
    private String image;

    @ApiModelProperty(value = "分类描述")
    private String description;

    @Size(max = 255, message = "关键字长度不能超过255个字符")
    @ApiModelProperty(value = "SEO关键字")
    private String keywords;

    @NotNull(message = "显示状态不能为空")
    @Range(min = 0, max = 1, message = "显示状态值必须是0或1")
    @ApiModelProperty(value = "是否显示(0-不显示，1-显示)", required = true)
    private Integer isDisplay;

    @NotNull(message = "父分类ID不能为空")
    @ApiModelProperty(value = "父分类ID", required = true)
    private String parentId;
}
