CREATE TABLE FILEDATA
(
  FILEID UUID PRIMARY KEY,
  FILENAME  VARCHAR2(50),
  FILETYPE VARCHAR2(50),
  FILESIZE BIGINT,
  UPLOADEDDATE DATE
);