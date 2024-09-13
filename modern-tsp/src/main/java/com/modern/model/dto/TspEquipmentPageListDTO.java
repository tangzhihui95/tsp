package com.modern.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.modern.common.annotation.Excel;
import com.modern.common.core.domain.dto.BaseDto;
import com.modern.enums.TspVehicleStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto.dto
 * @Project：tsp
 * @name：TspEquipmentPageListDTO
 * @Date：2024/8/28 15:42
 * @Filename：TspEquipmentPageListDTO
 */
@Data
public class TspEquipmentPageListDTO extends BaseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("分类ID")
    private Long tspEquipmentTypeId;

    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("型号ID")
    private Long tspEquipmentModelId;

    @ApiModelProperty("设备分类")
    private String name;

    @ApiModelProperty("设备绑定状态")
    private String bindStatus;

    @Excel(name = "车辆状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("车辆状态")
    //@JSONField(serialzeFeatures = {SerializerFeature.WriteEnumUsingToString})
    private TspVehicleStateEnum state;

    @ApiModelProperty("设备型号")
    private String modelName;

    @ApiModelProperty("设备型号")
    private String typeModel;

    @ApiModelProperty("供应商")
    private String suppliers;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("设备SN")
    private String sn;

    @ApiModelProperty("iccId")
    private String iccId;

    @ApiModelProperty("IMSI")
    private String imsi;

    @ApiModelProperty("sim")
    private String sim;

    @ApiModelProperty("imei")
    private String imei;

    @ApiModelProperty("是否为终端 1：是 0：否")
    private Boolean isTerminal;

    @ApiModelProperty("供应商代码")
    private String supplierCode;

    @ApiModelProperty("运营商")
    private Integer operator;

    @ApiModelProperty("批次流水号")
    private String serialNumber;

    @ApiModelProperty("是否在线 1:在线 0:未在线")
    private Boolean isOnline;

    @ApiModelProperty("是否注册 1:是 0:否")
    private Boolean isRegister;

    @ApiModelProperty("是否报废 1:是 0:否")
    private Boolean isScrap;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("报废时间")
    private LocalDateTime scrapTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("解绑时间")
    private LocalDateTime unBindTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;
}
