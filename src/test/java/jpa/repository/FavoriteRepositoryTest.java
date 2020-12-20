package jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FavoriteRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void addFavorite() {
        Member expected = memberRepository.save(new Member(24, "bong_pal@dev.com"));
        List<Station> stations = stationRepository.saveAll(Arrays.asList(
              new Station("삼성역"),
              new Station("강남역"),
              new Station("청계산입구")
        ));

        Favorite favorite = new Favorite(stations.get(0), stations.get(1), expected);
        Favorite favorite2 = new Favorite(stations.get(0), stations.get(2), expected);

        //when
        favorite = favoriteRepository.save(favorite);
        favorite2 = favoriteRepository.save(favorite2);

        //then
        entityManager.flush();
        entityManager.clear();
        Member byEmail = memberRepository.findByEmail(expected.getEmail());

        assertThat(byEmail.getFavorites()).hasSize(2);
        assertThat(byEmail).isEqualTo(expected);
    }
}