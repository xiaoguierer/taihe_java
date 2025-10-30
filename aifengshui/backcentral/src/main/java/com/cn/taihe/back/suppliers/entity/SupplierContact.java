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
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "supplier_contacts")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "供应商联系人实体")
public class SupplierContact {

    @Id
    @Column(length = 36)
    @ApiModelProperty(value = "主键ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @NotBlank(message = "供应商ID不能为空")
    @Column(name = "supplier_id", length = 36, nullable = false)
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    private String supplierId;

    @NotBlank(message = "联系人姓名不能为空")
    @Size(max = 100, message = "联系人姓名长度不能超过100")
    @Column(nullable = false)
    @ApiModelProperty(value = "联系人姓名", required = true, example = "张三")
    private String name;

    @Size(max = 100, message = "职位长度不能超过100")
    @ApiModelProperty(value = "职位", example = "销售经理")
    private String position;

    @Size(max = 100, message = "部门长度不能超过100")
    @ApiModelProperty(value = "部门", example = "销售部")
    private String department;

    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^[0-9-]{7,20}$", message = "电话格式不正确")
    @Column(nullable = false)
    @ApiModelProperty(value = "电话", required = true, example = "021-12345678")
    private String phone;

    @Pattern(regexp = "^[0-9-]{11,20}$", message = "手机格式不正确")
    @Size(max = 50, message = "手机长度不能超过50")
    @ApiModelProperty(value = "手机", example = "13800138000")
    private String mobile;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100")
    @ApiModelProperty(value = "邮箱", example = "zhangsan@example.com")
    private String email;

    @Size(max = 100, message = "微信长度不能超过100")
    @ApiModelProperty(value = "微信", example = "zhangsan123")
    private String wechat;

    @Size(max = 50, message = "QQ长度不能超过50")
    @ApiModelProperty(value = "QQ", example = "12345678")
    private String qq;

    @NotNull(message = "是否主联系人不能为空")
    @Column(name = "is_primary", nullable = false)
    @ApiModelProperty(value = "是否主联系人", required = true, example = "true")
    private Boolean isPrimary = false;

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

    @Size(max = 500, message = "备注长度不能超过500")
    @ApiModelProperty(value = "备注")
    private String remark;
}
