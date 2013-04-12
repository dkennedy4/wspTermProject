
/*
  these tables belong to the f2l app
*/
create table members
(
  userId integer Not Null Primary Key GENERATED ALWAYS AS IDENTITY,
  password varchar(20) Not Null,
  lastName varchar(45) Not Null,
  firstName varchar(45) Not Null,
  email varchar(50) Not Null,
  phoneNumber varchar(8) Not Null,
  gender varchar(7) Not Null, 
  contLang varchar(60) Not Null,
  homeTown varchar(30) Not Null,
  associateId integer Not Null,
  Foreign Key (associateId) references associationLookUp(associateId));


create table associationLookUp
(
  associateId integer Not Null Primary Key GENERATED ALWAYS AS IDENTITY,
  association varchar(25) Not Null
);

create table content 
   (
    fileid INT Not NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    filename VARCHAR(100),
    contents BLOB
    );

drop table contents
drop table members

drop table associationLookUp
