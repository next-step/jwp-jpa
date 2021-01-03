package jpa.repository;

import jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void create() {
        Member actual = memberRepository.save(new Member());
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void duplicationEmail() {
        String email = "yhjeong1009@gmail.com";
        Member member1 = memberRepository.save(new Member(email));
        Member member2 = memberRepository.save(new Member(email));
        List<Member> members = memberRepository.findByEmail(email);
        assertThat(members).containsExactly(member1, member2);
    }

    @Test
    void identity() {
        Member member1 = memberRepository.save(new Member());
        Member member2 = memberRepository.findById(member1.getId()).orElse(null);
        assertThat(member1 == member2).isTrue();
    }

    @Test
    void update() {
        Member member1 = memberRepository.save(new Member("yhjeong1009@gmail.com"));
        member1.setEmail("yhjeong1009@naver.com");

        // findById로 할 경우 업데이트가 되지 않고 flush를 해야만 update 됨
//        memberRepository.flush();
//        Member member2 = memberRepository.findById(member1.getId()).orElse(null);
//        assertThat(member1 == member2).isTrue();

        // findByEmail로 할 경우 flush 없이 update 됨
        List<Member> members = memberRepository.findByEmail("yhjeong1009@naver.com");
        assertThat(members).containsExactly(member1);
    }

    @Test
    void delete() {
        Member member1 = memberRepository.save(new Member());
        memberRepository.delete(member1);
        memberRepository.flush();
        Member member2 = memberRepository.findById(member1.getId()).orElse(null);
        assertThat(member2).isNull();
    }

}