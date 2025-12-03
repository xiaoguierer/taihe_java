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

 Date: 03/12/2025 17:22:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `spu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'SPU ID',
  `sku_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'SKU编码',
  `sku_name_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '英文SKU名称',
  `sku_name_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文SKU名称',
  `sku_name_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '阿拉伯语SKU名称',
  `variant_description_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '英文变体描述',
  `variant_description_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文变体描述',
  `variant_description_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '阿拉伯语变体描述',
  `variant_yuyi_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '寓意描述',
  `variant_yuyi_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '寓意描述',
  `variant_yuyi_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '寓意描述',
  `primary_material` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主要材质',
  `material_purity` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '材质纯度',
  `material_color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '材质颜色',
  `material_finish` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '表面处理',
  `material_thickness_mm` decimal(4, 2) NULL DEFAULT NULL COMMENT '材质厚度(mm)',
  `gemstone_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '宝石类型',
  `gemstone_shape` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '宝石形状',
  `gemstone_cut` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '宝石切工',
  `gemstone_size_mm` decimal(5, 2) NULL DEFAULT NULL COMMENT '宝石尺寸(mm)',
  `gemstone_weight_ct` decimal(6, 3) NULL DEFAULT NULL COMMENT '宝石重量(克拉)',
  `gemstone_quality` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '宝石品质',
  `gemstone_count` int NULL DEFAULT 0 COMMENT '宝石数量',
  `length_cm` decimal(6, 2) NULL DEFAULT NULL COMMENT '长度(cm)',
  `width_cm` decimal(6, 2) NULL DEFAULT NULL COMMENT '宽度(cm)',
  `height_cm` decimal(6, 2) NULL DEFAULT NULL COMMENT '高度(cm)',
  `chain_length_cm` decimal(6, 2) NULL DEFAULT NULL COMMENT '链长(cm)',
  `pendant_size_mm` decimal(6, 2) NULL DEFAULT NULL COMMENT '吊坠尺寸(mm)',
  `total_weight_g` decimal(8, 2) NULL DEFAULT NULL COMMENT '总重量(克)',
  `metal_weight_g` decimal(8, 2) NULL DEFAULT NULL COMMENT '金属重量(克)',
  `gemstone_weight_g` decimal(8, 2) NULL DEFAULT NULL COMMENT '宝石重量(克)',
  `craftsmanship_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工艺等级',
  `setting_technique` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '镶嵌工艺',
  `clasp_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '扣类型',
  `chain_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '链类型',
  `suitable_gender` int NULL DEFAULT 1 COMMENT '适用性别:1 男性,2 女性,3 中性',
  `suitable_age_min` int NULL DEFAULT NULL COMMENT '适用最小年龄',
  `suitable_age_max` int NULL DEFAULT NULL COMMENT '适用最大年龄',
  `size_standard` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '尺寸标准',
  `stock_quantity` int NULL DEFAULT 0 COMMENT '库存数量',
  `reserved_quantity` int NULL DEFAULT 0 COMMENT '预留库存',
  `available_quantity` int GENERATED ALWAYS AS ((`stock_quantity` - `reserved_quantity`)) VIRTUAL COMMENT '可用库存' NULL,
  `safety_stock` int NULL DEFAULT 5 COMMENT '安全库存',
  `reorder_point` int NULL DEFAULT 3 COMMENT '补货点',
  `stock_status` int NULL DEFAULT 1 COMMENT '库存状态: 1有货,2 低库存,3 缺货',
  `low_stock_alert` tinyint(1) NULL DEFAULT 0 COMMENT '低库存预警',
  `last_stock_update` datetime NULL DEFAULT NULL COMMENT '最后库存更新',
  `cost_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '成本价',
  `retail_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '零售价',
  `sale_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销价',
  `member_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '会员价',
  `price_currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'USD' COMMENT '价格货币',
  `discount_rate` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '折扣率(%)',
  `discount_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣金额',
  `discount_start_date` date NULL DEFAULT NULL COMMENT '折扣开始日期',
  `discount_end_date` date NULL DEFAULT NULL COMMENT '折扣结束日期',
  `tax_rate` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '税率(%)',
  `final_price` decimal(10, 2) GENERATED ALWAYS AS ((coalesce(`sale_price`,`retail_price`) * (1 - (`discount_rate` / 100)))) VIRTUAL COMMENT '最终售价' NULL,
  `price_adjustment_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '价格调整原因',
  `last_price_update` datetime NULL DEFAULT NULL COMMENT '最后价格更新',
  `main_image_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图ID',
  `image_1_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片1 ID',
  `image_2_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片2 ID',
  `image_3_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片3 ID',
  `image_4_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片4 ID',
  `image_5_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片5 ID',
  `main_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图url',
  `image_1_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片1 url',
  `image_2_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片2 url',
  `image_3_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片3 url',
  `image_4_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片4 url',
  `image_5_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片5 url',
  `available_date` date NULL DEFAULT NULL COMMENT '可售开始日期',
  `available_end_date` date NULL DEFAULT NULL COMMENT '可售结束日期',
  `is_new_arrival` tinyint(1) NULL DEFAULT 0 COMMENT '是否新品',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态:0 草稿,1 上架,2 下架',
  `is_available` tinyint(1) NULL DEFAULT 1 COMMENT '是否可售',
  `is_featured` tinyint(1) NULL DEFAULT 0 COMMENT '是否推荐',
  `is_best_seller` tinyint(1) NULL DEFAULT 0 COMMENT '是否畅销',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序值',
  `visibility` tinyint(1) NULL DEFAULT 1 COMMENT '可见性:1 公开,2 隐藏',
  `created_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `updated_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品SKU表(关联图片ID)' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
