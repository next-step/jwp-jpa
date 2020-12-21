package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.LineStation;

/**
 * @author : byungkyu
 * @date : 2020/12/21
 * @description :
 **/
public interface LineStationRepository extends JpaRepository<LineStation, Long> {
}
