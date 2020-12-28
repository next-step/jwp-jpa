package jpa.domain.member;

import jpa.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByAge(int age);
    Member findByEmail(String email);
}
