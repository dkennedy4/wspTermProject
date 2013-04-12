create table FILESTORAGE (
    fileid INT Not NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    userid Int Not Null,
    filename VARCHAR(100),
    contents BLOB,
    Foreign Key (userid) references contacts(userid));

drop table filestorage