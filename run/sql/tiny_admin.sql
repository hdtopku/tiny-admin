# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20077
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 0.0.0.0 (MySQL 8.0.40)
# 数据库: tiny_admin
# 生成时间: 2024-11-08 12:27:42 +0000
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
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `brand_name` varchar(64) DEFAULT NULL COMMENT '品牌名称',
  `brand_desc` varchar(256) DEFAULT NULL COMMENT '品牌简介',
  `logo` varchar(256) DEFAULT NULL COMMENT '品牌logo',
  `link_to` varchar(256) DEFAULT NULL COMMENT '链接至',
  `sort` int DEFAULT '1' COMMENT '排序(数值越大越靠后)',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态(0:未启用,1:启用)',
  `brand_story` text COMMENT '品牌故事',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='品牌表';

LOCK TABLES `pms_brand` WRITE;
/*!40000 ALTER TABLE `pms_brand` DISABLE KEYS */;

INSERT INTO `pms_brand` (`id`, `brand_name`, `brand_desc`, `logo`, `link_to`, `sort`, `status`, `brand_story`, `create_time`, `update_time`)
VALUES
	('1','华为','华为的愿景与使命是把数字世界带入每个人、每个家庭、每个组织，构建万物互联的智能世界。','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfFu0Tpk1H3KkolBdwWgl7pUsImrPu5kh5Ig&s',NULL,1,1,'华为创立于1987年，是全球领先的ICT（信息与通信）基础设施和智能终端提供商。我们的20.7万员工遍及170多个国家和地区，为全球30多亿人口提供服务。\n\n华为致力于把数字世界带入每个人、每个家庭、每个组织，构建万物互联的智能世界：让无处不在的联接，成为人人平等的权利，成为智能世界的前提和基础；为世界提供多样性算力，让云无处不在，让智能无所不及；所有的行业和组织，因强大的数字平台而变得敏捷、高效、生机勃勃；通过AI重新定义体验，让消费者在家居、出行、办公、影音娱乐、运动健康等全场景获得极致的个性化智慧体验。\n\n','2024-09-24 18:32:56','2024-09-24 18:33:35'),
	('1835771562238705666','耐克','耐克公司是世界运动和健康产品的领导者','https://www.sportspromedia.com/wp-content/uploads/2023/03/Nike.jpeg',NULL,1,1,'耐克(NIKE)公司，全球著名的体育用品制造商，总部位于美国俄勒冈州波特兰市。公司生产的体育用品包罗万象，例如服装、鞋类、运动器材等。NIKE英文含义指希腊胜利女神，商标图案是个小钩子，一直将激励全世界的每一位运动员并为其献上最好的产品视为光荣的任务。','2024-09-24 18:32:56','2024-09-24 18:33:37'),
	('1835780815108210690','tiny admin',' 企业级的中后台管理系统','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/f085681d-d8db-49ab-861a-c0502936a39b.png?alt=media',NULL,1,1,'Tiny Admin是一款基于BPM的低代码平台！前后端分离架构 SpringBoot 3.x，Ant Design&Vue3，Mybatis-plus，Shiro，JWT，支持微服务。','2024-09-24 18:32:56','2024-09-24 18:33:39'),
	('1835788859732967425','阿迪达斯','阿迪达牌(adidas)是一家总部立在德国个体育用品生产商，也是世界浪顶顶大个生产商之一，市场份额立在欧洲排名第一。','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/630bacd9-ab20-477f-875d-36fdb98ef8f9.png?alt=media',NULL,1,1,'阿迪达斯（德语：adidas）是一间德国运动用品的制造商，是阿迪达斯AG的成员公司。 阿迪达斯以其创办人阿道夫·达斯勒（Adi Dassler）命名，前身是1924年由鲁道夫·达斯勒和阿道夫·达斯勒两兄弟共同建立的“达斯勒兄弟鞋厂”，于纽伦堡附近的黑措根奥拉赫开始生产鞋类产品。','2024-09-24 18:32:56','2024-10-30 04:26:36'),
	('2','小米','永远相信美好的事情即将发生','https://upload.wikimedia.org/wikipedia/commons/a/ae/Xiaomi_logo_%282021-%29.svg',NULL,1,1,'小米集团成立于2010年4月，2018年7月9日在香港交易所主板挂牌上市(1810.HK)，是一家以智能手机、智能硬件和IoT平台为核心的消费电子及智能制造公司。\n\n胸怀“和用户交朋友，做用户心中最酷的公司”的愿景，小米致力于持续创新，不断追求极致的产品服务体验和公司运营效率，努力践行“始终坚持做感动人心、价格厚道的好产品，让全球每个人都能享受科技带来的美好生活”的公司使命。','2024-09-24 18:32:56','2024-09-24 18:33:42');

/*!40000 ALTER TABLE `pms_brand` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 pms_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pms_goods`;

CREATE TABLE `pms_goods` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `goods_name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `goods_desc` varchar(256) DEFAULT NULL COMMENT '商品简介',
  `album_pics` varchar(500) DEFAULT NULL COMMENT '商品图册，连产品图片限制为5张，以逗号分割',
  `sort` int DEFAULT '99999' COMMENT '排序(值越大越靠后)',
  `sale` int DEFAULT NULL COMMENT '销量',
  `stock` int DEFAULT NULL COMMENT '库存',
  `low_stock` int DEFAULT NULL COMMENT '库存预警值',
  `market_price` decimal(10,2) DEFAULT NULL COMMENT '市场价',
  `promotion_price` decimal(10,2) DEFAULT NULL COMMENT '促销价',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态(0:未启用,1:启用)',
  `detail_html` text COMMENT '产品详情网页内容',
  `brand_name` varchar(256) DEFAULT NULL COMMENT '品牌名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='商品表';

LOCK TABLES `pms_goods` WRITE;
/*!40000 ALTER TABLE `pms_goods` DISABLE KEYS */;

INSERT INTO `pms_goods` (`id`, `goods_name`, `goods_desc`, `album_pics`, `sort`, `sale`, `stock`, `low_stock`, `market_price`, `promotion_price`, `status`, `detail_html`, `brand_name`, `create_time`, `update_time`)
VALUES
	('124','银色星芒刺绣网纱底裤','开学季银色星芒刺绣网纱底裤','http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png',9999,115,1000,10,199.90,99.90,1,'<p>银色星芒刺绣网纱底裤</p>','其他','2024-09-20 04:14:46','2024-09-20 04:16:38'),
	('183','小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待','骁龙845处理器，红外人脸解锁，AI变焦双摄，AI语音助手小米6X低至1299，点击抢购',NULL,99999,NULL,9999,20,1999.00,1799.00,1,'<p style=\\\"text-align: center;\\\"><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/95935/9/19330/159477/5e9ecc13E5b8db8ae/8e954021a0835fb7.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99224/22/19596/137593/5e9ecc13E34ef2113/2b362b90d8378ee1.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/93131/25/19321/107691/5e9ecc13E41e8addf/202a5f84d9129878.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/101843/19/19533/66831/5e9ecc13Ecb7f9d53/4fdd807266583c1e.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99629/36/19016/59882/5e9ecc13E1f5beef5/1e5af3528f366e70.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/72343/29/8945/84548/5d6e5c67Ea07b1125/702791816b90eb3d.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/65403/35/9017/129532/5d6e5c68E3f2d0546/9ec771eb6e04a75a.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/58261/33/9380/106603/5d6e5c68E05a99177/2b5b9e29eed05b08.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51968/40/9688/113552/5d6e5c68E5052b312/8969d83124cb78a4.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75491/9/9101/146883/5d6e5c68E3db89775/c1aa57e78ded4e44.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75063/11/9107/127874/5d6e5c68E0b1dfc61/10dd6000ce213375.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/47452/25/9579/108279/5d6e5c68Ea9002f3b/865b5d32ffd9da75.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82146/26/9077/87075/5d6e5c68Ef63bccc8/556de5665a35a3f2.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82804/21/9019/124404/5d6e5c69E06a8f575/0f861f8c4636c546.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/46044/10/9734/107686/5d6e5c69Edd5e66c7/a8c7b9324e271dbd.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/40729/32/13755/45997/5d6e5c69Eafee3664/6a3457a4efdb79c5.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/76254/34/9039/96195/5d6e5c69E3c71c809/49033c0b7024c208.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/79825/20/9065/90864/5d6e5c69E1e62ef89/a4d3ce383425a666.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/49939/21/9618/106207/5d6e5c6aEf7b1d4fd/0f5e963c66be3d0c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/64035/7/9001/115159/5d6e5c6aE6919dfdf/39dfe4840157ad81.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/53389/3/9616/99637/5d6e5c6aEa33b9f35/b8f6aa26e72616a3.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/63219/6/9026/81576/5d6e5c6aEa9c74b49/b4fa364437531012.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/42146/27/13902/80437/5d6e5c6bE30c31ce9/475d4d54c7334313.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/45317/28/9596/78175/5d6e5c6bEce31e4b7/5675858b6933565c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/60080/1/9112/138722/5d6e5c6bEefd9fc62/7ece7460a36d2fcc.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51525/13/9549/36018/5d6e5c73Ebbccae6c/99bc2770dccc042b.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/61948/20/9088/72918/5d6e5c73Eab7aef5c/6f21e2f85cf478fa.jpg!q70.dpg.webp\\\" /></p>\', \'<p style=\\\"text-align: center;\\\"><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/95935/9/19330/159477/5e9ecc13E5b8db8ae/8e954021a0835fb7.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99224/22/19596/137593/5e9ecc13E34ef2113/2b362b90d8378ee1.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/93131/25/19321/107691/5e9ecc13E41e8addf/202a5f84d9129878.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/101843/19/19533/66831/5e9ecc13Ecb7f9d53/4fdd807266583c1e.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99629/36/19016/59882/5e9ecc13E1f5beef5/1e5af3528f366e70.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/72343/29/8945/84548/5d6e5c67Ea07b1125/702791816b90eb3d.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/65403/35/9017/129532/5d6e5c68E3f2d0546/9ec771eb6e04a75a.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/58261/33/9380/106603/5d6e5c68E05a99177/2b5b9e29eed05b08.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51968/40/9688/113552/5d6e5c68E5052b312/8969d83124cb78a4.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75491/9/9101/146883/5d6e5c68E3db89775/c1aa57e78ded4e44.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75063/11/9107/127874/5d6e5c68E0b1dfc61/10dd6000ce213375.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/47452/25/9579/108279/5d6e5c68Ea9002f3b/865b5d32ffd9da75.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82146/26/9077/87075/5d6e5c68Ef63bccc8/556de5665a35a3f2.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82804/21/9019/124404/5d6e5c69E06a8f575/0f861f8c4636c546.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/46044/10/9734/107686/5d6e5c69Edd5e66c7/a8c7b9324e271dbd.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/40729/32/13755/45997/5d6e5c69Eafee3664/6a3457a4efdb79c5.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/76254/34/9039/96195/5d6e5c69E3c71c809/49033c0b7024c208.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/79825/20/9065/90864/5d6e5c69E1e62ef89/a4d3ce383425a666.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/49939/21/9618/106207/5d6e5c6aEf7b1d4fd/0f5e963c66be3d0c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/64035/7/9001/115159/5d6e5c6aE6919dfdf/39dfe4840157ad81.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/53389/3/9616/99637/5d6e5c6aEa33b9f35/b8f6aa26e72616a3.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/63219/6/9026/81576/5d6e5c6aEa9c74b49/b4fa364437531012.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/42146/27/13902/80437/5d6e5c6bE30c31ce9/475d4d54c7334313.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/45317/28/9596/78175/5d6e5c6bEce31e4b7/5675858b6933565c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/60080/1/9112/138722/5d6e5c6bEefd9fc62/7ece7460a36d2fcc.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51525/13/9549/36018/5d6e5c73Ebbccae6c/99bc2770dccc042b.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/61948/20/9088/72918/5d6e5c73Eab7aef5c/6f21e2f85cf478fa.jpg!q70.dpg.webp\\\" /></p>',NULL,'2024-09-20 04:14:46','2024-10-25 23:12:09'),
	('189','小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待','骁龙845处理器，红外人脸解锁，AI变焦双摄，AI语音助手小米6X低至1299，点击抢购',NULL,99999,NULL,NULL,20,1999.00,1799.00,1,'<p style=\\\"text-align: center;\\\"><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/95935/9/19330/159477/5e9ecc13E5b8db8ae/8e954021a0835fb7.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99224/22/19596/137593/5e9ecc13E34ef2113/2b362b90d8378ee1.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/93131/25/19321/107691/5e9ecc13E41e8addf/202a5f84d9129878.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/101843/19/19533/66831/5e9ecc13Ecb7f9d53/4fdd807266583c1e.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99629/36/19016/59882/5e9ecc13E1f5beef5/1e5af3528f366e70.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/72343/29/8945/84548/5d6e5c67Ea07b1125/702791816b90eb3d.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/65403/35/9017/129532/5d6e5c68E3f2d0546/9ec771eb6e04a75a.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/58261/33/9380/106603/5d6e5c68E05a99177/2b5b9e29eed05b08.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51968/40/9688/113552/5d6e5c68E5052b312/8969d83124cb78a4.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75491/9/9101/146883/5d6e5c68E3db89775/c1aa57e78ded4e44.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75063/11/9107/127874/5d6e5c68E0b1dfc61/10dd6000ce213375.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/47452/25/9579/108279/5d6e5c68Ea9002f3b/865b5d32ffd9da75.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82146/26/9077/87075/5d6e5c68Ef63bccc8/556de5665a35a3f2.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82804/21/9019/124404/5d6e5c69E06a8f575/0f861f8c4636c546.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/46044/10/9734/107686/5d6e5c69Edd5e66c7/a8c7b9324e271dbd.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/40729/32/13755/45997/5d6e5c69Eafee3664/6a3457a4efdb79c5.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/76254/34/9039/96195/5d6e5c69E3c71c809/49033c0b7024c208.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/79825/20/9065/90864/5d6e5c69E1e62ef89/a4d3ce383425a666.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/49939/21/9618/106207/5d6e5c6aEf7b1d4fd/0f5e963c66be3d0c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/64035/7/9001/115159/5d6e5c6aE6919dfdf/39dfe4840157ad81.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/53389/3/9616/99637/5d6e5c6aEa33b9f35/b8f6aa26e72616a3.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/63219/6/9026/81576/5d6e5c6aEa9c74b49/b4fa364437531012.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/42146/27/13902/80437/5d6e5c6bE30c31ce9/475d4d54c7334313.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/45317/28/9596/78175/5d6e5c6bEce31e4b7/5675858b6933565c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/60080/1/9112/138722/5d6e5c6bEefd9fc62/7ece7460a36d2fcc.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51525/13/9549/36018/5d6e5c73Ebbccae6c/99bc2770dccc042b.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/61948/20/9088/72918/5d6e5c73Eab7aef5c/6f21e2f85cf478fa.jpg!q70.dpg.webp\\\" /></p>\', \'<p style=\\\"text-align: center;\\\"><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/95935/9/19330/159477/5e9ecc13E5b8db8ae/8e954021a0835fb7.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99224/22/19596/137593/5e9ecc13E34ef2113/2b362b90d8378ee1.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/93131/25/19321/107691/5e9ecc13E41e8addf/202a5f84d9129878.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/101843/19/19533/66831/5e9ecc13Ecb7f9d53/4fdd807266583c1e.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWareDetail/jfs/t1/99629/36/19016/59882/5e9ecc13E1f5beef5/1e5af3528f366e70.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/72343/29/8945/84548/5d6e5c67Ea07b1125/702791816b90eb3d.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/65403/35/9017/129532/5d6e5c68E3f2d0546/9ec771eb6e04a75a.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/58261/33/9380/106603/5d6e5c68E05a99177/2b5b9e29eed05b08.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51968/40/9688/113552/5d6e5c68E5052b312/8969d83124cb78a4.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75491/9/9101/146883/5d6e5c68E3db89775/c1aa57e78ded4e44.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/75063/11/9107/127874/5d6e5c68E0b1dfc61/10dd6000ce213375.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/47452/25/9579/108279/5d6e5c68Ea9002f3b/865b5d32ffd9da75.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82146/26/9077/87075/5d6e5c68Ef63bccc8/556de5665a35a3f2.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/82804/21/9019/124404/5d6e5c69E06a8f575/0f861f8c4636c546.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/46044/10/9734/107686/5d6e5c69Edd5e66c7/a8c7b9324e271dbd.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/40729/32/13755/45997/5d6e5c69Eafee3664/6a3457a4efdb79c5.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/76254/34/9039/96195/5d6e5c69E3c71c809/49033c0b7024c208.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/79825/20/9065/90864/5d6e5c69E1e62ef89/a4d3ce383425a666.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/49939/21/9618/106207/5d6e5c6aEf7b1d4fd/0f5e963c66be3d0c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/64035/7/9001/115159/5d6e5c6aE6919dfdf/39dfe4840157ad81.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/53389/3/9616/99637/5d6e5c6aEa33b9f35/b8f6aa26e72616a3.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/63219/6/9026/81576/5d6e5c6aEa9c74b49/b4fa364437531012.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/42146/27/13902/80437/5d6e5c6bE30c31ce9/475d4d54c7334313.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/45317/28/9596/78175/5d6e5c6bEce31e4b7/5675858b6933565c.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/60080/1/9112/138722/5d6e5c6bEefd9fc62/7ece7460a36d2fcc.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/51525/13/9549/36018/5d6e5c73Ebbccae6c/99bc2770dccc042b.jpg!q70.dpg.webp\\\" /><img src=\\\"//img30.360buyimg.com/popWaterMark/jfs/t1/61948/20/9088/72918/5d6e5c73Eab7aef5c/6f21e2f85cf478fa.jpg!q70.dpg.webp\\\" /></p>',NULL,'2024-09-20 04:14:46','2024-10-03 04:31:31'),
	('919','Huawei HUAWEI P20 upgraded version','AI智慧全面屏 6GB +64GB 亮黑色 全网通版 移动联通电信4G手机 双卡双待手机 双卡双待','http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ab46a3cN616bdc41.jpg,http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf5fN2522b9dc.jpg',100,65,991,3,2999.00,2499.00,1,'<p><img src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44f1cNf51f3bb0.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa8Nfcf71c10.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa9N40e78ee0.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f4N1c94bdda.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f5Nd30de41d.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5b10fb0eN0eb053fb.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>','华为','2024-09-20 04:14:46','2024-11-05 19:14:10');

/*!40000 ALTER TABLE `pms_goods` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_banner
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_banner`;

CREATE TABLE `sms_banner` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `banner_name` varchar(64) DEFAULT NULL COMMENT '轮播卡片名称',
  `pic_url` varchar(256) DEFAULT NULL COMMENT '图片链接',
  `platform` int DEFAULT '1' COMMENT '轮播平台(1:PC,2:APP)',
  `link_to` varchar(256) DEFAULT NULL COMMENT '链接至',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `sort` int DEFAULT '9999' COMMENT '排序(值越大越靠后)',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否生效(0:未生效,1:已生效)',
  `click_count` int DEFAULT '0' COMMENT '点击次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='轮播表';

LOCK TABLES `sms_banner` WRITE;
/*!40000 ALTER TABLE `sms_banner` DISABLE KEYS */;

INSERT INTO `sms_banner` (`id`, `banner_name`, `pic_url`, `platform`, `link_to`, `remark`, `sort`, `status`, `click_count`, `create_time`, `update_time`)
VALUES
	('1838308281244774401','OPPO Reno8 新品大促','http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20221108/oppo_banner_01.png',1,'https://www.oppo.com/uk','Oppo 双十一大促，全场9.5 折',9999,1,0,'2024-09-23 21:03:43','2024-10-31 02:55:03');

/*!40000 ALTER TABLE `sms_banner` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_brand
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_brand`;

CREATE TABLE `sms_brand` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `brand_id` varchar(32) NOT NULL DEFAULT '' COMMENT '品牌ID',
  `sort` int DEFAULT '9999' COMMENT '排序(值越大越靠后)',
  `remark` varchar(256) DEFAULT NULL COMMENT '品牌名称',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否生效(0:未生效,1:已生效)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='品牌推荐表';

LOCK TABLES `sms_brand` WRITE;
/*!40000 ALTER TABLE `sms_brand` DISABLE KEYS */;

INSERT INTO `sms_brand` (`id`, `brand_id`, `sort`, `remark`, `status`, `create_time`, `update_time`)
VALUES
	('1839780146333114370','1',9999,'华为',1,'2024-09-27 22:32:23','2024-09-28 05:33:49'),
	('42531','2',9999,'',1,'2024-09-24 17:45:23','2024-09-24 18:41:17'),
	('8028302','1835771562238705666',9999,'耐克双十一全场 9.5 折',1,'2024-09-24 17:45:38','2024-10-31 17:42:01');

/*!40000 ALTER TABLE `sms_brand` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_flash_goods_rel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_flash_goods_rel`;

CREATE TABLE `sms_flash_goods_rel` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `flash_id` varchar(32) NOT NULL DEFAULT '' COMMENT '秒杀ID',
  `goods_id` varchar(32) NOT NULL DEFAULT '' COMMENT '商品ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flash_id` (`flash_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户角色关系表';

LOCK TABLES `sms_flash_goods_rel` WRITE;
/*!40000 ALTER TABLE `sms_flash_goods_rel` DISABLE KEYS */;

INSERT INTO `sms_flash_goods_rel` (`id`, `flash_id`, `goods_id`)
VALUES
	('1841061529420566529','123','123'),
	('1841843215053987841','1840800346738278402','124'),
	('1841841820217221121','1840800346738278402','183');

/*!40000 ALTER TABLE `sms_flash_goods_rel` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_flash_sale
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_flash_sale`;

CREATE TABLE `sms_flash_sale` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `activity_name` varchar(128) NOT NULL DEFAULT '' COMMENT '活动名称',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `sort` int DEFAULT '9999' COMMENT '排序(数字越大越靠后)',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态(0:已禁用,1:已启用)',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='秒杀表';

LOCK TABLES `sms_flash_sale` WRITE;
/*!40000 ALTER TABLE `sms_flash_sale` DISABLE KEYS */;

INSERT INTO `sms_flash_sale` (`id`, `activity_name`, `remark`, `sort`, `status`, `start_time`, `end_time`, `create_time`, `update_time`)
VALUES
	('1840800346738278402','国庆大促销','特价活动，仅此一天！特价活动，仅此一天！特价活动，仅此一天！特价活动，仅此一天！',9999,1,'2024-09-24 18:05:42','2024-10-01 18:05:47','2024-09-30 18:06:18','2024-11-07 02:54:52');

/*!40000 ALTER TABLE `sms_flash_sale` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_hot_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_hot_goods`;

CREATE TABLE `sms_hot_goods` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `goods_id` varchar(32) NOT NULL DEFAULT '' COMMENT '品牌ID',
  `sort` int DEFAULT '9999' COMMENT '排序(值越大越靠后)',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否生效(0:未生效,1:已生效)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='热销商品推荐表';

LOCK TABLES `sms_hot_goods` WRITE;
/*!40000 ALTER TABLE `sms_hot_goods` DISABLE KEYS */;

INSERT INTO `sms_hot_goods` (`id`, `goods_id`, `sort`, `remark`, `status`, `create_time`, `update_time`)
VALUES
	('1853765046761472001','919',9999,NULL,1,'2024-11-05 11:43:23','2024-11-07 06:20:39'),
	('1853765046765666306','189',9999,NULL,1,'2024-11-05 11:43:23','2024-11-07 02:24:52'),
	('1853765046769860609','124',9999,NULL,1,'2024-11-05 11:43:23','2024-11-07 02:24:58');

/*!40000 ALTER TABLE `sms_hot_goods` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sms_new_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_new_goods`;

CREATE TABLE `sms_new_goods` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `goods_id` varchar(32) NOT NULL DEFAULT '' COMMENT '品牌ID',
  `sort` int DEFAULT '9999' COMMENT '排序(值越大越靠后)',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否生效(0:未生效,1:已生效)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='新品推荐表';

LOCK TABLES `sms_new_goods` WRITE;
/*!40000 ALTER TABLE `sms_new_goods` DISABLE KEYS */;

INSERT INTO `sms_new_goods` (`id`, `goods_id`, `sort`, `remark`, `status`, `create_time`, `update_time`)
VALUES
	('1853764601431244802','124',9999,NULL,1,'2024-11-05 11:41:37','2024-11-05 19:41:37'),
	('1853764601456410625','183',9999,NULL,1,'2024-11-05 11:41:37','2024-11-05 19:41:37');

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
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `parent_id` varchar(32) DEFAULT '' COMMENT '父级ID',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `redirect` varchar(255) DEFAULT NULL COMMENT '一级菜单跳转地址',
  `type` int DEFAULT '1' COMMENT '菜单类型(1:菜单;2:按钮权限)',
  `unauthorized_strategy` int DEFAULT NULL COMMENT '未授权时：按钮(0:隐藏;1:显示但不可点击:2:显示按钮(可点击，但提交失败)；菜单重定向至(0:404页面;1:403 页面)',
  `permission` varchar(255) DEFAULT NULL COMMENT '菜单权限编码',
  `sort` int DEFAULT '9999' COMMENT '菜单排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `keep_alive` tinyint(1) DEFAULT '1' COMMENT '是否缓存该页面(0:否;1:是)',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏路由(0:否;1:是)',
  `hidden_tab` int DEFAULT '0' COMMENT '是否隐藏 tab(0:否;1:是)',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除状态(0:正常;1:已删除)',
  `status` varchar(2) DEFAULT '0' COMMENT '按钮权限状态(0:有效;1:无效)',
  `internal_or_external` tinyint(1) DEFAULT '0' COMMENT '外链菜单打开方式(0:内部打开;1:外部打开)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_menu_type` (`type`) USING BTREE,
  KEY `index_menu_hidden` (`hidden`) USING BTREE,
  KEY `index_menu_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `component`, `redirect`, `type`, `unauthorized_strategy`, `permission`, `sort`, `icon`, `keep_alive`, `hidden`, `hidden_tab`, `description`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`, `status`, `internal_or_external`)
VALUES
	('1','','首页','/home','Home','',1,0,'',1,'HomeFilled',1,0,0,NULL,NULL,'2024-06-08 00:30:23',NULL,'2024-10-29 04:25:25',0,'0',0),
	('1805162239428915202','6','代码测试','/tool/test','tool/Test',NULL,1,0,NULL,9990,'BugOutlined',1,0,0,NULL,NULL,'2024-06-24 09:53:11',NULL,'2024-10-03 00:39:53',0,'0',0),
	('1807047220929433602','3','新增用户','','',NULL,2,0,'user:add',9999,'',1,0,0,NULL,NULL,'2024-06-29 14:43:25',NULL,'2024-07-11 18:18:10',0,'0',0),
	('1810315067529027585','5','新增菜单',NULL,NULL,NULL,2,0,'menu:add',9999,NULL,1,0,0,NULL,NULL,'2024-07-08 15:08:41',NULL,'2024-07-11 18:18:13',0,'0',0),
	('1810319487910207490','4','新增角色','/system/sysrole/saveorupdate',NULL,NULL,2,1,'role:add',9999,NULL,1,0,0,NULL,NULL,'2024-07-08 15:26:15',NULL,'2024-08-14 20:50:36',0,'0',0),
	('1812067616699441153','5','修改菜单','/system/sysmenu/saveorupdate',NULL,NULL,2,2,'menu:update',9999,NULL,1,0,0,NULL,NULL,'2024-07-13 11:12:41',NULL,'2024-07-13 21:50:53',0,'0',0),
	('1812129343856115714','5','获取菜单列表','/system/sysmenu/tree','system/sysMenu/tree',NULL,2,2,'system:sysmenu:tree',9999,NULL,1,0,0,NULL,NULL,'2024-07-13 15:17:58',NULL,'2024-07-13 22:26:06',0,'0',0),
	('1812132225640759297','4','获取角色列表','/system/sysrole/page','system/sysRole/page',NULL,2,2,'system:sysrole:page',9999,NULL,1,0,0,NULL,NULL,'2024-07-13 15:29:25',NULL,'2024-07-16 16:49:25',0,'0',0),
	('1812160122644553730','5','删除菜单','/system/sysmenu/deletebyids',NULL,NULL,2,1,'system:sysmenu:deletebyids',9999,NULL,1,0,0,NULL,NULL,'2024-07-13 17:20:16',NULL,'2024-07-14 00:20:17',0,'0',0),
	('1823704170941317121','3','获取用户列表','/system/sysuser/page',NULL,NULL,2,0,'system:sysuser:page',9999,NULL,1,0,0,NULL,NULL,'2024-08-14 13:52:12',NULL,'2024-08-14 20:52:12',0,'0',0),
	('1834991127946592258','','商品管理','/pms','',NULL,1,0,'pms',10,'ShoppingFilled',1,0,0,NULL,NULL,'2024-09-14 17:22:32',NULL,'2024-09-17 05:20:38',0,'0',0),
	('1834992374506323970','1834991127946592258','品牌管理','/pms/brand','pms/brand/index',NULL,1,0,'pms:brand',9999,'CopyrightCircleOutlined',1,0,0,NULL,NULL,'2024-09-14 17:27:29',NULL,'2024-09-19 18:21:52',0,'0',0),
	('1836710946790227969','1834991127946592258','商品列表','/pms/goods','pms/goods/index',NULL,1,1,'pms:goods',9899,'ShoppingCartOutlined',0,0,0,NULL,NULL,'2024-09-19 11:16:29',NULL,'2024-09-19 18:27:55',0,'0',0),
	('1838149623168974849','','营销管理','/sms','sms',NULL,1,0,'sms',60,'ThunderboltFilled',1,0,0,NULL,NULL,'2024-09-23 10:33:16',NULL,'2024-09-23 17:41:55',0,'0',0),
	('1838149997980368897','1838149623168974849','品牌推荐','/sms/brand','sms/brand/index',NULL,1,0,'sms:brand',9919,'CopyrightCircleTwoTone',1,0,0,NULL,NULL,'2024-09-23 10:34:45',NULL,'2024-09-24 17:22:29',0,'0',0),
	('1838151619515072513','1838149623168974849','热销商品','/sms/hotgoods','sms/hotgoods/index',NULL,1,0,'sms:hotgoods',9949,'FireTwoTone',1,0,0,NULL,NULL,'2024-09-23 10:41:12',NULL,'2024-09-28 17:13:06',0,'0',0),
	('1838154441195339777','1838149623168974849','新品推荐','/sms/newgoods','sms/newgoods/index',NULL,1,0,'sms:newgoods',9939,'SoundTwoTone',1,0,0,NULL,NULL,'2024-09-23 10:52:25',NULL,'2024-09-28 17:12:01',0,'0',0),
	('1838157722969784322','1838149623168974849','首页轮播','/sms/banner','sms/banner/index',NULL,1,0,'sms:banner',9900,'PushpinTwoTone',1,0,0,NULL,NULL,'2024-09-23 11:05:27',NULL,'2024-09-24 02:58:43',0,'0',0),
	('1839958739902291969','1838149623168974849','秒杀专场','/sms/flashsale','sms/flashsale/index',NULL,1,0,'sms:flashsale',9999,'ClockCircleTwoTone',1,0,0,NULL,NULL,'2024-09-28 10:22:03',NULL,'2024-09-28 17:33:54',0,'0',0),
	('1845084689682001922','2','国际化','/system/i18n','system/i18n/index',NULL,1,0,'system:i18n',9999,'GlobalOutlined',1,0,0,NULL,NULL,'2024-10-12 13:50:45',NULL,'2024-10-12 20:54:26',0,'0',0),
	('2','','系统管理','/system',NULL,'/system/user',1,0,NULL,20,'SettingFilled',1,0,0,NULL,NULL,'2024-06-08 00:36:56',NULL,'2024-10-20 03:31:09',0,'0',0),
	('3','2','用户管理','/system/user','system/user/index','',1,0,NULL,4,'UserOutlined',0,0,0,NULL,NULL,'2024-06-08 00:43:31',NULL,'2024-07-21 00:10:41',0,'0',0),
	('4','2','角色管理','/system/role','system/role/index',NULL,1,0,NULL,5,'ContactsOutlined',1,0,0,NULL,NULL,'2024-06-08 01:04:55',NULL,'2024-09-15 00:31:26',0,'0',0),
	('5','2','菜单管理','/system/menu','system/menu/index',NULL,1,0,NULL,6,'BarsOutlined',1,0,0,NULL,NULL,'2024-06-08 01:06:12',NULL,'2024-10-29 03:51:46',0,'0',0),
	('6','','开发工具','/tool','','',1,0,NULL,100,'CodeFilled',1,0,0,NULL,NULL,'2024-06-08 01:07:25',NULL,'2024-09-15 00:29:47',0,'0',0),
	('7','6','代码生成','/tool/code','tool/Code','',1,1,NULL,9995,'ConsoleSqlOutlined',1,0,0,NULL,NULL,'2024-06-08 01:08:00',NULL,'2024-10-03 00:40:06',0,'0',0);

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `role_name` varchar(200) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint DEFAULT '1' COMMENT '状态(0:禁用;1:正常)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `role_name`, `description`, `create_by`, `create_time`, `update_by`, `update_time`, `status`)
VALUES
	('1','超级管理员','拥有最高权限',NULL,'2024-06-08 01:09:13',NULL,'2024-10-28 02:44:19',1),
	('1807495103998803969','聊天客户','拥有聊天权限1',NULL,'2024-06-30 20:23:09',NULL,'2024-10-28 03:28:15',1),
	('1810353969174351874','test','testtest11',NULL,'2024-07-08 17:43:16',NULL,'2024-10-28 03:28:33',1),
	('1823703364108222466','test1',NULL,NULL,'2024-08-14 13:48:59',NULL,'2024-10-27 18:52:28',1),
	('2','开发者','拥有开发者权限',NULL,'2024-06-08 01:09:34',NULL,'2024-06-21 16:54:23',1);

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_role_menu_rel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_menu_rel`;

CREATE TABLE `sys_role_menu_rel` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_role_menu` (`role_id`,`menu_id`),
  KEY `key_role_id` (`role_id`),
  KEY `key_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色菜单表';

LOCK TABLES `sys_role_menu_rel` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_rel` DISABLE KEYS */;

INSERT INTO `sys_role_menu_rel` (`id`, `role_id`, `menu_id`)
VALUES
	('1845085075339866114','1','1'),
	('1845085075264368641','1','1805162239428915202'),
	('1845085075365031937','1','1807047220929433602'),
	('1845085075373420545','1','1810315067529027585'),
	('1845085075314700289','1','1810319487910207490'),
	('1845085075297923074','1','1812067616699441153'),
	('1845085075348254721','1','1812129343856115714'),
	('1845085075327283201','1','1812132225640759297'),
	('1845085075302117378','1','1812160122644553730'),
	('1845085075289534466','1','1823704170941317121'),
	('1845085075331477505','1','1834991127946592258'),
	('1845085075281145857','1','1834992374506323970'),
	('1845085075293728769','1','1836710946790227969'),
	('1845085075335671810','1','1838149623168974849'),
	('1845085075323088897','1','1838149997980368897'),
	('1845085075386003458','1','1838151619515072513'),
	('1845085075306311682','1','1838154441195339777'),
	('1845085075285340162','1','1838157722969784322'),
	('1845085075285340163','1','1839958739902291969'),
	('1845085075318894594','1','1845084689682001922'),
	('1845085075352449026','1','2'),
	('1845085075360837634','1','3'),
	('1845085075365031938','1','4'),
	('1845085075377614850','1','5'),
	('1845085075377614851','1','6'),
	('1845085075381809153','1','7'),
	('1850487600886562819','1810353969174351874','1'),
	('1850487600882368514','1810353969174351874','1805162239428915202'),
	('1850487600886562824','1810353969174351874','1807047220929433602'),
	('1850487600886562822','1810353969174351874','1810315067529027585'),
	('1850487600882368515','1810353969174351874','1810319487910207490'),
	('1850487600890757125','1810353969174351874','1812067616699441153'),
	('1850487600886562818','1810353969174351874','1812129343856115714'),
	('1850487600882368516','1810353969174351874','1812132225640759297'),
	('1850487600853008385','1810353969174351874','1812160122644553730'),
	('1850487600890757122','1810353969174351874','1823704170941317121'),
	('1850487600886562820','1810353969174351874','2'),
	('1850487600886562821','1810353969174351874','3'),
	('1850487600886562823','1810353969174351874','4'),
	('1850487600890757121','1810353969174351874','5'),
	('1850487600890757123','1810353969174351874','6'),
	('1850487600890757124','1810353969174351874','7'),
	('1850487908815585281','1823703364108222466','1');

/*!40000 ALTER TABLE `sys_role_menu_rel` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用;1:正常)',
  `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除状态(0:已删除;1:正常)',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `username`, `nickname`, `password`, `avatar`, `email`, `phone`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES
	('1','admin','admin123123','$2a$10$lBNMUzBbxThm0r7OENVT5On7VV7OgKs4rC.GGkWfMJg3OKMWBjeHa','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/eaae417a-7cbb-4486-bf20-9c3ea3334f4e.png?alt=media','1450948930@qq.com','01234567890',1,1,NULL,'2024-06-08 01:28:20',NULL,'2024-10-19 19:31:11'),
	('1807493848987549697','operation',' 运营1','$2a$10$tvdAWQPGwI6QYbp3Z9nbFOk1nKcSoJJcODFKZni6j5vMf8cTHlHDu','https://firebasestorage.googleapis.com/v0/b/tiny-blog-1a77f.appspot.com/o/feffbcb7-fc74-4706-9eb2-42a05415bbc2.png?alt=media','1450948930@qq.com','123456789',1,1,NULL,'2024-06-30 20:18:10',NULL,'2024-10-26 21:29:26'),
	('2','test','test','$2a$10$WKeSgL0QYmepFKyii/UcUuja8VWDzg52KJ6sE7G93kXbjfHNXuwi6',NULL,'test12@qq.com','123456',1,1,NULL,'2024-06-08 01:28:20',NULL,'2024-10-26 21:27:45');

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_user_role_rel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role_rel`;

CREATE TABLE `sys_user_role_rel` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_2` (`user_id`,`role_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户角色关系表';

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
