package backend.server.salendar.service;

import backend.server.salendar.DTO.BoardDTO;
import backend.server.salendar.repository.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardDTO boardDTO){
        return boardRepository.save(boardDTO.toEntity()).getBoardNo();
    }
}
