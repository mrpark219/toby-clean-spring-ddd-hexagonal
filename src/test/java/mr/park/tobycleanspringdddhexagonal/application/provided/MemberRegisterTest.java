package mr.park.tobycleanspringdddhexagonal.application.provided;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import mr.park.tobycleanspringdddhexagonal.SplearnTestConfiguration;
import mr.park.tobycleanspringdddhexagonal.domain.DuplicateEmailException;
import mr.park.tobycleanspringdddhexagonal.domain.Member;
import mr.park.tobycleanspringdddhexagonal.domain.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import static mr.park.tobycleanspringdddhexagonal.domain.MemberFixture.createMemberRegisterRequest;
import static mr.park.tobycleanspringdddhexagonal.domain.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
@TestConstructor(autowireMode = ALL)
public record MemberRegisterTest(MemberRegister memberRegister) {


    @Test
    void register() {
        Member member = memberRegister.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = memberRegister.register(createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        extracted(new MemberRegisterRequest("mrpark219@gmail.com", "park", "longSecret"));
        extracted(new MemberRegisterRequest("mrpark219@gmail.com", "mrpark219_______________________", "longSecret"));
        extracted(new MemberRegisterRequest("mrpark219", "mrpark219", "longSecret"));
    }

    private void extracted(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
