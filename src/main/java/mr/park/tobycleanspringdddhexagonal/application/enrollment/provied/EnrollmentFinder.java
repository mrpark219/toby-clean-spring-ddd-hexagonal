package mr.park.tobycleanspringdddhexagonal.application.enrollment.provied;

import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentFinder {
    Enrollment find(Long enrollmentId);

    List<Enrollment> findByMember(Long memberId);

    Optional<Enrollment> findByMemberAndCourse(Long memberId, Long courseId);
}
