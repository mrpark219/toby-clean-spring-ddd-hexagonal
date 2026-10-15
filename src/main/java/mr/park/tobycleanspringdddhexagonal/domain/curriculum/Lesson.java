package mr.park.tobycleanspringdddhexagonal.domain.curriculum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mr.park.tobycleanspringdddhexagonal.domain.AbstractEntity;

import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true, exclude = {"section"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson extends AbstractEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Section section;

    @Column(length = 200)
    private String title;

    Lesson(Section section, String title) {
        this.section = section;
        this.title = Objects.requireNonNull(title);
    }

    void updateTitle(String title) {
        this.title = Objects.requireNonNull(title);
    }

    public void moveTo(Section section) {
        this.section = section;
    }
}
