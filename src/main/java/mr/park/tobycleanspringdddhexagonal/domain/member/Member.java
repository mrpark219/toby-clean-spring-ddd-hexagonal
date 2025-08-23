package mr.park.tobycleanspringdddhexagonal.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mr.park.tobycleanspringdddhexagonal.domain.AbstractEntity;
import mr.park.tobycleanspringdddhexagonal.domain.shared.Email;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;
import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberStatus.*;
import static org.springframework.util.Assert.state;

@Entity
@Getter
@ToString(callSuper = true, exclude = "detail")
@NoArgsConstructor(access = PROTECTED)
public class Member extends AbstractEntity {
    @NaturalId
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    @OneToOne(fetch = LAZY, cascade = ALL)
    private MemberDetail detail;

    public static Member register(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder) {
        var member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));

        member.status = PENDING;

        member.detail = MemberDetail.create();

        return member;
    }

    public void active() {
        state(status == PENDING, "PENDING 상태가 아닙니다.");

        this.status = ACTIVE;
        this.detail.active();
    }

    public void deactivate() {
        state(status == ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.nickname = Objects.requireNonNull(updateRequest.nickname());

        this.detail.updateInfo(updateRequest);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == ACTIVE;
    }
}
