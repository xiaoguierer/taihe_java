package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单支付新增参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderPaymentCreateDTO", description = "订单支付新增参数")
public class OrderPaymentCreateDTO {

  @NotBlank(message = "订单ID不能为空")
  @ApiModelProperty(value = "订单ID", example = "order123456", required = true)
  private String orderId;

  @NotBlank(message = "支付方式不能为空")
  @ApiModelProperty(value = "支付方式", example = "ALIPAY", required = true)
  private String paymentMethod;

  @NotNull(message = "支付金额不能为空")
  @ApiModelProperty(value = "支付金额", example = "199.99", required = true)
  private BigDecimal paymentAmount;

  @ApiModelProperty(value = "状态:pending,paid,failed,refunded", example = "pending")
  private String paymentStatus = "pending";

  @ApiModelProperty(value = "支付平台交易号", example = "202411180000100001")
  private String gatewayTradeNo;

  @ApiModelProperty(value = "支付成功时间", example = "2024-01-01T10:30:00")
  private java.time.LocalDateTime paidTime;
}
