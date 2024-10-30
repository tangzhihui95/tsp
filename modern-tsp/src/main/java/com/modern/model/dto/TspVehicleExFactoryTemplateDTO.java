package com.modern.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleExFactoryTemplateDTO
 * @Date：2024/10/29 16:48
 * @Filename：TspVehicleExFactoryTemplateDTO
 */
@ApiModel("车辆信息- 数据传输对象- 出厂信息模版")
@Data
public class TspVehicleExFactoryTemplateDTO {
    @Excel(name = "VIN", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("VIN")
    private String vin;

    @Excel(name = "车辆厂商", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("车辆厂商")
    private String providerName;

    @Excel(name = "配置名称", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("配置名称")
    private String configureName;

    @Excel(name = "颜色", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("颜色")
    private String color;

    @Excel(name = "批次号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("批次号")
    private String batchNo;

    @Excel(name = "出厂日期", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("出厂日期")
    private String exFactoryDate;

    @Excel(name = "下线日期", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("下线日期")
    private String operateDate;

    @Excel(name = "标签", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("标签")
    private String label;

    @Excel(name = "备注", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("备注")
    private String remark;

    @Excel(name = "设备SN", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("设备SN")
    private String sn;

    @Excel(name = "设备类型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("设备类型")
    private String name;

    @Excel(name = "设备型号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("设备型号")
    private String modelName;

    @Excel(name = "一级车型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("一级车型")
    private String vehicleModelName;

    @Excel(name = "二级车型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("二级车型")
    private String stdModelName;

    @Excel(name = "发动机序号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("发动机序号")
    private String engineNum;

    @Excel(name = "CDU", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("CDU")
    private String cduNum;

    @Excel(name = "电池包规格", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("电池包规格")
    private String essModel;

    @Excel(name = "电池包编号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("电池包编号")
    private String essNum;

    @Excel(name = "发动机品牌", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("发动机品牌")
    private String motorBrand;

    @Excel(name = "电动机序列号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("电动机序列号")
    private String motorNum;
}
