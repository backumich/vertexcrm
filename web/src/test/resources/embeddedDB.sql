CREATE TABLE Roles
(
  role_id INT(11)     NOT NULL AUTO_INCREMENT,
  name    VARCHAR(50) NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE Users
(
  user_id       INT(11)                NOT NULL AUTO_INCREMENT PRIMARY KEY,
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
  FOREIGN KEY (role_id) REFERENCES Roles(role_id)
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

CREATE TABLE Payments
(
  payment_id   INT(11)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  deal_id      INT(11)   NOT NULL,
  amount       DOUBLE    NOT NULL,
  payment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
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
  id         INT(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  start      DATE,
  finished   TINYINT,
  price      DECIMAL,
  teacher_id INT(11)      NOT NULL,
  schedule   VARCHAR(255),
  notes      VARCHAR(999)
);

CREATE TABLE Course_users
(
  id         INT(11)      NOT NULL AUTO_INCREMENT,
  course_id  INT(11)      NOT NULL,
  user_id    INT(11)      NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO Roles (role_id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO Roles (role_id, name)
VALUES (2, 'ROLE_USER');

INSERT INTO Roles (role_id, name)
VALUES (3, 'ROLE_TEACHER');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('1', 'email1', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00', '1');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id, is_active)
VALUES ('2', 'email1@test.com', 'password', 'FirstName', 'LastName', 64, 64, '0', '38066 000 00 00', '3', '1');

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

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('401', 'user1@email.com', '$2a$12$lJElN7.2IR4YCueJNTPp9eUbRlXrYlP3M71dHc1czmKaqtJCvzhtS',
        'Name1', 'Surname1', NULL, NULL, '0', '+38050 111 1111', '2');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('402', 'user2@email.com', '$2a$12$lJElN7.2IR4YCueJNTPp9eUbRlXrYlP3M71dHc1czmKaqtJCvzhtS',
        'Name2', 'Surname2', NULL, NULL, '0', '+38050 222 2222', '2');

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('403', 'user3@email.com', '$2a$12$lJElN7.2IR4YCueJNTPp9eUbRlXrYlP3M71dHc1czmKaqtJCvzhtS',
        'Name3', 'Surname3', NULL, NULL, '0', '+38050 333 3333', '2');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('1', '1', '2016-12-1', 'Java Professional', 'Java', '1492779828793891');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('2', '2', '2016-12-1', 'Java Professional', 'Java', '1492779828793892');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('3', '2', '2016-12-1', 'Java Professional', 'Java', '1492779828793893');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('222', '22', '2016-12-1', 'Java Professional', 'Java', '1492779828793888');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('333', '33', '2016-12-1', 'Java Professional', 'Java', '1492779828793889');

INSERT INTO Certificate (certification_id, user_id, certification_date, course_name, language, certificate_uid)
VALUES ('500', NULL, '2016-12-1', 'Java Professional', 'Java', '1492779828793890');

INSERT INTO Users (user_id, email, password, first_name, last_name, discount, phone, role_id)
VALUES (10, 'emailTest', '2222222', 'first_name', 'last_name', 0, '666666666', 1);

INSERT INTO Accounting (deal_id, user_id, course_id, course_coast, debt) VALUES (1, 1, 1, 4000, 4000);

INSERT INTO Courses (id, name, start, finished, price, teacher_id, notes)
VALUES (1, 'JavaPro', '2017-02-01', 0, 4000, 1, 'Test');

INSERT INTO Courses (id, name, start, finished, price, teacher_id, notes)
VALUES (2, 'JavaStart', '2017-02-01', 0, 8000, 1, 'Before update');

INSERT INTO Courses (id, name, start, finished, price, teacher_id, notes)
VALUES (3, 'JavaPro', '2016-01-01', 1, 8000, 1, 'test');

INSERT INTO Courses (id, name, start, finished, price, teacher_id, schedule, notes) VALUES
  (111, 'Super JAVA', '2017-04-01', 0, 999999.99, 1, 'Sat, Sun', 'Welcome, we don''t expect you (='),
  (222, 'MEGA Java', '2017-02-01', 1, 100.11, 1, 'Sat, Sun', 'Come, the courses are over.');

INSERT INTO Course_users (course_id, user_id)
VALUES (1, 401);

INSERT INTO Course_users (course_id, user_id)
VALUES (1, 402);

INSERT INTO Course_users (course_id, user_id)
VALUES (2, 403);

INSERT INTO Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id)
VALUES ('7', 'teacher@test.com', 'password', 'Teacher', 'Goodman', NULL, NULL, '0', '38073 777 00 00', '3');

INSERT INTO Courses (id, name, start, finished, price, teacher_id, schedule, notes)
VALUES (7, 'Teacher JAVA', '2017-04-01', 0, 850.09, 7, 'Sat, Sun', 'Welcome (=');
