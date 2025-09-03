package com.modern.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.modern.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author：tzh
 * @Package：com.modern.model.vo
 * @Project：tsp
 * @name：TspUserExcelVO
 * @Date：2025/9/1 14:03
 * @Filename：TspUserExcelVO
 */
@ApiModel("一般用户- 请求对象- 导入")
@Data
public class TspUserExcelVO {
    @NotEmpty(message = "真实姓名不能为空")
    @ApiModelProperty("车主姓名")
    @Excel(name = "车主姓名", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String realName;

    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty("手机号(账号)")
    @Excel(name = "手机号(账号)", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String mobile;

    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty("身份证号")
    @Excel(name = "身份证号", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String idCard;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出生日期")
    @Excel(name = "出生日期", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String birthday;

    @ApiModelProperty("用户性别 1-男 2-女 3-未知")
    @Excel(name = "性别", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL, readConverterExp = "1=男 2=女")
    private Integer sex;

    @ApiModelProperty("注册地址(省)")
    @Excel(name = "注册地址(省)", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String province;

    @ApiModelProperty("注册地址(市)")
    @Excel(name = "注册地址(市)", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String city;

    @ApiModelProperty("注册地址(区)")
    @Excel(name = "注册地址(区)", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String area;

    @ApiModelProperty("详细地址")
    @Excel(name = "详细地址", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String address;

    @ApiModelProperty("用户标签")
    @Excel(name = "用户标签", cellType = Excel.ColumnType.STRING, type = Excel.Type.ALL)
    private String label;
}
