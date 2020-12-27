package jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByEmailContaining(String email);

	List<Member> findTop2ByOrderByAgeDesc();

	Stream<Member> findAllByOrderByAgeDesc();

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Member m set m.age = m.age + :age")
	int updateAllAgePlus(@Param("age") int age);

	@Modifying
	void deleteByEmailContaining(String email);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Member m where m.email like %?1%")
	void deleteInBulkByEmailContaining(String email);
}