package backend.server.salendar.controller;

import backend.server.salendar.DTO.BoardDTO;
import backend.server.salendar.domain.Board;
import backend.server.salendar.service.BoardService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = {"3. Board"})
@RestController
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/board")
@Controller
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/boardList")
    public ResponseEntity<Board> list() {
        Optional<Board> board = boardService.;
        return new ResponseEntity<Board>(board.get(), HttpStatus.OK);
    }

    public ResponseEntity<Board> write(BoardDTO boardDTO){


    }
}
