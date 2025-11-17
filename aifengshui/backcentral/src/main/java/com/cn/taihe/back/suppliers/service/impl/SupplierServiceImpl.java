package com.cn.taihe.back.suppliers.service.impl;

import com.cn.taihe.back.product.service.ProductSpuSupplierService;
import com.cn.taihe.back.suppliers.dto.request.SupplierCreateDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierQueryDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierUpdateDTO;
import com.cn.taihe.back.suppliers.entity.Supplier;
import com.cn.taihe.back.suppliers.mapper.SupplierMapper;
import com.cn.taihe.back.suppliers.service.SupplierService;
import com.cn.taihe.common.utils.SnowflakeIdGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 供应商服务实现类
 */
@Service
public class SupplierServiceImpl implements SupplierService {

  private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);
  @Autowired
  private SupplierMapper supplierMapper;
  @Autowired
  private ProductSpuSupplierService productSpuSupplierService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Supplier createSupplier(SupplierCreateDTO createDTO, String operator) {
    logger.info("创建供应商开始，参数：{}, 操作人：{}", createDTO, operator);

    Supplier Supplier = new Supplier();
    BeanUtils.copyProperties(createDTO, Supplier);

    // 设置系统字段
    Supplier.setId(String.valueOf(SnowflakeIdGenerator.nextId()));
    Supplier.setCreatedBy(operator);
    Supplier.setPerformanceScore(new BigDecimal("5.00"));
    Supplier.setQualityScore(new BigDecimal("5.00"));
    Supplier.setDeliveryScore(new BigDecimal("5.00"));
    Supplier.setServiceScore(new BigDecimal("5.00"));

    int result = supplierMapper.insertSupplier(Supplier);
    if (result <= 0) {
      logger.error("创建供应商失败，参数：{}", Supplier);
      throw new RuntimeException("创建供应商失败");
    }

    logger.info("创建供应商成功，ID：{}", Supplier.getId());
    return Supplier;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Supplier updateSupplier(SupplierUpdateDTO updateDTO, String operator) {
    logger.info("更新供应商开始，参数：{}, 操作人：{}", updateDTO, operator);

    // 先查询现有数据
    Supplier existingSupplier = supplierMapper.selectById(updateDTO.getId());
    if (existingSupplier == null) {
      logger.error("供应商不存在，ID：{}", updateDTO.getId());
      throw new RuntimeException("供应商不存在");
    }

    Supplier Supplier = new Supplier();
    BeanUtils.copyProperties(updateDTO, Supplier);
    Supplier.setUpdatedBy(operator);

    int result = supplierMapper.updateSupplierSelective(Supplier);
    if (result <= 0) {
      logger.error("更新供应商失败，参数：{}", Supplier);
      throw new RuntimeException("更新供应商失败");
    }

    // 返回更新后的完整数据
    Supplier updatedSupplier = supplierMapper.selectById(updateDTO.getId());
    logger.info("更新供应商成功，ID：{}", updatedSupplier.getId());
    return updatedSupplier;
  }

  public int createRealiations(String spuId, List list) {
    return productSpuSupplierService.createRealiations(spuId, list);
  }

  @Override
  @Cacheable(value = "supplier", key = "#id")
  public Supplier getSupplierById(String id) {
    logger.info("获取供应商详情，ID：{}", id);
    Supplier supplier = supplierMapper.selectById(id);
    if (supplier == null) {
      logger.warn("供应商不存在，ID：{}", id);
      throw new RuntimeException("供应商不存在");
    }
    logger.info("获取供应商详情成功，ID：{}", id);
    return supplier;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteSupplierById(String id, String operator) {
    logger.info("删除供应商开始，ID：{}, 操作人：{}", id, operator);

    Supplier existingSupplier = supplierMapper.selectById(id);
    if (existingSupplier == null) {
      logger.warn("供应商不存在，ID：{}", id);
      throw new RuntimeException("供应商不存在");
    }

    int result = supplierMapper.deleteById(id);
    if (result <= 0) {
      logger.error("删除供应商失败，ID：{}", id);
      throw new RuntimeException("删除供应商失败");
    }

    logger.info("删除供应商成功，ID：{}", id);
  }

  @Override
  public PageInfo<Supplier> querySupplierPage(SupplierQueryDTO queryDTO, int page, int size) {
    logger.info("分页查询供应商开始，参数：{}, page: {}, size: {}", queryDTO, page, size);

    // 设置分页参数
    PageHelper.startPage(page, size);
    List<Supplier> supplierList = supplierMapper.selectByCondition(queryDTO);

    PageInfo<Supplier> pageInfo = new PageInfo<>(supplierList);
    logger.info("分页查询供应商成功，总数：{}", pageInfo.getTotal());
    return pageInfo;
  }

  @Override
  public List<Supplier> getAllSuppliers() {
    logger.info("获取所有供应商列表开始");
    List<Supplier> suppliers = supplierMapper.selectAll();
    logger.info("获取所有供应商列表成功，数量：{}", suppliers.size());
    return suppliers;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void batchDeleteSuppliers(List<String> ids, String operator) {
    logger.info("批量删除供应商开始，IDs：{}, 操作人：{}", ids, operator);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量删除供应商失败，ID列表为空");
      throw new RuntimeException("ID列表不能为空");
    }

    int result = supplierMapper.batchDeleteByIds(ids);
    if (result != ids.size()) {
      logger.error("批量删除供应商失败，预期删除：{}，实际删除：{}", ids.size(), result);
      throw new RuntimeException("批量删除供应商失败");
    }

    logger.info("批量删除供应商成功，数量：{}", result);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void batchUpdateStatus(List<String> ids, Integer status, String operator) {
    logger.info("批量更新供应商状态开始，IDs：{}, 状态：{}, 操作人：{}", ids, status, operator);

    if (ids == null || ids.isEmpty()) {
      logger.warn("批量更新状态失败，ID列表为空");
      throw new RuntimeException("ID列表不能为空");
    }

    if (status == null || (status != 0 && status != 1)) {
      logger.warn("批量更新状态失败，状态值不合法：{}", status);
      throw new RuntimeException("状态值不合法");
    }

    int result = supplierMapper.batchUpdateStatus(ids, status, operator);
    if (result != ids.size()) {
      logger.error("批量更新状态失败，预期更新：{}，实际更新：{}", ids.size(), result);
      throw new RuntimeException("批量更新状态失败");
    }

    logger.info("批量更新供应商状态成功，数量：{}", result);
  }
}
