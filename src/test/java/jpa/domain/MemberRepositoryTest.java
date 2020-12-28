package jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository members;
    @Autowired
    private LineRepository lines;
    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        Member expected = new Member("truly-sparkle@gmail.com", "1q2w3e!@#", 29);
        Member actual = members.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
            () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
            () -> assertThat(actual.getAge()).isEqualTo(expected.getAge())
        );
    }

    @Test
    void findByEmail() {
        String expected = "truly-sparkle@gmail.com";
        members.save(new Member(expected, "1q2w3e!@#", 29));
        String actual = members.findByEmail(expected).getEmail();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        Member actual = members.save(new Member("truly-sparkle@gmail.com", "1q2w3e!@#", 29));
        actual.changePassword("1234");
        Member member2 = members.findByEmail("truly-sparkle@gmail.com");
        assertThat(member2.getPassword()).isEqualTo("1234");
    }

    @Test
    void delete() {
        Member actual = members.save(new Member("truly-sparkle@gmail.com", "1q2w3e!@#", 29));
        members.delete(actual);
        Member member2 = members.findByEmail("truly-sparkle@gmail.com");
        assertThat(member2).isNull();

    }


    @Test
    void favorite() {
        //Given
        Member actual = members.save(new Member("truly-sparkle@gmail.com", "1q2w3e!@#", 29));

        Line blue = lines.save(new Line("1호선", "Blue"));
        Line green = lines.save(new Line("2호선", "Green"));

        Station cityHall = new Station("시청");
        cityHall.addLines(blue);
        cityHall.addLines(green);
        stations.save(cityHall);

        Station jonggak = createStation(blue, "종각");
        Station jongno3ga = createStation(blue, "종로3가");
        Station soyosan = createStation(blue, "소요산");

        //When
        Favorite favorite = new Favorite(jonggak, jongno3ga);
        actual.addFavorite(favorite);

        Favorite favorite2 = new Favorite(jongno3ga, soyosan);
        actual.addFavorite(favorite2);

        //Then
        Member expectedMember = members.findByEmail("truly-sparkle@gmail.com");
        List<Favorite> expectedFavorites = expectedMember.getFavorites();

        Favorite expectedFavorite = expectedFavorites.get(0);
        Station expectedSource = expectedFavorite.getSource();
        Station expectedDestination = expectedFavorite.getDestination();

        Assertions.assertThat(expectedSource).isEqualTo(jonggak);
        Assertions.assertThat(expectedDestination).isEqualTo(jongno3ga);

        Favorite expectedFavorite2 = expectedFavorites.get(1);
        Station expectedSource2 = expectedFavorite2.getSource();
        Station expectedDestination2 = expectedFavorite2.getDestination();

        Assertions.assertThat(expectedSource2).isEqualTo(jongno3ga);
        Assertions.assertThat(expectedDestination2).isEqualTo(soyosan);
    }

    private Station createStation(Line line, String stationName) {
        Station station3 = new Station(stationName);
        station3.addLines(line);
        return stations.save(station3);
    }
}
