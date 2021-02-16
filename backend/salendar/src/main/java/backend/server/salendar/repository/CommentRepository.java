package backend.server.salendar.repository;

import backend.server.salendar.domain.Board;
import backend.server.salendar.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByBoard(Board board);
}
