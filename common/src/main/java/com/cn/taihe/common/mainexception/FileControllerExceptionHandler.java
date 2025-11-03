package com.cn.taihe.common.mainexception;

import com.cn.taihe.common.mainexception.FileNotFoundException;
import com.cn.taihe.common.mainexception.FileStorageException;
import com.cn.taihe.common.mainexception.FileValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件控制器异常处理器（限定范围，避免与全局异常处理器冲突）
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.cn.taihe.back.imagefile.controller")
public class FileControllerExceptionHandler {

  @ExceptionHandler(FileValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleFileValidationException(FileValidationException e) {
    log.warn("文件验证失败: {}", e.getMessage());
    return createErrorResponse("400", e.getMessage());
  }

  @ExceptionHandler(FileNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> handleFileNotFoundException(FileNotFoundException e) {
    log.warn("文件未找到: {}", e.getMessage());
    return createErrorResponse("404", e.getMessage());
  }

  @ExceptionHandler(FileStorageException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleFileStorageException(FileStorageException e) {
    log.error("文件存储异常: {}", e.getMessage(), e);
    return createErrorResponse("500", "文件处理失败，请稍后重试");
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    log.warn("文件大小超过限制: {}", e.getMessage());
    return createErrorResponse("400", "文件大小超过限制");
  }

  private Map<String, Object> createErrorResponse(String code, String message) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("code", code);
    response.put("message", message);
    response.put("timestamp", System.currentTimeMillis());
    return response;
  }
}
