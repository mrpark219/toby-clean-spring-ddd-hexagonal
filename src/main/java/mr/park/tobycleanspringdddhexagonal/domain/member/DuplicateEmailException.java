package mr.park.tobycleanspringdddhexagonal.domain.member;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
