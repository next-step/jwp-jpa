package jpa.com.jaenyeong.domain.member;

import jpa.com.jaenyeong.domain.favorite.Favorite;
import jpa.com.jaenyeong.domain.favorite.FavoriteRepository;
import jpa.com.jaenyeong.domain.station.Station;
import jpa.com.jaenyeong.domain.station.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@DataJpaTest
@DisplayName("Member Repository 테스트")
class MemberRepositoryTest {
    @Autowired
    private MemberRepository members;
    @Autowired
    private StationRepository stations;
    @Autowired
    private FavoriteRepository favorites;

    private Member firstMember;
    private Member secondMember;
    private Member thirdMember;

    private Favorite firstFavorite;
    private Favorite secondFavorite;
    private Favorite thirdFavorite;

    private Station kkachisan;
    private Station gangnam;
    private Station yongsan;
    private Station jamsil;
    private Station gimpoAirport;
    private Station yangjae;

    @BeforeEach
    void setUp() {
        firstMember = new Member();
        secondMember = new Member();
        thirdMember = new Member();

        kkachisan = new Station("까치산역");
        gangnam = new Station("강남역");
        yongsan = new Station("용산역");
        jamsil = new Station("잠실역");
        gimpoAirport = new Station("김포공항역");
        yangjae = new Station("양재역");
        stations.save(kkachisan);
        stations.save(gangnam);
        stations.save(yongsan);
        stations.save(jamsil);
        stations.save(gimpoAirport);
        stations.save(yangjae);

        firstFavorite = new Favorite(kkachisan, gangnam);
        secondFavorite = new Favorite(yongsan, jamsil);
        thirdFavorite = new Favorite(gimpoAirport, yangjae);
        favorites.save(firstFavorite);
        favorites.save(secondFavorite);
        favorites.save(thirdFavorite);

        firstMember.addFavorite(firstFavorite);
        secondMember.addFavorite(secondFavorite);
        thirdMember.addFavorite(thirdFavorite);
        members.save(firstMember);
        members.save(secondMember);
        members.save(thirdMember);
    }

    @Test
    @DisplayName("객체 저장 테스트")
    void saveAll() {
        final List<Member> before = members.findAll();
        assertSame(before.size(), 3);

        final List<Member> newMembers = new ArrayList<>();
        newMembers.add(new Member());
        newMembers.add(new Member());
        newMembers.add(new Member());

        final List<Member> afterSave = this.members.saveAll(newMembers);
        List<Member> findAllMembers = members.findAll();

        assertSame(findAllMembers.size(), 6);
    }

    @Test
    @DisplayName("객체를 이메일로 찾는 테스트")
    void findByEmail() {
        final String targetEmail = "jaenyeong.dev@gmail.com";
        final Member beforeSave = members.findByEmail(targetEmail)
            .orElse(null);

        assertThat(beforeSave).isNull();

        firstMember.changeMemberEmail(targetEmail);
        members.save(firstMember);
        final Member afterSave = members.findByEmail(targetEmail)
            .orElseThrow(RuntimeException::new);

        assertThat(afterSave).isNotNull();
        assertThat(afterSave.getEmail()).isNotNull();
    }

    @Test
    @DisplayName("객체를 나이로 찾는 테스트")
    void findByAge() {
        final List<Member> beforeSave = members.findByAge(18);

        assertSame(beforeSave.size(), 0);

        firstMember.changeMemberAge(18);
        secondMember.changeMemberAge(18);
        thirdMember.changeMemberAge(22);

        members.save(firstMember);
        members.save(secondMember);
        members.save(thirdMember);

        final List<Member> afterSave = members.findByAge(18);

        assertSame(afterSave.size(), 2);
    }

    @Test
    @DisplayName("멤버별 즐겨찾기 수 테스트")
    void numberOfFavorites() {
        assertSame(firstMember.getFavoritesSize(), 1);
        assertSame(secondMember.getFavoritesSize(), 1);
        assertSame(thirdMember.getFavoritesSize(), 1);
    }

    @Test
    @DisplayName("즐겨찾기 추가 테스트")
    void addFavorites() {
        final List<Favorite> newFavorites = new ArrayList<>();
        newFavorites.add(new Favorite(new Station("당산역"), new Station("사당역")));
        newFavorites.add(new Favorite(new Station("신정네거리역"), new Station("수원역")));

        firstMember.addFavorites(newFavorites);

        assertSame(firstMember.getFavoritesSize(), 3);
    }
}
