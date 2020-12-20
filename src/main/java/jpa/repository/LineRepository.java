package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Line;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
public interface LineRepository extends JpaRepository<Line, Long> {
}
