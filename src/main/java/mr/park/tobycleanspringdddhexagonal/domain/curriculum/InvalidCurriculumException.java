package mr.park.tobycleanspringdddhexagonal.domain.curriculum;

public class InvalidCurriculumException extends RuntimeException {
    public InvalidCurriculumException(String message) {
        super(message);
    }
}
