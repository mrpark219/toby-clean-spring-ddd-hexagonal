package mr.park.tobycleanspringdddhexagonal.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import mr.park.tobycleanspringdddhexagonal.SplearnTestConfiguration;
import mr.park.tobycleanspringdddhexagonal.domain.member.DuplicateEmailException;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture.createMemberRegisterRequest;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberStatus.ACTIVE;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("mrpark219@gmail.com", "park", "longSecret"));
        checkValidation(new MemberRegisterRequest("mrpark219@gmail.com", "mrpark219_______________________", "longSecret"));
        checkValidation(new MemberRegisterRequest("mrpark219", "mrpark219", "longSecret"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
