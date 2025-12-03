package com.cn.taihe.back.user.controller;

import com.cn.taihe.back.user.dto.request.RegisterRequest;
import com.cn.taihe.back.user.dto.request.UserQueryCondition;
import com.cn.taihe.back.user.dto.response.UserResponse;
import com.cn.taihe.back.user.entity.User;
import com.cn.taihe.back.user.service.UserService;
import com.cn.taihe.common.*;
import com.cn.taihe.common.utils.PasswordUtil;
import com.cn.taihe.config.JwtUtil;
import com.cn.taihe.loginstiats.AllowAnonymous;
import com.cn.taihe.loginstiats.LogoutService;
import com.cn.taihe.loginstiats.RequireLogin;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
@RequireLogin
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private LogoutService logoutService;


  Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * 用户登录
   * POST /api/users/login
   */
  @AllowAnonymous
  @PostMapping("/login")
  public ResponseEntity<?> login(
    @RequestParam String email,
    @RequestParam String password) {
    logger.info("..................用户登录信息验证........");
    User user = userService.findByEmail(email);
    if (user == null) {
      throw new BusinessException("用户不存在");
    }
    if (!PasswordUtil.verifyPassword(password, user.getPasswordHash(), user.getSalt())) {
      throw new BusinessException("密码错误");
    }
    User userOpt = userService.login(email, password);
    // 生成JWT Token
    String token = jwtUtil.generateToken(user.getId());
    // 构建响应
    UserResponse userResponse = new UserResponse(userOpt);
    logger.info("登录生成的token 是：----------------",token);
    return ResponseEntity.ok(buildSuccessResponse(token, userResponse));
  }
  /**
   * 用户注册新增
   * POST /api/users/register
   */
  @AllowAnonymous
  @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> register(
    @RequestPart("request") RegisterRequest request,
    @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
    logger.info("新增注册用户信息........");
    logger.info("生成的日期是：" + LocalDateTime.now());
    logger.info("接收的日期是：" + request.getBirthdaytime());
    try {

      //检测邮箱是否存在
      if (userService.existsByEmail(request.getEmail())) {
        return ResponseEntity.badRequest().body(buildErrorResponse("邮箱已被注册"));
      }
      // 2. 生成盐值和加密密码
      String salt = PasswordUtil.generateSalt();
      String passwordHash = PasswordUtil.hashPassword(request.getPassword(), salt);
      User user = new User();
      // 优先使用文件上传方式
      if (avatarFile != null && !avatarFile.isEmpty()) {
        user = userService.registerWithAvatar(
          request.getEmail(),
          passwordHash,
          salt,
          request.getNickname(),
          avatarFile,
          Integer.valueOf(1),
          request.getBirthdaytime()
        );
      } else {
        // 使用URL方式
        user = userService.register(
          request.getEmail(),
          passwordHash,
          salt,
          request.getNickname(),
          request.getAvatar(),
          Integer.valueOf(1),
          request.getBirthdaytime()
        );
      }
      // 生成JWT Token
      String token = jwtUtil.generateToken(user.getId());
      // 构建响应
      UserResponse userResponse = new UserResponse(user);
      logger.info("注册生成的token 是：----------------",token);
      return ResponseEntity.ok(buildSuccessResponse(token, userResponse));
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
      return ResponseEntity.ok(Result.success(userOpt));
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
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime birthdaytime,
                                         @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
    int result = 0;
    try {
      if (avatarFile != null && !avatarFile.isEmpty()) {
        logger.info("头像信息上传不为空");
        result = userService.updateProfile(id, email, nickname, avatar, status, avatarFile, birthdaytime);
      } else {
        result = userService.updateProfile(id, email, nickname, avatar, status, birthdaytime);
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
   * 退出登录接口
   */
  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    try {
      String token = extractTokenFromRequest(request);
      if (token == null) {
        return ResponseEntity.badRequest().body(buildErrorResponse("未找到token: " + token));
      }
      // 执行退出操作
      logoutService.logout(token);
      logger.info("用户退出登录成功");
      return ResponseEntity.ok(Result.success("退出成功"));

    } catch (Exception e) {
      logger.error("退出登录失败", e);
      return ResponseEntity.badRequest().body(buildErrorResponse("退出水白: " + e.getMessage()));
    }
  }

  /**
   * 管理员接口：强制清理黑名单
   */
  @PostMapping("/logout/cleanup")
  public ResponseEntity<?> cleanupBlacklist() {
    try {
      logoutService.forceLogoutAll();
      return ResponseEntity.ok(Result.success("黑名单清理完成"));
    } catch (Exception e) {
      logger.error("清理黑名单失败", e);
      return ResponseEntity.badRequest().body(buildErrorResponse("token清理失败: " + e.getMessage()));
    }
  }

  /**
   * 查看黑名单统计信息
   */
  @GetMapping("/logout/statistics")
  public ResponseEntity<?> getStatistics() {
    try {
      Object stats = logoutService.getLogoutStatistics();
      return ResponseEntity.ok(Result.success(stats));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(buildErrorResponse("获取统计信息失败: " + e.getMessage()));
    }
  }

  private String extractTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    // 也可以从参数中获取（兼容性）
    String paramToken = request.getParameter("token");
    if (paramToken != null && !paramToken.trim().isEmpty()) {
      return paramToken;
    }

    return null;
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
