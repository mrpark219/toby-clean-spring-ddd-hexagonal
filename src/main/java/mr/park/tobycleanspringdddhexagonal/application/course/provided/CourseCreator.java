package mr.park.tobycleanspringdddhexagonal.application.course.provided;

import jakarta.validation.Valid;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;

/**
 * 강의를 준비하는 작업
 */
public interface CourseCreator {
    Course createC(@Valid CourseCreateRequest createRequest);

    Course updateInfo(Long courseId, @Valid CourseInfoUpdateRequest infoUpdateRequest);
}
