package com.cn.taihe.back.user.dto.response;
import lombok.Data;

/**
 * 认证响应DTO（登录/注册返回）
 */
@Data
public class AuthResponse {
    private String token;
    private UserResponse user;

    public AuthResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }
}
