DROP TABLE IF EXISTS member;
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

DROP TABLE IF EXISTS email_verification;
CREATE TABLE email_verification
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    email             VARCHAR(255),
    verification_code VARCHAR(255),
    expiration_time   TIMESTAMP,
    status            VARCHAR(20) DEFAULT 'ACTIVE'
);