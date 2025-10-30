package com.cn.taihe.back.user.dto.response;


import com.cn.taihe.back.user.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息响应DTO
 */
@Data
public class UserResponse {
    private String id;
    private String email;
    private String nickname;
    private String avatar;
    private Integer status;
    private LocalDateTime createdAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
    }
}