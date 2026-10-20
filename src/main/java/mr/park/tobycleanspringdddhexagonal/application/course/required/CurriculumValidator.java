package mr.park.tobycleanspringdddhexagonal.application.course.required;

import mr.park.tobycleanspringdddhexagonal.domain.curriculum.InvalidCurriculumException;

public interface CurriculumValidator {
    void validate(Long courseId) throws InvalidCurriculumException;
}
