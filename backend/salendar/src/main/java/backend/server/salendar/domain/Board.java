package backend.server.salendar.domain;

import lombok.*;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String createdDate;
    private String modifiedDate;
    private String boardType;
    private String imgUrl;

    @Builder
    public Board(String usrEmail, String boardTitle, String boardContents,
                 String createdDate, String modifiedDate, String boardType, String imgUrl) {
        this.usrEmail = usrEmail;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.boardType = boardType;
        this.imgUrl = imgUrl;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)   return true;
        if(o == null || getClass() != o.getClass()) return false;
        Board board = (Board)  o;
        return Objects.equals(boardNo, board.boardNo) &&
                Objects.equals(usrEmail, board.usrEmail) &&
                Objects.equals(boardTitle, board.boardTitle) &&
                Objects.equals(boardContents, board.boardContents) &&
                Objects.equals(createdDate, board.createdDate) &&
                Objects.equals(modifiedDate, board.modifiedDate) &&
                Objects.equals(boardType, board.boardType) &&
                Objects.equals(imgUrl, board.imgUrl);
    }

}


