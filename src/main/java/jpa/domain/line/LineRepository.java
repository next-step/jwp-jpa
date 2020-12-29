package jpa.domain.line;

import jpa.domain.line.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
    Line findByName(String name);
}
