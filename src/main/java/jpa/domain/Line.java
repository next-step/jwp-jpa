package jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "id")
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
}

