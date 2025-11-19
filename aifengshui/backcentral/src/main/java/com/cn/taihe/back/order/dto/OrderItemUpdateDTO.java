package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 订单商品更新参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderItemUpdateDTO", description = "订单商品更新参数")
public class OrderItemUpdateDTO {

  @NotBlank(message = "订单商品ID不能为空")
  @ApiModelProperty(value = "订单商品ID", example = "1234567890abcdef", required = true)
  private String id;

  @ApiModelProperty(value = "商品名称快照", example = "iPhone 15 Pro Max")
  private String productName;

  @ApiModelProperty(value = "单价快照", example = "999.99")
  private BigDecimal unitPrice;

  @ApiModelProperty(value = "购买数量", example = "2")
  private Integer quantity;

  @ApiModelProperty(value = "商品总价", example = "1999.98")
  private BigDecimal totalPrice;

  @ApiModelProperty(value = "已退款数量", example = "1")
  private Integer refundQuantity;

  @ApiModelProperty(value = "已退款金额", example = "999.99")
  private BigDecimal refundAmount;

  @ApiModelProperty(value = "退款状态:0无退款 1部分退款 2全部退款", example = "1")
  private Integer refundStatus;
}
