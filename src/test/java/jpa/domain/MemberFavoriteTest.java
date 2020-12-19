package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;

@DataJpaTest
public class MemberFavoriteTest {
    private Station gangnam = new Station("gangnam");
    private Station seocho = new Station("seocho");

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        stationRepository.saveAll(Arrays.asList(gangnam, seocho));
    }

    @DisplayName("POJO에 데이터를 추가하는 것처럼 Favorite을 추가할 수 있다.")
    @Test
    void saveTest() {
        Member member = new Member(31, "test@test.gmail.com", "password");

        member.addFavorite(new Favorite(gangnam.getId(), seocho.getId()));

        entityManager.flush();
        // 이후 favorite까지 저장되는 쿼리 확인
    }
}
