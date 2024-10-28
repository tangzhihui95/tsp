package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspVehicleModelAddVO
 * @Date：2024/10/14 17:37
 * @Filename：TspVehicleModelAddVO
 */
@ApiModel("车辆车型-请求对象-添加 ")
@Data
public class TspVehicleModelAddVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleModelId;

    @NotEmpty(message = "车型名称不能为空")
    @ApiModelProperty("车型名称")
    private String vehicleModelName;
}
