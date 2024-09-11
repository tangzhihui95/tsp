package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspEquipmentTypeAddVO
 * @Date：2024/8/30 14:37
 * @Filename：TspEquipmentTypeAddVO
 */

@ApiModel("设备分类-请求对象-添加")
@Data
public class TspEquipmentTypeAddVO {
    @ApiModelProperty("设备分类ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long equipmentTypeId;

    @NotNull(message = "设备类型不能为空")
    @ApiModelProperty("设备类型")
    private String name;

    @ApiModelProperty("供应商")
    private String suppliers;

    @NotNull(message = "设备扩展信息不能为空")
    @ApiModelProperty("设备扩展信息")
    private String extraType;

    @ApiModelProperty("是否为终端设备")
    private Integer isTerminal;
}
