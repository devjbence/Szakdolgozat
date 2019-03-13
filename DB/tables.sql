use szakdoga_db;

drop table product_category;
drop table product_image;
drop table comment;
drop table attribute;
drop table attribute_core;
drop table bid;
drop table product;
drop table category;
drop table buyer;
drop table seller;
drop table image;
drop table user_role;
drop table role;
drop table user_activation;
drop table user;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(60) DEFAULT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
);

CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(24) NOT NULL,
   `email` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
     activated tinyint(1),
    CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`),
   UNIQUE KEY `email` (`email`)
);

CREATE TABLE `user_activation` (
 `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `activation_string` varchar(20) DEFAULT NULL,
  CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expiration_date TIMESTAMP,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `user_role` (
  `USER_ID` int(11) DEFAULT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  KEY `USER_ID` (`USER_ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`id`),
  FOREIGN KEY (`USER_ID`) REFERENCES `user` (`id`)
);

create table `image`
(
	`id` int(11) not null auto_increment,
    `file` BLOB DEFAULT NULL,
	CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);

CREATE TABLE `seller` ( 
  `ID` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) not null,
  `first_name` varchar(60) not NULL,
  `last_name` varchar(60) not NULL,
  `about_me` TEXT DEFAULT NULL,
  `image_id` int(11),
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
);


CREATE TABLE `buyer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) not null,
  `first_name` varchar(60) not NULL,
  `last_name` varchar(60) not NULL,
  `about_me` TEXT DEFAULT NULL,
  `image_id` int(11),
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
);

CREATE TABLE `category` ( 
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  parent_id int(11) ,
  `about` varchar(255) DEFAULT NULL,
   active tinyint(1),
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) DEFAULT NULL,
  `buyer_id` int(11) DEFAULT NULL,
  `name` varchar(200) default null,
  `description` text default null,
  `type` int(4) not null,
  `price` int(11),
  `start` TIMESTAMP,
  `end` TIMESTAMP,
  CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`),
  FOREIGN KEY (`buyer_id`) REFERENCES `buyer` (`id`)
);

CREATE TABLE `bid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `buyer_id` int(11) DEFAULT NULL,
  `price` int(11),
    CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  FOREIGN KEY (`buyer_id`) REFERENCES `buyer` (`id`)
);

CREATE TABLE `product_category` (
	`product_id` int(11) NOT NULL,
	`category_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);

CREATE TABLE `product_image` (
	`product_id` int(11) NOT NULL,
	`image_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
);

create table `comment`
(
	`id` int(11) not null auto_increment,
	`product_id` int(11) not null,
    `buyer_id` int(11) not null,
    `message` varchar(200) DEFAULT NULL,
	CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
    KEY `product_id` (`product_id`),
	KEY `buyer_id` (`buyer_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
	FOREIGN KEY (`buyer_id`) REFERENCES `buyer` (`id`)
);

create table `attribute_core`
(
	`id` int(11) not null auto_increment,
    `name` varchar(200) DEFAULT NULL,
	`type` int(4) not null,
	CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);

create table `attribute`
(
	`id` int(11) not null auto_increment,
	`attribute_name_id` int(11) not null,
    `product_id` int(11) not null,
    `value` varchar(200) DEFAULT NULL,
	CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	KEY `attribute_name_id` (`attribute_name_id`),
    KEY `product_id` (`product_id`),
    FOREIGN KEY (`attribute_name_id`) REFERENCES `attribute_core` (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);

insert into role(name) values('ROLE_ADMIN');
insert into role(name) values('ROLE_USER');

insert into category(id,product_name,parent_id,about,active) values(1,'mobil',null,'Mobilkészülékek',0);
insert into category(id,product_name,parent_id,about,active) values(2,'IPHONE',1,'Legújabb iphone X',1);
insert into category(id,product_name,parent_id,about,active) values(3,'XIOMI',1,'Legújabb Xiomi redmi',1);

insert into attribute_core(id,name,type) values(1,'height',0);
insert into attribute_core(id,name,type) values(2,'weight',1);
insert into attribute_core(id,name,type) values(3,'othername',2);

/*
select * from role;
select * from user;
select * from user_role;
select * from image;
select *from seller;
select * from product;
select * from product_category;
select * from product_product_category;
select * from comment;
select * from bid;
select * from attribute;
select * from attribute_core;
*/



















