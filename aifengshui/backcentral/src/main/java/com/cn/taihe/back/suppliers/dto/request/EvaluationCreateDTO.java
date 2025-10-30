package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@ApiModel(description = "创建评价记录DTO")
public class EvaluationCreateDTO {
    @NotBlank(message = "供应商ID不能为空")
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    private String supplierId;

    @NotBlank(message = "评价人不能为空")
    @ApiModelProperty(value = "评价人", required = true, example = "李四")
    private String evaluator;

    @NotNull(message = "质量评分不能为空")
    @DecimalMin(value = "0.00", message = "质量评分不能小于0")
    @DecimalMax(value = "5.00", message = "质量评分不能大于5")
    @ApiModelProperty(value = "质量评分(0-5分)", required = true, example = "4.50")
    private BigDecimal qualityScore;

    @NotNull(message = "交货评分不能为空")
    @DecimalMin(value = "0.00", message = "交货评分不能小于0")
    @DecimalMax(value = "5.00", message = "交货评分不能大于5")
    @ApiModelProperty(value = "交货评分(0-5分)", required = true, example = "4.20")
    private BigDecimal deliveryScore;

    @NotNull(message = "服务评分不能为空")
    @DecimalMin(value = "0.00", message = "服务评分不能小于0")
    @DecimalMax(value = "5.00", message = "服务评分不能大于5")
    @ApiModelProperty(value = "服务评分(0-5分)", required = true, example = "4.60")
    private BigDecimal serviceScore;

    @ApiModelProperty(value = "优势", example = "产品质量稳定")
    private String strengths;

    @ApiModelProperty(value = "不足", example = "售后服务响应慢")
    private String weaknesses;
}
