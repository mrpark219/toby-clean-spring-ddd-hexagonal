package mr.park.tobycleanspringdddhexagonal.domain.enrollment;

import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.course.CourseFixture;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnrollmentTest {

    @Test
    void enroll() {
        Member member = MemberFixture.createActiveMember();
        Course course = CourseFixture.createPublishedCourse();

        Enrollment enroll = Enrollment.enroll(member, course);

        assertThat(enroll.getStatus()).isEqualTo(EnrollmentStatus.ENROLLED);
        assertThat(enroll.getEnrolledAt()).isNotNull();
    }

    @Test
    void enrollFailNotPublishedCourse() {
        Member member = MemberFixture.createActiveMember();
        Course course = CourseFixture.createCourse();

        assertThatThrownBy(() -> Enrollment.enroll(member, course))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void startStudying() {
        Enrollment enrollment = EnrollmentFixture.createEnrollment();

        enrollment.startStudying();

        assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.STUDYING);

        assertThatThrownBy(() -> enrollment.startStudying())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void complete() {
        Enrollment enrollment = EnrollmentFixture.createEnrollment();
        enrollment.startStudying();

        enrollment.complete();

        assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.COMPLETED);
        assertThat(enrollment.getCompletedAt()).isNotNull();

        assertThatThrownBy(() -> enrollment.complete())
                .isInstanceOf(IllegalStateException.class);
    }
}