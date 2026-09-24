package mr.park.tobycleanspringdddhexagonal.adapter.webapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.adapter.webapi.dto.MemberRegisterResponse;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberRegister;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberRegisterRequest;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.WebApiAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WebApiAdapter
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);
    }
}
