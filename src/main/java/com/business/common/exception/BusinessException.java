package com.business.common.exception;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {
    private ExceptionEnum exp;

    public BusinessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exp = exceptionEnum;
    }

    public BusinessException() {
    }

    public ExceptionEnum getExp() {
        return exp;
    }
}
