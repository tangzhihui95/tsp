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
 * @name：TspVehicleModelSelectDTO
 * @Date：2024/10/15 16:04
 * @Filename：TspVehicleModelSelectDTO
 */
@ApiModel("二级车型-数据传输对象-导入导出")
@Data
public class TspVehicleModelSelectDTO extends BaseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("ID")
    private Long value;

    @ApiModelProperty("类型")
    private String label;

    @ApiModelProperty("二级车型")
    private List<TspVehicleModelSelectDTO> children;
}
