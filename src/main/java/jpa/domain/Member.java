package jpa.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column
  private int age;

  @Column
  private String password;

  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp
  private LocalDateTime modifiedDate;

  public void changeAge(final int age) {
    if (age < 0) {
      throw new IllegalArgumentException("나이가 올바르지 않습니다.");
    }
    this.age = age;
  }

  protected Member() {
  }

  public Member(final String email, final int age, final String password) {
    this.email = email;
    this.age = age;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public int getAge() {
    return age;
  }

  public String getPassword() {
    return password;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public LocalDateTime getModifiedDate() {
    return modifiedDate;
  }
}
