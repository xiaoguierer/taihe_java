package com.cn.taihe.common;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success(data);
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.success(null);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.error(code, message);
    }

    public static <T> ApiResponse<T> error(ResponseCode responseCode) {
        return error(responseCode.getCode(), responseCode.getMessage());
    }

    public static <T> ApiResponse<T> error(ResponseCode responseCode, String customMessage) {
        return error(responseCode.getCode(), customMessage);
    }
}
