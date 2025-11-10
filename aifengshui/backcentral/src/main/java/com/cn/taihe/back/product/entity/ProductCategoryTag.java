package com.cn.taihe.back.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品品类标签表 实体类
 *
 * @author auto generate
 * @since 2025-11-07
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "product_categorytag")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ProductCategoryTag", description = "商品品类标签表")
public class ProductCategoryTag implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  @GeneratedValue(generator = "system-uuid")
  @Column(name = "id", length = 50, nullable = false)
  @ApiModelProperty(value = "主键ID", example = "1234567890abcdef")
  private String id;

  @Column(name = "tag_key", length = 50, nullable = false)
  @ApiModelProperty(value = "标签键名", required = true, example = "necklace")
  private String tagKey;

  @Column(name = "tag_name_en", length = 100, nullable = false)
  @ApiModelProperty(value = "英文标签名", required = true, example = "Necklace")
  private String tagNameEn;

  @Column(name = "tag_name_zh", length = 100, nullable = false)
  @ApiModelProperty(value = "中文标签名", required = true, example = "项链")
  private String tagNameZh;

  @Column(name = "tag_name_ar", length = 100)
  @ApiModelProperty(value = "阿拉伯语标签名", example = "قلادة")
  private String tagNameAr;

  @Column(name = "description_en", columnDefinition = "text")
  @ApiModelProperty(value = "英文详细描述")
  private String descriptionEn;

  @Column(name = "description_zh", columnDefinition = "text")
  @ApiModelProperty(value = "中文详细描述")
  private String descriptionZh;

  @Column(name = "description_ar", columnDefinition = "text")
  @ApiModelProperty(value = "阿拉伯语详细描述")
  private String descriptionAr;

  @Column(name = "short_desc_en", length = 200)
  @ApiModelProperty(value = "英文简短描述")
  private String shortDescEn;

  @Column(name = "short_desc_zh", length = 200)
  @ApiModelProperty(value = "中文简短描述")
  private String shortDescZh;

  @Column(name = "short_desc_ar", length = 200)
  @ApiModelProperty(value = "阿拉伯语简短描述")
  private String shortDescAr;

  @Column(name = "icon_id", length = 50)
  @ApiModelProperty(value = "图标访问id")
  private String iconId;

  @Column(name = "icon_path", length = 255)
  @ApiModelProperty(value = "图标url")
  private String iconPath;

  @Column(name = "cover_image_url", length = 500)
  @ApiModelProperty(value = "封面图访问URL")
  private String coverImageUrl;

  @Column(name = "cover_id", length = 50)
  @ApiModelProperty(value = "封面图ID")
  private String coverId;

  @Column(name = "hover_image_url", length = 500)
  @ApiModelProperty(value = "悬停图访问URL")
  private String hoverImageUrl;

  @Column(name = "hover_id", length = 50)
  @ApiModelProperty(value = "悬停图id")
  private String hoverId;

  @Column(name = "tag_type", nullable = false)
  @ApiModelProperty(value = "标签类型:1首饰类型,2材质,3宝石类型,4风格,5场合,6工艺,7人群,8五行属性", required = true, example = "1")
  private Integer tagType;

  @Column(name = "parent_tag_id", length = 50)
  @ApiModelProperty(value = "父标签ID")
  private String parentTagId;

  @Column(name = "color_code", length = 20)
  @ApiModelProperty(value = "标签代表色(用于UI)", example = "#FF5733")
  private String colorCode;

  @Column(name = "sort_order", nullable = false)
  @ApiModelProperty(value = "排序值", example = "0")
  private Integer sortOrder = 0;

  @Column(name = "is_active", nullable = false)
  @ApiModelProperty(value = "是否启用", example = "true")
  private Boolean isActive = true;

  @Column(name = "show_in_filter", nullable = false)
  @ApiModelProperty(value = "是否在筛选器显示", example = "true")
  private Boolean showInFilter = true;

  @Column(name = "show_in_navigation", nullable = false)
  @ApiModelProperty(value = "是否在导航显示", example = "false")
  private Boolean showInNavigation = false;

  @Column(name = "meta_title_en", length = 200)
  @ApiModelProperty(value = "英文SEO标题")
  private String metaTitleEn;

  @Column(name = "meta_title_zh", length = 200)
  @ApiModelProperty(value = "中文SEO标题")
  private String metaTitleZh;

  @Column(name = "meta_title_ar", length = 200)
  @ApiModelProperty(value = "阿拉伯语SEO标题")
  private String metaTitleAr;

  @Column(name = "meta_description_en", columnDefinition = "text")
  @ApiModelProperty(value = "英文SEO描述")
  private String metaDescriptionEn;

  @Column(name = "meta_description_zh", columnDefinition = "text")
  @ApiModelProperty(value = "中文SEO描述")
  private String metaDescriptionZh;

  @Column(name = "meta_description_ar", columnDefinition = "text")
  @ApiModelProperty(value = "阿拉伯语SEO描述")
  private String metaDescriptionAr;

  @Column(name = "meta_keywords_en", length = 500)
  @ApiModelProperty(value = "英文SEO关键词")
  private String metaKeywordsEn;

  @Column(name = "meta_keywords_zh", length = 500)
  @ApiModelProperty(value = "中文SEO关键词")
  private String metaKeywordsZh;

  @Column(name = "meta_keywords_ar", length = 500)
  @ApiModelProperty(value = "阿拉伯语SEO关键词")
  private String metaKeywordsAr;

  @CreatedBy
  @Column(name = "created_by", nullable = false, updatable = false)
  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建人ID", hidden = true)
  private Long createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", nullable = false)
  @TableField(fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新人ID", hidden = true)
  private Long updatedBy;

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间", hidden = true)
  private LocalDateTime createdTime;

  @LastModifiedDate
  @Column(name = "updated_time", nullable = false)
  @TableField(fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新时间", hidden = true)
  private LocalDateTime updatedTime;
}
