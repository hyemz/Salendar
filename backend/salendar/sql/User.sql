use salendar;

drop table if exists user;
CREATE TABLE User
(
    `usrNo`        INT            NOT NULL    AUTO_INCREMENT COMMENT '사용자 일련번호',
    `usrPwd`        VARCHAR(20)    NULL        COMMENT '비밀번호',
    `usrNick`       VARCHAR(15)    NULL        COMMENT '닉네임',
    `usrEmail`      VARCHAR(30)    NULL        COMMENT '이메일',
    `usrFollowing`  INT            NULL        COMMENT '팔로우 매장',
    `usrImgUrl`		VARCHAR(30)	  NULL		   COMMENT '사용자 프로필',
    PRIMARY KEY (usrNo)
);

select * from comment;
select * from user;
select * from board;