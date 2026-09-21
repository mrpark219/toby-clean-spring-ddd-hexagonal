package mr.park.tobycleanspringdddhexagonal.application.instructor.provided;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.instructor.required.InstructorRepository;
import mr.park.tobycleanspringdddhexagonal.application.member.required.MemberRepository;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.Instructor;
import mr.park.tobycleanspringdddhexagonal.domain.instructor.InstructorStatus;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class InstructorApplicationTest {
    final InstructorApplication instructorApplication;
    final InstructorRepository instructorRepository;
    final MemberRepository memberRepository;

    @Test
    void apply() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);

        instructorRepository.findById(instructor.getId()).orElseThrow();
    }

    @Test
    void duplicateApply() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        assertThatThrownBy(
                () -> instructorApplication.apply(new InstructorApplyRequest(member.getId())))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void approve() {
        Instructor instructor = instructorApplication.approve(preparePendingInstructor().getId());

        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    void reject() {
        Instructor instructor = instructorApplication.reject(preparePendingInstructor().getId());

        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.REJECTED);
    }

    private Instructor preparePendingInstructor() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);
        return instructorApplication.apply(new InstructorApplyRequest(member.getId()));
    }
}