CREATE DATABASE /*!32312 IF NOT EXISTS*/`wxservice` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wxservice`;

DROP TABLE IF EXISTS `RECOVER_YOUR_DATA`;

CREATE TABLE USER (
  id INT AUTO_INCREMENT PRIMARY KEY,
  NAME VARCHAR (20) NOT NULL,
  FROM_USER_NAME VARCHAR (100) NOT NULL,
  TO_USER_NAME VARCHAR (100) NOT NULL,
  PHONE_NUMBER VARCHAR (20) NOT NULL
) ENGINE = INNODB DEFAULT CHARSET = utf8 ;