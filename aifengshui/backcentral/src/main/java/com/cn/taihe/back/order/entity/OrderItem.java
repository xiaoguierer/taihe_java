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
 * 订单商品表实体类
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "order_item")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "OrderItem", description = "订单商品表")
public class OrderItem {

  @Id
  @Column(name = "id", length = 50, nullable = false)
  @ApiModelProperty(value = "订单详情ID", example = "1234567890abcdef")
  private String id;

  @Column(name = "order_id", length = 50, nullable = false)
  @ApiModelProperty(value = "订单ID", example = "order123456", required = true)
  private String orderId;

  @Column(name = "sku_id", length = 50, nullable = false)
  @ApiModelProperty(value = "商品SKU ID", example = "sku123456", required = true)
  private String skuId;

  @Column(name = "product_name", length = 200, nullable = false)
  @ApiModelProperty(value = "商品名称快照", example = "iPhone 15 Pro Max", required = true)
  private String productName;

  @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
  @ApiModelProperty(value = "单价快照", example = "999.99", required = true)
  private BigDecimal unitPrice;

  @Column(name = "quantity", nullable = false)
  @ApiModelProperty(value = "购买数量", example = "2", required = true)
  private Integer quantity;

  @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
  @ApiModelProperty(value = "商品总价", example = "1999.98", required = true)
  private BigDecimal totalPrice;

  @Column(name = "refund_quantity", nullable = false)
  @ApiModelProperty(value = "已退款数量", example = "0")
  private Integer refundQuantity = 0;

  @Column(name = "refund_amount", nullable = false, precision = 10, scale = 2)
  @ApiModelProperty(value = "已退款金额", example = "0.00")
  private BigDecimal refundAmount = BigDecimal.ZERO;

  @Column(name = "refund_status", nullable = false)
  @ApiModelProperty(value = "退款状态:0无退款 1部分退款 2全部退款", example = "0")
  private Integer refundStatus = 0;

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @ApiModelProperty(value = "创建时间", example = "2024-01-01T10:00:00")
  private LocalDateTime createdTime;
}
