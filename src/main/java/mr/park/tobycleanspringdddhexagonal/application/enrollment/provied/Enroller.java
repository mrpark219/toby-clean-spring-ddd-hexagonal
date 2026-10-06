package mr.park.tobycleanspringdddhexagonal.application.enrollment.provied;

import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;

/**
 * 수강 신청과 관리를 담당
 */
public interface Enroller {
    Enrollment enroll(Long memberId, Long courseId);

    Enrollment startStudying(Long enrollmentId);

    Enrollment complete(Long enrollmentId);
}
