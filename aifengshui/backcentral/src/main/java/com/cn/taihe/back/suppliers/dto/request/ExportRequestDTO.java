package com.cn.taihe.back.suppliers.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "数据导出请求DTO")
public class ExportRequestDTO {
    public enum ExportType {
        EXCEL, PDF, CSV
    }

    @NotEmpty(message = "供应商ID列表不能为空")
    @ApiModelProperty(value = "供应商ID列表", required = true)
    private List<String> supplierIds;

    @ApiModelProperty(value = "导出类型(EXCEL,PDF,CSV)", example = "EXCEL")
    private ExportType exportType = ExportType.EXCEL;

    @ApiModelProperty(value = "包含的字段列表")
    private List<String> includedFields;

    @ApiModelProperty(value = "是否包含联系人", example = "false")
    private Boolean includeContacts = false;

    @ApiModelProperty(value = "是否包含资质文件", example = "false")
    private Boolean includeCertificates = false;
}
