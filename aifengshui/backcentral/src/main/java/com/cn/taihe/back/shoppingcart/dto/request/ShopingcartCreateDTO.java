package com.cn.taihe.back.shoppingcart.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车新增参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ShopingcartCreateDTO", description = "购物车新增参数")
public class ShopingcartCreateDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @NotNull(message = "用户ID不能为空")
  @ApiModelProperty(value = "用户ID", required = true, example = "user123")
  private String userId;

  /**
   * 商品SKU ID
   */
  @NotNull(message = "商品SKU ID不能为空")
  @ApiModelProperty(value = "商品SKU ID", required = true, example = "sku123")
  private String skuId;

  /**
   * 购买数量
   */
  @ApiModelProperty(value = "购买数量", example = "1")
  private Integer quantity = 1;

  /**
   * 是否选中: 0-否, 1-是
   */
  @ApiModelProperty(value = "是否选中: 0-否, 1-是", example = "1")
  private Integer selected = 1;

  /**
   * 加入时单价
   */
  @NotNull(message = "单价不能为空")
  @ApiModelProperty(value = "加入时单价", required = true, example = "99.99")
  private BigDecimal unitPrice;

  /**
   * 货币
   */
  @ApiModelProperty(value = "货币", example = "USD")
  private String currency = "USD";

}
