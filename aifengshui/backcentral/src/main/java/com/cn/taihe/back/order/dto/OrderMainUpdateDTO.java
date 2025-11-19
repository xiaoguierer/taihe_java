package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单更新参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderMainUpdateDTO", description = "订单更新参数")
public class OrderMainUpdateDTO {

  @NotBlank(message = "订单ID不能为空")
  @ApiModelProperty(value = "订单ID", example = "1234567890abcdef", required = true)
  private String id;

  @ApiModelProperty(value = "状态:1待付款 2待发货 3待收货 4已完成 5已关闭", example = "2")
  private Integer status;

  @ApiModelProperty(value = "订单总金额", example = "199.99")
  private BigDecimal totalAmount;

  @ApiModelProperty(value = "货币", example = "USD")
  private String currency;

  @ApiModelProperty(value = "收货人", example = "张三")
  private String receiverName;

  @ApiModelProperty(value = "电话", example = "13800138000")
  private String receiverPhone;

  @ApiModelProperty(value = "完整收货地址", example = "北京市朝阳区xxx街道xxx号")
  private String receiverAddress;

  @ApiModelProperty(value = "付款时间", example = "2024-01-01T10:30:00")
  private LocalDateTime paymentTime;

  @ApiModelProperty(value = "发货时间", example = "2024-01-02T09:00:00")
  private LocalDateTime shippingTime;

  @ApiModelProperty(value = "完成时间", example = "2024-01-05T15:00:00")
  private LocalDateTime completedTime;
}
