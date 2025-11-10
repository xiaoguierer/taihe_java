package com.cn.taihe.back.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 商品SKU查询条件DTO
 *
 * @author ADMIN
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductSkuQueryDTO", description = "商品SKU查询条件DTO")
public class ProductSkuQueryDTO {

  @ApiModelProperty(value = "SPU ID")
  private String spuId;

  @ApiModelProperty(value = "SKU编码（模糊查询）")
  private String skuCode;

  @ApiModelProperty(value = "英文SKU名称（模糊查询）")
  private String skuNameEn;

  @ApiModelProperty(value = "中文SKU名称（模糊查询）")
  private String skuNameZh;

  @ApiModelProperty(value = "阿拉伯语SKU名称（模糊查询）")
  private String skuNameAr;

  @ApiModelProperty(value = "主要材质（模糊查询）")
  private String primaryMaterial;

  @ApiModelProperty(value = "材质纯度")
  private String materialPurity;

  @ApiModelProperty(value = "材质颜色")
  private String materialColor;

  @ApiModelProperty(value = "宝石类型")
  private String gemstoneType;

  @ApiModelProperty(value = "宝石形状")
  private String gemstoneShape;

  @ApiModelProperty(value = "适用性别:1 男性,2 女性,3 中性")
  private Integer suitableGender;

  @ApiModelProperty(value = "适用最小年龄")
  private Integer suitableAgeMin;

  @ApiModelProperty(value = "适用最大年龄")
  private Integer suitableAgeMax;

  @ApiModelProperty(value = "库存状态:1有货,2 低库存,3 缺货")
  private Integer stockStatus;

  @ApiModelProperty(value = "低库存预警")
  private Boolean lowStockAlert;

  @ApiModelProperty(value = "价格货币")
  private String priceCurrency;

  @ApiModelProperty(value = "成本价最小值")
  private BigDecimal minCostPrice;

  @ApiModelProperty(value = "成本价最大值")
  private BigDecimal maxCostPrice;

  @ApiModelProperty(value = "零售价最小值")
  private BigDecimal minRetailPrice;

  @ApiModelProperty(value = "零售价最大值")
  private BigDecimal maxRetailPrice;

  @ApiModelProperty(value = "总重量最小值(g)")
  private BigDecimal minTotalWeightG;

  @ApiModelProperty(value = "总重量最大值(g)")
  private BigDecimal maxTotalWeightG;

  @ApiModelProperty(value = "库存数量最小值")
  private Integer minStockQuantity;

  @ApiModelProperty(value = "库存数量最大值")
  private Integer maxStockQuantity;

  @ApiModelProperty(value = "可售开始日期起始")
  private LocalDate availableDateStart;

  @ApiModelProperty(value = "可售开始日期结束")
  private LocalDate availableDateEnd;

  @ApiModelProperty(value = "可售结束日期起始")
  private LocalDate availableEndDateStart;

  @ApiModelProperty(value = "可售结束日期结束")
  private LocalDate availableEndDateEnd;

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

  @ApiModelProperty(value = "可见性:1 公开,2 隐藏")
  private Integer visibility;

  @ApiModelProperty(value = "创建时间起始")
  private LocalDate createdTimeStart;

  @ApiModelProperty(value = "创建时间结束")
  private LocalDate createdTimeEnd;

  @ApiModelProperty(value = "更新时间起始")
  private LocalDate updatedTimeStart;

  @ApiModelProperty(value = "更新时间结束")
  private LocalDate updatedTimeEnd;

  @ApiModelProperty(value = "排序字段：sku_code, sku_name_zh, retail_price, total_weight_g, stock_quantity, created_time, updated_time等")
  private String sortField;

  @ApiModelProperty(value = "排序方向：asc, desc")
  private String sortOrder;
}
