package mr.park.tobycleanspringdddhexagonal.application.curriculum.provided;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Curriculum;
import mr.park.tobycleanspringdddhexagonal.support.stereotype.ApplicationServiceTest;
import mr.park.tobycleanspringdddhexagonal.support.test.BaseApplicationServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class CurriculumCoordinatorTest extends BaseApplicationServiceTest {
    final CurriculumCoordinator coordinator;
    @Autowired
    private CurriculumCoordinator curriculumCoordinator;

    @Test
    void create() {
        Curriculum curriculum = curriculumCoordinator.create(prepareCourse().getId());

        assertThat(curriculum.getId()).isNotNull();
    }
}