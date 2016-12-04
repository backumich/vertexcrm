CREATE TABLE Users
(
  user_id       INT PRIMARY KEY,
  email         VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  first_name    VARCHAR(50)  NOT NULL,
  last_name     VARCHAR(50)  NOT NULL,
  passport_scan BLOB,
  photo         BLOB,
  discount      INT,
  phone         VARCHAR(25)
);

CREATE TABLE Certificate
(
  certification_id   INT PRIMARY KEY,
  user_id            INT,
  certification_date DATE         NOT NULL,
  course_name        VARCHAR(500) NOT NULL,
  language           VARCHAR(255) NOT NULL
);

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone)
VALUES ('22', 'email', 'password', 'FirstName', 'LastName', NULL, NULL, '0', '38066 000 00 00');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('222', '22', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('333', NULL, '2016-12-1', 'Java Professional', 'Java')
