package mr.park.tobycleanspringdddhexagonal.application.curriculum.required;

import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Section;
import org.springframework.data.repository.Repository;

public interface SectionRepository extends Repository<Section, Long> {
    void delete(Section section);
}
