# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20077
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 0.0.0.0 (MySQL 8.0.40)
# 数据库: tiny_admin
# 生成时间: 2024-11-22 23:59:48 +0000
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
                               `product_name` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'Product Name',
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
    ('1857845087657029634','SAMSUNG Galaxy A35 5G A Series Cell Phone, 128GB Unlocked Android Smartphone, AMOLED Display, Advanced Triple Camera System, Expandable Storage, Rugged Design, US Version, 2024, Awesome Navy','SAMSUNG Galaxy A35 5G A Series Cell Phone, 128GB Unlocked Android Smartphone, AMOLED Display, Advanced Triple Camera System, Expandable Storage, Rugged Design, US Version, 2024, Awesome Navy','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/c82918ab-9cf2-4711-b239-b68edc5a48fc.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b71a3c3e-a0f9-4577-8e2b-1fe705fb6f50.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/62d4557b-b4bb-4157-86b4-f07ea295adc2.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b040e6b3-960a-4a6b-a528-ee1cd3307b4c.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/58ae3342-2b3f-4c29-9290-82fe2f19c824.jpg?alt=media,https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/d57cc23a-da07-497c-8f75-39b215ad1de9.jpg?alt=media',99999,NULL,9999,10,299.99,201.84,1,'<p><br></p><h2><strong>From the manufacturer</strong></h2><p><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/d9ae9933-a5d8-4b86-9b51-eb8745f77f19.__CR0,0,3050,1250_PT0_SX1464_V1___.jpg\" alt=\"Samsung Galaxy A35 5G\"/></p><p><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/c15195f2-b54f-4ec6-beef-d791a402395d.__CR0,0,3050,1250_PT0_SX1464_V1___.jpg\" alt=\"Samsung Galaxy A35 5G\"/></p><h2><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/547fbbfc-d279-4523-9f1d-1add3cb8d50a.__CR0,0,3050,1250_PT0_SX1464_V1___.jpg\" alt=\"Samsung Galaxy A35 5G\"/></h2><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/f17ac0c6-c5ca-4d3f-b1c7-a859d8a44b1f.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/1ab5ecc7-c738-42df-aeef-d69d01468202.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/31a04956-b9f1-4b07-ac69-6f069a058be4.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b8768609-495f-489d-aec4-72a4f0103884.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/901c8e26-87ac-4e38-9910-ef812f59f74e.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/b196e449-c872-4b45-813f-8661ef1b06d5.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/33b8f1a4-68fd-4537-a85f-8feafba01d0e.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/060fea32-2917-41f7-b4b0-11780b83f616.png?alt=media\" alt=\"\"/></p><p><img src=\"https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/9ceb363e-7bc4-497e-9646-54425a13eda8.png?alt=media\" alt=\"\"/></p><p><br></p><p><img src=\"https://m.media-amazon.com/images/S/aplus-media-library-service-media/ac91c8f2-53da-4dbc-b78f-2fa16dcc82dc.__CR0,0,1464,625_PT0_SX1464_V1___.jpg\" alt=\"brand story_gray\"/></p>',NULL,'2024-11-17 01:56:00','2024-11-23 05:38:11'),
    ('1860074315146014722','Electric Scooter Reflective Stickers Night Safety Stickers Scooter Accessories Night Safety Stickers, Scooter Accessories','','https://ae-pic-a1.aliexpress-media.com/kf/S102598082aa34d9a85121ea838e96dde0.png,https://ae-pic-a1.aliexpress-media.com/kf/S23126816e9a845d8acaa3aef6f6ed622f.png,https://ae-pic-a1.aliexpress-media.com/kf/S5708695c69034668b98ec6757ee52090H.png,https://ae-pic-a1.aliexpress-media.com/kf/S61086eefe40b43a892cdb671d6844071Z.png,https://ae-pic-a1.aliexpress-media.com/kf/S4fa7361bcbdf496191192fc6cb202a3cQ.png,https://ae-pic-a1.aliexpress-media.com/kf/Seb3a06306d0041d3843c718a20559408g.png',99999,NULL,9999,10,169.00,150.00,1,'<p><br></p><h2>Specifications</h2><ul><li>Hign-concerned ChemicalnoneVoltageNone</li><li>is_customizedNoTypeWheel Hubs</li><li>OriginMainland China</li><li></li><li></li><li></li><li></li></ul><h2>Description<a href=\"https://report.aliexpress.com/health/reportIndex.htm?product_id=1005006998112633&b_login_id=cn1089961361ijcae\" target=\"_blank\">Report Item</a></h2><p><br></p><p style=\"text-align: center;\"><strong>Electric Scooter Reflective Stickers Night Safety Stickers Scooter Accessories Night Safety Stickers, Scooter Accessories</strong></p><p style=\"text-align: center;\"><strong><br></strong></p><p><strong>Product Parameters:</strong></p><p>-Product name: scooter reflective sticker</p><p>-Size: 6*2cm</p><p>-Colour: blue, red, yellow, green, silver, orange</p><p><br></p><p><strong>Package Included:</strong></p><p>A set of stickers (four pieces)</p><p><br></p><p><span style=\"color: rgb(244, 78, 59);\"><strong>NOTED:</strong></span></p><p>-Please allow 1-5cm errors to the actual dimension due to manual measurement! Thank you !</p><p>-Real color may slightly different from pictures due to computer screen\'s resolution, brightness, contrast etc.</p><p><br></p><p><strong>Product Features:</strong></p><p>- Made of high quality and durable material.</p><p>-Adopted reflective technology with multiple microcrystalline cubes, greatly increasing the reflective brightness, especially eye-catching.</p><p>- Not easy to fade: not easy to fade in the hot sun, wind and rain and other harsh environments.</p><p>- Waterproof and wear-resistant, long service life, high reflective brightness, bright colours.</p><p>-Suitable for ninebot max G30 electric scooter.</p><p><br><br></p><p><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/S200f95f84f3045aca67d986e3b1e6e36K.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/See5bfe991c02478eaf5e7e7ef543da6aK.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/S705bb3a7447048f18420c87d0e3005d9f.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/S3011ad5706944eff96d403974e623584C.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/S31cbe87dd854407b947c62ad77e2fcc6d.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/S935013c9f0634482bb68bcbe6343f2cen.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/Sf187b0a21bb24180b68f598bb5552b52C.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><span style=\"color: rgb(34, 34, 34); font-size: 14px;\"> </span><img src=\"https://ae01.alicdn.com/kf/S6003e068ed744ef8bc6e3fc3bba5547a2.jpeg\" alt=\"\" data-href=\"\" style=\"\"/><br></p>',NULL,'2024-11-22 21:34:10','2024-11-23 05:34:09'),
    ('1860079373468291073','14.1\" Ultra Slim Laptop 16GB RAM 2TB SSD Intel N3700 Notebook Gamer 1920*1080 Display Office Study Computer PC Windows 11 Pro\n','','https://ae-pic-a1.aliexpress-media.com/kf/Saa594f897cde4f59b1f396b91fb96f9by.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S034cfcb1b23c44faa77a7fcf0cd0a893h.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S622eb776ec62446dba936271f2ed2fc6x.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S133168e11ddc413a9015eecef7128fe8J.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sf0d84f2ef4494dd9b877637359ee7e09h.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sbfa8e5a80efe4d5fa146c5a6c1b46a368.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sff055f7aa80f49f6be8a8d9453c91737M.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S07990d8357784321bc38532ae48a70d4q.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sa9d1f145703c499bad9a1636bb3956e6W.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sc512cc47748d4a808067e66e11555a9eh.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S55f2936e6ff343dea7fe08afb9b0def1s.jpg',99999,NULL,9999,10,179.02,134.26,1,'<p><br></p><h2>Specifications</h2><ul><li>Recommended useLearing,Business Use,Gaming,Daily EntertainmentKeyboardsTOUCHPAD,Numeric Keypad</li><li>Battery Capacity（mAh）4000Network CommunictionBluetooth-compatible,WIFI</li><li>Touch ScreennoExpandable storageYES</li><li>Hard Drive InterfaceM.2Memory TypeDDR3</li><li>Thermal Design Power15Graphics BrandIntel</li><li>Graphic TypeIntegratedMax Turbo Frequency2.4</li><li>Base Frequency (GHz)41.6Number of Cores4</li><li>Battery lifetimeup to 7 - 9 hourKeyboard languageEnglish</li><li>Wireless InterfacesBluetooth 4.2CPU Brand/ModelIntel Pentium N3700</li><li>Weight (Battery Included)＜1.5KgScreen Refresh Rate60Hz</li><li>Video Memory CapacityMain memory allocated memoryDimensions (WxHxD)324*210*18mm</li><li>RAM16GBPanel TypeIPS</li><li>Display resolution1920x1080Operating SystemWindows 11</li><li>FeaturebluetoothAverage Battery Life(in hours)5-12</li><li>Thickness15mm- 18mmPackageYes</li><li>Hard Drive Capacity2TBPort2*USB3.0,Mini HDMI-Compatible</li><li>Display Ratio16:10Display Size14.1\"</li><li>Hard Drive TypeSSDGraphics Card ModelIntel&reg; HD Graphics</li><li>TypeUltraslimCertificationCE</li><li>OriginMainland China</li><li></li><li></li><li></li><li></li></ul><h2>Description<a href=\"https://report.aliexpress.com/health/reportIndex.htm?product_id=1005007161056551&b_login_id=es3081857489uptae\" target=\"_blank\">Report Item</a></h2><p><br></p><p style=\"text-align: left;\">Product Description<br>Weight:1.25KG<br>Screen size:14.1 inch<br>Screen material:IPS<br>Screen resolution:1920*1080<br>CPU model:intel N3700<br>CPU core:Dual Core 2.4GHz<br>GPU:lntel@ UHD Graphics<br>RAM:16GB<br>ROM:128GB/256GB/512GB/1024GB/2048GB<br>WIFI:2.4G/5G<br>Bluetooth:Bluetooth 4.2<br>OS:Windows 11<br>Certification:CE<br><br>1) 14.1-inch FHD 1920*1080P IPS screen The 14.1-inch FHD screen provides a wider view and is still clearly visible even in strong outdoor lighting. In addition, night mode and color adjustment will help reduce eye strain.<br><br>2) Large multi-function touch panel, high-quality keyboard, makes your typing more enjoyable and comfortable.<br><br>3) Intel N3700 quad-core quad-thread processor up to 1.6GHz,<br><br>4) 16GB RAM, revolutionizing memory and storage, running faster,<br><br>5) 128/256/512/1024GB ROM, solid-state speed, smoother operation, helps you run multiple apps smoothly<br><br>6) Dual independent high-quality speakers provide a resonant sound effect, minimize distortion, enhance details to create an immersive sound from all angles.<br><br>7) Multiple ports to meet your different needs: 2 x USB3.0, HDMI, audio, memory card, 1 x charging port<br><br>8) Personalized 0.3 MP single camera that narrows the boundaries.<br><br>Others: Support Wi-Fi, Bluetooth, multiple apps, books, wallpapers, calendar, calculator, clock, etc.<br><br>System language:<br>Chinese, English, Japanese, French, German, Korean, Italian, Portuguese, Russian, Spanish and other languages<br><br>You can change the languages in the settings.<br><br><br>Equipment<br>Laptop*1<br>User\'s Manual *1<br>Power Adapter *1</p><p style=\"text-align: left;\"><br></p><p style=\"text-align: left;\">We will send you the language of the corresponding country depending on the country where you placed the order. For example, if you are a Russian guest, I will send the default Russian language and provide Russian keyboard stickers. If you are in Spain, the Spanish language will be sent to you by default, and Spanish keyboards will be provided. By analogy, we are serious about serving mindfully.</p><p style=\"text-align: left;\"><img src=\"https://ae01.alicdn.com/kf/S6a3990f457d54753a09b340bab22362c1.jpg?width=1200&amp;height=1398&amp;hash=2598\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Sb256b901c8404a1883dcaecd436f3693m.jpg?width=1200&amp;height=1200&amp;hash=2400\" alt=\"\" data-href=\"\" style=\"\"></p><p style=\"text-align: left;\"><img src=\"https://ae01.alicdn.com/kf/S4b5609e3abf24af1a47acac7c3b515c3l.jpg?width=790&amp;height=1500&amp;hash=2290\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S1a23b97c3beb4f00bfba12e66f3f0cb0T.jpg?width=790&amp;height=1301&amp;hash=2091\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S686c51fc86134b22be45426e4039a699v.jpg?width=790&amp;height=1620&amp;hash=2410\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Sba83eba91e2146c8a584f1ef51c747c5g.jpg?width=790&amp;height=1886&amp;hash=2676\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S78b488e5b42d4936b4e908873365b48eL.jpg?width=790&amp;height=1457&amp;hash=2247\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S7b69b83df9f248e881906f1ff7e25c76h.jpg?width=790&amp;height=1550&amp;hash=2340\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S72956a7ac095435fa27969f828b75a11n.jpg?width=790&amp;height=1250&amp;hash=2040\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S9f24d097cfa84bca9e64cf81f5ffd0bdx.jpg?width=790&amp;height=1400&amp;hash=2190\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S4c5ffeb77155499498b1309cab2f3be1A.jpg?width=790&amp;height=1180&amp;hash=1970\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S1526ecf33ed142df8c3b7c4c78fc112eZ.jpg?width=790&amp;height=1350&amp;hash=2140\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S47c37dbefc6c4e709761944ef6ebceccK.jpg?width=730&amp;height=750&amp;hash=1480\" alt=\"\" data-href=\"\" style=\"\"></p><p> </p><p><br></p><h2>Buyer Questions & Answers (53)</h2><p><br></p><ul><li>It’s a fake the reviews not true don’t buyYou are right, it is not 16 of ram, it is 8 and ROM does not reach the 512</li><li>Where mine is? I have no tracking update in 7 days. Won\'t be a fraud?</li><li>I\'m still waiting for my team to arrive today and nothing</li><li></li><li></li><li></li><li><a href=\"\" target=\"_blank\">Information on additional charges</a></li><li>For items delivered from outside the European Union, you may be subject to additional charges for VAT and, when applicable, customs duties in your country. If AliExpress is obliged by law to collect VAT, you will see the VAT inclusive price at checkout. For more information about these costs, please contact the tax and customs authorities in your country.</li></ul>',NULL,'2024-11-22 21:54:16','2024-11-23 05:54:15'),
    ('1860081943549038593','100% Original Apple Watch Series 4 Smartwatch 40MM/44MM GPS Aluminum with Sport Band','','https://ae-pic-a1.aliexpress-media.com/kf/Sef6aec0f33c74becb9787daabdd2afc4A.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sd4875359557c4b93af5c594494b89c671.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S04d0a4cc8c0d4b15838dfe9a4bf3c04bv.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sb264a291c7854d9fae328107a4bcef76v.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S9a62974903cb465bb0eae07e73863fd4t.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sbdc233dc8c5848b9a2baa0c778a970dbr.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S04d0a4cc8c0d4b15838dfe9a4bf3c04bv.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sb264a291c7854d9fae328107a4bcef76v.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sd4875359557c4b93af5c594494b89c671.jpg',99999,NULL,9999,10,358.54,100.39,1,'<p><br></p><p><img src=\"https://ae01.alicdn.com/kf/Se4e6b9fb7f5642109980875076b47b26r.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S65bc84c50f074033813a137e16121934y.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Se0e80a3724654a1d8bccaaae103414d6W.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S3bdba2441e6c463da5dbb526fb0249326.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S9a62974903cb465bb0eae07e73863fd4t.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Sbdc233dc8c5848b9a2baa0c778a970dbr.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Sf7219c987fbf431b992811292514e05dy.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Se0b04a048623462f89d284239c0d98dcE.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Sdd908153ae6443f1a332da1a0ccdbb99r.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Se50c9cc322304ac89ce844b055b20fd8j.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S34a6fd9b92c549059f8bb5cd3a76a440y.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><br></p>',NULL,'2024-11-22 22:04:29','2024-11-23 06:05:30'),
    ('1860083282442178562','Apple AirPods 2nd Gen. 100% New Original. Bluetooth Earbuds. Siri Smart Control. Fine Sound, Good Build. Authentic.\n','','https://ae-pic-a1.aliexpress-media.com/kf/S968ee41e52ca404daa116021d462cdd2U.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S44581159befd429e8e4ad624d63ea8952.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S2216b44797f64b73979e44badb6197a4c.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S89bcb737d0c6415a900aaf7ab58f7dbeC.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S0844d9aa637f47a78c11ea687ede57eb9.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S19f82181380e4f95b17b6ad743f15f97c.jpg',99999,NULL,9999,10,157.81,44.19,1,'<p><br></p><h2>Description<a href=\"https://report.aliexpress.com/health/reportIndex.htm?product_id=1005008076664070&b_login_id=cn1088859072eoeae\" target=\"_blank\">Report Item</a></h2><p><br></p><p><span style=\"color: rgb(34, 34, 34); background-color: rgb(255, 255, 255); font-size: 14px;\">1. Do the prices include customs fees?</span><br><span style=\"color: rgb(34, 34, 34); background-color: rgb(255, 255, 255); font-size: 14px;\">Customs fees are not included in the price ( if you need to adjust the declared value, please leave a message ) so if the package arrives at your customs, please clear it yourself.</span><br><span style=\"color: rgb(34, 34, 34); background-color: rgb(255, 255, 255); font-size: 14px;\">2. About warranty.</span><br><span style=\"color: rgb(34, 34, 34); background-color: rgb(255, 255, 255); font-size: 14px;\">The products are 100% original and brand new! We offer a 1 year store warranty on all products. Any questions, please feel free to contact us!</span><img src=\"https://ae01.alicdn.com/kf/S22aae583fedc4243aa62c1a97ce81822Q.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S968ee41e52ca404daa116021d462cdd2U.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S0844d9aa637f47a78c11ea687ede57eb9.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S44581159befd429e8e4ad624d63ea8952.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S89bcb737d0c6415a900aaf7ab58f7dbeC.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S19f82181380e4f95b17b6ad743f15f97c.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><br></p>',NULL,'2024-11-22 22:09:48','2024-11-23 06:09:47'),
    ('1860084138281521153','Apple iPhone 12 mini 5G Face ID Mobile Phone Original Used Unlocked 5.4\" 64/128/256GB ROM Hexa-core IOS 12MP Camera\n','','https://ae-pic-a1.aliexpress-media.com/kf/Sf3fcd4a3415842dbb62b69cbf838fc50P.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S53640bb805d84b7abce21a75380ab280J.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S6c4c6f671e64462d948b54e42215ffa3S.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sf525629c29a940509b5a4684abf4483fj.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sefb3f4c18b07493a818ce50710b8d60db.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S69e85e78467347a98ff6002bba266f038.jpg',99999,NULL,9999,10,264.27,184.99,1,'<p><br><img src=\"https://ae01.alicdn.com/kf/S70611b619f044055bc302cee9ae831b6o.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://ae01.alicdn.com/kf/S53640bb805d84b7abce21a75380ab280J.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S7e99ad0d6fa346639a6edde18aed4e40Z.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Sf525629c29a940509b5a4684abf4483fj.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S6c4c6f671e64462d948b54e42215ffa3S.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Sa9921aec1f044d9aaea791a0c15b1b6bc.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Sefb3f4c18b07493a818ce50710b8d60db.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S69e85e78467347a98ff6002bba266f038.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S31e7a2acb0364952b680faaf02c95fb9S.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Se04f17a1ce8a46f39844c12e44d7ae99X.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S1fb50523aef94b4b8497289aace02c51J.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S336a59bf9a444eaab949f732f672dd37q.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/Sb6b6b806b9464e9e81fefcb83e7fab07Q.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S7b6b4c73f76946198745c8ed99a8dfb0f.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S8403fdf0f10e47098cd8dd32e43e4e3cf.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://ae01.alicdn.com/kf/S86fb55313ff44a19a7c394dee7eb81ffg.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://ae01.alicdn.com/kf/S8cc983a0331249878634f28a4b35b838K.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p style=\"text-align: start;\"><br></p><p style=\"text-align: start;\"><span style=\"color: rgb(244, 78, 59); background-color: rgb(255, 255, 255);\">1.About TAX</span></p><p style=\"text-align: start;\"><span style=\"color: rgb(0, 0, 0); background-color: rgb(255, 255, 255);\">Parcel ship from oversea to your country might be inspected by custom.VAT would be charged by local custom which buyer should be responsible for payment as it is the local custom policy. Our price does not include any custom taxes. If the parcel get strict inspected by custom. You still need to pay tax to clear custom. We won\'t bear the customs taxes. The cost of return would be on customer due to reject customs clearance.</span><span style=\"color: rgb(244, 78, 59); background-color: rgb(255, 255, 255);\">2.On the issue of logistics timeliness</span><span style=\"color: rgb(0, 0, 0); background-color: rgb(255, 255, 255);\">If you encounter problems with customs clearance or customs detention, please contact us in time, please do not give us a 1-star rating, we will solve all your troubles for you.</span></p>',NULL,'2024-11-22 22:13:12','2024-11-23 06:13:11'),
    ('1860084712498515970','Xiaomi 14 Ultra Black- Smartphone 16+512GB, Snapdragon 8 Gen3 3.3GHz, 120Hz 6.73\'\' AMOLED Display,90W Hyper Charge, 5000mAh\n','','https://ae-pic-a1.aliexpress-media.com/kf/S50bc5c4fb3a9485cacdfd68d6a524d0d9.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sd071a5b2c0f6457fb3803fd6e9d3ce27G.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S97df47b0e58a4b389c4c6403d6357c22R.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S3c5dfa03d06b428c8d6cfa5f5fd47966D.jpg,https://ae-pic-a1.aliexpress-media.com/kf/A86a31999af074f0a953c981c1d55b44cV.jpg,https://ae-pic-a1.aliexpress-media.com/kf/A4907fd536f81410ebd286e63c5739df7Y.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Add8c081d880b420692b1bafd4d4829bdN.jpg',99999,NULL,9999,10,1336.30,826.87,1,'<p><br></p><h2>Description<a href=\"https://report.aliexpress.com/health/reportIndex.htm?product_id=1005007839415989&b_login_id=gb3389035295ztlae\" target=\"_blank\">Report Item</a></h2><p><br></p><p style=\"text-align: center;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\"><strong>Xiaomi Smart Phone 14 Ultra</strong></span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Xiaomi new flagship smartphone in 2024. It continues to offer high performance while maintaining low power consumption,</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">which is a step further.</span></p><p style=\"text-align: left;\"><img src=\"https://ae01.alicdn.com/kf/S480c9263632f4aef8479784fd79174b6M.jpg\" alt=\"\" data-href=\"\" style=\"\"></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">The powerful Snapdragon 8Gen 3 processor will take it to the next level</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">This unlocked cellphone combines a Snapdragon 8Gen 3 processor with 16GB of RAM and Xiaomi Hyper OS operating system for</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">fast and efficient performance. 16GB of RAM allows you to run multiple apps at once.</span></p><p style=\"text-align: left;\"><img src=\"https://ae01.alicdn.com/kf/S498ac7588c1b4153a2cf5a7b692c73863.jpg\" alt=\"\" data-href=\"\" style=\"\"><br></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Outstanding 50MP Leica quad camera</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">The 50MP Leica quad camera allows you to deliver superb high-resolution images in all lighting conditions, with spectacular results,</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">especially in low light. With Xiaomi 14 Ultra, you can capture the great moments of your life.</span></p><p style=\"text-align: left;\"><img src=\"https://ae01.alicdn.com/kf/Sb0907174d538470388512bf458e17094E.jpg\" alt=\"\" data-href=\"\" style=\"\"></p><p style=\"text-align: left;\"><img src=\"https://ae01.alicdn.com/kf/S2005fc3f0edd4bbaa656e8470a99c59d2.jpg\" alt=\"\" data-href=\"\" style=\"\"><br></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">6.73\'\'AMOLED Display smooth experience at all times</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Xiaomi Smartphone 14 Ultra, featuring a 6.73\'\' AMOLED Display with a 20:9 aspect ratio and dual stereo speakers, lets you enjoy</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">the feel of a movie.</span></p><p style=\"text-align: left;\"><br></p><p style=\"text-align: left;\"><strong>About this item</strong></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*The powerful Snapdragon 8+ Gen 3 processor will take it to the next level</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*Outstanding 50MP Leica triple camera</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*6.73\'\'AMOLED Display smooth experience at all times</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*Incredible 90W HyperCharge</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*The unlocked smartphone is equipped with a powerful *5000mAh battery</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*Up to 16GB RAM and 512 ROM</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*IP68 water &amp; dust resistance</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">*Cutting-edge immersive visuals</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Operating system: Android 13.0</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">RAM memory installed size: 16 GB</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">CPU model: Snapdragon</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Memory storage capacity: 512 GB</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Screen size: 6.73 Inches</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Resolution: 1440 x 2630 or 1440 x 2640</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Refresh rate: 120 hertz</span></p><p style=\"text-align: left;\"><span style=\"color: rgb(15, 17, 17); background-color: rgb(255, 255, 255); font-size: 14px;\">Model name: Xiaomi 14 Ultra Black- Smartphone 16+512GB, Snapdragon 8 Gen3 3.3GHz, 120Hz 6.73\'\' AMOLED Display,90W Hyper Charge, 5000mAh Battery</span></p><p style=\"text-align: left;\"><img src=\"https://ae01.alicdn.com/kf/Sbda000ee5865446f8f9565bbb424b151A.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S2a224c408bb640e0a318d35976e1dd1fc.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Scaceadbb217246d1849e629a731a23c6Q.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/S2832f7fa3cb849808f53896092a7bf64v.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Sf24c0fe73cc14a8ab730a35aaaae21dbV.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Sca9c4d75179846699058c78a37d5cd16X.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Sd67c0e8ad4f8401391bb145b17576d670.jpg\" alt=\"\" data-href=\"\" style=\"\"><br></p><p>For a repair request, please contact the XIAOM Customer Service at 0800 021 1888 (Monday to Friday: 09:00-18:00) .<br></p>',NULL,'2024-11-22 22:15:29','2024-11-23 06:15:28'),
    ('1860085866016002050','NFC Ceramic Smart Ring Car Key Card for Tesla Model 3/Y\n','','https://ae-pic-a1.aliexpress-media.com/kf/S03934d04aa314d1b989532858cf48f57v.png,https://ae-pic-a1.aliexpress-media.com/kf/See8588e92d91443482cacf9a703b42c56.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Se07502f62cf24842932229126db2e50fe.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S1e587ae549c0496c93a5b691a2cb57663.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S9f5bc2938d534fd7ad8414160ab420d6n.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Se660f52633e5452c98f7579afcd37ccdW.jpg,https://ae-pic-a1.aliexpress-media.com/kf/S1dd1fb679dcb4f31a88e7462fd52a556I.jpg,https://ae-pic-a1.aliexpress-media.com/kf/Sf0a87f6a6bb54939a65cd7bab4e10279i.jpg',99999,NULL,9999,10,86.89,25.40,1,'<p><br></p><h2>Description<a href=\"https://report.aliexpress.com/health/reportIndex.htm?product_id=1005005782713705&b_login_id=cn1078009011yicae\" target=\"_blank\">Report Item</a></h2><p><br></p><p style=\"text-align: start;\"><span style=\"font-size: 24px;\"><strong>Car Smart Finger Key Ring with Box Fit for Tesla Model 3 / Y All Year</strong></span><br></p><p style=\"text-align: start;\"><br></p><h3 style=\"text-align: start;\">Smart Ring Accessory for Tesla Model 3 /Y RFID Technology Key Card Key Fob Replacement</h3><p style=\"text-align: start;\">your Tesla Comes with an RFID reader implanted into the center of driver-side B-beam. The term RFID refers to radio-frequency identification. The implanted RFID reader uses electromagnetic fields to automatically identify the electronically stored information in your smart ring. And meanwhile, the smart ring accessory collects energy from the RFID reader\'s interrogating radio waves. Comparing to Tesla key fob and any other key fobs, there is no charge and battery needed for your smart ring.</p><p style=\"text-align: start;\">Your smart key ring is the perfect replacement for your original Tesla Model key cards. So you won’t need to buy additional key cards, key card covers, and key fobs. It’s also a great way to save some space in your pocket, your handbag, or your purse.</p><p><br></p><p>Specification:</p><p>Item Type: Smart Key Ring<br>Material: ceramics<br>Color: Black or white<br>Fitment:</p><p>Fit for Tesla model 3 All years<br>Fit for Tesla model Y All years</p><p><br></p><p><span style=\"color: rgb(211, 49, 21); font-size: 22px;\"><strong>Note:Do not fit for Tesla Model X/S</strong></span></p><p><br></p><p>How to Use:<br>1. Organize and clear the central control sensing area</p><p>2. Click the car shape button in the lower left corner of the central control screen</p><p>3. Click the middle lock button</p><p>4. Click the \"+\" to the right side of the key</p><p>5. Put our smart ring on the central control induction area</p><p>6. When the screen pops up \"Induction of existing key card to approve pairing\", place the original car key/card in the central control induction area for authorization and wait for completion.</p><p><br></p><p>Package List:</p><p>1 x Smart King</p><p>1 x Packing Box</p><p style=\"text-align: left;\"><br></p><p style=\"text-align: left;\"><br></p><p style=\"text-align: start;\"><br></p><p style=\"text-align: start;\"><img src=\"https://ae01.alicdn.com/kf/H7b7bb8f1bf23432dbc9fe656e1f7c2650.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H9b8e3a0cceae49deb0e3af11119947b9y.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Hfb942398d01b48d58fa4cef05c2777e18.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/He922470c84124fd2872de6cc9e8d4a1aH.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H21635ea963f9415997c19be5e70cf4ccV.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/Hae723e27693f4429984070495f1c3982O.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H692c33ec76b445d28df3ff392249dab48.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H19328561f32448e9a02066151bac2492a.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H7747eb4cad2d4b8d9e9e2e4401959f58o.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H824ba3a3d7a940d5ba91e5ef43dd3232L.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H4b4d82b0df8d4355b79b600779bf0bfbT.jpg\" alt=\"\" data-href=\"\" style=\"\"><img src=\"https://ae01.alicdn.com/kf/H69c0a2b1463a44cf8b46c9a4a42e710aO.jpg\" alt=\"\" data-href=\"\" style=\"\"></p><p style=\"text-align: start;\"><br></p>',NULL,'2024-11-22 22:20:04','2024-11-23 06:20:03');

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
