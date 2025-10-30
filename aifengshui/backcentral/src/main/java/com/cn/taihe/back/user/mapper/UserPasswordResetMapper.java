package com.cn.taihe.back.user.mapper;

import com.cn.taihe.back.user.entity.UserPasswordReset;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserPasswordResetMapper {

    @Insert("INSERT INTO user_password_reset (user_id, token, expires_at, is_used) " +
            "VALUES (#{userId}, #{token}, #{expiresAt}, #{isUsed})")
    int insert(UserPasswordReset reset);

    @Select("SELECT * FROM user_password_reset WHERE token = #{token} AND is_used = 0 AND expires_at > NOW()")
    UserPasswordReset selectValidByToken(String token);

    @Update("UPDATE user_password_reset SET is_used = 1 WHERE id = #{id}")
    int markAsUsed(String id);

    @Delete("DELETE FROM user_password_reset WHERE expires_at < NOW()")
    int deleteExpiredTokens();
}