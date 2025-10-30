package com.cn.taihe.back.category.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("product_category")
public class Category {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField("parent_id")
    private String parentId;

    private String name;
    private Integer level;
    private Integer sort;
    private String icon;
    private String image;
    private String description;
    private String keywords;

    @TableField("is_display")
    private Integer displayStatus;

    @TableField("is_deleted")
    private Integer deleted;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<Category> children;
}
