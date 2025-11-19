package com.cn.taihe.back.product.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.back.product.vo
 * @Author: 大咖
 * @CreateTime: 2025-11-19  15:11
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@ApiModel("根据情感意图查找商品标签以及统计")
public class ProductCategoryTagCountDTO {
  private String id;
  private String intentId;
  private String tagNameZh;
  private String tagNameEn;
  private String tagKey;           // 新增
  private String tagNameAr;        // 新增
  private String shortDescEn;      // 新增
  private String shortDescZh;      // 新增
  private String colorCode;
  private Integer productCount;
}
