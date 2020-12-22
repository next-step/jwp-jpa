package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByAge(int age);

    List<Member> findByEmail(String email);
}
