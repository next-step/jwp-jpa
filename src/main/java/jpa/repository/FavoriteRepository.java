package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.Favorite;

/**
 * @author : byungkyu
 * @date : 2020/12/20
 * @description :
 **/
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
