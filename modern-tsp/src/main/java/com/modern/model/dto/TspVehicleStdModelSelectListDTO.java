package com.modern.model.dto;

import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleStdModelSelectListDTO
 * @Date：2024/10/25 15:10
 * @Filename：TspVehicleStdModelSelectListDTO
 */
@ApiModel("二级车型类型- 数据传输对象- 二级车型下拉列表")
@Data
public class TspVehicleStdModelSelectListDTO extends BaseDto {
    @ApiModelProperty("型号名称")
    private String stdModeName;
}
