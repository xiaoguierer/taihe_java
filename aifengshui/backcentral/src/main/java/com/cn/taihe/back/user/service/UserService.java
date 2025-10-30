package com.cn.taihe.back.user.service;

import com.cn.taihe.back.user.dto.request.UserQueryCondition;
import com.cn.taihe.back.user.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.Optional;

/**
 * 用户服务接口
 * 基于UserRepository提供的数据库操作方法，封装业务逻辑
 */
public interface UserService {

    /**
     * 用户注册
     * @param email 邮箱
     * @param password 密码
     * @param nickname 昵称（可选）
     * @return 注册成功的用户信息
     */
    User register(String email, String password,String salt, String nickname,String avatar,Integer status);

    /**
     * 用户登录
     * @param email 邮箱
     * @param password 密码
     * @return 登录成功的用户信息，如果登录失败返回空
     */
    User login(String email, String password);

    /**
     * 根据用户ID查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    Optional<User> findById(String id);

    /**
     * 根据邮箱查询用户信息
     * 对应UserRepository中的findByEmail方法
     * @param email 邮箱
     * @return 用户信息
     */
    public User findByEmail(String email);

    /**
     * 检查邮箱是否已存在
     * 对应UserRepository中的existsByEmail方法
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 更新用户基本信息
     *
     * @param userId   用户ID
     * @param nickname 昵称
     * @param avatar   头像URL
     * @return 更新后的用户信息
     */
    int updateProfile(String userId, String email, String nickname, String avatar, Integer status);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    int changePassword(String userId, String oldPassword, String newPassword,String salt);

    /**
     * 重置密码请求
     * @param email 邮箱
     * @return 重置令牌
     */
    String requestPasswordReset(String email);

    /**
     * 重置密码确认
     * @param token 重置令牌
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    boolean resetPassword(String token, String newPassword);

    /**
     * 启用/禁用用户账户
     * @param userId 用户ID
     * @param status 状态（0-禁用，1-启用）
     * @return 是否操作成功
     */
    boolean updateStatus(String userId, Integer status);

    /**
     * 删除用户（软删除）
     * @param userId 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(String userId);
    // ...原有方法...

    /**
     * 分页查询所有用户
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageInfo<User> listAllUsers(int pageNum, int pageSize);

    /**
     * 条件查询用户
     * @param condition 查询条件
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageInfo<User> searchUsers(UserQueryCondition condition, int pageNum, int pageSize);


}
