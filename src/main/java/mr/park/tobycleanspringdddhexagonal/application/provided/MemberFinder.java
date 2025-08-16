package mr.park.tobycleanspringdddhexagonal.application.provided;

import mr.park.tobycleanspringdddhexagonal.domain.Member;

/**
 * 회원을 조회한다.
 */
public interface MemberFinder {
    Member find(Long memberId);
}
