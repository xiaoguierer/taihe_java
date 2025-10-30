package com.cn.taihe.back.suppliers.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "supplier_certificates")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "供应商资质文件实体")
public class SupplierCertificate implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Column(name = "supplier_id", nullable = false)
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440001")
    private String supplierId;

    @Column(name = "certificate_type", nullable = false, length = 50)
    @Size(max = 50, message = "资质类型长度不能超过50个字符")
    @ApiModelProperty(value = "资质类型", required = true, example = "营业执照")
    private String certificateType;

    @Column(name = "certificate_name", nullable = false, length = 255)
    @Size(max = 255, message = "资质名称长度不能超过255个字符")
    @ApiModelProperty(value = "资质名称", required = true, example = "营业执照正本")
    private String certificateName;

    @Column(name = "certificate_number", length = 100)
    @Size(max = 100, message = "资质编号长度不能超过100个字符")
    @ApiModelProperty(value = "资质编号", example = "91110105MA01XY6X6L")
    private String certificateNumber;

    @Column(name = "issuing_authority", length = 255)
    @Size(max = 255, message = "发证机关长度不能超过255个字符")
    @ApiModelProperty(value = "发证机关", example = "北京市工商行政管理局")
    private String issuingAuthority;

    @Column(name = "issue_date")
    @ApiModelProperty(value = "发证日期", example = "2023-01-15")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    @ApiModelProperty(value = "到期日期", example = "2028-01-14")
    private LocalDate expiryDate;

    @Column(name = "file_url", nullable = false, length = 500)
    @Size(max = 500, message = "文件存储路径长度不能超过500个字符")
    @ApiModelProperty(value = "文件存储路径", required = true, example = "/uploads/certificates/2024/01/abc.pdf")
    private String fileUrl;

    @Column(name = "file_name", nullable = false, length = 255)
    @Size(max = 255, message = "文件名称长度不能超过255个字符")
    @ApiModelProperty(value = "文件名称", required = true, example = "营业执照.pdf")
    private String fileName;

    @Column(name = "file_size")
    @ApiModelProperty(value = "文件大小(KB)", example = "1024")
    private Integer fileSize;

    @Column(name = "is_valid", nullable = false)
    @ApiModelProperty(value = "是否有效: 0-有效, 1-无效/冻结", example = "0")
    private Integer isValid;

    @Column(name = "verified_by", length = 50)
    @Size(max = 50, message = "审核人长度不能超过50个字符")
    @ApiModelProperty(value = "审核人", example = "admin")
    private String verifiedBy;

    @Column(name = "verified_at")
    @ApiModelProperty(value = "审核时间", example = "2024-01-15 10:30:00")
    private LocalDateTime verifiedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @Column(name = "remark", length = 500)
    @Size(max = 500, message = "备注长度不能超过500个字符")
    @ApiModelProperty(value = "备注", example = "这是备注信息")
    private String remark;
}