package jpa.repositories;

import jpa.domain.Member;
import jpa.domain.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository members;

  @Test
  void save() {
    final Member expected = getMemberSampelData();
    assertThat(expected.getId()).isNull();

    final Member actual = members.save(expected);
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getAge()).isEqualTo(34),
        () -> assertThat(actual.getEmail()).isEqualTo("jin87.an@gmail.com"),
        () -> assertThat(actual.getCreatedDate()).isNotNull(),
        () -> assertThat(actual.getModifiedDate()).isNotNull()
    );
  }

  @Test
  void update() {
    final Member member = members.save(getMemberSampelData());
    member.changeAge(35);
    members.flush();
    assertThat(member.getAge()).isEqualTo(35);
    assertThatThrownBy(() -> {
      member.changeAge(-1);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void delete() {
    final Member member = members.save(getMemberSampelData());
    members.delete(member);
    assertThat(members.findById(member.getId()).orElse(null)).isNull();
  }

  private Member getMemberSampelData() {
    return new Member("jin87.an@gmail.com", 34, "jinjin00!");
  }
}