create database contact_manager;
CREATE USER 'contact_manager'@'localhost' IDENTIFIED BY 'contact_manager';
GRANT ALL PRIVILEGES ON contact_manager.* TO 'contact_manager'@'localhost';

