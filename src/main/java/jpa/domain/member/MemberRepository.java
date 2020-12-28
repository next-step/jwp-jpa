package jpa.domain.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByEmail(String email);
}
