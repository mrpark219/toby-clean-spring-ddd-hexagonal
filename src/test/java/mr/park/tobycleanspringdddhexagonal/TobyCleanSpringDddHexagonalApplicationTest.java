package mr.park.tobycleanspringdddhexagonal;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static mr.park.tobycleanspringdddhexagonal.TobyCleanSpringDddHexagonalApplication.main;
import static org.mockito.Mockito.mockStatic;

class TobyCleanSpringDddHexagonalApplicationTest {
    @Test
    void run() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            main(new String[0]);

            mocked.verify(() -> SpringApplication.run(TobyCleanSpringDddHexagonalApplication.class, new String[0]));
        }
    }
}