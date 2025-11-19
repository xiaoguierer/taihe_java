DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main` (
                              `id` varchar(50) NOT NULL COMMENT '订单ID，UUID格式，主键',
                              `order_sn` varchar(32) NOT NULL COMMENT '订单编号，业务唯一标识，格式: ORD{年月日}{6位序列号}',
                              `user_id` varchar(50) NOT NULL COMMENT '用户ID，关联用户表',

    -- 订单基本信息
                              `order_type` tinyint NOT NULL DEFAULT '1' COMMENT '订单类型: 1-普通订单, 2-预售订单, 3-定制订单, 4-批发订单',
                              `order_status` tinyint NOT NULL DEFAULT '1' COMMENT '订单状态: 1-待付款, 2-已付款/待发货, 3-已发货, 4-已完成, 5-已关闭, 6-售后中',
                              `payment_status` tinyint NOT NULL DEFAULT '1' COMMENT '支付状态: 1-待支付, 2-支付中, 3-支付成功, 4-支付失败, 5-已退款',
                              `source_channel` varchar(20) DEFAULT 'web' COMMENT '订单来源: web, app, wechat, mini_program, api',

    -- 货币信息
                              `base_currency` varchar(10) NOT NULL DEFAULT 'USD' COMMENT '基准货币，固定为USD，用于内部结算',
                              `display_currency` varchar(10) NOT NULL DEFAULT 'USD' COMMENT '显示货币，用户看到的货币类型',
                              `exchange_rate` decimal(10,6) NOT NULL DEFAULT '1.000000' COMMENT '显示货币对基准货币的汇率',

    -- 金额信息 (所有金额均以基准货币USD存储)
                              `product_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '商品总金额(USD)，不含任何费用',
                              `shipping_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '运费金额(USD)',
                              `tax_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '税费金额(USD)',
                              `insurance_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '保险费金额(USD)',
                              `discount_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '优惠总金额(USD)，包括促销和优惠券',
                              `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '订单总额(USD)，应付金额',
                              `paid_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '已支付金额(USD)',
                              `refund_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '已退款金额(USD)',

    -- 显示金额 (根据汇率计算，用于前端显示)
                              `display_product_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '显示商品金额',
                              `display_total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '显示总金额',

    -- 收货地址信息 (地址快照，防止后续地址修改影响订单)
                              `shipping_address_id` varchar(50) DEFAULT NULL COMMENT '使用的地址ID，关联user_address表',
                              `receiver_name` varchar(100) NOT NULL COMMENT '收货人姓名',
                              `receiver_phone` varchar(50) NOT NULL COMMENT '收货人电话，包含国际区号',
                              `receiver_country` varchar(100) NOT NULL COMMENT '收货国家',
                              `receiver_country_code` varchar(10) NOT NULL COMMENT '国家代码(ISO 3166-1 alpha-2)',
                              `receiver_state` varchar(100) NOT NULL COMMENT '州/省',
                              `receiver_city` varchar(100) NOT NULL COMMENT '城市',
                              `receiver_district` varchar(100) DEFAULT NULL COMMENT '区/县',
                              `receiver_address` varchar(500) NOT NULL COMMENT '详细地址',
                              `receiver_zip_code` varchar(20) DEFAULT NULL COMMENT '邮编',

    -- 物流信息
                              `shipping_method` varchar(50) DEFAULT NULL COMMENT '配送方式代码',
                              `shipping_method_name` varchar(100) DEFAULT NULL COMMENT '配送方式名称',
                              `tracking_number` varchar(100) DEFAULT NULL COMMENT '物流单号',
                              `logistics_company` varchar(100) DEFAULT NULL COMMENT '物流公司',
                              `logistics_company_code` varchar(50) DEFAULT NULL COMMENT '物流公司代码',
                              `estimated_delivery_days` int DEFAULT NULL COMMENT '预计配送天数',

    -- 时间信息
                              `auto_cancel_time` datetime DEFAULT NULL COMMENT '自动取消时间(未付款订单)',
                              `payment_time` datetime DEFAULT NULL COMMENT '支付成功时间',
                              `shipping_time` datetime DEFAULT NULL COMMENT '发货时间',
                              `confirm_time` datetime DEFAULT NULL COMMENT '确认收货时间',
                              `completion_time` datetime DEFAULT NULL COMMENT '订单完成时间',
                              `close_time` datetime DEFAULT NULL COMMENT '订单关闭时间',

    -- 用户信息
                              `user_notes` varchar(500) DEFAULT NULL COMMENT '用户备注/留言',
                              `admin_notes` varchar(500) DEFAULT NULL COMMENT '管理员备注',
                              `ip_address` varchar(45) DEFAULT NULL COMMENT '用户IP地址',
                              `user_agent` varchar(500) DEFAULT NULL COMMENT '用户浏览器信息',

    -- 系统信息
                              `version` int NOT NULL DEFAULT '1' COMMENT '版本号，用于乐观锁',
                              `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除: 0-未删除, 1-已删除',
                              `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_order_sn` (`order_sn`),
                              KEY `idx_user_id` (`user_id`),
                              KEY `idx_order_status` (`order_status`),
                              KEY `idx_payment_status` (`payment_status`),
                              KEY `idx_created_time` (`created_time`),
                              KEY `idx_country_code` (`receiver_country_code`),
                              KEY `idx_tracking_number` (`tracking_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单主表';
