package mr.park.tobycleanspringdddhexagonal.domain.course;

import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.InstructorFixture;
import org.instancio.Instancio;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

import static org.instancio.Instancio.gen;
import static org.instancio.Select.field;

public class CourseFixture {
    public static Course createCourse(@Nullable Instructor instructor, @Nullable String title) {
        CourseDetail detail = Instancio.of(CourseDetail.class)
                .ignore(field(CourseDetail::getId))
                .generate(field(CourseDetail::getDescription), gen -> gen.string().maxLength(500).nullable())
                .set(field(CourseDetail::getCreatedAt), LocalDateTime.now())
                .create();

        return Instancio.of(Course.class)
                .ignore(field(Course::getId))
                .set(field(Course::getInstructor), instructor == null ? InstructorFixture.createActiveInstructor() : instructor)
                .set(field(Course::getTitle), title == null ? gen().string().maxLength(100).minLength(2).get() : title)
                .set(field(Course::getStatus), CourseStatus.DRAFT)
                .set(field(Course::getDetail), detail)
                .create();
    }

    public static Course createCourse() {
        return createCourse(null, null);
    }
}
