package com.modern.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author：tzh
 * @Package：com.modern.common.core.domain
 * @Project：tsp
 * @name：ResultErrInfo
 * @Date：2024/8/28 15:26
 * @Filename：ResultErrInfo
 */
public class ResultErrInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("错误码 0:正常 非0错误")
    private Integer errcode = Integer.valueOf(0);

    @ApiModelProperty("错误信息")
    private String errmsg = "success";

    @ApiModelProperty("是否有返回信息")
    private boolean hasInfo = false;

    @ApiModelProperty("返回信息，等同于接口正常返回信息")
    private T info;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultErrInfo<?> that = (ResultErrInfo<?>) o;
        return hasInfo == that.hasInfo && Objects.equals(errcode, that.errcode) && Objects.equals(errmsg, that.errmsg) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errcode, errmsg, hasInfo, info);
    }
}
