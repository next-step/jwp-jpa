package jpa.dao;

import jpa.repository.FavoriteRepository;
import jpa.repository.MemberRepository;
import jpa.repository.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @DisplayName("Member의 Favorite 조회")
    @Test
    void register() {
        Member member = memberRepository.save(new Member(20, "email@naver.com", "password"));
        Station departure = stationRepository.save(new Station("신도림"));
        Station arrival = stationRepository.save(new Station("영등포"));

        favoriteRepository.save(new Favorite(member, departure, arrival));

        Station arrival2 = stationRepository.save(new Station("서울"));

        favoriteRepository.save(new Favorite(member, departure, arrival2));

        Stream<Favorite> favoriteStream = favoriteRepository.findByMember_Password("password");

        assertThat(favoriteStream.count()).isEqualTo(2);
    }
}
