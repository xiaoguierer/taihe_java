package com.cn.taihe.back.user.dto.request;
import lombok.Data;

@Data
public class UserQueryCondition {
    private String email;       // 邮箱模糊查询
    private String nickname;    // 昵称模糊查询
    private Integer status;     // 状态精确查询
}
