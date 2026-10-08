package mr.park.tobycleanspringdddhexagonal.application.enrollment.provied;

import jakarta.validation.Valid;
import mr.park.tobycleanspringdddhexagonal.domain.enrollment.Enrollment;

/**
 * 수강 신청과 관리를 담당
 */
public interface Enroller {
    Enrollment enroll(@Valid EnrollRequest enrollRequest);

    Enrollment startStudying(Long enrollmentId);

    Enrollment complete(Long enrollmentId);
}
