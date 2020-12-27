package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByUpwardStation(Station upwardStation);

    Optional<Section> findByDownStation(Station downStation);
}
