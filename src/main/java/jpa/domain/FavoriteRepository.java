package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	List<Favorite> findAllByMember(Member member);
	List<Favorite> findAllByDepartureStation(Station arrivalStation);
}
