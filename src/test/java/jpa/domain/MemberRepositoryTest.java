package jpa.domain;

import jpa.common.JpaAuditingDate;
import jpa.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE
        , classes = {JpaAuditingConfig.class, JpaAuditingDate.class}
))
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Test
    @DisplayName("member 저장 테스트")
    public void save() {
        // given
        Member member = new Member();

        // when
        Member saveMember = this.memberRepository.save(member);

        // then
        assertAll(
                () -> assertThat(saveMember.getId()).isNotNull(),
                () -> assertEquals(saveMember, member),
                () -> assertThat(saveMember.getCreatedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("이름으로 member 조회 테스트")
    public void findByName() {
        // given
        Member member = memberRepository.save(new Member());
        Long memberId = member.getId();

        // when
        Optional<Member> findMember = this.memberRepository.findById(memberId);

        // then
        assertThat(findMember.get().getId()).isEqualTo(memberId);
    }

    @Test
    @DisplayName("사용자에서 즐겨찾기들 조회 테스트")
    public void findMappedFavorite() {
        // given
        Member member = new Member();
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        member.addFavorites(this.favoriteRepository.save(favorite1));
        member.addFavorites(this.favoriteRepository.save(favorite2));
        Member savedMember = this.memberRepository.save(member);

        // when
        Optional<Member> memberOptional = this.memberRepository.findById(savedMember.getId());
        List<Favorite> favorites = memberOptional.get().getFavorites();

        // then
        assertAll(
                () -> assertThat(favorites.size()).isEqualTo(2)
                , () -> assertThat(favorites.toString()).contains(favorite1.getId() + "", favorite2.getId() + "")
        );
    }
}
