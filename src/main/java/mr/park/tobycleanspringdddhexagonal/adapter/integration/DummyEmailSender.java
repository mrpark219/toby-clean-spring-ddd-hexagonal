package mr.park.tobycleanspringdddhexagonal.adapter.integration;

import mr.park.tobycleanspringdddhexagonal.application.required.EmailSender;
import mr.park.tobycleanspringdddhexagonal.domain.Email;
import org.springframework.stereotype.Component;

@Component
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email: " + email);
    }
}
