package jpa.com.jaenyeong.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long aLong);
    Optional<Member> findByEmail(String email);
    List<Member> findByAge(int age);
}
