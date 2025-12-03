/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 90500 (9.5.0)
 Source Host           : localhost:3306
 Source Schema         : taihe

 Target Server Type    : MySQL
 Target Server Version : 90500 (9.5.0)
 File Encoding         : 65001

 Date: 03/12/2025 23:34:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_spu_sku_rel
-- ----------------------------
DROP TABLE IF EXISTS `product_spu_sku_rel`;
CREATE TABLE `product_spu_sku_rel`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `spu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SPU ID',
  `sku_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SKU ID',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认SKU',
  `sort_order` int NOT NULL DEFAULT 0 COMMENT '排序值',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_spu_sku`(`spu_id` ASC, `sku_id` ASC) USING BTREE,
  INDEX `idx_spu_id`(`spu_id` ASC) USING BTREE,
  INDEX `idx_sku_id`(`sku_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'SPU-SKU关联关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_spu_sku_rel
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
