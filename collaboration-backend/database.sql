CREATE SEQUENCE users_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
CREATE TABLE USER_DETAILS(	
	ID NUMBER(8,0), 
	BIRTH_DATE DATE, 
	E_MAIL VARCHAR2(255) NOT NULL, 
	ENABLED NUMBER(1,0), 
	FAMILY_NAME VARCHAR2(255) NOT NULL, 
	FIRST_NAME VARCHAR2(255) NOT NULL,  
	LOGIN VARCHAR2(255) NOT NULL, 
	PASSWORD VARCHAR2(255) NOT NULL, 
  	ROLE VARCHAR2(20) NOT NULL,
	PHONE VARCHAR2(255) NOT NULL, 
	ID_PICTURE VARCHAR2(255), 
	CONSTRAINT pk_user_details_id PRIMARY KEY (ID)
);