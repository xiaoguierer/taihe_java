package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单退单查询参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderRefundQueryDTO", description = "订单退单查询参数")
public class OrderRefundQueryDTO {

  @ApiModelProperty(value = "退单号（模糊查询）")
  private String refundSn;

  @ApiModelProperty(value = "订单ID")
  private String orderId;

  @ApiModelProperty(value = "用户ID")
  private String userId;

  @ApiModelProperty(value = "退单类型:1仅退款 2退货退款")
  private Integer refundType;

  @ApiModelProperty(value = "退单原因（模糊查询）")
  private String refundReason;

  @ApiModelProperty(value = "申请退款金额最小值")
  private BigDecimal minApplyAmount;

  @ApiModelProperty(value = "申请退款金额最大值")
  private BigDecimal maxApplyAmount;

  @ApiModelProperty(value = "实际退款金额最小值")
  private BigDecimal minActualAmount;

  @ApiModelProperty(value = "实际退款金额最大值")
  private BigDecimal maxActualAmount;

  @ApiModelProperty(value = "状态:1申请中 2审核通过 3审核拒绝 4退款中 5退款成功 6退款失败")
  private Integer refundStatus;

  @ApiModelProperty(value = "审核人ID")
  private String approverId;

  @ApiModelProperty(value = "退货物流公司")
  private String returnLogistics;

  @ApiModelProperty(value = "退货物流单号")
  private String returnTrackingNo;

  @ApiModelProperty(value = "申请时间开始")
  private LocalDateTime applyTimeStart;

  @ApiModelProperty(value = "申请时间结束")
  private LocalDateTime applyTimeEnd;

  @ApiModelProperty(value = "审核时间开始")
  private LocalDateTime reviewTimeStart;

  @ApiModelProperty(value = "审核时间结束")
  private LocalDateTime reviewTimeEnd;

  @ApiModelProperty(value = "退款时间开始")
  private LocalDateTime refundTimeStart;

  @ApiModelProperty(value = "退款时间结束")
  private LocalDateTime refundTimeEnd;
}
