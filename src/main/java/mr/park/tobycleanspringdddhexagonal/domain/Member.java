package mr.park.tobycleanspringdddhexagonal.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

import static mr.park.tobycleanspringdddhexagonal.domain.MemberStatus.*;
import static org.springframework.util.Assert.state;

@Getter
@ToString
public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    public Member(String email, String nickname, String passwordHash) {
        this.email = Objects.requireNonNull(email);
        this.nickname = Objects.requireNonNull(nickname);
        this.passwordHash = Objects.requireNonNull(passwordHash);

        this.status = PENDING;
    }

    public void active() {
        state(status == PENDING, "PENDING 상태가 아닙니다.");

        this.status = ACTIVE;
    }

    public void deactivate() {
        state(status == ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = DEACTIVATED;
    }
}
