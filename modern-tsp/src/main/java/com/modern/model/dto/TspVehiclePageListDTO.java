package com.modern.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehiclePageListDTO
 * @Date：2024/10/16 14:49
 * @Filename：TspVehiclePageListDTO
 */
@ApiModel("车辆信息- 数据传输对象 - 分页列表")
@Data
public class TspVehiclePageListDTO extends BaseDto {
    @Excel(name = "车牌号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("车牌号")
    private String plateCode;

    @ApiModelProperty("二级车型ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleStdModelId;

    @ApiModelProperty("一级车型ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspVehicleModelId;

    @Excel(name = "一级车型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("一级车型")
    private String vehicleModelName;

    @Excel(name = "二级车型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("二级车型")
    private String stdModeName;

    @Excel(name = "VIN", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("VIN")
    private String vin;

    @Excel(name = "SIM", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("SIM")
    private String sim;

    @Excel(name = "关联账号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("关联账号")
    private String mobile;

    @Excel(name = "真实姓名", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("真实姓名")
    private String realName;

    @Excel(name = "车辆状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("车辆状态")
    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    private Integer state;

    @ApiModelProperty("绑定状态")
    private String bindStatus;

    @Excel(name = "认证状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("认证状态")
    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    private Integer certificationState;

    @ApiModelProperty("是否在线 1在线 0未在线")
    private Boolean isOnline;

    @Excel(name = "推送状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("推送状态 1已推送 0未推送")
    @JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    private Integer sendStatus;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("推送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty("推送方式")
    private String mode;

    @ApiModelProperty("设备ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tspEquipmentId;

    @ApiModelProperty("车型")
    private String vehicleType;
}
