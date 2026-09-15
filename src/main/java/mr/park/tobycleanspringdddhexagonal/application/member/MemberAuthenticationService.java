package mr.park.tobycleanspringdddhexagonal.application.member;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.LoginFailedException;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberAuthenticator;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberLoginRequest;
import mr.park.tobycleanspringdddhexagonal.application.member.required.MemberRepository;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.domain.member.PasswordEncoder;
import mr.park.tobycleanspringdddhexagonal.domain.shared.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberAuthenticationService implements MemberAuthenticator {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member login(MemberLoginRequest loginRequest) throws LoginFailedException {
        Member member = memberRepository.findByEmail(new Email(loginRequest.email()))
                .orElseThrow(LoginFailedException::new);

        if (!member.isActive()) {
            throw new LoginFailedException();
        }

        if (!member.verifyPassword(loginRequest.password(), passwordEncoder)) {
            throw new LoginFailedException();
        }

        return member;
    }
}
