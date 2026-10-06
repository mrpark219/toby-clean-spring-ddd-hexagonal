package mr.park.tobycleanspringdddhexagonal.application.enrollment.provied;

import jakarta.annotation.Nonnull;

public record EnrollRequest(
        @Nonnull Long memberId,
        @Nonnull Long courseId
) {
}
