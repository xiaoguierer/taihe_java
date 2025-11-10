package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.product.dto.ProductSpuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSpuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSpuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSpu;
import com.cn.taihe.back.product.mapper.ProductSpuMapper;
import com.cn.taihe.back.product.service.ProductSpuService;
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

import java.util.List;

/**
 * 商品SPU Service实现类
 *
 * @author system
 * @since 2025-01-01
 */
@Service
public class ProductSpuServiceImpl implements ProductSpuService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSpuServiceImpl.class);

  private static final String DEFAULT_OPERATOR = "ADMIN";

  @Autowired
  private ProductSpuMapper productSpuMapper;

  /**
   * 根据主键查找数据
   */
  @Override
  public ProductSpu getById(String id) {
    logger.info("根据主键查找商品SPU数据，请求参数: id={}, operator={}", id, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(id)) {
      logger.warn("主键ID不能为空");
      return null;
    }

    try {
      ProductSpu result = productSpuMapper.selectById(id);
      logger.info("根据主键查找商品SPU数据成功，返回结果: {}", result);
      return result;
    } catch (Exception e) {
      logger.error("根据主键查找商品SPU数据异常，id: {}, operator: {}", id, DEFAULT_OPERATOR, e);
      throw new RuntimeException("查询商品SPU数据失败", e);
    }
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(ProductSpuCreateDTO createDTO) {
    logger.info("新增商品SPU数据，请求参数: {}, operator={}", createDTO, DEFAULT_OPERATOR);

    if (createDTO == null) {
      logger.warn("新增参数不能为空");
      return false;
    }

    // 检查SPU编码是否已存在
    if (productSpuMapper.countBySpuCode(createDTO.getSpuCode(), null) > 0) {
      logger.warn("SPU编码已存在: {}", createDTO.getSpuCode());
      throw new RuntimeException("SPU编码已存在");
    }

    try {
      ProductSpu productSpu = new ProductSpu();
      BeanUtils.copyProperties(createDTO, productSpu);

      // 设置系统生成字段
      productSpu.setId(String.valueOf(SnowflakeIdGenerator.nextId()));

      int result = productSpuMapper.insert(productSpu);
      boolean success = result > 0;
      logger.info("新增商品SPU数据{}，id: {}, operator: {}",
        success ? "成功" : "失败", productSpu.getId(), DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("新增商品SPU数据异常，参数: {}, operator: {}", createDTO, DEFAULT_OPERATOR, e);
      throw new RuntimeException("新增商品SPU数据失败", e);
    }
  }

  /**
   * 修改数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(ProductSpuUpdateDTO updateDTO) {
    logger.info("修改商品SPU数据，请求参数: {}, operator={}", updateDTO, DEFAULT_OPERATOR);

    if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
      logger.warn("更新参数或主键ID不能为空");
      return false;
    }

    // 检查数据是否存在
    ProductSpu existing = productSpuMapper.selectById(updateDTO.getId());
    if (existing == null) {
      logger.warn("商品SPU数据不存在，id: {}", updateDTO.getId());
      throw new RuntimeException("商品SPU数据不存在");
    }

    // 检查SPU编码是否已存在（排除当前记录）
    if (StringUtils.hasText(updateDTO.getSpuCode()) &&
      !updateDTO.getSpuCode().equals(existing.getSpuCode())) {
      if (productSpuMapper.countBySpuCode(updateDTO.getSpuCode(), updateDTO.getId()) > 0) {
        logger.warn("SPU编码已存在: {}", updateDTO.getSpuCode());
        throw new RuntimeException("SPU编码已存在");
      }
    }

    try {
      ProductSpu productSpu = new ProductSpu();
      BeanUtils.copyProperties(updateDTO, productSpu);

      int result = productSpuMapper.updateByIdSelective(productSpu);
      boolean success = result > 0;
      logger.info("修改商品SPU数据{}，id: {}, operator: {}",
        success ? "成功" : "失败", updateDTO.getId(), DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("修改商品SPU数据异常，参数: {}, operator: {}", updateDTO, DEFAULT_OPERATOR, e);
      throw new RuntimeException("修改商品SPU数据失败", e);
    }
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    logger.info("删除商品SPU数据，请求参数: id={}, operator={}", id, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(id)) {
      logger.warn("主键ID不能为空");
      return false;
    }

    // 检查数据是否存在
    ProductSpu existing = productSpuMapper.selectById(id);
    if (existing == null) {
      logger.warn("商品SPU数据不存在，id: {}", id);
      throw new RuntimeException("商品SPU数据不存在");
    }

    try {
      int result = productSpuMapper.deleteById(id);
      boolean success = result > 0;
      logger.info("删除商品SPU数据{}，id: {}, operator: {}",
        success ? "成功" : "失败", id, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("删除商品SPU数据异常，id: {}, operator: {}", id, DEFAULT_OPERATOR, e);
      throw new RuntimeException("删除商品SPU数据失败", e);
    }
  }

  /**
   * 条件分页查询数据
   */
  @Override
  public PageInfo<ProductSpu> getByCondition(ProductSpuQueryDTO queryDTO, Integer page, Integer size) {
    logger.info("条件分页查询商品SPU数据，请求参数: queryDTO={}, page={}, size={}, operator={}",
      queryDTO, page, size, DEFAULT_OPERATOR);

    // 设置分页参数默认值
    int pageNum = (page == null || page < 1) ? 1 : page;
    int pageSize = (size == null || size < 1) ? 10 : size;

    try {
      // 开启分页
      PageHelper.startPage(pageNum, pageSize);
      List<ProductSpu> list = productSpuMapper.selectByCondition(queryDTO);
      PageInfo<ProductSpu> pageInfo = new PageInfo<>(list);

      logger.info("条件分页查询商品SPU数据成功，总记录数: {}, 当前页: {}, 页大小: {}, operator: {}",
        pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), DEFAULT_OPERATOR);
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询商品SPU数据异常，参数: {}, operator: {}", queryDTO, DEFAULT_OPERATOR, e);
      throw new RuntimeException("查询商品SPU数据失败", e);
    }
  }

  /**
   * 查询所有数据
   */
  @Override
  public List<ProductSpu> getAll() {
    logger.info("查询所有商品SPU数据，operator={}", DEFAULT_OPERATOR);

    try {
      List<ProductSpu> result = productSpuMapper.selectAll();
      logger.info("查询所有商品SPU数据成功，记录数: {}, operator: {}", result.size(), DEFAULT_OPERATOR);
      return result;
    } catch (Exception e) {
      logger.error("查询所有商品SPU数据异常，operator: {}", DEFAULT_OPERATOR, e);
      throw new RuntimeException("查询所有商品SPU数据失败", e);
    }
  }

  /**
   * 根据主键集合批量删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteBatchIds(List<String> ids) {
    logger.info("批量删除商品SPU数据，请求参数: ids={}, operator={}", ids, DEFAULT_OPERATOR);

    if (ids == null || ids.isEmpty()) {
      logger.warn("主键ID集合不能为空");
      return false;
    }

    try {
      int result = productSpuMapper.deleteBatchIds(ids);
      boolean success = result > 0;
      logger.info("批量删除商品SPU数据{}，删除记录数: {}, operator: {}",
        success ? "成功" : "失败", result, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("批量删除商品SPU数据异常，ids: {}, operator: {}", ids, DEFAULT_OPERATOR, e);
      throw new RuntimeException("批量删除商品SPU数据失败", e);
    }
  }

  /**
   * 根据主键更新状态（冻结/启用）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatusById(String id, Boolean isActive) {
    logger.info("更新商品SPU状态，请求参数: id={}, isActive={}, operator={}", id, isActive, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(id) || isActive == null) {
      logger.warn("主键ID和状态不能为空");
      return false;
    }

    // 检查数据是否存在
    ProductSpu existing = productSpuMapper.selectById(id);
    if (existing == null) {
      logger.warn("商品SPU数据不存在，id: {}", id);
      throw new RuntimeException("商品SPU数据不存在");
    }

    try {
      int result = productSpuMapper.updateStatusById(id, isActive, getCurrentOperatorId());
      boolean success = result > 0;
      logger.info("更新商品SPU状态{}，id: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", id, isActive, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("更新商品SPU状态异常，id: {}, isActive: {}, operator: {}",
        id, isActive, DEFAULT_OPERATOR, e);
      throw new RuntimeException("更新商品SPU状态失败", e);
    }
  }

  /**
   * 根据主键批量更新状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatusBatchIds(List<String> ids, Boolean isActive) {
    logger.info("批量更新商品SPU状态，请求参数: ids={}, isActive={}, operator={}", ids, isActive, DEFAULT_OPERATOR);

    if (ids == null || ids.isEmpty() || isActive == null) {
      logger.warn("主键ID集合和状态不能为空");
      return false;
    }

    try {
      int result = productSpuMapper.updateStatusBatchIds(ids, isActive, getCurrentOperatorId());
      boolean success = result > 0;
      logger.info("批量更新商品SPU状态{}，更新记录数: {}, 状态: {}, operator: {}",
        success ? "成功" : "失败", result, isActive, DEFAULT_OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("批量更新商品SPU状态异常，ids: {}, isActive: {}, operator: {}",
        ids, isActive, DEFAULT_OPERATOR, e);
      throw new RuntimeException("批量更新商品SPU状态失败", e);
    }
  }

  /**
   * 根据SPU编码查询数据
   */
  @Override
  public ProductSpu getBySpuCode(String spuCode) {
    logger.info("根据SPU编码查询商品SPU数据，请求参数: spuCode={}, operator={}", spuCode, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(spuCode)) {
      logger.warn("SPU编码不能为空");
      return null;
    }

    try {
      ProductSpu result = productSpuMapper.selectBySpuCode(spuCode);
      logger.info("根据SPU编码查询商品SPU数据成功，返回结果: {}", result);
      return result;
    } catch (Exception e) {
      logger.error("根据SPU编码查询商品SPU数据异常，spuCode: {}, operator: {}", spuCode, DEFAULT_OPERATOR, e);
      throw new RuntimeException("根据SPU编码查询商品SPU数据失败", e);
    }
  }

  /**
   * 检查SPU编码是否存在
   */
  @Override
  public boolean isSpuCodeExists(String spuCode, String excludeId) {
    logger.info("检查SPU编码是否存在，请求参数: spuCode={}, excludeId={}, operator={}",
      spuCode, excludeId, DEFAULT_OPERATOR);

    if (!StringUtils.hasText(spuCode)) {
      return false;
    }

    try {
      int count = productSpuMapper.countBySpuCode(spuCode, excludeId);
      boolean exists = count > 0;
      logger.info("检查SPU编码是否存在结果: {}, spuCode: {}, operator: {}", exists, spuCode, DEFAULT_OPERATOR);
      return exists;
    } catch (Exception e) {
      logger.error("检查SPU编码是否存在异常，spuCode: {}, operator: {}", spuCode, DEFAULT_OPERATOR, e);
      throw new RuntimeException("检查SPU编码是否存在失败", e);
    }
  }

  /**
   * 获取当前操作人ID（实际项目中应从安全上下文获取）
   */
  private Long getCurrentOperatorId() {
    // 这里模拟获取当前操作人ID，实际项目中应从SecurityContext或Token中获取
    return 1L; // 默认管理员ID
  }
}
