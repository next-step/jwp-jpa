package jpa.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "line")
public class Line {

    public enum Color {
        RED, BLUE, GREEN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "line")
    private Set<StationLine> stationLines = new HashSet<>();

    public Line(final String name, final Color color) {
        this.name = name;
        this.color = color;
    }

    public Line changeName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("옯바른 이름이 아닙니다.");
        }
        this.name = name;
        return this;
    }

    public List<Station> stations() {
        return stationLines.stream()
                .map(StationLine::getStation)
                .collect(Collectors.toList());
    }
}

