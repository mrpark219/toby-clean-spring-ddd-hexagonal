package mr.park.tobycleanspringdddhexagonal.application.course.provided;

import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.support.exception.ValidationException;

public interface CourseValidator {
    void validateForCreate(Instructor instructor, CourseCreateRequest createRequest) throws ValidationException;

    void validateForUpdate(Course course, CourseInfoUpdateRequest infoUpdateRequest) throws ValidationException;
}
