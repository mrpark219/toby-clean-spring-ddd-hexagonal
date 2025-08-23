package mr.park.tobycleanspringdddhexagonal;

import mr.park.tobycleanspringdddhexagonal.application.member.required.EmailSender;
import mr.park.tobycleanspringdddhexagonal.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static mr.park.tobycleanspringdddhexagonal.domain.member.MemberFixture.createPasswordEncoder;

@TestConfiguration
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("Sending email: " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return createPasswordEncoder();
    }
}
