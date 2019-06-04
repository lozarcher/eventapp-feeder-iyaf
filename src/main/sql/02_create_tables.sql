drop table if exists foodfest.event;
create table foodfest.event (
      ID NUMERIC(20,0) not null,
      NAME varchar(255) null,
      DESCRIPTION varchar(4000) CHARSET utf8,
      PROFILE_URL varchar(255) null,
      COVER_URL varchar(255) null,
      COVER_OFFSET_X INT null,
      COVER_OFFSET_Y INT null,
      LOCATION varchar(255) null,
      START_TIME TIMESTAMP null,
      END_TIME TIMESTAMP null,
      VENUE_ID NUMERIC(20,0) null,
      TICKET_URI varchar(255) null,
      EVENT_ID NUMERIC(20,0) not null,
     	PRIMARY KEY (`ID`)
);
drop table if exists foodfest.tweet;
create table foodfest.tweet (
      ID NUMERIC(20,0) not null,
      NAME varchar(255) null,
      SCREEN_NAME varchar(255) null,
      TEXT VARCHAR(4000) CHARSET utf8,
      PROFILE_PIC varchar(255) null,
      CREATED_DATE TIMESTAMP not null
);
drop table if exists foodfest.last_refresh;
create table foodfest.last_refresh (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TABLE_NAME varchar(255) not null,
  LAST_REFRESH TIMESTAMP not null
);
drop table if exists foodfest.venue;
create table foodfest.venue (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY,
	STREET varchar(255),
	CITY varchar(255),
	POSTCODE varchar(10),
	LATITUDE DOUBLE,
	LONGITUDE DOUBLE
);
drop table if exists foodfest.trader_feed;
create table foodfest.trader_feed (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY,
	KPOUND BIT(1) DEFAULT 0
);
drop table if exists foodfest.trader;
create table foodfest.trader (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY,
	NAME varchar(255) null,
  ABOUT varchar(4000) null,
  PROFILE_IMG varchar(500) null,
  COVER_IMG varchar(500) null,
  COVER_OFFSET_X INT null,
  COVER_OFFSET_Y INT null,
	LINK varchar(255),
	WEBSITE varchar(255),
	PHONE varchar(255),
  KPOUND BIT(1) DEFAULT 0
);
drop table if exists foodfest.voucher;
create table foodfest.voucher (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	POSITION INT,
	TITLE varchar(255),
	URL varchar(255)
);
drop table if exists foodfest.message;
create table foodfest.message (
	    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      NAME varchar(255) null,
      TEXT VARCHAR(4000) CHARSET utf8,
      PROFILE_PIC varchar(255) null,
      CREATED_DATE TIMESTAMP not null
);
drop table if exists foodfest.post;
create table foodfest.post (
	ID NUMERIC(20,0) NOT NULL PRIMARY KEY,
  MESSAGE TEXT CHARSET utf8,
  PICTURE varchar(1000) null,
  LINK varchar(1000) null,
	NAME varchar(255) CHARSET utf8,
	CAPTION varchar(4000) CHARSET utf8,
	CREATED_DATE TIMESTAMP NOT NULL
);
drop table if exists foodfest.gallery;
create table foodfest.gallery (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PICTURE varchar(1000) null,
  THUMB varchar(1000) null,
	USER varchar(255) CHARSET utf8,
	CAPTION varchar(4000) CHARSET utf8,
	MODERATED BIT(1) DEFAULT 0,
	CREATED_DATE TIMESTAMP NOT NULL
);
drop table if exists foodfest.info;
create table foodfest.info (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  PICTURE varchar(1000) null,
  THUMB varchar(1000) null,
	TITLE varchar(4000) CHARSET utf8,
  CONTENT text CHARSET utf8
);
drop table if exists foodfest.category;
create table foodfest.category (
	ID INT(10) NOT NULL AUTO_INCREMENT,
  CATEGORY varchar(1000) not null,
	PRIMARY KEY (`ID`)
) ENGINE INNODB;
drop table if exists foodfest.event_category;
create table foodfest.event_category (
	EVENTID NUMERIC(20,0) NOT NULL,
	CATEGORYID INT(10) NOT NULL,
	PRIMARY KEY (`EVENTID`, `CATEGORYID`),
	FOREIGN KEY (`EVENTID`)
        REFERENCES `event`(`ID`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	FOREIGN KEY (`CATEGORYID`)
        REFERENCES `category`(`ID`)
		ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE INNODB;