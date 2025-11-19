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
 * 订单退单表实体类
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "order_refund")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "OrderRefund", description = "订单退单表")
public class OrderRefund {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", length = 50, nullable = false)
  @ApiModelProperty(value = "退单ID", example = "1234567890abcdef")
  private String id;

  @Column(name = "refund_sn", length = 32, nullable = false)
  @ApiModelProperty(value = "退单号", example = "REFUND202411180001", required = true)
  private String refundSn;

  @Column(name = "order_id", length = 50, nullable = false)
  @ApiModelProperty(value = "订单ID", example = "order123456", required = true)
  private String orderId;

  @Column(name = "user_id", length = 50, nullable = false)
  @ApiModelProperty(value = "用户ID", example = "user123456", required = true)
  private String userId;

  @Column(name = "refund_type", nullable = false)
  @ApiModelProperty(value = "退单类型:1仅退款 2退货退款", example = "1", required = true)
  private Integer refundType;

  @Column(name = "refund_reason", length = 200, nullable = false)
  @ApiModelProperty(value = "退单原因", example = "商品质量问题", required = true)
  private String refundReason;

  @Column(name = "apply_amount", nullable = false, precision = 10, scale = 2)
  @ApiModelProperty(value = "申请退款金额", example = "199.99", required = true)
  private BigDecimal applyAmount;

  @Column(name = "actual_amount", precision = 10, scale = 2)
  @ApiModelProperty(value = "实际退款金额", example = "199.99")
  private BigDecimal actualAmount;

  @Column(name = "refund_items", columnDefinition = "json", nullable = false)
  @ApiModelProperty(value = "退款商品明细", example = "[{\"itemId\":\"item123\",\"quantity\":1,\"amount\":199.99}]", required = true)
  private String refundItems;

  @Column(name = "refund_status", nullable = false)
  @ApiModelProperty(value = "状态:1申请中 2审核通过 3审核拒绝 4退款中 5退款成功 6退款失败", example = "1")
  private Integer refundStatus = 1;

  @Column(name = "approver_id", length = 50)
  @ApiModelProperty(value = "审核人ID", example = "admin001")
  private String approverId;

  @Column(name = "approver_notes", length = 500)
  @ApiModelProperty(value = "审核意见", example = "审核通过，同意退款")
  private String approverNotes;

  @Column(name = "return_logistics", length = 100)
  @ApiModelProperty(value = "退货物流公司", example = "顺丰速运")
  private String returnLogistics;

  @Column(name = "return_tracking_no", length = 100)
  @ApiModelProperty(value = "退货物流单号", example = "SF1234567890")
  private String returnTrackingNo;

  @CreatedDate
  @Column(name = "apply_time", nullable = false, updatable = false)
  @ApiModelProperty(value = "申请时间", example = "2024-01-01T10:00:00")
  private LocalDateTime applyTime;

  @Column(name = "review_time")
  @ApiModelProperty(value = "审核时间", example = "2024-01-01T10:30:00")
  private LocalDateTime reviewTime;

  @Column(name = "refund_time")
  @ApiModelProperty(value = "退款时间", example = "2024-01-01T11:00:00")
  private LocalDateTime refundTime;
}
