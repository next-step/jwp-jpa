package jpa.repository;

import java.util.List;
import java.util.Optional;
import jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<List<Member>> findAllByAge(int age);

    List<Member> findAllByEmailEndsWith(String postFix);

    @Transactional
    @Modifying
    @Query("delete from Member m where m.email= :email")
    void deleteByEmailInQuery(@Param("email") String email);
}
