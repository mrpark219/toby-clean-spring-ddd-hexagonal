package mr.park.tobycleanspringdddhexagonal.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter
    private Long id;
}
