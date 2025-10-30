package com.cn.taihe.back.category.dto;

import lombok.Data;

@Data
public class CategoryQueryDTO {
    private Integer level;
    private String name;
    private String keywords;
    private Integer isDisplay;
    private Integer isDeleted;
}
