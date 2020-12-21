package jpa.domain.manyToMany;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManyToManyStationRepository extends JpaRepository<ManyToManyStation, Long> {
    Optional<ManyToManyStation> findByName(String name);
}
