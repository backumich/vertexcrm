CREATE TABLE Users
(
  user_id       INT(11)                NOT NULL AUTO_INCREMENT,
  email         VARCHAR(255)           NOT NULL,
  password      VARCHAR(255),
  first_name    VARCHAR(50)            NOT NULL,
  last_name     VARCHAR(50)            NOT NULL,
  passport_scan BLOB,
  photo         BLOB,
  discount      INT,
  phone         VARCHAR(25),
  role_id       INT(11),
  is_active     TINYINT(1) DEFAULT '0' NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE Certificate
(
  certification_id   INT AUTO_INCREMENT PRIMARY KEY,
  user_id            INT,
  certification_date DATE         NOT NULL,
  course_name        VARCHAR(500) NOT NULL,
  language           VARCHAR(255) NOT NULL,
  certificate_uid    BIGINT(20)   NOT NULL
);

CREATE TABLE Roles
(
  role_id INT(11)     NOT NULL AUTO_INCREMENT,
  name    VARCHAR(50) NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE Payments
(
  payment_id   INT(11)   NOT NULL AUTO_INCREMENT,
  deal_id      INT(11)   NOT NULL,
  amount       DOUBLE    NOT NULL,
  payment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (payment_id)
);

CREATE TABLE Accounting
(
  deal_id      INT(11) NOT NULL AUTO_INCREMENT,
  user_id      INT(11) NOT NULL,
  course_id    INT(11) NOT NULL,
  course_coast DOUBLE  NOT NULL,
  debt         DOUBLE  NOT NULL,
  PRIMARY KEY (deal_id)
);

CREATE TABLE Courses
(
  id           INT(11)      NOT NULL AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  start        DATETIME,
  finished     TINYINT,
  price        DECIMAL,
  teacher_name VARCHAR(255),
  schedule     VARCHAR(255),
  notes        VARCHAR(999),
  PRIMARY KEY (id)
);

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone)
VALUES ('1', 'email1', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('22', '22@test.com', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00', '2');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('33', '33@test.com', '$2a$12$lJElN7.2IR4YCueJNTPp9eUbRlXrYlP3M71dHc1czmKaqtJCvzhtS', 'FirstName', 'LastName',
        NULL, NULL, '0', '38066 000 00 00', '1');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('34', '34@test.com', '$2a$12$lJElN7.2IR4YCueJNTPp9eUbRlXrYlP3M71dHc1czmKaqtJCvzhtS', 'FirstName', 'LastName',
        NULL, NULL, '0', '38066 000 00 00', '1');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('44', '44@test.com', '$2a$12$lJElN7.2IR4YCueJNTPp9eUbRlXrYlP3M71dHc1czmKaqtJCvzhtS',
        'FirstName', 'LastName', NULL, NULL, '0', '38066 000 00 00', '2');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('222', '22', '2016-12-1', 'Java Professional', 'Java', '1492779828793888');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('333', '33', '2016-12-1', 'Java Professional', 'Java', '1492779828793889');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('500', NULL, '2016-12-1', 'Java Professional', 'Java', '1492779828793890');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('1', '1', '2016-12-1', 'Java Professional', 'Java', '1492779828793891');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('2', '2', '2016-12-1', 'Java Professional', 'Java', '1492779828793892');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('3', '2', '2016-12-1', 'Java Professional', 'Java', '1492779828793893');

INSERT INTO Users (user_id, email, password, first_name, last_name, discount, phone, role_id)
VALUES (10, 'emailTest', '2222222', 'first_name', 'last_name', 0, '666666666', 1);

INSERT INTO Accounting (deal_id, user_id, course_id, course_coast, debt) VALUES (1, 1, 1, 4000, 4000);

INSERT INTO Courses (id, name, start, finished, price, teacher_name, notes)
VALUES (1, 'JavaPro', '2017-02-01 10:10:10', 0, 4000, 'Test', 'Test');

INSERT INTO Courses VALUES
  (111, 'Super JAVA', '2017-04-01 00:00:00', 0, 999999.99, 'Yo Ho Ho', 'Sat, Sun', 'Welcome, we don''t expect you (='),
  (222, 'MEGA Java', '2017-02-01 00:00:00', 1, 100.11, 'Capt. Jack Sparrow', 'Sat, Sun', 'Come, the courses are over.');