CREATE TABLE IF not exists admins(username varchar(50) not null,password varchar(50) not null);
CREATE TABLE IF not exists cinema(cinema_name varchar(50) not null,username varchar(50) not null,password varchar(50) not null,verification varchar (50) not null);
CREATE TABLE IF not exists ticket(id bigserial,film_name varchar(50) not null,cinema_name varchar(50) not null,dateOfPerformance date not null,quantity int not null,price int not null );
CREATE TABLE IF not exists purchaser(username varchar(50) not null,password varchar(50) not null);
CREATE TABLE IF not exists reserve(user_name varchar(50) not null,ticket_id varchar(50) not null,quantity int );
INSERT INTO admins(username,password) VALUES ('admin','admin');