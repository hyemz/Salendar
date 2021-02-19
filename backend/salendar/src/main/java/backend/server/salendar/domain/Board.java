package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @JsonProperty("usrEmail")
    private String usrEmail;

    @JsonProperty("usrNick")
    private String usrNick;

    @JsonProperty("boardTitle")
    private String boardTitle;

    @JsonProperty("boardContents")
    private String boardContents;

    @JsonProperty("createdDate")
    private String createdDate;

    @JsonProperty("modifiedDate")
    private String modifiedDate;

    @JsonProperty("boardType")
    private String boardType;

    @JsonProperty("boardImg")
    @Lob
    private byte[] boardImg;

    @JsonProperty("hit")
    private Long hit;

    @JsonProperty("likeCnt")
    private int likeCnt;

    // 게시글 좋아요
    @ManyToMany(mappedBy = "boardLike", fetch = FetchType.EAGER)
    //@JsonManagedReference(value = "board-like")
    private List<User> boards = new ArrayList<>();

    @Override
    public boolean equals(Object o){
        if(this == o)   return true;
        if(o == null || getClass() != o.getClass()) return false;
        Board board = (Board)  o;
        return Objects.equals(boardNo, board.boardNo) &&
                Objects.equals(usrEmail, board.usrEmail) &&
                Objects.equals(usrNick, board.usrNick) &&
                Objects.equals(boardTitle, board.boardTitle) &&
                Objects.equals(boardContents, board.boardContents) &&
                Objects.equals(createdDate, board.createdDate) &&
                Objects.equals(modifiedDate, board.modifiedDate) &&
                Objects.equals(boardType, board.boardType) &&
                Objects.equals(hit, board.hit) &&
                Objects.equals(likeCnt, board.likeCnt);
//        &&
//                Objects.equals(board_like, board.board_like);
    }
}


