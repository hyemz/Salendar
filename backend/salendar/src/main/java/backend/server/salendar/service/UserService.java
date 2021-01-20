package backend.server.salendar.service;

import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
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
    public Optional<User> findByUsrSeq(Long usrSeq) {
        Optional<User> user = userRepository.findByUsrSeq(usrSeq);
        return user;
    }

    /*
    * 회원 탈퇴
    */
    public void deleteByUsrSeq(Long usrSeq) {
        userRepository.deleteByUsrSeq(usrSeq);
    }

    /*
    * 회원정보 수정
    */
    public void updateByUsrSeq(Long usrSeq, User user) {
        Optional<User> e = userRepository.findByUsrSeq(usrSeq);
        if (e.isPresent()) {
            e.get().setUsrSeq(user.getUsrSeq());
//            e.get().setUsrFollowing(user.getUsrFollowing());
            e.get().setUsrEmail(user.getUsrEmail());
            e.get().setUsrNick(user.getUsrNick());
            e.get().setUsrPwd(user.getUsrPwd());
            userRepository.save(user);
        }
    }
}
