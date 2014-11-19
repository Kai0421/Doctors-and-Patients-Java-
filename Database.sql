--# Database for programming Doctor & Patient 
--# Student Number: R00095982
--# Student Name : Ren Kai Tam


--# Patient Table
create table Patient(pno varchar(5) not null, 
pfname varchar(10) not null,
plname varchar(10) not null,
address varchar (300) not null,
dob date not null,
dno varchar(5),
phone int(10),
primary key(pno, dno),
foreign key(dno) references Doctor(dno) on delete cascade on update cascade);

--# Doctor
create table Doctor(dno varchar(5) not null,
drName varchar(10) not null,
password varchar(10) not null,
primary key (dno));

--# Patient History Table
create table PatientHistory(phNo varchar(5) not null, 
meds varchar(50),
description varchar(255),
procedre varchar (255),
visitDate date not null,
pno varchar(5),
dno varchar(5),
primary key(pno, dno, phNo),
foreign key (pno) references Patient(pno) on delete cascade on update cascade,
foreign key (dno) references Doctor(dno) on delete cascade on update cascade);

--# Insert Patient History Table
Insert into PatientHistory values("1", "kai", "descpt", "procre", "2014-03-1", "1", "0");
Insert into PatientHistory values("2", "kai", "descpt - kai", "procre", "2014-03-2", "1", "0");

Insert into PatientHistory values("1", "Arthur", "descpt - Art", "procre", "2014-03-20", "2", "0");
Insert into PatientHistory values("2", "Arthur", "descpt - Art", "procre", "2014-03-19", "2", "0");

Insert into PatientHistory values("1", "Jean", "descpt - Jean", "procre", "2014-03-20" ,"1", "1");
Insert into PatientHistory values("2", "Jean", "descpt - Jean", "procre", "2014-03-17", "1", "1");

Insert into PatientHistory values("1", "Jagoda", "descpt - Jagoda", "procre", "2014-03-4", "2", "1");
Insert into PatientHistory values("2", "Jagoda", "descpt - Jagoda", "procre", "2014-03-11", "2", "1");

Insert into PatientHistory values("1", "Febin", "descpt - Febs", "procre", "2014-03-20", "3", "1");
Insert into PatientHistory values("2", "Febin", "descpt - Febs", "procre", "2014-03-19", "3", "1");

Insert into PatientHistory values("1", "Johnny", "descpt - Johnny", "procre", "2014-03-10", "4", "1");
Insert into PatientHistory values("2", "Johnny", "descpt - Johnny", "procre", "2014-03-30", "4", "1");

Insert into PatientHistory values("1", "Lisa", "descpt - Lis", "procre", "2014-03-5", "3", "0");
Insert into PatientHistory values("2", "Lisa", "descpt - Lis", "procre", "2014-03-29", "3", "0");

Insert into PatientHistory values("1", "Chris", "descpt - Chris", "procre", "2014-04-1", "4", "0");
Insert into PatientHistory values("2", "Chris", "descpt - Chris", "procre", "2014-03-27", "4", "0");

--# Insert Paient Table
Insert into Patient values("1", "Kai", "Tam", "21, Kenley Avenue, BishopsTown, Cork", "1993-4-21", "0", "0860624510");
Insert into Patient values("2", "Arthur", "Derviniks", "12, Cropse, MillerBrook, Nenagh", "1996-5-14", "0", "0877769008");
Insert into Patient values("1", "Jean", "OConnor", "Roscrea", "1994-8-8", "1", "0877769008");
Insert into Patient values("2", "Jagoda", "Beraja", "Coille Bheithe, Nenagh", "1994-6-12", "1", "0877769008");
Insert into Patient values("3", "Febin", "Oonikunju", "4, Cluain Muillean, Nenagh", "1996-2-14", "1", "0877769008");
Insert into Patient values("4", "Jonathon", "Shoer", "Ballycommon", "1995-2-15", "1", "0877769008");
Insert into Patient values("3", "Lisa", "Cariaga", "KL,", "1993-11-7", "0", "0877769008");
Insert into Patient Values("4", "Chris", "Cashel", "fairview 2, College Road, Cork", "1989-8-20", "0", "0877769008");

--# Insert Doctor Table 
Insert into Doctor values("0", "Kai", "kai");
Insert into Doctor values("1", "Arthur", "arthur");