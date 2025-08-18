package mr.park.tobycleanspringdddhexagonal.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;
import static mr.park.tobycleanspringdddhexagonal.domain.MemberStatus.*;
import static org.springframework.util.Assert.state;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Member extends AbstractEntity {
    @NaturalId
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    public static Member register(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder) {
        var member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));

        member.status = PENDING;

        return member;
    }

    public void active() {
        state(status == PENDING, "PENDING 상태가 아닙니다.");

        this.status = ACTIVE;
    }

    public void deactivate() {
        state(status == ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == ACTIVE;
    }
}
