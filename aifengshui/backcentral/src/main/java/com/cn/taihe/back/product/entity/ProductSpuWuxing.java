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
import java.util.Date;

/**
 * SPU-五行关系表 实体类
 *
 * @author system
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "product_spu_wuxing")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@TableName("product_spu_wuxing")
@ApiModel(value = "ProductSpuWuxing", description = "SPU-五行关系表")
public class ProductSpuWuxing implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键ID", example = "12345678-1234-1234-1234-123456789abc")
  private String id;

  @ApiModelProperty(value = "SPU ID", required = true)
  private String spuId;

  @ApiModelProperty(value = "五行属性ID", required = true)
  private String wuXingId;

  @ApiModelProperty(value = "元素强度1-100", required = true)
  private String elementStrength;

  @ApiModelProperty(value = "是否主要元素", required = true)
  private Integer isPrimary;

  @ApiModelProperty(value = "排序值", required = true)
  private Integer sortOrder;

  @CreatedDate
  @ApiModelProperty(value = "创建时间")
  private Date createdTime;
}
