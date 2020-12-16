package jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
