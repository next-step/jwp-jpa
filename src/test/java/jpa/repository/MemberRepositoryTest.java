package jpa.repository;

import jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member expected = new Member();
        Member actual = memberRepository.save(expected);
        assertAll(
                ()->assertNotNull(actual.getId())
        );
    }

    @Test
    void findByAge() {
        String expected = "27";
        Member member = new Member();
        member.setAge(expected);
        memberRepository.save(member);
        String actual = memberRepository.findByAge(expected).getAge();
        assertEquals(actual, expected);
    }

    @Test
    void findByEmail() {
        String expected = "jack@kakao.com";
        Member member = new Member();
        member.setEmail(expected);
        memberRepository.save(member);
        String actual = memberRepository.findByEmail(expected).getEmail();
        assertEquals(actual, expected);
    }
}