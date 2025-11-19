package com.cn.taihe.back.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.back.product.vo
 * @Author: 大咖
 * @CreateTime: 2025-11-19  20:33
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class ProductspuByEmotionAndTagId {
  private String id;
  private String skuCode;
  private String skuNameZh;
  private String skuNameEn;
  private BigDecimal salePrice;
  private BigDecimal retailPrice;
  private BigDecimal finalPrice;
  private String mainImageUrl;
  private String image1Url;
  private String image2Url;
  private Integer stockStatus;
  private Boolean isFeatured;
  private Boolean isBestSeller;
  private Boolean isNewArrival;

  // SPU信息
  private String spuId;
  private String spuNameZh;
  private String spuNameEn;
  private String spuImageUrl;
  private String fullDescriptionZh;
  private String fullDescriptionEn;

  // 标签信息
  private String emotionalIntents;  // 情感意图标签
  private String wuxingTags;        // 五行属性标签
  private String categoryTags;      // 品类标签
}
