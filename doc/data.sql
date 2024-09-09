USE demo;


CREATE TABLE Rating (
  id INT NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(255) NOT NULL,
  sandprating VARCHAR(255) NOT NULL,
  fitch_rating VARCHAR(255) NOT NULL,
  order_number INT NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE BidList (
  bidlist_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidquantity DOUBLE,
  askquantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bidlist_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  sourcelist_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (bidlist_id)
);


CREATE TABLE Trade (
  trade_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE ,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  sourcelist_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (trade_id)
);



CREATE TABLE CurvePoint (
  id INT NOT NULL AUTO_INCREMENT,
  curve_id INT,
  as_of_date TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creation_date TIMESTAMP ,

  PRIMARY KEY (id)
);


CREATE TABLE RuleName (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlstr VARCHAR(125),
  sqlpart VARCHAR(125),

  PRIMARY KEY (id)
);


CREATE TABLE Users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(255),
  fullname VARCHAR(125),
  role VARCHAR(125),

  PRIMARY KEY (id)
);

insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN");
insert into Users(fullname, username, password, role) values("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");