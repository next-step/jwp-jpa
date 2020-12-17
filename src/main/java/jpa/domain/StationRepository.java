package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByName(String name);

    @Query("select s " +
            "from Station s " +
            "join fetch s.lineStations " +
            "where s.name = :name")
    Station findByNameWithLineStation(String name);

}
