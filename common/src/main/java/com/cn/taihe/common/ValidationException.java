package com.cn.taihe.common;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(400, message);
    }
}
