package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Favorite findByFromStation(Station station);
    Favorite findByToStation(Station station);
}
