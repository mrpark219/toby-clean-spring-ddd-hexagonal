package mr.park.tobycleanspringdddhexagonal.application.course;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseCreateRequest;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseInfoUpdateRequest;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseValidator;
import mr.park.tobycleanspringdddhexagonal.application.course.required.CourseRepository;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
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

        checkTitleDuplicationForCreate(instructor, createRequest.title(), errors);
        checkBannedWords(createRequest.title(), errors);
        checkBannedWords(createRequest.description(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateForUpdate(Course course, CourseInfoUpdateRequest infoUpdateRequest) throws ValidationException {
        List<String> errors = new ArrayList<>();

        checkTitleDuplicationForUpdate(course, course.getInstructor(), infoUpdateRequest.title(), errors);
        checkBannedWords(infoUpdateRequest.title(), errors);
        checkBannedWords(infoUpdateRequest.description(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void checkBannedWords(String text, List<String> errors) {
        // TODO
    }

    private void checkTitleDuplicationForCreate(Instructor instructor, String title, List<String> errors) {
        if (courseRepository.findByInstructorAndTitle(instructor, title).isPresent()) {
            errors.add("이미 사용 중인 강의 제목입니다. title: " + title);
        }
    }

    private void checkTitleDuplicationForUpdate(Course course, Instructor instructor, String title, List<String> errors) {
        courseRepository.findByInstructorAndTitle(instructor, title).ifPresent(found -> {
            if (!found.equals(course)) {
                errors.add("이미 사용 중인 강의 제목입니다. title: " + title);
            }
        });
    }
}
