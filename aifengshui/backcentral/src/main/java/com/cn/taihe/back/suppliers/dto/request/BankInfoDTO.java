package com.cn.taihe.back.suppliers.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "银行信息DTO")
public class BankInfoDTO {
    @NotBlank(message = "开户银行不能为空")
    @Size(max = 100, message = "开户银行长度不能超过100")
    @ApiModelProperty(value = "开户银行", required = true, example = "中国工商银行")
    private String bankName;

    @NotBlank(message = "银行账号不能为空")
    @Size(max = 100, message = "银行账号长度不能超过100")
    @ApiModelProperty(value = "银行账号", required = true, example = "6222021000001234567")
    private String bankAccount;

    @NotBlank(message = "开户名不能为空")
    @Size(max = 100, message = "开户名长度不能超过100")
    @ApiModelProperty(value = "开户名", required = true, example = "XX科技有限公司")
    private String bankAccountName;
}
