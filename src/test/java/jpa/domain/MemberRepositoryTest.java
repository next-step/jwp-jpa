package jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository members;
    @Autowired
    private TestEntityManager em;
    private Member member;

    @BeforeEach
    void beforeEach() {
        member = new Member(20, "giveawesome@gmail.com", "AbCdEf123!@");
    }

    @DisplayName("`Member` 객체 저장")
    @Test
    void save() {
        // When
        Member actual = members.save(member);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, member)
        );
    }

    @DisplayName("이미 저장된 `Member`와 찾게된 `Member`의 동일성 여부 확인")
    @Test
    void findByEmail() {
        // Given
        members.save(member);
        // When
        Member actual = members.findByEmail("giveawesome@gmail.com")
                .stream()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지않는 이메일입니다."));
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertSame(actual, member)
        );
    }

    @DisplayName("`Member` 객체의 이메일 변경")
    @Test
    void changeEmail() {
        // Given
        String expected = "changed@gmail.com";
        Member actual = members.save(member);
        // When
        actual.changeEmail(expected);
        // Then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertThat(actual.getEmail()).isEqualTo(expected)
        );
    }

    @DisplayName("트랜잭션 안에서 `Member` 객체 삭제")
    @Test
    void deleteInTransaction() {
        // When
        members.delete(member);
        // Then
        assertAll(
                () -> assertNotNull(member)
        );
    }

    @DisplayName("엔티티 매니저로 1차 캐시를 관리하여, `Member` 객체 삭제")
    @Test
    void deleteWithEntityManager() {
        // Given
        long id = em.persistAndGetId(member, Long.class);
        // When
        members.delete(member);
        em.flush();
        Member actual = em.find(Member.class, id);
        // Then
        assertNull(actual);
    }
}
