package mr.park.tobycleanspringdddhexagonal.application;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.provided.MemberRegister;
import mr.park.tobycleanspringdddhexagonal.application.required.EmailSender;
import mr.park.tobycleanspringdddhexagonal.application.required.MemberRepository;
import mr.park.tobycleanspringdddhexagonal.domain.Member;
import mr.park.tobycleanspringdddhexagonal.domain.MemberRegisterRequest;
import mr.park.tobycleanspringdddhexagonal.domain.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.");

        return member;
    }
}
