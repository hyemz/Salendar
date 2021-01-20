package backend.server.salendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.server.salendar.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsrSeq(Long usrSeq);
    public Optional<User> findByUsrNick(String usrNick);
    void deleteByUsrSeq(Long mbrNo);
    public Optional<Object> findByUsrEmail(String usrNick);
}
