CREATE TABLE Users
(
  user_id       INT(11)      NOT NULL AUTO_INCREMENT,
  email         VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  first_name    VARCHAR(50)  NOT NULL,
  last_name     VARCHAR(50)  NOT NULL,
  passport_scan BLOB,
  photo         BLOB,
  discount      INT,
  phone         VARCHAR(25),
  role_id       INT(11),
  PRIMARY KEY (user_id)
);

CREATE TABLE Certificate
(
  certification_id   INT PRIMARY KEY,
  user_id            INT,
  certification_date DATE         NOT NULL,
  course_name        VARCHAR(500) NOT NULL,
  language           VARCHAR(255) NOT NULL
);

CREATE TABLE Roles
(
  role_id     INT(11)       NOT NULL AUTO_INCREMENT,
  name        VARCHAR(50)   NOT NULL,
  PRIMARY KEY (role_id)
);

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone)
VALUES ('1', 'email1', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone)
VALUES ('22', 'email', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone)
VALUES ('33', 'email', 'password', 'FirstName', 'LastName', NULL, NULL, '0', '38066 000 00 00');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('222', '22', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('333', '33', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('500', NULL, '2016-12-1', 'Java Professional', 'Java');


INSERT INTO Users(user_id, email, password, first_name, last_name , discount, phone, role_id)
VALUES(10, 'emailTest', '2222222', 'first_name', 'last_name', 0, '666666666', 1);

-- INSERT INTO Roles (role_id, name) VALUES(1, 'Admin');
-- INSERT INTO Roles (role_id, name) VALUES(2, 'User');
--
-- INSERT INTO Certificate(certification_id, user_id, certification_date, course_name, language)
-- VALUES(2, 1, '2016-11-08', 'Java Professional', 'Java');
-- INSERT INTO Certificate(certification_id, user_id, certification_date, course_name, language)
-- VALUES(32, 1, '2017-01-25', 'Java OOP', 'Ru');