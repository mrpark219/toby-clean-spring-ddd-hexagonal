package mr.park.tobycleanspringdddhexagonal.domain;

import org.junit.jupiter.api.Test;

import static mr.park.tobycleanspringdddhexagonal.domain.MemberStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    @Test
    void createMember() {
        var member = new Member("mrpark219@gmail.com", "mrpark219", "secret");

        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "mrpark219", "secret"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        var member = new Member("mrpark219@gmail.com", "mrpark219", "secret");

        member.active();

        assertThat(member.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void activeFail() {
        var member = new Member("mrpark219@gmail.com", "mrpark219", "secret");

        member.active();

        assertThatThrownBy(member::active)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        var member = new Member("mrpark219@gmail.com", "mrpark219", "secret");
        member.active();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        var member = new Member("mrpark219@gmail.com", "mrpark219", "secret");

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);

        member.active();
        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }
}