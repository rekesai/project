create database if not exists musicserver;
use musicserver;
drop table if exists user;
create table user (
    id int primary key auto_increment,
    username varchar(20) not null,
    password varchar(30) not null,
    age int not null,
    gender varchar(2) not null,
    email varchar(50) not null
);
insert into user(username, password, age, gender, email) values(
    "zxc", "zxc", 21, "男", "zxc123321122@163.com"
);
drop table if exists music;
create table music (
    id int primary key auto_increment,
    title varchar(50) not null,
    singer varchar(30) not null,
    url varchar(1000) not null,
    userId int not null,
    postTime varchar(13) not null
);
drop table if exists lovemusic;
create table lovemusic (
    id int primary key auto_increment,
    userId int not null,
    musicId int not null
);
drop table if exists mv;
create table mv (
    id int primary key auto_increment,
    title varchar(50) not null,
    singer varchar(30) not null,
    url varchar(1000) not null,
    userId int not null,
    postTime varchar(13) not null
);