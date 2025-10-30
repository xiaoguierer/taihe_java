package com.cn.taihe.back.category.mapper;
import com.cn.taihe.back.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface DateMapper {

    // 日期→字符串
    default String formatDateTime(Date date) {
        return date != null ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) : null;
    }

    default String formatDate(Date date) {
        return date != null ?
                new SimpleDateFormat("yyyy-MM-dd").format(date) : null;
    }

    // 字符串→日期
    default Date parseDateTime(String dateStr) {
        try {
            return StringUtils.hasText(dateStr) ?
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr) : null;
        } catch (Exception e) {
            throw new RuntimeException("日期格式转换失败", e);
        }
    }
/*
    // 特定字段格式转换（不再与CategoryDTO绑定）
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Category formatDatesInEntity(Category entity);*/
}
