package com.cn.taihe.back.category.dto;
import com.cn.taihe.back.category.entity.Category;
import com.cn.taihe.back.category.mapper.DateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DateMapper.class})
public interface CategoryConvert {
    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    @Mapping(target = "parentName", ignore = true)
    @Mapping(target = "childrenCount", ignore = true)
    CategoryDTO entityToDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category createDTOToEntity(CategoryCreateDTO dto);

    @Mapping(target = "level", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category updateDTOToEntity(CategoryUpdateDTO dto);

    @Mapping(target = "hasChildren",
            expression = "java(category.getChildren() != null && !category.getChildren().isEmpty())")
    @Mapping(target = "categoryPath", ignore = true)
    CategoryTreeDTO entityToTreeDTO(Category category);

    // 新增集合转换方法
    List<CategoryDTO> entitiesToDTOs(List<Category> categories);
    List<CategoryTreeDTO> entitiesToTreeDTOs(List<Category> categories);
}