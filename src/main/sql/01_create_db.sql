create database foodfest;

CREATE USER 'foodfest'@'localhost' IDENTIFIED WITH mysql_native_password BY 'foodfest';
GRANT ALL PRIVILEGES ON foodfest.* TO 'foodfest'@'localhost' WITH GRANT OPTION;