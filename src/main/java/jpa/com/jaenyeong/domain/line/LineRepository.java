package jpa.com.jaenyeong.domain.line;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LineRepository extends JpaRepository<Line, Long> {
    Line findByName(String name);
    Optional<Line> findById(Long id);
    List<Line> findByColor(String colorName);
}
