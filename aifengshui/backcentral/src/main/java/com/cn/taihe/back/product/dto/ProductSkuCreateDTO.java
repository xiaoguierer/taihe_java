package com.cn.taihe.back.product.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 商品SKU新增DTO
 *
 * @author ADMIN
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductSkuCreateDTO", description = "商品SKU新增DTO")
public class ProductSkuCreateDTO {

  @NotBlank(message = "SPU ID不能为空")
  @ApiModelProperty(value = "SPU ID", required = true)
  private String spuId;

  @NotBlank(message = "SKU编码不能为空")
  @ApiModelProperty(value = "SKU编码", required = true)
  private String skuCode;

  @NotBlank(message = "英文SKU名称不能为空")
  @ApiModelProperty(value = "英文SKU名称", required = true)
  private String skuNameEn;

  @NotBlank(message = "中文SKU名称不能为空")
  @ApiModelProperty(value = "中文SKU名称", required = true)
  private String skuNameZh;

  @ApiModelProperty(value = "阿拉伯语SKU名称")
  private String skuNameAr;

  @ApiModelProperty(value = "英文变体描述")
  private String variantDescriptionEn;

  @ApiModelProperty(value = "中文变体描述")
  private String variantDescriptionZh;

  @ApiModelProperty(value = "阿拉伯语变体描述")
  private String variantDescriptionAr;

  @ApiModelProperty(value = "英文寓意描述")
  private String variantYuyiEn;

  @ApiModelProperty(value = "中文寓意描述")
  private String variantYuyiZh;

  @ApiModelProperty(value = "阿拉伯语寓意描述")
  private String variantYuyiAr;

  @NotBlank(message = "主要材质不能为空")
  @ApiModelProperty(value = "主要材质", required = true)
  private String primaryMaterial;

  @ApiModelProperty(value = "材质纯度")
  private String materialPurity;

  @ApiModelProperty(value = "材质颜色")
  private String materialColor;

  @ApiModelProperty(value = "表面处理")
  private String materialFinish;

  @ApiModelProperty(value = "材质厚度(mm)")
  private BigDecimal materialThicknessMm;

  @ApiModelProperty(value = "宝石类型")
  private String gemstoneType;

  @ApiModelProperty(value = "宝石形状")
  private String gemstoneShape;

  @ApiModelProperty(value = "宝石切工")
  private String gemstoneCut;

  @ApiModelProperty(value = "宝石尺寸(mm)")
  private BigDecimal gemstoneSizeMm;

  @ApiModelProperty(value = "宝石重量(克拉)")
  private BigDecimal gemstoneWeightCt;

  @ApiModelProperty(value = "宝石品质")
  private String gemstoneQuality;

  @ApiModelProperty(value = "宝石数量")
  private Integer gemstoneCount;

  @ApiModelProperty(value = "长度(cm)")
  private BigDecimal lengthCm;

  @ApiModelProperty(value = "宽度(cm)")
  private BigDecimal widthCm;

  @ApiModelProperty(value = "高度(cm)")
  private BigDecimal heightCm;

  @ApiModelProperty(value = "链长(cm)")
  private BigDecimal chainLengthCm;

  @ApiModelProperty(value = "吊坠尺寸(mm)")
  private BigDecimal pendantSizeMm;

  @NotNull(message = "总重量不能为空")
  @ApiModelProperty(value = "总重量(克)", required = true)
  private BigDecimal totalWeightG;

  @ApiModelProperty(value = "金属重量(克)")
  private BigDecimal metalWeightG;

  @ApiModelProperty(value = "宝石重量(克)")
  private BigDecimal gemstoneWeightG;

  @ApiModelProperty(value = "工艺等级")
  private String craftsmanshipLevel;

  @ApiModelProperty(value = "镶嵌工艺")
  private String settingTechnique;

  @ApiModelProperty(value = "扣类型")
  private String claspType;

  @ApiModelProperty(value = "链类型")
  private String chainType;

  @ApiModelProperty(value = "适用性别:1 男性,2 女性,3 中性")
  private Integer suitableGender;

  @ApiModelProperty(value = "适用最小年龄")
  private Integer suitableAgeMin;

  @ApiModelProperty(value = "适用最大年龄")
  private Integer suitableAgeMax;

  @ApiModelProperty(value = "尺寸标准")
  private String sizeStandard;

  @ApiModelProperty(value = "库存数量")
  private Integer stockQuantity;

  @ApiModelProperty(value = "预留库存")
  private Integer reservedQuantity;

  @ApiModelProperty(value = "安全库存")
  private Integer safetyStock;

  @ApiModelProperty(value = "补货点")
  private Integer reorderPoint;

  @ApiModelProperty(value = "库存状态:1有货,2 低库存,3 缺货")
  private Integer stockStatus;

  @ApiModelProperty(value = "低库存预警")
  private Boolean lowStockAlert;

  @NotNull(message = "成本价不能为空")
  @ApiModelProperty(value = "成本价", required = true)
  private BigDecimal costPrice;

  @NotNull(message = "零售价不能为空")
  @ApiModelProperty(value = "零售价", required = true)
  private BigDecimal retailPrice;

  @ApiModelProperty(value = "促销价")
  private BigDecimal salePrice;

  @ApiModelProperty(value = "会员价")
  private BigDecimal memberPrice;

  @ApiModelProperty(value = "价格货币")
  private String priceCurrency;

  @ApiModelProperty(value = "折扣率(%)")
  private BigDecimal discountRate;

  @ApiModelProperty(value = "折扣金额")
  private BigDecimal discountAmount;

  @ApiModelProperty(value = "折扣开始日期")
  private LocalDate discountStartDate;

  @ApiModelProperty(value = "折扣结束日期")
  private LocalDate discountEndDate;

  @ApiModelProperty(value = "税率(%)")
  private BigDecimal taxRate;

  @ApiModelProperty(value = "价格调整原因")
  private String priceAdjustmentReason;

  @ApiModelProperty(value = "主图ID")
  private String mainImageId;

  @ApiModelProperty(value = "图片1 ID")
  private String image1Id;

  @ApiModelProperty(value = "图片2 ID")
  private String image2Id;

  @ApiModelProperty(value = "图片3 ID")
  private String image3Id;

  @ApiModelProperty(value = "图片4 ID")
  private String image4Id;

  @ApiModelProperty(value = "图片5 ID")
  private String image5Id;

  @ApiModelProperty(value = "主图url")
  private String mainImageUrl;

  @ApiModelProperty(value = "图片1 url")
  private String image1Url;

  @ApiModelProperty(value = "图片2 url")
  private String image2Url;

  @ApiModelProperty(value = "图片3 url")
  private String image3Url;

  @ApiModelProperty(value = "图片4 url")
  private String image4Url;

  @ApiModelProperty(value = "图片5 url")
  private String image5Url;

  @NotNull(message = "可售开始日期不能为空")
  @ApiModelProperty(value = "可售开始日期", required = true)
  private LocalDate availableDate;

  @ApiModelProperty(value = "可售结束日期")
  private LocalDate availableEndDate;

  @ApiModelProperty(value = "是否新品")
  private Boolean isNewArrival;

  @ApiModelProperty(value = "状态:0 草稿,1 上架,2 下架")
  private Integer status;

  @ApiModelProperty(value = "是否可售")
  private Boolean isAvailable;

  @ApiModelProperty(value = "是否推荐")
  private Boolean isFeatured;

  @ApiModelProperty(value = "是否畅销")
  private Boolean isBestSeller;

  @ApiModelProperty(value = "排序值")
  private Integer sortOrder;

  @ApiModelProperty(value = "可见性:1 公开,2 隐藏")
  private Integer visibility;

  @NotNull(message = "创建人ID不能为空")
  @ApiModelProperty(value = "创建人ID", required = true)
  private Long createdBy;

  @NotNull(message = "更新人ID不能为空")
  @ApiModelProperty(value = "更新人ID", required = true)
  private Long updatedBy;
}
