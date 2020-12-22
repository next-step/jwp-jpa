package jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member extends BaseEntity{
    private int age;
    private String email;
    private String password;
    @OneToMany
    private List<Favorite> favorites = new ArrayList<>();

    public static class Builder {
        private final String email;
        private final String password;

        private int age = 0;
        private List<Favorite> favorites = new ArrayList<>();

        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder addFavorites(Favorite favorite) {
            favorites.add(favorite);
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }

    protected Member() {}

    private Member(Builder builder) {
        age       = builder.age;
        email     = builder.email;
        password  = builder.password;
        favorites = builder.favorites;
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

    public List<Favorite> getFavorites() {
        return favorites;
    }
}
