package mr.park.tobycleanspringdddhexagonal.application.member.required;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture.createMemberRegisterRequest;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture.createPasswordEncoder;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@RequiredArgsConstructor
class MemberRepositoryTest {
    final MemberRepository memberRepository;
    final EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var found = memberRepository.findById(member.getId())
                .orElseThrow();
        assertThat(found.getStatus()).isEqualTo(PENDING);
        assertThat(found.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void duplicateEmailFail() {
        var registerRequest = createMemberRegisterRequest().toInfo();
        Member member1 = Member.register(registerRequest, createPasswordEncoder());
        memberRepository.save(member1);

        Member member2 = Member.register(registerRequest, createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}