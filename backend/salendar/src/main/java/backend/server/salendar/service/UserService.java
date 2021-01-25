package backend.server.salendar.service;

import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.security.JwtTokenProvider;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void validateDuplicateUserNick(String usrNick) {
        userRepository.findByUsrNick(usrNick)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });
    }


    public void validateDuplicateUserEmail(String usrEmail) {
        userRepository.findByUsrEmail(usrEmail)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }


    /**
     * 전체 회원 조회
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(e -> users.add(e));
        return users;
    }


    /*
     * 단일 회원 조회
     */
    public Optional<User> findByUsrNo(Long usrNo) {
        Optional<User> user = userRepository.findByUsrNo(usrNo);
        return user;
    }


    /*
    * 이메일로 회원 조회
    */
    public User loadUserByUsername(String usrEmail) throws UsernameNotFoundException {
        return (User) userRepository.findByUsrEmail(usrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }


    /*
    * 회원 탈퇴
    */
    public void deleteByUsrNo(Long usrNo) {
        userRepository.deleteByUsrNo(usrNo);
    }


    /*
    * Token으로 회원찾기
    */
    public User findByToken(String token) {
        return loadUserByUsername(JwtTokenProvider.getUserNo(token));
    }


    /*
    * 회원 이미지 설정
    * 얘는 DB에 저장하는 방법
    */
//    @SneakyThrows
//    public void saveUserImage(String token, MultipartFile file) {
//        User user = findByToken(token);
//        Byte[] byteObjects = new Byte[file.getBytes().length];
//        int i = 0;
//        for (byte b: file.getBytes()){
//            byteObjects[i++] = b;
//        }
//        user.setUsrImg(byteObjects);
//        userRepository.save(user);
//    }

    /*
    * 얘는 우리가 정한 방법
    */
    @SneakyThrows
    public void saveUserImageUrl(String token, String Url) {
        User user = findByToken(token);
        user.setUsrImgUrl(Url);
        userRepository.save(user);
    }
}
