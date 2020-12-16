package jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 엔티티 관련 기본 CRUD 테스트")
    @Test
    void simpleTest() {
        Integer memberAge = 10;
        String memberEmail = "test@test.com";
        String memberPassword = "password";

        // 새로운 엔티티 생성 및 영속화
        Member member = new Member(memberAge, memberEmail, memberPassword);
        memberRepository.save(member);

        // 영속화 시 자동으로 ID 부여
        assertThat(member.getId()).isNotNull();

        // 영속화 된 엔티티의 더티 체킹
        assertThat(member.getModifiedDate()).isNull();
        Member changedMember = new Member(1000, memberEmail, memberPassword);
        member.updateMember(changedMember);
        assertThat(member.getModifiedDate()).isNotNull();

        // 대상 삭제 후 조회 불가능
        memberRepository.deleteById(member.getId());
        assertThat(memberRepository.findById(member.getId()).isPresent()).isFalse();
    }
}
