package mr.park.tobycleanspringdddhexagonal.application.course.required;

import mr.park.tobycleanspringdddhexagonal.domain.course.Course;

public interface CurriculumCreator {
    Long createCurriculum(Course course);
}
