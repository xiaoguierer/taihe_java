package com.cn.taihe.back.order.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单支付表实体类
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "order_payment")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "OrderPayment", description = "订单支付表")
public class OrderPayment {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", length = 50, nullable = false)
  @ApiModelProperty(value = "支付ID", example = "1234567890abcdef")
  private String id;

  @Column(name = "order_id", length = 50, nullable = false)
  @ApiModelProperty(value = "订单ID", example = "order123456", required = true)
  private String orderId;

  @Column(name = "payment_method", length = 50, nullable = false)
  @ApiModelProperty(value = "支付方式", example = "ALIPAY", required = true)
  private String paymentMethod;

  @Column(name = "payment_amount", nullable = false, precision = 10, scale = 2)
  @ApiModelProperty(value = "支付金额", example = "199.99", required = true)
  private BigDecimal paymentAmount;

  @Column(name = "payment_status", length = 20, nullable = false)
  @ApiModelProperty(value = "状态:pending,paid,failed,refunded", example = "pending")
  private String paymentStatus = "pending";

  @Column(name = "gateway_trade_no", length = 100)
  @ApiModelProperty(value = "支付平台交易号", example = "202411180000100001")
  private String gatewayTradeNo;

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @ApiModelProperty(value = "创建时间", example = "2024-01-01T10:00:00")
  private LocalDateTime createdTime;

  @Column(name = "paid_time")
  @ApiModelProperty(value = "支付成功时间", example = "2024-01-01T10:30:00")
  private LocalDateTime paidTime;

  @LastModifiedDate
  @Column(name = "updated_time", nullable = false)
  @ApiModelProperty(value = "更新时间", example = "2024-01-01T10:30:00")
  private LocalDateTime updatedTime;
}
