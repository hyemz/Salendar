package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 사용자
public class User implements UserDetails {

    // 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usrNo;

    // 비밀번호
    @JsonProperty("usrPwd")
    private String usrPwd;

    // 닉네임
    @JsonProperty("usrNick")
    private String usrNick;

    // 이메일
    @JsonProperty("usrEmail")
    private String usrEmail;
//
//    // 팔로우 매장
//    @JsonProperty("usrFollowing")
//    private Integer usrFollowing;


    // User 모델 복사
    public void CopyData(User param)
    {
        this.usrNo = param.getUsrNo();
        this.usrPwd = param.getUsrPwd();
        this.usrNick = param.getUsrNick();
        this.usrEmail = param.getUsrEmail();
//        this.usrFollowing = param.getUsrFollowing();
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usrPwd;
    }

    @Override
    public String getUsername() {
        return usrEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}