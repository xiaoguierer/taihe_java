package com.cn.taihe.back.shoppingcart.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车商品表 实体类
 *
 * @author system
 * @TableName shopingcart
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "shopingcart")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "Shopingcart", description = "购物车商品表")
public class Shopingcart implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 购物车项ID
   */
  @Id
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "购物车项ID", example = "550e8400-e29b-41d4-a716-446655440000")
  private String id;

  /**
   * 用户ID
   */
  @TableField("user_id")
  @ApiModelProperty(value = "用户ID", required = true, example = "user123")
  private String userId;

  /**
   * 商品SKU ID
   */
  @TableField("sku_id")
  @ApiModelProperty(value = "商品SKU ID", required = true, example = "sku123")
  private String skuId;

  /**
   * 购买数量
   */
  @TableField("quantity")
  @ApiModelProperty(value = "购买数量", example = "1")
  private Integer quantity;

  /**
   * 是否选中: 0-否, 1-是
   */
  @TableField("selected")
  @ApiModelProperty(value = "是否选中: 0-否, 1-是", example = "1")
  private Integer selected;

  /**
   * 加入时单价
   */
  @TableField("unit_price")
  @ApiModelProperty(value = "加入时单价", required = true, example = "99.99")
  private BigDecimal unitPrice;

  /**
   * 货币
   */
  @TableField("currency")
  @ApiModelProperty(value = "货币", example = "USD")
  private String currency;

  /**
   * 加入时间
   */
  @CreatedDate
  @TableField(value = "added_time", fill = FieldFill.INSERT)
  @ApiModelProperty(value = "加入时间")
  private LocalDateTime addedTime;

  /**
   * 更新时间
   */
  @LastModifiedDate
  @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updatedTime;

}
