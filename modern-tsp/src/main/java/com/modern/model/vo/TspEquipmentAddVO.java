package com.modern.model.vo;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspEquipmentAddVO
 * @Date：2024/9/13 10:33
 * @Filename：TspEquipmentAddVO
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel("设备信息-请求对象-添加")
@Data
public class TspEquipmentAddVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备ID")
    private Long tspEquipmentId;

    @NotNull(message = "设备分类不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("设备型号ID")
    private Long tspEquipmentModelId;

    @NotEmpty(message = "ICCID不能为空")
    @Length(min = 19, max = 19, message = "ICCID必须由长度为19位的字母数字组成")
    @Pattern(regexp = "^[A-Za-z\\d]+$", message = "ICCID必须由长度为19位的字母数字组成")
    @ApiModelProperty("iccId")
    private String iccId;

    @NotEmpty(message = "IMSI不能为空")
    @Length(min = 1, max = 15, message = "IMSI只能由不超过15位的数字组成")
    @Pattern(regexp = "^[0-9]\\d*$", message = "IMSI只能由不超过15位的数字组成")
    @ApiModelProperty("IMSI")
    private String imsi;

    @NotEmpty(message = "SIM不能为空")
    @Length(min = 11, max = 11, message = "SIM必须为长度11位数字组成")
    @Pattern(regexp = "^[0-9]\\d*$", message = "SIM必须为长度11位数字组成")
    @ApiModelProperty("sim")
    private String sim;

    @NotEmpty(message = "IMEI不能为空")
    @Length(min = 15, max = 15, message = "IMEI必须长度为15位数字")
    @Pattern(regexp = "^[0-9]\\d*$", message = "IMEI必须长度为15位数字")
    @ApiModelProperty("imei")
    private String imei;

    @NotEmpty(message = "版本号不能为空")
    @Pattern(regexp = "^([0-9A-Za-z]){1,10}(-([0-9A-Za-z]){1,10}){2}$", message = "版本号格式错误,版本号格式应为x-x-x组成")
    @ApiModelProperty("版本号")
    private String version;

    @NotEmpty(message = "设备SN不能为空")
    @Length(min = 1, max = 24, message = "设备SN码不能超过24位、区分大小写")
    @ApiModelProperty("设备SN")
    private String sn;

    @ApiModelProperty("是否为终端")
    private Boolean isTerminal;

    @ApiModelProperty("供应商代码")
    private String supplierCode;

    @ApiModelProperty("运营商")
    private Integer operator;

    @NotEmpty(message = "批次流水号不能为空")
    @Length(min = 1, max = 24, message = "批次流水号不能超过24位、区分大小写")
    @ApiModelProperty("批次流水号")
    private String serialNumber;

    @ApiModelProperty("是否在线")
    private Boolean isOnline;

    @ApiModelProperty("是否注册")
    private Boolean isRegister;

    @ApiModelProperty("是否报废")
    private Boolean isScrap;
}
