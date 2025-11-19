package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单支付查询参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderPaymentQueryDTO", description = "订单支付查询参数")
public class OrderPaymentQueryDTO {

  @ApiModelProperty(value = "订单ID")
  private String orderId;

  @ApiModelProperty(value = "支付方式")
  private String paymentMethod;

  @ApiModelProperty(value = "支付金额最小值")
  private BigDecimal minPaymentAmount;

  @ApiModelProperty(value = "支付金额最大值")
  private BigDecimal maxPaymentAmount;

  @ApiModelProperty(value = "状态:pending,paid,failed,refunded")
  private String paymentStatus;

  @ApiModelProperty(value = "支付平台交易号")
  private String gatewayTradeNo;

  @ApiModelProperty(value = "创建时间开始")
  private LocalDateTime createdTimeStart;

  @ApiModelProperty(value = "创建时间结束")
  private LocalDateTime createdTimeEnd;

  @ApiModelProperty(value = "支付成功时间开始")
  private LocalDateTime paidTimeStart;

  @ApiModelProperty(value = "支付成功时间结束")
  private LocalDateTime paidTimeEnd;

  @ApiModelProperty(value = "更新时间开始")
  private LocalDateTime updatedTimeStart;

  @ApiModelProperty(value = "更新时间结束")
  private LocalDateTime updatedTimeEnd;
}
