package com.cn.taihe.back.suppliers.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description = "资质文件响应DTO")
public class CertificateDTO {

    @ApiModelProperty(value = "资质文件ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @NotBlank(message = "供应商ID不能为空")
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    private String supplierId;

    @NotBlank(message = "资质类型不能为空")
    @ApiModelProperty(value = "资质类型", required = true, example = "营业执照")
    private String certificateType;

    @NotBlank(message = "资质名称不能为空")
    @ApiModelProperty(value = "资质名称", required = true, example = "营业执照正本")
    private String certificateName;

    @ApiModelProperty(value = "资质编号", example = "1234567890")
    private String certificateNumber;

    @ApiModelProperty(value = "发证机关", example = "上海市市场监督管理局")
    private String issuingAuthority;

    @ApiModelProperty(value = "发证日期", example = "2020-01-01")
    private Date issueDate;

    @ApiModelProperty(value = "到期日期", example = "2025-12-31")
    private Date expiryDate;

    @NotBlank(message = "文件路径不能为空")
    @ApiModelProperty(value = "文件存储路径", required = true, example = "/uploads/certificates/123.jpg")
    private String fileUrl;

    @NotBlank(message = "文件名称不能为空")
    @ApiModelProperty(value = "文件名称", required = true, example = "营业执照.jpg")
    private String fileName;

    @ApiModelProperty(value = "文件大小(KB)", example = "1024")
    private Integer fileSize;

    @NotNull(message = "是否有效不能为空")
    @ApiModelProperty(value = "是否有效", required = true, example = "true")
    private Boolean isValid;

    @ApiModelProperty(value = "审核人", example = "admin")
    private String verifiedBy;

    @ApiModelProperty(value = "审核时间", example = "2023-01-01 12:00:00")
    private Date verifiedAt;

    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Date updatedAt;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 业务方法：检查是否即将过期（30天内到期）
    @ApiModelProperty(value = "是否即将过期", hidden = true)
    public boolean isAboutToExpire() {
        if (expiryDate == null) return false;
        long daysUntilExpiry = (expiryDate.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
        return daysUntilExpiry <= 30 && daysUntilExpiry > 0;
    }

    // 业务方法：检查是否已过期
    @ApiModelProperty(value = "是否已过期", hidden = true)
    public boolean isExpired() {
        if (expiryDate == null) return false;
        return expiryDate.getTime() < System.currentTimeMillis();
    }
}
