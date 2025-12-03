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

 Date: 03/12/2025 23:34:42
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

-- ----------------------------
-- Records of product_sku
-- ----------------------------
INSERT INTO `product_sku` VALUES ('SKU_HETIAN_58MM', 'SPU_HETIAN_BRACELET_001', 'HETIAN_WHITE_58MM', 'Hetian White Jade Bracelet - 58mm Small', '和田白玉手镯 - 58毫米小号', 'سوار يشب خه تيان أبيض - 58 مم صغير', '58mm inner diameter, creamy white color, smooth polished finish', '58毫米内径，乳白色，光滑抛光表面', 'قطر داخلي 58 ملم، لون أبيض كريمي، تشطيب أملس مصقول', 'Symbolizes purity and spiritual protection, enhances earth energy connection', '象征纯洁与灵性保护，增强大地能量连接', 'يرمز إلى النقاء والحماية الروحية، يعزز اتصال طاقة الأرض', 'Hetian Nephrite', 'Grade A Natural', 'Creamy White', 'Mirror Polish', 8.50, 'Nephrite Jade', 'Bracelet', 'Cabochon', 58.00, 45.000, 'Premium Quality', 1, 18.50, 1.50, 1.50, NULL, NULL, 42.50, 42.50, 0.00, 'Excellent', 'Solid Jade', 'None', 'Bracelet', 2, 18, 65, 'Asian Standard', 15, 2, DEFAULT, 5, 3, 1, 0, '2025-12-03 16:30:07', 2800.00, 10584.00, 9525.60, 10054.80, 'USD', 10.00, 1058.40, '2025-01-01', '2025-12-31', 8.00, DEFAULT, 'New arrival promotion', '2025-12-03 16:30:07', '386797068011302912', '386797067956776960', '386797067969359872', '386797067977748480', '386797067990331392', '386797068002914304', '/api/files/PRODUCDETAIL/2025/12/03/20251203163327354482.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163327340096.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163327343674.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163327346047.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163327348530.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163327351457.png', '2025-01-01', NULL, 1, 1, 1, 1, 0, 100, 1, 1, 1, '2025-12-03 16:30:07', '2025-12-03 16:33:27');
INSERT INTO `product_sku` VALUES ('SKU_HETIAN_60MM', 'SPU_HETIAN_BRACELET_001', 'HETIAN_WHITE_60MM', 'Hetian White Jade Bracelet - 60mm Medium', '和田白玉手镯 - 60毫米中号', 'سوار يشب خه تيان أبيض - 60 مم وسط', '60mm inner diameter, standard size, smooth polished finish', '60毫米内径，标准尺寸，光滑抛光表面', 'قطر داخلي 60 ملم، مقاس قياسي، تشطيب أملس مصقول', 'Represents balance and harmony, suitable for daily wear and spiritual practice', '代表平衡与和谐，适合日常佩戴和灵性修炼', 'يمثل التوازن والانسجام، مناسب للارتداء اليومي والممارسة الروحية', 'Hetian Nephrite', 'Grade A Natural', 'Creamy White', 'Mirror Polish', 9.00, 'Nephrite Jade', 'Bracelet', 'Cabochon', 60.00, 48.000, 'Premium Quality', 1, 18.80, 1.50, 1.50, NULL, NULL, 45.20, 45.20, 0.00, 'Excellent', 'Solid Jade', 'None', 'Bracelet', 2, 20, 70, 'Asian Standard', 25, 3, DEFAULT, 8, 5, 1, 0, '2025-12-03 16:30:07', 3200.00, 12096.00, 10886.40, 11491.20, 'USD', 10.00, 1209.60, '2025-01-01', '2025-12-31', 8.00, DEFAULT, 'Best seller model', '2025-12-03 16:30:07', '386797202346471424', '386797202258391040', '386797202275168256', '386797202291945472', '386797202308722688', '386797202329694208', '/api/files/PRODUCDETAIL/2025/12/03/20251203163359380414.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203163359359451.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163359363421.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163359367731.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163359372408.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163359376022.png', '2025-01-01', NULL, 1, 1, 1, 1, 1, 200, 1, 1, 1, '2025-12-03 16:30:07', '2025-12-03 16:33:59');
INSERT INTO `product_sku` VALUES ('SKU_HETIAN_62MM', 'SPU_HETIAN_BRACELET_001', 'HETIAN_WHITE_62MM', 'Hetian White Jade Bracelet - 62mm Large', '和田白玉手镯 - 62毫米大号', 'سوار يشب خه تيان أبيض - 62 مم كبير', '62mm inner diameter, comfortable fit, smooth polished finish', '62毫米内径，舒适佩戴，光滑抛光表面', 'قطر داخلي 62 ملم، ملاءمة مريحة، تشطيب أملس مصقول', 'Symbolizes strength and protection, ideal for those preferring loose fit', '象征力量与保护，适合喜欢宽松佩戴的用户', 'يرمز إلى القوة والحماية، مثالي لمن يفضلون الملاءمة الفضفاضة', 'Hetian Nephrite', 'Grade A Natural', 'Creamy White', 'Mirror Polish', 9.50, 'Nephrite Jade', 'Bracelet', 'Cabochon', 62.00, 52.000, 'Premium Quality', 1, 19.50, 1.50, 1.50, NULL, NULL, 48.80, 48.80, 0.00, 'Excellent', 'Solid Jade', 'None', 'Bracelet', 2, 25, 75, 'Asian Standard', 10, 1, DEFAULT, 3, 2, 1, 0, '2025-12-03 16:30:07', 3500.00, 13230.00, 11907.00, 12568.50, 'USD', 10.00, 1323.00, '2025-01-01', '2025-12-31', 8.00, DEFAULT, 'Limited stock item', '2025-12-03 16:30:07', '386797317517864960', '386797317459144704', '386797317475921920', '386797317488504832', '386797317496893440', '386797317509476352', '/api/files/PRODUCDETAIL/2025/12/03/20251203163426841293.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163426825817.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163426829032.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163426833835.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203163426836258.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203163426838087.png', '2025-01-01', NULL, 1, 1, 1, 0, 0, 300, 1, 1, 1, '2025-12-03 16:30:07', '2025-12-03 16:34:26');
INSERT INTO `product_sku` VALUES ('SKU_HETIAN_NECKLACE_L001', 'SPU_HETIAN_NECKLACE_001', 'HETIAN_NECKLACE_LG_001', 'Hetian Jade Necklace - Large Collector Edition (25mm)', '和田玉项链 - 大号收藏版(25mm)', 'قلادة يشب خه تيان - إصدار جامع كبير (25مم)', 'Large carved white jade pendant (25mm) with premium adjustable chain, 50-55cm', '大号雕刻白玉吊坠(25mm)配高级可调节链，长度50-55厘米', 'قلادة بيضاء منحوتة كبيرة (25مم) مع سلسلة قابلة للتعديل عالية الجودة، طول 50-55سم', 'Embodies elegance, spiritual connection, and ancestral wisdom', '体现优雅、灵性连接和祖先智慧', 'يجسد الأناقة والاتصال الروحي وحكمة الأسلاف', 'Hetian White Nephrite', 'Grade AAA Collector', 'Snow White', 'Carved Polish', 8.00, 'Jade', 'Custom Carved', 'Artisan Cut', 25.00, 25.800, 'Collector Grade', 1, 5.00, 2.50, 0.80, 55.00, 25.00, 32.50, 5.20, 27.30, 'Master Artisan', 'Custom Bezel', 'Box Clasp', 'Premium Chain', 3, 25, 65, 'Standard-Large', 10, 1, DEFAULT, 2, 1, 1, 1, '2025-12-03 17:30:00', 450.00, 1200.00, 980.00, 1080.00, 'USD', 18.00, 220.00, '2025-12-01', '2025-12-31', 8.00, DEFAULT, 'Limited collector item', '2025-12-03 17:30:00', '386812945570066432', '386812945515540480', '386812945528123392', '386812945536512000', '386812945549094912', '386812945557483520', '/api/files/PRODUCDETAIL/2025/12/03/20251203173632858922.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173632845927.jpeg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173632848255.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173632851082.webp', '/api/files/PRODUCDETAIL/2025/12/03/20251203173632853118.webp', '/api/files/PRODUCDETAIL/2025/12/03/20251203173632856411.webp', '2025-12-01', '2026-12-01', 1, 1, 1, 1, 0, 80, 1, 1, 1, '2025-12-03 17:30:00', '2025-12-03 17:36:32');
INSERT INTO `product_sku` VALUES ('SKU_HETIAN_NECKLACE_M001', 'SPU_HETIAN_NECKLACE_001', 'HETIAN_NECKLACE_MD_001', 'Hetian Jade Necklace - Medium Pendant (20mm)', '和田玉项链 - 中号吊坠(20mm)', 'قلادة يشب خه تيان - قلادة متوسطة (20مم)', 'Medium oval white jade pendant (20mm) with adjustable silk cord, 45-50cm', '中号椭圆形白玉吊坠(20mm)配可调节真丝绳，长度45-50厘米', 'قلادة بيضاوية بيضاء متوسطة (20مم) مع حبل حريري قابل للتعديل، طول 45-50سم', 'Represents harmony, wisdom, and emotional balance', '代表和谐、智慧和情绪平衡', 'يمثل الانسجام والحكمة والتوازن العاطفي', 'Hetian White Nephrite', 'Grade AA Premium', 'Pure White', 'Mirror Polish', 6.50, 'Jade', 'Oval', 'Cabochon', 20.00, 15.200, 'Premium', 1, 4.00, 2.00, 0.65, 50.00, 20.00, 18.80, 3.50, 15.30, 'Artisan Level', 'Prong Setting', 'Lobster Clasp', 'Adjustable Silk', 3, 20, 70, 'Standard-Medium', 30, 5, DEFAULT, 5, 3, 1, 0, '2025-12-03 17:30:00', 280.00, 680.00, 580.00, 620.00, 'USD', 15.00, 100.00, '2025-12-01', '2025-12-31', 8.00, DEFAULT, 'Premium quality edition', '2025-12-03 17:30:00', '386813066412158976', '386813066353438720', '386813066370215936', '386813066382798848', '386813066391187456', '386813066403770368', '/api/files/PRODUCDETAIL/2025/12/03/20251203173701669602.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173701653159.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173701658923.webp', '/api/files/PRODUCDETAIL/2025/12/03/20251203173701662664.webp', '/api/files/PRODUCDETAIL/2025/12/03/20251203173701665050.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173701667183.webp', '2025-12-01', '2026-12-01', 1, 1, 1, 1, 1, 90, 1, 1, 1, '2025-12-03 17:30:00', '2025-12-03 17:37:01');
INSERT INTO `product_sku` VALUES ('SKU_HETIAN_NECKLACE_S001', 'SPU_HETIAN_NECKLACE_001', 'HETIAN_NECKLACE_SM_001', 'Hetian Jade Necklace - Small Pendant (15mm)', '和田玉项链 - 小号吊坠(15mm)', 'قلادة يشب خه تيان - قلادة صغيرة (15مم)', 'Small round white jade pendant (15mm) with black silk cord, adjustable 40-45cm', '小号圆形白玉吊坠(15mm)配黑色真丝绳，可调节长度40-45厘米', 'قلادة بيضاء دائرية صغيرة (15مم) مع حبل حريري أسود، طول قابل للتعديل 40-45سم', 'Symbolizes purity, protection, and new beginnings', '象征纯洁、保护和新的开始', 'يرمز للنقاء والحماية وبدايات جديدة', 'Hetian White Nephrite', 'Grade A Natural', 'Creamy White', 'High Polish', 5.00, 'Jade', 'Round', 'Cabochon', 15.00, 8.500, 'Excellent', 1, 3.00, 1.50, 0.50, 45.00, 15.00, 12.50, 2.00, 10.50, 'Master Craftsmanship', 'Bezel Setting', 'Magnetic Clasp', 'Silk Cord', 3, 18, 80, 'Standard-Small', 50, 2, DEFAULT, 5, 3, 1, 0, '2025-12-03 17:30:00', 180.00, 450.00, 380.00, 400.00, 'USD', 15.00, 70.00, '2025-12-01', '2025-12-31', 8.00, DEFAULT, 'New season promotion', '2025-12-03 17:30:00', '386813206497718272', '386813206447386624', '386813206459969536', '386813206468358144', '386813206476746752', '386813206489329664', '/api/files/PRODUCDETAIL/2025/12/03/20251203173735069681.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173735056990.jpg', '/api/files/PRODUCDETAIL/2025/12/03/20251203173735059002.webp', '/api/files/PRODUCDETAIL/2025/12/03/20251203173735061035.webp', '/api/files/PRODUCDETAIL/2025/12/03/20251203173735064828.webp', '/api/files/PRODUCDETAIL/2025/12/03/20251203173735066724.jpg', '2025-12-01', '2026-12-01', 1, 1, 1, 1, 0, 100, 1, 1, 1, '2025-12-03 17:30:00', '2025-12-03 17:37:35');

SET FOREIGN_KEY_CHECKS = 1;
