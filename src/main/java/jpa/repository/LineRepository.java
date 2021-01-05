package jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
	Optional<Line> findByName(String name);
}
