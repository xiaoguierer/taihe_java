package com.cn.taihe.back.user.service.impl;

import com.cn.taihe.back.user.dto.request.UserQueryCondition;
import com.cn.taihe.back.user.entity.User;
import com.cn.taihe.back.user.entity.UserPasswordReset;
import com.cn.taihe.back.user.mapper.UserMapper;
import com.cn.taihe.back.user.mapper.UserPasswordResetMapper;
import com.cn.taihe.back.user.service.UserService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPasswordResetMapper passwordResetMapper;



    @Transactional
    public User register(String email, String password,String salt, String nickname,String avatar,Integer status) {
        // 检查邮箱是否已存在（使用图片中的existsByEmail方法）
        if (userMapper.existsByEmail(email)) {
            throw new RuntimeException("邮箱已被注册");
        }

        User user = new User();
        user.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setSalt(salt);
        user.setNickname(nickname != null ? nickname : "新用户");
        user.setStatus(status);
        user.setAvatar(avatar);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.insert(user);
        return user;
    }

    @Override
    public User login(String email, String password) {
        // 使用图片中的findByEmail方法
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            return null;
        }
        return user;
    }


    public Optional<User> findById(String id) {
        User user = userMapper.selectById(id);
        return Optional.ofNullable(user);
    }

    @Override
    public User findByEmail(String email) {
        User user = userMapper.selectByEmail(email);
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    public int updateProfile(String userId, String email, String nickname, String avatar, Integer status) {
        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setStatus(status);
        return userMapper.update(user);
    }

    @Override
    public int changePassword(String userId, String oldPassword, String newPassword,String salt) {
        User user = new User();
        user.setId(userId);
        user.setSalt(salt);
        user.setPasswordHash(newPassword);
        return userMapper.update(user);
    }

    @Override
    @Transactional
    public String requestPasswordReset(String email) {
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            throw new RuntimeException("邮箱未注册");
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        UserPasswordReset reset = new UserPasswordReset();
        reset.setUserId(user.getId());
        reset.setToken(token);
        reset.setExpiresAt(LocalDateTime.now().plusHours(1));
        reset.setIsUsed(false);

        passwordResetMapper.insert(reset);
        return token;
    }

    @Override
    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        UserPasswordReset reset = passwordResetMapper.selectValidByToken(token);
        if (reset == null) {
            throw new RuntimeException("无效的令牌或已过期");
        }

        User user = userMapper.selectById(reset.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 更新密码
       // userMapper.updatePassword(user.getId(), passwordEncoder.encode(newPassword));
        userMapper.updatePassword(user.getId(), newPassword);

        // 标记token为已使用
        passwordResetMapper.markAsUsed(reset.getId());

        return true;
    }

    @Override
    public boolean updateStatus(String userId, Integer status) {
        return false;
    }
    @Transactional
    public boolean deleteUser(String userId) {
        int res = userMapper.deleteById(userId);
        if (res > 0) { return true;}
        else  return false;
    }
    @Override
    public PageInfo<User> listAllUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectAll();
        return new PageInfo<>(users);
    }

    @Override
    public PageInfo<User> searchUsers(UserQueryCondition condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectByCondition(condition);
        return new PageInfo<>(users);
    }
}
