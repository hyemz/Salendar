package backend.server.salendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.server.salendar.domain.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);
}
