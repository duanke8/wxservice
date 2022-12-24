CREATE DATABASE /*!32312 IF NOT EXISTS*/`wxservice` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wxservice`;

DROP TABLE IF EXISTS `USER`;
CREATE TABLE USER (
  id INT AUTO_INCREMENT PRIMARY KEY,
  NAME VARCHAR (20) NOT NULL,
  FROM_USER_NAME VARCHAR (100) NOT NULL,
  TO_USER_NAME VARCHAR (100) NOT NULL,
  PHONE_NUMBER VARCHAR (20) NOT NULL
) ENGINE = INNODB DEFAULT CHARSET = utf8 ;

DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `order_id` varchar(100) DEFAULT NULL,
                              `order_info` longtext,
                              `order_images` longtext,
                              `create_time` datetime DEFAULT  CURRENT_TIMESTAMP,
                              `update_time` datetime DEFAULT  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `create_user_id` varchar(100) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `order_id_index` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
