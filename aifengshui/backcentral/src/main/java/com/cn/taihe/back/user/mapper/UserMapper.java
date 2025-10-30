package com.cn.taihe.back.user.mapper;
import com.cn.taihe.back.user.dto.request.UserQueryCondition;
import com.cn.taihe.back.user.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    // 插入用户（返回主键）
    @Insert("INSERT INTO user (id,email, password_hash, salt, nickname, avatar, status, created_at, updated_at) " +
            "VALUES (#{id},#{email}, #{passwordHash},#{salt}, #{nickname}, #{avatar}, #{status}, #{createdAt}, #{updatedAt})")
    int insert(User user);

    // 根据ID查询（对应JPA的findById）
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(String id);

    // 根据邮箱查询（对应图片中的findByEmail方法）
    @Select("SELECT * FROM user WHERE email = #{email}")
    User selectByEmail(String email);

    // 检查邮箱是否存在（对应图片中的existsByEmail方法）
    @Select("SELECT COUNT(*) FROM user WHERE email = #{email}")
    boolean existsByEmail(String email);

    // 更新用户信息
    @Update("UPDATE user SET email = #{email},nickname = #{nickname}, avatar = #{avatar}, status = #{status} WHERE id = #{id}")
    int update(User user);

    // 更新密码
    @Update("UPDATE user SET password_hash = #{passwordHash}, updated_at = NOW() WHERE id = #{id}")
    int updatePassword(@Param("id") String id, @Param("passwordHash") String passwordHash);

    // 更新状态
    @Update("UPDATE user SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") String id, @Param("status") Integer status);

    // 更新最后登录时间
    @Update("UPDATE user SET updated_at = NOW() WHERE id = #{id}")
    int updateLastLoginTime(String id);


    @Select("SELECT * FROM user ORDER BY created_at DESC")
    List<User> selectAll();

    @Select("<script>" +
            "SELECT * FROM user WHERE 1=1 " +
            "<if test='email != null'>AND email LIKE CONCAT('%',#{email},'%')</if>" +
            "<if test='nickname != null'>AND nickname LIKE CONCAT('%',#{nickname},'%')</if>" +
            "<if test='status != null'>AND status = #{status}</if>" +
            "ORDER BY created_at DESC" +
            "</script>")
    List<User> selectByCondition(UserQueryCondition condition);

    // 根据ID删除用户
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(String id);
}