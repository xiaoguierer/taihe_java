package com.cn.taihe.back.user.dto.request;

import lombok.Data;

@Data
public class SearchRequest {
    private String email;
    private String name;
    private Integer status;
}
