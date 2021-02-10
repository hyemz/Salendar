package backend.server.salendar.controller;

import backend.server.salendar.service.CookieService;
import backend.server.salendar.service.JwtService;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.RedisService;
import backend.server.salendar.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.*;


@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    private final UserRepository userRepository;
    private final JwtService jwtService;
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
                    .roles(Collections.singletonList("ROLE_USER"))
                    .usrAlarm(Boolean.valueOf(user.get("usrAlarm")))
                    .build());
            return new ResponseEntity<String>(user.get("usrNick"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    // 로그인
    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@ApiParam(value = "usrEmail, usrPwd", required = true) @RequestBody Map<String, String> user,
                                        HttpServletRequest req,
                                        HttpServletResponse res) {
        try {
            User member = (User) userRepository.findByUsrEmail(user.get("usrEmail"))
                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다."));
            if (!passwordEncoder.matches(user.get("usrPwd"), member.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
            final String token = JwtService.createToken(member.getUsername(), member.getRoles());
            final String refreshJwt = JwtService.createRefreshToken(member.getUsername(), member.getRoles());
            Cookie accessToken = CookieService.createCookie(JwtService.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = CookieService.createCookie(JwtService.REFRESH_TOKEN_NAME, refreshJwt);
            redisService.setDataExpire(refreshJwt, member.getUsername(), JwtService.REFRESH_TOKEN_VALIDATION_SECOND);
            CookieGenerator cg = new CookieGenerator();
            cg.setCookieName(JwtService.ACCESS_TOKEN_NAME);
            cg.addCookie(res, token);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(user.get("usrEmail"), HttpStatus.OK);
    }


    // 로그아웃
    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    public void logout(HttpServletRequest req) {
        User user = userService.findByToken(CookieService.getCookie(req, JwtService.ACCESS_TOKEN_NAME).getValue());
        redisService.deleteData(CookieService.getCookie(req, JwtService.REFRESH_TOKEN_NAME).getValue());
    }

    // 모든 회원 조회
    @ApiOperation(value = "회원 가입", notes = "회원가입")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // 토큰으로 회원조회
    @ApiOperation(value = "token으로 회원 정보 조회")
    @GetMapping(value = "/token/mypage")
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        Optional<User> user = Optional.ofNullable(userService.findByToken(jwtService.resolveToken(request)));
        System.out.println(user.toString());
        return user
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(user.get(), HttpStatus.NOT_FOUND));
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


    // 회원 정보 수정
    @ApiOperation(value = "회원 정보 변경", notes = "token 필요")
    @PutMapping(value = "/token/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateMember(@RequestBody Map<String, String> user, HttpServletRequest request) {
        User curUser = userService.findByToken(JwtService.resolveToken(request));
        try {
            if (!user.get("usrNick").equals(curUser.getUsrNick())) {
                userService.validateDuplicateUserNick(user.get("usrNick"));
                curUser.setUsrNick(user.get("usrNick"));
            }
            curUser.setUsrPwd(passwordEncoder.encode(user.get("usrPwd")));
            curUser.setUsrAlarm(Boolean.valueOf(user.get("userAlarm")));
            userRepository.save(curUser);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(user.get("usrNick"), HttpStatus.OK);
    }

    //     프로필 이미지 설정 -> 이건 DB에 들어가는 용
    @ApiOperation(value = "프로필 이미지 설정", notes = "Img file, token")
    @PutMapping(value = "/token/profileImg")
    public ResponseEntity<String> setUserProfileImg(@ApiParam(value = "image file") @RequestParam("usrImg") MultipartFile file,
                                                    HttpServletRequest request) {
        try {
            userService.saveUserImage(JwtService.resolveToken(request), file);
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
            userService.Follow(JwtService.resolveToken(request), storeName);
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
            userService.unFollow(JwtService.resolveToken(request), storeName);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //    팔로우 조회
    @ApiOperation(value = "팔로우 중 매장 조회")
    @GetMapping(value = "/token/followings")
    public ResponseEntity<Map<String, Boolean>> userFollowings(HttpServletRequest request) {
        try {
            return new ResponseEntity<>(userService.usrFollowings(JwtService.resolveToken(request)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //  비밀번호 일치 여부
    @ApiOperation(value = "비밀번호 일치 여부")
    @PostMapping(value = "/token/pwdConfirm")
    public HttpStatus pwdConfirm(HttpServletRequest request, @RequestBody String pwd) {
        User user = userService.findByToken(JwtService.resolveToken(request));
        if (!passwordEncoder.matches(pwd.substring(0, pwd.length() - 1), user.getPassword())) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }
}
