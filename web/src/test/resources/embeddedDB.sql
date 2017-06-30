CREATE TABLE Users
(
  user_id       INT(11)      NOT NULL AUTO_INCREMENT,
  email         VARCHAR(255) NOT NULL,
  password      VARCHAR(255),
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
  certification_id   INT AUTO_INCREMENT PRIMARY KEY,
  user_id            INT,
  certification_date DATE         NOT NULL,
  course_name        VARCHAR(500) NOT NULL,
  language           VARCHAR(255) NOT NULL
);

CREATE TABLE Roles
(
  role_id INT(11)     NOT NULL AUTO_INCREMENT,
  name    VARCHAR(50) NOT NULL,
  PRIMARY KEY (role_id)
);

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone)
VALUES ('1', 'email1', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('22', '22@test.com', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00', '2');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('33', '33@test.com', 'password', 'FirstName', 'LastName', NULL, NULL, '0', '38066 000 00 00', '2');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('44', '44@test.com', '$2a$12$lJElN7.2IR4YCueJNTPp9eUbRlXrYlP3M71dHc1czmKaqtJCvzhtS',
        'FirstName', 'LastName', NULL, NULL, '0', '38066 000 00 00', '2');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('222', '22', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('333', '33', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('500', NULL, '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('1', '1', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('2', '2', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language)
VALUES ('3', '2', '2016-12-1', 'Java Professional', 'Java');

INSERT INTO Users (user_id, email, password, first_name, last_name, discount, phone, role_id)
VALUES (10, 'emailTest', '2222222', 'first_name', 'last_name', 0, '666666666', 1);

CREATE TABLE courses (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  start timestamp NULL DEFAULT NULL,
  finished tinyint(1) DEFAULT NULL,
  price decimal(19, 2) DEFAULT NULL,
  teacher_name varchar(255) DEFAULT NULL,
  schedule varchar(255) DEFAULT NULL,
  notes varchar(999) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO Courses VALUES
  (111, 'Super JAVA', '2017-04-01 00:00:00', 0, 999999.00, 'Yo Ho Ho', 'Sat, Sun', 'Welcome, we don''t expect you (='),
  (222, 'MEGA Java', '2017-02-01 00:00:00', 1, 100.11, 'Capt. Jack Sparrow', 'Sat, Sun', 'Come, the courses are over.');