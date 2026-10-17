package mr.park.tobycleanspringdddhexagonal.application.curriculum.required;

import lombok.RequiredArgsConstructor;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Curriculum;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Lesson;
import mr.park.tobycleanspringdddhexagonal.domain.curriculum.Section;
import mr.park.tobycleanspringdddhexagonal.support.test.BaseRepositoryTest;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static mr.park.tobycleanspringdddhexagonal.domain.curriculum.LessonContent.lesson;
import static mr.park.tobycleanspringdddhexagonal.domain.curriculum.SectionContent.from;
import static mr.park.tobycleanspringdddhexagonal.domain.curriculum.SectionContent.section;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RequiredArgsConstructor
class CurriculumRepositoryTest extends BaseRepositoryTest {
    final CurriculumRepository curriculumRepository;
    final LessonRepository lessonRepository;
    final SectionRepository sectionRepository;

    @Test
    void saveAndFindById() {
        Long curriculumId = saveCurriculum();

        Statistics statistics = prepareStatistics();

        Curriculum found = curriculumRepository.findById(curriculumId).orElseThrow();

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
        assertThat(found.getId()).isEqualTo(curriculumId);

        found.allLessons();

        assertThat(from(found)).containsExactly(
                section("S1", lesson("L1"), lesson("L2")),
                section("S2", lesson("L3"))
        );

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(4);
    }

    @Test
    void saveAndFindWithSectionsById() {
        Long curriculumId = saveCurriculum();

        Statistics statistics = prepareStatistics();

        Curriculum found = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
        assertThat(found.getId()).isEqualTo(curriculumId);

        found.allLessons();

        assertThat(from(found)).containsExactly(
                section("S1", lesson("L1"), lesson("L2")),
                section("S2", lesson("L3"))
        );

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    @Test
    void removeLesson() {
        Long curriculumId = saveCurriculum();
        Curriculum curriculum = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();

        Lesson lesson = curriculum.removeLesson(0, 0);
        lessonRepository.delete(lesson);

        curriculumRepository.save(curriculum);

        entityManager.flush();
        entityManager.clear();

        curriculum = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();
        assertThat(from(curriculum)).containsExactly(
                section("S1", lesson("L2")),
                section("S2", lesson("L3"))
        );
    }

    @Test
    void removeSection() {
        Long curriculumId = saveCurriculum();
        Curriculum curriculum = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();

        Section section = curriculum.removeSection(0);
        sectionRepository.delete(section);

        curriculumRepository.save(curriculum);

        entityManager.flush();
        entityManager.clear();

        curriculum = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();
        assertThat(from(curriculum)).containsExactly(
                section("S2", lesson("L1"), lesson("L2"), lesson("L3"))
        );
    }

    @Test
    void moveLesson() {
        Long curriculumId = saveCurriculum();
        Curriculum curriculum = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();

        curriculum.moveLesson(0, 0, 1, 1);

        curriculumRepository.save(curriculum);

        entityManager.flush();
        entityManager.clear();

        curriculum = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();
        assertThat(from(curriculum)).containsExactly(
                section("S1", lesson("L2")),
                section("S2", lesson("L3"), lesson("L1"))
        );
    }

    private Long saveCurriculum() {
        Curriculum curriculum = new Curriculum(prepareCourse());

        curriculum = curriculumRepository.save(curriculum);
        curriculum.addSection("S1");
        curriculum.addLesson(0, "L1");
        curriculum.addLesson(0, "L2");
        curriculum.addSection("S2");
        curriculum.addLesson(1, "L3");

        entityManager.flush();
        entityManager.clear();

        return curriculum.getId();
    }
}