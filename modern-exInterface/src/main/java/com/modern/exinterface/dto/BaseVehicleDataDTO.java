package com.modern.exinterface.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.collect.ImmutableMap;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.LocalDateTime;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：com.modern.dto
 * @Project：tsp
 * @name：BaseVehicleDataDTO
 * @Date：2025/6/12 17:29
 * @Filename：BaseVehicleDataDTO
 */
@Data
public class BaseVehicleDataDTO {
    public static final int EXCEL_EXPORT_SORT_START = 3;

    public static final String EXCEPTION_STRING = "异常";


    public static final String INVALID_STRING = "无效";


    public static final String HAVE_STRING = "有";


    public static final String NOT_HAVE_STRING = "无";

    public static final Map<Integer, String> BYTE_EXCEPTION_INVALID_MAP = (Map<Integer, String>) ImmutableMap.of(Integer.valueOf(254), ", Integer.valueOf(255), ");

    public static final Map<Integer, String> SHORT_EXCEPTION_INVALID_MAP = (Map<Integer, String>)ImmutableMap.of(Integer.valueOf(65534), ", Integer.valueOf(65535), ");

    public static final Map<Integer, String> DATA_TYPE_MAP = (Map<Integer, String>)ImmutableMap.of(Integer.valueOf(1), ", Integer.valueOf(2), ", Integer.valueOf(3), ", Integer.valueOf(4), ");

    public static final DecimalFormat df1 = new DecimalFormat("0.0");

    public static final DecimalFormat df2 = new DecimalFormat("0.00");

    public static final DecimalFormat df3 = new DecimalFormat("0.000");

    public static final DecimalFormat df6 = new DecimalFormat("0.000000");

    @Excel(name = "ID", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, width = 20.0D, sort = 2147483647)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("ID")
    protected Long id;

    @Excel(name = "VIN", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 1)
    @ApiModelProperty("VIN")
    protected String vin;

    @Excel(name = "命令标识", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, sort = 2147483645)
    @ApiModelProperty("命令标识")
    protected String dataType;

    @Excel(name = "采集时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss", sort = 2)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("采集时间")
    protected LocalDateTime collectTime;

    @Excel(name = "接收时间", cellType = Excel.ColumnType.STRING, type = Excel.Type.EXPORT, dateFormat = "yyyy-MM-dd HH:mm:ss", sort = 2147483646)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("接收时间")
    protected LocalDateTime createTime;

    protected void parseBaseData(Long id, String vin, Integer dataType, LocalDateTime collectTime, LocalDateTime createTime) {
        this.id = id;
        this.vin = vin;
        this.dataType = (DATA_TYPE_MAP.get(dataType) != null) ? DATA_TYPE_MAP.get(dataType) : dataType.toString();
        this.collectTime = collectTime;
        this.createTime = createTime;
    }


}
