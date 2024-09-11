package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspEquipmentTypeSelectDTO
 * @Date：2024/9/9 16:16
 * @Filename：TspEquipmentTypeSelectDTO
 */
@ApiModel("设备分类- 数据传输对象 - 下拉列表")
@Data
public class TspEquipmentTypeSelectDTO extends BaseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("ID")
    private Long value;

    @ApiModelProperty("")
    private String label;

    @ApiModelProperty("")
    private List<TspEquipmentTypeSelectDTO> children;

}
