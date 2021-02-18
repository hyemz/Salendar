package backend.server.salendar.controller;

import backend.server.salendar.domain.Board;
import backend.server.salendar.domain.Comment;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.BoardRepository;
import backend.server.salendar.repository.CommentRepository;
import backend.server.salendar.security.JwtTokenProvider;
import backend.server.salendar.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Api(tags = {"4. Comment"})
@RestController
@RequestMapping("/api/boardList")
@Controller
public class CommentController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    //  댓글 가져오기
    @GetMapping("/{boardNo}/comment")
    public List<Comment> getBoardComments(@PathVariable("boardNo") Long no){
        Board board = boardRepository.findById(no).get();

        return commentRepository.findCommentsByBoard(board);
    }

    //  댓글 작성하기
    @PutMapping("/{boardNo}/comment")
    public Comment createComment(@PathVariable("boardNo") Long no,
                                 @RequestBody Comment comment,
                                 HttpServletRequest request){

        User user = userService.findByToken(JwtTokenProvider.resolveToken(request));

        Optional<Board> boardItem = boardRepository.findById(no);
        comment.setBoard((boardItem.get()));
        comment.setUsrNick(user.getUsrNick());

        //  현재시각 가져오기
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);
        comment.setCreatedDate(dateString);
        comment.setModifiedDate(dateString);

        commentRepository.save(comment);

        return comment;
    }

    // 댓글 수정하기
    @PutMapping("/token/{boardNo}/comment/{commentNo}")
    public Comment updateComment(@PathVariable("boardNo") Long no,
                                 @PathVariable("commentNo") Long commentNo,
                                 @RequestBody Comment comment,
                                 HttpServletRequest request){

        try {
            User user = userService.findByToken(JwtTokenProvider.resolveToken(request));// 로그인한 사용자
            Comment com = commentRepository.findById(commentNo).get();// 수정할 댓글
            String commentWriter = com.getUsrNick();// 수정할 댓글의 작성자

            //  댓글 수정 권한 확인 (로그인한 사용자와 수정할 댓글의 작성자가 같은지 여부 확인)
            if(!commentWriter.equals(user.getUsrNick())){  //  댓글 수정 권한 없ㅇ음
                return null;
            }

            //  댓글 수정 권한 ㅇ
            Optional<Board> boardItem = boardRepository.findById(no);
            Comment newComment = commentRepository.findById(commentNo).get();
            newComment.setBoard((boardItem.get()));
            newComment.setCommentContents(comment.getCommentContents());
            newComment.setUsrNick(user.getUsrNick());

            // 댓글 작성시각 업데이트 하기
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = format.format(date);
            newComment.setModifiedDate(dateString);

            commentRepository.save(newComment);

            return newComment;

        }catch (Exception e){
            return null;
        }
    }

    //  댓글 삭제하기
    @DeleteMapping("/token/{boardNo}/comment/{commentNo}")
    public String deleteComment(@PathVariable("boardNo") Long no,
                                @PathVariable("commentNo") Long commentNo,
                                HttpServletRequest request){

        try {
            User user = userService.findByToken(JwtTokenProvider.resolveToken(request));

            Comment com = commentRepository.findById(commentNo).get();
            String commentWriter = com.getUsrNick();

            if(!commentWriter.equals(user.getUsrNick())){
                return "Comment Delete Fail";
            }

            commentRepository.deleteById(commentNo);
            return "Comment Delete Success";

        }catch (Exception e){
            return "Comment Delete Fail";
        }
    }
}
