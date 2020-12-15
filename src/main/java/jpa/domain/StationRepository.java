package jpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : leesangbae
 * @project : jpa
 * @since : 2020-12-15
 */
public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByName(String name);

}
