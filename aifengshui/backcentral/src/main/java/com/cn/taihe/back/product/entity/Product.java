package com.cn.taihe.back.product.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品信息实体类
 *
 * @author auto generator
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product")
@Entity
@ApiModel(value = "Product对象", description = "商品信息表")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID", required = true)
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @NotBlank(message = "商品ID不能为空")
    @Size(max = 255, message = "商品ID长度不能超过255个字符")
    @Column(name = "id", nullable = false, length = 255)
    private String id;

    @ApiModelProperty(value = "分类ID", required = true)
    @NotBlank(message = "分类ID不能为空")
    @Size(max = 255, message = "分类ID长度不能超过255个字符")
    @Column(name = "category_id", nullable = false, length = 255)
    private String categoryId;

    @ApiModelProperty(value = "品牌ID", required = true)
    @NotBlank(message = "品牌ID不能为空")
    @Size(max = 255, message = "品牌ID长度不能超过255个字符")
    @Column(name = "brand_id", nullable = false, length = 255)
    private String brandId;

    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 128, message = "商品名称长度不能超过128个字符")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @ApiModelProperty(value = "副标题")
    @Size(max = 255, message = "副标题长度不能超过255个字符")
    @Column(name = "sub_title", length = 255)
    private String subTitle;

    @ApiModelProperty(value = "主图URL")
    @Size(max = 255, message = "主图URL长度不能超过255个字符")
    @Column(name = "main_image", length = 255)
    private String mainImage;

    @ApiModelProperty(value = "商品详情")
    @Lob
    @Column(name = "detail", columnDefinition = "text")
    private String detail;

    @ApiModelProperty(value = "商品规格参数(JSON格式)")
    @Lob
    @Column(name = "specs", columnDefinition = "longtext")
    private String specs;

    @ApiModelProperty(value = "价格区间(如100-200)")
    @Size(max = 64, message = "价格区间长度不能超过64个字符")
    @Column(name = "price_range", length = 64)
    private String priceRange;

    @ApiModelProperty(value = "销量")
    @NotNull(message = "销量不能为空")
    @Column(name = "sales", nullable = false)
    private Integer sales = 0;

    @ApiModelProperty(value = "状态(0-下架，1-上架)", required = true)
    @NotNull(message = "状态不能为空")
    @Column(name = "status", nullable = false)
    private Integer status;

    @ApiModelProperty(value = "是否删除(0-未删除，1-已删除)")
    @TableLogic
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "默认供应商ID")
    @Size(max = 255, message = "默认供应商ID长度不能超过255个字符")
    @Column(name = "default_supplier_id", length = 255)
    private String defaultSupplierId;

    /**
     * 获取规格参数的JSON对象
     */
    @Transient
    public JSONObject getSpecsJson() {
        if (this.specs == null || this.specs.trim().isEmpty()) {
            return new JSONObject();
        }
        return JSON.parseObject(this.specs);
    }

    /**
     * 设置规格参数的JSON对象
     */
    public void setSpecsJson(JSONObject specsJson) {
        if (specsJson == null) {
            this.specs = null;
        } else {
            this.specs = specsJson.toJSONString();
        }
    }

    /**
     * 判断商品是否上架
     */
    @Transient
    public boolean isOnSale() {
        return this.status != null && this.status == 1;
    }

    /**
     * 判断商品是否已删除
     */
    @Transient
    public boolean isDeleted() {
        return this.isDeleted != null && this.isDeleted == 1;
    }
}