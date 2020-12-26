package jpa.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.entity.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
	List<Line> findByCreatedDateBefore(LocalDateTime createDate);

	Line findByColor(String color);
}