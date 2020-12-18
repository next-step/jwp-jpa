package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository members;

    @Test
    @DisplayName("멤버 저장 테스트")
    public void save() throws Exception {
        Member expected = new Member();
        Member actual = members.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId()).isEqualTo(expected.getId())
        );
    }

    @Test
    @DisplayName("멤버 id로 조회 테스트")
    public void findById() throws Exception {
        Member expected1 = new Member(25, "email1@woowa.com", "0000");
        Member expected2 = members.save(expected1);
        Optional<Member> byId = members.findById(expected1.getId());

        assertAll(
                () -> assertThat(byId).isNotNull(),
                () -> assertThat(byId.get().getId()).isEqualTo(expected1.getId()),
                () -> assertThat(byId.get().getId()).isEqualTo(expected2.getId())
        );
    }

    @Test
    @DisplayName("멤버 age로 조회 테스트")
    public void findByAge() throws Exception {
        Member member1 = new Member(25, "email1@woowa.com", "0000");
        Member member2 = new Member(25, "email2@woowa.com", "0000");
        members.save(member1);
        members.save(member2);

        List<Member> byAge = members.findByAge(25);

        assertAll(
                () -> assertThat(byAge).isNotNull(),
                () -> assertThat(byAge).hasSize(2)
        );
    }

    @Test
    @DisplayName("멤버 email 로 조회 테스트")
    public void findByEmail() throws Exception {
        Member member1 = new Member(25, "email1@woowa.com", "0000");
        Member member2 = new Member(25, "email2@woowa.com", "0000");
        members.save(member1);
        members.save(member2);

        List<Member> byEmail = members.findByEmail("email1@woowa.com");

        assertAll(
                () -> assertThat(byEmail).isNotNull(),
                () -> assertThat(byEmail).hasSize(1)
        );
    }
}
