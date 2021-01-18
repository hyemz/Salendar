package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
// 사용자
public class User {

    // 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("usr_seq")
    private Integer usr_seq;

    // 비밀번호
    @JsonProperty("usr_pwd")
    private String usr_pwd;

    // 닉네임
    @JsonProperty("usr_nick")
    private String usr_nick;

    // 이메일
    @JsonProperty("usr_email")
    private String usr_email;

    // 팔로우 매장
    @JsonProperty("usr_following")
    private Integer usr_follwing;

    public Integer getUsrSeq() {
        return usr_seq;
    }

    public void setUsrSeq(Integer usr_seq) {
        this.usr_seq = usr_seq;
    }

    public String getUsrPwd() {
        return usr_pwd;
    }

    public void setUsrPwd(String usr_pwd) {
        this.usr_pwd = usr_pwd;
    }

    public String getUsrNick() {
        return usr_nick;
    }

    public void setUsrNick(String usr_nick) {
        this.usr_nick = usr_nick;
    }

    public String getUsrEmail() {
        return usr_email;
    }

    public void setUsrEmail(String usr_email) {
        this.usr_email = usr_email;
    }

    public Integer getUsrFollowing() {
        return usr_follwing;
    }

    public void setUsrFollowing(Integer usr_follwing) {
        this.usr_follwing = usr_follwing;
    }

    // User 모델 복사
    public void CopyData(User param) {
        this.usr_seq = param.getUsrSeq();
        this.usr_pwd = param.getUsrPwd();
        this.usr_nick = param.getUsrNick();
        this.usr_email = param.getUsrEmail();
        this.usr_follwing = param.getUsrFollowing();
    }

    public User() {}

    public User(int usr_seq, int usr_following,String usr_email, String usr_pwd, String usr_nick){
        this.usr_seq = usr_seq;
        this.usr_pwd = usr_pwd;
        this.usr_nick = usr_nick;
        this.usr_email = usr_email;
        this.usr_follwing = usr_follwing;
    }
}