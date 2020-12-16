package jpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.domain.entity.MemberFavorite;
import jpa.domain.entity.MemberFavoriteId;

public interface MemberFavoriteRepository extends JpaRepository<MemberFavorite, MemberFavoriteId> {
}
