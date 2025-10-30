package com.cn.taihe.back.suppliers.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "评价记录响应DTO")
public class EvaluationDTO {
    @ApiModelProperty(value = "评价ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @ApiModelProperty(value = "评价日期", example = "2023-06-01")
    private Date evaluationDate;

    @ApiModelProperty(value = "评价人", example = "李四")
    private String evaluator;

    @ApiModelProperty(value = "质量评分", example = "4.50")
    private BigDecimal qualityScore;

    @ApiModelProperty(value = "交货评分", example = "4.20")
    private BigDecimal deliveryScore;

    @ApiModelProperty(value = "服务评分", example = "4.60")
    private BigDecimal serviceScore;

    @ApiModelProperty(value = "综合评分", example = "4.43")
    private BigDecimal overallScore;

    @ApiModelProperty(value = "优势", example = "产品质量稳定")
    private String strengths;

    @ApiModelProperty(value = "不足", example = "售后服务响应慢")
    private String weaknesses;
}
