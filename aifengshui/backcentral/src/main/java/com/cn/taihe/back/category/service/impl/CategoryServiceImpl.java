package com.cn.taihe.back.category.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cn.taihe.back.category.dto.*;
import com.cn.taihe.back.category.entity.Category;
import com.cn.taihe.back.category.mapper.CategoryMapper;
import com.cn.taihe.back.category.service.CategoryService;
import com.cn.taihe.common.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 商品分类服务实现
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final int MAX_CATEGORY_LEVEL = 3;

    Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private  CategoryMapper categoryMapper;
    @Autowired
    private  CategoryConvert categoryConvert;

    // ========== 基础CRUD操作 ========== //
    /**
     * PageHelper 分页查询分类列表
     */
    @Override
    public PageInfo<CategoryDTO> getCategoryPage(CategoryQueryDTO queryDTO,
                                                 Integer pageNum,
                                                 Integer pageSize) {
        // 使用PageHelper分页
        PageHelper.startPage(pageNum, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getDeleted, 0);

        if (queryDTO != null) {
            if (queryDTO.getLevel() != null) {
                wrapper.eq(Category::getLevel, queryDTO.getLevel());
            }
            if (StringUtils.hasText(queryDTO.getName())) {
                wrapper.like(Category::getName, queryDTO.getName());
            }
            if (StringUtils.hasText(queryDTO.getKeywords())) {
                wrapper.like(Category::getKeywords, queryDTO.getKeywords());
            }
            if (queryDTO.getIsDisplay() != null) {
                wrapper.eq(Category::getDisplayStatus, queryDTO.getIsDisplay());
            }
            if (queryDTO.getIsDeleted() != null) {
                wrapper.eq(Category::getDeleted, queryDTO.getIsDeleted());
            }
        }

        // 关键修改：按level分组，再按sort排序
        wrapper.orderByAsc(Category::getLevel)  // 先按层级排序
                .orderByAsc(Category::getSort);   // 再按排序值排序

        // 关键修正：使用MyBatis-Plus的selectList方法，避免自定义XML方法
        List<Category> categories = categoryMapper.selectList(wrapper);

        // 转换为DTO
        List<CategoryDTO> dtos = categories.stream()
                .map(categoryConvert::entityToDTO)
                .collect(Collectors.toList());

        return new PageInfo<>(dtos);
    }



    @Override
    public CategoryDTO getCategoryById(String id) {
        Category category = categoryMapper.selectById(id);
        CategoryDTO dto = categoryConvert.entityToDTO(category);
        // 手动设置需要特殊处理的字段
        if (category.getParentId() != null) {
            Category parent = categoryMapper.selectById(category.getParentId());
            dto.setParentName(parent != null ? parent.getName() : null);
        }

        Integer childrenCount = categoryMapper.selectCount(
                Wrappers.<Category>lambdaQuery()
                        .eq(Category::getParentId, id)
                        .eq(Category::getDeleted, 0)
        );
        dto.setChildrenCount(childrenCount.intValue());

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"category", "categoryTree"}, allEntries = true)
    @Override
    public boolean createCategory(CategoryCreateDTO dto) {
        try {
            // 1. 参数验证
            validateForCreate(dto);
            // 2. 构建实体
            Category category = categoryConvert.createDTOToEntity(dto);
            category.setLevel(calculateLevel(dto.getParentId()));
            category.setDeleted(0);
            // 直接设置时间，避免调用不存在的方法
            Date now = new Date();
            category.setCreateTime(now);
            category.setUpdateTime(now);
            // 3. 持久化
            if (categoryMapper.insert(category) <= 0) {
                throw new BusinessException("category.create.failed");
            }
            log.info("创建分类成功：ID={}, Name={}", category.getId(), category.getName());
            return true;
        } catch (BusinessException e) {
            log.warn("创建分类业务异常：{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("创建分类系统异常", e);
            throw new BusinessException("category.create.error");
        }
    }

    @Transactional
    @Override
    public boolean updateCategory(Category dto) {
        // 1. 验证分类是否存在
        /*Category existing = categoryMapper.selectById(dto.getId());
        if (existing == null ) {
            throw new BusinessException("分类不存在");
        }*/
        // 2. 防止循环引用
        /*if (dto.getParentId() != null &&
                !dto.getParentId().equals(existing.getParentId())) {
            if (isCircularReference(dto.getId(), dto.getParentId())) {
                throw new BusinessException("禁止循环引用");
            }
        }*/
        // 3. 转换并更新
        //Category entity = categoryConvert.updateDTOToEntity(dto);
        dto.setUpdateTime(new Date());
        int result = categoryMapper.updateById(dto);
        log.info("000");
        if (result <= 0) {
            throw new BusinessException("更新失败");
        }
        return true;
    }


    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"category", "categoryTree"}, allEntries = true)
    @Override
    public boolean deleteCategory(String id) {
        // 1. 参数校验
        if (id == null) {
            throw new BusinessException("分类ID不能为空");
        }
        // 2. 检查分类是否存在
        Category category = categoryMapper.selectById(id);
        if (category == null ) {
            log.warn("分类不存在或已被删除：ID={}", id);
            throw new BusinessException("分类不存在");
        }
        // 3. 检查子分类
        List<Category> children = categoryMapper.selectChildrenByParentId(id);
        if (!CollectionUtils.isEmpty(children)) {
            log.warn("存在未删除的子分类：ID={}, 数量={}", id, children.size());
            throw new BusinessException("请先删除子分类");
        }
        // 4. 逻辑删除
        try {
            int result = categoryMapper.logicalDeleteById(id);
            if (result <= 0) {
                throw new BusinessException("分类删除失败");
            }
            log.info("分类删除成功：ID={}", id);
            return true;
        } catch (Exception e) {
            log.error("数据库删除异常：ID={}", id, e);
            throw new BusinessException("系统繁忙，请稍后重试");
        }
    }

    // ========== 树形结构操作 ========== //

    @Override
    @Cacheable(value = "categoryTree", key = "'all'")
    public List<CategoryTreeDTO> getCategoryTree() {
        List<Category> allCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getDeleted, 0)
                        .orderByAsc(Category::getSort)
                        .orderByAsc(Category::getId)
        );
        return buildCategoryTree(allCategories);
    }

    @Override
    @Cacheable(value = "categoryTree", key = "'display'")
    public List<CategoryTreeDTO> getDisplayCategoryTree() {
        List<Category> displayCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .eq(Category::getDeleted, 0)
                        .eq(Category::getDisplayStatus, 1)
                        .orderByAsc(Category::getSort)
                        .orderByAsc(Category::getId)
        );
        return buildCategoryTree(displayCategories);
    }

    @Override
    public List<CategoryDTO> getChildrenByParentId(String parentId) {
        if (parentId == null) {
            throw new BusinessException("category.parentId.null");
        }

        List<Category> children = categoryMapper.selectList(
                Wrappers.<Category>lambdaQuery()
                        .eq(Category::getParentId, parentId)
                        .eq(Category::getDeleted, 0)
                        .orderByAsc(Category::getSort)
        );

        return children.stream()
                .map(categoryConvert::entityToDTO)
                .collect(Collectors.toList());
    }

    // ========== 其他业务操作 ========== //

    @Transactional
    @CacheEvict(value = "categoryTree", allEntries = true)
    @Override
    public boolean batchUpdateChildrenSort(Map<String, Integer> sortMap) {
        if (CollectionUtils.isEmpty(sortMap)) {
            throw new BusinessException("category.sortMap.empty");
        }

        sortMap.forEach((categoryId, sort) -> {
            Category updateEntity = new Category();
            updateEntity.setId(categoryId);
            updateEntity.setSort(sort);
            updateEntity.setUpdateTime(new Date());

            if (categoryMapper.updateById(updateEntity) <= 0) {
                log.warn("更新分类排序失败：ID={}, Sort={}", categoryId, sort);
            }
        });

        log.info("批量更新分类排序完成：count={}", sortMap.size());
        return true;
    }

    @Transactional
    @CacheEvict(value = {"category", "categoryTree"}, allEntries = true)
    @Override
    public boolean updateDisplayStatus(String id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("category.status.invalid");
        }

        Category category = getExistingCategory(id);
        Category updateEntity = new Category();
        updateEntity.setId(id);
        updateEntity.setDisplayStatus(status);
        updateEntity.setUpdateTime(new Date());
        //updateEntity.setVersion(category.getVersion());

        int result = categoryMapper.updateById(updateEntity);
        if (result <= 0) {
            throw new BusinessException("category.status.update.failed");
        }

        log.info("更新分类显示状态：ID={}, Status={}", id, status);
        return true;
    }

    @Override
    public List<CategoryPathDTO> getCategoryPath(String categoryId) {
        List<CategoryPathDTO> path = new ArrayList<>();
        String currentId = categoryId;

        while (currentId != null ) {
            Category category = categoryMapper.selectById(currentId);
            if (category == null || category.getDeleted() == 1) {
                break;
            }

            path.add(0, new CategoryPathDTO(category.getId(), category.getName()));
            currentId = category.getParentId();
        }

        return path;
    }

    @Override
    public boolean existsById(String id) {
        if (id == null ) {
            return false;
        }

        return categoryMapper.selectCount(
                Wrappers.<Category>lambdaQuery()
                        .eq(Category::getId, id)
                        .eq(Category::getDeleted, 0)
        ) > 0;
    }

    // ========== 私有辅助方法 ========== //

    /**
     * 获取存在的分类（带验证）
     */
    private Category getExistingCategory(String id) {
        if (id == null ) {
            throw new BusinessException("category.id.invalid");
        }

        Category category = categoryMapper.selectById(id);
        if (category == null || category.getDeleted() == 1) {
            throw new BusinessException("category.not.exist");
        }
        return category;
    }

    /**
     * 计算分类层级
     */
    private Integer calculateLevel(String parentId) {
        if (parentId == null ) {
            return 1; // 根分类为1级
        }

        Category parent = categoryMapper.selectById(parentId);
        if (parent == null || parent.getDeleted() == 1) {
            throw new BusinessException("category.parent.not.exist");
        }

        if (parent.getLevel() >= MAX_CATEGORY_LEVEL) {
            throw new BusinessException("category.level.max");
        }

        return parent.getLevel() + 1;
    }

    /**
     * 验证创建参数
     */
    private void validateForCreate(CategoryCreateDTO dto) {
        if (dto == null) {
            throw new BusinessException("category.create.dto.null");
        }
        // 名称验证
        if (!StringUtils.hasText(dto.getName())) {
            throw new BusinessException("category.name.required");
        }
        if (dto.getName().length() > 64) {
            throw new BusinessException("category.name.too.long");
        }
        // 父分类验证
        if (dto.getParentId() == null) {
            throw new BusinessException("category.parent.required");
        }
        // 唯一性验证
        checkNameUniqueness(dto);
    }

    /**
     * 验证更新参数
     */
    private void validateForUpdate(CategoryUpdateDTO dto, Category existing){
        if (dto == null) {
            throw new BusinessException("category.update.dto.null");
        }

        // 名称验证
        if (!StringUtils.hasText(dto.getName())) {
            throw new BusinessException("category.name.required");
        }

        // 父分类不能设置为自己
        if (dto.getId().equals(dto.getParentId())) {
            throw new BusinessException("category.parent.self");
        }

        // 如果名称或父分类有变化，检查唯一性
        if (!dto.getName().equals(existing.getName()) ||
                !Objects.equals(dto.getParentId(), existing.getParentId())) {
            // 使用自定义SQL查询
            Integer count;
            if (dto.getParentId() != null) {
                count = categoryMapper.countByNameAndParentIdExcludeSelf(
                        dto.getName(),
                        dto.getParentId(),
                        dto.getId()
                );
            } else {
                count = categoryMapper.countByNameAndNullParentExcludeSelf(
                        dto.getName(),
                        dto.getId()
                );
            }

            if (count > 0) {
                throw new BusinessException("category.name.exists");
            }
        }
    }

    /**
     * 检查名称唯一性
     */
    private void checkNameUniqueness(CategoryCreateDTO dto) {
        Integer count;
        if (dto.getParentId() != null) {
            count = categoryMapper.countByNameAndParentId(dto.getName(), dto.getParentId());
        } else {
            count = categoryMapper.countByNameAndNullParent(dto.getName());
        }

        if (count > 0) {
            throw new BusinessException("category.name.exists");
        }
    }

    /**
     * 构建分类树
     */
    private List<CategoryTreeDTO> buildCategoryTree(List<Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptyList();
        }

        // 按父ID分组
        Map<String, List<Category>> groupByParent = categories.stream()
                .collect(Collectors.groupingBy(Category::getParentId));

        // 从根节点开始构建（parentId=0）
        return buildTreeNodes(groupByParent.get(0L), groupByParent);
    }

    /**
     * 递归构建树节点
     */
    private List<CategoryTreeDTO> buildTreeNodes(List<Category> nodes,
                                                 Map<String, List<Category>> groupByParent) {
        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }

        return nodes.stream()
                .map(node -> {
                    CategoryTreeDTO dto = categoryConvert.entityToTreeDTO(node);
                    List<Category> children = groupByParent.get(node.getId());
                    if (!CollectionUtils.isEmpty(children)) {
                        dto.setChildren(buildTreeNodes(children, groupByParent));
                    }
                    return dto;
                })
                .sorted(Comparator.comparing(CategoryTreeDTO::getSort))
                .collect(Collectors.toList());
    }

    /**
     * 检查循环引用
     */
    private boolean isCircularReference(String currentId, String newParentId) {
        if (currentId.equals(newParentId)) {
            return true;
        }

        Set<String> visited = new HashSet<>();
        String checkId = newParentId;

        while (checkId != null ) {
            if (visited.contains(checkId)) {
                break; // 防止无限循环
            }
            if (checkId.equals(currentId)) {
                return true; // 发现循环引用
            }

            visited.add(checkId);
            Category category = categoryMapper.selectById(checkId);
            if (category == null) {
                break;
            }
            checkId = category.getParentId();
        }

        return false;
    }

    /**
     * 丰富分类DTO信息
     */
    private CategoryDTO enrichCategoryDTO(CategoryDTO dto) {
        // 设置父分类名称
        if (dto.getParentId() != null) {
            Category parent = categoryMapper.selectById(dto.getParentId());
            if (parent != null) {
                dto.setParentName(parent.getName());
            }
        }

        // 设置子分类数量
        Integer childrenCount = categoryMapper.selectCount(
                Wrappers.<Category>lambdaQuery()
                        .eq(Category::getParentId, dto.getId())
                        .eq(Category::getDeleted, 0)
        );
        dto.setChildrenCount(childrenCount.intValue());

        return dto;
    }
}