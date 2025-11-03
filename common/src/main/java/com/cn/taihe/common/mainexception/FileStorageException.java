package com.cn.taihe.common.mainexception;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.common.mainexception
 * @Author: 大咖
 * @CreateTime: 2025-11-03  12:40
 * @Description: TODO
 * @Version: 1.0
 */
public class FileStorageException extends RuntimeException {

  public FileStorageException(String message) {
    super(message);
  }

  public FileStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
