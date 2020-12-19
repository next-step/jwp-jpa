package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberFavoriteTest {
    private Station gangnam = new Station("gangnam");
    private Station seocho = new Station("seocho");
    private Station samsung = new Station("samsung");

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        stationRepository.saveAll(Arrays.asList(gangnam, seocho, samsung));
    }

    @DisplayName("POJO에 데이터를 추가하는 것처럼 Favorite을 추가할 수 있다.")
    @Test
    void saveTest() {
        Member member = new Member(31, "test@test.gmail.com", "password");

        member.addFavorite(new Favorite(gangnam.getId(), seocho.getId()));

        memberRepository.save(member);
        // 이후 favorite까지 저장되는 쿼리 확인
    }

    @DisplayName("Member에 Favorite을 저장한 후 영속 컨텍스트에서 Favorite을 조회할 수 있다.")
    @Test
    void getFavoriteTest() {
        Member member = new Member(31, "test@test.gmail.com", "password");
        member.addFavorite(new Favorite(gangnam.getId(), seocho.getId()));
        memberRepository.save(member);

        Member foundMember = memberRepository.findByEmail(member.getEmail()).orElse(null);
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getFavorites().isContains(gangnam)).isTrue();
        assertThat(foundMember.getFavorites().isContains(seocho)).isTrue();
    }

    @DisplayName("더티 체킹을 통해 Member의 Favorite을 추가하고 저장할 수 있다.")
    @Test
    void updateTest() {
        Member member = new Member(31, "test@test.gmail.com", "password");
        member.addFavorite(new Favorite(gangnam.getId(), seocho.getId()));
        memberRepository.save(member);
        assertThat(member.getFavorites().size()).isEqualTo(1);

        member.addFavorite(new Favorite(samsung.getId(), seocho.getId()));
        Member foundMember = memberRepository.findByEmail(member.getEmail()).orElse(null);  // 쿼리 발생 확인
        assertThat(foundMember.getFavorites().size()).isEqualTo(2);
    }
}
