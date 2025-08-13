package mr.park.tobycleanspringdddhexagonal.application.provided;

import mr.park.tobycleanspringdddhexagonal.application.MemberService;
import mr.park.tobycleanspringdddhexagonal.application.required.EmailSender;
import mr.park.tobycleanspringdddhexagonal.application.required.MemberRepository;
import mr.park.tobycleanspringdddhexagonal.domain.Email;
import mr.park.tobycleanspringdddhexagonal.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static mr.park.tobycleanspringdddhexagonal.domain.MemberFixture.createMemberRegisterRequest;
import static mr.park.tobycleanspringdddhexagonal.domain.MemberFixture.createPasswordEncoder;
import static mr.park.tobycleanspringdddhexagonal.domain.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MemberRegisterTest {

    @Test
    void registerTestStub() {
        MemberRegister register = new MemberService(
                new MemberRepositoryStub(), new EmailSenderStub(), createPasswordEncoder()
        );

        Member member = register.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();
        MemberRegister register = new MemberService(
                new MemberRepositoryStub(), emailSenderMock, createPasswordEncoder()
        );

        Member member = register.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(PENDING);

        assertThat(emailSenderMock.getTos()).hasSize(1);
        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
    }

    @Test
    void registerTestMockito() {
        EmailSender emailSenderMock = mock(EmailSender.class);

        MemberRegister register = new MemberService(
                new MemberRepositoryStub(), emailSenderMock, createPasswordEncoder()
        );

        Member member = register.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(PENDING);

        verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
    }

    static class MemberRepositoryStub implements MemberRepository {
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }
    }

    static class EmailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String body) {
        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        public List<Email> getTos() {
            return tos;
        }

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }
    }
}