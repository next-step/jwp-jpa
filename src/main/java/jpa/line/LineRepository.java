package jpa.line;

import org.springframework.data.repository.CrudRepository;

public interface LineRepository extends CrudRepository<Line, Long> {

    Line findByName(String name);
}
