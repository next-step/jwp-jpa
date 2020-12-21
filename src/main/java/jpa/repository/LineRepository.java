package jpa.repository;

import jpa.domain.Line;
import jpa.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
	Line findByName(String name);
}
