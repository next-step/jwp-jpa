package jpa.step1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.step1.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
