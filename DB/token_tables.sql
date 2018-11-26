use szakdoga_db;

create table oauth_access_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BLOB,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256) primary key,
  token BLOB,
  authentication BLOB
);


select * from oauth_access_token;
select * from oauth_refresh_token;

drop table oauth_access_token;
drop table  oauth_refresh_token;

delete from oauth_access_token;
delete from oauth_refresh_token; 

/*https://stackoverflow.com/questions/948174/how-do-i-convert-from-blob-to-text-in-mysql*/
select CAST(token AS CHAR(10000) CHARACTER SET utf8) from oauth_access_token;
select CAST(authentication AS CHAR(10000) CHARACTER SET utf8) from oauth_access_token;
















