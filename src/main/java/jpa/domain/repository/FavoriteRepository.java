package jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
