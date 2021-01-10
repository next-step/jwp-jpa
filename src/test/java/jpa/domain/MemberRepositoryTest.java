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
    void member_onetomany_favorite_test() {
        //Given
        Member createdMember = members.save(new Member("truly-sparkle@gmail.com", "1q2w3e!@#", 29));

        Line blue = lines.save(new Line("1호선", "Blue"));

        Station jonggak = createStation(blue, "종각");
        Station jongno3ga = createStation(blue, "종로3가");

        //When
        Favorite favorite = new Favorite(jonggak, jongno3ga);
        createdMember.addFavorite(favorite);

        //Then
        Member expectedMember = members.findByEmail("truly-sparkle@gmail.com");
        Favorite expectedFavorite = expectedMember.getFirstFavorite();

        Assertions.assertThat(expectedFavorite.getSource()).isEqualTo(jonggak);
        Assertions.assertThat(expectedFavorite.getDestination()).isEqualTo(jongno3ga);

    }

    private Station createStation(Line line, String stationName) {
        Station station3 = new Station(stationName);
        station3.addLines(line);
        return stations.save(station3);
    }
}
