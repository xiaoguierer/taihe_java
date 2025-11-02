package com.cn.taihe.imageutils;

import net.coobird.thumbnailator.Thumbnails;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

  /**
   * 压缩图片
   */
  public static boolean compressImage(File source, File target, float quality) {
    try {
      Thumbnails.of(source)
        .scale(1.0)
        .outputQuality(quality)
        .toFile(target);
      return true;
    } catch (Exception e) {
      System.err.println("图片压缩失败: " + e.getMessage());
      return false;
    }
  }

  /**
   * 生成缩略图
   */
  public static boolean generateThumbnail(File source, File target, int width, int height) {
    try {
      Thumbnails.of(source)
        .size(width, height)
        .toFile(target);
      return true;
    } catch (Exception e) {
      System.err.println("缩略图生成失败: " + e.getMessage());
      return false;
    }
  }

  /**
   * 获取图片信息 - 增强错误处理
   */
  public static ImageInfo getImageInfo(File imageFile) {
    try {
      // 首先检查文件是否存在且可读
      if (!imageFile.exists() || !imageFile.canRead()) {
        return new ImageInfo(0, 0, imageFile.length(), "文件不可读");
      }

      // 检查文件大小
      if (imageFile.length() == 0) {
        return new ImageInfo(0, 0, 0, "文件为空");
      }

      BufferedImage image = ImageIO.read(imageFile);
      if (image != null) {
        return new ImageInfo(image.getWidth(), image.getHeight(), imageFile.length(), "成功");
      } else {
        return new ImageInfo(0, 0, imageFile.length(), "无法解析图片格式");
      }
    } catch (IOException e) {
      // 对于模拟文件，这是正常现象，不打印错误堆栈
      return new ImageInfo(0, 0, imageFile.length(), "图片解析失败: " + e.getMessage());
    } catch (Exception e) {
      return new ImageInfo(0, 0, imageFile.length(), "未知错误: " + e.getMessage());
    }
  }

  public static class ImageInfo {
    private final int width;
    private final int height;
    private final long size;
    private final String status;

    public ImageInfo(int width, int height, long size, String status) {
      this.width = width;
      this.height = height;
      this.size = size;
      this.status = status;
    }

    // 重载构造函数，兼容旧代码
    public ImageInfo(int width, int height, long size) {
      this(width, height, size, "成功");
    }

    // getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public long getSize() { return size; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
      if (width > 0 && height > 0) {
        return String.format("ImageInfo{width=%d, height=%d, size=%.2fMB}",
          width, height, size / (1024.0 * 1024.0));
      } else {
        return String.format("ImageInfo{size=%.2fMB, status=%s}",
          size / (1024.0 * 1024.0), status);
      }
    }

    public boolean isValid() {
      return width > 0 && height > 0;
    }
  }
}
