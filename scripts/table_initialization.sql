--
--  First drop any existing tables. Any errors are ignored.
--
drop table Camp cascade constraints;
drop table Facility cascade constraints;
drop table TypeFee cascade constraints;
drop table CampSession cascade constraints;
drop table Registration cascade constraints;
drop table Counsellor cascade constraints;
drop table Camper cascade constraints;
drop table Cabin cascade constraints;
drop table Activity cascade constraints;
drop table CampOffers cascade constraints;
--
-- Now, add each table.
--
create table Facility(
	id number(9,0) primary key,
	name varchar2(50),
	address varchar2(50),
	phone_num varchar2(50)
);
create table TypeFee(
	type varchar2(30) primary key,
	fee number(9,0)
);
create table CampSession(
	id number(9,0) primary key,
	start_date date,
	end_date date
);
create table Activity(
	name varchar2(50) primary key,
	supplies varchar2(256),
	description varchar2(256)
);
create table Camper(
	id number(9,0) primary key,
	name varchar2(50) not null,
	phone_num varchar2(20),
	address varchar2(50),
	email varchar2(30) not null,
	constraint name_email unique (name, email)
);
create table Camp(
	name varchar2(50) primary key,
	fid number(9,0) not null,
	type varchar2(30) not null,
	max_capacity number(9,0),
	foreign key (fid) references Facility(id),
	foreign key (type) references TypeFee(type)
);
create table Cabin(
	id number(9,0) primary key,
	num number(9,0),
	fid number(9,0) not null,
	foreign key (fid) references Facility(id)
);
create table Counsellor(
	id number(9,0) primary key,
	name varchar2(50),
	cabin_id number(9,0) not null,
	foreign key (cabin_id) references Cabin(id)
);
create table CampOffers(
	camp_name varchar2(50) not null,
	activity_name varchar2(50) not null,
	primary key (camp_name, activity_name),
	foreign key (camp_name) references Camp(name),
	foreign key (activity_name) references Activity(name)
 );
create table Registration(
	conf_num number(9,0) primary key,
	sid number(9,0) not null,
	camp_name varchar2(50) not null,
	camper_id number(9,0) not null,
	cabin_id number(9,0) not null,
	counsellor_id number (9,0) not null,
	is_paid char check (is_paid in (0,1)),
	foreign key (sid) references CampSession(id),
	foreign key (camp_name) references Camp(name),
	foreign key (camper_id) references Camper(id),
	foreign key (cabin_id) references Cabin(id),
	foreign key (counsellor_id) references Counsellor(id),
	constraint session_camper unique (sid, camper_id)
);
