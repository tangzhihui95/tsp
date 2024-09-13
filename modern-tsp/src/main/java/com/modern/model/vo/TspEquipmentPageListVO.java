package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.core.domain.vo.BaseVO;
import com.modern.enums.TspVehicleStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspEquipmentPageListVO
 * @Date：2024/9/12 14:24
 * @Filename：TspEquipmentPageListVO
 */
@ApiModel("设备信息-请求对象-分页列表 ")
@Data
public class TspEquipmentPageListVO extends BaseVO {

    @ApiModelProperty("型号ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentModelId;

    @ApiModelProperty("SN")
    private String sn;

    @ApiModelProperty("SIM")
    private String sim;

    @ApiModelProperty("报废状态")
    private String showScrap;

    @ApiModelProperty("设备绑定状态")
    private String bindStatus;

    @ApiModelProperty("车辆状态")
    //@JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    private TspVehicleStateEnum state;

    @ApiModelProperty("设备IDs")
    private List<Long> tspEquipmentIds;
}
