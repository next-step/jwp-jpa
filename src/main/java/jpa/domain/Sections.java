package jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
public class Sections {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "line_id")
    private List<Section> sections = new ArrayList<>();

    public void addSection(final Section section) {
        this.sections.add(section);
    }

    public int size() {
        return this.sections.size();
    }

    public Long firstDistance() {
        return this.sections.get(0).getDistance();
    }

    public List<Station> getAllStations() {
        Set<Station> dupRemovedStations = this.sections.stream()
                .flatMap(it -> it.getStations().stream())
                .collect(Collectors.toSet());

        return new ArrayList<>(dupRemovedStations).stream()
                .sorted(Comparator.comparingLong(Station::getId))
                .collect(Collectors.toList());
    }
}
