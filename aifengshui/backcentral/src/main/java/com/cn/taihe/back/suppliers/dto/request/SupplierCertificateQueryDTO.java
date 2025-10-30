package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 供应商资质文件查询条件DTO
 * 用于接收查询参数，支持模糊查询
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SupplierCertificateQueryDTO", description = "供应商资质文件查询条件DTO")
public class SupplierCertificateQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "供应商ID", example = "550e8400-e29b-41d4-a716-446655440001")
    private String supplierId;

    @Size(max = 50, message = "资质类型长度不能超过50个字符")
    @ApiModelProperty(value = "资质类型", example = "营业执照")
    private String certificateType;

    @Size(max = 255, message = "资质名称长度不能超过255个字符")
    @ApiModelProperty(value = "资质名称(支持模糊查询)", example = "营业执照")
    private String certificateName;

    @Size(max = 100, message = "资质编号长度不能超过100个字符")
    @ApiModelProperty(value = "资质编号(支持模糊查询)", example = "91110105")
    private String certificateNumber;

    @ApiModelProperty(value = "是否有效: 0-有效, 1-无效/冻结", example = "0")
    private Integer isValid;
}
