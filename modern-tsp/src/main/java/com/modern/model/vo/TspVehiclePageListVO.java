package com.modern.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
 * @name：TspVehiclePageListVO
 * @Date：2024/10/15 16:09
 * @Filename：TspVehiclePageListVO
 */
@ApiModel("车辆信息-请求对象-分页列表")
@Data
public class TspVehiclePageListVO extends BaseVO {
    @ApiModelProperty("关联账号")
    private String mobile;

    @ApiModelProperty("车型id")
    private Long tspVehicleStdModelId;

    @ApiModelProperty("vin")
    private String vin;

    @ApiModelProperty("车牌号")
    private String plateCode;

    @ApiModelProperty("设备ids")
    private List<Long> tspEquipmentIds;

    @ApiModelProperty("设备绑定状态")
    private String bindStatus;

    @ApiModelProperty("车辆状态")
    //@JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    //private TspVehicleStateEnum state;
    private Integer state;

    @ApiModelProperty("关联账号")
    private Integer needAll;

    @ApiModelProperty("推送状态")
    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    private Integer sendStatus;

    @ApiModelProperty("认证状态")
    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    private Integer certificationState;

    private List<Long> carIds;
}
