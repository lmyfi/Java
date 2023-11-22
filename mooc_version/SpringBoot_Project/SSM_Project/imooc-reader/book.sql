/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.12 : Database - imooc-reader
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`imooc-reader` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `imooc-reader`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `book_name` varchar(64) NOT NULL COMMENT '书名',
  `sub_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '子标题',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `cover` varchar(255) NOT NULL COMMENT '图片封面url',
  `description` text NOT NULL COMMENT '图书详情',
  `category_id` bigint(20) NOT NULL COMMENT '分类编号',
  `evaluation_scope` float NOT NULL COMMENT '图书评分',
  `evaluation_quantity` int(11) NOT NULL COMMENT '评价数量',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `book` */

insert  into `book`(`book_id`,`book_name`,`sub_name`,`author`,`cover`,`description`,`category_id`,`evaluation_scope`,`evaluation_quantity`) values 
(1,'测试','测试1','某人','111','3333',1,2,1);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `category` */

insert  into `category`(`category_id`,`category_name`) values 
(1,'前端'),
(2,'后端'),
(3,'测试'),
(4,'产品');

/*Table structure for table `evaluation` */

DROP TABLE IF EXISTS `evaluation`;

CREATE TABLE `evaluation` (
  `evaluation_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '评价编号',
  `content` varchar(255) NOT NULL COMMENT '短评内容',
  `score` int(11) NOT NULL COMMENT '评分-5分制',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `member_id` bigint(20) NOT NULL COMMENT '会员id',
  `book_id` bigint(20) NOT NULL COMMENT '图书id',
  `enjoy` int(11) NOT NULL COMMENT '点赞数量',
  `state` varchar(16) NOT NULL COMMENT '审核状态：enable有效，disable已禁用',
  `disable_reason` varchar(255) DEFAULT NULL COMMENT '禁用理由',
  `disable_time` datetime DEFAULT NULL COMMENT '禁用时间',
  PRIMARY KEY (`evaluation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `evaluation` */

insert  into `evaluation`(`evaluation_id`,`content`,`score`,`create_time`,`member_id`,`book_id`,`enjoy`,`state`,`disable_reason`,`disable_time`) values 
(1,'测试评论',2,'2023-05-04 16:14:16',1,1,1,'enable',NULL,NULL);

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `member_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '会员编号',
  `username` varchar(16) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` int(11) NOT NULL COMMENT '加盐',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `nickname` varchar(16) NOT NULL COMMENT '昵称',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `member` */

insert  into `member`(`member_id`,`username`,`password`,`salt`,`create_time`,`nickname`) values 
(1,'测试','1111111',1234,'2023-05-04 16:15:29','测试'),
(2,'s123456','a79e3eb6cd74634896d2c8fd8cdacbb4',1517,'2023-05-04 21:34:01','s测试');

/*Table structure for table `member_read_state` */

DROP TABLE IF EXISTS `member_read_state`;

CREATE TABLE `member_read_state` (
  `rs_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `book_id` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL,
  `read_state` int(11) NOT NULL COMMENT '阅读状态，1想看，2看过',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`rs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `member_read_state` */

insert  into `member_read_state`(`rs_id`,`book_id`,`member_id`,`read_state`,`create_time`) values 
(1,1,1,1,'2023-05-04 16:16:01');

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `content` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `test` */

insert  into `test`(`id`,`content`) values 
(20,'测试MyBatisPlus1');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `salt` varchar(32) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
