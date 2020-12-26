package jpa.domain.line;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
	List<Line> findByColor(String color);

	Line findByName(String name);
}
