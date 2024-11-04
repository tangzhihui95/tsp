package com.modern.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspVehicleSaleTemplateDTO
 * @Date：2024/10/30 14:20
 * @Filename：TspVehicleSaleTemplateDTO
 */
@ApiModel("车辆信息- 数据传输对象- 销售信息")
@Data
public class TspVehicleSaleTemplateDTO {
    @Excel(name = "VIN", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("VIN")
    private String vin;

    @ApiModelProperty("购买领域 1-私人用车 2-单位用车 0-未知")
    @Excel(name = "购买领域", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String purchaserState;

    @Excel(name = "用途", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("用途")
    private String purpose;

    @ApiModelProperty("购买方")
    @Excel(name = "购买方", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String purchaser;

    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String vehicleIdCard;

    @Excel(name = "价税合计", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("价税合计")
    private BigDecimal priceTax;

    @Excel(name = "发票号码", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("发票号码")
    private String invoiceNo;

    @Excel(name = "开票日期", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    @ApiModelProperty("开票日期")
    private String invoicingDate;

    @ApiModelProperty("是否三包 1-是 0-否")
    @Excel(name = "是否三包", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "1=")
    private Integer isSanBao;

    @ApiModelProperty("销货单位名称")
    @Excel(name = "销货单位名称", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String salesUnitName;

    @ApiModelProperty("销货地址")
    @Excel(name = "销货地址", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String salesUnitAddress;

    @ApiModelProperty("车辆状态")
    @Excel(name = "车辆状态", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "1=")
    private Integer vehicleStatus;

    @ApiModelProperty("销售渠道名称")
    @Excel(name = "销售渠道名称", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String salesChannel;

    @ApiModelProperty("销售渠道类型")
    @Excel(name = "销售渠道类型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "1=")
    private Integer channelType;

    @ApiModelProperty("办理员工名称")
    @Excel(name = "办理员工名称", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String employeeName;

    @ApiModelProperty("是否是新车")
    @Excel(name = "是否是新车", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String newVehicleFlagText;

    @ApiModelProperty("是否是新车")
    private Integer newVehicleFlag;
}
