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
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = {"4. Comment"})
@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/boardList")
@Controller
public class CommentController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @GetMapping("/{boardNo}/comment")
    public List<Comment> getBoardComments(@PathVariable("boardNo") Long no){
        Board board = boardRepository.findById(no).get();

        return commentRepository.findCommentsByBoard(board);
    }

    @PutMapping("/{boardNo}/comment")
    public Comment createComment(@PathVariable("boardNo") Long no,
                                 @RequestBody Comment comment,
                                 HttpServletRequest request){

        User user = userService.findByToken(JwtTokenProvider.resolveToken(request));

        Optional<Board> boardItem = boardRepository.findById(no);
        comment.setBoard((boardItem.get()));
        comment.setUsrEmail(user.getUsrEmail());

        commentRepository.save(comment);

        return comment;
    }

    @GetMapping("/token/{boardNo}/comment/{commentNo}")
    public Comment updateComment(@PathVariable("boardNo") Long no,
                                 @PathVariable("commentNo") Long commentNo,
                                 @RequestBody Comment comment,
                                 HttpServletRequest request){

        try {
            User user = userService.findByToken(JwtTokenProvider.resolveToken(request));

            Comment com = commentRepository.findById(commentNo).get();
            String commentWriter = com.getUsrEmail();

            if(!commentWriter.equals(user.getUsrEmail())){
                return null;
            }

            Optional<Board> boardItem = boardRepository.findById(no);
            comment.setBoard((boardItem.get()));
            Comment newComment = commentRepository.findById(commentNo).get();
            newComment.setCommentContents(comment.getCommentContents());
            newComment.setUsrEmail(comment.getUsrEmail());

            return comment;

        }catch (Exception e){
            return null;
        }
    }

    @DeleteMapping("/token/{boardNo}/comment/{commentNo}")
    public String deleteComment(@PathVariable("boardNo") Long no,
                                @PathVariable("commentNo") Long commentNo,
                                HttpServletRequest request){

        try {
            User user = userService.findByToken(JwtTokenProvider.resolveToken(request));

            Comment com = commentRepository.findById(commentNo).get();
            String commentWriter = com.getUsrEmail();

            if(!commentWriter.equals(user.getUsrEmail())){
                return "Comment Delete Fail";
            }

            commentRepository.deleteById(commentNo);
            return "Comment Delete Success";

        }catch (Exception e){
            return "Comment Delete Fail";
        }
    }
}
