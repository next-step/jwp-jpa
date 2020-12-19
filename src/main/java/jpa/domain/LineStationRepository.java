package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-17
 */
public interface LineStationRepository extends JpaRepository<LineStation, Long> {

    LineStation findByLineNameAndStationName(String lineName, String stationName);
}
