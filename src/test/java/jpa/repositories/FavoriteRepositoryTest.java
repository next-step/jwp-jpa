package jpa.repositories;

import jpa.domain.Favorite;
import jpa.domain.Station;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        favoriteRepository.save(new Favorite());
    }

    @AfterEach
    void tearDown() {
        favoriteRepository.deleteAll();
        entityManager
                .createNativeQuery("alter table favorite alter column id restart 1")
                .executeUpdate();
    }

    @Test
    @DisplayName("Favorite 추가")
    void save() {
        // given, when
        Favorite actual = favoriteRepository.save(new Favorite());

        // then
        FavoriteAssertAll(actual);
    }

    @Test
    @DisplayName("Favorite 삭제")
    void delete() {
        // given when
        favoriteRepository.delete(favoriteRepository.findById(1L).get());
        Optional<Favorite> actual = favoriteRepository.findById(1L);

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Favorite 조회")
    void select() {
        // given when
        Optional<Favorite> actual = favoriteRepository.findById(1L);

        // then
        FavoriteAssertAll(actual.get());
    }

    private void FavoriteAssertAll(Favorite actual) {
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("즐겨찾기에 출발역과 도착역이 포함되어 있는지 확인")
    void selectRelationMappingFavoriteAndStation() {
        // given
        saves();
        // when
        List<Favorite> favorites = favoriteRepository.findAll();
        // then
        assertThat(favorites).isNotEmpty();
        assertThat(favorites).hasSize(4);
        assertThat(favorites.get(3).getDepartureStation()).isNull();
        assertThat(favorites.get(3).getArrivalStation()).isNull();
        assertThat(favorites.get(1).getDepartureStation().getName()).isEqualTo("고속터미널");
        assertThat(favorites.get(1).getArrivalStation().getName()).isEqualTo("반포");
    }

    private void saves() {
        favoriteRepository.saveAll(Arrays.asList(Favorite.of(Station.of("고속터미널"),Station.of("반포")),
                Favorite.of(Station.of("수락산"),Station.of("도봉산")),
                Favorite.of(null, null)));
        entityManager.clear();
    }
}