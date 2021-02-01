package backend.server.salendar.service;

import backend.server.salendar.domain.Store;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.StoreRepository;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.security.JwtTokenProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public UserService(UserRepository userRepository, StoreRepository storeRepository) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
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
    public User loadUserByUsername(String usrEmail) {
        return (User) userRepository.findByUsrEmail(usrEmail)
                .orElse(null);
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

    /*
     * 팔로우하기
     */
    @Transactional
    public void Follow(String token, String storeName) {
        storeRepository.findStoreByStoreName(storeName).ifPresent(s -> {
            User user = findByToken(token);
            user.getUsrFollowing().add(s);
            userRepository.save(user);
        });
    }

    /*
     * 언팔
     */
    @Transactional
    public void unFollow(String token, String storeName) {
        storeRepository.findStoreByStoreName(storeName).ifPresent(s -> {
            User user = findByToken(token);
            user.getUsrFollowing().remove(s);
            userRepository.save(user);
        });
    }


    /*
     * 팔로윙 조회
     */
    @Transactional
    public Map<String, Boolean> usrFollowings(String token) {
        Map<String, Boolean> response = new HashMap<>();
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            response.put(store.getStoreName(), false);
        }
        List<Store> Followings = findByToken(token).getUsrFollowing();
        System.out.println(Followings.size());
        for (Store store : Followings) {
            response.replace(store.getStoreName(), true);
        }
        for (Map.Entry<String, Boolean> entry : response.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
            return response;
    }
}
