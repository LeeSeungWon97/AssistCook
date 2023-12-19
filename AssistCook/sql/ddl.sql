CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(255) NOT NULL,
    pw       VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    birth    VARCHAR(10),
    address  VARCHAR(255),
    email    VARCHAR(255),
    profile  VARCHAR(255),
    grade    VARCHAR(10)  NOT NULL
);