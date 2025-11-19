package com.cn.taihe.back.user.controller;

import com.cn.taihe.back.user.dto.UserAddressCreateDTO;
import com.cn.taihe.back.user.dto.UserAddressUpdateDTO;
import com.cn.taihe.back.user.entity.UserAddress;
import com.cn.taihe.back.user.service.UserAddressService;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收货地址表Controller
 *
 * @author system
 */
@RestController
@RequestMapping("/user-address")
@Api(value = "UserAddressController", tags = "用户收货地址管理接口")
public class UserAddressController {

  private static final Logger logger = LoggerFactory.getLogger(UserAddressController.class);

  private static final String OPERATOR = "ADMIN";

  @Autowired
  private UserAddressService userAddressService;

  /**
   * 根据主键查找地址
   */
  @GetMapping("/getById/{id}")
  @ApiOperation(value = "根据ID查询地址详情", notes = "根据地址ID查询地址详细信息")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "地址ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id) {

    logger.info("查询地址详情开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("地址ID不能为空"));
    }

    try {
      UserAddress result = userAddressService.getById(id);
      if (result != null) {
        logger.info("查询地址详情成功，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("地址不存在，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.badRequest().body(Result.error("地址不存在"));
      }
    } catch (Exception e) {
      logger.error("查询地址详情异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("查询地址详情失败"));
    }
  }

  /**
   * 新增用户地址
   */
  @PostMapping(value = "/add")
  @ApiOperation(value = "新增用户地址", notes = "创建新的用户收货地址")
  public ResponseEntity<Object> create(
    @ApiParam(value = "地址创建信息", required = true)
    @Validated @RequestBody UserAddressCreateDTO createDTO) {

    logger.info("新增用户地址开始，操作人：{}，参数：{}", OPERATOR, createDTO);

    if (createDTO == null) {
      logger.warn("新增参数不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("参数不能为空"));
    }

    try {
      UserAddress result = userAddressService.create(createDTO);
      logger.info("新增用户地址成功，操作人：{}，地址ID：{}", OPERATOR, result.getId());
      return ResponseEntity.ok(Result.success(result));
    } catch (IllegalArgumentException e) {
      logger.warn("参数验证失败，操作人：{}，参数：{}，错误信息：{}", OPERATOR, createDTO, e.getMessage());
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("新增用户地址异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      return ResponseEntity.badRequest().body(Result.error("新增地址失败"));
    }
  }

  /**
   * 修改用户地址
   */
  @PostMapping(value = "/update")
  @ApiOperation(value = "修改用户地址", notes = "更新用户收货地址信息")
  public ResponseEntity<Object> update(
    @ApiParam(value = "地址更新信息", required = true)
    @Validated @RequestBody UserAddressUpdateDTO updateDTO) {

    logger.info("修改用户地址开始，操作人：{}，参数：{}", OPERATOR, updateDTO);

    if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
      logger.warn("修改参数不能为空或地址ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("参数不能为空"));
    }

    try {
      UserAddress result = userAddressService.update(updateDTO);
      logger.info("修改用户地址成功，操作人：{}，地址ID：{}", OPERATOR, updateDTO.getId());
      return ResponseEntity.ok(Result.success(result));
    } catch (IllegalArgumentException e) {
      logger.warn("参数验证失败，操作人：{}，参数：{}，错误信息：{}", OPERATOR, updateDTO, e.getMessage());
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (RuntimeException e) {
      logger.warn("修改地址失败，操作人：{}，参数：{}，错误信息：{}", OPERATOR, updateDTO, e.getMessage());
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("修改用户地址异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      return ResponseEntity.badRequest().body(Result.error("修改地址失败"));
    }
  }

  /**
   * 根据主键删除地址
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "删除用户地址", notes = "根据地址ID逻辑删除用户收货地址")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "地址ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id) {

    logger.info("删除地址开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("地址ID不能为空"));
    }

    try {
      boolean result = userAddressService.deleteById(id);
      if (result) {
        logger.info("删除地址成功，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success("删除成功"));
      } else {
        logger.warn("删除地址失败，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.badRequest().body(Result.error("删除失败，地址不存在"));
      }
    } catch (Exception e) {
      logger.error("删除地址异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("删除地址失败"));
    }
  }

  /**
   * 根据用户ID查询地址列表
   */
  @GetMapping("/getByUserId/{userId}")
  @ApiOperation(value = "查询用户地址列表", notes = "根据用户ID查询该用户的所有收货地址")
  public ResponseEntity<Object> getByUserId(
    @ApiParam(value = "用户ID", required = true, example = "user123")
    @PathVariable String userId) {

    logger.info("查询用户地址列表开始，操作人：{}，参数：userId={}", OPERATOR, userId);

    if (!StringUtils.hasText(userId)) {
      logger.warn("用户ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("用户ID不能为空"));
    }

    try {
      List<UserAddress> result = userAddressService.getByUserId(userId);
      logger.info("查询用户地址列表成功，操作人：{}，用户ID：{}，地址数量：{}", OPERATOR, userId, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (IllegalArgumentException e) {
      logger.warn("参数验证失败，操作人：{}，参数：userId={}，错误信息：{}", OPERATOR, userId, e.getMessage());
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("查询用户地址列表异常，操作人：{}，参数：userId={}，异常信息：", OPERATOR, userId, e);
      return ResponseEntity.badRequest().body(Result.error("查询地址列表失败"));
    }
  }

  /**
   * 分页查询所有地址列表
   */
  @GetMapping("/page")
  @ApiOperation(value = "分页查询所有地址", notes = "分页查询所有用户的收货地址列表")
  public ResponseEntity<Object> getAllWithPage(
    @ApiParam(value = "页码", defaultValue = "1", example = "1")
    @RequestParam(defaultValue = "1") Integer page,
    @ApiParam(value = "每页大小", defaultValue = "10", example = "10")
    @RequestParam(defaultValue = "10") Integer size) {

    logger.info("分页查询所有地址开始，操作人：{}，参数：page={}, size={}", OPERATOR, page, size);

    try {
      PageInfo<UserAddress> result = userAddressService.getAllWithPage(page, size);
      logger.info("分页查询所有地址成功，操作人：{}，总记录数：{}，当前页：{}，页大小：{}",
        OPERATOR, result.getTotal(), result.getPageNum(), result.getPageSize());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("分页查询所有地址异常，操作人：{}，参数：page={}, size={}，异常信息：", OPERATOR, page, size, e);
      return ResponseEntity.badRequest().body(Result.error("分页查询地址列表失败"));
    }
  }

  /**
   * 冻结地址
   */
  @PutMapping("/freeze/{id}")
  @ApiOperation(value = "冻结地址", notes = "将地址设置为无效状态")
  public ResponseEntity<Object> freeze(
    @ApiParam(value = "地址ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id) {

    logger.info("冻结地址开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("地址ID不能为空"));
    }

    try {
      boolean result = userAddressService.freeze(id);
      if (result) {
        logger.info("冻结地址成功，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success("冻结成功"));
      } else {
        logger.warn("冻结地址失败，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.badRequest().body(Result.error("冻结失败，地址不存在"));
      }
    } catch (Exception e) {
      logger.error("冻结地址异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("冻结地址失败"));
    }
  }

  /**
   * 解冻地址
   */
  @PutMapping("/unfreeze/{id}")
  @ApiOperation(value = "解冻地址", notes = "将地址设置为有效状态")
  public ResponseEntity<Object> unfreeze(
    @ApiParam(value = "地址ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id) {

    logger.info("解冻地址开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("地址ID不能为空"));
    }

    try {
      boolean result = userAddressService.unfreeze(id);
      if (result) {
        logger.info("解冻地址成功，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success("解冻成功"));
      } else {
        logger.warn("解冻地址失败，操作人：{}，地址ID：{}", OPERATOR, id);
        return ResponseEntity.badRequest().body(Result.error("解冻失败，地址不存在"));
      }
    } catch (Exception e) {
      logger.error("解冻地址异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("解冻地址失败"));
    }
  }

  /**
   * 设置默认地址
   */
  @PutMapping("/set-default/{id}")
  @ApiOperation(value = "设置默认地址", notes = "将指定地址设置为用户的默认收货地址")
  public ResponseEntity<Object> setDefault(
    @ApiParam(value = "地址ID", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
    @PathVariable String id,
    @ApiParam(value = "用户ID", required = true, example = "user123")
    @RequestParam String userId) {

    logger.info("设置默认地址开始，操作人：{}，参数：id={}, userId={}", OPERATOR, id, userId);

    if (!StringUtils.hasText(id) || !StringUtils.hasText(userId)) {
      logger.warn("地址ID或用户ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("地址ID和用户ID不能为空"));
    }

    try {
      boolean result = userAddressService.setDefault(id, userId);
      if (result) {
        logger.info("设置默认地址成功，操作人：{}，地址ID：{}，用户ID：{}", OPERATOR, id, userId);
        return ResponseEntity.ok(Result.success("设置默认地址成功"));
      } else {
        logger.warn("设置默认地址失败，操作人：{}，地址ID：{}，用户ID：{}", OPERATOR, id, userId);
        return ResponseEntity.badRequest().body(Result.error("设置默认地址失败，地址不存在或不属于该用户"));
      }
    } catch (Exception e) {
      logger.error("设置默认地址异常，操作人：{}，参数：id={}, userId={}，异常信息：", OPERATOR, id, userId, e);
      return ResponseEntity.badRequest().body(Result.error("设置默认地址失败"));
    }
  }

  /**
   * 获取用户的默认地址
   */
  @GetMapping("/default/{userId}")
  @ApiOperation(value = "获取用户默认地址", notes = "获取指定用户的默认收货地址")
  public ResponseEntity<Object> getDefaultAddress(
    @ApiParam(value = "用户ID", required = true, example = "user123")
    @PathVariable String userId) {

    logger.info("获取用户默认地址开始，操作人：{}，参数：userId={}", OPERATOR, userId);

    if (!StringUtils.hasText(userId)) {
      logger.warn("用户ID不能为空，操作人：{}", OPERATOR);
      return ResponseEntity.badRequest().body(Result.error("用户ID不能为空"));
    }

    try {
      UserAddress result = userAddressService.getDefaultAddress(userId);
      logger.info("获取用户默认地址成功，操作人：{}，用户ID：{}，默认地址：{}",
        OPERATOR, userId, result != null ? result.getId() : "无");
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("获取用户默认地址异常，操作人：{}，参数：userId={}，异常信息：", OPERATOR, userId, e);
      return ResponseEntity.badRequest().body(Result.error("获取默认地址失败"));
    }
  }
}
