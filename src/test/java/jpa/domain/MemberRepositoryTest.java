package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {

    private static final String DEFAULT_PASSWORD = "0000";

    @Autowired
    private MemberRepository members;

    @Autowired
    private FavoriteRepository favorites;

    @Autowired
    private EntityManager em;

    @DisplayName("단건 조회를 확인합니다.")
    @Test
    void findOne() {
        // given
        String email = "good_1411@naver.com";
        Member expected = members.save(new Member(28, email, DEFAULT_PASSWORD));

        // when
        Member actual = members.findFirstByEmail(email).get();

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @DisplayName("조회 시 값이 없을 경우 null 이 반환됩니다.")
    @Test
    void findOneNull() {
        // when
        Optional<Member> memberOptional = members.findFirstByEmail("empty@gmail.com");

        // then
        assertThat(memberOptional.isPresent()).isFalse();
    }

    @DisplayName("여러건 조회를 확인합니다.")
    @Test
    void findAll() {
        // given
        Member member1 = new Member(28, "good_1411@naver.com", DEFAULT_PASSWORD);
        Member member3 = new Member(29, "next@gmail.com", DEFAULT_PASSWORD);
        Member member2 = new Member(30, "step@gmail.com", DEFAULT_PASSWORD);
        members.saveAll(Arrays.asList(member1, member2, member3));

        // when
        List<Member> memberList = members.findAll();

        // then
        assertAll(
                () -> assertThat(memberList).isNotNull(),
                () -> assertThat(memberList).containsExactly(member1, member2, member3)
        );
    }

    @DisplayName("정상적으로 저장되는지 확인합니다.")
    @Test
    void save() {
        // given
        Member expected = new Member(28, "good_1411@naver.com", DEFAULT_PASSWORD);

        // when
        Member actual = members.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(actual).isSameAs(expected)
        );
    }

    @DisplayName("변경 감지가 정상적으로 작동하는지 확인합니다.")
    @Test
    void update() {
        // given
        Member savedMember = members.save(new Member(28, "good_1411@naver.com", DEFAULT_PASSWORD));

        // when
        Member expected = savedMember.changeEmail("nextstep@gmail.com");
        Member actual = members.findFirstByEmail(expected.getEmail()).get();

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @DisplayName("사용자는 여러 즐겨찾기를 가질 수 있다.")
    @Test
    void favorites() {
        // given
        String email = "good_1411@naver.com";
        Member yohan = new Member(28, email, DEFAULT_PASSWORD);
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        yohan.addFavorite(favorite1);
        yohan.addFavorite(favorite2);

        em.persist(yohan);
        em.persist(favorite1);
        em.persist(favorite2);

        // when
        Member member = members.findFirstByEmail(email).get();
        List<Favorite> favorites = member.getFavorites();

        // then
        assertThat(favorites).contains(favorite1, favorite2);
    }

    @DisplayName("사용자는 즐겨찾기를 추가할 수 있다.")
    @Test
    void saveFavorite() {
        // given
        // 지하철 역 저장
        Station station1 = new Station("금정역");
        Station station2 = new Station("당정역");
        em.persist(station1);
        em.persist(station2);

        String email = "good_1411@naver.com";
        Member yohan = new Member(28, email, DEFAULT_PASSWORD);
        Favorite favorite1 = new Favorite("가는길", station1, station2);
        Favorite favorite2 = new Favorite("오는길", station2, station1);
        yohan.addFavorite(favorite1);
        yohan.addFavorite(favorite2);
        members.save(yohan);

        // when
        Member member = members.findFirstByEmail(email).get();

        // then
        assertThat(member.getFavorites()).contains(favorite1, favorite2);
    }

    @DisplayName("사용자는 즐겨찾기를 업데이트 할 수 있다.")
    @Test
    void updateFavorite() {
        // given
        // 지하철 역 저장
        Station station1 = new Station("금정역");
        Station station2 = new Station("당정역");
        em.persist(station1);
        em.persist(station2);

        // 멤버, 즐겨찾기 저장
        String email = "good_1411@naver.com";
        Member yohan = new Member(28, email, DEFAULT_PASSWORD);
        Favorite favorite1 = new Favorite("가는길", station1, station2);
        Favorite favorite2 = new Favorite("오는길", station2, station1);
        yohan.addFavorite(favorite1);
        yohan.addFavorite(favorite2);
        members.save(yohan);

        // 즐겨 찾기 변경
        Favorite expected = yohan.getFavorite("가는길").changeName("테스트");

        em.flush();
        em.clear();

        // when
        Member member = members.findFirstByEmail(email).get();
        Favorite actual = member.getFavorite(expected.getName());

        // then
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @DisplayName("사용자는 즐겨찾기를 제거할 수 있다.")
    @Test
    void removeFavorite() {
        // given
        // 지하철 역 저장
        Station station1 = new Station("금정역");
        Station station2 = new Station("당정역");
        em.persist(station1);
        em.persist(station2);

        // 멤버, 즐겨찾기 저장
        String email = "good_1411@naver.com";
        Member yohan = new Member(28, email, DEFAULT_PASSWORD);
        Favorite favorite1 = new Favorite("가는길", station1, station2);
        Favorite favorite2 = new Favorite("오는길", station2, station1);
        yohan.addFavorite(favorite1);
        yohan.addFavorite(favorite2);
        members.save(yohan);

        // 즐겨 찾기 제거
        yohan.getFavorites().remove(favorite1);

        // when
        Optional<Favorite> favoriteOptional = favorites.findByName("가는길");
        Member member = members.findFirstByEmail(email).get();
        List<Favorite> actual = member.getFavorites();

        // then
        assertThat(favoriteOptional.isPresent()).isFalse(); // 고아객체 확인
        assertThat(actual.size()).isEqualTo(1); // 삭제 확인
        assertThat(actual).containsExactly(favorite2);
    }

    @DisplayName("사용자가 제거되면 즐겨찾기는 제거된다.")
    @Test
    void removeAll() {
        // given
        // 지하철 역 저장
        Station station1 = new Station("금정역");
        Station station2 = new Station("당정역");
        em.persist(station1);
        em.persist(station2);

        // 멤버, 즐겨찾기 저장
        String email = "good_1411@naver.com";
        Member yohan = new Member(28, email, DEFAULT_PASSWORD);
        Favorite favorite1 = new Favorite("가는길", station1, station2);
        Favorite favorite2 = new Favorite("오는길", station2, station1);
        yohan.addFavorite(favorite1);
        yohan.addFavorite(favorite2);
        members.save(yohan);

        // 즐겨 찾기 제거
        members.delete(yohan);

        // when
        Optional<Favorite> favoriteOptional1 = favorites.findByName("가는길");
        Optional<Favorite> favoriteOptional2 = favorites.findByName("오는길");
        Optional<Member> memberOptional = members.findFirstByEmail(email);

        // then
        assertAll(
                () -> assertThat(favoriteOptional1.isPresent()).isFalse(),
                () -> assertThat(favoriteOptional2.isPresent()).isFalse(),
                () -> assertThat(memberOptional.isPresent()).isFalse()
        );
    }
}
