package backend.server.salendar.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    // 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;

    private String commentTitle;
    private String commentContents;

    private String usrEmail;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    public Long getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(Long commentNo) {
        this.commentNo = commentNo;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentContents() {
        return commentContents;
    }

    public void setCommentContents(String commentContents) {
        this.commentContents = commentContents;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)   return true;
        if(o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment)  o;
        return Objects.equals(commentNo, comment.commentNo) &&
                Objects.equals(usrEmail, comment.usrEmail) &&
                Objects.equals(commentTitle, comment.commentTitle) &&
                Objects.equals(commentContents, comment.commentContents);
    }

    @Override
    public int hashCode(){
        return Objects.hash(commentNo, usrEmail, commentTitle, commentContents);
    }


}
