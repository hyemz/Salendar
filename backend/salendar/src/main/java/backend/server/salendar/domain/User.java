package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.persistence.*;

@Entity(name="user")
// 사용자
public class User {

    // 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("usr_seq")
    private Long usrSeq;

    // 비밀번호
    @JsonProperty("usr_pwd")
    private String usrPwd;

    // 닉네임
    @JsonProperty("usr_nick")
    private String usrNick;

//    // 이메일
//    @JsonProperty("usr_email")
//    private String usrEmail;
//
//    // 팔로우 매장
//    @JsonProperty("usr_following")
//    private Integer usrFollowing;
//
    public Long getUsrSeq() {
        return usrSeq;
    }

    public void setUsrSeq(Long usrSeq) {
        this.usrSeq = usrSeq;
    }

    public String getUsrPwd() {
        return usrPwd;
    }

    public void setUsrPwd(String usrPwd) {
        this.usrPwd = usrPwd;
    }

    public String getUsrNick() {
        return usrNick;
    }

    public void setUsrNick(String usrNick) {
        this.usrNick = usrNick;
    }

//    public String getUsrEmail() {
//        return usrEmail;
//    }
//
//    public void setUsrEmail(String usrEmail) {
//        this.usrEmail = usrEmail;
//    }
//
//    public Integer getUsrFollowing() {
//        return usrFollowing;
//    }
//
//    public void setUsrFollowing(Integer usrFollowing) {
//        this.usrFollowing = usrFollowing;
//    }
//
//    // User 모델 복사
//    public void CopyData(User param)
//    {
//        this.usrSeq = param.getUsrSeq();
//        this.usrPwd = param.getUsrPwd();
//        this.usrNick = param.getUsrNick();
//        this.usrEmail = param.getUsrEmail();
//        this.usrFollowing = param.getUsrFollowing();
//    }

    public User() {}

    @Builder
    public User(Long usrSeq, String usrPwd, String usrNick){
        this.usrSeq = usrSeq;
        this.usrPwd = usrPwd;
        this.usrNick = usrNick;
//        this.usrEmail = usrEmail;
//        this.usrFollowing = usrFollowing;
    }

}