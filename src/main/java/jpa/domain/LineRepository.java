package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
public interface LineRepository extends JpaRepository<Line, Long> {

    Line findByName(String name);

    Line findByColor(String color);

    @Query("select l " +
            "from Line l " +
            "join fetch l.lineStations " +
            "where l.name = :name")
    Line findByNameWithLineStation(String name);
}
