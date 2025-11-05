package com.cn.taihe.back.user.controller;

import com.cn.taihe.back.user.dto.request.RegisterRequest;
import com.cn.taihe.back.user.dto.request.UserQueryCondition;
import com.cn.taihe.back.user.entity.User;
import com.cn.taihe.back.user.service.UserService;
import com.cn.taihe.common.ApiResponse;
import com.cn.taihe.common.BusinessException;
import com.cn.taihe.common.ResponseBuilder;
import com.cn.taihe.common.ResponseUtil;
import com.cn.taihe.common.utils.PasswordUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 用户控制器
 * 基于UserRepository提供的数据库操作，实现RESTful API
 */
@CrossOrigin(origins = "*") // 允许所有来源
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;
  Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * 用户注册新增
   * POST /api/users/register
   */
  @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> register(
    @RequestPart("request") RegisterRequest request,
    @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
    logger.info("新增注册用户信息........");
    try {
      //检测邮箱是否存在
      if (userService.existsByEmail(request.getEmail())) {
        return ResponseEntity.badRequest().body(buildErrorResponse("邮箱已被注册"));
      }
      // 2. 生成盐值和加密密码
      String salt = PasswordUtil.generateSalt();
      String passwordHash = PasswordUtil.hashPassword(request.getPassword(), salt);
      User user;
      // 优先使用文件上传方式
      if (avatarFile != null && !avatarFile.isEmpty()) {
        user = userService.registerWithAvatar(
          request.getEmail(),
          passwordHash,
          salt,
          request.getNickname(),
          avatarFile,
          Integer.valueOf(request.getStatus())
        );
      } else {
        // 使用URL方式
        user = userService.register(
          request.getEmail(),
          passwordHash,
          salt,
          request.getNickname(),
          request.getAvatar(),
          Integer.valueOf(request.getStatus())
        );
      }
      return ResponseEntity.ok(buildSuccessResponse("注册成功", user));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }


  /**
   * 获取所有用户列表（分页）
   * GET /api/users?page=1&size=10
   */
  @GetMapping("/findallusers")
  public ResponseEntity<?> listAllUsers(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size) {
    PageInfo<User> pageInfo = userService.listAllUsers(page, size);
    return ResponseBuilder.success(pageInfo);
  }

  /**
   * 条件查询用户
   * GET /api/users/search?page=1&size=10
   */
  @GetMapping("/search")
  public ResponseEntity<?> searchUsers(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String email,
    @RequestParam(required = false) String nickname,
    @RequestParam(required = false) Integer status) {
    try {
      UserQueryCondition condition = new UserQueryCondition();
      condition.setEmail(email);
      condition.setNickname(nickname);
      condition.setStatus(status);
      PageInfo<User> pageInfo = userService.searchUsers(condition, page, size);
      return ResponseBuilder.success(pageInfo);
    } catch (Exception e) {
      ApiResponse<PageInfo<User>> errorResponse = ResponseUtil.error("400", e.getMessage());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }

  /**
   * 获取用户信息
   * GET /api/users/{id}
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable String id) {
    Optional<User> userOpt = userService.findById(id);
    if (userOpt.isPresent()) {
      return ResponseEntity.ok(buildSuccessResponse("查询成功", userOpt.get()));
    }
    return ResponseEntity.status(404).body(buildErrorResponse("用户不存在"));
  }


  /**
   * 更新用户资料
   * PUT /api/users/{id}/profile
   */
  @PutMapping(value = "/{id}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updateProfile(@PathVariable String id,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false) String nickname,
                                         @RequestParam(required = false) String avatar,
                                         @RequestParam(required = false) Integer status,
                                         @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
    int result = 0;
    try {
      if (avatarFile != null && !avatarFile.isEmpty()) {
        logger.info("头像信息上传不为空");
        result = userService.updateProfile(id, email, nickname, avatar, status, avatarFile);
      } else {
        result = userService.updateProfile(id, email, nickname, avatar, status);
      }
      if (result > 0) {
        // 返回更新后的完整用户信息
        Optional<User> updatedUser = userService.findById(id);
        return ResponseEntity.ok(buildSuccessResponse("资料更新成功", updatedUser));
      } else {
        return ResponseEntity.badRequest().body(buildErrorResponse("资料更新失败"));
      }
    } catch (
      Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }

  }

  /**
   * 删除用户
   * DELETE /api/users/del
   */
  @DeleteMapping("/del")
  public ResponseEntity<?> deleteUser(@RequestParam String id) {
    try {
      boolean success = userService.deleteUser(id);
      if (success) {
        return ResponseEntity.ok(buildSuccessResponse("用户删除成功", null));
      }
      return ResponseEntity.badRequest().body(buildErrorResponse("用户删除失败"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 更新用户头像（文件上传方式）
   * POST /api/users/{id}/avatar
   */
  @PostMapping("/{id}/avatar")
  public ResponseEntity<?> updateAvatar(
    @PathVariable String id,
    @RequestParam("avatar") MultipartFile avatarFile) {
    try {
      User user = userService.updateUserAvatar(id, avatarFile);
      return ResponseEntity.ok(buildSuccessResponse("头像更新成功", user));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse("头像更新失败: " + e.getMessage()));
    }
  }

  /**
   * 清理无效头像
   * POST /api/users/{id}/clean-avatar
   */
  @PostMapping("/{id}/clean-avatar")
  public ResponseEntity<?> cleanAvatar(@PathVariable String id) {
    try {
      userService.clearInvalidAvatar(id);
      return ResponseEntity.ok(buildSuccessResponse("头像清理完成", null));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse("头像清理失败: " + e.getMessage()));
    }
  }


  /**
   * ----------------------------------
   * 用户登录
   * POST /api/users/login
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
    User user = userService.findByEmail(email);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    if (!PasswordUtil.verifyPassword(password, user.getPasswordHash(), user.getSalt())) {
      throw new BusinessException("密码错误");
    }
    User userOpt = userService.login(email, password);
    if (userOpt != null) {
      return ResponseEntity.ok(buildSuccessResponse("登录成功", userOpt.getEmail()));
    }
    return ResponseEntity.status(401).body(buildErrorResponse("邮箱或密码错误"));
  }


  /**
   * 根据邮箱查询用户
   * GET /api/users/email/{email}
   */
  @GetMapping("/email/{email}")
  public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
    User userOpt = userService.findByEmail(email);
    if (userOpt != null) {
      return ResponseEntity.ok(buildSuccessResponse("查询成功", userOpt.getEmail()));
    }
    return ResponseEntity.status(404).body(buildErrorResponse("用户不存在"));
  }

  /**
   * 检查邮箱是否已存在
   * GET /api/users/check-email?email=test@example.com
   */
  @GetMapping("/check-email")
  public ResponseEntity<?> checkEmailExists(@RequestParam String email) {
    boolean exists = userService.existsByEmail(email);
    Map<String, Object> result = new HashMap<>();
    result.put("email", email);
    result.put("exists", exists);
    result.put("message", exists ? "邮箱已存在" : "邮箱可用");
    return ResponseEntity.ok(buildSuccessResponse("检查完成", result));
  }


  /**
   * 修改密码
   * PUT /api/users/{id}/password
   */
  @PutMapping("/{id}/password")
  public ResponseEntity<?> changePassword(@PathVariable String id,
                                          @RequestParam String oldPassword,
                                          @RequestParam String newPassword) {
    try {
      Optional<Optional<User>> user = Optional.ofNullable(userService.findById(id));
      if (user == null) {
        throw new BusinessException("用户不存在");
      }
      String salt = PasswordUtil.generateSalt();
      String passwordHash = PasswordUtil.hashPassword(newPassword, salt);

      int success = userService.changePassword(id, oldPassword, passwordHash, salt);
      if (success > 0) {
        return ResponseEntity.ok(buildSuccessResponse("密码修改成功", null));
      }
      return ResponseEntity.badRequest().body(buildErrorResponse("密码修改失败"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 忘记密码 - 发送重置邮件
   * POST /api/users/forgot-password
   */
  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestParam String email) {
    try {
      String token = userService.requestPasswordReset(email);
      // 实际项目中应该发送邮件，这里返回token用于测试
      Map<String, Object> result = new HashMap<>();
      result.put("email", email);
      result.put("resetToken", token);
      result.put("message", "重置邮件已发送");
      return ResponseEntity.ok(buildSuccessResponse("重置请求已处理", result));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 重置密码
   * POST /api/users/reset-password
   */
  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestParam String token,
                                         @RequestParam String newPassword) {
    try {
      boolean success = userService.resetPassword(token, newPassword);
      if (success) {
        return ResponseEntity.ok(buildSuccessResponse("密码重置成功", null));
      }
      return ResponseEntity.badRequest().body(buildErrorResponse("密码重置失败"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 启用/禁用用户
   * PUT /api/users/{id}/status
   */
  @PutMapping("/{id}/status")
  public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestParam Integer status) {
    try {
      boolean success = userService.updateStatus(id, status);
      if (success) {
        String message = status == 1 ? "用户已启用" : "用户已禁用";
        return ResponseEntity.ok(buildSuccessResponse(message, null));
      }
      return ResponseEntity.badRequest().body(buildErrorResponse("状态更新失败"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }


  // 构建成功响应
  private Map<String, Object> buildSuccessResponse(String message, Object data) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", message);
    response.put("data", data);
    response.put("timestamp", System.currentTimeMillis());
    return response;
  }

  // 构建错误响应
  private Map<String, Object> buildErrorResponse(String message) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", message);
    response.put("timestamp", System.currentTimeMillis());
    return response;
  }
}
