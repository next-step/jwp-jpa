package jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.entity.Favorite;
import jpa.entity.Member;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	List<Favorite> findAllByMember(Member member);
}