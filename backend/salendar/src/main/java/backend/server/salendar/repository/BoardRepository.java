package backend.server.salendar.repository;

import backend.server.salendar.domain.Board;
import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
