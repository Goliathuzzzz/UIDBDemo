DROP DATABASE IF EXISTS localizations;
CREATE DATABASE localizations CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use localizations;

CREATE TABLE translations (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    key_name VARCHAR(50) NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    translation_text VARCHAR(255) NOT NULL,
    primary key (id, key_name)
);

DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED by 'maailmanilmaa';
GRANT SELECT,INSERT,UPDATE,ALTER,DELETE ON localizations.* TO 'appuser'@'localhost';
GRANT CREATE, DROP ON localizations.* TO 'appuser'@'localhost';