package com.modern.model.dto;

import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Author：tzh
 * @Package：com.modern.model.dto
 * @Project：tsp
 * @name：TspEquipmentExcelDTO
 * @Date：2024/9/13 13:51
 * @Filename：TspEquipmentExcelDTO
 */
@ApiModel("设备信息-数据传输对象 - 导入导出")
@Data
public class TspEquipmentExcelDTO {

    @Excel(name = "设备型号名称", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 1)
    @ApiModelProperty("设备型号名称")
    private String modelName;

    @Excel(name = "设备扩展信息类型", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 1)
    @ApiModelProperty("设备扩展信息类型")
    private String extraType;

    @Excel(name = "设备分类", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 2)
    @ApiModelProperty("设备分类")
    private String name;

    @Excel(name = "版本号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 3)
    @ApiModelProperty("版本号")
    @NotEmpty(message = "版本号不能为空")
    @Pattern(regexp = "^([0-9A-Za-z]){1,10}(-([0-9A-Za-z]){1,10}){2}$", message = "版本号格式错误，版本号格式应为x-x-x组成")
    private String version;

    @Excel(name = "设备SN", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 4)
    @ApiModelProperty("设备SN")
    private String sn;

    @Excel(name = "ICCID", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 5)
    @ApiModelProperty("ICCID")
    @NotEmpty(message = "ICCID不能为空")
    @Length(min = 19, max = 19, message = "ICCID必须由长度为19位的字母数字组成")
    @Pattern(regexp = "^[A-Za-z\\d]+$", message = "ICCID必须由长度为19位的字母数字组成")
    private String iccId;

    @Excel(name = "IMSI", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 6)
    @ApiModelProperty("IMSI")
    @NotEmpty(message = "IMSI不能为空")
    @Length(min = 1, max = 15, message = "IMSI只能由不超过15位的数字组成")
    @Pattern(regexp = "^[0-9]\\d*$", message = "IMSI只能由不超过15位的数字组成")
    private String imsi;

    @Excel(name = "SIM", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 7)
    @ApiModelProperty("SIM")
    @NotEmpty(message = "SIM不能为空")
    @Length(min = 11, max = 11, message = "SIM必须为长度11位数字组成")
    @Pattern(regexp = "^[0-9]\\d*$", message = "SIM必须为长度11位数字组成")
    private String sim;

    @Excel(name = "IMEI", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 8)
    @ApiModelProperty("IMEI")
    @NotEmpty(message = "IMEI不能为空")
    @Length(min = 15, max = 15, message = "IMEI必须长度为15位数字")
    @Pattern(regexp = "^[0-9]\\d*$", message = "IMEI必须长度为15位数字")
    private String imei;

    @Excel(name = "是否为终端", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "true=是,false=否", sort = 9)
    @ApiModelProperty("是否为终端1-是 0-否")
    private Integer isTerminal;

    @Excel(name = "供应商代码", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 10)
    @ApiModelProperty("供应商代码")
    private String supplierCode;

    @Excel(name = "批次流水号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 11)
    @ApiModelProperty("批次流水号")
    private String serialNumber;

    @ApiModelProperty("是否在线1:在线 0:未在线")
    private Boolean isOnline;

    @Excel(name = "是否注册", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "true=是,false=否", sort = 13)
    @ApiModelProperty("是否注册1:是 0:否")
    private Boolean isRegister;

    @ApiModelProperty("是否报废1:是 0:否")
    private Boolean isScrap;

    @Excel(name = "运营商", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, sort = 15)
    @ApiModelProperty("运营商")
    private String operator;
}
