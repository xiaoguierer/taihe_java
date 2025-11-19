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
 * 订单退单更新参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderRefundUpdateDTO", description = "订单退单更新参数")
public class OrderRefundUpdateDTO {

  @NotBlank(message = "退单ID不能为空")
  @ApiModelProperty(value = "退单ID", example = "1234567890abcdef", required = true)
  private String id;

  @ApiModelProperty(value = "实际退款金额", example = "199.99")
  private BigDecimal actualAmount;

  @ApiModelProperty(value = "状态:1申请中 2审核通过 3审核拒绝 4退款中 5退款成功 6退款失败", example = "2")
  private Integer refundStatus;

  @ApiModelProperty(value = "审核人ID", example = "admin001")
  private String approverId;

  @ApiModelProperty(value = "审核意见", example = "审核通过，同意退款")
  private String approverNotes;

  @ApiModelProperty(value = "退货物流公司", example = "顺丰速运")
  private String returnLogistics;

  @ApiModelProperty(value = "退货物流单号", example = "SF1234567890")
  private String returnTrackingNo;

  @ApiModelProperty(value = "审核时间", example = "2024-01-01T10:30:00")
  private LocalDateTime reviewTime;

  @ApiModelProperty(value = "退款时间", example = "2024-01-01T11:00:00")
  private LocalDateTime refundTime;
}
