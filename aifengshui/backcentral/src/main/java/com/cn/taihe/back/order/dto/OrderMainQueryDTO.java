package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单查询参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderMainQueryDTO", description = "订单查询参数")
public class OrderMainQueryDTO {

  @ApiModelProperty(value = "订单号（模糊查询）")
  private String orderSn;

  @ApiModelProperty(value = "用户ID")
  private String userId;

  @ApiModelProperty(value = "状态:1待付款 2待发货 3待收货 4已完成 5已关闭")
  private Integer status;

  @ApiModelProperty(value = "订单总金额最小值")
  private BigDecimal minTotalAmount;

  @ApiModelProperty(value = "订单总金额最大值")
  private BigDecimal maxTotalAmount;

  @ApiModelProperty(value = "货币")
  private String currency;

  @ApiModelProperty(value = "收货人姓名（模糊查询）")
  private String receiverName;

  @ApiModelProperty(value = "收货人电话（模糊查询）")
  private String receiverPhone;

  @ApiModelProperty(value = "下单时间开始")
  private LocalDateTime createdTimeStart;

  @ApiModelProperty(value = "下单时间结束")
  private LocalDateTime createdTimeEnd;

  @ApiModelProperty(value = "付款时间开始")
  private LocalDateTime paymentTimeStart;

  @ApiModelProperty(value = "付款时间结束")
  private LocalDateTime paymentTimeEnd;

  @ApiModelProperty(value = "发货时间开始")
  private LocalDateTime shippingTimeStart;

  @ApiModelProperty(value = "发货时间结束")
  private LocalDateTime shippingTimeEnd;

  @ApiModelProperty(value = "完成时间开始")
  private LocalDateTime completedTimeStart;

  @ApiModelProperty(value = "完成时间结束")
  private LocalDateTime completedTimeEnd;
}
