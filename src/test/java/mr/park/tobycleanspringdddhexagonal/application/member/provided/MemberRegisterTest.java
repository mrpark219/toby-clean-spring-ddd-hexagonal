package mr.park.tobycleanspringdddhexagonal.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import mr.park.tobycleanspringdddhexagonal.SplearnTestConfiguration;
import mr.park.tobycleanspringdddhexagonal.domain.member.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture.createMemberRegisterRequest;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberStatus.DEACTIVATED;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    private Member registerMember(String email) {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    @Test
    void deactivate() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("shpark", "park0219", "자기소개"));

        assertThat(member.getDetail().getProfile().address()).isEqualTo("park0219");
    }

    @Test
    void updateInfoFail() {
        Member member = registerMember();
        memberRegister.activate(member.getId());
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("shpark", "park0219", "자기소개"));

        Member member2 = registerMember("mrpark219.2@gmail.com");
        memberRegister.activate(member2.getId());
        entityManager.flush();
        entityManager.clear();

        // member2는 기존의 member와 같은 프로필 주소를 사용할 수 없다
        assertThatThrownBy(() -> {
            memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("mrpark", "park0219", "Introduction"));
        }).isInstanceOf(DuplicateProfileException.class);

        // 다른 프로필 주소로는 변경 가능
        memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("mrpark", "mrpark1234", "Introduction"));

        // 기존 프로필 주소를 바꾸는 것도 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("mrpark", "park0219", "Introduction"));

        // 프로필 주소를 제거하는 것도 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("mrpark", "", "Introduction"));

        // 프로필 주소 중복는 허용하지 않음
        assertThatThrownBy(() -> {
            memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("mrpark", "mrpark1234", "Introduction"));
        }).isInstanceOf(DuplicateProfileException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("mrpark219@gmail.com", "park", "longSecret"));
        checkValidation(new MemberRegisterRequest("mrpark219@gmail.com", "mrpark219_______________________", "longSecret"));
        checkValidation(new MemberRegisterRequest("mrpark219", "mrpark219", "longSecret"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
