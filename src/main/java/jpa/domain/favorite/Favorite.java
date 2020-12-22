package jpa.domain.favorite;

import jpa.domain.BaseDateTimeEntity;
import jpa.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Favorite extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "favorite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteStation> favoriteStations = new ArrayList<>();

    public Favorite() {}

    public Favorite(Member member) {
        setMember(member);
    }

    public long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getFavorites().add(this);
    }

    public List<FavoriteStation> getFavoriteStations() {
        return favoriteStations;
    }

    public void addFavoriteStations(FavoriteStation favoriteStation) {
        favoriteStations.add(favoriteStation);
        favoriteStation.setFavorite(this);
    }

    public void removeFavoriteStation(FavoriteStation favoriteStation) {
        favoriteStations.remove(favoriteStation);
    }
}
