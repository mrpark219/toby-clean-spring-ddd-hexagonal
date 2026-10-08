package mr.park.tobycleanspringdddhexagonal.application.enrollment.provied;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.EnrollmentStatus;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationServiceTest;
import mr.park.tobycleanspringdddhexagonal.support.test.BaseApplicationServiceTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class EnrollerTest extends BaseApplicationServiceTest {
    final Enroller enroller;

    @Test
    void enroll() {
        Member member = prepareActiveMember();
        Course course = preparePublishedCourse();

        Enrollment enrollment = enroller.enroll(new EnrollRequest(member.getId(), course.getId()));

        assertThat(enrollment.getId()).isNotNull();
    }

    @Test
    void enrollFailDuplicate() {
        prepareEnrollment();

        assertThatThrownBy(() -> enroller.enroll(new EnrollRequest(enrollment.getMember().getId(), enrollment.getCourse().getId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enrollFailNullIds() {
        assertThatThrownBy(() -> enroller.enroll(new EnrollRequest(null, null)))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void startStudying() {
        prepareEnrollment();

        Enrollment enrollmentStudying = enroller.startStudying(this.enrollment.getId());

        assertThat(enrollmentStudying.getStatus()).isEqualTo(EnrollmentStatus.STUDYING);
    }

    @Test
    void complete() {
        prepareEnrollment();
        enroller.startStudying(this.enrollment.getId());

        Enrollment enrollmentCompleted = enroller.complete(this.enrollment.getId());

        assertThat(enrollmentCompleted.getStatus()).isEqualTo(EnrollmentStatus.COMPLETED);
    }
}