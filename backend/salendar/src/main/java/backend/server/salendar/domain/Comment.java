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

    private String commentContents;
    private String createdDate;
    private String modifiedDate;

    private String usrNick;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @Override
    public boolean equals(Object o){
        if(this == o)   return true;
        if(o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment)  o;
        return Objects.equals(commentNo, comment.commentNo) &&
                Objects.equals(usrNick, comment.usrNick) &&
                Objects.equals(commentContents, comment.commentContents) &&
                Objects.equals(createdDate, comment.createdDate) &&
                Objects.equals(modifiedDate, comment.modifiedDate);
    }

}
