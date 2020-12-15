package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
public interface LineRepository extends JpaRepository<Line, Long> {

    Line findByName(String name);

    Line findByColor(String color);

}
