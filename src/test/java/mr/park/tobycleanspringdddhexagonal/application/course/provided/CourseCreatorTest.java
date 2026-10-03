package mr.park.tobycleanspringdddhexagonal.application.course.provided;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.course.CourseFixture;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationServiceTest;
import mr.park.tobycleanspringdddhexagonal.support.test.BaseApplicationServiceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseCreatorTest extends BaseApplicationServiceTest {
    final CourseCreator courseCreator;

    @Test
    void create() {
        prepareInstructor();

        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));

        assertThat(course.getId()).isNotNull();
    }

    @Test
    void updateInfo() {
        prepareInstructor();
        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));

        Course updated = courseCreator.updateInfo(course.getId(), CourseFixture.createCourseInfoUpdateRequest("Updated"));

        assertThat(updated.getTitle()).isEqualTo("Updated");
    }
}