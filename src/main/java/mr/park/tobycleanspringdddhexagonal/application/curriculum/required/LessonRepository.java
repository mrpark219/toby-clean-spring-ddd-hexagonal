package mr.park.tobycleanspringdddhexagonal.application.curriculum.required;

import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Lesson;
import org.springframework.data.repository.Repository;

public interface LessonRepository extends Repository<Lesson, Long> {
    void delete(Lesson lesson);
}
