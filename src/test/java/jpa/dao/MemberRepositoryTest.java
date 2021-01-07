package jpa.dao;

import jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        final Member expect = new Member("abcd@test.com", "1111");
        expect.setAge(30);
        final Member actual = memberRepository.save(expect);
        assertThat(actual.getAge()).isEqualTo(30);
    }

    @Test
    void findById() {
        final Member expect = new Member("sunny@test.com", "2222");
        expect.setAge(60);
        final Member actual1 = memberRepository.save(expect);
        Optional<Member> actual2 = memberRepository.findById(actual1.getId());
        assertThat(actual2.get().getAge()).isEqualTo(actual1.getAge());
    }

    @Test
    void update() {
        final Member expect = new Member("carry@test.com", "3333");
        expect.setAge(40);
        expect.changeAge(50);
        final Member actual = memberRepository.save(expect);
        assertThat(actual.getAge()).isEqualTo(50);
    }

    @Test
    void delete() {
        final Member expect = new Member("minji@test.com", "7777");
        expect.setAge(30);
        memberRepository.save(expect);
        final Member actual1 = memberRepository.getOne(expect.getId());
        assertThat(expect == actual1).isTrue();
        memberRepository.delete(actual1);
        final List<Member> actual2 = memberRepository.findAll();
        assertThat(actual2).isEmpty();
    }
}
