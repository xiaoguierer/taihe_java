package com.cn.taihe.back.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SKU与供应商关联关系表 基础DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "product_sku_supplier")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@TableName("product_sku_supplier")
@ApiModel(value = "ProductSkuSupplier", description = "SKU与供应商关联关系表")
public class ProductSkuSupplier implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键ID", example = "12345678-1234-1234-1234-123456789abc")
  private String id;

  @ApiModelProperty(value = "SKU ID", required = true)
  private String skuId;

  @ApiModelProperty(value = "供应商ID", required = true)
  private String supplierId;

  @ApiModelProperty(value = "排序值")
  private Integer sortOrder;

  @CreatedDate
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createdTime;
}
