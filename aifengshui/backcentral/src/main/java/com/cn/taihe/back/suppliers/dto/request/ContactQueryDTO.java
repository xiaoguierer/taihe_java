package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "联系人查询DTO")
public class ContactQueryDTO {

    @ApiModelProperty(value = "供应商ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String supplierId;

    @ApiModelProperty(value = "联系人姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "部门", example = "销售部")
    private String department;

    @ApiModelProperty(value = "是否主联系人", example = "true")
    private Boolean isPrimary;

    @ApiModelProperty(value = "关键词搜索(姓名/电话/邮箱)", example = "张三")
    private String keyword;
}
