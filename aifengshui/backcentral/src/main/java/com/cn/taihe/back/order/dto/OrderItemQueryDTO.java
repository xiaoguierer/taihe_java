package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单商品查询参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderItemQueryDTO", description = "订单商品查询参数")
public class OrderItemQueryDTO {

  @ApiModelProperty(value = "订单ID")
  private String orderId;

  @ApiModelProperty(value = "商品SKU ID")
  private String skuId;

  @ApiModelProperty(value = "商品名称（模糊查询）")
  private String productName;

  @ApiModelProperty(value = "单价最小值")
  private BigDecimal minUnitPrice;

  @ApiModelProperty(value = "单价最大值")
  private BigDecimal maxUnitPrice;

  @ApiModelProperty(value = "购买数量最小值")
  private Integer minQuantity;

  @ApiModelProperty(value = "购买数量最大值")
  private Integer maxQuantity;

  @ApiModelProperty(value = "商品总价最小值")
  private BigDecimal minTotalPrice;

  @ApiModelProperty(value = "商品总价最大值")
  private BigDecimal maxTotalPrice;

  @ApiModelProperty(value = "退款状态:0无退款 1部分退款 2全部退款")
  private Integer refundStatus;

  @ApiModelProperty(value = "创建时间开始")
  private LocalDateTime createdTimeStart;

  @ApiModelProperty(value = "创建时间结束")
  private LocalDateTime createdTimeEnd;
}
