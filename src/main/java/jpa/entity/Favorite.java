package jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_station")
    private Station sourceStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station")
    private Station destinationStation;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favorites")
    private final List<Member> members = new ArrayList<>();

    @Builder
    public Favorite(Station sourceStation, Station destinationStation) {
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    public void addMember(Member member) {
        member.getFavorites().add(this);
        this.members.add(member);
    }

    public void deleteMember(Member member) {
        member.getFavorites().remove(this);
        this.members.remove(member);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id) && Objects.equals(sourceStation, favorite.sourceStation) && Objects.equals(destinationStation, favorite.destinationStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceStation, destinationStation);
    }
}
