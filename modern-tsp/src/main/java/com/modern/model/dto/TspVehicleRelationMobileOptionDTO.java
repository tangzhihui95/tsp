package com.modern.model.dto;

import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleRelationMobileOptionDTO
 * @Date：2024/11/6 14:35
 * @Filename：TspVehicleRelationMobileOptionDTO
 */
@ApiModel("车辆信息- 数据传输对象- 关联账号")
@Data
public class TspVehicleRelationMobileOptionDTO extends BaseDto {
    @ApiModelProperty("手机号(账号)")
    private String mobile;
}
