package jpa.repository;

import jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
     List<Member> findByAge(int age);
     Member findByEmail(String email);
}
