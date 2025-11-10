package com.cn.taihe.back.product.entity;
import com.baomidou.mybatisplus.annotation.*;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 商品SKU表(关联图片ID) 实体类
 *
 * @author ADMIN
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_sku")
@ApiModel(value = "ProductSku", description = "商品SKU表(关联图片ID)")
public class ProductSku {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键ID", example = "12345678-1234-1234-1234-123456789012")
  private String id;

  @Column(name = "spu_id", nullable = false)
  @ApiModelProperty(value = "SPU ID", required = true)
  private String spuId;

  @Column(name = "sku_code", nullable = false)
  @ApiModelProperty(value = "SKU编码", required = true)
  private String skuCode;

  @Column(name = "sku_name_en", nullable = false)
  @ApiModelProperty(value = "英文SKU名称", required = true)
  private String skuNameEn;

  @Column(name = "sku_name_zh", nullable = false)
  @ApiModelProperty(value = "中文SKU名称", required = true)
  private String skuNameZh;

  @Column(name = "sku_name_ar")
  @ApiModelProperty(value = "阿拉伯语SKU名称")
  private String skuNameAr;

  @Column(name = "variant_description_en")
  @ApiModelProperty(value = "英文变体描述")
  private String variantDescriptionEn;

  @Column(name = "variant_description_zh")
  @ApiModelProperty(value = "中文变体描述")
  private String variantDescriptionZh;

  @Column(name = "variant_description_ar")
  @ApiModelProperty(value = "阿拉伯语变体描述")
  private String variantDescriptionAr;

  @Column(name = "variant_yuyi_en")
  @ApiModelProperty(value = "英文寓意描述")
  private String variantYuyiEn;

  @Column(name = "variant_yuyi_zh")
  @ApiModelProperty(value = "中文寓意描述")
  private String variantYuyiZh;

  @Column(name = "variant_yuyi_ar")
  @ApiModelProperty(value = "阿拉伯语寓意描述")
  private String variantYuyiAr;

  @Column(name = "primary_material", nullable = false)
  @ApiModelProperty(value = "主要材质", required = true)
  private String primaryMaterial;

  @Column(name = "material_purity")
  @ApiModelProperty(value = "材质纯度")
  private String materialPurity;

  @Column(name = "material_color")
  @ApiModelProperty(value = "材质颜色")
  private String materialColor;

  @Column(name = "material_finish")
  @ApiModelProperty(value = "表面处理")
  private String materialFinish;

  @Column(name = "material_thickness_mm")
  @ApiModelProperty(value = "材质厚度(mm)")
  private BigDecimal materialThicknessMm;

  @Column(name = "gemstone_type")
  @ApiModelProperty(value = "宝石类型")
  private String gemstoneType;

  @Column(name = "gemstone_shape")
  @ApiModelProperty(value = "宝石形状")
  private String gemstoneShape;

  @Column(name = "gemstone_cut")
  @ApiModelProperty(value = "宝石切工")
  private String gemstoneCut;

  @Column(name = "gemstone_size_mm")
  @ApiModelProperty(value = "宝石尺寸(mm)")
  private BigDecimal gemstoneSizeMm;

  @Column(name = "gemstone_weight_ct")
  @ApiModelProperty(value = "宝石重量(克拉)")
  private BigDecimal gemstoneWeightCt;

  @Column(name = "gemstone_quality")
  @ApiModelProperty(value = "宝石品质")
  private String gemstoneQuality;

  @Column(name = "gemstone_count")
  @ApiModelProperty(value = "宝石数量")
  private Integer gemstoneCount;

  @Column(name = "length_cm")
  @ApiModelProperty(value = "长度(cm)")
  private BigDecimal lengthCm;

  @Column(name = "width_cm")
  @ApiModelProperty(value = "宽度(cm)")
  private BigDecimal widthCm;

  @Column(name = "height_cm")
  @ApiModelProperty(value = "高度(cm)")
  private BigDecimal heightCm;

  @Column(name = "chain_length_cm")
  @ApiModelProperty(value = "链长(cm)")
  private BigDecimal chainLengthCm;

  @Column(name = "pendant_size_mm")
  @ApiModelProperty(value = "吊坠尺寸(mm)")
  private BigDecimal pendantSizeMm;

  @Column(name = "total_weight_g", nullable = false)
  @ApiModelProperty(value = "总重量(克)", required = true)
  private BigDecimal totalWeightG;

  @Column(name = "metal_weight_g")
  @ApiModelProperty(value = "金属重量(克)")
  private BigDecimal metalWeightG;

  @Column(name = "gemstone_weight_g")
  @ApiModelProperty(value = "宝石重量(克)")
  private BigDecimal gemstoneWeightG;

  @Column(name = "craftsmanship_level")
  @ApiModelProperty(value = "工艺等级")
  private String craftsmanshipLevel;

  @Column(name = "setting_technique")
  @ApiModelProperty(value = "镶嵌工艺")
  private String settingTechnique;

  @Column(name = "clasp_type")
  @ApiModelProperty(value = "扣类型")
  private String claspType;

  @Column(name = "chain_type")
  @ApiModelProperty(value = "链类型")
  private String chainType;

  @Column(name = "suitable_gender")
  @ApiModelProperty(value = "适用性别:1 男性,2 女性,3 中性")
  private Integer suitableGender;

  @Column(name = "suitable_age_min")
  @ApiModelProperty(value = "适用最小年龄")
  private Integer suitableAgeMin;

  @Column(name = "suitable_age_max")
  @ApiModelProperty(value = "适用最大年龄")
  private Integer suitableAgeMax;

  @Column(name = "size_standard")
  @ApiModelProperty(value = "尺寸标准")
  private String sizeStandard;

  @Column(name = "stock_quantity")
  @ApiModelProperty(value = "库存数量")
  private Integer stockQuantity;

  @Column(name = "reserved_quantity")
  @ApiModelProperty(value = "预留库存")
  private Integer reservedQuantity;

  @Column(name = "safety_stock")
  @ApiModelProperty(value = "安全库存")
  private Integer safetyStock;

  @Column(name = "reorder_point")
  @ApiModelProperty(value = "补货点")
  private Integer reorderPoint;

  @Column(name = "stock_status")
  @ApiModelProperty(value = "库存状态:1有货,2 低库存,3 缺货")
  private Integer stockStatus;

  @Column(name = "low_stock_alert")
  @ApiModelProperty(value = "低库存预警")
  private Boolean lowStockAlert;

  @Column(name = "last_stock_update")
  @ApiModelProperty(value = "最后库存更新")
  private LocalDateTime lastStockUpdate;

  @Column(name = "cost_price", nullable = false)
  @ApiModelProperty(value = "成本价", required = true)
  private BigDecimal costPrice;

  @Column(name = "retail_price", nullable = false)
  @ApiModelProperty(value = "零售价", required = true)
  private BigDecimal retailPrice;

  @Column(name = "sale_price")
  @ApiModelProperty(value = "促销价")
  private BigDecimal salePrice;

  @Column(name = "member_price")
  @ApiModelProperty(value = "会员价")
  private BigDecimal memberPrice;

  @Column(name = "price_currency")
  @ApiModelProperty(value = "价格货币")
  private String priceCurrency;

  @Column(name = "discount_rate")
  @ApiModelProperty(value = "折扣率(%)")
  private BigDecimal discountRate;

  @Column(name = "discount_amount")
  @ApiModelProperty(value = "折扣金额")
  private BigDecimal discountAmount;

  @Column(name = "discount_start_date")
  @ApiModelProperty(value = "折扣开始日期")
  private LocalDate discountStartDate;

  @Column(name = "discount_end_date")
  @ApiModelProperty(value = "折扣结束日期")
  private LocalDate discountEndDate;

  @Column(name = "tax_rate")
  @ApiModelProperty(value = "税率(%)")
  private BigDecimal taxRate;

  @Column(name = "price_adjustment_reason")
  @ApiModelProperty(value = "价格调整原因")
  private String priceAdjustmentReason;

  @Column(name = "last_price_update")
  @ApiModelProperty(value = "最后价格更新")
  private LocalDateTime lastPriceUpdate;

  @Column(name = "main_image_id")
  @ApiModelProperty(value = "主图ID")
  private String mainImageId;

  @Column(name = "image_1_id")
  @ApiModelProperty(value = "图片1 ID")
  private String image1Id;

  @Column(name = "image_2_id")
  @ApiModelProperty(value = "图片2 ID")
  private String image2Id;

  @Column(name = "image_3_id")
  @ApiModelProperty(value = "图片3 ID")
  private String image3Id;

  @Column(name = "image_4_id")
  @ApiModelProperty(value = "图片4 ID")
  private String image4Id;

  @Column(name = "image_5_id")
  @ApiModelProperty(value = "图片5 ID")
  private String image5Id;

  @Column(name = "main_image_url")
  @ApiModelProperty(value = "主图url")
  private String mainImageUrl;

  @Column(name = "image_1_url")
  @ApiModelProperty(value = "图片1 url")
  private String image1Url;

  @Column(name = "image_2_url")
  @ApiModelProperty(value = "图片2 url")
  private String image2Url;

  @Column(name = "image_3_url")
  @ApiModelProperty(value = "图片3 url")
  private String image3Url;

  @Column(name = "image_4_url")
  @ApiModelProperty(value = "图片4 url")
  private String image4Url;

  @Column(name = "image_5_url")
  @ApiModelProperty(value = "图片5 url")
  private String image5Url;

  @Column(name = "available_date", nullable = false)
  @ApiModelProperty(value = "可售开始日期", required = true)
  private LocalDate availableDate;

  @Column(name = "available_end_date")
  @ApiModelProperty(value = "可售结束日期")
  private LocalDate availableEndDate;

  @Column(name = "is_new_arrival")
  @ApiModelProperty(value = "是否新品")
  private Boolean isNewArrival;

  @Column(name = "status")
  @ApiModelProperty(value = "状态:0 草稿,1 上架,2 下架")
  private Integer status;

  @Column(name = "is_available")
  @ApiModelProperty(value = "是否可售")
  private Boolean isAvailable;

  @Column(name = "is_featured")
  @ApiModelProperty(value = "是否推荐")
  private Boolean isFeatured;

  @Column(name = "is_best_seller")
  @ApiModelProperty(value = "是否畅销")
  private Boolean isBestSeller;

  @Column(name = "sort_order")
  @ApiModelProperty(value = "排序值")
  private Integer sortOrder;

  @Column(name = "visibility")
  @ApiModelProperty(value = "可见性:1 公开,2 隐藏")
  private Integer visibility;

  @CreatedBy
  @Column(name = "created_by", nullable = false, updatable = false)
  @ApiModelProperty(value = "创建人ID")
  private Long createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", nullable = false)
  @ApiModelProperty(value = "更新人ID")
  private Long updatedBy;

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createdTime;

  @LastModifiedDate
  @Column(name = "updated_time", nullable = false)
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updatedTime;

  // 虚拟字段 - 需要在查询时手动映射
  @Transient
  @ApiModelProperty(value = "可用库存")
  private Integer availableQuantity;

  @Transient
  @ApiModelProperty(value = "最终售价")
  private BigDecimal finalPrice;
}
