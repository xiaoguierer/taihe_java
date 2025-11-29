package com.cn.taihe.back.order.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单主表实体类
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "order_main")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "OrderMain", description = "订单主表")
public class OrderMain {

  @Id
  @GeneratedValue(generator = "uuid2")
  @Column(name = "id", length = 50, nullable = false)
  @ApiModelProperty(value = "订单ID", example = "1234567890abcdef")
  private String id;

  @Column(name = "order_sn", length = 32, nullable = false)
  @ApiModelProperty(value = "订单号", example = "ORDER202411180001", required = true)
  private String orderSn;

  @Column(name = "user_id", length = 50, nullable = false)
  @ApiModelProperty(value = "用户ID", example = "user123456", required = true)
  private String userId;

  @Column(name = "status", nullable = false)
  @ApiModelProperty(value = "状态:1待付款 2待发货 3待收货 4已完成 5已关闭", example = "1")
  private Integer status = 1;

  @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
  @ApiModelProperty(value = "订单总金额", example = "199.99", required = true)
  private BigDecimal totalAmount;

  @Column(name = "currency", length = 10, nullable = false)
  @ApiModelProperty(value = "货币", example = "USD")
  private String currency = "USD";

  @Column(name = "address_id", length = 50, nullable = false)
  @ApiModelProperty(value = "收货地址ID", example = "address123", required = true)
  private String addressId;

  @Column(name = "receiver_name", length = 100, nullable = false)
  @ApiModelProperty(value = "收货人", example = "张三", required = true)
  private String receiverName;

  @Column(name = "receiver_phone", length = 50, nullable = false)
  @ApiModelProperty(value = "电话", example = "13800138000", required = true)
  private String receiverPhone;

  @Column(name = "receiver_address", length = 500, nullable = false)
  @ApiModelProperty(value = "完整收货地址", example = "北京市朝阳区xxx街道xxx号", required = true)
  private String receiverAddress;

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @ApiModelProperty(value = "下单时间", example = "2024-01-01T10:00:00")
  private LocalDateTime createdTime;

  @Column(name = "payment_time")
  @ApiModelProperty(value = "付款时间", example = "2024-01-01T10:30:00")
  private LocalDateTime paymentTime;

  @Column(name = "shipping_time")
  @ApiModelProperty(value = "发货时间", example = "2024-01-02T09:00:00")
  private LocalDateTime shippingTime;

  @Column(name = "completed_time")
  @ApiModelProperty(value = "完成时间", example = "2024-01-05T15:00:00")
  private LocalDateTime completedTime;
}
