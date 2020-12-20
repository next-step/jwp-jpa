package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column
  private int age;

  @Column
  private String password;

  public void changeAge(final int age) {
    if (age < 0) {
      throw new IllegalArgumentException("나이가 올바르지 않습니다.");
    }
    this.age = age;
  }

  public Member(final String email, final int age, final String password) {
    this.email = email;
    this.age = age;
    this.password = password;
  }

}
