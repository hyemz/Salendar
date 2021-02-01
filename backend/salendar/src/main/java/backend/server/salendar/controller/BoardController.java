package backend.server.salendar.controller;

import backend.server.salendar.domain.Board;
import backend.server.salendar.repository.BoardRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = {"3. Board"})
@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/boardList")
@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/")
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

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

    @PostMapping("/{boardNo}")
    public Board updateBoard(@PathVariable("boardNo") String no, @RequestBody Board newBoard){

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

    @PutMapping("/createboard")
    public Board createBoard(@RequestBody Board board){
        Board newBoard = boardRepository.save(board);

        return newBoard;
    }

    @DeleteMapping("/{boardNo}")
    public String deleteBoard(@PathVariable("boardNo") String no){

        try {
            Long boardNo = Long.parseLong(no);
            boardRepository.deleteById(boardNo);

            return "Delete Success";

        }catch (Exception e){
            return "Delete Fail";
        }
    }
}