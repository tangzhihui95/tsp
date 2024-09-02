package com.modern.common.exception;

import com.modern.common.constant.ErrorEnum;
import com.modern.common.core.domain.ResultErrInfo;

import java.util.Objects;

/**
 * 业务异常
 *
 * @author tzh
 */
public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer type = Integer.valueOf(0);

    private Integer code = Integer.valueOf(0);

    private String message = "异常，请联系管理员";

    private String detailMessage;

    private ResultErrInfo<Object> errInfo = new ResultErrInfo();


    protected boolean canEqual(Object other) {
        return other instanceof com.modern.common.exception.ServiceException;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setErrInfo(ResultErrInfo<Object> errInfo) {
        this.errInfo = errInfo;
    }

    public String toString() {
        return "ServiceException(type=" + getType() + ", code=" + getCode() + ", message=" + getMessage() + ", detailMessage=" + getDetailMessage() + ", errInfo=" + getErrInfo() + ")";
    }

    public Integer getType() {
        return this.type;
    }

    public ResultErrInfo<Object> getErrInfo() {
        return this.errInfo;
    }

    public ServiceException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
        this.type = errorEnum.getType();
        this.errInfo.setErrcode(Integer.valueOf(1));
        this.errInfo.setErrmsg(this.message);
        this.errInfo.setHasInfo(false);
        this.errInfo.setInfo(null);
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message) {
        this.errInfo.setErrcode(Integer.valueOf(1));
        this.errInfo.setErrmsg(message);
        this.errInfo.setHasInfo(false);
        this.errInfo.setInfo(null);
        this.message = message;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return this.code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    public ServiceException(ErrorEnum errorEnum, Exception e) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceException that = (ServiceException) o;
        return Objects.equals(type, that.type) && Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(detailMessage, that.detailMessage) && Objects.equals(errInfo, that.errInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, message, detailMessage, errInfo);
    }
}
