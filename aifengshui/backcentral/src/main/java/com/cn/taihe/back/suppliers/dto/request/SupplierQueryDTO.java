package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商查询DTO
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SupplierQueryDTO", description = "供应商查询条件DTO")
public class SupplierQueryDTO {

    @ApiModelProperty(value = "供应商编码", example = "SUP20230001")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称(模糊查询)", example = "科技")
    private String name;

    @ApiModelProperty(value = "供应商简称(模糊查询)", example = "科技")
    private String shortName;

    @ApiModelProperty(value = "状态:1-启用,0-禁用", example = "1")
    private Integer status;

    @ApiModelProperty(value = "供应商等级:1-战略,2-主力,3-一般,4-备选", example = "3")
    private Integer supplierLevel;

    @ApiModelProperty(value = "企业类型", example = "有限责任公司")
    private String companyType;

    @ApiModelProperty(value = "统一社会信用代码", example = "91310101MA1FPX1234")
    private String unifiedCreditCode;

    @ApiModelProperty(value = "法人代表", example = "张三")
    private String legalPerson;

    @ApiModelProperty(value = "注册地址(模糊查询)", example = "上海")
    private String registeredAddress;

    @ApiModelProperty(value = "业务联系人", example = "李四")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话", example = "13800138000")
    private String contactPhone;

    @ApiModelProperty(value = "绩效评分最小值", example = "3.00")
    private BigDecimal performanceScoreMin;

    @ApiModelProperty(value = "绩效评分最大值", example = "5.00")
    private BigDecimal performanceScoreMax;

    @ApiModelProperty(value = "主营品类", example = "电子产品")
    private String mainCategories;
}