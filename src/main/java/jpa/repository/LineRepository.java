package jpa.repository;

import jpa.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
    Line findByColor(String color);
    Line findByName(String name);
}
