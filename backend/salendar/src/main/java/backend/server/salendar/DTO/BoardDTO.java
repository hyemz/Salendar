package backend.server.salendar.DTO;

import backend.server.salendar.domain.Board;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {

    private Long boardNo;
    private String usrEmail;
    private String boardTitle;
    private String boardContents;

    private LocalDateTime createdDate;

    public Board toEntity(){
        Board build = Board.builder()
                .boardNo(boardNo)
                .usrEmail(usrEmail)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .build();

        return build;
    }

    @Builder
    public BoardDTO(Long boardNo, String usrEmail, String boardTitle, String boardContents, LocalDateTime createdDate) {
        this.boardNo = boardNo;
        this.usrEmail = usrEmail;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.createdDate = createdDate;
    }

    public Long getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(Long boardNo) {
        this.boardNo = boardNo;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContents() {
        return boardContents;
    }

    public void setBoardContents(String boardContents) {
        this.boardContents = boardContents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
