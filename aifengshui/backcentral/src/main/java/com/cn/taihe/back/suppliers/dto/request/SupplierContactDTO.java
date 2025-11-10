package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "联系人响应DTO")
public class SupplierContactDTO {
    @ApiModelProperty(value = "联系人ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @ApiModelProperty(value = "联系人姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "职位", example = "销售经理")
    private String position;

    @ApiModelProperty(value = "电话", example = "021-12345678")
    private String phone;

    @ApiModelProperty(value = "手机", example = "138****8000")
    private String mobile;

    @ApiModelProperty(value = "邮箱", example = "z****@example.com")
    private String email;

    @ApiModelProperty(value = "是否主联系人", example = "true")
    private Boolean isPrimary;
}
