package com.cn.taihe.back.suppliers.controller;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateCreateDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateQueryDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateUpdateDTO;
import com.cn.taihe.back.suppliers.entity.SupplierCertificate;
import com.cn.taihe.back.suppliers.service.SupplierCertificateService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商资质文件管理控制器
 * 严格遵循RESTful API规范
 */
@Api(tags = "供应商资质文件管理API")
@RestController
@RequestMapping("/supplier-certificates")
public class SupplierCertificateController {

    private static final Logger logger = LoggerFactory.getLogger(SupplierCertificateController.class);
    private static final String OPERATOR = "ADMIN"; // 默认操作人

    @Autowired
    private SupplierCertificateService supplierCertificateService;

    /**
     * 新增资质文件
     */
    @ApiOperation("新增资质文件")
    @PostMapping("/add")
    public ResponseEntity<Object> create(
            @Valid @RequestBody SupplierCertificateCreateDTO createDTO) {
        logger.info("【新增资质文件】API请求开始，操作人：{}，参数：{}", OPERATOR, createDTO);

        try {
            boolean result = supplierCertificateService.create(createDTO);
            if (result) {
                logger.info("【新增资质文件】API请求成功，操作人：{}", OPERATOR);
                return ResponseEntity.ok(result);
            } else {
                logger.warn("【新增资质文件】API请求失败，操作人：{}", OPERATOR);
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            logger.error("【新增资质文件】API请求异常，操作人：{}，参数：{}，错误信息：", OPERATOR, createDTO, e);
            return ResponseEntity.badRequest().body(e);
        }
    }

    /**
     * 更新资质文件信息
     */
    @ApiOperation("更新资质文件信息")
    @PutMapping("/{id}/profile")
    public ResponseEntity<Object> update(
            @ApiParam(value = "资质文件ID", required = true) @PathVariable String id,
            @Valid @RequestBody SupplierCertificateUpdateDTO updateDTO) {
        logger.info("【更新资质文件】API请求开始，操作人：{}，参数：id={}, updateDTO={}", OPERATOR, id, updateDTO);

        try {
            if (!StringUtils.hasText(id)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("id不能为空"));
            }

            boolean result = supplierCertificateService.update(id, updateDTO);
            if (result) {
                logger.info("【更新资质文件】API请求成功，操作人：{}，ID：{}", OPERATOR, id);
                return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
            } else {
                logger.warn("【更新资质文件】API请求失败，操作人：{}，ID：{}", OPERATOR, id);
                return ResponseEntity.badRequest().body(buildErrorResponse("跟新失败"));
            }
        } catch (Exception e) {
            logger.error("【更新资质文件】API请求异常，操作人：{}，参数：id={}, updateDTO={}，错误信息：", OPERATOR, id, updateDTO, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 根据主键获取资质文件详情
     */
    @ApiOperation("根据主键获取资质文件详情")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(
            @ApiParam(value = "资质文件ID", required = true) @PathVariable String id) {
        logger.info("【查询资质文件详情】API请求开始，操作人：{}，参数：id={}", OPERATOR, id);

        try {
            if (!StringUtils.hasText(id)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("失败"));
            }

            SupplierCertificate result = supplierCertificateService.getById(id);
            if (result != null) {
                logger.info("【查询资质文件详情】API请求成功，操作人：{}，ID：{}", OPERATOR, id);
                return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
            } else {
                logger.warn("【查询资质文件详情】API请求未找到数据，操作人：{}，ID：{}", OPERATOR, id);
                return ResponseEntity.badRequest().body(buildErrorResponse("失败"));
            }
        } catch (Exception e) {
            logger.error("【查询资质文件详情】API请求异常，操作人：{}，参数：id={}，错误信息：", OPERATOR, id, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 根据主键删除资质文件
     */
    @ApiOperation("根据主键删除资质文件")
    @DeleteMapping("/del")
    public ResponseEntity<Object> deleteById(
            @ApiParam(value = "资质文件ID", required = true) @RequestParam String id) {
        logger.info("【删除资质文件】API请求开始，操作人：{}，参数：id={}", OPERATOR, id);

        try {
            if (!StringUtils.hasText(id)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("资质文件ID不能为空"));
              //  return ResponseEntity.badRequest().body(ResponseResult.error("资质文件ID不能为空"));
            }

            boolean result = supplierCertificateService.deleteById(id);
            if (result) {
                logger.info("【删除资质文件】API请求成功，操作人：{}，ID：{}", OPERATOR, id);
                return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
            } else {
                logger.warn("【删除资质文件】API请求失败，操作人：{}，ID：{}", OPERATOR, id);
                return ResponseEntity.badRequest().body(buildErrorResponse("删除资质文件失败，记录不存在"));
            }
        } catch (Exception e) {
            logger.error("【删除资质文件】API请求异常，操作人：{}，参数：id={}，错误信息：", OPERATOR, id, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 条件分页查询资质文件列表
     */
    @ApiOperation("条件分页查询资质文件列表")
    @PostMapping("/query")
    public ResponseEntity<Object> getByCondition(
            @Valid @RequestBody SupplierCertificateQueryDTO queryDTO,
            @ApiParam(value = "页码，默认1") @RequestParam(defaultValue = "1") int page,
            @ApiParam(value = "每页大小，默认10") @RequestParam(defaultValue = "10") int size) {
        logger.info("【条件分页查询】API请求开始，操作人：{}，参数：queryDTO={}, page={}, size={}",
                OPERATOR, queryDTO, page, size);

        try {
            PageInfo<SupplierCertificate> result = supplierCertificateService.getByCondition(queryDTO, page, size);
            logger.info("【条件分页查询】API请求成功，操作人：{}，总记录数：{}", OPERATOR, result.getTotal());
            return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
        } catch (Exception e) {
            logger.error("【条件分页查询】API请求异常，操作人：{}，参数：queryDTO={}, page={}, size={}，错误信息：",
                    OPERATOR, queryDTO, page, size, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 获取所有资质文件列表
     */
    @ApiOperation("获取所有资质文件列表")
    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        logger.info("【查询所有资质文件】API请求开始，操作人：{}", OPERATOR);

        try {
            List<SupplierCertificate> result = supplierCertificateService.getAll();
            logger.info("【查询所有资质文件】API请求成功，操作人：{}，记录数：{}", OPERATOR, result.size());
            return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
        } catch (Exception e) {
            logger.error("【查询所有资质文件】API请求异常，操作人：{}，错误信息：", OPERATOR, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 批量删除资质文件
     */
    @ApiOperation("批量删除资质文件")
    @DeleteMapping("/batch-del")
    public ResponseEntity<Object> deleteBatch(
            @ApiParam(value = "资质文件ID集合", required = true) @RequestParam List<String> ids) {
        logger.info("【批量删除资质文件】API请求开始，操作人：{}，参数：ids={}", OPERATOR, ids);

        try {
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body(buildErrorResponse("资质文件ID集合不能为空"));
            }

            boolean result = supplierCertificateService.deleteBatch(ids);
            if (result) {
                logger.info("【批量删除资质文件】API请求成功，操作人：{}，删除ID数量：{}", OPERATOR, ids.size());
                return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
            } else {
                logger.warn("【批量删除资质文件】API请求失败，操作人：{}", OPERATOR);
                return ResponseEntity.badRequest().body(buildErrorResponse("批量删除资质文件失败"));
            }
        } catch (Exception e) {
            logger.error("【批量删除资质文件】API请求异常，操作人：{}，参数：ids={}，错误信息：", OPERATOR, ids, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 冻结/解冻资质文件
     */
    @ApiOperation("冻结/解冻资质文件")
    @PutMapping("/{id}/status")
    public ResponseEntity<Object> updateStatus(
            @ApiParam(value = "资质文件ID", required = true) @PathVariable String id,
            @ApiParam(value = "状态：0-有效，1-无效/冻结", required = true) @RequestParam Integer isValid) {
        logger.info("【更新资质文件状态】API请求开始，操作人：{}，参数：id={}, isValid={}", OPERATOR, id, isValid);

        try {
            if (!StringUtils.hasText(id)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("资质文件ID不能为空"));
            }
            if (isValid == null || (isValid != 0 && isValid != 1)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("状态值必须为0或1"));
            }

            boolean result = supplierCertificateService.updateStatus(id, isValid);
            if (result) {
                String statusText = isValid == 0 ? "解冻" : "冻结";
                logger.info("【更新资质文件状态】API请求成功，操作人：{}，ID：{}，操作：{}", OPERATOR, id, statusText);
                return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
            } else {
                logger.warn("【更新资质文件状态】API请求失败，操作人：{}，ID：{}", OPERATOR, id);
                return ResponseEntity.badRequest().body(buildErrorResponse("更新资质文件状态失败"));
            }
        } catch (Exception e) {
            logger.error("【更新资质文件状态】API请求异常，操作人：{}，参数：id={}, isValid={}，错误信息：",
                    OPERATOR, id, isValid, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 批量冻结/解冻资质文件
     */
    @ApiOperation("批量冻结/解冻资质文件")
    @PutMapping("/batch-status")
    public ResponseEntity<Object> updateBatchStatus(
            @ApiParam(value = "资质文件ID集合", required = true) @RequestParam List<String> ids,
            @ApiParam(value = "状态：0-有效，1-无效/冻结", required = true) @RequestParam Integer isValid) {
        logger.info("【批量更新资质文件状态】API请求开始，操作人：{}，参数：ids={}, isValid={}", OPERATOR, ids, isValid);

        try {
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.badRequest().body(buildErrorResponse("资质文件ID集合不能为空"));
            }
            if (isValid == null || (isValid != 0 && isValid != 1)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("状态值必须为0或1"));
            }

            boolean result = supplierCertificateService.updateBatchStatus(ids, isValid);
            if (result) {
                String statusText = isValid == 0 ? "解冻" : "冻结";
                logger.info("【批量更新资质文件状态】API请求成功，操作人：{}，操作ID数量：{}，操作：{}",
                        OPERATOR, ids.size(), statusText);
                return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
            } else {
                logger.warn("【批量更新资质文件状态】API请求失败，操作人：{}", OPERATOR);
                return ResponseEntity.badRequest().body(buildErrorResponse("批量更新资质文件状态失败"));
            }
        } catch (Exception e) {
            logger.error("【批量更新资质文件状态】API请求异常，操作人：{}，参数：ids={}, isValid={}，错误信息：",
                    OPERATOR, ids, isValid, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 根据供应商ID查询资质文件列表
     */
    @ApiOperation("根据供应商ID查询资质文件列表")
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<Object> getBySupplierId(
            @ApiParam(value = "供应商ID", required = true) @PathVariable String supplierId) {
        logger.info("【根据供应商ID查询】API请求开始，操作人：{}，参数：supplierId={}", OPERATOR, supplierId);

        try {
            if (!StringUtils.hasText(supplierId)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("供应商ID不能为空"));
            }

            List<SupplierCertificate> result = supplierCertificateService.getBySupplierId(supplierId);
            logger.info("【根据供应商ID查询】API请求成功，操作人：{}，记录数：{}", OPERATOR, result.size());
            return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
        } catch (Exception e) {
            logger.error("【根据供应商ID查询】API请求异常，操作人：{}，参数：supplierId={}，错误信息：", OPERATOR, supplierId, e);
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

    /**
     * 根据供应商ID和资质类型查询
     */
    @ApiOperation("根据供应商ID和资质类型查询")
    @GetMapping("/supplier/{supplierId}/type/{certificateType}")
    public ResponseEntity<Object> getBySupplierIdAndType(
            @ApiParam(value = "供应商ID", required = true) @PathVariable String supplierId,
            @ApiParam(value = "资质类型", required = true) @PathVariable String certificateType) {
        logger.info("【根据供应商ID和类型查询】API请求开始，操作人：{}，参数：supplierId={}, certificateType={}",
                OPERATOR, supplierId, certificateType);

        try {
            if (!StringUtils.hasText(supplierId)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("供应商ID不能为空"));
            }
            if (!StringUtils.hasText(certificateType)) {
                return ResponseEntity.badRequest().body(buildErrorResponse("资质类型不能为空"));
            }

            List<SupplierCertificate> result = supplierCertificateService.getBySupplierIdAndType(supplierId, certificateType);
            logger.info("【根据供应商ID和类型查询】API请求成功，操作人：{}，记录数：{}", OPERATOR, result.size());
            return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
        } catch (Exception e) {
            logger.error("【根据供应商ID和类型查询】API请求异常，操作人：{}，参数：supplierId={}, certificateType={}，错误信息：",
                    OPERATOR, supplierId, certificateType, e);
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