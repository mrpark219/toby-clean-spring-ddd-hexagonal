package mr.park.tobycleanspringdddhexagonal.domain;

import org.junit.jupiter.api.Test;

import static mr.park.tobycleanspringdddhexagonal.domain.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    @Test
    void createMember() {
        var member = new Member("mrpark219@gmail.com", "mrpark219", "secret");

        assertThat(member.getStatus()).isEqualTo(PENDING);
    }
}