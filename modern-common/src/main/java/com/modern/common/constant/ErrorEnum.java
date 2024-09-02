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
    TSP_EQUIPMENT_TYPE_NOT_NULL_ERR(Integer.valueOf(3), Integer.valueOf(107), "设备分类已存在");

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
