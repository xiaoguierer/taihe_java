package com.cn.taihe.back.product.controller;
import com.cn.taihe.back.product.entity.Product;
import com.cn.taihe.back.product.service.ProductService;
import com.cn.taihe.back.user.entity.User;
import com.cn.taihe.common.ApiResponse;
import com.cn.taihe.common.ResponseBuilder;
import com.cn.taihe.common.ResponseUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品信息控制器
 *
 * @author auto generator
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/product")
@Api(tags = "商品管理API")
@Validated
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 新增商品
     *
     * @param product 商品信息
     * @return 操作结果
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增商品", notes = "创建新的商品信息")
    public ResponseEntity<Map<String, Object>> saveProduct(
            @Valid @RequestBody Product product) {
        logger.info("接收到新增商品请求，商品名称：{}", product.getName());

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = productService.saveProduct(product);
            if (success) {
                result.put("code", 200);
                result.put("message", "新增商品成功");
                result.put("data", product.getId());
                logger.info("新增商品成功，商品ID：{}", product.getId());
            } else {
                result.put("code", 500);
                result.put("message", "新增商品失败");
                logger.error("新增商品失败，商品名称：{}", product.getName());
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("新增商品异常，商品名称：{}，异常信息：{}", product.getName(), e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "新增商品异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 批量新增商品
     *
     * @param productList 商品列表
     * @return 操作结果
     */
    @PostMapping("/saveBatch")
    @ApiOperation(value = "批量新增商品", notes = "批量创建商品信息")
    public ResponseEntity<Map<String, Object>> saveBatchProduct(
            @Valid @RequestBody List<Product> productList) {
        logger.info("接收到批量新增商品请求，商品数量：{}", productList.size());

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = productService.saveBatchProduct(productList);
            if (success) {
                result.put("code", 200);
                result.put("message", "批量新增商品成功");
                result.put("data", productList.size());
                logger.info("批量新增商品成功，商品数量：{}", productList.size());
            } else {
                result.put("code", 500);
                result.put("message", "批量新增商品失败");
                logger.error("批量新增商品失败，商品数量：{}", productList.size());
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("批量新增商品异常，异常信息：{}", e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "批量新增商品异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 根据ID删除商品
     *
     * @param id 商品ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据ID删除商品", notes = "逻辑删除指定商品")
    public ResponseEntity<Map<String, Object>> deleteProductById(
            @ApiParam(value = "商品ID", required = true)
            @PathVariable("id") @NotBlank(message = "商品ID不能为空") String id) {
        logger.info("接收到删除商品请求，商品ID：{}", id);

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = productService.deleteProductById(id);
            if (success) {
                result.put("code", 200);
                result.put("message", "删除商品成功");
                logger.info("删除商品成功，商品ID：{}", id);
            } else {
                result.put("code", 404);
                result.put("message", "商品不存在或删除失败");
                logger.warn("删除商品失败，商品ID：{}", id);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("删除商品异常，商品ID：{}，异常信息：{}", id, e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "删除商品异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 批量删除商品
     *
     * @param ids 商品ID列表
     * @return 操作结果
     */
    @DeleteMapping("/deleteBatch")
    @ApiOperation(value = "批量删除商品", notes = "批量逻辑删除商品")
    public ResponseEntity<Map<String, Object>> deleteBatchProduct(
            @ApiParam(value = "商品ID列表", required = true)
            @RequestParam("ids") @NotNull(message = "商品ID列表不能为空") List<String> ids) {
        logger.info("接收到批量删除商品请求，ID数量：{}", ids.size());

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = productService.deleteBatchProductByIds(ids);
            if (success) {
                result.put("code", 200);
                result.put("message", "批量删除商品成功");
                result.put("data", ids.size());
                logger.info("批量删除商品成功，ID数量：{}", ids.size());
            } else {
                result.put("code", 500);
                result.put("message", "批量删除商品失败");
                logger.error("批量删除商品失败，ID数量：{}", ids.size());
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("批量删除商品异常，异常信息：{}", e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "批量删除商品异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 更新商品信息
     *
     * @param product 商品信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新商品信息", notes = "更新指定商品的全部信息")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @Valid @RequestBody Product product) {
        logger.info("接收到更新商品请求，商品ID：{}", product.getId());

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = productService.updateProduct(product);
            if (success) {
                result.put("code", 200);
                result.put("message", "更新商品成功");
                logger.info("更新商品成功，商品ID：{}", product.getId());
            } else {
                result.put("code", 404);
                result.put("message", "商品不存在或更新失败");
                logger.warn("更新商品失败，商品ID：{}", product.getId());
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("更新商品异常，商品ID：{}，异常信息：{}", product.getId(), e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "更新商品异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 根据ID查询商品
     *
     * @param id 商品ID
     * @return 商品信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据ID查询商品", notes = "根据商品ID查询详细信息")
    public ResponseEntity<Map<String, Object>> getProductById(
            @ApiParam(value = "商品ID", required = true)
            @PathVariable("id") @NotBlank(message = "商品ID不能为空") String id) {
        logger.info("接收到查询商品请求，商品ID：{}", id);

        Map<String, Object> result = new HashMap<>();
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                result.put("code", 200);
                result.put("message", "查询商品成功");
                result.put("data", product);
                logger.info("查询商品成功，商品ID：{}", id);
            } else {
                result.put("code", 404);
                result.put("message", "商品不存在");
                logger.warn("查询商品失败，商品不存在，商品ID：{}", id);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("查询商品异常，商品ID：{}，异常信息：{}", id, e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "查询商品异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 分页查询商品列表
     *
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 商品名称
     * @param status 状态
     * @return 分页结果
     */
    @GetMapping("/getlist")
    @ApiOperation(value = "分页查询商品列表", notes = "根据条件分页查询商品列表")
    public ResponseEntity<?> getProductListByPage(
            @ApiParam(value = "页码", defaultValue = "1")
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam(value = "每页数量", defaultValue = "10")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @ApiParam(value = "商品名称")
            @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "状态(0-下架，1-上架)")
            @RequestParam(value = "status", required = false) Integer status) {
        logger.info("接收到分页查询商品列表请求，页码：{}，每页数量：{}", pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> params = new HashMap<>();
            if (name != null) params.put("name", name);
            if (status != null) params.put("status", status);
            PageInfo<Product> pageInfo = productService.getProductListByPage(pageNum, pageSize, params);
            logger.info("分页查询商品列表成功，总数量：{}", pageInfo.getTotal());
            return ResponseBuilder.success(pageInfo);
          //  return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("分页查询商品列表异常，异常信息：{}", e.getMessage(), e);
            ApiResponse<PageInfo<Product>> errorResponse = ResponseUtil.error("400", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 更新商品状态
     *
     * @param id 商品ID
     * @param status 状态
     * @return 操作结果
     */
    @PutMapping("/updateStatus")
    @ApiOperation(value = "更新商品状态", notes = "更新商品上架/下架状态")
    public ResponseEntity<Map<String, Object>> updateProductStatus(
            @ApiParam(value = "商品ID", required = true)
            @RequestParam("id") @NotBlank(message = "商品ID不能为空") String id,
            @ApiParam(value = "状态(0-下架，1-上架)", required = true)
            @RequestParam("status") @NotNull(message = "状态不能为空") Integer status) {
        logger.info("接收到更新商品状态请求，商品ID：{}，状态：{}", id, status);

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = productService.updateProductStatus(id, status);
            if (success) {
                result.put("code", 200);
                result.put("message", "更新状态成功");
                logger.info("更新商品状态成功，商品ID：{}，状态：{}", id, status);
            } else {
                result.put("code", 404);
                result.put("message", "商品不存在或更新失败");
                logger.warn("更新商品状态失败，商品ID：{}", id);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("更新商品状态异常，商品ID：{}，异常信息：{}", id, e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "更新状态异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 批量更新商品状态
     *
     * @param ids 商品ID列表
     * @param status 状态
     * @return 操作结果
     */
    @PutMapping("/updateBatchStatus")
    @ApiOperation(value = "批量更新商品状态", notes = "批量更新商品上架/下架状态")
    public ResponseEntity<Map<String, Object>> updateBatchProductStatus(
            @ApiParam(value = "商品ID列表", required = true)
            @RequestParam("ids") @NotNull(message = "商品ID列表不能为空") List<String> ids,
            @ApiParam(value = "状态(0-下架，1-上架)", required = true)
            @RequestParam("status") @NotNull(message = "状态不能为空") Integer status) {
        logger.info("接收到批量更新商品状态请求，ID数量：{}，状态：{}", ids.size(), status);

        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = productService.updateBatchProductStatus(ids, status);
            if (success) {
                result.put("code", 200);
                result.put("message", "批量更新状态成功");
                result.put("data", ids.size());
                logger.info("批量更新商品状态成功，ID数量：{}", ids.size());
            } else {
                result.put("code", 500);
                result.put("message", "批量更新状态失败");
                logger.error("批量更新商品状态失败，ID数量：{}", ids.size());
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("批量更新商品状态异常，异常信息：{}", e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "批量更新状态异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 根据分类ID查询商品列表
     *
     * @param categoryId 分类ID
     * @return 商品列表
     */
    @GetMapping("/byCategory/{categoryId}")
    @ApiOperation(value = "根据分类查询商品", notes = "根据分类ID查询商品列表")
    public ResponseEntity<Map<String, Object>> getProductListByCategoryId(
            @ApiParam(value = "分类ID", required = true)
            @PathVariable("categoryId") @NotBlank(message = "分类ID不能为空") String categoryId) {
        logger.info("接收到根据分类查询商品请求，分类ID：{}", categoryId);

        Map<String, Object> result = new HashMap<>();
        try {
            List<Product> productList = productService.getProductListByCategoryId(categoryId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", productList);
            result.put("total", productList.size());
            logger.info("根据分类查询商品成功，分类ID：{}，数量：{}", categoryId, productList.size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("根据分类查询商品异常，分类ID：{}，异常信息：{}", categoryId, e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "查询异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    /**
     * 根据品牌ID查询商品列表
     *
     * @param brandId 品牌ID
     * @return 商品列表
     */
    @GetMapping("/byBrand/{brandId}")
    @ApiOperation(value = "根据品牌查询商品", notes = "根据品牌ID查询商品列表")
    public ResponseEntity<Map<String, Object>> getProductListByBrandId(
            @ApiParam(value = "品牌ID", required = true)
            @PathVariable("brandId") @NotBlank(message = "品牌ID不能为空") String brandId) {
        logger.info("接收到根据品牌查询商品请求，品牌ID：{}", brandId);

        Map<String, Object> result = new HashMap<>();
        try {
            List<Product> productList = productService.getProductListByBrandId(brandId);
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", productList);
            result.put("total", productList.size());
            logger.info("根据品牌查询商品成功，品牌ID：{}，数量：{}", brandId, productList.size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("根据品牌查询商品异常，品牌ID：{}，异常信息：{}", brandId, e.getMessage(), e);
            result.put("code", 500);
            result.put("message", "查询异常：" + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }
}