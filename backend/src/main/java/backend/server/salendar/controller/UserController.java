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
public class UserController {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    // 모든 회원 조회
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getAllmembers() {
        List<User> member = userService.findAll();
        return new ResponseEntity<List<User>>(member, HttpStatus.OK);
    }

    // 회원번호로 한명의 회원 조회
    @GetMapping(value = "/{mbrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getMember(@PathVariable("mbrNo") Long mbrNo) {
        Optional<User> member = userService.findByUsrSeq(mbrNo);
        return new ResponseEntity<User>(member.get(), HttpStatus.OK);
    }

    // 회원번호로 회원 삭제
    @DeleteMapping(value = "/{mbrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) {
        userService.deleteByUsrSeq(mbrNo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    // 회원번호로 회원 수정(mbrNo로 회원을 찾아 Member 객체의 id, name로 수정함)
    @PutMapping(value = "/{mbrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> updateMember(@PathVariable("mbrNo") Long mbrNo, User member) {
        userService.updateByUsrSeq(mbrNo, member);
        return new ResponseEntity<User>(member, HttpStatus.OK);
    }

    // 회원 입력
    @PostMapping
    public ResponseEntity<User> save(User member) {
        return new ResponseEntity<User>(userService.save(member), HttpStatus.OK);
    }


    // 회원 입력
    @RequestMapping(value = "/saveMember", method = RequestMethod.GET)
    public ResponseEntity<User> save(HttpServletRequest req, User member) {
        return new ResponseEntity<User>(userService.save(member), HttpStatus.OK);
    }
}
