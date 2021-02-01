package backend.server.salendar.controller;

import backend.server.salendar.domain.Board;
import backend.server.salendar.domain.Comment;
import backend.server.salendar.repository.BoardRepository;
import backend.server.salendar.repository.CommentRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = {"4. Comment"})
@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/boardList")
@Controller
public class CommentController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{boardNo}/comment")
    public List<Comment> getBoardComments(@PathVariable("boardNo") Long no){
        Board board = boardRepository.findById(no).get();

        return commentRepository.findCommentsByBoard(board);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{boardNo}/comment")
    public Comment createComment(@PathVariable("boardNo") Long no, @RequestBody Comment comment){
        Optional<Board> boardItem = boardRepository.findById(no);
        comment.setBoard((boardItem.get()));
        commentRepository.save(comment);

        return comment;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{boardNo}/comment/{commentNo}")
    public Comment updateComment(@PathVariable("boardNo") Long no,
                                 @PathVariable("commentNo") Long commentNo,
                                 @RequestBody Comment comment){

        try {
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{boardNo}/comment/{commentNo}")
    public String deleteComment(@PathVariable("boardNo") Long no,
                                @PathVariable("commentNo") Long commentNo){

        try {
            commentRepository.deleteById(commentNo);
            return "Comment Delete Success";

        }catch (Exception e){
            return "Comment Delete Fail";
        }
    }
}
