package mr.park.tobycleanspringdddhexagonal.application.instructor;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.DuplicateInstructorApplicationException;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorApplication;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorApplyRequest;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorFinder;
import mr.park.tobycleanspringdddhexagonal.application.instructor.required.InstructorRepository;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberFinder;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ValidatedApplicationService;

@ValidatedApplicationService
@RequiredArgsConstructor
public class InstructorModifyService implements InstructorApplication {
    private final InstructorRepository instructorRepository;
    private final InstructorFinder instructorFinder;
    private final MemberFinder memberFinder;

    @Override
    public Instructor apply(InstructorApplyRequest applyRequest) {
        Member member = memberFinder.find(applyRequest.memberId());

        checkDuplicateApplication(member);

        Instructor instructor = Instructor.apply(member);

        return instructorRepository.save(instructor);
    }

    private void checkDuplicateApplication(Member member) {
        if (instructorRepository.findByMemberId(member.getId()).isPresent()) {
            throw new DuplicateInstructorApplicationException("회원은 중복해서 강사 신청을 할 수 없습니다.");
        }
    }

    @Override
    public Instructor approve(Long instructorId) {
        Instructor instructor = instructorFinder.find(instructorId);

        instructor.approve();

        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor reject(Long instructorId) {
        Instructor instructor = instructorFinder.find(instructorId);

        instructor.reject();

        return instructorRepository.save(instructor);
    }
}
