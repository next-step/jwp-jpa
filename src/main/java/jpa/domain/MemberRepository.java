package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String name);

}
