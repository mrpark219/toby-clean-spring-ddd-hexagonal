package mr.park.tobycleanspringdddhexagonal.domain.instructor;

import jakarta.validation.Valid;
import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorApplyRequest;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture;

public class InstructorFixture {
    public static Instructor createInstructor(Member member) {
        return Instructor.apply(member);
    }

    public static Instructor createInstructor() {
        return createInstructor(MemberFixture.createActiveMember());
    }

    public static Instructor createActiveInstructor() {
        Instructor instructor = createInstructor();
        instructor.approve();
        return instructor;
    }

    public static @Valid InstructorApplyRequest createApplyRequest(Member member) {
        return new InstructorApplyRequest(member.getId());
    }
}
