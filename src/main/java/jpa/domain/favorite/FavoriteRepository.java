package jpa.domain.favorite;

import jpa.domain.favorite.Favorite;
import jpa.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Favorite findByMember(Member member);
}
