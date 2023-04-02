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

CREATE TABLE `order_info`
(
    `id`              INT (11) NOT NULL AUTO_INCREMENT,
    `order_id`        VARCHAR(100) DEFAULT NULL,
    `order_info`      LONGTEXT,
    `order_images`    LONGTEXT,
    `create_time`     datetime     DEFAULT CURRENT_TIMESTAMP,
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id`  VARCHAR(100) DEFAULT NULL,
    `tracking_number` VARCHAR(30)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `order_id_index` (`order_id`) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE `file_info`
(
    `id`             INT (11) NOT NULL AUTO_INCREMENT,
    `file_name`      VARCHAR(100) DEFAULT NULL,
    `file_path`      VARCHAR(400) DEFAULT NULL,
    `name`           VARCHAR(100) DEFAULT NULL,
    `url`            VARCHAR(400) DEFAULT NULL,
    `create_time`    datetime     DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` VARCHAR(100) DEFAULT NULL,
    `description` VARCHAR(200) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

CREATE TABLE `icc_info` (
    `id` INT (11) NOT NULL AUTO_INCREMENT,
    `functionStr` VARCHAR (100) DEFAULT NULL COMMENT '功能',
    `command` VARCHAR (400) DEFAULT NULL COMMENT '命令',
    `remarks` VARCHAR (100) DEFAULT NULL COMMENT '备注',
    `type` VARCHAR (10) DEFAULT NULL COMMENT 'icc/8900',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_user_id` VARCHAR (100) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;
