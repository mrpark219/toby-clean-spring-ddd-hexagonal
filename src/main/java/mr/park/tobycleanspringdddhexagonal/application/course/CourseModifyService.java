package mr.park.tobycleanspringdddhexagonal.application.course;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.*;
import mr.park.tobycleanspringdddhexagonal.application.course.required.CourseRepository;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorFinder;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.support.exception.ValidationException;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ValidatedApplicationService;

@ValidatedApplicationService
@RequiredArgsConstructor
public class CourseModifyService implements CourseCreator, CoursePublisher {
    private final CourseRepository courseRepository;
    private final CourseFinder courseFinder;
    private final CourseValidator courseValidator;
    private final InstructorFinder instructorFinder;

    @Override
    public Course create(CourseCreateRequest createRequest) throws ValidationException {
        Instructor instructor = instructorFinder.find(createRequest.instructorId());

        courseValidator.validateForCreate(instructor, createRequest);

        Course course = new Course(instructor, createRequest.title(), createRequest.description());

        return courseRepository.save(course);
    }

    @Override
    public Course updateInfo(Long courseId, CourseInfoUpdateRequest infoUpdateRequest) throws ValidationException {
        Course course = courseFinder.find(courseId);

        courseValidator.validateForUpdate(course, infoUpdateRequest);

        course.updateInfo(infoUpdateRequest.toInfo());

        return courseRepository.save(course);
    }

    @Override
    public Course submitForReview(Long courseId) {
        Course course = courseFinder.find(courseId);

        courseValidator.validateForReview(course);

        course.submitForReview();

        return courseRepository.save(course);
    }

    @Override
    public Course publish(Long courseId) {
        Course course = courseFinder.find(courseId);

        courseValidator.validateForPublish(course);

        course.publish();

        return courseRepository.save(course);
    }

    @Override
    public Course archive(Long courseId) {
        Course course = courseFinder.find(courseId);

        courseValidator.validateForArchive(course);

        course.archive();

        return courseRepository.save(course);
    }
}
