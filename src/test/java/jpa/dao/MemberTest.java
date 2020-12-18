package jpa.dao;

import jpa.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest
public class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void init() {
        List<Member> members = Stream.iterate(1, i -> i +1 )
                .limit(5)
                .map(i -> new Member( i * 10, "test" + 1 + "@email.com", "password" ))
                .collect(Collectors.toList());
        memberRepository.saveAll(members);
    }


    @DisplayName("나이가 제일 많은사람 ?")
    @Test
    void findFirst1ByAge() {
        Member member = memberRepository.findTop1ByOrderByAgeDesc()
                .orElseGet(Member::new);
        System.out.println(member);
    }
}
