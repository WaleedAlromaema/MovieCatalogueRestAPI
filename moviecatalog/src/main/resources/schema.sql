CREATE TABLE Actor(
  ID INTEGER AUTO_INCREMENT PRIMARY KEY,
  FIRST_NAME VARCHAR(30) NOT NULL,
  LAST_NAME VARCHAR(30) NOT NULL,
  NUMBER_MOVIES INTEGER  NULL,
  START_YEAR DATE  NULL,
  BIRTH_DATE DATE  NULL,
  NATIONALITY VARCHAR(30)  NULL,
  GANDER VARCHAR(16)  NULL,
  UNIQUE(FIRST_NAME,LAST_NAME)
);

CREATE TABLE Director(
  ID INTEGER AUTO_INCREMENT PRIMARY KEY,
  FIRST_NAME VARCHAR(30) NOT NULL,
  LAST_NAME VARCHAR(30) NOT NULL,
  NUMBER_DIRECTED_MOVIES INT NULL,
  START_YEAR DATE NULL,
  BIRTH_DATE DATE NULL,
  NATIONALITY VARCHAR(30) NULL,
  GANDER VARCHAR(16) NULL,
UNIQUE(FIRST_NAME,LAST_NAME)
);

CREATE TABLE Movie(
  ID INTEGER AUTO_INCREMENT PRIMARY KEY,
  TITEL VARCHAR(60) NOT NULL,
  MOVIE_YEAR DATE NOT NULL,
  GENRE VARCHAR(30) NOT NULL,
  RATING INTEGER NOT NULL,
    UNIQUE(TITEL)
  
);

CREATE TABLE Actor_Participate_Movie(
  ACTOR_ID INTEGER NOT NULL,
  MOVIE_ID INTEGER NOT NULL,
  PRIMARY KEY (ACTOR_ID ,MOVIE_ID),
  FOREIGN KEY(ACTOR_ID) REFERENCES ACTOR(ID) ,
  FOREIGN KEY(MOVIE_ID) REFERENCES MOVIE(ID) 
);
CREATE TABLE Director_Participate_Movie(
  DIRECTOR_ID BIGINT NOT NULL,
  MOVIE_ID BIGINT NOT NULL,
  PRIMARY KEY (DIRECTOR_ID ,MOVIE_ID),
  FOREIGN KEY(DIRECTOR_ID) REFERENCES DIRECTOR(ID) ,
  FOREIGN KEY(MOVIE_ID) REFERENCES MOVIE(ID)
);
INSERT INTO Actor VALUES (0, 'ActorFirstName_0', 'ActorLastName_0', 12,'2014-09-22','1986-04-12','Yemeni','Male');
INSERT INTO Actor VALUES (1, 'ActorFirstName_1', 'ActorLastName_1', 12,'2014-09-22','1986-04-12','Yemeni','Male');
INSERT INTO Actor VALUES (2, 'ActorFirstName_2', 'ActorLastName_2', 12,'2014-09-22','1986-04-12','Yemeni','Male');
INSERT INTO Actor VALUES (3, 'ActorFirstName_3', 'ActorLastName_3', 12,'2014-09-22','1986-04-12','Yemeni','Male');

INSERT INTO Director VALUES (10, 'DirectorFirstName_0', 'DirectorLastName_0', 120,'2014-09-22','1986-04-12','Yemeni','Male');
INSERT INTO Director VALUES (20, 'DirectorFirstName_1', 'DirectorLastName_1', 120,'2014-09-22','1986-04-12','Yemeni','Male');
INSERT INTO Director VALUES (30, 'DirectorFirstName_2', 'DirectorLastName_2', 120,'2014-09-22','1986-04-12','Yemeni','Male');
INSERT INTO Director VALUES (40, 'DirectorFirstName_3', 'DirectorLastName_3', 120,'2014-09-22','1986-04-12','Yemeni','Male');

INSERT INTO Movie VALUES (100, 'MOVIE_TITEL_0', '1996-09-11', 'History',9);
INSERT INTO Movie VALUES (200, 'MOVIE_TITEL_1', '1996-09-11', 'Advanture',8);
INSERT INTO Movie VALUES (300, 'MOVIE_TITEL_2', '1996-09-11', 'Action',7);
INSERT INTO Movie VALUES (400, 'MOVIE_TITEL_3', '1996-09-11', 'Romantic',3);


INSERT INTO Actor_Participate_Movie VALUES (0,100);
INSERT INTO Actor_Participate_Movie VALUES (1,200);
INSERT INTO Actor_Participate_Movie VALUES (2,300);
INSERT INTO Actor_Participate_Movie VALUES (3,400);




INSERT INTO Director_Participate_Movie VALUES (10,100);
INSERT INTO Director_Participate_Movie VALUES (20,200);
INSERT INTO Director_Participate_Movie VALUES (30,300);
INSERT INTO Director_Participate_Movie VALUES (40,400);







