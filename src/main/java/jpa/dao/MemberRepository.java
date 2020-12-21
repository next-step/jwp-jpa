package jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
