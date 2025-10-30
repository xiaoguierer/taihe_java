package com.cn.taihe.back.suppliers.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "批量操作结果DTO")
public class BatchOperateResultDTO {
    @ApiModelProperty(value = "成功数量", example = "5")
    private int successCount;

    @ApiModelProperty(value = "失败数量", example = "2")
    private int failureCount;

    @ApiModelProperty(value = "失败详情")
    private List<FailureDetail> failureDetails;

    @Data
    @ApiModel(description = "失败详情")
    public static class FailureDetail {
        @ApiModelProperty(value = "供应商ID", example = "550e8400-e29b-41d4-a716-446655440000")
        private String id;

        @ApiModelProperty(value = "失败原因", example = "供应商不存在")
        private String reason;
    }
}