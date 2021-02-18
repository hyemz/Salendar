package backend.server.salendar.repository;

import backend.server.salendar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsrNo(Long usrNo);
    Optional<User> findByUsrEmail(String usrEmail);
    Optional<User> findByUsrNick(String usrNick);

    @Transactional
    void deleteByUsrNo(Long usrNo);

}
