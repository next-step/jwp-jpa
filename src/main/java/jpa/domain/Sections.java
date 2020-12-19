package jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
}
