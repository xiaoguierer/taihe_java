package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单商品新增参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderItemCreateDTO", description = "订单商品新增参数")
public class OrderItemCreateDTO {

  @NotBlank(message = "订单ID不能为空")
  @ApiModelProperty(value = "订单ID", example = "order123456", required = true)
  private String orderId;

  @NotBlank(message = "商品SKU ID不能为空")
  @ApiModelProperty(value = "商品SKU ID", example = "sku123456", required = true)
  private String skuId;

  @NotBlank(message = "商品名称不能为空")
  @ApiModelProperty(value = "商品名称快照", example = "iPhone 15 Pro Max", required = true)
  private String productName;

  @NotNull(message = "单价不能为空")
  @ApiModelProperty(value = "单价快照", example = "999.99", required = true)
  private BigDecimal unitPrice;

  @NotNull(message = "购买数量不能为空")
  @ApiModelProperty(value = "购买数量", example = "2", required = true)
  private Integer quantity;

  @NotNull(message = "商品总价不能为空")
  @ApiModelProperty(value = "商品总价", example = "1999.98", required = true)
  private BigDecimal totalPrice;

  @ApiModelProperty(value = "已退款数量", example = "0")
  private Integer refundQuantity = 0;

  @ApiModelProperty(value = "已退款金额", example = "0.00")
  private BigDecimal refundAmount = BigDecimal.ZERO;

  @ApiModelProperty(value = "退款状态:0无退款 1部分退款 2全部退款", example = "0")
  private Integer refundStatus = 0;
}
