package com.cn.taihe.back.category.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.category.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

// CategoryMapper.java
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    // 根据父ID查询子分类
    List<Category> selectByParentId(@Param("parentId") String parentId);

    // 查询显示状态的分类树
    List<Category> selectDisplayTree();

    // 查询分类路径（从根到当前分类）
    List<Category> selectCategoryPath(@Param("id") String id);

    // 更新显示状态
    int updateDisplayStatus(@Param("id") String id, @Param("isDisplay") Integer isDisplay);

    // 逻辑删除
    int logicDelete(@Param("id") String id);
    // 图片中用到的方法
    int insert(Category category);

    @Update("UPDATE product_category SET " +
            "parent_id = #{parentId}, " +
            "name = #{name}, " +
            "level = #{level}, " +
            "sort = #{sort}, " +
            "icon = #{icon}, " +
            "image = #{image}, " +
            "description = #{description}, " +
            "keywords = #{keywords}, " +
            "is_display = #{displayStatus}, " +
            "is_deleted = #{deleted}, " +
            "update_time = #{updateTime} " +
            "WHERE id = #{id}")
    int updateById(Category category);

    Integer selectCount(Wrapper<Category> wrapper);

    @Select("SELECT COUNT(*) FROM product_category WHERE name = #{name} AND parent_id = #{parentId} AND is_deleted = 0")
    int countByNameAndParentId(@Param("name") String name, @Param("parentId") String parentId);

    @Select("SELECT COUNT(*) FROM product_category WHERE name = #{name} AND parent_id IS NULL AND is_deleted = 0")
    int countByNameAndNullParent(@Param("name") String name);

    @Select("SELECT COUNT(*) FROM product_category WHERE name = #{name} AND parent_id = #{parentId} AND id != #{excludeId} AND is_deleted = 0")
    int countByNameAndParentIdExcludeSelf(
            @Param("name") String name,
            @Param("parentId") String parentId,
            @Param("excludeId") String excludeId
    );

    @Select("SELECT COUNT(*) FROM product_category WHERE name = #{name} AND parent_id IS NULL AND id != #{excludeId} AND is_deleted = 0")
    int countByNameAndNullParentExcludeSelf(
            @Param("name") String name,
            @Param("excludeId") String excludeId
    );
    // 使用@Select注解替代XML（可选）
    @Select("SELECT * FROM product_category WHERE parent_id = #{parentId} AND is_deleted = 0")
    List<Category> selectChildrenByParentId(String parentId);

    @Update("UPDATE product_category SET is_deleted = 1 WHERE id = #{id} AND is_deleted = 0")
    int logicalDeleteById(String id);

}
