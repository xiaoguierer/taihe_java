package com.cn.taihe.back.suppliers.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@ApiModel(description = "联系人响应DTO")
public class ContactDTO {

    @ApiModelProperty(value = "联系人ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @NotBlank(message = "供应商ID不能为空")
    @ApiModelProperty(value = "供应商ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    private String supplierId;

    @NotBlank(message = "联系人姓名不能为空")
    @ApiModelProperty(value = "联系人姓名", required = true, example = "张三")
    private String name;

    @ApiModelProperty(value = "职位", example = "销售经理")
    private String position;

    @ApiModelProperty(value = "部门", example = "销售部")
    private String department;

    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^[0-9-]{7,20}$", message = "电话格式不正确")
    @ApiModelProperty(value = "电话", required = true, example = "021-12345678")
    private String phone;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机格式不正确")
    @ApiModelProperty(value = "手机", example = "13800138000")
    private String mobile;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱", example = "zhangsan@example.com")
    private String email;

    @ApiModelProperty(value = "微信", example = "zhangsan123")
    private String wechat;

    @ApiModelProperty(value = "QQ", example = "12345678")
    private String qq;

    @NotNull(message = "是否主联系人不能为空")
    @ApiModelProperty(value = "是否主联系人", required = true, example = "true")
    private Boolean isPrimary;

    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Date updatedAt;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 业务方法：获取脱敏的手机号（用于显示）
    @ApiModelProperty(value = "脱敏手机号", hidden = true)
    public String getMaskedMobile() {
        if (mobile == null || mobile.length() < 11) {
            return mobile;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }

    // 业务方法：获取脱敏的邮箱（用于显示）
    @ApiModelProperty(value = "脱敏邮箱", hidden = true)
    public String getMaskedEmail() {
        if (email == null || !email.contains("@")) {
            return email;
        }
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) {
            return email.charAt(0) + "****" + email.substring(atIndex);
        }
        return email.substring(0, 2) + "****" + email.substring(atIndex);
    }

    // 业务方法：获取完整的联系信息
    @ApiModelProperty(value = "完整联系信息", hidden = true)
    public String getFullContactInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (position != null) {
            sb.append("(").append(position).append(")");
        }
        sb.append(" - ").append(phone);
        if (mobile != null) {
            sb.append("/").append(getMaskedMobile());
        }
        return sb.toString();
    }
}