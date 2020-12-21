package jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member extends BaseEntity{
    private int age;
    private String email;
    private String password;

    public static class Builder {
        private final String email;
        private final String password;

        private int age = 0;

        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }

    protected Member() {}

    private Member(Builder builder) {
        age      = builder.age;
        email    = builder.email;
        password = builder.password;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
