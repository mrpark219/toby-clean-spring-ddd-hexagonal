package mr.park.tobycleanspringdddhexagonal.application.member.provided;

import jakarta.transaction.Transactional;
import mr.park.tobycleanspringdddhexagonal.SplearnTestConfiguration;
import mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
class MemberAuthenticatorTest {
    @Autowired
    private MemberAuthenticator memberAuthenticator;

    @Autowired
    private MemberRegister memberRegister;

    @Test
    void login() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        var member = memberRegister.register(registerRequest);
        member.activate();

        var loggedInMember = memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password()));
        assertThat(loggedInMember).isEqualTo(member);
    }

    @Test
    void loginFailedNotActive() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest);

        assertThatThrownBy(() ->
                memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password())))
                .isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedEmailNotExist() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        assertThatThrownBy(() ->
                memberAuthenticator.login(new MemberLoginRequest("noexist@email.com", registerRequest.password())))
                .isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedWrongPassword() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        assertThatThrownBy(() ->
                memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), "wrongpassword")))
                .isInstanceOf(LoginFailedException.class);
    }
}