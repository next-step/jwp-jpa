package jpa.repository;

import jpa.domain.Line;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
    Line findByName(String name);
    Line findByNameAndColor(String name, String color);
    List<Line> findByColor(String color);
}
