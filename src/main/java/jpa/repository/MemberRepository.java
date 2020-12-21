package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Member;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
