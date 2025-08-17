package mr.park.tobycleanspringdddhexagonal.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailTest {
    @Test
    void equality() {
        var email1 = new Email("mrpark219@gmail.com");
        var email2 = new Email("mrpark219@gmail.com");

        assertEquals(email1, email2);
    }
}