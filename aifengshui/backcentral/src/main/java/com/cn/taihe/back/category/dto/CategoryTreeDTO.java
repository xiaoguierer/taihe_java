package com.cn.taihe.back.category.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分类树形结构DTO
 */
@Data
@ApiModel(description = "分类树形结构DTO")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryTreeDTO {

    @ApiModelProperty(value = "分类ID")
    private String id;

    @ApiModelProperty(value = "父分类ID")
    private String parentId;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "分类层级")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "分类图片")
    private String image;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "关键字")
    private String keywords;

    @ApiModelProperty(value = "是否显示")
    private Integer isDisplay;

    @ApiModelProperty(value = "子分类列表")
    private List<CategoryTreeDTO> children;

    @ApiModelProperty(value = "是否有子分类")
    private Boolean hasChildren;

    @ApiModelProperty(value = "分类路径，如：一级分类 > 二级分类 > 三级分类")
    private String categoryPath;

    /**
     * 判断是否有子分类
     */
    public Boolean getHasChildren() {
        return children != null && !children.isEmpty();
    }
}
