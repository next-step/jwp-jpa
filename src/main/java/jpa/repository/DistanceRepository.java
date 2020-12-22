package jpa.repository;

import jpa.dao.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DistanceRepository extends JpaRepository<Distance, Long> {
    Optional<Distance> findByLine_NameAndStation_Name(String lineName, String stationName);
    Stream<Distance> findByStationOrderGreaterThanEqualAndStationOrderLessThan(Integer departureOrder, Integer arrivalOrder);
    Stream<Distance> findByLine_Name(String lineName);
}
