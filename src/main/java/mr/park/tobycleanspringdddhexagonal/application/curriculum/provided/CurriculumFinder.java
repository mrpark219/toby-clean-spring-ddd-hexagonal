package mr.park.tobycleanspringdddhexagonal.application.curriculum.provided;

import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Curriculum;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Lesson;

import java.util.Optional;

public interface CurriculumFinder {
    Curriculum find(Long curriculumId);

    Curriculum findWithSections(Long curriculumId);

    Curriculum findByCourse(Long courseId);

    Optional<Lesson> firstLesson(Long curriculumId);

    Optional<Lesson> nextLesson(Long curriculumId, Long lessonId);
}
