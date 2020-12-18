package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	List<Favorite> findByMember(Member member);

}
