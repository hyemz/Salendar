package backend.server.salendar.controller;

import backend.server.salendar.security.JwtTokenProvider;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081")
public class UserController {
    // 기본형
    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody Map<String, String> user) {
        try {
            userService.validateDuplicateUser(user.get("usrNick"), user.get("userEmail"));
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        userRepository.save(User.builder()
                .usrEmail(user.get("usrEmail"))
                .usrPwd(passwordEncoder.encode(user.get("usrPwd")))
                .usrNick(user.get("usrNick"))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getUsrNo();
        return new ResponseEntity<String>(user.get("usrNick"), HttpStatus.OK);
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> user) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            User member = (User) userRepository.findByUsrEmail(user.get("usrEmail"))
                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다."));
            if (!passwordEncoder.matches(user.get("usrPwd"), member.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            resultMap.put("token", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("message", e.toString());
            status = HttpStatus.NOT_ACCEPTABLE;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
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


    // 회원번호로 회원 삭제
    @DeleteMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteMember(@PathVariable("usrNo") Long usrNo) {
        userService.deleteByUsrNo(usrNo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


    // 회원번호로 회원 수정(usrNo로 회원을 찾아 Member 객체의 id, Nick을 수정함)
    @PutMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateMember(@PathVariable("usrNo") Long usrNo, @RequestBody Map<String, String> user) {
        Optional<User> curUser = userService.findByUsrNo(usrNo);
        try {
            userService.validateDuplicateUser(user.get("usrNick"), user.get("userEmail"));
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        if (curUser.isPresent()) {
            curUser.get().setUsrNick(user.get("usrNick"));
            curUser.get().setUsrPwd(user.get("usrPwd"));
            userRepository.save(curUser.get());
        }
        return new ResponseEntity<String>(user.get("usrNick"), HttpStatus.OK);
    }
}
