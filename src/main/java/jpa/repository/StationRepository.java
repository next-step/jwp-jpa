package jpa.repository;

import jpa.domain.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, Long> {

    Station findByName(String name);
}
