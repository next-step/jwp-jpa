package jpa;

import jpa.domain.Member;
import jpa.domain.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;

    @Test
    void save() {
        Member expected = new Member(10, "mj@naver.com");
        Member actual = repository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expected.getAge()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @ParameterizedTest()
    @ValueSource(strings = {"mj2@naver.com", "mj3@naver.com"})
    void findByAge(String email) {
        repository.save(new Member(8, "mj1@naver.com"));
        repository.save(new Member(10, "mj2@naver.com"));
        repository.save(new Member(10, "mj3@naver.com"));

        List<Member> members = repository.findByAge(10);
        assertAll(
                () -> assertThat(members.size()).isEqualTo(2),
                () -> assertThat(members.contains(
                        new Member(10, email)))
        );
    }

    @Test
    void findByEmail() {
        repository.save(new Member(8, "mj1@naver.com"));
        repository.save(new Member(10, "mj2@naver.com"));

        Optional<Member> member = repository.findByEmail("mj2@naver.com");
        assertThat(member).isPresent();
        assertThat(member.get().getAge()).isEqualTo(10);
    }
}