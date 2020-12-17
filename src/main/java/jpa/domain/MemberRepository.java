package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    @Query("select m " +
            "from Member m " +
            "join fetch m.favorites " +
            "where m.email = :email")
    Member findByEmailWithFavorites(String email);

}
