package com.cn.taihe.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  private final String secret;
  private final long expiration;

  public JwtUtil(@Value("${jwt.secret:mySuperSecretKeyChangeInProduction123!}") String secret,
                 @Value("${jwt.expiration:604800}") long expiration) {
    // 验证密钥长度
    if (secret == null || secret.length() < 32) {
      throw new IllegalArgumentException("JWT密钥长度至少32位，当前长度: " +
        (secret != null ? secret.length() : 0));
    }
    this.secret = secret;
    this.expiration = expiration;
    logger.info("JWT工具初始化完成，过期时间: {}秒", expiration);
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * 生成JWT Token
   */
  public String generateToken(String userId) {
    try {
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + expiration * 1000);

      String token = Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();

      logger.debug("为用户ID: {} 生成JWT Token成功", userId);
      return token;

    } catch (Exception e) {
      logger.error("生成JWT Token失败, 用户ID: {}", userId, e);
      throw new RuntimeException("生成Token失败", e);
    }
  }

  /**
   * 从Token中获取用户ID
   */
  public String getUserIdFromToken(String token) {
    try {
      Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();

      String userId = claims.getSubject();
      logger.debug("从Token中解析用户ID: {}", userId);
      return userId;

    } catch (ExpiredJwtException e) {
      logger.warn("JWT Token已过期: {}", e.getMessage());
      throw new RuntimeException("Token已过期", e);
    } catch (SignatureException e) {
      logger.warn("JWT Token签名无效: {}", e.getMessage());
      throw new RuntimeException("Token签名无效", e);
    } catch (MalformedJwtException e) {
      logger.warn("JWT Token格式错误: {}", e.getMessage());
      throw new RuntimeException("Token格式错误", e);
    } catch (Exception e) {
      logger.error("解析JWT Token失败", e);
      throw new RuntimeException("Token解析失败", e);
    }
  }

  /**
   * 验证Token是否有效
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token);
      logger.debug("JWT Token验证成功");
      return true;

    } catch (ExpiredJwtException e) {
      logger.warn("JWT Token已过期");
      return false;
    } catch (SignatureException e) {
      logger.warn("JWT Token签名无效");
      return false;
    } catch (MalformedJwtException e) {
      logger.warn("JWT Token格式错误");
      return false;
    } catch (Exception e) {
      logger.warn("JWT Token验证失败: {}", e.getMessage());
      return false;
    }
  }

  /**
   * 获取Token的过期时间
   */
  public Date getExpirationDateFromToken(String token) {
    try {
      Claims claims = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
      return claims.getExpiration();
    } catch (Exception e) {
      logger.error("获取Token过期时间失败", e);
      throw new RuntimeException("获取Token过期时间失败", e);
    }
  }

  /**
   * 检查Token是否即将过期（在指定时间内过期）
   */
  public boolean isTokenExpiringSoon(String token, long minutes) {
    try {
      Date expiration = getExpirationDateFromToken(token);
      long timeUntilExpiration = expiration.getTime() - System.currentTimeMillis();
      return timeUntilExpiration <= (minutes * 60 * 1000);
    } catch (Exception e) {
      logger.error("检查Token是否即将过期失败", e);
      return false;
    }
  }

  /**
   * 刷新Token（生成新Token）
   */
  public String refreshToken(String token) {
    try {
      String userId = getUserIdFromToken(token);
      return generateToken(userId);
    } catch (Exception e) {
      logger.error("刷新Token失败", e);
      throw new RuntimeException("刷新Token失败", e);
    }
  }
}
