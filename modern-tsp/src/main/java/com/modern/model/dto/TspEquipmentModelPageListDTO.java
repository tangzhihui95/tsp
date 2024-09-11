package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspEquipmentModelPageListDTO
 * @Date：2024/8/29 10:11
 * @Filename：TspEquipmentModelPageListDTO
 */
@Data
@ApiModel("设备信息-数据传输-导入导出")
public class TspEquipmentModelPageListDTO extends BaseModel {

    @ApiModelProperty("设备类型名称")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备分类ID")
    private Long tspEquipmentTypeId;

    @ApiModelProperty("是否为终端0:否 1:是")
    private Integer isTerminal;

    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    @ApiModelProperty("设备型号")
    private String modelName;

    @ApiModelProperty("供应商")
    private String suppliers;

    @ApiModelProperty("生产批次号")
    private String batchNumber;

    @ApiModelProperty("关联设备数量")
    private Integer count;
}
