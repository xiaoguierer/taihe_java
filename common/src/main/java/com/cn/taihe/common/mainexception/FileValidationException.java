package com.cn.taihe.common.mainexception;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.common.mainexception
 * @Author: 大咖
 * @CreateTime: 2025-11-03  12:41
 * @Description: TODO
 * @Version: 1.0
 */
/**
 * 文件验证异常（类型、大小等验证失败）
 */
public class FileValidationException extends FileStorageException {

  public FileValidationException(String message) {
    super(message);
  }

  public FileValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
