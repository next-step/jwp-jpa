package jpa.domain.favorite;

import jpa.domain.BaseDateTimeEntity;
import jpa.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "favorite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteStation> favoriteStations = new ArrayList<>();

    public Favorite() {}

    public Favorite(Member member) {
        changeMember(member);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public void changeMember(Member member) {
        this.member = member;
        member.addFavorite(this);
    }

    public List<FavoriteStation> getFavoriteStations() {
        return favoriteStations;
    }

    public void addFavoriteStation(FavoriteStation favoriteStation) {
        favoriteStations.add(favoriteStation);
        favoriteStation.changeFavorite(this);
    }

    public void removeFavoriteStation(FavoriteStation favoriteStation) {
        favoriteStations.remove(favoriteStation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite)) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
