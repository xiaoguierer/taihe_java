package com.cn.taihe.back.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户收货地址新增DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserAddressCreateDTO", description = "用户收货地址新增DTO")
public class UserAddressCreateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户ID", required = true, example = "user123")
  @NotBlank(message = "用户ID不能为空")
  @Length(max = 50, message = "用户ID长度不能超过50个字符")
  private String userId;

  @ApiModelProperty(value = "地址名称(如：家、公司)", example = "家")
  @Length(max = 50, message = "地址名称长度不能超过50个字符")
  private String addressName;

  @ApiModelProperty(value = "是否默认地址", required = true, example = "0")
  @NotNull(message = "是否默认地址不能为空")
  private Integer isDefault;

  @ApiModelProperty(value = "收货人姓名", required = true, example = "张三")
  @NotBlank(message = "收货人姓名不能为空")
  @Length(max = 100, message = "收货人姓名长度不能超过100个字符")
  private String receiverName;

  @ApiModelProperty(value = "国际区号", required = true, example = "+86")
  @NotBlank(message = "国际区号不能为空")
  @Length(max = 10, message = "国际区号长度不能超过10个字符")
  private String phoneCountryCode;

  @ApiModelProperty(value = "手机号码", required = true, example = "13800138000")
  @NotBlank(message = "手机号码不能为空")
  @Pattern(regexp = "^[0-9+\\-\\s()]{7,20}$", message = "手机号码格式不正确")
  @Length(max = 50, message = "手机号码长度不能超过50个字符")
  private String phoneNumber;

  @ApiModelProperty(value = "邮箱地址", example = "zhangsan@example.com")
  @Email(message = "邮箱格式不正确")
  @Length(max = 100, message = "邮箱地址长度不能超过100个字符")
  private String email;

  @ApiModelProperty(value = "国家", required = true, example = "中国")
  @NotBlank(message = "国家不能为空")
  @Length(max = 100, message = "国家长度不能超过100个字符")
  private String country;

  @ApiModelProperty(value = "省/州", required = true, example = "广东省")
  @NotBlank(message = "省/州不能为空")
  @Length(max = 100, message = "省/州长度不能超过100个字符")
  private String stateProvince;

  @ApiModelProperty(value = "城市", required = true, example = "深圳市")
  @NotBlank(message = "城市不能为空")
  @Length(max = 100, message = "城市长度不能超过100个字符")
  private String city;

  @ApiModelProperty(value = "区/县", example = "南山区")
  @Length(max = 100, message = "区/县长度不能超过100个字符")
  private String district;

  @ApiModelProperty(value = "详细地址", required = true, example = "科技园南区1栋101室")
  @NotBlank(message = "详细地址不能为空")
  @Length(max = 500, message = "详细地址长度不能超过500个字符")
  private String streetAddress;

  @ApiModelProperty(value = "邮编", example = "518000")
  @Length(max = 20, message = "邮编长度不能超过20个字符")
  private String postalCode;

  @ApiModelProperty(value = "是否有效", required = true, example = "1")
  @NotNull(message = "是否有效不能为空")
  private Integer isActive;
}
