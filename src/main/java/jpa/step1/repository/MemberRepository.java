package jpa.step1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.step1.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
}
