package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.dto.BaseDto;
import com.modern.domain.TspVehicleStdModelExtra;
import com.modern.enums.TpsVehicleDataKeyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleStdModelInfoDTO
 * @Date：2024/10/25 15:00
 * @Filename：TspVehicleStdModelInfoDTO
 */
@ApiModel("车辆信息二级型号- 请求对象- 二级车型型号详情")
@Data
public class TspVehicleStdModelInfoDTO extends BaseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型ID")
    private Long tspVehicleModelId;

    @ApiModelProperty("型号名称")
    private String stdModeName;

    @ApiModelProperty("能源类型")
    private TpsVehicleDataKeyEnum dataKey;

    @ApiModelProperty("公告批次")
    private String noticeBatch;

    @ApiModelProperty("公告型号")
    private String noticeModel;

    @ApiModelProperty("车辆厂商")
    private String firm;

    @ApiModelProperty("扩展信息")
    private TspVehicleStdModelExtra stdModelExtra;
}
