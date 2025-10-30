package com.cn.taihe.back.suppliers.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ApiModel(description = "合作信息DTO")
public class CooperationInfoDTO {
    @NotNull(message = "合作开始日期不能为空")
    @ApiModelProperty(value = "合作开始日期", required = true, example = "2023-01-01")
    private Date cooperationStartDate;

    @Future(message = "合作结束日期必须为将来时间")
    @ApiModelProperty(value = "合作结束日期", example = "2025-12-31")
    private Date cooperationEndDate;

    @NotBlank(message = "结算周期不能为空")
    @Size(max = 50, message = "结算周期长度不能超过50")
    @ApiModelProperty(value = "结算周期", required = true, example = "月结30天")
    private String settlementCycle;

    @NotBlank(message = "结算货币不能为空")
    @Size(max = 10, message = "结算货币长度不能超过10")
    @ApiModelProperty(value = "结算货币", required = true, example = "CNY")
    private String currency;
}
