package com.cn.taihe.back.suppliers.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *完整的DTO体系包含：
 *
 * ​供应商核心DTO​：
 *
 * SupplierCreateDTO- 创建供应商
 *
 * SupplierUpdateDTO- 更新供应商
 *
 * SupplierResponseDTO- 供应商响应
 *
 * SupplierStatusDTO- 状态变更
 *
 * ​资质文件DTO​：
 *
 * CertificateCreateDTO- 创建资质
 *
 * CertificateDTO- 资质响应
 *
 * CertificateQueryDTO- 资质查询
 *
 * CertificateUploadDTO- 文件上传
 *
 * ​联系人DTO​：
 *
 * ContactCreateDTO- 创建联系人
 *
 * ContactDTO- 联系人响应
 *
 * ContactQueryDTO- 联系人查询
 *
 * ​评价记录DTO​：
 *
 * EvaluationCreateDTO- 创建评价
 *
 * EvaluationDTO- 评价响应
 *
 * ​批量操作DTO​：
 *
 * BatchOperateDTO- 批量操作请求
 *
 * BatchOperateResultDTO- 批量操作结果
 *
 * ​数据导出DTO​：
 *
 * ExportRequestDTO- 导出请求
 *
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "suppliers")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "供应商主表实体")
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 36)
    @ApiModelProperty(value = "主键ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @NotBlank(message = "供应商编码不能为空")
    @Size(max = 20, message = "供应商编码长度不能超过20")
    @Column(name = "supplier_code", length = 20, unique = true, nullable = false)
    @ApiModelProperty(value = "供应商编码", required = true, example = "SUP20230001")
    private String supplierCode;

    @NotBlank(message = "供应商名称不能为空")
    @Size(max = 255, message = "供应商名称长度不能超过255")
    @Column(nullable = false)
    @ApiModelProperty(value = "供应商全称", required = true, example = "XX科技有限公司")
    private String name;

    @NotBlank(message = "供应商简称不能为空")
    @Size(max = 100, message = "供应商简称长度不能超过100")
    @Column(name = "short_name", nullable = false)
    @ApiModelProperty(value = "供应商简称", required = true, example = "XX科技")
    private String shortName;

    @NotNull(message = "状态不能为空")
    @Column(nullable = false)
    @ApiModelProperty(value = "状态(1-启用,0-禁用)", required = true, example = "1")
    private Integer status = 1;

    @NotNull(message = "供应商等级不能为空")
    @Column(name = "supplier_level", nullable = false)
    @ApiModelProperty(value = "供应商等级(1-战略,2-主力,3-一般,4-备选)", required = true, example = "3")
    private Integer supplierLevel = 3;

    @NotBlank(message = "企业类型不能为空")
    @Size(max = 50, message = "企业类型长度不能超过50")
    @Column(name = "company_type", nullable = false)
    @ApiModelProperty(value = "企业类型", required = true, example = "有限责任公司")
    private String companyType;

    @NotBlank(message = "统一社会信用代码不能为空")
    @Size(max = 50, message = "统一社会信用代码长度不能超过50")
    @Column(name = "unified_credit_code", unique = true, nullable = false)
    @ApiModelProperty(value = "统一社会信用代码", required = true, example = "91310101MA1FPX1234")
    private String unifiedCreditCode;

    @NotBlank(message = "法人代表不能为空")
    @Size(max = 100, message = "法人代表长度不能超过100")
    @Column(name = "legal_person", nullable = false)
    @ApiModelProperty(value = "法人代表", required = true, example = "张三")
    private String legalPerson;

    @Digits(integer = 13, fraction = 2, message = "注册资本格式不正确")
    @Column(name = "registered_capital", precision = 15, scale = 2)
    @ApiModelProperty(value = "注册资本(万元)", example = "1000.00")
    private BigDecimal registeredCapital;

    @NotBlank(message = "注册地址不能为空")
    @Size(max = 500, message = "注册地址长度不能超过500")
    @Column(name = "registered_address", nullable = false)
    @ApiModelProperty(value = "注册地址", required = true, example = "上海市浦东新区张江高科技园区")
    private String registeredAddress;

    @Size(max = 500, message = "实际经营地址长度不能超过500")
    @Column(name = "business_address")
    @ApiModelProperty(value = "实际经营地址", example = "上海市浦东新区张江高科技园区")
    private String businessAddress;

    @Past(message = "成立日期必须为过去时间")
    @Column(name = "establish_date")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "成立日期", example = "2020-01-01")
    private Date establishDate;

    @Column(name = "business_scope", columnDefinition = "TEXT")
    @ApiModelProperty(value = "经营范围", example = "计算机软硬件开发、销售")
    private String businessScope;

    @Size(max = 500, message = "主营品类长度不能超过500")
    @Column(name = "main_categories")
    @ApiModelProperty(value = "主营品类(逗号分隔)", example = "电子产品,办公用品")
    private String mainCategories;

    @NotBlank(message = "业务联系人不能为空")
    @Size(max = 100, message = "业务联系人长度不能超过100")
    @Column(name = "contact_person", nullable = false)
    @ApiModelProperty(value = "业务联系人", required = true, example = "李四")
    private String contactPerson;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^[0-9-]{7,20}$", message = "联系电话格式不正确")
    @Column(name = "contact_phone", nullable = false)
    @ApiModelProperty(value = "联系电话", required = true, example = "021-12345678")
    private String contactPhone;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100")
    @Column(name = "contact_email")
    @ApiModelProperty(value = "联系邮箱", example = "contact@example.com")
    private String contactEmail;

    @Size(max = 255, message = "官网网址长度不能超过255")
    @ApiModelProperty(value = "官网网址", example = "https://www.example.com")
    private String website;

    @NotBlank(message = "开户银行不能为空")
    @Size(max = 100, message = "开户银行长度不能超过100")
    @Column(name = "bank_name", nullable = false)
    @ApiModelProperty(value = "开户银行", required = true, example = "中国工商银行")
    private String bankName;

    @NotBlank(message = "银行账号不能为空")
    @Size(max = 100, message = "银行账号长度不能超过100")
    @Column(name = "bank_account", nullable = false)
    @ApiModelProperty(value = "银行账号", required = true, example = "6222021000001234567")
    private String bankAccount;

    @NotBlank(message = "开户名不能为空")
    @Size(max = 100, message = "开户名长度不能超过100")
    @Column(name = "bank_account_name", nullable = false)
    @ApiModelProperty(value = "开户名", required = true, example = "XX科技有限公司")
    private String bankAccountName;

    @NotBlank(message = "纳税类型不能为空")
    @Size(max = 50, message = "纳税类型长度不能超过50")
    @Column(name = "tax_type", nullable = false)
    @ApiModelProperty(value = "纳税类型", required = true, example = "一般纳税人")
    private String taxType;

    @NotBlank(message = "纳税人识别号不能为空")
    @Size(max = 100, message = "纳税人识别号长度不能超过100")
    @Column(name = "tax_id", nullable = false)
    @ApiModelProperty(value = "纳税人识别号", required = true, example = "91310101MA1FPX1234")
    private String taxId;

    @NotBlank(message = "发票类型不能为空")
    @Size(max = 50, message = "发票类型长度不能超过50")
    @Column(name = "invoice_type", nullable = false)
    @ApiModelProperty(value = "发票类型", required = true, example = "增值税专用发票")
    private String invoiceType;

    @NotBlank(message = "结算周期不能为空")
    @Size(max = 50, message = "结算周期长度不能超过50")
    @Column(name = "settlement_cycle", nullable = false)
    @ApiModelProperty(value = "结算周期", required = true, example = "月结30天")
    private String settlementCycle = "月结30天";

    @NotBlank(message = "结算货币不能为空")
    @Size(max = 10, message = "结算货币长度不能超过10")
    @Column(nullable = false)
    @ApiModelProperty(value = "结算货币", required = true, example = "CNY")
    private String currency = "CNY";

    @Digits(integer = 10, fraction = 2, message = "最小订单金额格式不正确")
    @Column(name = "min_order_amount", precision = 12, scale = 2)
    @ApiModelProperty(value = "最小订单金额", example = "1000.00")
    private BigDecimal minOrderAmount;

    @Min(value = 1, message = "最小订单数量必须大于0")
    @Column(name = "min_order_quantity")
    @ApiModelProperty(value = "最小订单数量", example = "10")
    private Integer minOrderQuantity;

    @Min(value = 1, message = "交货周期必须大于0")
    @Column(name = "lead_time")
    @ApiModelProperty(value = "交货周期(天)", example = "7")
    private Integer leadTime;

    @NotNull(message = "合作开始日期不能为空")
    @Column(name = "cooperation_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "合作开始日期", required = true, example = "2023-01-01")
    private Date cooperationStartDate;

    @Future(message = "合作结束日期必须为将来时间")
    @Column(name = "cooperation_end_date")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "合作结束日期", example = "2025-12-31")
    private Date cooperationEndDate;

    @Future(message = "合同到期日必须为将来时间")
    @Column(name = "contract_expire_date")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(value = "合同到期日", example = "2024-12-31")
    private Date contractExpireDate;

    @DecimalMin(value = "0.00", message = "绩效评分不能小于0")
    @DecimalMax(value = "5.00", message = "绩效评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "绩效评分格式不正确")
    @Column(name = "performance_score", precision = 3, scale = 2)
    @ApiModelProperty(value = "绩效评分(0-5分)", example = "4.50")
    private BigDecimal performanceScore = BigDecimal.valueOf(5.00);

    @DecimalMin(value = "0.00", message = "质量评分不能小于0")
    @DecimalMax(value = "5.00", message = "质量评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "质量评分格式不正确")
    @Column(name = "quality_score", precision = 3, scale = 2)
    @ApiModelProperty(value = "质量评分", example = "4.80")
    private BigDecimal qualityScore = BigDecimal.valueOf(5.00);

    @DecimalMin(value = "0.00", message = "交货评分不能小于0")
    @DecimalMax(value = "5.00", message = "交货评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "交货评分格式不正确")
    @Column(name = "delivery_score", precision = 3, scale = 2)
    @ApiModelProperty(value = "交货评分", example = "4.20")
    private BigDecimal deliveryScore = BigDecimal.valueOf(5.00);

    @DecimalMin(value = "0.00", message = "服务评分不能小于0")
    @DecimalMax(value = "5.00", message = "服务评分不能大于5")
    @Digits(integer = 1, fraction = 2, message = "服务评分格式不正确")
    @Column(name = "service_score", precision = 3, scale = 2)
    @ApiModelProperty(value = "服务评分", example = "4.60")
    private BigDecimal serviceScore = BigDecimal.valueOf(5.00);

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createdAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    @ApiModelProperty(value = "更新人", hidden = true)
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updatedAt;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty(value = "备注")
    private String remark;
}
