drop table if exists file;

create table file(
  id serial NOT NULL,
  name varchar(100) NOT NULL ,
  md5 varchar(32) ,
  path varchar(100) NOT NULL ,
  upload_time timestamp NOT NULL ,
  CONSTRAINT file_pk PRIMARY KEY (id)
);
