package com.cn.taihe.back.user.service.impl;

import com.cn.taihe.back.user.dto.UserAddressCreateDTO;
import com.cn.taihe.back.user.dto.UserAddressUpdateDTO;
import com.cn.taihe.back.user.entity.UserAddress;
import com.cn.taihe.back.user.mapper.UserAddressMapper;
import com.cn.taihe.back.user.service.UserAddressService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 用户收货地址表Service实现类
 *
 * @author system
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

  private static final Logger logger = LoggerFactory.getLogger(UserAddressServiceImpl.class);

  private static final String OPERATOR = "ADMIN";

  @Autowired
  private UserAddressMapper userAddressMapper;

  /**
   * 根据主键查找数据
   */
  @Override
  public UserAddress getById(String id) {
    logger.info("根据主键查找地址开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return null;
    }

    try {
      UserAddress result = userAddressMapper.selectById(id);
      logger.info("根据主键查找地址成功，操作人：{}，结果：{}", OPERATOR, result);
      return result;
    } catch (Exception e) {
      logger.error("根据主键查找地址异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("查询地址信息失败", e);
    }
  }

  /**
   * 新增用户地址
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public UserAddress create(UserAddressCreateDTO createDTO) {
    logger.info("新增用户地址开始，操作人：{}，参数：{}", OPERATOR, createDTO);

    if (createDTO == null) {
      logger.warn("新增参数不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("参数不能为空");
    }

    try {
      // DTO转Entity
      UserAddress userAddress = new UserAddress();
      BeanUtils.copyProperties(createDTO, userAddress);

      // 设置系统字段
      userAddress.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
      userAddress.setCreatedBy(OPERATOR);
      userAddress.setUpdatedBy(OPERATOR);
      userAddress.setDeleted(0);

      // 如果设置为默认地址，先取消其他默认地址
      if (userAddress.getIsDefault() != null && userAddress.getIsDefault() == 1) {
        userAddressMapper.updateAllNotDefault(createDTO.getUserId());
      }

      int result = userAddressMapper.insert(userAddress);
      if (result > 0) {
        logger.info("新增用户地址成功，操作人：{}，地址ID：{}", OPERATOR, userAddress.getId());
        return userAddress;
      } else {
        logger.error("新增用户地址失败，操作人：{}，参数：{}", OPERATOR, createDTO);
        throw new RuntimeException("新增地址失败");
      }
    } catch (Exception e) {
      logger.error("新增用户地址异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      throw new RuntimeException("新增地址失败", e);
    }
  }

  /**
   * 修改用户地址
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public UserAddress update(UserAddressUpdateDTO updateDTO) {
    logger.info("修改用户地址开始，操作人：{}，参数：{}", OPERATOR, updateDTO);

    if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
      logger.warn("修改参数不能为空或地址ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("参数不能为空");
    }

    try {
      // 检查地址是否存在
      UserAddress existing = userAddressMapper.selectById(updateDTO.getId());
      if (existing == null) {
        logger.warn("要修改的地址不存在，操作人：{}，地址ID：{}", OPERATOR, updateDTO.getId());
        throw new RuntimeException("地址不存在");
      }

      // DTO转Entity
      UserAddress userAddress = new UserAddress();
      BeanUtils.copyProperties(updateDTO, userAddress);
      userAddress.setUpdatedBy(OPERATOR);

      // 如果设置为默认地址，先取消其他默认地址
      if (userAddress.getIsDefault() != null && userAddress.getIsDefault() == 1) {
        userAddressMapper.updateAllNotDefault(existing.getUserId());
      }

      int result = userAddressMapper.updateByIdSelective(userAddress);
      if (result > 0) {
        UserAddress updated = userAddressMapper.selectById(updateDTO.getId());
        logger.info("修改用户地址成功，操作人：{}，地址ID：{}", OPERATOR, updateDTO.getId());
        return updated;
      } else {
        logger.error("修改用户地址失败，操作人：{}，参数：{}", OPERATOR, updateDTO);
        throw new RuntimeException("修改地址失败");
      }
    } catch (Exception e) {
      logger.error("修改用户地址异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      throw new RuntimeException("修改地址失败", e);
    }
  }

  /**
   * 根据主键逻辑删除地址
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    logger.info("逻辑删除地址开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return false;
    }

    try {
      // 检查地址是否存在
      UserAddress existing = userAddressMapper.selectById(id);
      if (existing == null) {
        logger.warn("要删除的地址不存在，操作人：{}，地址ID：{}", OPERATOR, id);
        return false;
      }

      int result = userAddressMapper.logicDeleteById(id);
      boolean success = result > 0;
      logger.info("逻辑删除地址{}，操作人：{}，地址ID：{}", success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("逻辑删除地址异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("删除地址失败", e);
    }
  }

  /**
   * 根据用户ID查询地址列表
   */
  @Override
  public List<UserAddress> getByUserId(String userId) {
    logger.info("根据用户ID查询地址列表开始，操作人：{}，参数：userId={}", OPERATOR, userId);

    if (!StringUtils.hasText(userId)) {
      logger.warn("用户ID不能为空，操作人：{}", OPERATOR);
      throw new IllegalArgumentException("用户ID不能为空");
    }

    try {
      List<UserAddress> result = userAddressMapper.selectByUserId(userId);
      logger.info("根据用户ID查询地址列表成功，操作人：{}，结果数量：{}", OPERATOR, result.size());
      return result;
    } catch (Exception e) {
      logger.error("根据用户ID查询地址列表异常，操作人：{}，参数：userId={}，异常信息：", OPERATOR, userId, e);
      throw new RuntimeException("查询地址列表失败", e);
    }
  }

  /**
   * 查询所有地址列表（分页）
   */
  @Override
  public PageInfo<UserAddress> getAllWithPage(Integer page, Integer size) {
    logger.info("分页查询所有地址列表开始，操作人：{}，参数：page={}, size={}", OPERATOR, page, size);

    // 设置默认分页参数
    int pageNum = (page == null || page < 1) ? 1 : page;
    int pageSize = (size == null || size < 1) ? 10 : size;

    try {
      PageHelper.startPage(pageNum, pageSize);
      List<UserAddress> addressList = userAddressMapper.selectAll();
      PageInfo<UserAddress> pageInfo = new PageInfo<>(addressList);

      logger.info("分页查询所有地址列表成功，操作人：{}，总记录数：{}，当前页：{}，页大小：{}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
      return pageInfo;
    } catch (Exception e) {
      logger.error("分页查询所有地址列表异常，操作人：{}，参数：page={}, size={}，异常信息：", OPERATOR, page, size, e);
      throw new RuntimeException("分页查询地址列表失败", e);
    }
  }

  /**
   * 冻结地址（设置为无效）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean freeze(String id) {
    logger.info("冻结地址开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return false;
    }

    try {
      // 检查地址是否存在
      UserAddress existing = userAddressMapper.selectById(id);
      if (existing == null) {
        logger.warn("要冻结的地址不存在，操作人：{}，地址ID：{}", OPERATOR, id);
        return false;
      }

      int result = userAddressMapper.updateStatus(id, 0);
      boolean success = result > 0;
      logger.info("冻结地址{}，操作人：{}，地址ID：{}", success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("冻结地址异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("冻结地址失败", e);
    }
  }

  /**
   * 解冻地址（设置为有效）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean unfreeze(String id) {
    logger.info("解冻地址开始，操作人：{}，参数：id={}", OPERATOR, id);

    if (!StringUtils.hasText(id)) {
      logger.warn("地址ID不能为空，操作人：{}", OPERATOR);
      return false;
    }

    try {
      // 检查地址是否存在
      UserAddress existing = userAddressMapper.selectById(id);
      if (existing == null) {
        logger.warn("要解冻的地址不存在，操作人：{}，地址ID：{}", OPERATOR, id);
        return false;
      }

      int result = userAddressMapper.updateStatus(id, 1);
      boolean success = result > 0;
      logger.info("解冻地址{}，操作人：{}，地址ID：{}", success ? "成功" : "失败", OPERATOR, id);
      return success;
    } catch (Exception e) {
      logger.error("解冻地址异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      throw new RuntimeException("解冻地址失败", e);
    }
  }

  /**
   * 设置默认地址
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean setDefault(String id, String userId) {
    logger.info("设置默认地址开始，操作人：{}，参数：id={}, userId={}", OPERATOR, id, userId);

    if (!StringUtils.hasText(id) || !StringUtils.hasText(userId)) {
      logger.warn("地址ID或用户ID不能为空，操作人：{}", OPERATOR);
      return false;
    }

    try {
      // 检查地址是否存在且属于该用户
      UserAddress existing = userAddressMapper.selectById(id);
      if (existing == null || !userId.equals(existing.getUserId())) {
        logger.warn("要设置的地址不存在或不属于该用户，操作人：{}，地址ID：{}，用户ID：{}", OPERATOR, id, userId);
        return false;
      }

      // 先取消该用户的所有默认地址
      userAddressMapper.updateAllNotDefault(userId);

      // 设置当前地址为默认地址
      UserAddress updateAddress = new UserAddress();
      updateAddress.setId(id);
      updateAddress.setIsDefault(1);
      updateAddress.setUpdatedBy(OPERATOR);

      int result = userAddressMapper.updateByIdSelective(updateAddress);
      boolean success = result > 0;
      logger.info("设置默认地址{}，操作人：{}，地址ID：{}，用户ID：{}",
        success ? "成功" : "失败", OPERATOR, id, userId);
      return success;
    } catch (Exception e) {
      logger.error("设置默认地址异常，操作人：{}，参数：id={}, userId={}，异常信息：", OPERATOR, id, userId, e);
      throw new RuntimeException("设置默认地址失败", e);
    }
  }

  /**
   * 获取用户的默认地址
   */
  @Override
  public UserAddress getDefaultAddress(String userId) {
    logger.info("获取用户默认地址开始，操作人：{}，参数：userId={}", OPERATOR, userId);

    if (!StringUtils.hasText(userId)) {
      logger.warn("用户ID不能为空，操作人：{}", OPERATOR);
      return null;
    }

    try {
      List<UserAddress> addressList = userAddressMapper.selectByUserId(userId);
      UserAddress defaultAddress = addressList.stream()
        .filter(address -> address.getIsDefault() != null && address.getIsDefault() == 1)
        .findFirst()
        .orElse(null);

      logger.info("获取用户默认地址成功，操作人：{}，用户ID：{}，默认地址：{}",
        OPERATOR, userId, defaultAddress != null ? defaultAddress.getId() : "无");
      return defaultAddress;
    } catch (Exception e) {
      logger.error("获取用户默认地址异常，操作人：{}，参数：userId={}，异常信息：", OPERATOR, userId, e);
      throw new RuntimeException("获取默认地址失败", e);
    }
  }
}
