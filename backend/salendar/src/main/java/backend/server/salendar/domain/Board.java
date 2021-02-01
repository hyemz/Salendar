package backend.server.salendar.domain;

import lombok.*;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    // 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    private String usrEmail;
    private String boardTitle;
    private String boardContents;

    @Builder
    public Board(String usrEmail, String boardTitle, String boardContents) {
        this.usrEmail = usrEmail;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
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

    @Override
    public boolean equals(Object o){
        if(this == o)   return true;
        if(o == null || getClass() != o.getClass()) return false;
        Board board = (Board)  o;
        return Objects.equals(boardNo, board.boardNo) &&
                Objects.equals(usrEmail, board.usrEmail) &&
                Objects.equals(boardTitle, board.boardTitle) &&
                Objects.equals(boardContents, board.boardContents);
    }

    @Override
    public int hashCode(){
        return Objects.hash(boardNo, usrEmail, boardTitle, boardContents);
    }


}


