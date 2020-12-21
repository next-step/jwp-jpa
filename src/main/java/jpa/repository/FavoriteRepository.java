package jpa.repository;

import jpa.dao.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Stream<Favorite> findByMember_Password(String password);
}
