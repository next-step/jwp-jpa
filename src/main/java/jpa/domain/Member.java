package jpa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites = new ArrayList<>();

    public Member(int age) {
        this(age, null);
    }

    public Member(int age, String email) {
        this.age = age;
        this.email = email;
    }

    public void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
              Objects.equals(age, member.age) &&
              Objects.equals(email, member.email) &&
              Objects.equals(password, member.password) &&
              isEqualsFavorites(member.favorites);
    }

    private boolean isEqualsFavorites(List<Favorite> favorites) {
        Set<Long> favoriteIds = this.favorites.stream()
              .map(Favorite::getId)
              .collect(Collectors.toSet());

        Optional<Favorite> matchFail = favorites.stream()
              .filter(favorite -> !favoriteIds.contains(favorite.getId()))
              .findFirst();

        return !matchFail.isPresent();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, email, password, favorites);
    }
}
