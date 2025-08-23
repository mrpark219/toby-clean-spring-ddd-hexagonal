package mr.park.tobycleanspringdddhexagonal.application.member.provided;

import mr.park.tobycleanspringdddhexagonal.domain.member.Member;

/**
 * 회원을 조회한다.
 */
public interface MemberFinder {
    Member find(Long memberId);
}
