package mr.park.tobycleanspringdddhexagonal.application.enrollment;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.provied.EnrollmentFinder;
import mr.park.tobycleanspringdddhexagonal.application.enrollment.required.EnrollmentRepository;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationService;

import java.util.List;
import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class EnrollmentQueryService implements EnrollmentFinder {
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment find(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId).orElseThrow(
                () -> new IllegalArgumentException("수강을 찾을 수 없습니다. ID: " + enrollmentId)
        );
    }

    @Override
    public List<Enrollment> findByMember(Long memberId) {
        return enrollmentRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<Enrollment> findByMemberAndCourse(Long memberId, Long courseId) {
        return enrollmentRepository.findByMemberIdAndCourseId(memberId, courseId);
    }
}
