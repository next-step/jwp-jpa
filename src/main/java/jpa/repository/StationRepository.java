package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Station;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
public interface StationRepository extends JpaRepository<Station, Long> {
}
