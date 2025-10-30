package com.cn.taihe.back.category.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 分类数据传输对象
 */
@Data
@ApiModel(description = "分类信息DTO")
public class CategoryDTO {

    @ApiModelProperty(value = "分类ID")
    private String id;

    @ApiModelProperty(value = "父分类ID，一级分类为0")
    private String parentId;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 64, message = "分类名称长度不能超过64个字符")
    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    @NotNull(message = "分类层级不能为空")
    @ApiModelProperty(value = "分类层级(1-3)", required = true)
    private Integer level;

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

    @ApiModelProperty(value = "是否显示(0-不显示，1-显示)")
    private Integer isDisplay;

    @ApiModelProperty(value = "是否删除(0-未删除，1-已删除)")
    private Integer isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "父分类名称")
    private String parentName;

    @ApiModelProperty(value = "子分类数量")
    private Integer childrenCount;
}
