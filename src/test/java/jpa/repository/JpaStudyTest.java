package jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jpa.domain.Line;
import jpa.domain.Member;
import jpa.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class JpaStudyTest {

    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("entity 저장")
    @Test
    void save() {
        //given
        Station expected = Station.builder().name("잠실역").build();

        //when
        Station actual = stationRepository.save(expected);

        //then
        assertAll(
              () -> assertThat(actual.getId()).isNotNull(),
              () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @DisplayName("컬럼명으로 조회")
    @Test
    void findByName() {
        //given
        String expected = "잠실역";
        stationRepository.save(Station.builder()
              .name(expected)
              .build());

        //when
        String actual = stationRepository.findByName(expected).getName();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("나이가 10살인 회원 목록 조회")
    @Test
    void findAllByAge() {
        //when
        Optional<List<Member>> maybeMemberList = memberRepository.findAllByAge(10);

        //then
        assertThat(maybeMemberList.isPresent()).isTrue();
        assertThat(maybeMemberList.get()).hasSize(2);
    }

    @DisplayName("이메일이 dev.com 으로 끝나는 회원목록")
    @Test
    void findAllByEmailEndsWith() {
        //when
        List<Member> members1 = memberRepository.findAllByEmailEndsWith("dev.com");
        List<Member> members2 = memberRepository.findAllByEmailEndsWith("gmail.com");

        //then
        assertThat(members1).hasSize(3);
        assertThat(members2).isEmpty();
    }

    @DisplayName("entity 삭제")
    @Test
    void deleteByColor() {
        //when
        lineRepository.deleteAllByColor("green");

        //then
        assertThat(lineRepository.findAll()).hasSize(1);
    }

    @DisplayName("10_000개 in_batch 삭제")
    @Test
    void deleteInBatch() {
        //given
        List<Member> members = IntStream.rangeClosed(1, 1_000)
              .mapToObj(age -> Member.builder().age(age).email("test@batch.com").build())
              .collect(Collectors.toList());
        List<Member> savedMembers = memberRepository.saveAll(members);
        memberRepository.flush();

        //when
        memberRepository.deleteInBatch(savedMembers);

        //then
        assertThat(memberRepository.findAllByEmailEndsWith("batch.com")).isEmpty();
    }

    @DisplayName("직접 작성한 쿼리로 대상 삭제")
    @Test
    void deleteByQuery() {
        //given
        List<Member> members = IntStream.rangeClosed(1, 50)
              .mapToObj(age -> Member.builder().age(age).email("test@batch.com").build())
              .collect(Collectors.toList());
        memberRepository.saveAll(members);

        //when
        memberRepository.deleteByEmailInQuery("test@batch.com");

        //then
        assertThat(memberRepository.findAllByEmailEndsWith("batch.com")).isEmpty();
    }

    @DisplayName("동일성 보장 확인")
    @Test
    void identity() {
        //given
        Station station1 = stationRepository.save(Station.builder().name("잠실역").build());

        //when
        Station station2 = stationRepository.findById(station1.getId()).get();

        //then
        assertThat(station1 == station2).isTrue();
    }

    @DisplayName("생성시간, 변경시간 등록 확인")
    @Test
    void dateTime() {
        //given
        Member member = Member.builder().age(10).build();

        //when
        Member actual = memberRepository.save(member);

        //then
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getModifiedDate()).isNotNull();
    }

    @DisplayName("unique - 중복 오류 확인")
    @Test
    void nameDuplicateTest() {
        //given
        Line line1 = Line.builder().name("line2").color("green").build();
        Line line2 = Line.builder().name("line2").color("blue").build();

        lineRepository.save(line1);

        //when, then
        assertThatThrownBy(() -> lineRepository.save(line2))
              .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("학습테스트 - debugging. db반영 시점 확인")
    @Test
    void transaction() {
        System.out.println("===== [station1] save ====");
        Station station1 = stationRepository.save(Station.builder().name("잠실역").build());
        System.out.println("===== [station1] change name ====");
        station1.changeName("발산역");

        System.out.println("===== findByName ====");
        final Station station2 = stationRepository.findByName("발산역");
        assertThat(station2.getName()).isEqualTo("발산역");
    }
}
