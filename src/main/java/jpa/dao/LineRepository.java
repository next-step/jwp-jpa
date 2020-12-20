package jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
}
