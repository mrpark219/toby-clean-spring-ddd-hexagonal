package mr.park.tobycleanspringdddhexagonal.domain.member;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mr.park.tobycleanspringdddhexagonal.domain.AbstractEntity;
import mr.park.tobycleanspringdddhexagonal.domain.shared.Email;
import org.hibernate.annotations.NaturalId;
import org.springframework.util.Assert;

import java.util.Objects;

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

    public void activate() {
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

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        Assert.state(getStatus() == ACTIVE, "등록 완료 상태가 아니면 정보를 수정할 수 없습니다.");

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
