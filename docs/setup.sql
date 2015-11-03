/*
* File name: setup.sql
* Function: to create the intial database schema for the CMPUT 391 project,
* Fall, 2013
* Author: Prof. Li-Yan Yuan
*
* Dummy data inserted for testing purposes by mnaylor
*/
DROP TABLE images CASCADE CONSTRAINTS;
DROP TABLE group_lists CASCADE CONSTRAINTS;
DROP TABLE groups CASCADE CONSTRAINTS;
DROP TABLE persons CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;
DROP TABLE imagecount CASCADE CONSTRAINTS;
DROP SEQUENCE group_id_sequence;
DROP SEQUENCE image_id_sequence;
DROP SEQUENCE count_id_sequence;

CREATE TABLE users (
   user_name varchar(24),
   password varchar(24),
   date_registered date,
   primary key(user_name)
);

INSERT INTO users VALUES ('admin', 'admin', sysdate);

CREATE TABLE persons (
   user_name varchar(24),
   first_name varchar(24),
   last_name varchar(24),
   address varchar(128),
   email varchar(128),
   phone char(10),
   PRIMARY KEY(user_name),
   UNIQUE (email),
   FOREIGN KEY (user_name) REFERENCES users
);

CREATE TABLE groups (
   group_id int,
   user_name varchar(24),
   group_name varchar(24),
   date_created date,
   PRIMARY KEY (group_id),
   UNIQUE (user_name, group_name),
   FOREIGN KEY(user_name) REFERENCES users
);

CREATE SEQUENCE group_id_sequence
  START WITH 3
  INCREMENT BY 1
  CACHE 100;

INSERT INTO groups values(1,null,'public', sysdate);
INSERT INTO groups values(2,null,'private',sysdate);

CREATE TABLE group_lists (
   group_id int,
   friend_id varchar(24),
   date_added date,
   notice varchar(1024),
   PRIMARY KEY(group_id, friend_id),
   FOREIGN KEY(group_id) REFERENCES groups,
   FOREIGN KEY(friend_id) REFERENCES users
);

CREATE TABLE images (
   photo_id int,
   owner_name varchar(24),
   permitted int,
   subject varchar(128),
   place varchar(128),
   timing date,
   description varchar(2048),
   thumbnail BLOB,
   photo BLOB,
   PRIMARY KEY(photo_id),
   FOREIGN KEY(owner_name) REFERENCES users,
   FOREIGN KEY(permitted) REFERENCES groups
);

CREATE TABLE imagecount (
       count_id int,
       photo_id int,
       user_name varchar(24),
       PRIMARY KEY (count_id),
       FOREIGN KEY (photo_id) REFERENCES images,
       FOREIGN KEY (user_name) REFERENCES users
);

CREATE SEQUENCE count_id_sequence
  START WITH 1 INCREMENT BY 1 nomaxvalue;

CREATE INDEX myimageindex ON images(description) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX mysubindex ON images(subject) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX mylocindex ON images(place) INDEXTYPE IS CTXSYS.CONTEXT;

CREATE SEQUENCE image_id_sequence
  START WITH 1
  INCREMENT BY 1 nomaxvalue;
