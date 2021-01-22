package backend.server.salendar.repository;

import backend.server.salendar.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
