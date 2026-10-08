package mr.park.tobycleanspringdddhexagonal.application.enrollment.provied;

import jakarta.validation.constraints.NotNull;

public record EnrollRequest(
        @NotNull Long memberId,
        @NotNull Long courseId
) {
}
