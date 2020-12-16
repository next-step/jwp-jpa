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

    private final Member testMember = new Member(3, "test@test.com", "test");

    @DisplayName("엔티티 저장 후 ID 부여 확인")
    @Test
    void saveTest() {
        memberRepository.save(testMember);

        assertThat(testMember.getId()).isNotNull();
    }

    @DisplayName("더티 체킹을 통한 업데이트 확인")
    @Test
    void updateTest() {
        assertThat(testMember.getModifiedDate()).isNull();
        Member changedMember = new Member(100, "modified", "modified");
        testMember.updateMember(changedMember);
        assertThat(testMember.getModifiedDate()).isNotNull();
    }

    @DisplayName("쿼리 메서드를 통한 조회 기능 확인")
    @Test
    void getTest() {
        int expectedSize = 1;
        memberRepository.save(testMember);

        assertThat(memberRepository.findAll()).hasSize(expectedSize);
    }

    @DisplayName("삭제 기능 확인")
    @Test
    void deleteTest() {
        Member saved = memberRepository.save(testMember);
        Long savedId = saved.getId();

        memberRepository.deleteById(savedId);

        assertThat(memberRepository.findById(savedId).isPresent()).isFalse();
    }
}
