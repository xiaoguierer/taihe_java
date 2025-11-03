package com.cn.taihe.back.imagefile.controller;
import com.cn.taihe.common.fileutils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件服务健康检查控制器
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileHealthController {

  @GetMapping("/health")
  public ApiResult<String> health() {
    log.info("文件服务健康检查");
    return ApiResult.success("文件服务运行正常");
  }

  @GetMapping("/test")
  public ApiResult<String> test() {
    return ApiResult.success("测试接口正常");
  }
}
