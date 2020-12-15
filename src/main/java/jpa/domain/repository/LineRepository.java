package jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.entity.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
	Line findByName(String name);
}
