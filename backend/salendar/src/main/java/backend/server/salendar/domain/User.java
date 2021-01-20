package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 사용자
public class User {

    // 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usrSeq;

    // 비밀번호
    @JsonProperty("usr_pwd")
    private String usrPwd;

    // 닉네임
    @JsonProperty("usr_nick")
    private String usrNick;

    // 이메일
    @JsonProperty("usr_email")
    private String usrEmail;
//
//    // 팔로우 매장
//    @JsonProperty("usr_following")
//    private Integer usrFollowing;


    // User 모델 복사
    public void CopyData(User param)
    {
        this.usrSeq = param.getUsrSeq();
        this.usrPwd = param.getUsrPwd();
        this.usrNick = param.getUsrNick();
        this.usrEmail = param.getUsrEmail();
//        this.usrFollowing = param.getUsrFollowing();
    }

}