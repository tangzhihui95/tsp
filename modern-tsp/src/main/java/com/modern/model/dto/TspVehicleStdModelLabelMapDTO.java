package com.modern.model.dto;

import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleStdModelLabelMapDTO
 * @Date：2024/10/25 15:15
 * @Filename：TspVehicleStdModelLabelMapDTO
 */
@ApiModel("二级车型- 数据传输对象- 二级车型型号标签")
@Data
public class TspVehicleStdModelLabelMapDTO extends BaseDto {
    private String date;

    private String count;

    private String modeNames;
}
