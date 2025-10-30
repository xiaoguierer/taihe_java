package com.cn.taihe.back.suppliers.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "supplier_evaluations")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "供应商评价记录实体")
public class SupplierEvaluation {

    @Id
    @Column(length = 36)
    @ApiModelProperty(value = "主键ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @NotBlank(message = "供应商ID不能为空")
    @Column(name = "supplier_id", length = 36, nullable = false)
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    private String supplierId;

    @NotNull(message = "评价日期不能为空")
    @Column(name = "evaluation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "评价日期", required = true, example = "2023-06-01")
    private Date evaluationDate;

    @NotBlank(message = "评价人不能为空")
    @Size(max = 100, message = "评价人长度不能超过100")
    @Column(nullable = false)
    @ApiModelProperty(value = "评价人", required = true, example = "李四")
    private String evaluator;

    @NotNull(message = "质量评分不能为空")
    @DecimalMin(value = "0.00", message = "质量评分不能小于0")
    @DecimalMax(value = "5.00", message = "质量评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "质量评分格式不正确")
    @Column(name = "quality_score", precision = 3, scale = 2, nullable = false)
    @ApiModelProperty(value = "质量评分(0-5分)", required = true, example = "4.50")
    private BigDecimal qualityScore;

    @NotNull(message = "交货评分不能为空")
    @DecimalMin(value = "0.00", message = "交货评分不能小于0")
    @DecimalMax(value = "5.00", message = "交货评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "交货评分格式不正确")
    @Column(name = "delivery_score", precision = 3, scale = 2, nullable = false)
    @ApiModelProperty(value = "交货评分(0-5分)", required = true, example = "4.20")
    private BigDecimal deliveryScore;

    @NotNull(message = "价格评分不能为空")
    @DecimalMin(value = "0.00", message = "价格评分不能小于0")
    @DecimalMax(value = "5.00", message = "价格评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "价格评分格式不正确")
    @Column(name = "price_score", precision = 3, scale = 2, nullable = false)
    @ApiModelProperty(value = "价格评分(0-5分)", required = true, example = "4.80")
    private BigDecimal priceScore;

    @NotNull(message = "服务评分不能为空")
    @DecimalMin(value = "0.00", message = "服务评分不能小于0")
    @DecimalMax(value = "5.00", message = "服务评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "服务评分格式不正确")
    @Column(name = "service_score", precision = 3, scale = 2, nullable = false)
    @ApiModelProperty(value = "服务评分(0-5分)", required = true, example = "4.60")
    private BigDecimal serviceScore;

    @NotNull(message = "综合评分不能为空")
    @DecimalMin(value = "0.00", message = "综合评分不能小于0")
    @DecimalMax(value = "5.00", message = "综合评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "综合评分格式不正确")
    @Column(name = "overall_score", precision = 3, scale = 2, nullable = false)
    @ApiModelProperty(value = "综合评分(0-5分)", required = true, example = "4.50")
    private BigDecimal overallScore;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty(value = "优势", example = "产品质量稳定，交货准时")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty(value = "不足", example = "售后服务响应速度有待提高")
    private String weaknesses;

    @Column(name = "improvement_suggestions", columnDefinition = "TEXT")
    @ApiModelProperty(value = "改进建议", example = "建议加强售后服务团队建设")
    private String improvementSuggestions;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updatedAt;
}
