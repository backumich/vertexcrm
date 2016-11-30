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
  language           VARCHAR(255) NOT NULL,
  CONSTRAINT FK_Certificate_user_id FOREIGN KEY (user_id) REFERENCES Users (user_id)
);
