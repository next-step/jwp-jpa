package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "favorite")
    Set<FavoriteStation> favoriteStations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(final Member member) {
        if (Objects.nonNull(this.member)) {
            this.member.getFavorites().remove(this);
        }
        this.member = member;
        this.member.getFavorites().add(this);
    }

    public Optional<Station> startStation() {
        return favoriteStations.stream()
                .filter(FavoriteStation::isStart)
                .map(FavoriteStation::getStation)
                .findFirst();
    }

    public Optional<Station> endStation() {
        return favoriteStations.stream()
                .filter(FavoriteStation::isEnd)
                .map(FavoriteStation::getStation)
                .findFirst();
    }
}
