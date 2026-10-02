package mr.park.tobycleanspringdddhexagonal.application.course;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseCreateRequest;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseCreator;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseFinder;
import mr.park.tobycleanspringdddhexagonal.application.course.provided.CourseInfoUpdateRequest;
import mr.park.tobycleanspringdddhexagonal.application.course.required.CourseRepository;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorFinder;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ValidatedApplicationService;

@ValidatedApplicationService
@RequiredArgsConstructor
public class CourseModifyService implements CourseCreator {
    private final CourseRepository courseRepository;
    private final CourseFinder courseFinder;
    private final InstructorFinder instructorFinder;

    @Override
    public Course createC(CourseCreateRequest createRequest) {
        Instructor instructor = instructorFinder.find(createRequest.instructorId());


        return null;
    }

    @Override
    public Course updateInfo(Long courseId, CourseInfoUpdateRequest infoUpdateRequest) {
        return null;
    }
}
