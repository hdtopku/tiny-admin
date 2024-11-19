# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20077
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 0.0.0.0 (MySQL 8.0.40)
# 数据库: tiny_admin
# 生成时间: 2024-11-19 16:40:48 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 pms_brand
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pms_brand`;

CREATE TABLE `pms_brand` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary key ID',
  `brand_name` varchar(64) DEFAULT NULL COMMENT 'Brand name',
  `brand_desc` varchar(256) DEFAULT NULL COMMENT 'Brand description',
  `logo` varchar(256) DEFAULT NULL COMMENT 'Brand logo',
  `link_to` varchar(256) DEFAULT NULL COMMENT 'Link to',
  `sort` int DEFAULT '1' COMMENT 'Sort order (higher values appear later)',
  `enabled` tinyint(1) DEFAULT '1' COMMENT 'Brand status (0: disabled, 1: enabled)',
  `brand_story` text COMMENT 'Brand story',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Brand table';

LOCK TABLES `pms_brand` WRITE;
/*!40000 ALTER TABLE `pms_brand` DISABLE KEYS */;

INSERT INTO `pms_brand` (`id`, `brand_name`, `brand_desc`, `logo`, `link_to`, `sort`, `enabled`, `brand_story`, `create_time`, `update_time`)
VALUES
	('1','Huawei','Huawei’s vision and mission is to bring the digital world to everyone, every home, and every organization for a fully connected, intelligent world.','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfFu0Tpk1H3KkolBdwWgl7pUsImrPu5kh5Ig&s',NULL,1,1,'Founded in 1987, Huawei is a leading global provider of information and communications technology (ICT) infrastructure and smart devices. With 207,000 employees across more than 170 countries and regions, we serve over three billion people worldwide.\n\nHuawei is committed to bringing the digital world to everyone, every home, and every organization for a fully connected, intelligent world: making ubiquitous connectivity a basic right, a foundation for an intelligent world; providing diversified computing power, making cloud services accessible everywhere, and intelligent technologies pervasive; making industries and organizations agile, efficient, and dynamic through robust digital platforms; redefining user experiences through AI, offering consumers ultimate personalized and intelligent experiences in scenarios like home, travel, work, entertainment, sports, and health.\n\nFounded in 1987, Huawei is a leading global provider of information and communications technology (ICT) infrastructure and smart devices. With 207,000 employees across more than 170 countries and regions, we serve over three billion people worldwide.\n\nHuawei is committed to bringing the digital world to everyone, every home, and every organization for a fully connected, intelligent world: making ubiquitous connectivity a basic right, a foundation for an intelligent world; providing diversified computing power, making cloud services accessible everywhere, and intelligent technologies pervasive; making industries and organizations agile, efficient, and dynamic through robust digital platforms; redefining user experiences through AI, offering consumers ultimate personalized and intelligent experiences in scenarios like home, travel, work, entertainment, sports, and health.','2024-09-24 18:32:56','2024-11-19 09:30:21'),
	('1835771562238705666','Nike','Nike is a global leader in sports and wellness products.','https://www.sportspromedia.com/wp-content/uploads/2023/03/Nike.jpeg',NULL,1,1,'Nike, Inc., a world-renowned manufacturer of sports goods, is headquartered in Portland, Oregon, USA. The company produces a wide range of sports products, including apparel, footwear, and sporting equipment. The name “Nike” is derived from the Greek goddess of victory, and the iconic swoosh logo symbolizes the company’s commitment to inspiring athletes around the world and providing them with the best possible products.','2024-09-24 18:32:56','2024-11-19 09:20:59'),
	('1835780815108210690','Tiny Admin','An enterprise-level back-office management system.','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/f085681d-d8db-49ab-861a-c0502936a39b.png?alt=media',NULL,1,1,'Tiny Admin is a low-code platform based on BPM! It features a front-end and back-end separated architecture using SpringBoot 3.x, Ant Design & Vue 3, Mybatis-plus, Shiro, and JWT, with support for microservices.','2024-09-24 18:32:56','2024-09-24 18:33:39'),
	('1835788859732967425','Adidas','Adidas is one of the world’s leading sports goods manufacturers, headquartered in Germany.','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/630bacd9-ab20-477f-875d-36fdb98ef8f9.png?alt=media',NULL,1,1,'Adidas (in German: adidas) is a German sports goods manufacturer and a member company of Adidas AG. It was named after its founder, Adolf “Adi” Dassler. The company’s predecessor was the “Dassler Brothers Shoe Factory,” established by brothers Rudolf and Adi Dassler in 1924 in Herzogenaurach, near Nuremberg, where they began producing footwear.','2024-09-24 18:32:56','2024-10-30 04:26:36'),
	('2','Xiaomi','Always believe something wonderful is about to happen.','https://upload.wikimedia.org/wikipedia/commons/a/ae/Xiaomi_logo_%282021-%29.svg',NULL,1,1,'Founded in April 2010 and listed on the Main Board of the Hong Kong Stock Exchange (1810.HK) on July 9, 2018, Xiaomi Corporation is a consumer electronics and smart manufacturing company focused on smartphones, smart hardware, and IoT platforms.\n\nWith a vision to “be friends with our users and be the coolest company in their hearts,” Xiaomi continuously innovates to deliver excellent product experiences and operational efficiency. The company’s mission is to “relentlessly pursue high-quality products that touch people’s hearts and are offered at honest prices, so everyone in the world can enjoy a better life through innovative technology.”','2024-09-24 18:32:56','2024-09-24 18:33:42');

/*!40000 ALTER TABLE `pms_brand` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 pms_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pms_product`;

CREATE TABLE `pms_product` (
  `id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `product_name` varchar(64) DEFAULT NULL COMMENT 'Product Name',
  `product_desc` varchar(256) DEFAULT NULL COMMENT 'Product Description',
  `album` varchar(2048) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'Product Image Album (up to 10 images, separated by commas)',
  `sort` int DEFAULT '99999' COMMENT 'Sorting Order (Higher values rank later)',
  `sale` int DEFAULT NULL COMMENT 'Sales Volume',
  `stock` int DEFAULT NULL COMMENT 'Stock Quantity',
  `low_stock` int DEFAULT NULL COMMENT 'Stock Alert Threshold',
  `market_price` decimal(10,2) DEFAULT NULL COMMENT 'Market Price',
  `sale_price` decimal(10,2) DEFAULT NULL COMMENT 'Sale Price',
  `enabled` tinyint(1) DEFAULT '1' COMMENT 'Enabled Status (0: Disabled, 1: Enabled)',
  `detail_html` text COMMENT 'Product Detail HTML Content',
  `brand_id` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'Brand ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last Update Time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Product Table';

LOCK TABLES `pms_product` WRITE;
/*!40000 ALTER TABLE `pms_product` DISABLE KEYS */;

INSERT INTO `pms_product` (`id`, `product_name`, `product_desc`, `album`, `sort`, `sale`, `stock`, `low_stock`, `market_price`, `sale_price`, `enabled`, `detail_html`, `brand_id`, `create_time`, `update_time`)
VALUES
	('1857845087657029634','SAMSUNG Galaxy A35 5G A Series Cell Phone','SAMSUNG Galaxy A35 5G A Series Cell Phone, 128GB Unlocked Android Smartphone, AMOLED Display, Advanced Triple Camera System, Expandable Storage, Rugged Design, US Version, 2024, Awesome Navy','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/c82918ab-9cf2-4711-b239-b68edc5a48fc.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b71a3c3e-a0f9-4577-8e2b-1fe705fb6f50.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/62d4557b-b4bb-4157-86b4-f07ea295adc2.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b040e6b3-960a-4a6b-a528-ee1cd3307b4c.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/58ae3342-2b3f-4c29-9290-82fe2f19c824.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/d57cc23a-da07-497c-8f75-39b215ad1de9.jpg?alt=media',99999,NULL,9999,10,299.99,201.84,1,'<p><br></p><h2><strong>From the manufacturer</strong></h2><p><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/d9ae9933-a5d8-4b86-9b51-eb8745f77f19.__CR0,0,3050,1250_PT0_SX1464_V1___.jpg\" alt=\"Samsung Galaxy A35 5G\"/></p><p><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/c15195f2-b54f-4ec6-beef-d791a402395d.__CR0,0,3050,1250_PT0_SX1464_V1___.jpg\" alt=\"Samsung Galaxy A35 5G\"/></p><h2><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/547fbbfc-d279-4523-9f1d-1add3cb8d50a.__CR0,0,3050,1250_PT0_SX1464_V1___.jpg\" alt=\"Samsung Galaxy A35 5G\"/></h2><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/f17ac0c6-c5ca-4d3f-b1c7-a859d8a44b1f.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/1ab5ecc7-c738-42df-aeef-d69d01468202.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/31a04956-b9f1-4b07-ac69-6f069a058be4.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b8768609-495f-489d-aec4-72a4f0103884.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/901c8e26-87ac-4e38-9910-ef812f59f74e.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b196e449-c872-4b45-813f-8661ef1b06d5.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/33b8f1a4-68fd-4537-a85f-8feafba01d0e.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/060fea32-2917-41f7-b4b0-11780b83f616.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/9ceb363e-7bc4-497e-9646-54425a13eda8.png?alt=media\" alt=\"\"/></p><p><br></p><p><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/ac91c8f2-53da-4dbc-b78f-2fa16dcc82dc.__CR0,0,1464,625_PT0_SX1464_V1___.jpg\" alt=\"brand story_gray\"/></p>',NULL,'2024-11-17 01:56:00','2024-11-17 02:12:48');

/*!40000 ALTER TABLE `pms_product` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_banner
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_banner`;

CREATE TABLE `sms_banner` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `banner_name` varchar(64) DEFAULT NULL COMMENT 'Banner Card Name',
  `pic_url` varchar(256) DEFAULT NULL COMMENT 'Image URL',
  `platform` int DEFAULT '1' COMMENT 'Banner Platform (1:PC, 2:APP)',
  `link_to` varchar(256) DEFAULT NULL COMMENT 'Link To',
  `remark` varchar(256) DEFAULT NULL COMMENT 'Remark',
  `sort` int DEFAULT '9999' COMMENT 'Sort Order (Higher value appears later)',
  `enabled` tinyint(1) DEFAULT '1' COMMENT 'Status (0:Disabled, 1:Enabled)',
  `click_count` int DEFAULT '0' COMMENT 'Click Count',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last Update Time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='Banner Table';

LOCK TABLES `sms_banner` WRITE;
/*!40000 ALTER TABLE `sms_banner` DISABLE KEYS */;

INSERT INTO `sms_banner` (`id`, `banner_name`, `pic_url`, `platform`, `link_to`, `remark`, `sort`, `enabled`, `click_count`, `create_time`, `update_time`)
VALUES
	('1838308281244774401','OPPO Reno8 New Product Promotion','http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20221108/oppo_banner_01.png',1,'https://www.oppo.com/uk','Oppo Double 11 Big Sale, 5% Off Storewide',9998,1,0,'2024-09-23 21:03:43','2024-11-19 22:16:28');

/*!40000 ALTER TABLE `sms_banner` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_brand
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_brand`;

CREATE TABLE `sms_brand` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `brand_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Brand ID',
  `sort` int DEFAULT '9999' COMMENT 'Sort Order (Higher value appears later)',
  `remark` varchar(256) DEFAULT NULL COMMENT 'Brand Name',
  `enabled` tinyint(1) DEFAULT '1' COMMENT 'Status (0:Disabled, 1:Enabled)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last Update Time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='Brand Recommendation Table';

LOCK TABLES `sms_brand` WRITE;
/*!40000 ALTER TABLE `sms_brand` DISABLE KEYS */;

INSERT INTO `sms_brand` (`id`, `brand_id`, `sort`, `remark`, `enabled`, `create_time`, `update_time`)
VALUES
	('1839780146333114370','1',9999,'Huawei',1,'2024-09-27 22:32:23','2024-11-19 22:40:35'),
	('1858884003111325697','1835780815108210690',9999,'',1,'2024-11-19 14:44:18','2024-11-19 22:44:17'),
	('42531','2',9999,'',1,'2024-09-24 17:45:23','2024-09-24 18:41:17'),
	('8028302','1835771562238705666',9999,'Nike Double 11 Sale, 5% off storewide',1,'2024-09-24 17:45:38','2024-11-19 22:44:00');

/*!40000 ALTER TABLE `sms_brand` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_flash_goods_rel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_flash_goods_rel`;

CREATE TABLE `sms_flash_goods_rel` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `flash_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Flash Sale ID',
  `goods_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Product ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flash_id` (`flash_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Flash Sale - Product Relationship Table';

LOCK TABLES `sms_flash_goods_rel` WRITE;
/*!40000 ALTER TABLE `sms_flash_goods_rel` DISABLE KEYS */;

INSERT INTO `sms_flash_goods_rel` (`id`, `flash_id`, `goods_id`)
VALUES
	('1841061529420566529','123','123'),
	('1858908717967921154','1840800346738278402','124'),
	('1858908717976309762','1840800346738278402','183');

/*!40000 ALTER TABLE `sms_flash_goods_rel` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_flash_sale
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_flash_sale`;

CREATE TABLE `sms_flash_sale` (
  `id` varchar(32) NOT NULL COMMENT 'Primary Key ID',
  `activity_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'Activity Name',
  `remark` varchar(2560) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT 'Remarks',
  `sort` int DEFAULT '9999' COMMENT 'Sorting (the larger the number, the later it appears)',
  `enabled` tinyint(1) DEFAULT '1' COMMENT 'Status (0: Disabled, 1: Enabled)',
  `start_time` datetime DEFAULT NULL COMMENT 'Start Time',
  `end_time` datetime DEFAULT NULL COMMENT 'End Time',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='Flash Sale Table';

LOCK TABLES `sms_flash_sale` WRITE;
/*!40000 ALTER TABLE `sms_flash_sale` DISABLE KEYS */;

INSERT INTO `sms_flash_sale` (`id`, `activity_name`, `remark`, `sort`, `enabled`, `start_time`, `end_time`, `create_time`, `update_time`)
VALUES
	('1858911216829386754','Black Friday','Black Friday, celebrated the day after Thanksgiving in the United States, marks the beginning of the holiday shopping season. It has become synonymous with massive discounts and doorbuster deals across retail stores and online marketplaces, drawing crowds eager to snag the best prices on everything from electronics and fashion to home goods and toys. \n\nRetailers often extend promotions throughout the weekend, culminating in Cyber Monday, which focuses on online deals. Many people plan their purchases and wait for this annual sales event to maximize their savings.\n\nAre you planning to shop or take advantage of any Black Friday deals?',9999,1,'2024-11-19 17:32:14','2024-11-19 18:32:14','2024-11-19 16:32:26','2024-11-20 00:32:25');

/*!40000 ALTER TABLE `sms_flash_sale` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_hot_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_hot_goods`;

CREATE TABLE `sms_hot_goods` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `goods_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Goods ID',
  `sort` int DEFAULT '9999' COMMENT 'Sorting (the larger the number, the later it appears)',
  `remark` varchar(256) DEFAULT NULL COMMENT 'Remarks',
  `enabled` tinyint(1) DEFAULT '1' COMMENT 'Status (0: Disabled, 1: Enabled)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='Hot Sale Product Recommendation Table';

LOCK TABLES `sms_hot_goods` WRITE;
/*!40000 ALTER TABLE `sms_hot_goods` DISABLE KEYS */;

INSERT INTO `sms_hot_goods` (`id`, `goods_id`, `sort`, `remark`, `enabled`, `create_time`, `update_time`)
VALUES
	('1858902137230774273','1857845087657029634',9999,NULL,1,'2024-11-19 15:56:21','2024-11-20 00:12:14');

/*!40000 ALTER TABLE `sms_hot_goods` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_new_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_new_goods`;

CREATE TABLE `sms_new_goods` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `goods_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Goods ID',
  `sort` int DEFAULT '9999' COMMENT 'Sorting (the larger the number, the later it appears)',
  `remark` varchar(256) DEFAULT NULL COMMENT 'Remarks',
  `enabled` tinyint(1) DEFAULT '1' COMMENT 'Status (0: Disabled, 1: Enabled)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='New Product Recommendation Table';

LOCK TABLES `sms_new_goods` WRITE;
/*!40000 ALTER TABLE `sms_new_goods` DISABLE KEYS */;

INSERT INTO `sms_new_goods` (`id`, `goods_id`, `sort`, `remark`, `enabled`, `create_time`, `update_time`)
VALUES
	('1858899287008268290','1857845087657029634',9999,NULL,1,'2024-11-19 15:45:01','2024-11-20 00:09:06');

/*!40000 ALTER TABLE `sms_new_goods` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_i18n_raw
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_i18n_raw`;

CREATE TABLE `sys_i18n_raw` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `raw_content` text COMMENT '原始数据表',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='i18n原始内容表';

LOCK TABLES `sys_i18n_raw` WRITE;
/*!40000 ALTER TABLE `sys_i18n_raw` DISABLE KEYS */;

INSERT INTO `sys_i18n_raw` (`id`, `raw_content`, `create_time`, `update_time`)
VALUES
	('1','华为 HUAWEI P20 升级版','2024-10-11 18:41:43','2024-10-11 18:42:34'),
	('1845888206379864065','商品管理','2024-10-14 19:03:38','2024-10-15 02:03:40'),
	('1846238861617823746','商品列表','2024-10-15 18:17:01','2024-10-16 01:17:01'),
	('1846239172709351426','品牌管理','2024-10-15 18:18:15','2024-10-16 01:18:15');

/*!40000 ALTER TABLE `sys_i18n_raw` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_i18n_translation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_i18n_translation`;

CREATE TABLE `sys_i18n_translation` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `raw_id` varchar(32) NOT NULL COMMENT 'i18n原始内容表ID',
  `language_code` char(2) NOT NULL COMMENT '语言代码(中文:CN,英文:EN)',
  `translated_content` text COMMENT '翻译后的内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `raw_id` (`raw_id`,`language_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='i18n翻译表';

LOCK TABLES `sys_i18n_translation` WRITE;
/*!40000 ALTER TABLE `sys_i18n_translation` DISABLE KEYS */;

INSERT INTO `sys_i18n_translation` (`id`, `raw_id`, `language_code`, `translated_content`, `create_time`, `update_time`)
VALUES
	('1845559455991787522','1','JP','Huawei HUAWEI P20 upgraded version 123','2024-10-13 21:17:18','2024-10-14 04:17:18'),
	('1845888925099020290','1845888206379864065','EN','Goods Mgmt','2024-10-14 19:06:29','2024-10-15 02:06:29'),
	('1845892764082556929','1845888206379864065','CN','商品管理','2024-10-14 19:21:45','2024-10-15 02:21:45'),
	('1846238951145242626','1846238861617823746','EN','Goods List','2024-10-15 18:17:22','2024-10-16 01:17:22'),
	('1846239244989792257','1846239172709351426','EN','Brand Mgmt','2024-10-15 18:18:32','2024-10-16 01:18:32'),
	('3142','1','EN','Huawei HUAWEI P20 upgraded version','2024-10-11 18:42:56','2024-10-11 18:42:56'),
	('3143','1','CN','华为 HUAWEI P20 升级版','2024-10-11 18:42:56','2024-10-12 03:53:35'),
	('3144','1','TC','華為 HUAWEI P20 升級版 123','2024-10-11 18:42:56','2024-10-12 03:53:35');

/*!40000 ALTER TABLE `sys_i18n_translation` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `parent_id` varchar(32) DEFAULT '' COMMENT 'Parent ID',
  `name` varchar(255) DEFAULT NULL COMMENT 'Menu Title',
  `url` varchar(255) DEFAULT NULL COMMENT 'Path',
  `component` varchar(255) DEFAULT NULL COMMENT 'Component',
  `redirect` varchar(255) DEFAULT NULL COMMENT 'Redirect URL for Top-level Menu',
  `type` int DEFAULT '1' COMMENT 'Menu Type (1: Menu; 2: Button Permission)',
  `unauthorized_strategy` int DEFAULT NULL COMMENT 'Unauthorized Strategy: Button (0: Hide; 1: Show but not clickable; 2: Show and clickable but submission fails); Menu Redirect (0: 404 Page; 1: 403 Page)',
  `permission` varchar(255) DEFAULT NULL COMMENT 'Menu Permission Code',
  `sort` int DEFAULT '9999' COMMENT 'Menu Sort Order',
  `icon` varchar(255) DEFAULT NULL COMMENT 'Menu Icon',
  `keep_alive` tinyint(1) DEFAULT '1' COMMENT 'Cache Page (0: No; 1: Yes)',
  `enabled` tinyint NOT NULL DEFAULT '1' COMMENT 'Enabled Status (0: Disabled, 1: Enabled)',
  `hidden` tinyint(1) DEFAULT '0' COMMENT 'Hide Route (0: No; 1: Yes)',
  `hidden_tab` int DEFAULT '0' COMMENT 'Hide Tab (0: No; 1: Yes)',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'Description',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
  `del_flag` int DEFAULT '0' COMMENT 'Deletion Status (0: Active; 1: Deleted)',
  `internal_or_external` tinyint(1) DEFAULT '0' COMMENT 'External Link Opening Method (0: Internal; 1: External)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_menu_type` (`type`) USING BTREE,
  KEY `index_menu_hidden` (`hidden`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='Menu Permissions Table';

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `component`, `redirect`, `type`, `unauthorized_strategy`, `permission`, `sort`, `icon`, `keep_alive`, `enabled`, `hidden`, `hidden_tab`, `description`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`, `internal_or_external`)
VALUES
	('1','','Home Page','/home','Home','',1,0,'',1,'HomeFilled',1,1,0,0,NULL,NULL,'2024-06-08 00:30:23',NULL,'2024-11-16 22:22:17',0,0),
	('1805162239428915202','6','Code Testing','/tool/test','tool/Test',NULL,1,0,NULL,9990,'BugOutlined',1,1,0,0,NULL,NULL,'2024-06-24 09:53:11',NULL,'2024-11-16 22:23:41',0,0),
	('1807047220929433602','3','Add User','','',NULL,2,0,'user:add',9999,'',1,1,0,0,NULL,NULL,'2024-06-29 14:43:25',NULL,'2024-11-16 22:21:53',0,0),
	('1810315067529027585','5','Add Menu',NULL,NULL,NULL,2,0,'menu:add',9999,NULL,1,1,0,0,NULL,NULL,'2024-07-08 15:08:41',NULL,'2024-11-16 22:21:14',0,0),
	('1810319487910207490','4','Add Role','/system/sysrole/saveorupdate',NULL,NULL,2,1,'role:add',9999,NULL,1,1,0,0,NULL,NULL,'2024-07-08 15:26:15',NULL,'2024-11-16 22:21:35',0,0),
	('1812067616699441153','5','Edit Menu','/system/sysmenu/saveorupdate',NULL,NULL,2,2,'menu:update',9999,NULL,1,1,0,0,NULL,NULL,'2024-07-13 11:12:41',NULL,'2024-11-16 22:21:08',0,0),
	('1812129343856115714','5','Get Menu List','/system/sysmenu/tree','system/sysMenu/tree',NULL,2,2,'system:sysmenu:tree',9999,NULL,1,1,0,0,NULL,NULL,'2024-07-13 15:17:58',NULL,'2024-11-16 22:21:01',0,0),
	('1812132225640759297','4','Get Role List','/system/sysrole/page','system/sysRole/page',NULL,2,2,'system:sysrole:page',9999,NULL,1,1,0,0,NULL,NULL,'2024-07-13 15:29:25',NULL,'2024-11-16 22:21:28',0,0),
	('1812160122644553730','5','Delete Menu','/system/sysmenu/deletebyids',NULL,NULL,2,1,'system:sysmenu:deletebyids',9999,NULL,1,1,0,0,NULL,NULL,'2024-07-13 17:20:16',NULL,'2024-11-16 22:20:53',0,0),
	('1823704170941317121','3','Get User List','/system/sysuser/page',NULL,NULL,2,0,'system:sysuser:page',9999,NULL,1,1,0,0,NULL,NULL,'2024-08-14 13:52:12',NULL,'2024-11-16 22:21:45',0,0),
	('1834991127946592258','','Product','/pms','',NULL,1,0,'pms',10,'ShoppingFilled',1,1,0,0,NULL,NULL,'2024-09-14 17:22:32',NULL,'2024-11-17 08:05:11',0,0),
	('1834992374506323970','1834991127946592258','Brand List','/pms/brand','pms/brand/index',NULL,1,0,'pms:brand',9999,'CopyrightCircleOutlined',1,1,0,0,NULL,NULL,'2024-09-14 17:27:29',NULL,'2024-11-16 22:22:09',0,0),
	('1836710946790227969','1834991127946592258','Product List','/pms/product','pms/product/index',NULL,1,1,'pms:product',9899,'ShoppingCartOutlined',0,1,0,0,NULL,NULL,'2024-09-19 11:16:29',NULL,'2024-11-17 08:05:50',0,0),
	('1838149623168974849','','Marketing','/sms','sms',NULL,1,0,'sms',60,'ThunderboltFilled',1,1,0,0,NULL,NULL,'2024-09-23 10:33:16',NULL,'2024-11-16 22:14:34',0,0),
	('1838149997980368897','1838149623168974849','Brand Recommendation','/sms/brand','sms/brand/index',NULL,1,0,'sms:brand',9919,'CopyrightCircleTwoTone',1,1,0,0,NULL,NULL,'2024-09-23 10:34:45',NULL,'2024-11-16 22:20:14',0,0),
	('1838151619515072513','1838149623168974849','Best Sellers','/sms/hotgoods','sms/hotgoods/index',NULL,1,0,'sms:hotgoods',9949,'FireTwoTone',1,1,0,0,NULL,NULL,'2024-09-23 10:41:12',NULL,'2024-11-16 22:22:44',0,0),
	('1838154441195339777','1838149623168974849','New Arrivals','/sms/newgoods','sms/newgoods/index',NULL,1,0,'sms:newgoods',9939,'SoundTwoTone',1,1,0,0,NULL,NULL,'2024-09-23 10:52:25',NULL,'2024-11-16 22:20:41',0,0),
	('1838157722969784322','1838149623168974849','Home Carousel','/sms/banner','sms/banner/index',NULL,1,0,'sms:banner',9900,'PushpinTwoTone',1,1,0,0,NULL,NULL,'2024-09-23 11:05:27',NULL,'2024-11-16 22:19:43',0,0),
	('1839958739902291969','1838149623168974849','Flash Sale','/sms/flashsale','sms/flashsale/index',NULL,1,0,'sms:flashsale',9999,'ClockCircleTwoTone',1,1,0,0,NULL,NULL,'2024-09-28 10:22:03',NULL,'2024-11-16 22:23:02',0,0),
	('1845084689682001922','2','Internationalization','/system/i18n','system/i18n/index',NULL,1,0,'system:i18n',9999,'GlobalOutlined',1,1,0,0,NULL,NULL,'2024-10-12 13:50:45',NULL,'2024-11-16 20:15:39',0,0),
	('1858559760287195137','','About','/about','about/index',NULL,1,NULL,'about',9999,'InfoCircleFilled',1,1,0,0,NULL,NULL,'2024-11-18 17:15:52',NULL,'2024-11-19 01:18:12',0,0),
	('2','','System','/system',NULL,'/system/user',1,0,NULL,20,'SettingFilled',1,1,0,0,NULL,NULL,'2024-06-08 00:36:56',NULL,'2024-11-16 20:16:08',0,0),
	('3','2','User','/system/user','system/user/index','',1,0,NULL,4,'UserOutlined',0,1,0,0,NULL,NULL,'2024-06-08 00:43:31',NULL,'2024-11-16 20:15:17',0,0),
	('4','2','Role','/system/role','system/role/index',NULL,1,0,NULL,5,'ContactsOutlined',1,1,0,0,NULL,NULL,'2024-06-08 01:04:55',NULL,'2024-11-16 20:14:43',0,0),
	('5','2','Menu','/system/menu','system/menu/index',NULL,1,0,NULL,6,'BarsOutlined',1,1,0,0,NULL,NULL,'2024-06-08 01:06:12',NULL,'2024-11-16 20:14:22',0,0),
	('6','','Development Tools','/tool','','',1,0,NULL,100,'CodeFilled',1,1,0,0,NULL,NULL,'2024-06-08 01:07:25',NULL,'2024-11-16 22:23:24',0,0),
	('7','6','Code Generation','/tool/code','tool/Code','',1,1,NULL,9995,'ConsoleSqlOutlined',1,1,0,0,NULL,NULL,'2024-06-08 01:08:00',NULL,'2024-11-16 22:24:00',0,0);

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `role_name` varchar(200) NOT NULL DEFAULT '' COMMENT 'Role Name',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `create_by` varchar(32) DEFAULT NULL COMMENT 'Created by',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_by` varchar(32) DEFAULT NULL COMMENT 'Updated by',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
  `enabled` tinyint DEFAULT '1' COMMENT 'Role Status (0: Disabled; 1: Enabled)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_name_unique` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='Role Table';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `role_name`, `description`, `create_by`, `create_time`, `update_by`, `update_time`, `enabled`)
VALUES
	('1','Super Administrator','Has the highest privileges',NULL,'2024-06-08 01:09:13',NULL,'2024-11-19 03:19:06',1),
	('1807495103998803969','Chat Customer','Has Chat Permission',NULL,'2024-06-30 20:23:09',NULL,'2024-11-19 03:19:43',1),
	('1810353969174351874','test','testtest11',NULL,'2024-07-08 17:43:16',NULL,'2024-10-28 03:28:33',1),
	('1823703364108222466','test1',NULL,NULL,'2024-08-14 13:48:59',NULL,'2024-11-19 06:40:15',1),
	('2','Developer','Has Developer Permissions',NULL,'2024-06-08 01:09:34',NULL,'2024-11-19 03:20:14',1);

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_role_menu_rel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_menu_rel`;

CREATE TABLE `sys_role_menu_rel` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `role_id` varchar(32) DEFAULT NULL COMMENT 'Role ID',
  `menu_id` varchar(32) DEFAULT NULL COMMENT 'Menu ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_menu` (`role_id`,`menu_id`),
  KEY `role_id_index` (`role_id`),
  KEY `menu_id_index` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Role-Menu Relationship Table';

LOCK TABLES `sys_role_menu_rel` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_rel` DISABLE KEYS */;

INSERT INTO `sys_role_menu_rel` (`id`, `role_id`, `menu_id`)
VALUES
	('1858560127217491972','1','1'),
	('1858560127137800193','1','1805162239428915202'),
	('1858560127225880578','1','1807047220929433602'),
	('1858560127225880580','1','1810315067529027585'),
	('1858560127200714753','1','1810319487910207490'),
	('1858560127196520451','1','1812067616699441153'),
	('1858560127221686274','1','1812129343856115714'),
	('1858560127213297666','1','1812132225640759297'),
	('1858560127196520452','1','1812160122644553730'),
	('1858560127192326147','1','1823704170941317121'),
	('1858560127217491970','1','1834991127946592258'),
	('1858560127175548930','1','1834992374506323970'),
	('1858560127196520450','1','1836710946790227969'),
	('1858560127217491971','1','1838149623168974849'),
	('1858560127209103363','1','1838149997980368897'),
	('1858560127242657793','1','1838151619515072513'),
	('1858560127196520453','1','1838154441195339777'),
	('1858560127192326145','1','1838157722969784322'),
	('1858560127192326146','1','1839958739902291969'),
	('1858560127209103362','1','1845084689682001922'),
	('1858560127213297667','1','1858559760287195137'),
	('1858560127221686275','1','2'),
	('1858560127225880577','1','3'),
	('1858560127225880579','1','4'),
	('1858560127230074881','1','5'),
	('1858560127230074882','1','6'),
	('1858560127238463490','1','7'),
	('1858649449181032451','1810353969174351874','1'),
	('1858649449076174850','1810353969174351874','1805162239428915202'),
	('1858649449210392577','1810353969174351874','1807047220929433602'),
	('1858649449210392578','1810353969174351874','1810315067529027585'),
	('1858649449176838146','1810353969174351874','1810319487910207490'),
	('1858649449239752706','1810353969174351874','1812067616699441153'),
	('1858649449185226754','1810353969174351874','1812129343856115714'),
	('1858649449181032450','1810353969174351874','1812132225640759297'),
	('1858649449172643842','1810353969174351874','1812160122644553730'),
	('1858649449218781189','1810353969174351874','1823704170941317121'),
	('1858649449235558402','1810353969174351874','1836710946790227969'),
	('1858649449185226755','1810353969174351874','2'),
	('1858649449202003970','1810353969174351874','3'),
	('1858649449218781186','1810353969174351874','4'),
	('1858649449218781187','1810353969174351874','5'),
	('1858649449218781188','1810353969174351874','6'),
	('1858649449227169793','1810353969174351874','7'),
	('1850487908815585281','1823703364108222466','1');

/*!40000 ALTER TABLE `sys_role_menu_rel` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `username` varchar(100) NOT NULL COMMENT 'Username',
  `nickname` varchar(100) DEFAULT NULL COMMENT 'User Nickname',
  `password` varchar(256) NOT NULL COMMENT 'Password',
  `avatar` varchar(256) DEFAULT NULL COMMENT 'User Avatar URL',
  `email` varchar(100) DEFAULT NULL COMMENT 'User Email Address',
  `phone` varchar(15) DEFAULT NULL COMMENT 'User Phone Number',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Account Status (0: Disabled; 1: Enabled)',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Delete Flag (0: Deleted; 1: Active)',
  `create_by` varchar(32) DEFAULT NULL COMMENT 'Created By (User ID)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Timestamp',
  `update_by` varchar(32) DEFAULT NULL COMMENT 'Updated By (User ID)',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last Update Timestamp',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Table for storing user information';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `username`, `nickname`, `password`, `avatar`, `email`, `phone`, `enabled`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES
	('1','admin','admin123123','$2a$10$lBNMUzBbxThm0r7OENVT5On7VV7OgKs4rC.GGkWfMJg3OKMWBjeHa','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/eaae417a-7cbb-4486-bf20-9c3ea3334f4e.png?alt=media','1450948930@qq.com','01234567890',1,1,NULL,'2024-06-08 01:28:20',NULL,'2024-10-19 19:31:11'),
	('1807493848987549697','operation','Operation user','$2a$10$tvdAWQPGwI6QYbp3Z9nbFOk1nKcSoJJcODFKZni6j5vMf8cTHlHDu','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/feffbcb7-fc74-4706-9eb2-42a05415bbc2.png?alt=media','1450948930@qq.com','123456789',1,1,NULL,'2024-06-30 20:18:10',NULL,'2024-10-26 21:29:26'),
	('2','test','test','$2a$10$WKeSgL0QYmepFKyii/UcUuja8VWDzg52KJ6sE7G93kXbjfHNXuwi6',NULL,'test12@qq.com','123456',1,1,NULL,'2024-06-08 01:28:20',NULL,'2024-11-19 06:12:07');

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_user_role_rel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role_rel`;

CREATE TABLE `sys_user_role_rel` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Primary Key ID',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'User ID',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'Role ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_unique` (`user_id`,`role_id`),
  KEY `user_id_index` (`user_id`),
  KEY `role_id_index` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='User-Role Relationship Table';

LOCK TABLES `sys_user_role_rel` WRITE;
/*!40000 ALTER TABLE `sys_user_role_rel` DISABLE KEYS */;

INSERT INTO `sys_user_role_rel` (`id`, `user_id`, `role_id`)
VALUES
	('1852795108028264449','1','1'),
	('1852795108086984706','1','1810353969174351874'),
	('1852795108074401793','1','2'),
	('1807489156266000385','1807489098560765954','1'),
	('1850178964100464641','1807493848987549697','1'),
	('1850178964113047553','1807493848987549697','1807495103998803969'),
	('1850178964113047554','1807493848987549697','1810353969174351874'),
	('1850178964075298818','1807493848987549697','1823703364108222466');

/*!40000 ALTER TABLE `sys_user_role_rel` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
