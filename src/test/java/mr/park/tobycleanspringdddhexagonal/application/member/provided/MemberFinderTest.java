package mr.park.tobycleanspringdddhexagonal.application.member.provided;

import jakarta.persistence.EntityManager;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationServiceTest;
import org.junit.jupiter.api.Test;

import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture.createMemberRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {
    @Test
    void find() {
        Member member = memberRegister.register(createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member found = memberFinder.find(member.getId());

        assertThat(member.getId()).isEqualTo(found.getId());
    }

    @Test
    void findFail() {
        assertThatThrownBy(() -> memberFinder.find(99L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}