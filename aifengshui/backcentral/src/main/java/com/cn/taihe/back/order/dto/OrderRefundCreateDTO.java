package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单退单新增参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderRefundCreateDTO", description = "订单退单新增参数")
public class OrderRefundCreateDTO {

  @NotBlank(message = "退单号不能为空")
  @ApiModelProperty(value = "退单号", example = "REFUND202411180001", required = true)
  private String refundSn;

  @NotBlank(message = "订单ID不能为空")
  @ApiModelProperty(value = "订单ID", example = "order123456", required = true)
  private String orderId;

  @NotBlank(message = "用户ID不能为空")
  @ApiModelProperty(value = "用户ID", example = "user123456", required = true)
  private String userId;

  @NotNull(message = "退单类型不能为空")
  @ApiModelProperty(value = "退单类型:1仅退款 2退货退款", example = "1", required = true)
  private Integer refundType;

  @NotBlank(message = "退单原因不能为空")
  @ApiModelProperty(value = "退单原因", example = "商品质量问题", required = true)
  private String refundReason;

  @NotNull(message = "申请退款金额不能为空")
  @ApiModelProperty(value = "申请退款金额", example = "199.99", required = true)
  private BigDecimal applyAmount;

  @ApiModelProperty(value = "实际退款金额", example = "199.99")
  private BigDecimal actualAmount;

  @NotBlank(message = "退款商品明细不能为空")
  @ApiModelProperty(value = "退款商品明细", example = "[{\"itemId\":\"item123\",\"quantity\":1,\"amount\":199.99}]", required = true)
  private String refundItems;

  @ApiModelProperty(value = "退货物流公司", example = "顺丰速运")
  private String returnLogistics;

  @ApiModelProperty(value = "退货物流单号", example = "SF1234567890")
  private String returnTrackingNo;
}
