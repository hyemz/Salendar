package backend.server.salendar.controller;

import backend.server.salendar.security.JwtTokenProvider;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/user")
public class UserController {
    // 기본형
    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    // 회원 가입
    @ApiOperation(value = "회원 가입", notes = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<String> join(@ApiParam(value = "usrEmail, usrNick, usrPwd", required = true) @RequestBody Map<String, String> user) {
        try {
            userService.validateDuplicateUserNick(user.get("usrNick"));
            userService.validateDuplicateUserEmail(user.get("usrEmail"));
            userRepository.save(User.builder()
                    .usrEmail(user.get("usrEmail"))
                    .usrPwd(passwordEncoder.encode(user.get("usrPwd")))
                    .usrNick(user.get("usrNick"))
                    .roles(Collections.singletonList(user.get("usrEmail").endsWith("@admin.com") ? "ROLE_ADMIN" : "ROLE_USER"))
                    .build());
            return new ResponseEntity<String>(user.get("usrNick"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    // 로그인
    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login
    (@ApiParam(value = "usrEmail, usrPwd", required = true) @RequestBody Map<String, String> user) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = null;
        try {
            User member = (User) userRepository.findByUsrEmail(user.get("usrEmail"))
                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다."));
            if (!passwordEncoder.matches(user.get("usrPwd"), member.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            response.put("token", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
            status = HttpStatus.OK;
        } catch (Exception e) {
            response.put("message", e.toString());
            status = HttpStatus.NOT_ACCEPTABLE;
        }
        return new ResponseEntity<Map<String, Object>>(response, status);
    }

    // 모든 회원 조회
    @ApiOperation(value = "회원 가입", notes = "회원가입")
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
    @ApiOperation(value = "회원 정보 변경", notes = "token 필요")
    @PutMapping(value = "/token/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateMember(@RequestBody Map<String, String> user, HttpServletRequest request) {
        Optional<User> curUser = Optional.ofNullable(userService.findByToken(JwtTokenProvider.resolveToken(request)));
        try {
            userService.validateDuplicateUserNick(user.get("usrNick"));
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

    // 프로필 이미지 설정 -> 이건 DB에 들어가는 용
//    @ApiOperation(value = "프로필 이미지 설정", notes = "Img file, token")
//    @PutMapping(value = "/token/profileImg")
//    public ResponseEntity<String> setUserProfileImg(@ApiParam(value = "image file") @RequestParam("img") MultipartFile file,
//                                                    HttpServletRequest request) {
//        try {
//            userService.saveUserImage(JwtTokenProvider.resolveToken(request), file);
//            return new ResponseEntity<>("OK", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
//        }
//    }

    // 이게 우리가 정한 방법
    @ApiOperation(value = "프로필 이미지 설정", notes = "Img file Url, token")
    @PutMapping(value = "/token/profileImg")
    public ResponseEntity<String> setUserProfileImgUrl
    (@ApiParam(value = "image file Url") @RequestParam("imgUrl") String Url,
     HttpServletRequest request) {
        try {
            userService.saveUserImageUrl(JwtTokenProvider.resolveToken(request), Url);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    //    팔로우
    @ApiOperation(value = "매장 팔로우", notes = "token, storeName")
    @PostMapping(value = "/token/follow/{storeName}")
    public ResponseEntity<String> Follow(@PathVariable("storeName") String storeName, HttpServletRequest request) throws
            URISyntaxException {
        try {
            userService.Follow(JwtTokenProvider.resolveToken(request), storeName);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //    언팔로우
    @ApiOperation(value = "매장 언팔로우", notes = "token, storeName")
    @PostMapping(value = "/token/unfollow/{storeName}")
    public ResponseEntity<String> unFollow(@PathVariable("storeName") String storeName, HttpServletRequest request) throws
            URISyntaxException {
        try {
            userService.unFollow(JwtTokenProvider.resolveToken(request), storeName);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //    팔로우 조회
    @ApiOperation(value = "팔로우 중 매장 조회")
    @GetMapping(value = "/token/followings")
    public ResponseEntity<Map<String, Boolean>> userFollowings(HttpServletRequest request) {
        return new ResponseEntity<>(userService.usrFollowings(JwtTokenProvider.resolveToken(request)), HttpStatus.OK);
    }
}
