package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findAllByAge(int age);
	List<Member> findAllByAgeGreaterThanEqual(int age);
	List<Member> findAllByEmailContains(String email);
}
