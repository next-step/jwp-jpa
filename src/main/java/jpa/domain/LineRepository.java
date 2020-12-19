package jpa.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
	Line findByName(String name);

	Line findByColor(String color);

	Line findByNameAndColor(String name, String color);

	Line findFirstByColor(String color);

	List<Line> findTop3ByColor(String color);

	List<Line> findTop10ByColor(String color);

	List<Line> findAllByOrderByColor();

	List<Line> findAllByOrderByColorDesc();
}
