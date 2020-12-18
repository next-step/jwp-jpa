package jpa.repository;

import jpa.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByName(String name);
}
