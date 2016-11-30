-- Accounting
INSERT INTO db1.Accounting (user_id, certificate_id, course_coast, debt) VALUES (1, 2, 2000, 1000);
INSERT INTO db1.Accounting (user_id, certificate_id, course_coast, debt) VALUES (1, 1, 4000, 500);

-- Certificate
INSERT INTO db1.Certificate (user_id, certification_date, course_name, language) VALUES (1, '2016-11-08', 'Java Professional', 'Java');
INSERT INTO db1.Certificate (user_id, certification_date, course_name, language) VALUES (12, '2016-09-13', 'Java OOP', 'Java');

-- Payments
INSERT INTO db1.Payments (deal_id, amount) VALUES (1, 6000);
INSERT INTO db1.Payments (deal_id, amount) VALUES (2, 6000);

-- Roles
INSERT INTO db1.Roles (name) VALUES ('Admin');
INSERT INTO db1.Roles (name) VALUES ('User');

-- Users
INSERT INTO db1.Users (user_id, email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id) VALUES (1, 'test@test.com', '123', 'test_first_name', 'test_last_name', null, null, null, '007', 1);
INSERT INTO db1.Users (email, password, first_name, last_name, passport_scan, photo, discount, phone, role_id) VALUES ('test@test.com', '123', 'test_first_name', 'test_last_name', null, null, null, '007', null);
