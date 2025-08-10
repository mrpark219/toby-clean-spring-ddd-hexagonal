package mr.park.tobycleanspringdddhexagonal.application.required;

import mr.park.tobycleanspringdddhexagonal.domain.Member;
import org.springframework.data.repository.Repository;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
}
