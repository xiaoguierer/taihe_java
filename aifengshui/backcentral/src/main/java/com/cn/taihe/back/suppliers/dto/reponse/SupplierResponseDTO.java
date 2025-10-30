package com.cn.taihe.back.suppliers.dto.reponse;
import com.cn.taihe.back.suppliers.dto.request.BankInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "供应商响应DTO")
public class SupplierResponseDTO {
    @ApiModelProperty(value = "供应商ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @ApiModelProperty(value = "供应商编码", example = "SUP20230001")
    private String supplierCode;

    @ApiModelProperty(value = "供应商全称", example = "XX科技有限公司")
    private String name;

    @ApiModelProperty(value = "供应商简称", example = "XX科技")
    private String shortName;

    @ApiModelProperty(value = "供应商等级(1-战略,2-主力,3-一般,4-备选)", example = "3")
    private Integer supplierLevel;

    @ApiModelProperty(value = "统一社会信用代码", example = "91310101MA1FPX1234")
    private String unifiedCreditCode;

    @ApiModelProperty(value = "注册地址", example = "上海市浦东新区张江高科技园区")
    private String registeredAddress;

    @ApiModelProperty(value = "主营品类", example = "电子产品,办公用品")
    private String mainCategories;

    @ApiModelProperty(value = "联系人姓名", example = "李四")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话", example = "021-12345678")
    private String contactPhone;

    @ApiModelProperty(value = "绩效评分", example = "4.50")
    private BigDecimal performanceScore;

    @ApiModelProperty(value = "状态(1-启用,0-禁用)", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Date createdAt;

    @ApiModelProperty(value = "银行信息")
    private BankInfoDTO bankInfo;

    @ApiModelProperty(value = "资质文件列表")
    private List<CertificateDTO> certificates;

    @ApiModelProperty(value = "联系人列表")
    private List<ContactDTO> contacts;
}
