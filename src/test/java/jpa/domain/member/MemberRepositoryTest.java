package jpa.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.of(20, "pobi@test.com", "12345"));
        memberRepository.save(Member.of(30, "slamdunk@test.com", "678910"));
    }

    @Test
    @DisplayName("Member 추가 테스트")
    void insert_member_test() {
        // given
        Member member = Member.of(10, "ykj@test.com", "757575");

        // when
        Member persistMember = memberRepository.save(member);

        // then
        assertThat(persistMember.getId()).isNotNull();
        assertThat(persistMember.getCreatedDate()).isNotNull();
        assertThat(persistMember.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("Member 조회 테스트")
    void select_member_test() {
        // given
        Member member = Member.of(10, "ykj@test.com", "757575");

        // when
        Member persistMember = memberRepository.save(member);

        // then
        assertAll(
                () -> assertThat(persistMember.getAge()).isEqualTo(10),
                () -> assertThat(persistMember.getEmail()).isEqualTo("ykj@test.com"),
                () -> assertThat(persistMember.getPassword()).isEqualTo("757575")
        );
    }

    @Test
    @DisplayName("Member 전체 조회 테스트")
    void select_all_member_test() {
        // given
        Member member = Member.of(10, "ykj@test.com", "757575");
        memberRepository.save(member);

        // when
        List<Member> members = memberRepository.findAll();
        List<String> memberEmails = members.stream()
                .map(Member::getEmail)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(memberEmails.size()).isEqualTo(3),
                () -> assertThat(memberEmails).contains("pobi@test.com", "slamdunk@test.com", "ykj@test.com")
        );
    }

    @Test
    @DisplayName("Member 수정 테스트")
    void update_member_test() {
        // given
        String changeEmail = "change@test.com";

        // when
        Member member = memberRepository.findByEmail("pobi@test.com");
        member.updateEmail(changeEmail);

        Member updatedMember = memberRepository.findByEmail(changeEmail);

        // then
        assertThat(updatedMember.getEmail()).isEqualTo(changeEmail);
    }

    @Test
    @DisplayName("Member 삭제 테스트")
    void delete_member_test() {
        // given
        Member member = memberRepository.findByEmail("slamdunk@test.com");

        // when
        memberRepository.delete(member);

        // then
        Member deletedMember = memberRepository.findByEmail("slamdunk@test.com");
        assertThat(deletedMember).isNull();
    }
}
