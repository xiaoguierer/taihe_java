package com.cn.taihe.back.user.service.impl;

import com.cn.taihe.back.category.service.impl.CategoryServiceImpl;
import com.cn.taihe.back.imagefile.service.FileStorageService;
import com.cn.taihe.back.user.dto.request.UserQueryCondition;
import com.cn.taihe.back.user.entity.User;
import com.cn.taihe.back.user.entity.UserPasswordReset;
import com.cn.taihe.back.user.mapper.UserMapper;
import com.cn.taihe.back.user.mapper.UserPasswordResetMapper;
import com.cn.taihe.back.user.service.UserService;
import com.cn.taihe.common.AppCommonConstants;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
  Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserPasswordResetMapper passwordResetMapper;
  @Autowired
  private FileStorageService fileStorageService;

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

  /**
   * 注册用户并上传头像（文件上传方式）
   */
  @Transactional
  public User registerWithAvatar(String email, String passwordHash, String salt,
                                 String nickname, MultipartFile avatarFile, Integer status) {
    log.info("注册用户并上传头像: email={}", email);

    // 检查邮箱是否已存在
    if (userMapper.existsByEmail(email)) {
      throw new RuntimeException("邮箱已被注册");
    }
    User user = new User();
    user.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
    user.setEmail(email);
    user.setPasswordHash(passwordHash);
    user.setSalt(salt);
    user.setNickname(nickname != null ? nickname : "新用户");
    user.setStatus(status);
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());

    // 处理文件上传的头像
    if (avatarFile != null && !avatarFile.isEmpty()) {
      try {
        String avatarPath = fileStorageService.upload(avatarFile, AppCommonConstants.IMAGE_USER_File_PATH);
        setAvatarInfoFromFile(user, avatarPath, avatarFile);
      } catch (Exception e) {
        log.warn("头像上传失败，继续注册用户: email={}", email, e);
        // 头像上传失败不影响用户注册
        clearAvatarInfo(user);
      }
    } else {
      clearAvatarInfo(user);
    }

    userMapper.insert(user);
    log.info("用户注册成功（带头像）: id={}, email={}", user.getId(), user.getEmail());
    return user;
  }
  /**
   * 处理头像信息（从URL）
   */
  private void processAvatarInfo(User user, String avatarUrl) {
    if (avatarUrl != null && !avatarUrl.trim().isEmpty()) {
      user.setAvatar(avatarUrl); // 设置原始URL（兼容旧版）

      // 如果是本地文件路径，提取相对路径并设置新字段
      if (avatarUrl.startsWith("/api/files/")) {
        String relativePath = avatarUrl.substring("/api/files".length());
        user.setAvatarPath(relativePath);
        user.setAvatarOriginalName(extractFileNameFromUrl(avatarUrl));
        user.setAvatarContentType(inferContentTypeFromPath(relativePath));

        // 如果文件存在，获取文件大小
        if (fileStorageService.exists(relativePath)) {
          try {
            user.setAvatarSize(fileStorageService.getFileSize(relativePath));
          } catch (Exception e) {
            log.warn("获取文件大小失败: {}", relativePath, e);
          }
        }
      } else {
        // 外部URL，清空文件存储相关字段
        clearAvatarInfo(user);
      }
    } else {
      // 没有头像，清空所有相关字段
      clearAvatarInfo(user);
    }
  }

  /**
   * 从文件设置头像信息
   */
  private void setAvatarInfoFromFile(User user, String avatarPath, MultipartFile avatarFile) {
    user.setAvatar("/api/files" + avatarPath);        // 完整URL（兼容旧版）
    user.setAvatarPath(avatarPath);                   // 相对路径
    user.setAvatarOriginalName(avatarFile.getOriginalFilename()); // 原始文件名
    user.setAvatarSize(avatarFile.getSize());         // 文件大小
    user.setAvatarContentType(avatarFile.getContentType()); // 文件类型
  }

  /**
   * 清空头像信息
   */
  private void clearAvatarInfo(User user) {
    user.setAvatar(null);
    user.setAvatarPath(null);
    user.setAvatarOriginalName(null);
    user.setAvatarSize(null);
    user.setAvatarContentType(null);
  }

  /**
   * 删除旧头像文件
   */
  private void deleteOldAvatarFile(String oldAvatarPath) {
    if (oldAvatarPath != null && !oldAvatarPath.trim().isEmpty()) {
      try {
        fileStorageService.delete(oldAvatarPath);
        log.info("删除旧头像文件: {}", oldAvatarPath);
      } catch (Exception e) {
        log.warn("删除旧头像文件失败: {}", oldAvatarPath, e);
        // 不抛出异常，因为主要操作已成功
      }
    }
  }

  /**
   * 从URL中提取文件名
   */
  private String extractFileNameFromUrl(String url) {
    if (url != null) {
      int lastSlashIndex = url.lastIndexOf('/');
      int queryIndex = url.indexOf('?');

      if (lastSlashIndex >= 0 && lastSlashIndex < url.length() - 1) {
        String fileName = url.substring(lastSlashIndex + 1);
        if (queryIndex > 0) {
          fileName = fileName.substring(0, fileName.indexOf('?'));
        }
        return fileName;
      }
    }
    return "avatar";
  }

  /**
   * 根据文件路径推断内容类型
   */
  private String inferContentTypeFromPath(String filePath) {
    if (filePath == null) return null;

    String lowerCasePath = filePath.toLowerCase();
    if (lowerCasePath.endsWith(".jpg") || lowerCasePath.endsWith(".jpeg")) {
      return "image/jpeg";
    } else if (lowerCasePath.endsWith(".png")) {
      return "image/png";
    } else if (lowerCasePath.endsWith(".gif")) {
      return "image/gif";
    } else if (lowerCasePath.endsWith(".webp")) {
      return "image/webp";
    } else if (lowerCasePath.endsWith(".bmp")) {
      return "image/bmp";
    } else if (lowerCasePath.endsWith(".pdf")) {
      return "application/pdf";
    } else if (lowerCasePath.endsWith(".doc")) {
      return "application/msword";
    } else if (lowerCasePath.endsWith(".docx")) {
      return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    } else if (lowerCasePath.endsWith(".xls")) {
      return "application/vnd.ms-excel";
    } else if (lowerCasePath.endsWith(".xlsx")) {
      return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    } else if (lowerCasePath.endsWith(".mp4")) {
      return "video/mp4";
    } else if (lowerCasePath.endsWith(".mp3")) {
      return "audio/mpeg";
    } else if (lowerCasePath.endsWith(".zip")) {
      return "application/zip";
    } else if (lowerCasePath.endsWith(".rar")) {
      return "application/x-rar-compressed";
    } else {
      return "application/octet-stream";
    }
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
    // 处理头像信息
    processAvatarInfo(user, avatar);

    int result = userMapper.update(user);
    log.info("用户资料更新{}: userId={}", result > 0 ? "成功" : "失败", userId);
    return result;
  }

  /**
   * 更新用户头像（文件上传方式）
   */
  @Transactional
  public User updateUserAvatar(String userId, MultipartFile avatarFile) {
    log.info("更新用户头像: userId={}, filename={}", userId, avatarFile.getOriginalFilename());
    // 获取现有用户
    User existingUser = userMapper.selectById(userId);
    if (existingUser == null) {
      throw new RuntimeException("用户不存在");
    }
    // 保存旧头像路径（用于后续删除）
    String oldAvatarPath = existingUser.getAvatarPath();
    try {
      // 上传新头像文件
      String newAvatarPath = fileStorageService.upload(avatarFile, "avatar");
      // 更新用户头像信息
      User user = new User();
      user.setId(userId);
      setAvatarInfoFromFile(user, newAvatarPath, avatarFile);
      user.setUpdatedAt(LocalDateTime.now());
      userMapper.update(user);
      // 删除旧头像文件（如果存在）
      deleteOldAvatarFile(oldAvatarPath);
      log.info("用户头像更新成功: userId={}, newAvatarPath={}", userId, newAvatarPath);
      return userMapper.selectById(userId);
    } catch (Exception e) {
      log.error("更新用户头像失败: userId={}", userId, e);
      throw new RuntimeException("头像更新失败: " + e.getMessage(), e);
    }
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

  /**
   * 清除无效的头像信息
   */
  @Transactional
  public void clearInvalidAvatar(String userId) {
    log.info("清除无效头像信息: userId={}", userId);
    userMapper.clearAvatarInfo(userId);
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
