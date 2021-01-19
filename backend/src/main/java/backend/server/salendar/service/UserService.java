package backend.server.salendar.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
public class UserService {

//    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * 회원 가입
     */
//    public Long join(User user) {
//        //중복 이름 체크
//        validateDuplicateMember(user);
//        userRepository.save(user);
//        return user.getUsrSeq();
//    }
//
//    private void validateDuplicateMember(User user) {
//        userRepository.findByName(user.getName())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
//    }

    /**
     * 전체 회원 조회
     */
//    public List<User> findMembers() {
//        return userRepository.findAll();
//    }
//
//    public Optional<User> findOne(Long memberId) {
//        return userRepository.findById(memberId);
//    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(e -> users.add(e));
        return users;
    }

    public Optional<User> findByUsrSeq(Long mbrNo) {
        Optional<User> user = userRepository.findByUsrSeq(mbrNo);
        return user;
    }

    public void deleteByUsrSeq(Long mbrNo) {
        userRepository.deleteByUsrSeq(mbrNo);
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

//    public void updateByUsrSeq(Long mbrNo, User member) {
//        Optional<User> e = userRepository.findByUsrSeq(mbrNo);
//        if (e.isPresent()) {
//            e.get().setUsrSeq(member.getUsrSeq());
//            e.get().setUsrFollowing(member.getUsrFollowing());
//            e.get().setUsrEmail(member.getUsrEmail());
//            e.get().setUsrNick(member.getUsrNick());
//            e.get().setUsrPwd(member.getUsrPwd());
//            userRepository.save(member);
//        }
//    }
}
