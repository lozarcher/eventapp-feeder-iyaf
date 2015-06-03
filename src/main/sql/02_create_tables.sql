drop table if exists iyaf.event;
create table iyaf.event (
      ID NUMERIC(20,0) not null,
      NAME varchar(255) null,
      DESCRIPTION varchar(4000) null,
      PROFILE_URL varchar(255) null,
      COVER_URL varchar(255) null,
      COVER_OFFSET_X INT null,
      COVER_OFFSET_Y INT null,
      LOCATION varchar(255) null,
      START_TIME TIMESTAMP null,
      END_TIME TIMESTAMP null,
      VENUE_ID NUMERIC(20,0) null
);
drop table if exists iyaf.tweet;
create table iyaf.tweet (
      ID NUMERIC(20,0) not null,
      NAME varchar(255) null,
      SCREEN_NAME varchar(255) null,
      TEXT VARCHAR(4000) CHARSET utf8,
      PROFILE_PIC varchar(255) null,
      CREATED_DATE TIMESTAMP not null
);
drop table if exists iyaf.last_refresh;
create table iyaf.last_refresh (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TABLE_NAME varchar(255) not null,
  LAST_REFRESH TIMESTAMP not null
);
drop table if exists iyaf.venue;
create table iyaf.venue (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY,
	STREET varchar(255),
	CITY varchar(255),
	POSTCODE varchar(10),
	LATITUDE DOUBLE,
	LONGITUDE DOUBLE
);
drop table if exists iyaf.performer_feed;
create table iyaf.performer_feed (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY
);
drop table if exists iyaf.performer;
create table iyaf.performer (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY,
	NAME varchar(255) null,
  ABOUT varchar(4000) null,
  PROFILE_IMG varchar(255) null,
  COVER_IMG varchar(255) null,
  COVER_OFFSET_X INT null,
  COVER_OFFSET_Y INT null,
	LINK varchar(255),
	WEBSITE varchar(255),
	PHONE varchar(255),
  KPOUND BIT(1) DEFAULT 0
);
drop table if exists iyaf.post;
create table iyaf.post (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY,
  MESSAGE varchar(4000) CHARSET utf8,
  PICTURE varchar(1000) null,
  LINK varchar(255) null,
	NAME varchar(255) CHARSET utf8,
	CAPTION varchar(4000) null,
	CREATED_DATE TIMESTAMP NOT NULL
);



