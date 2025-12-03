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

 Date: 03/12/2025 17:19:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_spu
-- ----------------------------
DROP TABLE IF EXISTS `product_spu`;
CREATE TABLE `product_spu`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `spu_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SPU编码',
  `product_name_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '英文产品名称',
  `product_name_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '中文产品名称',
  `product_name_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '阿拉伯语产品名称',
  `short_description_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '英文简短描述',
  `short_description_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文简短描述',
  `short_description_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '阿拉伯语简短描述',
  `full_description_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文详细描述',
  `full_description_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文详细描述',
  `full_description_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语详细描述',
  `design_concept_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文设计理念',
  `design_concept_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文设计理念',
  `design_concept_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语设计理念',
  `intended_usage_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文用途说明',
  `intended_usage_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文用途说明',
  `intended_usage_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语用途说明',
  `emotional_purpose_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文情感目的',
  `emotional_purpose_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文情感目的',
  `emotional_purpose_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语情感目的',
  `spiritual_significance_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文灵性意义',
  `spiritual_significance_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文灵性意义',
  `spiritual_significance_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语灵性意义',
  `energy_properties_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文能量属性',
  `energy_properties_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文能量属性',
  `energy_properties_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语能量属性',
  `primary_element` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主要五行元素',
  `element_combination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '五行组合配置',
  `energy_intensity_default` tinyint NULL DEFAULT 50 COMMENT '默认能量强度(1-100)',
  `material_standards` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '材质标准JSON',
  `craftsmanship_standards` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工艺标准JSON',
  `quality_standards` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '质量标准JSON',
  `production_guidelines_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文生产指南',
  `production_guidelines_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文生产指南',
  `production_guidelines_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语生产指南',
  `variant_definition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '变体定义JSON',
  `customization_options` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '定制选项JSON',
  `production_lead_time` int NULL DEFAULT 0 COMMENT '生产周期(天)',
  `main_image_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图ID',
  `concept_image_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '概念图ID',
  `design_image_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设计图ID',
  `prototype_image_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '原型图ID',
  `usage_image_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '使用场景图ID',
  `technical_image_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '技术图纸ID',
  `main_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图url',
  `concept_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '概念图url',
  `design_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设计图url',
  `prototype_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '原型图url',
  `usage_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '使用场景图url',
  `technical_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '技术图纸url',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序值',
  `is_featured` tinyint(1) NULL DEFAULT 0 COMMENT '是否推荐',
  `is_new_arrival` tinyint(1) NULL DEFAULT 0 COMMENT '是否新品',
  `is_ai_designed` tinyint(1) NULL DEFAULT 0 COMMENT '是否AI设计',
  `ai_design_score` tinyint NULL DEFAULT NULL COMMENT 'AI设计评分(1-100)',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用',
  `meta_title_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '英文SEO标题',
  `meta_title_zh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文SEO标题',
  `meta_title_ar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '阿拉伯语SEO标题',
  `meta_description_en` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '英文SEO描述',
  `meta_description_zh` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '中文SEO描述',
  `meta_description_ar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '阿拉伯语SEO描述',
  `meta_keywords_en` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '英文SEO关键词',
  `meta_keywords_zh` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文SEO关键词',
  `meta_keywords_ar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '阿拉伯语SEO关键词',
  `created_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `updated_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品SPU表(关联图片ID)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_spu
-- ----------------------------
INSERT INTO `product_spu` VALUES ('SPU_HETIAN_BRACELET_001', 'HETIAN_JADE_BRACELET_001', 'Premium Hetian Jade Bracelet - White Nephrite', '精品和田玉手镯 - 羊脂白玉', 'سوار يشب خه تيان المتميز - نيفريت أبيض', 'Genuine Hetian White Jade bracelet with smooth texture and warm luster', '天然和田羊脂白玉手镯，质地细腻，温润光泽', 'سوار يشب خه تيان أبيض أصلي مع ملمس ناعم ولمعان دافئ', 'This premium Hetian Jade bracelet is crafted from authentic white nephrite mined from the Kunlun Mountains. Known for its fine texture, warm luster, and smooth touch, it represents the highest quality of Chinese jade craftsmanship. Each piece is carefully selected and polished to preserve the natural beauty and energy properties of the stone.', '这款精品和田玉手镯采用昆仑山脉开采的天然羊脂白玉精心打造。以其细腻的质地、温润的光泽和光滑的触感而闻名，代表了中国玉器工艺的最高水准。每件作品都经过精心挑选和抛光，以保留玉石的自然美感和能量特性。', 'هذا السوار المتميز من يشب خه تيان مصنوع من النيفريت الأبيض الأصلي المستخرج من جبال كونلون. معروف بملمسه الناعم ولمعانه الدافئ ولمسته الناعمة، يمثل أعلى جودة في حرفة اليشب الصينية. يتم اختيار كل قطعة وتلميعها بعناية للحفاظ على الجمال الطبيعي وخصائص الطاقة للحجر.', 'Inspired by traditional Chinese jade culture, the circular design symbolizes eternity and harmony. The smooth, unadorned surface highlights the natural beauty of the jade, while the perfect circle represents the unity of heaven and earth in Chinese philosophy.', '设计灵感源自中国传统玉文化，圆形设计象征永恒与和谐。光滑无饰的表面凸显了玉石的自然美，而完美的圆形代表了中国哲学中天与地的统一。', 'مستوحى من ثقافة اليشب الصينية التقليدية، يمثل التصميم الدائري الخلود والانسجام. يبرز السطح الأملس غير المزخرف الجمال الطبيعي لليشب، بينما تمثل الدائرة المثالية وحدة السماء والأرض في الفلسفة الصينية.', 'Wear as daily jewelry for spiritual cultivation, meditation companion, or as a meaningful gift for special occasions. Suitable for both casual and formal wear.', '可作为日常灵性修炼首饰、冥想伴侣，或作为特殊场合的寓意礼物。适合休闲和正式场合佩戴。', 'ارتدِ كمجوهرات يومية للتهذيب الروحي، رفيق للتأمل، أو كهدية ذات معنى للمناسبات الخاصة. مناسب للارتداء العادي والرسمي.', 'Promotes emotional stability, inner peace, and spiritual connection. The warm energy of jade helps soothe the mind and enhance compassion.', '促进情绪稳定、内心平静和灵性连接。玉石温暖的能量有助于安抚心灵，增强慈悲心。', 'يعزز الاستقرار العاطفي والسلام الداخلي والاتصال الروحي. تساعد الطاقة الدافئة لليشب على تهدئة العقل وتعزيز التعاطف.', 'In Chinese culture, jade symbolizes virtue, purity, and protection. Believed to carry the wisdom of ancestors and connect the wearer to earth energy.', '在中国文化中，玉象征美德、纯洁和保护。被认为承载着祖先的智慧，并将佩戴者与大地能量连接。', 'في الثقافة الصينية، يرمز اليشب إلى الفضيلة والنقاء والحماية. يُعتقد أنه يحمل حكمة الأسلاف ويصل مرتديه بطاقة الأرض.', 'Hetian jade emits stable earth energy that helps with grounding, protection, and energy balance. The white color enhances clarity and spiritual awareness.', '和田玉发出稳定的大地能量，有助于接地、保护和能量平衡。白色增强清晰度和灵性意识。', 'ينبعث يشب خه تيان طاقة أرضية مستقرة تساعد في التأريض والحماية وتوازن الطاقة. يعزز اللون الأبيض الوضوح والوعي الروحي.', 'Earth', 'Earth,Metal', 85, '{\"material\":\"Hetian White Nephrite\",\"purity\":\"Grade A Natural\",\"origin\":\"Xinjiang, China\",\"density\":2.95,\"hardness\":6.5}', '{\"polishing\":\"Mirror Finish\",\"carving\":\"None\",\"setting\":\"None\",\"quality\":\"Excellent Craftsmanship\"}', '{\"certification\":\"Authentic Hetian Jade\",\"inclusions\":\"Minimal\",\"transparency\":\"Semi-translucent\",\"color\":\"White with slight creamy tone\"}', 'Hand-selected rough jade, shaped by master craftsmen, polished through 12-step process, inspected for quality assurance.', '手工挑选原石，由大师工匠塑形，经过12道抛光工序，质量检验保证。', 'يتم اختيار اليشب الخام يدويًا، تشكيله بواسطة حرفيين مهرة، تلميع من خلال عملية من 12 خطوة، فحص لضمان الجودة.', '{\"variants\":[\"size\",\"color_intensity\"],\"options\":{\"size\":{\"58mm\":\"Small\",\"60mm\":\"Medium\",\"62mm\":\"Large\"},\"color\":{\"light_white\":\"Light White\",\"creamy_white\":\"Creamy White\"}}}', '{\"engraving\":true,\"size_customization\":true,\"gift_packaging\":true}', 21, '386795520908718080', '386795520925495296', '386795520938078208', '386795520946466816', '386795520959049728', '386795520975826944', '/api/files/PRODUCDETAIL/2025/12/03/20251203162718494128.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203162718499067.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203162718502740.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203162718505160.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203162718507249.png', '/api/files/PRODUCDETAIL/2025/12/03/20251203162718510653.jpg', 100, 1, 1, 0, 80, 1, 'Premium Hetian White Jade Bracelet - Authentic Nephrite Jewelry', '精品和田羊脂白玉手镯 - 天然玉石首饰', 'سوار يشب خه تيان أبيض متميز - مجوهرات نيفريت أصلية', 'Authentic Hetian white nephrite jade bracelet. Natural jade from Xinjiang, handcrafted by master artisans. Promotes spiritual balance and protection.', '正宗和田羊脂白玉手镯。新疆天然玉石，大师手工制作。促进灵性平衡与保护。', 'سوار يشب خه تيان أبيض أصلي. يشب طبيعي من شينجيانغ، مصنوع يدويًا بواسطة حرفيين مهرة. يعزز التوازن الروحي والحماية.', 'hetian jade, white nephrite, chinese jade, spiritual jewelry, jade bracelet, natural stone', '和田玉, 羊脂白玉, 中国玉, 灵性首饰, 玉镯, 天然宝石', 'يشب خه تيان, نيفريت أبيض, يشب صيني, مجوهرات روحية, سوار يشب, حجر طبيعي', 1, 1, '2025-12-03 16:23:17', '2025-12-03 16:27:18');

SET FOREIGN_KEY_CHECKS = 1;
