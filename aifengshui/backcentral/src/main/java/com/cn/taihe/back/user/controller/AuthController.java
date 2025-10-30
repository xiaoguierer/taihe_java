package com.cn.taihe.back.user.controller;

import com.cn.taihe.back.user.dto.request.RegisterRequest;
import com.cn.taihe.back.user.entity.User;
import com.cn.taihe.back.user.service.UserService;
import com.cn.taihe.common.BusinessException;
import com.cn.taihe.common.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // 1. 检查邮箱是否已存在
            if (userService.existsByEmail(request.getEmail())) {
                throw new BusinessException("邮箱已被注册");
            }
            // 2. 生成盐值和加密密码
            String salt = PasswordUtil.generateSalt();
            String passwordHash = PasswordUtil.hashPassword(request.getPassword(), salt);
            User user = userService.register(request.getEmail(), passwordHash,salt, request.getNickname(),request.getAvatar(),Integer.valueOf(request.getStatus()));
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        User userOpt = userService.login(email, password);
        if (userOpt != null) {
            return ResponseEntity.ok(userOpt.getEmail());
        }
        return ResponseEntity.status(401).body("登录失败");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            String token = userService.requestPasswordReset(email);
            return ResponseEntity.ok("重置令牌: " + token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok("密码重置成功");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
