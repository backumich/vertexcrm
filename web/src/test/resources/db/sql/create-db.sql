-- --------------------------------------------------------
-- Хост:                         seadev.tk
-- Версия сервера:               5.7.16 - MySQL Community Server (GPL)
-- Операционная система:         Linux
-- HeidiSQL Версия:              9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = NO_AUTO_VALUE_ON_ZERO */;

-- # -- Дамп структуры базы данных db1
-- # CREATE DATABASE IF NOT EXISTS db1 /*!40100 DEFAULT CHARACTER SET utf8 COLLATE 'utf8_unicode_ci' */;
-- # USE db1;


-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица db1.Roles
-- CREATE TABLE IF NOT EXISTS Roles (
-- role_id int(11) NOT NULL AUTO_INCREMENT,
-- name varchar(50) COLLATE 'utf8_unicode_ci' DEFAULT NULL,
-- PRIMARY KEY (role_id)
-- ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE='utf8_unicode_ci';

CREATE TABLE Roles (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  name    VARCHAR(50)      DEFAULT NULL,
  PRIMARY KEY (role_id)
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица db1.Users
CREATE TABLE Users (
  user_id       INT(11) NOT NULL          AUTO_INCREMENT,
  email         VARCHAR(255)
                COLLATE 'utf8_unicode_ci' DEFAULT NULL,
  password      VARCHAR(255)
                COLLATE 'utf8_unicode_ci' DEFAULT NULL,
  first_name    VARCHAR(50)
                COLLATE 'utf8_unicode_ci' DEFAULT NULL,
  last_name     VARCHAR(50)
                COLLATE 'utf8_unicode_ci' DEFAULT NULL,
  passport_scan LONGBLOB,
  photo         LONGBLOB,
  discount      INT(11)                   DEFAULT NULL,
  phone         VARCHAR(25)
                COLLATE 'utf8_unicode_ci' DEFAULT NULL,
  role_id       INT(11)                   DEFAULT NULL,
  PRIMARY KEY (user_id)
);

-- Дамп структуры для таблица db1.Accounting
CREATE TABLE Accounting (
  deal_id        INT(11) NOT NULL AUTO_INCREMENT,
  user_id        INT(11)          DEFAULT NULL,
  certificate_id INT(11)          DEFAULT NULL,
  course_coast   DOUBLE(16, 2)    DEFAULT NULL,
  debt           DOUBLE(16, 2)    DEFAULT NULL,
  PRIMARY KEY (deal_id)
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица db1.Payments
CREATE TABLE IF NOT EXISTS Payments (
  payment_id INT(11) NOT NULL AUTO_INCREMENT,
  deal_id    INT(11)          DEFAULT NULL,
  amount     DOUBLE(16, 2)    DEFAULT NULL,
  PRIMARY KEY (payment_id)
);

-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица db1.Certificate
CREATE TABLE IF NOT EXISTS Certificate (
  certification_id   INT(11) NOT NULL          AUTO_INCREMENT,
  user_id            INT(11)                   DEFAULT NULL,
  certification_date DATE                      DEFAULT NULL,
  course_name        VARCHAR(500)
                     COLLATE 'utf8_unicode_ci' DEFAULT NULL,
  language           VARCHAR(255)
                     COLLATE 'utf8_unicode_ci' DEFAULT NULL
);
-- Экспортируемые данные не выделены.
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
