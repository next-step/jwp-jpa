package jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}