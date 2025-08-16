package mr.park.tobycleanspringdddhexagonal.application;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.application.provided.MemberFinder;
import mr.park.tobycleanspringdddhexagonal.application.required.MemberRepository;
import mr.park.tobycleanspringdddhexagonal.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
    }
}
