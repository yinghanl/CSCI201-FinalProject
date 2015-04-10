DROP DATABASE if exists towerdefense;

CREATE DATABASE towerdefense;

USE towerdefense;

CREATE TABLE User (
  userID int(11) primary key not null auto_increment,
  date_created DATETIME DEFAULT CURRENT_TIMESTAMP,
  date_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  username varchar(50) not null,
  password varchar(50) not null,
  gold_earned int(32) DEFAULT 0,
  creeps_killed int(32) DEFAULT 0,
  games_played int(16) DEFAULT 0,
  games_won int(16) DEFAULT 0,
  game_lost int(16) DEFAULT 0
);


