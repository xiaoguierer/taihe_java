package com.cn.taihe.back.suppliers.controller;

import com.cn.taihe.back.product.dto.CreateRelationsRequest;
import com.cn.taihe.back.suppliers.dto.request.SupplierCreateDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierQueryDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierUpdateDTO;
import com.cn.taihe.back.suppliers.entity.Supplier;
import com.cn.taihe.back.suppliers.service.SupplierService;
import com.cn.taihe.back.user.entity.User;
import com.cn.taihe.common.ApiResponse;
import com.cn.taihe.common.ResponseBuilder;
import com.cn.taihe.common.ResponseUtil;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 供应商管理控制器
 */
@RestController
@RequestMapping("/suppliers")
@Api(tags = "供应商管理")
@Validated
public class SupplierController {

  private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
  @Autowired
  private SupplierService supplierService;

  /**
   * 新增供应商
   */
  @PostMapping("/add")
  @ApiOperation(value = "新增供应商", notes = "创建新的供应商信息")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "operator", value = "操作人", required = true, paramType = "header", example = "admin")
  })
  public ResponseEntity<?> createSupplier(
    @Valid @RequestBody SupplierCreateDTO createDTO,
    @RequestHeader(value = "operator", defaultValue = "ADMIN") String operator) {

    logger.info("新增供应商请求，参数：{}, 操作人：{}", createDTO, operator);

    try {
      Supplier result = supplierService.createSupplier(createDTO, operator);
      logger.info("新增供应商成功，ID：{}", result.getId());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("新增供应商失败，参数：{}, 错误：{}", createDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(e);
    }
  }

  /**
   * 更新供应商信息
   */
  @PutMapping("/{id}/profile")
  @ApiOperation(value = "更新供应商", notes = "更新供应商信息")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "operator", value = "操作人", required = true, paramType = "header", example = "admin")
  })
  public ResponseEntity<?> updateSupplier(
    @Valid @RequestBody SupplierUpdateDTO updateDTO,
    @RequestHeader(value = "operator", defaultValue = "ADMIN") String operator) {

    logger.info("更新供应商请求，参数：{}, 操作人：{}", updateDTO, operator);

    try {
      Supplier result = supplierService.updateSupplier(updateDTO, operator);
      logger.info("更新供应商成功，ID：{}", result.getId());
      return ResponseEntity.ok(buildSuccessResponse("资料更新成功", result));
    } catch (Exception e) {
      logger.error("更新供应商失败，参数：{}, 错误：{}", updateDTO, e.getMessage(), e);
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 新增数据
   */
  @PostMapping(value = "/createRealiations")
  @ApiOperation(value = "新增SPU-商品标签")
  public ResponseEntity<Object> createRealiations(
    @RequestBody @Valid CreateRelationsRequest request) {
    logger.info("开始处理新增SPU-商品标签关系请求, 操作人: {}, 参数: {}", request.getSpuId());
    int result = supplierService.createRealiations(request.getSpuId(), request.getIntentIds());
    logger.info("新增SPU-商品标签关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}", result);
    return ResponseEntity.ok(Result.success(result));
  }


  /**
   * 新增数据
   */
  @PostMapping(value = "/createSupplierProductSkuRealiations")
  @ApiOperation(value = "新增SkU-供应商")
  public ResponseEntity<Object> createSupplierProductSkuRealiations(
    @RequestBody @Valid CreateRelationsRequest request) {
    logger.info("开始处理新增SkU-供应商关系请求, 操作人: {}, 参数: {}", request.getSpuId());
    int result = supplierService.createSupplierProductSkuRealiations(request.getSpuId(), request.getIntentIds());
    logger.info("新增SkU-供应商系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}", result);
    return ResponseEntity.ok(Result.success(result));
  }

  /**
   * 根据ID获取供应商详情
   */
  @GetMapping("/{id}")
  @ApiOperation(value = "获取供应商详情", notes = "根据ID获取供应商详细信息")
  @ApiImplicitParam(name = "id", value = "供应商ID", required = true, paramType = "path", example = "uuid")
  public ResponseEntity<?> getSupplierById(@PathVariable String id) {
    logger.info("获取供应商详情请求，ID：{}", id);
    try {
      Optional<Supplier> result = Optional.ofNullable(supplierService.getSupplierById(id));
      logger.info("获取供应商详情成功，ID：{}", id);
      return ResponseEntity.ok(buildSuccessResponse("查询成功", result.get()));
    } catch (Exception e) {
      logger.error("获取供应商详情失败，ID：{}, 错误：{}", id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 根据ID获取供应商详情
   */
  @GetMapping("/getBySkuId/{skuid}")
  @ApiOperation(value = "获取供应商详情", notes = "根据ID获取供应商详细信息")
  @ApiImplicitParam(name = "skuid", value = "产品sku id", required = true, paramType = "path", example = "uuid")
  public ResponseEntity<?> getSupplierByskuId(@PathVariable String skuid) {
    logger.info("获取供应商详情请求商品spu，ID：{}", skuid);
    try {
      List<Supplier> result = supplierService.getBySkuId(skuid);
      logger.info("获取供应商列表，ID：{}", skuid);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("获取供应商列表失败，ID：{}, 错误：{}", skuid, e.getMessage(), e);
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 根据ID删除供应商
   */
  @DeleteMapping("/del")
  @ApiOperation(value = "删除供应商", notes = "根据ID删除供应商")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "供应商ID", required = true, paramType = "path", example = "uuid"),
    @ApiImplicitParam(name = "operator", value = "操作人", required = true, paramType = "header", example = "admin")
  })
  public ResponseEntity<?> deleteSupplierById(
    @RequestParam String id,
    @RequestHeader(value = "operator", defaultValue = "ADMIN") String operator) {

    logger.info("删除供应商请求，ID：{}, 操作人：{}", id, operator);

    try {
      supplierService.deleteSupplierById(id, operator);
      logger.info("删除供应商成功，ID：{}", id);
      return ResponseEntity.ok(buildSuccessResponse("用户删除成功", null));
    } catch (Exception e) {
      logger.error("删除供应商失败，ID：{}, 错误：{}", id, e.getMessage(), e);
      return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
    }
  }

  /**
   * 条件分页查询供应商列表
   */
  @PostMapping("/query")
  @ApiOperation(value = "条件分页查询供应商", notes = "根据条件分页查询供应商列表")
  public ResponseEntity<?> querySupplierPage(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestBody SupplierQueryDTO queryDTO) {
    logger.info("条件分页查询供应商请求，参数：{}, page: {}, size: {}", queryDTO, page, size);
    try {
      PageInfo<Supplier> result = supplierService.querySupplierPage(queryDTO, page, size);
      logger.info("条件分页查询供应商成功，总数：{}", result.getTotal());
      //return ResponseEntity.ok(result);
      return ResponseBuilder.success(result);
    } catch (Exception e) {
      logger.error("条件分页查询供应商失败，参数：{}, 错误：{}", queryDTO, e.getMessage(), e);
      ApiResponse<PageInfo<User>> errorResponse = ResponseUtil.error("400", e.getMessage());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }

  /**
   * 获取所有供应商列表
   */
  @GetMapping("/all")
  @ApiOperation(value = "获取所有供应商", notes = "获取所有供应商列表（不分页）")
  public ResponseEntity<?> getAllSuppliers() {
    logger.info("获取所有供应商列表请求");
    try {
      List<Supplier> result = supplierService.getAllSuppliers();
      logger.info("获取所有供应商列表成功，数量：{}", result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("获取所有供应商列表失败，错误：{}", e.getMessage(), e);
      return ResponseEntity.badRequest().body(e);
    }
  }

  /**
   * 批量删除供应商
   */
  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除供应商", notes = "批量删除供应商")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "operator", value = "操作人", required = true, paramType = "header", example = "admin")
  })
  public ResponseEntity<?> batchDeleteSuppliers(
    @RequestBody List<String> ids,
    @RequestHeader(value = "operator", defaultValue = "ADMIN") String operator) {

    logger.info("批量删除供应商请求，IDs：{}, 操作人：{}", ids, operator);

    try {
      supplierService.batchDeleteSuppliers(ids, operator);
      logger.info("批量删除供应商成功，数量：{}", ids.size());
      return ResponseEntity.ok(null);
    } catch (Exception e) {
      logger.error("批量删除供应商失败，IDs：{}, 错误：{}", ids, e.getMessage(), e);
      return ResponseEntity.badRequest().body(e);
    }
  }

  /**
   * 批量更新供应商状态（冻结/解冻）
   */
  @PostMapping("/batch-status")
  @ApiOperation(value = "批量更新供应商状态", notes = "批量冻结或解冻供应商")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "operator", value = "操作人", required = true, paramType = "header", example = "admin")
  })
  public ResponseEntity<?> batchUpdateStatus(
    @RequestBody List<String> ids,
    @RequestParam Integer status,
    @RequestHeader(value = "operator", defaultValue = "ADMIN") String operator) {

    logger.info("批量更新供应商状态请求，IDs：{}, 状态：{}, 操作人：{}", ids, status, operator);

    try {
      supplierService.batchUpdateStatus(ids, status, operator);
      String action = status == 0 ? "冻结" : "解冻";
      logger.info("批量更新供应商状态成功，数量：{}, 操作：{}", ids.size(), action);
      return ResponseEntity.ok(null);
    } catch (Exception e) {
      logger.error("批量更新供应商状态失败，IDs：{}, 状态：{}, 错误：{}", ids, status, e.getMessage(), e);
      return ResponseEntity.badRequest().body(e);
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
