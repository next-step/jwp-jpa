package jpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jpa.entity.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
	List<Line> findByCreatedDateBefore(LocalDateTime createDate);

	Line findByColor(String color);

	Optional<Line> findByName(String name);

	@Modifying
	void deleteByName(String name);
}