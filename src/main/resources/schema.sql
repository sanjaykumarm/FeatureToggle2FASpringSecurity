DROP TABLE IF EXISTS USERDETAILS;

create table USERDETAILS (
ID INTEGER  PRIMARY KEY auto_increment,
USERNAME VARCHAR(20) NOT NULL,
PASSWORD VARCHAR(100) NOT NULL,
ROLE VARCHAR(20) NOT NULL,
MFA_ENABLED BOOLEAN,
FORCE_ENABLED BOOLEAN,
SECRET_KEY VARCHAR(250));
