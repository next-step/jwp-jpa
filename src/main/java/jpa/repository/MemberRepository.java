package jpa.repository;

import java.util.List;
import jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    List<Member> findAllByAge(int age);

    List<Member> findAllByEmailEndsWith(String postFix);

    @Transactional
    @Modifying
    @Query("delete from Member m where m.email like :email")
    void deleteByEmailLikeInQuery(@Param("email") String email);
}
