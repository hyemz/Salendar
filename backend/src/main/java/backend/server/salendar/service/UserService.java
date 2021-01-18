package backend.server.salendar.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
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
        List<User> members = new ArrayList<>();
        UserRepository.findAll().forEach(e -> members.add(e));
        return members;
    }

    public Optional<User> findByUsrSeq(Long mbrNo) {
        Optional<User> member = UserRepository.findByUsrSeq(mbrNo);
        return member;
    }

    public void deleteByUsrSeq(Long mbrNo) {
        UserRepository.deleteByUsrSeq(mbrNo);
    }

    public User save(User member) {
        UserRepository.save(member);
        return member;
    }

    public void updateByUsrSeq(Long mbrNo, User member) {
        Optional<User> e = UserRepository.findByUsrSeq(mbrNo);
        if (e.isPresent()) {
            e.get().setUsrSeq(member.getUsrSeq());
            e.get().setUsrFollowing(member.getUsrFollowing());
            e.get().setUsrEmail(member.getUsrEmail());
            e.get().setUsrNick(member.getUsrNick());
            e.get().setUsrPwd(member.getUsrPwd());
            UserRepository.save(member);
        }
    }
}
