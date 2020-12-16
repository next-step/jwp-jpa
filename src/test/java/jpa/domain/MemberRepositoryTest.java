package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository members;

    @DisplayName("단건 조회를 확인합니다.")
    @Test
    void findOne() {
        // given
        String email = "good_1411@naver.com";
        Member expected = members.save(new Member(28, email));

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
        Member member1 = new Member(28, "good_1411@naver.com");
        Member member3 = new Member(29, "next@gmail.com");
        Member member2 = new Member(30, "step@gmail.com");
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
    @Rollback(value = false)
    void save() {
        // given
        Member expected = new Member(28, "good_1411@naver.com");

        // when
        Member actual = members.save(expected);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(actual).isSameAs(expected)
        );
    }

    @DisplayName("변경 감지가 정상적으로 작동하는지 확인합니다.")
    @Test
    void update() {
        // given
        Member savedMember = members.save(new Member(28, "good_1411@naver.com"));

        // when
        Member expected = savedMember.changeEmail("nextstep@gmail.com");
        Member actual = members.findFirstByEmail(expected.getEmail()).get();

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }
}
