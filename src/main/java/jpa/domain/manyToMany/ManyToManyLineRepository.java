package jpa.domain.manyToMany;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManyToManyLineRepository  extends JpaRepository<ManyToManyLine, Long> {
    Optional<ManyToManyLine> findByName(String name);
}
