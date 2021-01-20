package backend.server.salendar.controller;

import backend.server.salendar.config.JwtTokenProvider;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@RequestMapping("memberTest")
@CrossOrigin("http://localhost:8081")
public class UserController {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public String test() {
        return "test";
    }


    // 회원 가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .usrEmail(user.get("usrEmail"))
                .usrPwd(passwordEncoder.encode(user.get("usrPwd")))
                .usrNick(user.get("usrNick"))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getUsrNo();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = (User) userRepository.findByUsrEmail(user.get("usrEmail"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다."));
        if (!passwordEncoder.matches(user.get("usrPwd"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    // 모든 회원 조회
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // 회원번호로 한명의 회원 조회
    @GetMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getMember(@PathVariable("usrNo") Long usrNo) {
        Optional<User> user = userService.findByUsrNo(usrNo);
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }
//
    // 회원번호로 회원 삭제
    @DeleteMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteMember(@PathVariable("usrNo") Long usrNo) {
        userService.deleteByUsrNo(usrNo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

//
    // 회원번호로 회원 수정(usrNo로 회원을 찾아 Member 객체의 id, Nick을 수정함)
    @PutMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> updateMember(@PathVariable("usrNo") Long usrNo, User user) {
        userService.updateByUsrNo(usrNo, user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
