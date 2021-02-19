package backend.server.salendar.controller;

import backend.server.salendar.domain.Board;
import backend.server.salendar.domain.Comment;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.BoardRepository;
import backend.server.salendar.repository.CommentRepository;
import backend.server.salendar.security.JwtTokenProvider;
import backend.server.salendar.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Api(tags = {"3. Board"})
@RestController
@RequestMapping("/api/boardList")
@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    //  게시물 리스트
    @GetMapping("/")
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    //  게시물 상세보기
    @GetMapping("/{boardNo}")
    public Board getBoard(@PathVariable("boardNo") String no) {

        try {
            Long boardNo = Long.parseLong(no);
            Optional<Board> board = boardRepository.findById((boardNo));
            board.get().setHit(board.get().getHit() + 1);

            return boardRepository.save(board.get());

        } catch (Exception e) {
            return null;
        }

    }

    //  게시물 생성
    @PutMapping(value = "/token/createboard", produces = "application/json")
    public Board createBoard(@RequestBody Board board,
                             HttpServletRequest request) {

        User user = userService.findByToken(JwtTokenProvider.resolveToken(request));
        board.setUsrEmail(user.getUsrEmail());
        board.setUsrNick(user.getUsrNick());

        //  현재시각 가져오기
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);
        board.setCreatedDate(dateString);
        board.setModifiedDate(dateString);
        board.setHit(0L);

        return boardRepository.save(board);
    }

    //  게시물 수정
    @PostMapping("/token/{boardNo}")
    public Board updateBoard(@PathVariable("boardNo") String no,
                             @RequestBody Board newBoard,
                             HttpServletRequest request) {

        try {
            //  수정하려는 사람이 글 작성자와 일치하는지 확인
            User user = userService.findByToken(JwtTokenProvider.resolveToken(request));
            Long boardNo = Long.parseLong(no);
            Optional<Board> board = boardRepository.findById(boardNo);
            String boardWriter = board.get().getUsrEmail();

            //  일치하지 않을경우 예외처리
            if (!boardWriter.equals(user.getUsrEmail())) {
                throw new Exception();
            }

            //  일치하면 게시글 수정
            board.get().setBoardTitle(newBoard.getBoardTitle());
            board.get().setBoardContents(newBoard.getBoardContents());
            board.get().setBoardType(newBoard.getBoardType());
            board.get().setBoardImg(newBoard.getBoardImg());

            //  수정 시각 업데이트
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = format.format(date);
            board.get().setModifiedDate(dateString);

            boardRepository.save(board.get());
            return board.get();

        } catch (Exception e) {
            return null;
        }
    }

    //  게시물 삭제
    @DeleteMapping("/token/{boardNo}")
    public String deleteBoard(@PathVariable("boardNo") Long no,
                              HttpServletRequest request) {

        try {
            User user = userService.findByToken(JwtTokenProvider.resolveToken(request));

            Board board = boardRepository.findById(no).get();
            String boardWriter = board.getUsrEmail();

            if (!boardWriter.equals(user.getUsrEmail())) {
                throw new Exception();
            }

            List<Comment> comments = commentRepository.findCommentsByBoard(board);
            for (Comment comment : comments) {
                commentRepository.deleteById(comment.getCommentNo());
            }

//            List<Like> likes = likeRepository.findLikesByBoard(board);
//            for(int i=0; i<likes.size(); i++){
//                likeRepository.deleteById(likes.get(i).getId());
//            }


            boardRepository.deleteById(no);

            return "Delete Success";

        } catch (Exception e) {
            return "Delete Fail";
        }
    }


//    @PostMapping(value = "/token/{boardNo}/like", produces = "application/json")
//    @ResponseBody
//    public String likePost(@PathVariable("boardNo") Long no, HttpServletRequest request) throws JsonProcessingException {
//
//        User loginUser = userService.findByToken(JwtTokenProvider.resolveToken(request));
//
//        String isLike;
//        String loginEmail = loginUser.getUsrEmail();
//        if(loginEmail == null){
//            isLike = "ERROR: login to like/dislike board";
//        }
//        else{
//            User user = userService.loadUserByUsername(loginEmail);
//            Board board = boardRepository.findById(no).get();
//
//            int r = boardService.likePost(board, user);
//            if(r == 1){
//                isLike = "like";
//            }
//            else{
//                isLike = "dislike";
//            }
//        }
//
//        return objectMapper.writeValueAsString(isLike);
//    }
}
