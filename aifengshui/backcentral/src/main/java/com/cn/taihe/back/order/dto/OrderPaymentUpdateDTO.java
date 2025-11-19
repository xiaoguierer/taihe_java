package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单支付更新参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderPaymentUpdateDTO", description = "订单支付更新参数")
public class OrderPaymentUpdateDTO {

  @NotBlank(message = "支付ID不能为空")
  @ApiModelProperty(value = "支付ID", example = "1234567890abcdef", required = true)
  private String id;

  @ApiModelProperty(value = "支付方式", example = "ALIPAY")
  private String paymentMethod;

  @ApiModelProperty(value = "支付金额", example = "199.99")
  private BigDecimal paymentAmount;

  @ApiModelProperty(value = "状态:pending,paid,failed,refunded", example = "paid")
  private String paymentStatus;

  @ApiModelProperty(value = "支付平台交易号", example = "202411180000100001")
  private String gatewayTradeNo;

  @ApiModelProperty(value = "支付成功时间", example = "2024-01-01T10:30:00")
  private LocalDateTime paidTime;
}
