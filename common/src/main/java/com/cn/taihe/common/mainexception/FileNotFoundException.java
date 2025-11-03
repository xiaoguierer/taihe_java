package com.cn.taihe.common.mainexception;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe.common.mainexception
 * @Author: 大咖
 * @CreateTime: 2025-11-03  12:41
 * @Description: TODO
 * @Version: 1.0
 */
public class FileNotFoundException extends FileStorageException {

  public FileNotFoundException(String message) {
    super(message);
  }

  public FileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
