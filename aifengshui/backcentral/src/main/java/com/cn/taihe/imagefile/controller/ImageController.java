package com.cn.taihe.imagefile.controller;

import com.cn.taihe.imagefile.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class ImageController {

  @Autowired
  private ImageStorageService imageStorageService;

  /**
   * 单张图片上传
   */
  @PostMapping("/upload")
  public ResponseEntity<Map<String, Object>> uploadImage(
    @RequestParam("file") MultipartFile file,
    @RequestParam(value = "category", defaultValue = "products") String category) {

    Map<String, Object> result = imageStorageService.uploadImage(file, category);
    return ResponseEntity.ok(result);
  }

  /**
   * 多张图片上传
   */
  @PostMapping("/upload-multiple")
  public ResponseEntity<Map<String, Object>> uploadMultipleImages(
    @RequestParam("files") MultipartFile[] files,
    @RequestParam(value = "category", defaultValue = "products") String category) {

    Map<String, Object> result = new java.util.HashMap<>();
    java.util.List<Map<String, Object>> uploadResults = new java.util.ArrayList<>();

    for (MultipartFile file : files) {
      uploadResults.add(imageStorageService.uploadImage(file, category));
    }

    result.put("success", true);
    result.put("message", "批量上传完成");
    result.put("results", uploadResults);
    result.put("total", uploadResults.size());

    return ResponseEntity.ok(result);
  }

  /**
   * 删除图片
   */
  @DeleteMapping("/delete")
  public ResponseEntity<Map<String, Object>> deleteImage(@RequestParam String filePath) {
    Map<String, Object> result = new java.util.HashMap<>();
    boolean success = imageStorageService.deleteImage(filePath);

    result.put("success", success);
    result.put("message", success ? "删除成功" : "删除失败");

    return ResponseEntity.ok(result);
  }
}
