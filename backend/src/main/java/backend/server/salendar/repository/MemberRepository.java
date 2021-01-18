package backend.server.salendar.repository;


import backend.server.salendar.domain.Member;

import java.util.List;
import java.util.Optional;

//@Repository
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
