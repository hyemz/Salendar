package backend.server.salendar.controller;

import backend.server.salendar.domain.User;
import backend.server.salendar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("memberTest")
@CrossOrigin("http://localhost:8081")
public class UserController {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    // 모든 회원 조회
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // 회원번호로 한명의 회원 조회
    @GetMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getMember(@PathVariable("usrNo") Long usrNo) {
        Optional<User> user = userService.findByUsrSeq(usrNo);
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }
//
    // 회원번호로 회원 삭제
    @DeleteMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteMember(@PathVariable("usrNo") Long usrNo) {
        userService.deleteByUsrSeq(usrNo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

//
    // 회원번호로 회원 수정(usrNo로 회원을 찾아 Member 객체의 id, Nick을 수정함)
    @PutMapping(value = "/{usrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> updateMember(@PathVariable("usrNo") Long usrNo, User user) {
        userService.updateByUsrSeq(usrNo, user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @GetMapping("/test")
    public String test() {
        return "test";
    }


    // 회원 가입
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<User> join(HttpServletRequest req, User user) {
        return new ResponseEntity<User>(userService.join(user), HttpStatus.OK);
    }
}
