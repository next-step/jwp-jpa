package jpa.step1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.step1.domain.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
