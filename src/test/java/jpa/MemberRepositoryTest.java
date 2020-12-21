package jpa;

import jpa.member.Member;
import jpa.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        String email = "eversong0723@gmail.com";
        String password = "1234";
        int age = 30;
        Member actual = memberRepository.save(new Member(email, password, age));
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(email),
                () -> assertThat(actual.getPassword()).isEqualTo(password),
                () -> assertThat(actual.getAge()).isEqualTo(age)
        );
    }

    @Test
    void findByEmail() {
        String expected = "eversong0723@gmail.com";
        memberRepository.save(new Member("eversong0723@gmail.com", "", 1));
        String actual = memberRepository.findByEmail(expected).get(0).getEmail();
        assertThat(actual).isEqualTo(expected);
    }


}
