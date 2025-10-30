package com.cn.taihe.back.suppliers.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "supplier_change_logs")
@DynamicInsert
@DynamicUpdate
@ApiModel(description = "供应商变更日志实体")
public class SupplierChangeLog {

    @Id
    @Column(length = 36)
    @ApiModelProperty(value = "主键ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @NotBlank(message = "供应商ID不能为空")
    @Column(name = "supplier_id", length = 36, nullable = false)
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    private String supplierId;

    @NotBlank(message = "变更类型不能为空")
    @Size(max = 50, message = "变更类型长度不能超过50")
    @Column(name = "change_type", nullable = false)
    @ApiModelProperty(value = "变更类型", required = true, example = "基本信息变更")
    private String changeType;

    @NotBlank(message = "变更字段不能为空")
    @Size(max = 100, message = "变更字段长度不能超过100")
    @Column(name = "change_field", nullable = false)
    @ApiModelProperty(value = "变更字段", required = true, example = "contactPhone")
    private String changeField;

    @Column(name = "old_value", columnDefinition = "TEXT")
    @ApiModelProperty(value = "旧值", example = "021-12345678")
    private String oldValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    @ApiModelProperty(value = "新值", example = "021-87654321")
    private String newValue;

    @NotBlank(message = "变更人不能为空")
    @Size(max = 100, message = "变更人长度不能超过100")
    @Column(name = "changed_by", nullable = false)
    @ApiModelProperty(value = "变更人", required = true, example = "admin")
    private String changedBy;

    @NotNull(message = "变更时间不能为空")
    @Column(name = "changed_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "变更时间", required = true, example = "2023-06-01 10:00:00")
    private Date changedAt = new Date();

    @Size(max = 500, message = "备注长度不能超过500")
    @ApiModelProperty(value = "备注")
    private String remark;
}
