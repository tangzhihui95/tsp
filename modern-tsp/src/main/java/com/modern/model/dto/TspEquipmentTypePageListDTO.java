package com.modern.model.dto;

import com.modern.common.core.domain.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspEquipmentTypePageListDTO
 * @Date：2024/8/28 17:33
 * @Filename：TspEquipmentTypePageListDTO
 */
@ApiModel("设备分类返回对象")
@Data
public class TspEquipmentTypePageListDTO extends BaseModel {

    @ApiModelProperty("设备类型")
    private String name;
    @ApiModelProperty("供应商")
    private String suppliers;
    @ApiModelProperty("是否为终端")
    private Integer isTerminal;
    @ApiModelProperty("设备扩展类型")
    private String extraType;
    @ApiModelProperty("关联设备")
    private Integer count;
    @ApiModelProperty("型号")
    private List<TspEquipmentModelPageListDTO> children;
}
