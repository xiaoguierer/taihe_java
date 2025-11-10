package com.cn.taihe.back.product.service.impl;

import com.cn.taihe.back.product.dto.ProductSkuCreateDTO;
import com.cn.taihe.back.product.dto.ProductSkuQueryDTO;
import com.cn.taihe.back.product.dto.ProductSkuUpdateDTO;
import com.cn.taihe.back.product.entity.ProductSku;
import com.cn.taihe.back.product.mapper.ProductSkuMapper;
import com.cn.taihe.back.product.service.ProductSkuService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品SKU Service实现类
 *
 * @author ADMIN
 */
@Service
public class ProductSkuServiceImpl implements ProductSkuService {

  private static final Logger logger = LoggerFactory.getLogger(ProductSkuServiceImpl.class);

  private static final String OPERATOR = "ADMIN";

  @Autowired
  private ProductSkuMapper productSkuMapper;

  /**
   * 根据主键查找
   */
  @Override
  public ProductSku findById(String id) {
    logger.info("根据主键查找商品SKU，请求参数：id={}, operator={}", id, OPERATOR);
    try {
      ProductSku result = productSkuMapper.selectById(id);
      logger.info("根据主键查找商品SKU成功，返回结果：{}", result);
      return result;
    } catch (Exception e) {
      logger.error("根据主键查找商品SKU异常，id={}, operator={}, error={}", id, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 新增数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean create(ProductSkuCreateDTO createDTO) {
    logger.info("新增商品SKU，请求参数：{}, operator={}", createDTO, OPERATOR);
    try {
      // 检查SKU编码是否已存在
      int count = productSkuMapper.countBySkuCode(createDTO.getSkuCode(), null);
      if (count > 0) {
        logger.warn("SKU编码已存在，skuCode={}, operator={}", createDTO.getSkuCode(), OPERATOR);
        return false;
      }

      ProductSku productSku = new ProductSku();
      BeanUtils.copyProperties(createDTO, productSku);

      // 设置主键
      productSku.setId(String.valueOf(SnowflakeIdGenerator.nextId()));

      int result = productSkuMapper.insert(productSku);
      boolean success = result > 0;
      logger.info("新增商品SKU{}，id={}, operator={}", success ? "成功" : "失败", productSku.getId(), OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("新增商品SKU异常，createDTO={}, operator={}, error={}", createDTO, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 修改数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(ProductSkuUpdateDTO updateDTO) {
    logger.info("修改商品SKU，请求参数：{}, operator={}", updateDTO, OPERATOR);
    try {
      // 检查数据是否存在
      ProductSku existing = productSkuMapper.selectById(updateDTO.getId());
      if (existing == null) {
        logger.warn("商品SKU不存在，id={}, operator={}", updateDTO.getId(), OPERATOR);
        return false;
      }

      // 检查SKU编码是否已存在（排除当前记录）
      if (updateDTO.getSkuCode() != null && !updateDTO.getSkuCode().equals(existing.getSkuCode())) {
        int count = productSkuMapper.countBySkuCode(updateDTO.getSkuCode(), updateDTO.getId());
        if (count > 0) {
          logger.warn("SKU编码已存在，skuCode={}, operator={}", updateDTO.getSkuCode(), OPERATOR);
          return false;
        }
      }

      ProductSku productSku = new ProductSku();
      BeanUtils.copyProperties(updateDTO, productSku);

      int result = productSkuMapper.updateById(productSku);
      boolean success = result > 0;
      logger.info("修改商品SKU{}，id={}, operator={}", success ? "成功" : "失败", updateDTO.getId(), OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("修改商品SKU异常，updateDTO={}, operator={}, error={}", updateDTO, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据主键删除数据
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteById(String id) {
    logger.info("删除商品SKU，请求参数：id={}, operator={}", id, OPERATOR);
    try {
      int result = productSkuMapper.deleteById(id);
      boolean success = result > 0;
      logger.info("删除商品SKU{}，id={}, operator={}", success ? "成功" : "失败", id, OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("删除商品SKU异常，id={}, operator={}, error={}", id, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 条件分页查询数据
   */
  @Override
  public PageInfo<ProductSku> findByCondition(ProductSkuQueryDTO queryDTO, int page, int size) {
    logger.info("条件分页查询商品SKU，请求参数：queryDTO={}, page={}, size={}, operator={}", queryDTO, page, size, OPERATOR);
    try {
      PageHelper.startPage(page, size);
      List<ProductSku> list = productSkuMapper.selectByCondition(queryDTO);
      PageInfo<ProductSku> pageInfo = new PageInfo<>(list);
      logger.info("条件分页查询商品SKU成功，总记录数：{}, operator={}", pageInfo.getTotal(), OPERATOR);
      return pageInfo;
    } catch (Exception e) {
      logger.error("条件分页查询商品SKU异常，queryDTO={}, page={}, size={}, operator={}, error={}",
        queryDTO, page, size, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 查询所有数据
   */
  @Override
  public List<ProductSku> findAll() {
    logger.info("查询所有商品SKU，operator={}", OPERATOR);
    try {
      List<ProductSku> result = productSkuMapper.selectAll();
      logger.info("查询所有商品SKU成功，记录数：{}, operator={}", result.size(), OPERATOR);
      return result;
    } catch (Exception e) {
      logger.error("查询所有商品SKU异常，operator={}, error={}", OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据主键集合批量删除
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteByIds(List<String> ids) {
    logger.info("批量删除商品SKU，请求参数：ids={}, operator={}", ids, OPERATOR);
    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量删除商品SKU参数为空，operator={}", OPERATOR);
        return false;
      }

      int result = productSkuMapper.deleteByIds(ids);
      boolean success = result > 0;
      logger.info("批量删除商品SKU{}，删除记录数：{}, operator={}", success ? "成功" : "失败", result, OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("批量删除商品SKU异常，ids={}, operator={}, error={}", ids, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 更新状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatus(String id, Integer status, Long updatedBy) {
    logger.info("更新商品SKU状态，请求参数：id={}, status={}, updatedBy={}, operator={}", id, status, updatedBy, OPERATOR);
    try {
      int result = productSkuMapper.updateStatus(id, status, updatedBy);
      boolean success = result > 0;
      logger.info("更新商品SKU状态{}，id={}, status={}, operator={}", success ? "成功" : "失败", id, status, OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("更新商品SKU状态异常，id={}, status={}, updatedBy={}, operator={}, error={}",
        id, status, updatedBy, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 更新是否可售状态（冻结/解冻）
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateIsAvailable(String id, Boolean isAvailable, Long updatedBy) {
    logger.info("更新商品SKU可售状态，请求参数：id={}, isAvailable={}, updatedBy={}, operator={}",
      id, isAvailable, updatedBy, OPERATOR);
    try {
      int result = productSkuMapper.updateIsAvailable(id, isAvailable, updatedBy);
      boolean success = result > 0;
      logger.info("更新商品SKU可售状态{}，id={}, isAvailable={}, operator={}",
        success ? "成功" : "失败", id, isAvailable, OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("更新商品SKU可售状态异常，id={}, isAvailable={}, updatedBy={}, operator={}, error={}",
        id, isAvailable, updatedBy, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 批量更新状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean batchUpdateStatus(List<String> ids, Integer status, Long updatedBy) {
    logger.info("批量更新商品SKU状态，请求参数：ids={}, status={}, updatedBy={}, operator={}", ids, status, updatedBy, OPERATOR);
    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量更新商品SKU状态参数为空，operator={}", OPERATOR);
        return false;
      }

      int result = productSkuMapper.batchUpdateStatus(ids, status, updatedBy);
      boolean success = result > 0;
      logger.info("批量更新商品SKU状态{}，更新记录数：{}, operator={}", success ? "成功" : "失败", result, OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("批量更新商品SKU状态异常，ids={}, status={}, updatedBy={}, operator={}, error={}",
        ids, status, updatedBy, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 批量更新是否可售状态
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean batchUpdateIsAvailable(List<String> ids, Boolean isAvailable, Long updatedBy) {
    logger.info("批量更新商品SKU可售状态，请求参数：ids={}, isAvailable={}, updatedBy={}, operator={}",
      ids, isAvailable, updatedBy, OPERATOR);
    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量更新商品SKU可售状态参数为空，operator={}", OPERATOR);
        return false;
      }

      int result = productSkuMapper.batchUpdateIsAvailable(ids, isAvailable, updatedBy);
      boolean success = result > 0;
      logger.info("批量更新商品SKU可售状态{}，更新记录数：{}, operator={}", success ? "成功" : "失败", result, OPERATOR);
      return success;
    } catch (Exception e) {
      logger.error("批量更新商品SKU可售状态异常，ids={}, isAvailable={}, updatedBy={}, operator={}, error={}",
        ids, isAvailable, updatedBy, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据SPU ID查询
   */
  @Override
  public List<ProductSku> findBySpuId(String spuId) {
    logger.info("根据SPU ID查询商品SKU，请求参数：spuId={}, operator={}", spuId, OPERATOR);
    try {
      List<ProductSku> result = productSkuMapper.selectBySpuId(spuId);
      logger.info("根据SPU ID查询商品SKU成功，记录数：{}, operator={}", result.size(), OPERATOR);
      return result;
    } catch (Exception e) {
      logger.error("根据SPU ID查询商品SKU异常，spuId={}, operator={}, error={}", spuId, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 根据SKU编码查询
   */
  @Override
  public ProductSku findBySkuCode(String skuCode) {
    logger.info("根据SKU编码查询商品SKU，请求参数：skuCode={}, operator={}", skuCode, OPERATOR);
    try {
      ProductSku result = productSkuMapper.selectBySkuCode(skuCode);
      logger.info("根据SKU编码查询商品SKU成功，结果：{}, operator={}", result, OPERATOR);
      return result;
    } catch (Exception e) {
      logger.error("根据SKU编码查询商品SKU异常，skuCode={}, operator={}, error={}", skuCode, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 检查SKU编码是否存在
   */
  @Override
  public boolean isSkuCodeExists(String skuCode, String excludeId) {
    logger.info("检查SKU编码是否存在，请求参数：skuCode={}, excludeId={}, operator={}", skuCode, excludeId, OPERATOR);
    try {
      int count = productSkuMapper.countBySkuCode(skuCode, excludeId);
      boolean exists = count > 0;
      logger.info("检查SKU编码是否存在，结果：{}, operator={}", exists, OPERATOR);
      return exists;
    } catch (Exception e) {
      logger.error("检查SKU编码是否存在异常，skuCode={}, excludeId={}, operator={}, error={}",
        skuCode, excludeId, OPERATOR, e.getMessage(), e);
      throw e;
    }
  }
}
