package jpa.domain.repositories;

import jpa.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
  Line findByName(String name);
}
