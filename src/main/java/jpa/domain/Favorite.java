package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "favorite")
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "start_station_id")
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "end_station_id")
    private Station endStation;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Favorite(final String name, final Station startStation, final Station endStation) {
        this.name = name;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public void changeMember(final Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getFavorites().remove(this);
        }

        List<Favorite> favorites = member.getFavorites();
        if (!favorites.contains(this)) {
            favorites.add(this);
        }

        this.member = member;
    }

    public Favorite changeName(final String name) {
        this.name = name;
        return this;
    }

    public boolean isName(final String favoriteName) {
        return Objects.equals(name, favoriteName);
    }
}
