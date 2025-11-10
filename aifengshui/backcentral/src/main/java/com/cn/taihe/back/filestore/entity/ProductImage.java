package com.cn.taihe.back.filestore.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * 商品图片表(简化版) 实体类
 *
 * @author system
 * @date 2025-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "product_image")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@TableName("product_image")
@ApiModel(value = "ProductImage对象", description = "商品图片表(简化版)")
public class ProductImage implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键ID", required = true)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @TableId(value = "id", type = IdType.ASSIGN_UUID)
  @Column(name = "id", nullable = false, length = 50)
  private String id;

  @ApiModelProperty(value = "绝对URL(CDN地址)", required = true)
  @Column(name = "absolute_url", nullable = false, length = 500)
  private String absoluteUrl;

  @ApiModelProperty(value = "相对路径", required = true)
  @Column(name = "relative_path", nullable = false, length = 500)
  private String relativePath;

  @ApiModelProperty(value = "文件名", required = true)
  @Column(name = "file_name", nullable = false, length = 255)
  private String fileName;

  @ApiModelProperty(value = "文件大小(字节)", required = true)
  @Column(name = "file_size", nullable = false)
  private Integer fileSize;

  @ApiModelProperty(value = "文件类型", required = true)
  @Column(name = "mime_type", nullable = false, length = 100)
  private String mimeType;

  @ApiModelProperty(value = "英文替代文本")
  @Column(name = "image_alt_en", length = 200)
  private String imageAltEn;

  @ApiModelProperty(value = "中文替代文本")
  @Column(name = "image_alt_zh", length = 200)
  private String imageAltZh;

  @ApiModelProperty(value = "阿拉伯语替代文本")
  @Column(name = "image_alt_ar", length = 200)
  private String imageAltAr;

  @ApiModelProperty(value = "英文标题")
  @Column(name = "image_title_en", length = 200)
  private String imageTitleEn;

  @ApiModelProperty(value = "中文标题")
  @Column(name = "image_title_zh", length = 200)
  private String imageTitleZh;

  @ApiModelProperty(value = "阿拉伯语标题")
  @Column(name = "image_title_ar", length = 200)
  private String imageTitleAr;

  @ApiModelProperty(value = "排序值")
  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder = 0;

  @ApiModelProperty(value = "是否主图")
  @Column(name = "is_primary", nullable = false)
  private Boolean isPrimary = false;

  @ApiModelProperty(value = "是否启用")
  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @ApiModelProperty(value = "创建人ID", required = true)
  @CreatedBy
  @Column(name = "created_by", nullable = false)
  private Long createdBy;

  @ApiModelProperty(value = "更新人ID", required = true)
  @LastModifiedBy
  @Column(name = "updated_by", nullable = false)
  private Long updatedBy;

  @ApiModelProperty(value = "创建时间", required = true)
  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  private LocalDateTime createdTime;

  @ApiModelProperty(value = "更新时间", required = true)
  @LastModifiedDate
  @Column(name = "updated_time", nullable = false)
  private LocalDateTime updatedTime;

}
