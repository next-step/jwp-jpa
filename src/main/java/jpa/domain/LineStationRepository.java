package jpa.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    List<LineStation> findByLine(Line line);

    List<LineStation> findByStation(Station station);
}
