package jpa.repositories;

import jpa.domain.Favorite;
import jpa.domain.Member;
import jpa.domain.Station;
import jpa.domain.repositories.FavoriteRepository;
import jpa.domain.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository members;

  @Autowired
  private FavoriteRepository favorites;

  @Test
  void save() {
    final Member expected = getMemberSampleData();
    assertThat(expected.getId()).isNull();

    final Member actual = members.save(expected);
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getAge()).isEqualTo(34),
        () -> assertThat(actual.getEmail()).isEqualTo("jin87.an@gmail.com"),
        () -> assertThat(actual.getCreatedDate()).isNotNull(),
        () -> assertThat(actual.getModifiedDate()).isNotNull()
    );
  }

  @Test
  void update() {
    // given
    final Member member = members.save(getMemberSampleData());
    // when
    member.changeAge(35);
    members.flush();
    // then
    assertThat(member.getAge()).isEqualTo(35);
    assertThatThrownBy(() -> {
      member.changeAge(-1);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void delete() {
    final Member member = members.save(getMemberSampleData());
    members.delete(member);

    assertThat(members.findById(member.getId()).orElse(null)).isNull();
  }

  @Test
  void saveFavorite() {
    // given
    final Member member = members.save(getMemberSampleData());
    final Favorite favorite = favorites.save(getFavoriteSampleData());
    member.addFavorite(favorite);
    members.save(member);
    members.flush();
    // when
    final Member expected = members.findById(member.getId()).get();
    // then
    assertThat(expected.getFavorites().size()).isEqualTo(1);
    assertThat(expected.getFavorites()).contains(favorite);
  }

  private Member getMemberSampleData() {
    return new Member("jin87.an@gmail.com", 34, "jinjin00!");
  }

  private Favorite getFavoriteSampleData() {
    return new Favorite("출근경로", new Station("삼성역"), new Station("강남역"));
  }
}