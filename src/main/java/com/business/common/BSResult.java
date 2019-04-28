package com.business.common;

import com.business.common.exception.ExceptionEnum;

import java.io.Serializable;

public class BSResult implements Serializable {
    /**
     * 执行结果
     */
    private boolean isSuccess;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public static BSResult success() {
        BSResult result = new BSResult();
        result.setSuccess(true);
        return result;
    }

    public static BSResult success(Object data) {
        BSResult result = new BSResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static BSResult failure() {
        BSResult result = new BSResult();
        result.setSuccess(false);
        result.setErrorCode(ExceptionEnum.UNKNOW_ERROR.getCode());
        result.setMessage(ExceptionEnum.UNKNOW_ERROR.getMessage());
        return result;
    }

    public static BSResult failure(ExceptionEnum exception) {
        BSResult result = new BSResult();
        result.setSuccess(false);
        result.setErrorCode(exception.getCode());
        result.setMessage(exception.getMessage());
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BSResult{" +
                "isSuccess=" + isSuccess +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
