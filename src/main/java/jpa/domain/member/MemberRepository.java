package jpa.domain.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.age > :age and m.email = :email and m.password = :password")
    List<Member> findMembers(@Param("age") int age, @Param("email") String email, @Param("password") String password);
    List<Member> findMembersByAgeGreaterThan(int age);
}
