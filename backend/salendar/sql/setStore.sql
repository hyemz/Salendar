use salendar;

CREATE TABLE store
(
    `store_no`	INT	NOT NULL AUTO_INCREMENT COMMENT '매장 일련번호',
    `store_logo`	VARCHAR(100) NULL,
    `store_name`	VARCHAR(30)	NULL,
    PRIMARY KEY (store_no)
);


INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Oliveyoung.png', 'Oliveyoung');
INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Lalavala.png', 'Lalavla');
INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Aritaum.png', 'Aritaum');
INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Innisfree.png', 'Innisfree');
INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Etude.png', 'Etude');
INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Missha.png', 'Missha');
INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Tonymoly.png', 'Tonymoly');
INSERT INTO store (store_logo, store_name) VALUES ('assets/logo/Thefaceshop.png', 'Thefaceshop');