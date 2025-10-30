package com.cn.taihe.back.category.controller;
import com.cn.taihe.back.category.dto.*;
import com.cn.taihe.back.category.entity.Category;
import com.cn.taihe.back.category.service.CategoryService;
import com.cn.taihe.common.JsonUtil;
import com.cn.taihe.common.ResponseBuilder;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
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

@RestController
@RequestMapping("/categories")
@Validated
public class CategoryController {
    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    /**
     * PageHelper分页查询
     */
    @GetMapping("/categorieslist")
    public ResponseEntity<?>  getCategoryPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) Integer isDisplay,
            @RequestParam(required = false) Integer isDeleted) {

        CategoryQueryDTO queryDTO = new CategoryQueryDTO();
        queryDTO.setLevel(level);
        queryDTO.setName(name);
        queryDTO.setKeywords(keywords);
        queryDTO.setIsDisplay(isDisplay);
        queryDTO.setIsDeleted(isDeleted);
        PageInfo<CategoryDTO> pageInfo = categoryService.getCategoryPage(queryDTO, page, size);
        return ResponseBuilder.success(pageInfo);
    }



    @GetMapping("/tree")
    public ResponseEntity<?> getCategoryTree() {
        List<CategoryTreeDTO> categorytreelist  = categoryService.getCategoryTree();
        return ResponseBuilder.success(categorytreelist);
    }

    @PostMapping("/add")
    public Result<Boolean> createCategory(@Valid @RequestBody CategoryCreateDTO dto) {
        logger.info("dto information:{}", JsonUtil.toJson(dto));
        return Result.success(categoryService.createCategory(dto));
    }

    @PutMapping("/{id}/display-status")
    public Result<Boolean> updateDisplayStatus(
            @PathVariable String id,
            @RequestParam Integer status) {
        return Result.success(categoryService.updateDisplayStatus(id, status));
    }
    /**
     * 更新分类信息
     * 接口地址
     * /api/categories/update/
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody Category dto) {
        try {
            logger.info("更新分类信息", JsonUtil.toJson(dto));
            boolean bool = categoryService.updateCategory(dto);
            return ResponseEntity.ok(buildSuccessResponse("资料更新成功", dto));
        } catch (Exception e) {
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

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/del")
    public ResponseEntity<?> deletCategory(@RequestParam String id) {
        try {
            boolean success = categoryService.deleteCategory(id);
            if (success) {
                return ResponseEntity.ok(buildSuccessResponse("用户删除成功", null));
            }
            return ResponseEntity.badRequest().body(buildErrorResponse("用户删除失败"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildErrorResponse(e.getMessage()));
        }
    }

}