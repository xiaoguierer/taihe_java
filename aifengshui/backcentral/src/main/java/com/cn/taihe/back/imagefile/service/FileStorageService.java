package com.cn.taihe.back.imagefile.service;
import com.cn.taihe.common.mainexception.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * 文件存储服务接口
 */
public interface FileStorageService {

  /**
   * 上传文件
   *
   * @param file 上传的文件
   * @param businessType 业务类型，用于分类存储（如：avatar, contract, document）
   * @return 文件的相对路径（用于存储在数据库中）
   * @throws FileStorageException 文件存储异常
   */
  String upload(MultipartFile file, String businessType) throws FileStorageException;

  /**
   * 上传文件（指定文件名）
   *
   * @param file 上传的文件
   * @param businessType 业务类型
   * @param customFileName 自定义文件名（不含扩展名）
   * @return 文件的相对路径
   * @throws FileStorageException 文件存储异常
   */
  String upload(MultipartFile file, String businessType, String customFileName) throws FileStorageException;

  /**
   * 下载文件为Resource对象
   *
   * @param relativePath 文件的相对路径
   * @return Resource对象
   * @throws FileStorageException 文件存储异常
   */
  Resource downloadAsResource(String relativePath) throws FileStorageException;

  /**
   * 获取文件输入流
   *
   * @param relativePath 文件的相对路径
   * @return 文件输入流
   * @throws FileStorageException 文件存储异常
   */
  InputStream getFileInputStream(String relativePath) throws FileStorageException;

  /**
   * 删除文件
   *
   * @param relativePath 文件的相对路径
   * @return 是否删除成功
   * @throws FileStorageException 文件存储异常
   */
  boolean delete(String relativePath) throws FileStorageException;

  /**
   * 检查文件是否存在
   *
   * @param relativePath 文件的相对路径
   * @return 是否存在
   */
  boolean exists(String relativePath);

  /**
   * 获取文件大小
   *
   * @param relativePath 文件的相对路径
   * @return 文件大小（字节）
   * @throws FileStorageException 文件存储异常
   */
  long getFileSize(String relativePath) throws FileStorageException;

  /**
   * 获取文件访问URL
   *
   * @param relativePath 文件的相对路径
   * @return 完整的访问URL
   */
  String getFileAccessUrl(String relativePath);

  /**
   * 获取文件存储的绝对路径
   *
   * @param relativePath 文件的相对路径
   * @return 绝对路径
   */
  Path getFileAbsolutePath(String relativePath);
}
