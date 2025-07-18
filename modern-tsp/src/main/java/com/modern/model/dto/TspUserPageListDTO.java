package com.modern.model.dto;

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
 * @name：TspUserPageListDTO
 * @Date：2025/7/2 13:59
 * @Filename：TspUserPageListDTO
 */
@ApiModel("tsp用户- 数据传输对象- 分页列表")
@Data
public class TspUserPageListDTO extends BaseDto {
    @Excel(name = "账号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("手机号")
    private String mobile;

    @Excel(name = "真实姓名", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("真实姓名")
    private String realName;

    @Excel(name = "身份证号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("身份证号")
    private String idCard;

    @Excel(name = "状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "1=已认证,0=已注册")
    @ApiModelProperty("实名认证状态:0未认证 1:已认证 (默认为0)")
    private Integer realNameAudit;

    @ApiModelProperty("是否启用")
    private Boolean isEnable;

    @Excel(name = "车辆数量", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("车辆数量")
    private Integer vehicleCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Excel(name = "注册时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM - dd HH:mm:ss")
    @ApiModelProperty("注册时间")
    private LocalDateTime regTime;
}
