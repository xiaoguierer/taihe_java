package com.cn.taihe.back.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.back.product.dto
 * @Author: 大咖
 * @CreateTime: 2025-11-14  15:22
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@ApiModel("创建SPU-情感意图关系请求")
public class CreateRelationsRequest {
  @NotBlank(message = "SPU ID不能为空")
  @ApiModelProperty(value = "SPU ID", required = true, example = "123456")
  private String spuId;

  @NotEmpty(message = "情感意图ID列表不能为空")
  @ApiModelProperty(value = "情感意图ID列表", required = true)
  private List<String> intentIds;
}
