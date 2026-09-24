package mr.park.tobycleanspringdddhexagonal.application.instructor;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorFinder;
import mr.park.tobycleanspringdddhexagonal.application.instructor.required.InstructorRepository;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationService;

import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class InstructorQueryService implements InstructorFinder {
    private final InstructorRepository instructorRepository;

    @Override
    public Instructor find(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(
                () -> new IllegalArgumentException("강사를 찾을 수 없습니다. ID: " + instructorId)
        );
    }

    @Override
    public Optional<Instructor> findByMember(Long memberId) {
        return instructorRepository.findByMemberId(memberId);
    }
}
