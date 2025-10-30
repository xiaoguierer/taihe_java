package com.cn.taihe.back.suppliers.service.impl;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateCreateDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateQueryDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateUpdateDTO;
import com.cn.taihe.back.suppliers.entity.SupplierCertificate;
import com.cn.taihe.back.suppliers.mapper.SupplierCertificateMapper;
import com.cn.taihe.back.suppliers.service.SupplierCertificateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * 供应商资质文件服务实现类
 */
@Api(tags = "供应商资质文件管理服务实现")
@Service
public class SupplierCertificateServiceImpl implements SupplierCertificateService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierCertificateServiceImpl.class);
    private static final String OPERATOR = "ADMIN"; // 默认操作人

    @Autowired
    private SupplierCertificateMapper supplierCertificateMapper;

    @Override
    public SupplierCertificate getById(String id) {
        logger.info("【查询资质文件】开始执行，操作人：{}，参数：id={}", OPERATOR, id);

        try {
            SupplierCertificate result = supplierCertificateMapper.selectById(id);
            logger.info("【查询资质文件】执行成功，操作人：{}，结果：{}", OPERATOR, result);
            return result;
        } catch (Exception e) {
            logger.error("【查询资质文件】执行失败，操作人：{}，参数：id={}，错误信息：", OPERATOR, id, e);
            throw new RuntimeException("查询资质文件失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(SupplierCertificateCreateDTO createDTO) {
        logger.info("【新增资质文件】开始执行，操作人：{}，参数：{}", OPERATOR, createDTO);

        try {
            // 参数校验
            if (createDTO == null) {
                throw new IllegalArgumentException("新增参数不能为空");
            }

            // 转换DTO：CreateDTO -> 基础DTO
            SupplierCertificate certificateDTO = new SupplierCertificate();
            BeanUtils.copyProperties(createDTO, certificateDTO);
            certificateDTO.setId(UUID.randomUUID().toString()); // 生成UUID主键
            certificateDTO.setIsValid(0); // 默认有效

            // 修正：使用正确的参数绑定
            int result = supplierCertificateMapper.insert(certificateDTO);
            boolean success = result > 0;

            logger.info("【新增资质文件】执行{}，操作人：{}，生成ID：{}",
                    success ? "成功" : "失败", OPERATOR, certificateDTO.getId());
            return success;
        } catch (Exception e) {
            logger.error("【新增资质文件】执行失败，操作人：{}，参数：{}，错误信息：", OPERATOR, createDTO, e);
            throw new RuntimeException("新增资质文件失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(String id, SupplierCertificateUpdateDTO updateDTO) {
        logger.info("【更新资质文件】开始执行，操作人：{}，参数：id={}, updateDTO={}", OPERATOR, id, updateDTO);

        try {
            // 参数校验
            if (!StringUtils.hasText(id)) {
                throw new IllegalArgumentException("主键ID不能为空");
            }
            if (updateDTO == null) {
                throw new IllegalArgumentException("更新参数不能为空");
            }

            // 修正：明确传递id和updateDTO参数
            int result = supplierCertificateMapper.updateById(id, updateDTO);
            boolean success = result > 0;

            logger.info("【更新资质文件】执行{}，操作人：{}，ID：{}",
                    success ? "成功" : "失败", OPERATOR, id);
            return success;
        } catch (Exception e) {
            logger.error("【更新资质文件】执行失败，操作人：{}，参数：id={}, updateDTO={}，错误信息：",
                    OPERATOR, id, updateDTO, e);
            throw new RuntimeException("更新资质文件失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(String id) {
        logger.info("【删除资质文件】开始执行，操作人：{}，参数：id={}", OPERATOR, id);

        try {
            if (!StringUtils.hasText(id)) {
                throw new IllegalArgumentException("主键ID不能为空");
            }

            int result = supplierCertificateMapper.deleteById(id);
            boolean success = result > 0;

            logger.info("【删除资质文件】执行{}，操作人：{}，ID：{}",
                    success ? "成功" : "失败", OPERATOR, id);
            return success;
        } catch (Exception e) {
            logger.error("【删除资质文件】执行失败，操作人：{}，参数：id={}，错误信息：", OPERATOR, id, e);
            throw new RuntimeException("删除资质文件失败", e);
        }
    }

    @Override
    public PageInfo<SupplierCertificate> getByCondition(SupplierCertificateQueryDTO queryDTO, int page, int size) {
        logger.info("【条件分页查询】开始执行，操作人：{}，参数：queryDTO={}, page={}, size={}",
                OPERATOR, queryDTO, page, size);

        try {
            // 设置分页参数
            int currentPage = page <= 0 ? 1 : page;
            int pageSize = size <= 0 ? 10 : size;

            PageHelper.startPage(currentPage, pageSize);
            // 修正：使用正确的参数绑定
            List<SupplierCertificate> list = supplierCertificateMapper.selectByCondition(queryDTO);
            PageInfo<SupplierCertificate> pageInfo = new PageInfo<>(list);

            logger.info("【条件分页查询】执行成功，操作人：{}，查询结果：总记录数={}, 当前页={}, 页大小={}",
                    OPERATOR, pageInfo.getTotal(), currentPage, pageSize);
            return pageInfo;
        } catch (Exception e) {
            logger.error("【条件分页查询】执行失败，操作人：{}，参数：queryDTO={}, page={}, size={}，错误信息：",
                    OPERATOR, queryDTO, page, size, e);
            throw new RuntimeException("条件分页查询失败", e);
        }
    }

    @Override
    public List<SupplierCertificate> getAll() {
        logger.info("【查询所有资质文件】开始执行，操作人：{}", OPERATOR);

        try {
            List<SupplierCertificate> result = supplierCertificateMapper.selectAll();
            logger.info("【查询所有资质文件】执行成功，操作人：{}，记录数：{}", OPERATOR, result.size());
            return result;
        } catch (Exception e) {
            logger.error("【查询所有资质文件】执行失败，操作人：{}，错误信息：", OPERATOR, e);
            throw new RuntimeException("查询所有资质文件失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(List<String> ids) {
        logger.info("【批量删除资质文件】开始执行，操作人：{}，参数：ids={}", OPERATOR, ids);

        try {
            if (ids == null || ids.isEmpty()) {
                throw new IllegalArgumentException("主键ID集合不能为空");
            }

            int result = supplierCertificateMapper.deleteBatchIds(ids);
            boolean success = result > 0;

            logger.info("【批量删除资质文件】执行{}，操作人：{}，删除记录数：{}",
                    success ? "成功" : "失败", OPERATOR, result);
            return success;
        } catch (Exception e) {
            logger.error("【批量删除资质文件】执行失败，操作人：{}，参数：ids={}，错误信息：", OPERATOR, ids, e);
            throw new RuntimeException("批量删除资质文件失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(String id, Integer isValid) {
        logger.info("【更新资质文件状态】开始执行，操作人：{}，参数：id={}, isValid={}", OPERATOR, id, isValid);

        try {
            if (!StringUtils.hasText(id)) {
                throw new IllegalArgumentException("主键ID不能为空");
            }
            if (isValid == null || (isValid != 0 && isValid != 1)) {
                throw new IllegalArgumentException("状态值必须为0或1");
            }

            int result = supplierCertificateMapper.updateStatusById(id, isValid);
            boolean success = result > 0;

            logger.info("【更新资质文件状态】执行{}，操作人：{}，ID：{}，新状态：{}",
                    success ? "成功" : "失败", OPERATOR, id, isValid);
            return success;
        } catch (Exception e) {
            logger.error("【更新资质文件状态】执行失败，操作人：{}，参数：id={}, isValid={}，错误信息：",
                    OPERATOR, id, isValid, e);
            throw new RuntimeException("更新资质文件状态失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchStatus(List<String> ids, Integer isValid) {
        logger.info("【批量更新资质文件状态】开始执行，操作人：{}，参数：ids={}, isValid={}", OPERATOR, ids, isValid);

        try {
            if (ids == null || ids.isEmpty()) {
                throw new IllegalArgumentException("主键ID集合不能为空");
            }
            if (isValid == null || (isValid != 0 && isValid != 1)) {
                throw new IllegalArgumentException("状态值必须为0或1");
            }

            int result = supplierCertificateMapper.updateBatchStatus(ids, isValid);
            boolean success = result > 0;

            logger.info("【批量更新资质文件状态】执行{}，操作人：{}，更新记录数：{}，新状态：{}",
                    success ? "成功" : "失败", OPERATOR, result, isValid);
            return success;
        } catch (Exception e) {
            logger.error("【批量更新资质文件状态】执行失败，操作人：{}，参数：ids={}, isValid={}，错误信息：",
                    OPERATOR, ids, isValid, e);
            throw new RuntimeException("批量更新资质文件状态失败", e);
        }
    }

    @Override
    public List<SupplierCertificate> getBySupplierId(String supplierId) {
        logger.info("【根据供应商ID查询】开始执行，操作人：{}，参数：supplierId={}", OPERATOR, supplierId);

        try {
            if (!StringUtils.hasText(supplierId)) {
                throw new IllegalArgumentException("供应商ID不能为空");
            }

            List<SupplierCertificate> result = supplierCertificateMapper.selectBySupplierId(supplierId);
            logger.info("【根据供应商ID查询】执行成功，操作人：{}，记录数：{}", OPERATOR, result.size());
            return result;
        } catch (Exception e) {
            logger.error("【根据供应商ID查询】执行失败，操作人：{}，参数：supplierId={}，错误信息：",
                    OPERATOR, supplierId, e);
            throw new RuntimeException("根据供应商ID查询失败", e);
        }
    }

    @Override
    public List<SupplierCertificate> getBySupplierIdAndType(String supplierId, String certificateType) {
        logger.info("【根据供应商ID和类型查询】开始执行，操作人：{}，参数：supplierId={}, certificateType={}",
                OPERATOR, supplierId, certificateType);

        try {
            if (!StringUtils.hasText(supplierId)) {
                throw new IllegalArgumentException("供应商ID不能为空");
            }
            if (!StringUtils.hasText(certificateType)) {
                throw new IllegalArgumentException("资质类型不能为空");
            }

            List<SupplierCertificate> result = supplierCertificateMapper.selectBySupplierIdAndType(supplierId, certificateType);
            logger.info("【根据供应商ID和类型查询】执行成功，操作人：{}，记录数：{}", OPERATOR, result.size());
            return result;
        } catch (Exception e) {
            logger.error("【根据供应商ID和类型查询】执行失败，操作人：{}，参数：supplierId={}, certificateType={}，错误信息：",
                    OPERATOR, supplierId, certificateType, e);
            throw new RuntimeException("根据供应商ID和类型查询失败", e);
        }
    }
}