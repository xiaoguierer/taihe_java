package com.cn.taihe.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ResponseUtil.success(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, HttpStatus status) {
        return ResponseEntity.status(status).body(ResponseUtil.success(data));
    }

    public static ResponseEntity<ApiResponse<?>> error(String code, String message) {
        ApiResponse<?> response = ResponseUtil.error(code, message);
        return ResponseEntity.badRequest().body(response);
    }
}
