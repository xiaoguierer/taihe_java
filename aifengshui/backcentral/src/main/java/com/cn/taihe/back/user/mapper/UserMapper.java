package com.cn.taihe.back.user.mapper;
import com.cn.taihe.back.user.dto.request.UserQueryCondition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.user.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper extends BaseMapper {

  // ==================== 修改的方法 ====================

  // 插入用户（返回主键）- 新增文件存储字段
  // ===== 修改：添加了4个新的文件存储字段 =====
  @Insert("INSERT INTO user (id, email, password_hash, salt, nickname, avatar, status, created_at, updated_at, " +
    "avatar_path, avatar_original_name, avatar_size, avatar_content_type,birthdaytime) " + // ← 新增字段
    "VALUES (#{id}, #{email}, #{passwordHash}, #{salt}, #{nickname}, #{avatar}, #{status}, #{createdAt}, #{updatedAt}, " +
    "#{avatarPath}, #{avatarOriginalName}, #{avatarSize}, #{avatarContentType},#{birthdaytime})") // ← 新增参数
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(User user);

  // 根据ID查询 - 新增文件存储字段
  // ===== 修改：添加了4个新的文件存储字段映射 =====
  @Select("SELECT " +
    "id, email, password_hash as passwordHash, salt, nickname, avatar, status, birthdaytime," +
    "created_at as createdAt, updated_at as updatedAt, " +
    "avatar_path as avatarPath, avatar_original_name as avatarOriginalName, " + // ← 新增字段映射
    "avatar_size as avatarSize, avatar_content_type as avatarContentType " +    // ← 新增字段映射
    "FROM user WHERE id = #{id}")
  User selectById(String id);

  // 根据邮箱查询 - 新增文件存储字段
  // ===== 修改：添加了4个新的文件存储字段映射 =====
  @Select("SELECT " +
    "id, email, password_hash as passwordHash, salt, nickname, avatar, status, birthdaytime," +
    "created_at as createdAt, updated_at as updatedAt, " +
    "avatar_path as avatarPath, avatar_original_name as avatarOriginalName, " + // ← 新增字段映射
    "avatar_size as avatarSize, avatar_content_type as avatarContentType " +    // ← 新增字段映射
    "FROM user WHERE email = #{email}")
  User selectByEmail(String email);

  // 检查邮箱是否存在 - 保持不变
  // ===== 未修改 =====
  @Select("SELECT COUNT(*) FROM user WHERE email = #{email}")
  boolean existsByEmail(String email);

  // 更新用户信息 - 新增文件存储字段
  // ===== 修改：添加了4个新的文件存储字段的动态更新 =====
  @Update("<script>" +
    "UPDATE user SET " +
    "email = #{email}, " +
    "nickname = #{nickname}, " +
    "avatar = #{avatar}, " +
    "updated_at = #{updatedAt}, " +
    "birthdaytime = #{birthdaytime}," +
    "<if test='avatarPath != null'>avatar_path = #{avatarPath},</if>" +              // ← 新增动态更新
    "<if test='avatarOriginalName != null'>avatar_original_name = #{avatarOriginalName},</if>" + // ← 新增动态更新
    "<if test='avatar != null'>avatar = #{avatar},</if>" +            // ← 新增动态更新
    "<if test='avatarSize != null'>avatar_size = #{avatarSize},</if>" +            // ← 新增动态更新
    "<if test='avatarContentType != null'>avatar_content_type = #{avatarContentType},</if>" +     // ← 新增动态更新
    "updated_at = NOW() " +
    "WHERE id = #{id}" +
    "</script>")
  int update(User user);

  // ==================== 新增的方法 ====================

  // 专门更新头像信息的方法
  // ===== 新增方法 =====
  @Update("UPDATE user SET " +
    "avatar = #{avatar}, " +
    "avatar_path = #{avatarPath}, " +           // ← 新增字段
    "avatar_original_name = #{avatarOriginalName}, " + // ← 新增字段
    "avatar_size = #{avatarSize}, " +            // ← 新增字段
    "avatar_content_type = #{avatarContentType}, " +   // ← 新增字段
    "updated_at = NOW() " +
    "WHERE id = #{id}")
  int updateAvatarInfo(@Param("id") String id,
                       @Param("avatar") String avatar,
                       @Param("avatarPath") String avatarPath,
                       @Param("avatarOriginalName") String avatarOriginalName,
                       @Param("avatarSize") Long avatarSize,
                       @Param("avatarContentType") String avatarContentType);

  // 更新密码 - 保持不变
  // ===== 未修改 =====
  @Update("UPDATE user SET password_hash = #{passwordHash}, updated_at = NOW() WHERE id = #{id}")
  int updatePassword(@Param("id") String id, @Param("passwordHash") String passwordHash);

  // 更新状态 - 保持不变
  // ===== 未修改 =====
  @Update("UPDATE user SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
  int updateStatus(@Param("id") String id, @Param("status") Integer status);

  // 更新最后登录时间 - 保持不变
  // ===== 未修改 =====
  @Update("UPDATE user SET updated_at = NOW() WHERE id = #{id}")
  int updateLastLoginTime(String id);

  // 查询所有用户 - 保持不变（不包含新增字段）
  // ===== 未修改 =====
  @Select("SELECT id, email, nickname, avatar, status,birthdaytime, created_at as createdAt, updated_at as updatedAt " +
    "FROM user ORDER BY created_at DESC")
  List<User> selectAll();

  // 条件查询用户 - 保持不变（不包含新增字段）
  // ===== 未修改 =====
  @Select("<script>" +
    "SELECT id, email, nickname, avatar, status, birthdaytime,created_at as createdAt, updated_at as updatedAt,avatar_path as avatarPath, avatar_original_name as avatarOriginalName, avatar_size as avatarSize, avatar_content_type as avatarContentType " +
    "FROM user WHERE 1=1 " +
    "<if test='email != null'>AND email LIKE CONCAT('%',#{email},'%')</if>" +
    "<if test='nickname != null'>AND nickname LIKE CONCAT('%',#{nickname},'%')</if>" +
    "<if test='status != null'>AND status = #{status}</if>" +
    "ORDER BY created_at DESC" +
    "</script>")
  List<User> selectByCondition(UserQueryCondition condition);

  // 根据ID删除用户 - 保持不变
  // ===== 未修改 =====
  @Delete("DELETE FROM user WHERE id = #{id}")
  int deleteById(String id);

  // ==================== 新增的辅助方法 ====================

  // 查询所有有效的头像路径（用于文件清理）
  // ===== 新增方法 =====
  @Select("SELECT avatar_path FROM user WHERE avatar_path IS NOT NULL AND avatar_path != ''")
  List<String> selectAllAvatarPaths();

  // 查询有头像的用户（用于头像管理）
  // ===== 新增方法 =====
  @Select("SELECT " +
    "id, email, nickname, avatar, avatar_path as avatarPath, " +
    "avatar_original_name as avatarOriginalName, avatar_size as avatarSize " +
    "FROM user WHERE avatar_path IS NOT NULL AND avatar_path != ''")
  List<User> selectUsersWithAvatar();

  // 清除无效的头像路径
  // ===== 新增方法 =====
  @Update("UPDATE user SET avatar = NULL, avatar_path = NULL, " +
    "avatar_original_name = NULL, avatar_size = NULL, avatar_content_type = NULL " +
    "WHERE id = #{id}")
  int clearAvatarInfo(String id);

}
