package com.cn.taihe.back.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.back.product.vo
 * @Author: 大咖
 * @CreateTime: 2025-11-19  17:25
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class ProductSpuSkuDTO {
  private String spuId;
  private String productNameZh;
  private String productNameEn;
  private String skuId;
  private BigDecimal salePrice;
  private BigDecimal retailPrice;
  private String mainImageUrl;
  private Boolean isFeatured;
  private Boolean isBestSeller;
  private String mainCategory;
}
