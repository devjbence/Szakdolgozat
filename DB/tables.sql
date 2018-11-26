use szakdoga_db;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(60) DEFAULT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_activation` (
 `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `activation_string` varchar(20) DEFAULT NULL,
  CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expiration_date TIMESTAMP,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `USER_ID` int(11) DEFAULT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  KEY `USER_ID` (`USER_ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`id`),
  FOREIGN KEY (`USER_ID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `seller` ( 
  `ID` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) not null,
  `first_name` varchar(60) not NULL,
  `last_name` varchar(60) not NULL,
  `about_me` TEXT DEFAULT NULL,
  `profile_image` BLOB DEFAULT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `buyer` ( /*with user table - one to one*/ /*with job_seeker_job_categry many to many*/
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) not null,
  `first_name` varchar(60) not NULL,
  `last_name` varchar(60) not NULL,
  `about_me` TEXT DEFAULT NULL,
  `profile_image` BLOB DEFAULT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `product_category` ( 
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  parent_id int(11) ,
  `about` varchar(255) DEFAULT NULL,
   active tinyint(1),
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `seller_product_category` (
	`seller_id` int(11) NOT NULL,
	`category_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `buyer_job_category` (
	`buyer_id` int(11) NOT NULL,
	`category_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`buyer_id`) REFERENCES `buyer` (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) DEFAULT NULL,
  `name` varchar(200) default null,
  `description` text default null,
    CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_product_category` (
	`product_id` int(11) NOT NULL,
	`category_id` int(11) NOT NULL,
   CREATED    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


insert into role(name) values('ROLE_ADMIN');
insert into role(name) values('ROLE_USER');

select * from role;
select * from user;
select * from user_role;




drop table user;








