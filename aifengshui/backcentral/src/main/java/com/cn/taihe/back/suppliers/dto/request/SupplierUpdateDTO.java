package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商更新DTO
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SupplierUpdateDTO", description = "供应商更新DTO")
public class SupplierUpdateDTO {
    @NotBlank(message = "ID不能为空")
    @ApiModelProperty(value = "ID", required = true, example = "UUID")
    private String id;

    @NotBlank(message = "供应商全称不能为空")
    @Size(max = 255, message = "供应商全称长度不能超过255")
    @ApiModelProperty(value = "供应商全称", required = true, example = "XX科技有限公司")
    private String name;

    @NotBlank(message = "供应商简称不能为空")
    @Size(max = 100, message = "供应商简称长度不能超过100")
    @ApiModelProperty(value = "供应商简称", required = true, example = "XX科技")
    private String shortName;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态:1-启用,0-禁用", required = true, example = "1")
    private Integer status;

    @NotNull(message = "供应商等级不能为空")
    @ApiModelProperty(value = "供应商等级:1-战略,2-主力,3-一般,4-备选", required = true, example = "3")
    private Integer supplierLevel;

    @Size(max = 500, message = "实际经营地址长度不能超过500")
    @ApiModelProperty(value = "实际经营地址", example = "上海市浦东新区张江高科技园区XX路123号")
    private String businessAddress;

    @ApiModelProperty(value = "成立日期", example = "2010-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date establishDate;

    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    @Size(max = 500, message = "主营品类长度不能超过500")
    @ApiModelProperty(value = "主营品类", example = "电子产品,办公用品")
    private String mainCategories;

    @NotBlank(message = "业务联系人不能为空")
    @Size(max = 100, message = "业务联系人长度不能超过100")
    @ApiModelProperty(value = "业务联系人", required = true, example = "李四")
    private String contactPerson;

    @NotBlank(message = "联系电话不能为空")
    @Size(max = 50, message = "联系电话长度不能超过50")
    @ApiModelProperty(value = "联系电话", required = true, example = "13800138000")
    private String contactPhone;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "联系邮箱长度不能超过100")
    @ApiModelProperty(value = "联系邮箱", example = "contact@example.com")
    private String contactEmail;

    @Size(max = 255, message = "官网网址长度不能超过255")
    @ApiModelProperty(value = "官网网址", example = "https://www.example.com")
    private String website;

    @NotBlank(message = "开户银行不能为空")
    @Size(max = 100, message = "开户银行长度不能超过100")
    @ApiModelProperty(value = "开户银行", required = true, example = "中国工商银行")
    private String bankName;

    @NotBlank(message = "银行账号不能为空")
    @Size(max = 100, message = "银行账号长度不能超过100")
    @ApiModelProperty(value = "银行账号", required = true, example = "6222021000001234567")
    private String bankAccount;

    @NotBlank(message = "开户名不能为空")
    @Size(max = 100, message = "开户名长度不能超过100")
    @ApiModelProperty(value = "开户名", required = true, example = "XX科技有限公司")
    private String bankAccountName;

    @NotBlank(message = "纳税类型不能为空")
    @Size(max = 50, message = "纳税类型长度不能超过50")
    @ApiModelProperty(value = "纳税类型", required = true, example = "一般纳税人")
    private String taxType;

    @NotBlank(message = "发票类型不能为空")
    @Size(max = 50, message = "发票类型长度不能超过50")
    @ApiModelProperty(value = "发票类型", required = true, example = "增值税专用发票")
    private String invoiceType;

    @NotBlank(message = "结算周期不能为空")
    @Size(max = 50, message = "结算周期长度不能超过50")
    @ApiModelProperty(value = "结算周期", example = "月结30天")
    private String settlementCycle = "月结30天";

    @NotBlank(message = "结算货币不能为空")
    @Size(max = 10, message = "结算货币长度不能超过10")
    @ApiModelProperty(value = "结算货币", example = "CNY")
    private String currency = "CNY";

    @ApiModelProperty(value = "最小订单金额", example = "1000.00")
    private BigDecimal minOrderAmount;

    @ApiModelProperty(value = "最小订单数量", example = "10")
    private Integer minOrderQuantity;

    @ApiModelProperty(value = "交货周期(天)", example = "7")
    private Integer leadTime;

    @ApiModelProperty(value = "合同到期日", example = "2025-12-31")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractExpireDate;

    @ApiModelProperty(value = "绩效评分(0-5分)", example = "4.50")
    private BigDecimal performanceScore;

    @ApiModelProperty(value = "质量评分", example = "4.80")
    private BigDecimal qualityScore;

    @ApiModelProperty(value = "交货评分", example = "4.70")
    private BigDecimal deliveryScore;

    @ApiModelProperty(value = "服务评分", example = "4.60")
    private BigDecimal serviceScore;

    @ApiModelProperty(value = "备注")
    private String remark;
}
