package backend.server.salendar.service;

import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * 회원 가입
     */
    public User join(User user) {
        //중복 이름 체크
        validateDuplicateUser(user);
        userRepository.save(user);
        return user;
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUsrNick(user.getUsrNick())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });

        userRepository.findByUsrEmail(user.getUsrEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }


    /*
    * 로그인
    */
    public void Login() {

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
    * 회원정보 수정
    */
    public void updateByUsrNo(Long usrNo, User user) {
        Optional<User> e = userRepository.findByUsrNo(usrNo);
        if (e.isPresent()) {
            e.get().setUsrNo(user.getUsrNo());
//          e.get().setUsrFollowing(user.getUsrFollowing());
            e.get().setUsrEmail(user.getUsrEmail());
            e.get().setUsrNick(user.getUsrNick());
            e.get().setUsrPwd(user.getUsrPwd());
            userRepository.save(user);
        }
    }
}
