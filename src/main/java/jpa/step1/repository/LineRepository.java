package jpa.step1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.step1.domain.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
}
