package jpa.domain;

import jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void create() {
        Member expected = new Member();
        expected.setPassword("1234");

        Member actual = memberRepository.save(expected);
        memberRepository.flush();
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

    @Test
    void update() {
        Member expected = new Member();
        expected.setPassword("1234");
        memberRepository.save(expected);

        expected.setPassword("3456");
        Member actual = memberRepository.save(expected);
        memberRepository.flush();

        assertThat(actual.getPassword()).isEqualTo("3456");
    }

    @Test
    void delete() {
        Member expected = new Member();
        expected.setEmail("chh9975@naver.com");
        expected.setAge(10);
        memberRepository.save(expected);

        Member actual = memberRepository.findByEmail(expected.getEmail());
        memberRepository.deleteById(actual.getId());

        memberRepository.flush();
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }
}
