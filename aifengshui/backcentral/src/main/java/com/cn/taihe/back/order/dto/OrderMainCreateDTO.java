package com.cn.taihe.back.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单新增参数DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OrderMainCreateDTO", description = "订单新增参数")
public class OrderMainCreateDTO {

  @NotBlank(message = "订单号不能为空")
  @ApiModelProperty(value = "订单号", example = "ORDER202411180001", required = true)
  private String orderSn;

  @NotBlank(message = "用户ID不能为空")
  @ApiModelProperty(value = "用户ID", example = "user123456", required = true)
  private String userId;

  @ApiModelProperty(value = "状态:1待付款 2待发货 3待收货 4已完成 5已关闭", example = "1")
  private Integer status = 1;

  @NotNull(message = "订单总金额不能为空")
  @ApiModelProperty(value = "订单总金额", example = "199.99", required = true)
  private BigDecimal totalAmount;

  @ApiModelProperty(value = "货币", example = "USD")
  private String currency = "USD";

  @NotBlank(message = "收货地址ID不能为空")
  @ApiModelProperty(value = "收货地址ID", example = "address123", required = true)
  private String addressId;

  @NotBlank(message = "收货人姓名不能为空")
  @ApiModelProperty(value = "收货人", example = "张三", required = true)
  private String receiverName;

  @NotBlank(message = "收货人电话不能为空")
  @ApiModelProperty(value = "电话", example = "13800138000", required = true)
  private String receiverPhone;

  @NotBlank(message = "收货地址不能为空")
  @ApiModelProperty(value = "完整收货地址", example = "北京市朝阳区xxx街道xxx号", required = true)
  private String receiverAddress;
}
