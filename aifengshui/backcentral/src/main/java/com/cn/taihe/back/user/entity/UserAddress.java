package com.cn.taihe.back.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户收货地址表实体类
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "user_address")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "UserAddress", description = "用户收货地址表")
public class UserAddress {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "system-uuid")
  @Column(name = "id", nullable = false, length = 50)
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "地址ID", example = "550e8400-e29b-41d4-a716-446655440000")
  private String id;

  @Column(name = "user_id", nullable = false, length = 50)
  @ApiModelProperty(value = "用户ID", required = true, example = "user123")
  private String userId;

  @Column(name = "address_name", length = 50)
  @ApiModelProperty(value = "地址名称(如：家、公司)", example = "家")
  private String addressName;

  @Column(name = "is_default", nullable = false)
  @ApiModelProperty(value = "是否默认地址", required = true, example = "0")
  private Integer isDefault;

  @Column(name = "receiver_name", nullable = false, length = 100)
  @ApiModelProperty(value = "收货人姓名", required = true, example = "张三")
  private String receiverName;

  @Column(name = "phone_country_code", nullable = false, length = 10)
  @ApiModelProperty(value = "国际区号", required = true, example = "+86")
  private String phoneCountryCode;

  @Column(name = "phone_number", nullable = false, length = 50)
  @ApiModelProperty(value = "手机号码", required = true, example = "13800138000")
  private String phoneNumber;

  @Column(name = "email", length = 100)
  @ApiModelProperty(value = "邮箱地址", example = "zhangsan@example.com")
  private String email;

  @Column(name = "country", nullable = false, length = 100)
  @ApiModelProperty(value = "国家", required = true, example = "中国")
  private String country;

  @Column(name = "state_province", nullable = false, length = 100)
  @ApiModelProperty(value = "省/州", required = true, example = "广东省")
  private String stateProvince;

  @Column(name = "city", nullable = false, length = 100)
  @ApiModelProperty(value = "城市", required = true, example = "深圳市")
  private String city;

  @Column(name = "district", length = 100)
  @ApiModelProperty(value = "区/县", example = "南山区")
  private String district;

  @Column(name = "street_address", nullable = false, length = 500)
  @ApiModelProperty(value = "详细地址", required = true, example = "科技园南区1栋101室")
  private String streetAddress;

  @Column(name = "postal_code", length = 20)
  @ApiModelProperty(value = "邮编", example = "518000")
  private String postalCode;

  @Column(name = "is_active", nullable = false)
  @ApiModelProperty(value = "是否有效", required = true, example = "1")
  private Integer isActive;

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @TableField(value = "created_time", fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createdTime;

  @LastModifiedDate
  @Column(name = "updated_time", nullable = false)
  @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updatedTime;

  @CreatedBy
  @Column(name = "created_by", length = 50)
  @TableField(value = "created_by", fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建人")
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", length = 50)
  @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新人")
  private String updatedBy;

  @TableLogic
  @Column(name = "deleted")
  @TableField(value = "deleted", fill = FieldFill.INSERT)
  @ApiModelProperty(value = "逻辑删除标志：0-未删除，1-已删除")
  private Integer deleted;
}
