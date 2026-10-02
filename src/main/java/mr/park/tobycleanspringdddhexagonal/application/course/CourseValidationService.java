package mr.park.tobycleanspringdddhexagonal.application.course;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseCreateRequest;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseValidator;
import mr.park.tobycleanspringdddhexagonal.application.course.required.CourseRepository;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.support.exception.ValidationException;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationService;

import java.util.ArrayList;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class CourseValidationService implements CourseValidator {
    private final CourseRepository courseRepository;

    @Override
    public void validateForCreate(Instructor instructor, CourseCreateRequest createRequest) throws ValidationException {
        instructor.ensureActive();

        List<String> errors = new ArrayList<>();

        checkTitleDuplication(instructor, createRequest.title(), errors);
        checkBannedWords(createRequest.title(), errors);
        checkBannedWords(createRequest.description(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void checkBannedWords(String text, List<String> errors) {
        // TODO
    }

    private void checkTitleDuplication(Instructor instructor, String title, List<String> errors) {
        if (courseRepository.findByInstructorAndTitle(instructor, title).isPresent()) {
            errors.add("이미 사용 중인 강의 제목입니다. title: " + title);
        }
    }
}
