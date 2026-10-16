package mr.park.tobycleanspringdddhexagonal.application.curriculum.provided;

import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Curriculum;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Lesson;

public interface CurriculumFinder {
    Curriculum find(Long curriculumId);

    Curriculum findByCourse(Long courseId);

    Lesson firstLesson(Long curriculumId);

    Lesson nextLesson(Long curriculumId, Long lessonId);
}
