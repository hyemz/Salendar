drop table if exists `user`;

CREATE TABLE User
(
    `usr_seq`        INT            NOT NULL    AUTO_INCREMENT COMMENT '사용자 일련번호',
    `usr_email`      VARCHAR(30)    NULL        COMMENT '이메일',
    `usr_pwd`        VARCHAR(20)    NULL        COMMENT '비밀번호',
    `usr_nick`       VARCHAR(15)    NULL        COMMENT '닉네임',
    `usr_following`  INT            NULL        COMMENT '팔로우 매장',
    PRIMARY KEY (usr_seq)
);

ALTER TABLE User COMMENT '사용자';

-- ALTER TABLE User
--     ADD CONSTRAINT FK_User_usr_following_Store_store_seq FOREIGN KEY (usr_following)
--         REFERENCES Store (store_seq) ON DELETE RESTRICT ON UPDATE RESTRICT;