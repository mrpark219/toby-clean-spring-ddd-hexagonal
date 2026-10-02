package mr.park.tobycleanspringdddhexagonal.support.test;

import mr.park.tobycleanspringdddhexagonal.application.instructor.provided.InstructorApplication;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberRegister;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.InstructorFixture;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationServiceTest;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationServiceTest
public class BaseApplicationServiceTest {
    @Autowired
    MemberRegister memberRegister;

    @Autowired
    InstructorApplication instructorApplication;

    protected Member member;
    protected Instructor instructor;

    @NonNull
    protected Instructor prepareInstructor() {
        this.member = prepareMember();

        this.instructor = instructorApplication.apply(InstructorFixture.createApplyRequest(member));
        this.instructor.approve();

        return this.instructor;
    }

    protected @NonNull Member prepareMember() {
        this.member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        this.member.activate();
        return this.member;
    }
}
