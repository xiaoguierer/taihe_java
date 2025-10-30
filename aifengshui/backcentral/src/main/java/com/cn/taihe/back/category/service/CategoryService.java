package com.cn.taihe.back.category.service;
import com.cn.taihe.back.category.dto.*;
import com.cn.taihe.back.category.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 商品分类服务接口
 */
public interface CategoryService {

    // 基础CRUD
    CategoryDTO getCategoryById(String id);
    boolean createCategory(CategoryCreateDTO dto);
    boolean updateCategory(Category dto);
    boolean deleteCategory(String id);

    // 树形结构
    List<CategoryTreeDTO> getCategoryTree();
    List<CategoryTreeDTO> getDisplayCategoryTree();

    // 子分类操作
    List<CategoryDTO> getChildrenByParentId(String parentId);
    boolean batchUpdateChildrenSort(Map<String, Integer> sortMap);

    // 状态管理
    boolean updateDisplayStatus(String id, Integer status);

    // 分类路径
    List<CategoryPathDTO> getCategoryPath(String categoryId);

    // 校验方法
    boolean existsById(String id);
    /**
     * PageHelper 分页查询分类列表
     */
    public PageInfo<CategoryDTO> getCategoryPage(CategoryQueryDTO queryDTO,
                                                 Integer pageNum,
                                                 Integer pageSize);

}