use JukeboxDB;
/*List of all Users*/
create table userlist
(
username varchar(30),
userid varchar(20) primary key,
userpassword varchar(30)
);
insert into userlist values("Vishal","Vishal1","Porsche911");
select * from userlist;
delete from userlist;/*2nd to delete content if necessary*/

/*List of playlists of users*/
create table usersplaylists
(
playlistid varchar(20) primary key,
playlistname varchar(20),
userid varchar(20),
foreign key(userid) references userlist(userid)
);
select * from usersplaylists;
delete from usersplaylists;/*1st to delete content if necessary*/

create table songslist /*Total list 15 - 20*/
(
songid varchar(5) primary key,
songname varchar(30),
genre varchar(20),
artist varchar (20),
duration varchar(6),
songpath varchar (1000)
);

select * from songslist;

insert into songslist values('s001','abstract fashion pop','pop','unknown','1:32','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Classical Piano\alle-origini-mirko-boroni-10552.wav');
insert into songslist values('s002','Galway Girl','pop','Ed Sheeran','2:50','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\Galway Girl.wav');
insert into songslist values('s003','Give Me Love','pop','Ed Sheeran','8:46','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\Give Me Love.wav');
insert into songslist values('s004','Lego House','pop','Ed Sheeran','3:05','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\Lego House.wav');
insert into songslist values('s005','One','pop','Ed Sheeran','4:12','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\One.wav');
insert into songslist values('s006','Perfect','pop','Ed Sheeran','4:23','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\Perfect.wav');
insert into songslist values('s007','Photograph','pop','Ed Sheeran','3:22','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\Photograph.wav');
insert into songslist values('s008','Shape Of You','pop','Ed Sheeran','3:53','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\Shape Of You.wav');
insert into songslist values('s009','South of the Border','pop','Ed Sheeran','4:45','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\South of the border.wav');
insert into songslist values('s010','The A Team','pop','Ed Sheeran','4:21','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\The A Team.wav');
insert into songslist values('s011','Thinking Out Loud','pop','Ed Sheeran','4:41','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Ed Sheeran\Thinking Out Loud.wav');
insert into songslist values('s012','Another One Bites The Dust','rock','Queen','3:42','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\Another One Bites the Dust.wav');
insert into songslist values('s013','Bohemian Rhapsody','rock','Queen','5:59','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\Bohemian Rhapsody.wav');
insert into songslist values('s014','Don-t Stop Me Now','rock','Queen','3:36','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\Don-t Stop Me Now.wav');
insert into songslist values('s015','I Want To Break Free','rock','Queen','4:31','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\I Want To Break Free.wav');
insert into songslist values('s016','Killer Queen','rock','Queen','3:11','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\Killer Queen.wav');
insert into songslist values('s017','Radio Ga Ga','rock','Queen','5:53','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\Radio Ga Ga.wav');
insert into songslist values('s018','Somebody To Love','rock','Queen','5:09','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\Somebody To Love.wav');
insert into songslist values('s019','Under Pressure','rock','Queen','4:13','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\Under Pressure.wav');
insert into songslist values('s020','We Are The Champions','rock','Queen','3:10','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\We Are The Champions.wav');
insert into songslist values('s021','We Will Rock You','rock','Queen','2:14','C:\Users\X510U\Desktop\NIIT\Wave 43\Assignments\Course 7 - Capstone Project - Jukebox\Songs\WAV\Queen\We Will Rock You.wav');


delete from songslist;
/*sample table playlist1 for user1 created*/
Create table user1playlist1(songid varchar(5),foreign key(songid) references songslist(songid), songname varchar(30), genre varchar(20), artist varchar (20), duration varchar(6), songpath varchar (1000));
select * from songslist;
select * from user1playlist1;
