package com.cn.taihe.back.suppliers.dto.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "批量操作请求DTO")
public class BatchOperateDTO {
    public enum OperationType {
        DELETE, FREEZE, UNFREEZE
    }

    @NotNull(message = "操作类型不能为空")
    @ApiModelProperty(value = "操作类型(DELETE-删除,FREEZE-冻结,UNFREEZE-解冻)", required = true, example = "DELETE")
    private OperationType operation;

    @NotEmpty(message = "ID列表不能为空")
    @ApiModelProperty(value = "供应商ID列表", required = true)
    private List<String> ids;

    @ApiModelProperty(value = "操作原因")
    private String reason;
}
