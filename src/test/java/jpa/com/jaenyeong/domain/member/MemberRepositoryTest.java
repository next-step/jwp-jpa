package jpa.com.jaenyeong.domain.member;

import jpa.com.jaenyeong.domain.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("Member Repository 테스트")
class MemberRepositoryTest extends BaseTest {
    @Autowired
    private MemberRepository members;

    private Member firstMember;
    private Member secondMember;
    private Member thirdMember;

    @BeforeEach
    void setUp() {
        firstMember = new Member();
        secondMember = new Member();
        thirdMember = new Member();
    }

    @Test
    @DisplayName("객체 저장 테스트")
    void saveAll() {
        final List<Member> before = members.findAll();
        assertSame(before.size(), 0);

        final List<Member> members = new ArrayList<>();
        members.add(firstMember);
        members.add(secondMember);
        members.add(thirdMember);

        final List<Member> afterSave = this.members.saveAll(members);

        assertSame(afterSave.size(), 3);
    }

    @Test
    @DisplayName("객체를 이메일로 찾는 테스트")
    void findByEmail() {
        final String targetEmail = "jaenyeong.dev@gmail.com";
        final Member beforeSave = members.findByEmail(targetEmail)
            .orElse(null);

        assertThat(beforeSave).isNull();

        firstMember.changeMemberEmail(targetEmail);
        members.save(firstMember);
        final Member afterSave = members.findByEmail(targetEmail)
            .orElseThrow(RuntimeException::new);

        assertThat(afterSave).isNotNull();
        assertThat(afterSave.getEmail()).isNotNull();
    }

    @Test
    @DisplayName("객체를 나이로 찾는 테스트")
    void findByAge() {
        final List<Member> beforeSave = members.findByAge(18);

        assertSame(beforeSave.size(), 0);

        firstMember.changeMemberAge(18);
        secondMember.changeMemberAge(18);
        thirdMember.changeMemberAge(22);

        members.save(firstMember);
        members.save(secondMember);
        members.save(thirdMember);

        final List<Member> afterSave = members.findByAge(18);

        assertSame(afterSave.size(), 2);
    }
}
