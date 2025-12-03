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

 Date: 03/12/2025 17:02:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_spu_intent
-- ----------------------------
DROP TABLE IF EXISTS `product_spu_intent`;
CREATE TABLE `product_spu_intent`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID，UUID格式',
  `spu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SPU ID，关联product_spu表',
  `intent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '情感意图主键，关联emotional_intent表',
  `association_level` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'primary' COMMENT '关联级别：primary:主要;secondary:次要',
  `custom_message_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '产品特定的情感描述（中文）',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序值，数值越小越靠前',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用：1启用/0停用',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_spu_intent`(`spu_id` ASC, `intent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'SPU与情感意图关联关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_spu_intent
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
