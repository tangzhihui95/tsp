package com.modern.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleStdModePageListDTO
 * @Date：2024/10/14 15:10
 * @Filename：TspVehicleStdModePageListDTO
 */
@ApiModel(value = "车型- 数据传输对象- 二级车型车型管理列表")
@Data
public class TspVehicleStdModePageListDTO extends BaseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("车型id")
    private Long tspVehicleModelId;

    @ApiModelProperty("车型")
    private String vehicleModelName;

    @ApiModelProperty("型号名称")
    private String stdModeName;

    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    @ApiModelProperty("能源类型")
    private Integer dataKey;

    @ApiModelProperty("公告批次")
    private String noticeBatch;

    @ApiModelProperty("公告型号")
    private String noticeModel;

    @ApiModelProperty("二级车型关联车辆")
    private Integer stdModeCount;
}
