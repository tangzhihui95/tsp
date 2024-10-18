package com.modern.model.dto;

import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleModelPageListDTO
 * @Date：2024/10/14 15:08
 * @Filename：TspVehicleModelPageListDTO
 */

@ApiModel("车辆车型- 数据传输对象- 分页列表")
@Data
public class TspVehicleModelPageListDTO extends BaseDto {
    @ApiModelProperty("车型")
    private String vehicleModelName;

    @ApiModelProperty("关联车辆")
    private Integer vehicleCount;

    @ApiModelProperty("二级车辆")
    private List<TspVehicleStdModePageListDTO> children;
}
