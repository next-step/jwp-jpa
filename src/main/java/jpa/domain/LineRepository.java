package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LineRepository extends JpaRepository<Line, Long> {

	List<Line> findAllByColor(String color);
	Optional<Line> findByName(String name);
}
