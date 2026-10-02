package mr.park.tobycleanspringdddhexagonal.application.course.required;

import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends Repository<Course, Long> {
    Course save(Course course);

    Optional<Course> findById(Long id);

    List<Course> findByTitleContaining(String keyword);

    default List<Course> findByInstructor(Instructor instructor) {
        return findByInstructorId(instructor.getId());
    }

    List<Course> findByInstructorId(Long instructorId);

    Optional<Course> findByInstructorAndTitle(Instructor instructor, String title);
}
