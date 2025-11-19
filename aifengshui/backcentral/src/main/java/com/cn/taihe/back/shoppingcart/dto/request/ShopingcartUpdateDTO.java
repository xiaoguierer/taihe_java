package com.cn.taihe.back.shoppingcart.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车修改参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ShopingcartUpdateDTO", description = "购物车修改参数")
public class ShopingcartUpdateDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 购物车项ID
   */
  @NotNull(message = "购物车项ID不能为空")
  @ApiModelProperty(value = "购物车项ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
  private String id;

  /**
   * 用户ID
   */
  @ApiModelProperty(value = "用户ID", example = "user123")
  private String userId;

  /**
   * 商品SKU ID
   */
  @ApiModelProperty(value = "商品SKU ID", example = "sku123")
  private String skuId;

  /**
   * 购买数量
   */
  @ApiModelProperty(value = "购买数量", example = "2")
  private Integer quantity;

  /**
   * 是否选中: 0-否, 1-是
   */
  @ApiModelProperty(value = "是否选中: 0-否, 1-是", example = "1")
  private Integer selected;

  /**
   * 加入时单价
   */
  @ApiModelProperty(value = "加入时单价", example = "99.99")
  private BigDecimal unitPrice;

  /**
   * 货币
   */
  @ApiModelProperty(value = "货币", example = "USD")
  private String currency;

}
