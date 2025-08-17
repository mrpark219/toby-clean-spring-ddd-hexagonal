package mr.park.tobycleanspringdddhexagonal.adapter.integration;

import mr.park.tobycleanspringdddhexagonal.domain.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("mrpark219@gmail.com"), "subject", "body");

        assertThat(out.capturedLines()[0])
                .isEqualTo("DummyEmailSender send email: Email[address=mrpark219@gmail.com]");
    }
}