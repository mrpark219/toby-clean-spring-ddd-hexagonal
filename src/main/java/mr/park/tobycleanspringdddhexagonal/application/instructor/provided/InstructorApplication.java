package mr.park.tobycleanspringdddhexagonal.application.instructor.provided;

import jakarta.validation.Valid;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;

/**
 * 강사 신청
 */
public interface InstructorApplication {
    Instructor apply(@Valid InstructorApplyRequest applyRequest);

    Instructor approve(Long memberId);

    Instructor reject(Long memberId);
}
