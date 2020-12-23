package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository members;

    @Test
    @DisplayName("멤버 저장 테스트")
    public void save() throws Exception {
        Member expected = new Member();
        Member actual = members.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId()).isEqualTo(expected.getId())
        );
    }

    @Test
    @DisplayName("멤버 id로 조회 테스트")
    public void findById() throws Exception {
        Member expected1 = new Member
                .Builder("email1@woowa.com", "0000")
                .age(25)
                .build();
        Member expected2 = members.save(expected1);
        Optional<Member> byId = members.findById(expected1.getId());

        assertAll(
                () -> assertThat(byId).isNotNull(),
                () -> assertThat(byId.get().getId()).isEqualTo(expected1.getId()),
                () -> assertThat(byId.get().getId()).isEqualTo(expected2.getId()),
                () -> assertThat(byId.get().getAge()).isEqualTo(25),
                () -> assertThat(byId.get().getEmail()).isEqualTo("email1@woowa.com"),
                () -> assertThat(byId.get().getPassword()).isEqualTo("0000")
        );
    }

    @Test
    @DisplayName("멤버 age로 조회 테스트")
    public void findByAge() throws Exception {
        Member member1 = new Member
                .Builder("email1@woowa.com", "0000")
                .age(25)
                .build();
        Member member2 = new Member
                .Builder("email2@woowa.com", "0000")
                .age(25)
                .build();
        members.save(member1);
        members.save(member2);

        List<Member> byAge = members.findByAge(25);

        assertAll(
                () -> assertThat(byAge).isNotNull(),
                () -> assertThat(byAge).hasSize(2),
                () -> assertThat(byAge.get(0).getAge()).isEqualTo(25),
                () -> assertThat(byAge.get(0).getEmail()).isEqualTo("email1@woowa.com"),
                () -> assertThat(byAge.get(0).getPassword()).isEqualTo("0000")
        );
    }

    @Test
    @DisplayName("멤버 email 로 조회 테스트")
    public void findByEmail() throws Exception {
        Member member1 = new Member
                .Builder("email1@woowa.com", "0000")
                .age(25)
                .build();
        Member member2 = new Member
                .Builder("email2@woowa.com", "0000")
                .age(25)
                .build();
        members.save(member1);
        members.save(member2);

        List<Member> byEmail = members.findByEmail("email1@woowa.com");

        assertAll(
                () -> assertThat(byEmail).isNotNull(),
                () -> assertThat(byEmail).hasSize(1),
                () -> assertThat(byEmail.get(0).getAge()).isEqualTo(25),
                () -> assertThat(byEmail.get(0).getEmail()).isEqualTo("email1@woowa.com"),
                () -> assertThat(byEmail.get(0).getPassword()).isEqualTo("0000")
        );
    }

    @Test
    @DisplayName("사용자는 여러 즐겨찾기를 가질 수 있다.")
    public void findMemberWithFavorite() throws Exception {
        Favorite favorite1 = new Favorite.Builder()
                                         .fromStation(new Station("화정역"))
                                         .toStation(new Station("잠실역"))
                                         .build();
        Favorite favorite2 = new Favorite.Builder()
                                         .fromStation(new Station("잠실역"))
                                         .toStation(new Station("강남역"))
                                         .build();
        Member member1 = new Member
                .Builder("email1@woowa.com", "0000")
                .age(25)
                .addFavorites(favorite1)
                .addFavorites(favorite2)
                .build();
        members.save(member1);

        Member expected = members.findById(member1.getId()).get();

        assertAll(
                () -> assertThat(expected).isNotNull(),
                () -> assertThat(expected.getFavorites()).hasSize(2),
                () -> assertThat(expected.getFavorites().get(0).getFromStationName()).isEqualTo(favorite1.getFromStationName()),
                () -> assertThat(expected.getFavorites().get(0).getToStationName()).isEqualTo(favorite1.getToStationName()),
                () -> assertThat(expected.getFavorites().get(1).getFromStationName()).isEqualTo(favorite2.getFromStationName()),
                () -> assertThat(expected.getFavorites().get(1).getToStationName()).isEqualTo(favorite2.getToStationName())
        );
    }
}
