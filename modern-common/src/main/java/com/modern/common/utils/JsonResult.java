package com.modern.common.utils;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class JsonResult {
    private boolean success = true;
    private String businessCode = "1";
    private String errorCode = "1";
    private String msg = "操作成功";
    private Object data;

    public static JsonResult getResult(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult errotResult(Object data, Boolean success) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(data);
        jsonResult.setSuccess(success);
        return jsonResult;
    }

    public static JsonResult getResult2(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(data);
        jsonResult.setBusinessCode("2");
        jsonResult.setMsg("正在上报");
        return jsonResult;
    }

    public static JsonResult getResult1(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(data);
        jsonResult.setMsg("上报完成");
        return jsonResult;
    }

    public static JsonResult error(String errorCode, MessageSource messageSource, Object... params) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setBusinessCode("0");
        jsonResult.setErrorCode(errorCode);
        jsonResult.setMsg(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
        return jsonResult;
    }

    public static JsonResult error(String errorCode, String errorMsg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setBusinessCode("0");
        jsonResult.setErrorCode(errorCode);
        jsonResult.setMsg(errorMsg);
        return jsonResult;
    }

    public static JsonResult errorData(String errorCode, MessageSource messageSource, Object data, Object... params) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setBusinessCode("0");
        jsonResult.setErrorCode(errorCode);
        jsonResult.setMsg(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
        jsonResult.setData(data);
        return jsonResult;
    }

    public JsonResult() {
        super();
    }

    public JsonResult(boolean success) {
        super();
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public void setError(String errorCode, MessageSource messageSource, Object... params) {
        this.setSuccess(false);
        this.setBusinessCode("0");
        this.setErrorCode(errorCode);
        this.setMsg(messageSource.getMessage(errorCode, params, errorCode, Locale.SIMPLIFIED_CHINESE));
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "success=" + success +
                ", businessCode='" + businessCode + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
