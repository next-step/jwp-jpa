package jpa.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
	Line findByName(String name);

	Line findByColor(String color);

	Line findByNameAndColor(String name, String color);

	Line findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

	Line findByCreatedDate(LocalDateTime createdDate);

	Line findFirstByCreatedDate(LocalDateTime createdDate);

	List<Line> findTop3ByCreatedDate(LocalDateTime createdDate);

	List<Line> findTop10ByCreatedDate(LocalDateTime createdDate);

	List<Line> findByCreatedDateOrderByColor(LocalDateTime createdDate);

	List<Line> findByCreatedDateOrderByColorDesc(LocalDateTime createdDate);
}
