package mr.park.tobycleanspringdddhexagonal.application.instructor.provided;

import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;

import java.util.Optional;

/**
 * 강사 조회
 */
public interface InstructorFinder {
    Instructor find(Long instructorId);

    Optional<Instructor> findByMember(Long memberId);

    default Optional<Instructor> findByMember(Member member) {
        return findByMember(member.getId());
    }
}
