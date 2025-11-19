

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for emotional_intent
-- ----------------------------
DROP TABLE IF EXISTS `emotional_intent`;
CREATE TABLE `emotional_intent` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `intent_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '意图键名',
  `intent_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '意图代码',
  `intent_category` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'primary' COMMENT '意图分类:primary主要,extended扩展,combined组合',
  `intent_name_en` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '英文名称',
  `intent_name_zh` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '中文名称',
  `intent_name_ar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语名称',
  `symbol_character` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '象征字符',
  `symbol_color` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '象征颜色代码',
  `symbol_color_gradient` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '渐变色系',
  `primary_emotion_zh` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主要情感(中文)',
  `primary_emotion_en` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主要情感(英文)',
  `primary_emotion_ar` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主要情感(阿拉伯语)',
  `secondary_emotions_zh` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '次要情感(中文)',
  `secondary_emotions_en` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '次要情感(英文)',
  `secondary_emotions_ar` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '次要情感(阿拉伯语)',
  `emotional_intensity` tinyint DEFAULT '50' COMMENT '情感强度(1-100)',
  `emotional_direction` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '情感方向:inward内向,outward外向,balanced平衡',
  `emotional_frequency` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '情感频率:low低频,medium中频,high高频',
  `philosophy_meaning_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文哲学含义',
  `philosophy_meaning_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文哲学含义',
  `philosophy_meaning_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语哲学含义',
  `cultural_significance_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文文化意义',
  `cultural_significance_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文文化意义',
  `cultural_significance_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语文化意义',
  `spiritual_meaning_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文灵性意义',
  `spiritual_meaning_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文灵性意义',
  `spiritual_meaning_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语灵性意义',
  `modern_interpretation_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文现代诠释',
  `modern_interpretation_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文现代诠释',
  `modern_interpretation_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语现代诠释',
  `personality_archetype_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文人格原型',
  `personality_archetype_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文人格原型',
  `personality_archetype_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语人格原型',
  `life_guidance_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文人生指引',
  `life_guidance_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文人生指引',
  `life_guidance_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语人生指引',
  `healing_property_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文疗愈属性',
  `healing_property_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文疗愈属性',
  `healing_property_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语疗愈属性',
  `relationship_impact_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文关系影响',
  `relationship_impact_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文关系影响',
  `relationship_impact_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语关系影响',
  `career_alignment_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文职业契合',
  `career_alignment_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文职业契合',
  `career_alignment_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语职业契合',
  `icon_id` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标图片Id',
  `symbol_image_id` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '象征图id',
  `energy_image_id` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '能量可视化图id',
  `application_image_id` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用场景图id',
  `meditation_image_id` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冥想引导图URL',
  `meta_title_zh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文SEO标题',
  `meta_title_en` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文SEO标题',
  `meta_title_ar` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语SEO标题',
  `meta_description_zh` text COLLATE utf8mb4_unicode_ci COMMENT '中文SEO描述',
  `meta_description_en` text COLLATE utf8mb4_unicode_ci COMMENT '英文SEO描述',
  `meta_description_ar` text COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语SEO描述',
  `meta_keywords_zh` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文SEO关键词',
  `meta_keywords_en` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文SEO关键词',
  `meta_keywords_ar` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语SEO关键词',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序值',
  `intensity_level` tinyint DEFAULT '50' COMMENT '强度等级(1-100)',
  `popularity_score` int DEFAULT '0' COMMENT '受欢迎度评分',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `show_in_navigation` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在导航显示',
  `show_in_filter` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在筛选器显示',
  `is_featured` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `icon_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标图片url',
  `symbol_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '象征图url',
  `energy_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '能量可视化图url',
  `application_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用场景图url',
  `meditation_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冥想引导图url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='情感意图表(完整多语言支持)';


-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类ID',
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父分类ID，一级分类为0',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `level` tinyint NOT NULL COMMENT '分类层级(1-3)',
  `sort` int DEFAULT '0' COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '描述',
  `keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关键字',
  `is_display` tinyint(1) DEFAULT '1' COMMENT '是否显示(0-不显示，1-显示)',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `nikename` varchar(255) CHARACTER SET utf32 COLLATE utf32_general_ci NOT NULL COMMENT '俗称',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE,
  KEY `idx_level` (`level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品分类表';

-- ----------------------------
-- Table structure for product_categorytag
-- ----------------------------
DROP TABLE IF EXISTS `product_categorytag`;
CREATE TABLE `product_categorytag` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `tag_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签键名: necklace, ring, 18k_gold',
  `tag_name_en` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '英文标签名',
  `tag_name_zh` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '中文标签名',
  `tag_name_ar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语标签名',
  `description_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文详细描述',
  `description_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文详细描述',
  `description_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语详细描述',
  `short_desc_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文简短描述',
  `short_desc_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文简短描述',
  `short_desc_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语简短描述',
  `icon_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标访问id',
  `icon_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标url',
  `cover_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面图访问URL',
  `cover_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面图ID',
  `hover_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '悬停图访问URL',
  `hover_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '悬停图id',
  `tag_type` tinyint NOT NULL COMMENT '标签类型',
  `parent_tag_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父标签ID',
  `color_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签代表色(用于UI)',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序值',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `show_in_filter` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在筛选器显示',
  `show_in_navigation` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否在导航显示',
  `meta_title_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文SEO标题',
  `meta_title_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文SEO标题',
  `meta_title_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语SEO标题',
  `meta_description_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文SEO描述',
  `meta_description_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文SEO描述',
  `meta_description_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语SEO描述',
  `meta_keywords_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文SEO关键词',
  `meta_keywords_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文SEO关键词',
  `meta_keywords_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语SEO关键词',
  `created_by` bigint NOT NULL COMMENT '创建人ID',
  `updated_by` bigint NOT NULL COMMENT '更新人ID',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品品类标签表';

-- ----------------------------
-- Table structure for product_image
-- ----------------------------
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `absolute_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '绝对URL(CDN地址)',
  `relative_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '相对路径',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名',
  `file_size` int NOT NULL COMMENT '文件大小(字节)',
  `mime_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件类型',
  `image_alt_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文替代文本',
  `image_alt_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文替代文本',
  `image_alt_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语替代文本',
  `image_title_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文标题',
  `image_title_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文标题',
  `image_title_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语标题',
  `sort_order` int DEFAULT '0' COMMENT '排序值',
  `is_primary` tinyint(1) DEFAULT '0' COMMENT '是否主图',
  `is_active` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `created_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品图片表(简化版)';

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `spu_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SPU ID',
  `sku_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SKU编码',
  `sku_name_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文SKU名称',
  `sku_name_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文SKU名称',
  `sku_name_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语SKU名称',
  `variant_description_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文变体描述',
  `variant_description_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文变体描述',
  `variant_description_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语变体描述',
  `variant_yuyi_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '寓意描述',
  `variant_yuyi_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '寓意描述',
  `variant_yuyi_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '寓意描述',
  `primary_material` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主要材质',
  `material_purity` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '材质纯度',
  `material_color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '材质颜色',
  `material_finish` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表面处理',
  `material_thickness_mm` decimal(4,2) DEFAULT NULL COMMENT '材质厚度(mm)',
  `gemstone_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宝石类型',
  `gemstone_shape` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宝石形状',
  `gemstone_cut` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宝石切工',
  `gemstone_size_mm` decimal(5,2) DEFAULT NULL COMMENT '宝石尺寸(mm)',
  `gemstone_weight_ct` decimal(6,3) DEFAULT NULL COMMENT '宝石重量(克拉)',
  `gemstone_quality` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宝石品质',
  `gemstone_count` int DEFAULT '0' COMMENT '宝石数量',
  `length_cm` decimal(6,2) DEFAULT NULL COMMENT '长度(cm)',
  `width_cm` decimal(6,2) DEFAULT NULL COMMENT '宽度(cm)',
  `height_cm` decimal(6,2) DEFAULT NULL COMMENT '高度(cm)',
  `chain_length_cm` decimal(6,2) DEFAULT NULL COMMENT '链长(cm)',
  `pendant_size_mm` decimal(6,2) DEFAULT NULL COMMENT '吊坠尺寸(mm)',
  `total_weight_g` decimal(8,2) DEFAULT NULL COMMENT '总重量(克)',
  `metal_weight_g` decimal(8,2) DEFAULT NULL COMMENT '金属重量(克)',
  `gemstone_weight_g` decimal(8,2) DEFAULT NULL COMMENT '宝石重量(克)',
  `craftsmanship_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工艺等级',
  `setting_technique` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '镶嵌工艺',
  `clasp_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扣类型',
  `chain_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '链类型',
  `suitable_gender` int DEFAULT '1' COMMENT '适用性别:1 男性,2 女性,3 中性',
  `suitable_age_min` int DEFAULT NULL COMMENT '适用最小年龄',
  `suitable_age_max` int DEFAULT NULL COMMENT '适用最大年龄',
  `size_standard` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '尺寸标准',
  `stock_quantity` int DEFAULT '0' COMMENT '库存数量',
  `reserved_quantity` int DEFAULT '0' COMMENT '预留库存',
  `available_quantity` int GENERATED ALWAYS AS ((`stock_quantity` - `reserved_quantity`)) VIRTUAL COMMENT '可用库存',
  `safety_stock` int DEFAULT '5' COMMENT '安全库存',
  `reorder_point` int DEFAULT '3' COMMENT '补货点',
  `stock_status` int DEFAULT '1' COMMENT '库存状态: 1有货,2 低库存,3 缺货',
  `low_stock_alert` tinyint(1) DEFAULT '0' COMMENT '低库存预警',
  `last_stock_update` datetime DEFAULT NULL COMMENT '最后库存更新',
  `cost_price` decimal(10,2) DEFAULT NULL COMMENT '成本价',
  `retail_price` decimal(10,2) DEFAULT NULL COMMENT '零售价',
  `sale_price` decimal(10,2) DEFAULT NULL COMMENT '促销价',
  `member_price` decimal(10,2) DEFAULT NULL COMMENT '会员价',
  `price_currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'USD' COMMENT '价格货币',
  `discount_rate` decimal(5,2) DEFAULT '0.00' COMMENT '折扣率(%)',
  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '折扣金额',
  `discount_start_date` date DEFAULT NULL COMMENT '折扣开始日期',
  `discount_end_date` date DEFAULT NULL COMMENT '折扣结束日期',
  `tax_rate` decimal(5,2) DEFAULT '0.00' COMMENT '税率(%)',
  `final_price` decimal(10,2) GENERATED ALWAYS AS ((coalesce(`sale_price`,`retail_price`) * (1 - (`discount_rate` / 100)))) VIRTUAL COMMENT '最终售价',
  `price_adjustment_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '价格调整原因',
  `last_price_update` datetime DEFAULT NULL COMMENT '最后价格更新',
  `main_image_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图ID',
  `image_1_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片1 ID',
  `image_2_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片2 ID',
  `image_3_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片3 ID',
  `image_4_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片4 ID',
  `image_5_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片5 ID',
  `main_image_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图url',
  `image_1_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片1 url',
  `image_2_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片2 url',
  `image_3_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片3 url',
  `image_4_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片4 url',
  `image_5_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片5 url',
  `available_date` date DEFAULT NULL COMMENT '可售开始日期',
  `available_end_date` date DEFAULT NULL COMMENT '可售结束日期',
  `is_new_arrival` tinyint(1) DEFAULT '0' COMMENT '是否新品',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态:0 草稿,1 上架,2 下架',
  `is_available` tinyint(1) DEFAULT '1' COMMENT '是否可售',
  `is_featured` tinyint(1) DEFAULT '0' COMMENT '是否推荐',
  `is_best_seller` tinyint(1) DEFAULT '0' COMMENT '是否畅销',
  `sort_order` int DEFAULT '0' COMMENT '排序值',
  `visibility` tinyint(1) DEFAULT '1' COMMENT '可见性:1 公开,2 隐藏',
  `created_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品SKU表(关联图片ID)';

-- ----------------------------
-- Table structure for product_spu
-- ----------------------------
DROP TABLE IF EXISTS `product_spu`;
CREATE TABLE `product_spu` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `spu_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SPU编码',
  `product_name_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '英文产品名称',
  `product_name_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '中文产品名称',
  `product_name_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语产品名称',
  `short_description_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文简短描述',
  `short_description_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文简短描述',
  `short_description_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语简短描述',
  `full_description_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文详细描述',
  `full_description_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文详细描述',
  `full_description_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语详细描述',
  `design_concept_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文设计理念',
  `design_concept_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文设计理念',
  `design_concept_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语设计理念',
  `intended_usage_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文用途说明',
  `intended_usage_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文用途说明',
  `intended_usage_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语用途说明',
  `emotional_purpose_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文情感目的',
  `emotional_purpose_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文情感目的',
  `emotional_purpose_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语情感目的',
  `spiritual_significance_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文灵性意义',
  `spiritual_significance_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文灵性意义',
  `spiritual_significance_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语灵性意义',
  `energy_properties_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文能量属性',
  `energy_properties_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文能量属性',
  `energy_properties_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语能量属性',
  `primary_element` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主要五行元素',
  `element_combination` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '五行组合配置',
  `energy_intensity_default` tinyint DEFAULT '50' COMMENT '默认能量强度(1-100)',
  `material_standards` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '材质标准JSON',
  `craftsmanship_standards` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工艺标准JSON',
  `quality_standards` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '质量标准JSON',
  `production_guidelines_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文生产指南',
  `production_guidelines_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文生产指南',
  `production_guidelines_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语生产指南',
  `variant_definition` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '变体定义JSON',
  `customization_options` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '定制选项JSON',
  `production_lead_time` int DEFAULT '0' COMMENT '生产周期(天)',
  `main_image_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图ID',
  `concept_image_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '概念图ID',
  `design_image_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设计图ID',
  `prototype_image_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原型图ID',
  `usage_image_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用场景图ID',
  `technical_image_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '技术图纸ID',
  `main_image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主图url',
  `concept_image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '概念图url',
  `design_image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设计图url',
  `prototype_image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原型图url',
  `usage_image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用场景图url',
  `technical_image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '技术图纸url',
  `sort_order` int DEFAULT '0' COMMENT '排序值',
  `is_featured` tinyint(1) DEFAULT '0' COMMENT '是否推荐',
  `is_new_arrival` tinyint(1) DEFAULT '0' COMMENT '是否新品',
  `is_ai_designed` tinyint(1) DEFAULT '0' COMMENT '是否AI设计',
  `ai_design_score` tinyint DEFAULT NULL COMMENT 'AI设计评分(1-100)',
  `is_active` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `meta_title_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文SEO标题',
  `meta_title_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文SEO标题',
  `meta_title_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语SEO标题',
  `meta_description_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '英文SEO描述',
  `meta_description_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '中文SEO描述',
  `meta_description_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '阿拉伯语SEO描述',
  `meta_keywords_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '英文SEO关键词',
  `meta_keywords_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中文SEO关键词',
  `meta_keywords_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '阿拉伯语SEO关键词',
  `created_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `updated_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品SPU表(关联图片ID)';

-- ----------------------------
-- Table structure for product_spu_categorytag
-- ----------------------------
DROP TABLE IF EXISTS `product_spu_categorytag`;
CREATE TABLE `product_spu_categorytag` (
  `id` varchar(50) NOT NULL,
  `spu_id` varchar(255) NOT NULL COMMENT 'SPU ID',
  `category_tag_id` varchar(255) NOT NULL COMMENT '品类标签ID',
  `is_primary` tinyint(1) DEFAULT '0' COMMENT '是否主要标签',
  `sort_order` int DEFAULT '0' COMMENT '排序值',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SPU-品类标签关系表';

-- ----------------------------
-- Table structure for product_spu_intent
-- ----------------------------
DROP TABLE IF EXISTS `product_spu_intent`;
CREATE TABLE `product_spu_intent` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID，UUID格式',
  `spu_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SPU ID，关联product_spu表',
  `intent_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '情感意图主键，关联emotional_intent表',
  `association_level` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'primary' COMMENT '关联级别：primary:主要;secondary:次要',
  `custom_message_zh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品特定的情感描述（中文）',
  `sort_order` int DEFAULT '0' COMMENT '排序值，数值越小越靠前',
  `is_active` tinyint(1) DEFAULT '1' COMMENT '是否启用：1启用/0停用',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_spu_intent` (`spu_id`,`intent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='SPU与情感意图关联关系表';

-- ----------------------------
-- Table structure for product_spu_sku_rel
-- ----------------------------
DROP TABLE IF EXISTS `product_spu_sku_rel`;
CREATE TABLE `product_spu_sku_rel` (
  `id` varchar(50) NOT NULL COMMENT '主键ID',
  `spu_id` varchar(50) NOT NULL COMMENT 'SPU ID',
  `sku_id` varchar(50) NOT NULL COMMENT 'SKU ID',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认SKU',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序值',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_spu_sku` (`spu_id`,`sku_id`),
  KEY `idx_spu_id` (`spu_id`),
  KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SPU-SKU关联关系表';

-- ----------------------------
-- Table structure for product_spu_supplier
-- ----------------------------
DROP TABLE IF EXISTS `product_spu_supplier`;
CREATE TABLE `product_spu_supplier` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `spu_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SPU ID',
  `supplier_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '供应商ID',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_spu_supplier` (`spu_id`,`supplier_id`),
  KEY `idx_spu_id` (`spu_id`),
  KEY `idx_supplier_id` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='SPU与供应商关联关系表';

-- ----------------------------
-- Table structure for product_spu_wuxing
-- ----------------------------
DROP TABLE IF EXISTS `product_spu_wuxing`;
CREATE TABLE `product_spu_wuxing` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `spu_id` varchar(50) NOT NULL COMMENT 'SPU ID',
  `wu_xing_id` varchar(50) NOT NULL COMMENT '五行属性ID',
  `element_strength` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '50' COMMENT '元素强度1-100',
  `is_primary` tinyint(1) DEFAULT '0' COMMENT '是否主要元素',
  `sort_order` int DEFAULT '0' COMMENT '排序值',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SPU-五行关系表';

-- ----------------------------
-- Table structure for suppliers
-- ----------------------------
DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE `suppliers` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '程序生成的UUID主键',
  `supplier_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '供应商编码(如SUP20230001)',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '供应商全称',
  `short_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '供应商简称',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态:1-启用,0-禁用',
  `supplier_level` tinyint NOT NULL DEFAULT '3' COMMENT '供应商等级:1-战略,2-主力,3-一般,4-备选',
  `company_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '企业类型:有限责任公司/个体工商户等',
  `unified_credit_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '统一社会信用代码',
  `legal_person` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '法人代表',
  `registered_capital` decimal(15,2) DEFAULT NULL COMMENT '注册资本(万元)',
  `registered_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '注册地址',
  `business_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '实际经营地址',
  `establish_date` date DEFAULT NULL COMMENT '成立日期',
  `business_scope` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '经营范围',
  `main_categories` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主营品类(逗号分隔的品类名称)',
  `contact_person` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务联系人',
  `contact_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系电话',
  `contact_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '官网网址',
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '开户银行',
  `bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '银行账号',
  `bank_account_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '开户名',
  `tax_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '纳税类型:一般纳税人/小规模纳税人等',
  `tax_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '纳税人识别号',
  `invoice_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发票类型:增值税专用/普通发票等',
  `settlement_cycle` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '月结30天' COMMENT '结算周期',
  `currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CNY' COMMENT '结算货币',
  `min_order_amount` decimal(12,2) DEFAULT NULL COMMENT '最小订单金额',
  `min_order_quantity` int DEFAULT NULL COMMENT '最小订单数量',
  `lead_time` int DEFAULT NULL COMMENT '交货周期(天)',
  `cooperation_start_date` date NOT NULL COMMENT '合作开始日期',
  `cooperation_end_date` date DEFAULT NULL COMMENT '合作结束日期',
  `contract_expire_date` date DEFAULT NULL COMMENT '合同到期日',
  `performance_score` decimal(3,2) DEFAULT '5.00' COMMENT '绩效评分(0-5分)',
  `quality_score` decimal(3,2) DEFAULT '5.00' COMMENT '质量评分',
  `delivery_score` decimal(3,2) DEFAULT '5.00' COMMENT '交货评分',
  `service_score` decimal(3,2) DEFAULT '5.00' COMMENT '服务评分',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_supplier_code` (`supplier_code`) USING BTREE,
  UNIQUE KEY `uk_unified_credit_code` (`unified_credit_code`) USING BTREE,
  KEY `idx_supplier_name` (`name`) USING BTREE,
  KEY `idx_supplier_status` (`status`) USING BTREE,
  KEY `idx_supplier_level` (`supplier_level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='供应商主表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱(唯一登录账号)',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'PBKDF2加密后的密码',
  `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码盐值',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '新用户' COMMENT '用户昵称',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像URL',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态(0-禁用 1-正常)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `avatar_path` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像相对路径（存储在文件系统中的路径）',
  `avatar_original_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原始头像文件名',
  `avatar_size` bigint DEFAULT NULL COMMENT '头像文件大小（字节）',
  `avatar_content_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像文件类型（MIME类型）',
  `birthdaytime` datetime DEFAULT NULL COMMENT '出生时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_user_avatar_path` (`avatar_path`),
  KEY `idx_user_avatar_content_type` (`avatar_content_type`),
  KEY `idx_user_status` (`status`),
  KEY `idx_user_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户主表';

-- ----------------------------
-- Table structure for wu_xing_attribute
-- ----------------------------
DROP TABLE IF EXISTS `wu_xing_attribute`;
CREATE TABLE `wu_xing_attribute` (
  `id` varchar(50) NOT NULL COMMENT '主键ID',
  `element_key` varchar(20) NOT NULL COMMENT '五行元素键名',
  `element_code` varchar(10) NOT NULL COMMENT '五行代码',
  `element_category` varchar(20) NOT NULL COMMENT '元素分类:basic五行,extended扩展,combined组合',
  `element_name_en` varchar(50) NOT NULL COMMENT '英文名称',
  `element_name_zh` varchar(50) NOT NULL COMMENT '中文名称',
  `element_name_ar` varchar(50) DEFAULT NULL COMMENT '阿拉伯语名称',
  `symbol_character` varchar(10) NOT NULL COMMENT '象征字符',
  `symbol_color` varchar(20) NOT NULL COMMENT '象征颜色代码',
  `symbol_color_gradient` varchar(100) DEFAULT NULL COMMENT '渐变色系',
  `symbol_direction` varchar(20) DEFAULT NULL COMMENT '象征方位',
  `symbol_season` varchar(20) DEFAULT NULL COMMENT '象征季节',
  `symbol_time` varchar(20) DEFAULT NULL COMMENT '象征时辰',
  `symbol_planet` varchar(20) DEFAULT NULL COMMENT '象征行星',
  `symbol_weather` varchar(20) DEFAULT NULL COMMENT '象征气候',
  `symbol_landform` varchar(20) DEFAULT NULL COMMENT '象征地貌',
  `symbol_animal` varchar(20) DEFAULT NULL COMMENT '象征动物',
  `symbol_plant` varchar(20) DEFAULT NULL COMMENT '象征植物',
  `symbol_organ` varchar(20) DEFAULT NULL COMMENT '象征脏腑',
  `symbol_sense` varchar(20) DEFAULT NULL COMMENT '象征感官',
  `symbol_tissue` varchar(20) DEFAULT NULL COMMENT '象征组织',
  `symbol_emotion` varchar(20) DEFAULT NULL COMMENT '象征情绪',
  `symbol_sound` varchar(20) DEFAULT NULL COMMENT '象征声音',
  `symbol_taste` varchar(20) DEFAULT NULL COMMENT '象征味道',
  `symbol_virtue` varchar(20) DEFAULT NULL COMMENT '象征品德',
  `symbol_career` varchar(20) DEFAULT NULL COMMENT '象征职业',
  `symbol_number` tinyint DEFAULT NULL COMMENT '象征数字',
  `symbol_music` varchar(20) DEFAULT NULL COMMENT '象征音律',
  `philosophy_meaning_zh` text COMMENT '中文哲学本义',
  `philosophy_meaning_en` text COMMENT '英文哲学本义',
  `cosmology_meaning_zh` text COMMENT '中文宇宙观含义',
  `cosmology_meaning_en` text COMMENT '英文宇宙观含义',
  `life_philosophy_zh` text COMMENT '中文生命哲学',
  `life_philosophy_en` text COMMENT '英文生命哲学',
  `change_principle_zh` text COMMENT '中文变化法则',
  `change_principle_en` text COMMENT '英文变化法则',
  `cultural_symbolism_zh` text COMMENT '中文文化象征',
  `cultural_symbolism_en` text COMMENT '英文文化象征',
  `mythological_association_zh` text COMMENT '中文神话关联',
  `mythological_association_en` text COMMENT '英文神话关联',
  `historical_reference_zh` text COMMENT '中文历史典故',
  `historical_reference_en` text COMMENT '英文历史典故',
  `artistic_expression_zh` text COMMENT '中文艺术表现',
  `artistic_expression_en` text COMMENT '英文艺术表现',
  `energy_essence_zh` text COMMENT '中文能量本质',
  `energy_essence_en` text COMMENT '英文能量本质',
  `energy_manifestation_zh` text COMMENT '中文能量显现',
  `energy_manifestation_en` text COMMENT '英文能量显现',
  `spiritual_meaning_zh` text COMMENT '中文灵性意义',
  `spiritual_meaning_en` text COMMENT '英文灵性意义',
  `personality_archetype_zh` text COMMENT '中文人格原型',
  `personality_archetype_en` text COMMENT '英文人格原型',
  `life_guidance_zh` text COMMENT '中文人生指引',
  `life_guidance_en` text COMMENT '英文人生指引',
  `healing_property_zh` text COMMENT '中文疗愈属性',
  `healing_property_en` text COMMENT '英文疗愈属性',
  `generates_element` varchar(20) DEFAULT NULL COMMENT '相生元素',
  `generated_by_element` varchar(20) DEFAULT NULL COMMENT '被生元素',
  `restrains_element` varchar(20) DEFAULT NULL COMMENT '相克元素',
  `restrained_by_element` varchar(20) DEFAULT NULL COMMENT '被克元素',
  `relationship_meaning_zh` text COMMENT '中文关系意义',
  `relationship_meaning_en` text COMMENT '英文关系意义',
  `symbol_icon_url` varchar(500) DEFAULT NULL COMMENT '象征图标URL',
  `philosophy_image_url` varchar(500) DEFAULT NULL COMMENT '哲学图解URL',
  `energy_flow_image_url` varchar(500) DEFAULT NULL COMMENT '能量流动图URL',
  `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序值',
  `element_tier` tinyint NOT NULL DEFAULT '1' COMMENT '元素层级',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `symbol_icon_id` varchar(50) DEFAULT NULL COMMENT '象征图标ID（用于UI展示的小图标）',
  `philosophy_image_id` varchar(50) DEFAULT NULL COMMENT '哲学原理图ID（五行相生相克关系图）',
  `energy_flow_image_id` varchar(50) DEFAULT NULL COMMENT '能量流动图ID（能量流动示意图）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='五行哲学文化属性表(完整版)';

SET FOREIGN_KEY_CHECKS = 1;

-- SKU与供应商关联表
DROP TABLE IF EXISTS `product_sku_supplier`;
CREATE TABLE `product_sku_supplier` (
                                        `id` varchar(50) NOT NULL COMMENT '主键ID',
                                        `sku_id` varchar(50) NOT NULL COMMENT 'SKU ID',
                                        `supplier_id` varchar(50) NOT NULL COMMENT '供应商ID',
                                        `sort_order` int DEFAULT 0 COMMENT '排序值',
                                        `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `uk_sku_supplier` (`sku_id`, `supplier_id`),
                                        KEY `idx_sku_id` (`sku_id`),
                                        KEY `idx_supplier_id` (`supplier_id`)
) COMMENT='SKU与供应商关联关系表';
