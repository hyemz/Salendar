package backend.server.salendar.service;

import backend.server.salendar.domain.Board;
import backend.server.salendar.domain.Store;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.BoardRepository;
import backend.server.salendar.repository.StoreRepository;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.transaction.Transactional;
import java.util.*;

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BoardRepository boardRepository;

    public UserService(UserRepository userRepository, StoreRepository storeRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.boardRepository = boardRepository;
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


    /*
     * 이메일로 회원 조회
     */
    public User loadUserByUsername(String usrEmail) {
        return userRepository.findByUsrEmail(usrEmail)
                .orElse(null);
    }


    /*
     * Token으로 회원찾기
     */
    public User findByToken(String token) {
        return loadUserByUsername(JwtTokenProvider.getUserNo(token));
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

    //  게시글 좋아요
    @Transactional
    public void likePost(String token, Long boardNo) {
        boardRepository.findById(boardNo).ifPresent(s -> {
            User user = findByToken(token);
            user.getBoardLike().add(s);
            Board board = boardRepository.findById(boardNo).get();
            int curLikeCnt = board.getLikeCnt();
            board.setLikeCnt(curLikeCnt+1);
            boardRepository.save(board);
            userRepository.save(user);
        });
    }

    //  게시글 좋아요 취소
    @Transactional
    public void unlikePost(String token, Long boardNo) {
        boardRepository.findById(boardNo).ifPresent(s -> {
            User user = findByToken(token);
            user.getBoardLike().remove(s);
            Board board = boardRepository.findById(boardNo).get();
            int curLikeCnt = board.getLikeCnt();
            board.setLikeCnt(curLikeCnt-1);
            boardRepository.save(board);
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
        for (Store store : Followings) {
            response.replace(store.getStoreName(), true);
        }
        return response;
    }
}
