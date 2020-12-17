package jpa.repository;

import jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member expected = new Member(30, "kim@gmail.com","qwerty1234");
        Member actual = memberRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getAge()).isEqualTo(expected.getAge()),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    void findByName() {
        String expected = "kim@gmail.com";
        memberRepository.save(new Member(30, "kim@gmail.com","qwerty1234"));
        List<Member> memberList = memberRepository.findByEmail(expected);
        assertThat(memberList.size()).isEqualTo(1);
    }
}
