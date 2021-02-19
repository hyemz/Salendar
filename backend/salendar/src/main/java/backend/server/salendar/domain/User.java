package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "user")
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

    // 메일 알람 수신 동의
    @JsonProperty("usrAlarm")
    @Builder.Default
    private Boolean usrAlarm = false;

    // 팔로우 매장
    @ManyToMany
    @JsonBackReference(value = "user-following")
    @JoinTable(name = "user_following",
            joinColumns = @JoinColumn(name = "usr_no"),
            inverseJoinColumns = @JoinColumn(name = "store_no"))
    private List<Store> usrFollowing = new ArrayList<Store>();

    // 사용자가 좋아요 한 게시물
    @ManyToMany
    @JsonBackReference(value = "board-like")
    @JoinTable(name = "board_like",
            joinColumns = @JoinColumn(name = "usr_no"),
            inverseJoinColumns = @JoinColumn(name = "board_no"))
    private List<Board> boardLike = new ArrayList<Board>();

    // 프로필 이미지
    @JsonProperty("usrImg")
    @Lob
    private byte[] usrImg;


    @Cascade(org.hibernate.annotations.CascadeType.ALL)
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