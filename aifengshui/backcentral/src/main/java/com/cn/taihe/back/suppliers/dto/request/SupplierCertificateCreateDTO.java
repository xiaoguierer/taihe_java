package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 供应商资质文件新增DTO
 * 只包含可新增字段，不包含系统生成字段
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SupplierCertificateCreateDTO", description = "供应商资质文件新增DTO")
public class SupplierCertificateCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "供应商ID不能为空")
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440001")
    private String supplierId;

    @NotBlank(message = "资质类型不能为空")
    @Size(max = 50, message = "资质类型长度不能超过50个字符")
    @ApiModelProperty(value = "资质类型", required = true, example = "营业执照")
    private String certificateType;

    @NotBlank(message = "资质名称不能为空")
    @Size(max = 255, message = "资质名称长度不能超过255个字符")
    @ApiModelProperty(value = "资质名称", required = true, example = "营业执照正本")
    private String certificateName;

    @Size(max = 100, message = "资质编号长度不能超过100个字符")
    @ApiModelProperty(value = "资质编号", example = "91110105MA01XY6X6L")
    private String certificateNumber;

    @Size(max = 255, message = "发证机关长度不能超过255个字符")
    @ApiModelProperty(value = "发证机关", example = "北京市工商行政管理局")
    private String issuingAuthority;

    @ApiModelProperty(value = "发证日期", example = "2023-01-15")
    private LocalDate issueDate;

    @ApiModelProperty(value = "到期日期", example = "2028-01-14")
    private LocalDate expiryDate;

    @NotBlank(message = "文件存储路径不能为空")
    @Size(max = 500, message = "文件存储路径长度不能超过500个字符")
    @ApiModelProperty(value = "文件存储路径", required = true, example = "/uploads/certificates/2024/01/abc.pdf")
    private String fileUrl;

    @NotBlank(message = "文件名称不能为空")
    @Size(max = 255, message = "文件名称长度不能超过255个字符")
    @ApiModelProperty(value = "文件名称", required = true, example = "营业执照.pdf")
    private String fileName;

    @ApiModelProperty(value = "文件大小(KB)", example = "1024")
    private Integer fileSize;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @ApiModelProperty(value = "备注", example = "这是备注信息")
    private String remark;
}
