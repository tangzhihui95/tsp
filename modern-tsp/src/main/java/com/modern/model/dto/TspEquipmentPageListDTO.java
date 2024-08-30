package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto.dto
 * @Project：tsp
 * @name：TspEquipmentPageListDTO
 * @Date：2024/8/28 15:42
 * @Filename：TspEquipmentPageListDTO
 */
public class TspEquipmentPageListDTO extends BaseDto {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备型号ID")
    private Long tspEquipmentModelId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备类型ID")
    private Long tspEquipmentTypeId;

}
