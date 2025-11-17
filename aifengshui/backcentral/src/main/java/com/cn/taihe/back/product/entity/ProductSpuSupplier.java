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
 * SPU与供应商关联关系表 基础DTO
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "product_spu_supplier")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@TableName("product_spu_supplier")
@ApiModel(value = "ProductSpuSupplier", description = "SPU与供应商关联关系表")
public class ProductSpuSupplier implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键ID", example = "12345678-1234-1234-1234-123456789abc")
  private String id;

  @ApiModelProperty(value = "SPU ID", required = true)
  private String spuId;

  @ApiModelProperty(value = "供应商ID", required = true)
  private String supplierId;

  @CreatedDate
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createdTime;
}
