package jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Favorites {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private List<Favorite> favorites = new ArrayList<>();

    public void add(final Favorite favorite) {
        this.favorites.add(favorite);
    }

    public boolean isContains(final Station station) {
        long count = favorites.stream().filter(it -> it.isContain(station)).count();

        return count > 0L;
    }

    public void remove(final Favorite favorite) {
        this.favorites.remove(favorite);
    }

    public int size() {
        return this.favorites.size();
    }

    public Favorite getFirst() {
        return this.favorites.get(0);
    }
}
