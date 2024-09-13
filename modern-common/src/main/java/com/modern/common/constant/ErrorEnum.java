package com.modern.common.constant;

import com.modern.common.exception.ServiceException;

/**
 * @Author：tzh
 * @Package：com.modern.common.constant
 * @Project：tsp
 * @name：ErrorEnum
 * @Date：2024/8/30 15:45
 * @Filename：ErrorEnum
 */
public enum ErrorEnum {
    TSP_EQUIPMENT_TYPE_NOT_NULL_ERR(Integer.valueOf(3), Integer.valueOf(107), "设备分类已存在"),
    TSP_EQUIPMENT_TYPE_NULL_ERR(Integer.valueOf(3), Integer.valueOf(108), "未找到设备分类"),
    TSP_EQUIPMENT_TYPE_DELETE_ERR(Integer.valueOf(3), Integer.valueOf(109), "设备分类下存在设备型号,无法删除"),
    TSP_EQUIPMENT_MODEL_NOT_NULL_ERR(Integer.valueOf(12), Integer.valueOf(110), "设备型号已存在"),
    TSP_EQUIPMENT_MODEL_DELETE_ERR(Integer.valueOf(13), Integer.valueOf(112), "设备型号下存在设备无法删除"),
    TSP_EQUIPMENT_NULL_ERR(Integer.valueOf(4), Integer.valueOf(113), "未找到设备请检查"),
    TSP_EQUIPMENT_EQUIPMENT_DELETE_ERR(Integer.valueOf(4), Integer.valueOf(114), "设备下存在绑定车辆,无法删除"),
    TSP_EQUIPMENT_SCRAP_ERR(Integer.valueOf(7), Integer.valueOf(124), "未绑定车辆不可进行报废操作,请先进行解绑"),
    TSP_VEHICLE_SCRAP_ERR(Integer.valueOf(7), Integer.valueOf(125), "密码输入错误,请重新输入"),
    TSP_SN_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(152), "该SN在设备中已存在"),
    TSP_ICCID_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(153), "该ICCID在设备中已存在"),
    TSP_IMEI_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(154), "该IMEI在设备中已存在"),
    TSP_IMSI_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(155), "该IMSI在设备中已存在"),
    TSP_SIM_NOT_UNIQ_ERR(Integer.valueOf(4), Integer.valueOf(156), "该SIM在设备中已存在");

    private Integer type;

    private Integer code;

    private String msg;

    ErrorEnum(Integer type, Integer code, String msg) {
        this.type = type;
        this.code = code;
        this.msg = msg;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void throwErr(Exception e) {
        throw new ServiceException(String.valueOf(e));
    }

    public void throwErr(String msg, Integer code, Integer type) {
        this.msg = msg;
        this.code = code;
        this.type = type;
        throw new ServiceException(this);
    }

    public void throwErr() {
        throw new ServiceException(this);
    }

}
