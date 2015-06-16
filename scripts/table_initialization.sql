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
drop sequence camper_counter;
drop sequence registration_counter;
--
-- Now, add each table.
--
create table Facility(
	id number(9,0) primary key,
	name varchar2(50),
	address varchar2(100),
	phone_num varchar2(50)
);
create table TypeFee(
	type varchar2(30) primary key,
	fee number(9,0)
);
create table CampSession(
	id number(9,0) primary key,
	name varchar2(50),
	description varchar2(256)
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
	address varchar2(100),
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
	camp_name varchar2(50),
	cabin_id number(9,0),
	foreign key (camp_name) references Camp(name),
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
	foreign key (camper_id) references Camper(id) on delete cascade,
	foreign key (cabin_id) references Cabin(id),
	foreign key (counsellor_id) references Counsellor(id),
	constraint session_camper unique (sid, camper_id)
);

--
-- Add sequences for auto-incrementing ids for various tables
--
create sequence camper_counter
	START WITH 100
	INCREMENT BY 1
	NOCACHE
	NOCYCLE;

create sequence registration_counter
	START WITH 100
	INCREMENT BY 1
	NOCACHE
	NOCYCLE;

--
-- Add sample data to tables
--
insert into Facility values(1, 'Athlete Locale', '8990 W 24th Ave, Vancouver, BC, V5E 3D5, Canada', '778-555-1357');
insert into Facility values(2, 'Seaside Locale', '4102 NW Marine Drive, Vancouver, BC, V7R 2F5, Canada', '778-555-2468');
insert into Facility values(3, 'Studio Locale', '8990 W 23th Ave, Vancouver, BC, V5E 3D6, Canada', '778-555-3579');
insert into Facility values(4, 'Music Locale', '2000 Johnston St, Vancouver, BC, V3Q 8P7, Canada', '778-555-4680');
insert into Facility values(5, 'Mountain Locale', '3360 Lynn Valley Rd, North Vancouver, BC, W4C 2L8, Canada', '778-555-1470');

insert into TypeFee values('Sports', 600);
insert into TypeFee values('Music', 500);
insert into TypeFee values('Arts', 700);
insert into TypeFee values('First Aid', 700);
insert into TypeFee values('Swimming', 600);

insert into CampSession values(1, 'Week 1', '6/7/2015 - 6/13/2015');
insert into CampSession values(2, 'Week 2', '6/14/2015 - 6/20/2015');
insert into CampSession values(3, 'Week 3', '6/21/2015 - 6/27/2015');
insert into CampSession values(4, 'Week 4', '6/28/2015 - 7/4/2015');
insert into CampSession values(5, 'Week 5', '7/5/2015 - 7/11/2015');

insert into Activity values('CPR Rescue Breathing', 'Dummies', 'An introduction into CPR and how to properly administer it.');
insert into Activity values('Bandaging and Splinting', 'Bandages, Splints', 'How to properly tie bandages, slings, and splints for injuries');
insert into Activity values('Pitching and Fielding', 'Baseballs, Catching Mitts', 'Pitching, catching and fielding practice for beginners');
insert into Activity values('Arranging Class', 'Music Manuscript Paper', 'How to go from music to your ears to music on paper.');
insert into Activity values('5km Beach Run', 'Stopwatch, Whistle', 'A brisk run on the beach to get the blood pumping.');
insert into Activity values('Beach Volleyball', 'Volleyball, Net, Chalk', 'Joining exercise with a game in the summer sun.');
insert into Activity values('Junk Sculpting', 'Recyclables, Glue Gun', 'Turning trash into treasures.');

insert into Camper values(camper_counter.nextval, 'Jim Curry', '604-555-4936', '3974 W 13th Ave, Vancouver, BC, V6R 3C1, Canada', 'rhpic@aol.com');
insert into Camper values(camper_counter.nextval, 'Campbell Alexander', '604-555-1585', '3086 W 12th Ave, Vancouver, BC, V6R 3C1, Canada', 'soupsister@aol.com');
insert into Camper values(camper_counter.nextval, 'Jean Lafayette', '604-555-7253', '3102 W 9th Ave, Vancouver, BC, V6R 3D4, Canada', 'manofear@aol.com');
insert into Camper values(camper_counter.nextval, 'Erik Stokes', '604-555-9245', '3755 W 16th Ave, Vancouver, BC, V6R 3C7, Canada', 'castle33@aol.com');
insert into Camper values(camper_counter.nextval, 'Anne-Marie Clark', '604-555-1176', '3621 W 21st Ave, Vancouver, BC, V6R 3B2, Canada', 'am_clarkie@aol.com');
insert into Camper values(camper_counter.nextval, 'Trisha Barianne', '604-555-0121', '3410 W 18th Ave, Vancouver, BC, V6R 3C5, Canada', 'trisha_bar@aol.com');
insert into Camper values(camper_counter.nextval, 'Danielle Carrefour', '604-555-8065', '3383 W 18th Ave, Vancouver, BC, V6R 3C5, Canada', 'dannidept@aol.com');

insert into Camp values('Rescue 911 Emergency Camp', 3, 'First Aid', 25);
insert into Camp values('Beachside Fitness', 2, 'Sports', 30);
insert into Camp values('Homerun Baseball Camp', 1, 'Sports', 40);
insert into Camp values('Orchestral 101', 4, 'Music', 30);
insert into Camp values('Sculptural Pursuit', 3, 'Arts', 20);

insert into Cabin values(100, 1, 1);
insert into Cabin values(200, 2, 1);
insert into Cabin values(300, 1, 2);
insert into Cabin values(400, 1, 3);
insert into Cabin values(500, 2, 3);
insert into Cabin values(600, 1, 4);
insert into Cabin values(700, 1, 5);

insert into Counsellor values(55551, 'Babe Ruth', 'Homerun Baseball Camp', 200);
insert into Counsellor values(55552, 'Roger Clemens', 'Homerun Baseball Camp', 100);
insert into Counsellor values(55553, 'William Shatner', 'Rescue 911 Emergency Camp', 500);
insert into Counsellor values(55554, 'Michelangelo Splint', 'Sculptural Pursuit', 400);
insert into Counsellor values(55555, 'Steve Nash', 'Beachside Fitness', 300);
insert into Counsellor values(55556, 'Amadeus Mozart', 'Orchestral 101', 600);
insert into Counsellor values(55557, 'Lazy Bob', NULL, NULL);

insert into CampOffers values('Rescue 911 Emergency Camp', 'CPR Rescue Breathing');
insert into CampOffers values('Rescue 911 Emergency Camp', 'Bandaging and Splinting');
insert into CampOffers values('Homerun Baseball Camp', 'Pitching and Fielding');
insert into CampOffers values('Orchestral 101', 'Arranging Class');
insert into CampOffers values('Beachside Fitness', '5km Beach Run');
insert into CampOffers values('Beachside Fitness', 'Beach Volleyball');
insert into CampOffers values('Sculptural Pursuit', 'Junk Sculpting');

insert into Registration values(registration_counter.nextval, 4, 'Rescue 911 Emergency Camp', 101, 400, 55553, 1);
insert into Registration values(registration_counter.nextval, 1, 'Beachside Fitness', 102, 300, 55555, 0);
insert into Registration values(registration_counter.nextval, 3, 'Homerun Baseball Camp', 103, 100, 55551, 1);
insert into Registration values(registration_counter.nextval, 3, 'Homerun Baseball Camp', 104, 200, 55552, 1);
insert into Registration values(registration_counter.nextval, 4, 'Rescue 911 Emergency Camp', 105, 400, 55553, 0);
insert into Registration values(registration_counter.nextval, 2, 'Sculptural Pursuit', 106, 400, 55554, 0);
insert into Registration values(registration_counter.nextval, 5, 'Orchestral 101', 107, 600, 55556, 1);
