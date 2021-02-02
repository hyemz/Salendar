package backend.server.salendar.controller;

import backend.server.salendar.domain.Board;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.BoardRepository;
import backend.server.salendar.security.JwtTokenProvider;
import backend.server.salendar.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = {"3. Board"})
@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/boardList")
@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    UserService userService;

    //  게시물 리스트
    @GetMapping("/")
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    //  게시물 상세보기
    @GetMapping("/{boardNo}")
    public Board getBoard(@PathVariable("boardNo") String no){

        try {
            Long boardNo = Long.parseLong(no);
            Optional<Board> board = boardRepository.findById((boardNo));

            return board.get();

        }catch (Exception e){
            return null;
        }

    }

    //  게시물 생성
    @PutMapping("/token/createboard")
    public Board createBoard(@RequestBody Board board,
                             HttpServletRequest request){

        User user = userService.findByToken(JwtTokenProvider.resolveToken(request));
        board.setUsrEmail(user.getUsrEmail());
        Board newBoard = boardRepository.save(board);

        return newBoard;
    }

    //  게시물 수정
    @PostMapping("/token/{boardNo}")
    public Board updateBoard(@PathVariable("boardNo") String no,
                             @RequestBody Board newBoard,
                             HttpServletRequest request){

        try {
            Long boardNo = Long.parseLong(no);

            Optional<Board> board = boardRepository.findById(boardNo);

            board.get().setBoardTitle(newBoard.getBoardTitle());
            board.get().setBoardContents(newBoard.getBoardContents());

            boardRepository.save(board.get());

            return board.get();

        }catch (Exception e){
            return null;
        }
    }

    //  게시물 삭제
    @DeleteMapping("/token/{boardNo}")
    public String deleteBoard(@PathVariable("boardNo") Long no,
                              HttpServletRequest request){

        try {
            User user = userService.findByToken(JwtTokenProvider.resolveToken(request));

            Board board = boardRepository.findById(no).get();
            String boardWriter = board.getUsrEmail();

            if(!boardWriter.equals(user.getUsrEmail())){
                System.out.println("당신은 게시물 작성자가 아님! 지울수 없 다 !!!!!!!!!");
                return "Delete Fail";
            }

            Long boardNo = no;
            boardRepository.deleteById(boardNo);

            return "Delete Success";

        }catch (Exception e){
            return "Delete Fail";
        }
    }
}
