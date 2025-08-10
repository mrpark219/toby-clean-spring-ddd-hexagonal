package mr.park.tobycleanspringdddhexagonal.domain;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@Embeddable
public record Email(String address) {
    private static final Pattern EMAIL_PATTERN = compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public Email {
        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("이메일 형식이 바르지 않습니다: " + address);
        }
    }
}