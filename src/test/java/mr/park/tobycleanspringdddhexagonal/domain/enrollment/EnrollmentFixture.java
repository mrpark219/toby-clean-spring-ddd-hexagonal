package mr.park.tobycleanspringdddhexagonal.domain.enrollment;

import mr.park.tobycleanspringdddhexagonal.domain.course.Course;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import org.springframework.lang.Nullable;

import static mr.park.tobycleanspringdddhexagonal.domain.course.CourseFixture.createPublishedCourse;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture.createActiveMember;

public class EnrollmentFixture {
    public static Enrollment createEnrollment(@Nullable Member member, @Nullable Course course) {
        return Enrollment.enroll(
                member == null ? createActiveMember() : member,
                course == null ? createPublishedCourse() : course
        );
    }

    public static Enrollment createEnrollment() {
        return createEnrollment(null, null);
    }
}
