package jpa.repositories;

import jpa.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {
    
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.of(30, "hazard@gmail.com", "1234"));
    }

    @Test
    @DisplayName("회원 추가")
    void save() {
        // given, when
        Member actual = memberRepository.save(Member.of(33, "justhis@gmail.com", "1234"));

        // then
        MemberAssertAll(actual, 33, "justhis@gmail.com", "1234");
    }

    @Test
    @DisplayName("회원 수정")
    void update() {
        // given
        String changeEmail = "justhis@gmail.com";

        // when
        Member Member = memberRepository.findByEmail("hazard@gmail.com");
        Member.setEmail(changeEmail);
        Member actual = memberRepository.save(Member);

        // then
        assertThat(actual.getEmail()).isEqualTo("justhis@gmail.com");
    }

    @Test
    @DisplayName("회원 삭제")
    void delete() {
        // given when
        memberRepository.delete(memberRepository.findByEmail("hazard@gmail.com"));
        Member actual = memberRepository.findByEmail("hazard@gmail.com");

        // then
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("회원 조회")
    void select() {
        // given when
        Member actual = memberRepository.findByEmail("hazard@gmail.com");

        // then
        MemberAssertAll(actual, 30,"hazard@gmail.com", "1234");
    }

    private void MemberAssertAll(Member actual, int expectedAge, String expectedEmail, String expectedPassword) {
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expectedAge),
                () -> assertThat(actual.getEmail()).isEqualTo(expectedEmail),
                () -> assertThat(actual.getPassword()).isEqualTo(expectedPassword),
                () -> assertThat(actual.getCreatedDate()).isNotNull(),
                () -> assertThat(actual.getModifiedDate()).isNotNull()
        );
    }
}