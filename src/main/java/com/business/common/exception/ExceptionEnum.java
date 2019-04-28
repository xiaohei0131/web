package com.business.common.exception;

import java.io.Serializable;

import static com.business.common.exception.ExceptionPrefix.COM_EXP_PREFIX;
import static com.business.common.exception.ExceptionPrefix.USER_EXP_PREFIX;

public enum ExceptionEnum implements Serializable {

    /**
     * 通用异常
     */
    UNKNOW_ERROR(COM_EXP_PREFIX+"000","未知异常"),
    OPERATION_FAIL(COM_EXP_PREFIX+"001","操作失败"),
    AUTH_FAIL(COM_EXP_PREFIX+"002","验证失败"),
    ERROR_401(COM_EXP_PREFIX+"003","401错误"),
    ERROR_403(COM_EXP_PREFIX+"004","403错误"),
    GUEST_ONLY(COM_EXP_PREFIX+"005","只允许游客访问"),
    UNLOGIN(COM_EXP_PREFIX+"006","未登录"),
    NO_PERMISSION(COM_EXP_PREFIX+"007","没有访问权限"),

    /**
     * 用户模块异常
     */
    USERNAME_EMPTY(USER_EXP_PREFIX+"000","用户名为空"),
    UNKNOWN_ACCOUNT(USER_EXP_PREFIX+"001","账号不存在"),
    ERROR_PASSWORD(USER_EXP_PREFIX+"002","密码错误"),
    ACCOUNT_USED(USER_EXP_PREFIX+"003","账号已被使用"),
            ;

    private String code;
    private String message;

    private ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ExceptionEnum() {

    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
