package mr.park.tobycleanspringdddhexagonal.application.course.provided;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.course.required.CourseRepository;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.course.CourseFixture;
import mr.park.tobycleanspringdddhexagonal.support.exception.ValidationException;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationServiceTest;
import mr.park.tobycleanspringdddhexagonal.support.test.BaseApplicationServiceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseValidatorTest extends BaseApplicationServiceTest {
    final CourseValidator courseValidator;
    final CourseRepository courseRepository;

    @Test
    void titleDuplication() {
        var instructor1 = prepareInstructor();
        var instructor2 = prepareInstructor();

        Course course1 = courseRepository.save(CourseFixture.createCourse(instructor1, "Clean Spring"));
        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "Clean Code"));

        // instructor1 중복되지 않는 제목 - OK
        courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "Spring 7", null));

        // instructor1 중복 제목 - FAIL
        assertThatThrownBy(() ->
                courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "Clean Spring", null))
        ).isInstanceOfSatisfying(ValidationException.class, e -> {
            assertThat(e.getErrors()).hasSize(1);
        });


        // instructor2 1과 중복되는 제목 - OK
        courseValidator.validateForCreate(instructor2, new CourseCreateRequest(instructor2.getId(), "Clean Spring", null));
    }

    @Test
    void titleDuplicationForUpdate() {
        var instructor1 = prepareInstructor();
        var instructor2 = prepareInstructor();

        Course course1_1 = courseRepository.save(CourseFixture.createCourse(instructor1, "Clean Spring"));
        Course course1_2 = courseRepository.save(CourseFixture.createCourse(instructor1, "Clean Code"));
        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "Clean Spring"));

        // instructor1 title 변경 없이 update - OK
        courseValidator.validateForUpdate(course1_1, CourseFixture.createCourseInfoUpdateRequest(course1_1.getTitle()));

        // instructor1 title 변경하는데 중복 발생 - FAIL
        assertThatThrownBy(() ->
                courseValidator.validateForUpdate(course1_1, CourseFixture.createCourseInfoUpdateRequest(course1_2.getTitle()))
        ).isInstanceOfSatisfying(ValidationException.class, e -> {
            assertThat(e.getErrors()).hasSize(1);
        });

        // instructor2 title 변경 없이 update - OK
        courseValidator.validateForUpdate(course2, CourseFixture.createCourseInfoUpdateRequest(course2.getTitle()));

        // instructor2 다른 강사의 강의 제목으로 변경 - OK
        courseValidator.validateForUpdate(course2, CourseFixture.createCourseInfoUpdateRequest(course1_2.getTitle()));
    }
}
