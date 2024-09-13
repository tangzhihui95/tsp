package com.modern.model.dto;

import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspEquipmentLikeSelectDTO
 * @Date：2024/9/13 13:32
 * @Filename：TspEquipmentLikeSelectDTO
 */
@ApiModel("设备信息- 数据传输对象 - 模糊搜索下拉列表")
@Data
public class TspEquipmentLikeSelectDTO extends BaseDto {
    @ApiModelProperty("设备类型")
    private String modelName;

}
