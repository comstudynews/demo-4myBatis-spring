DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
ID VARCHAR(20) PRIMARY KEY,
PASSWORD VARCHAR(20),
NAME VARCHAR(50),
ROLE VARCHAR(20)
);

INSERT INTO USERS VALUES('test','test123','관리자','Admin');
INSERT INTO USERS VALUES('user1','user1','홍길동','User');