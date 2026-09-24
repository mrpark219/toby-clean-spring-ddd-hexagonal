package mr.park.tobycleanspringdddhexagonal.application.member;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.member.provided.MemberFinder;
import mr.park.tobycleanspringdddhexagonal.application.member.required.MemberRepository;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationService;

@ApplicationService
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
    }
}
