package jpa.repository;

import jpa.domain.Favorite;
import jpa.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	Favorite findByDepartureStation(Station station);
	Favorite findByArrivalStation(Station station);
}
